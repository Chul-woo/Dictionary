package com.example.owen_kim.dictionary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.owen_kim.dictionary.APIS.InternetConnection;
import com.example.owen_kim.dictionary.APIS.JSONParser;
import com.example.owen_kim.dictionary.APIS.PermissionChecker;
import com.example.owen_kim.dictionary.Requests.DicRegisterRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import static android.speech.tts.TextToSpeech.ERROR;

public class ViewActivity extends AppCompatActivity {
    static final int REQUEST_TAKE_PHOTO = 2001;
    static final int REQUEST_TAKE_ALBUM = 2002;

    private static final String EXTRA_PERMISSIONS = "com.example.owen_kim.dictionary.LoadingActivity.permissions";

    static String mCurrentPhotoPath;
    String imageFilePath = null;
    String user_id, user_name;
    static String photopath;

    URL url;

    File f;

    private TextToSpeech tts;
    ImageButton speaker;

    Uri albumURI, photoUri;
    Uri photoURI;

    ImageView imageView;
    Context mContext;

    PermissionChecker checker;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        if (getIntent() == null || !getIntent().hasExtra(EXTRA_PERMISSIONS)) {
            try {
            } catch (RuntimeException e) {
                e.printStackTrace();
                System.out.println("This Activity needs to be launched using the static startActivityForResult() method.");
            }
        }

        mContext = ViewActivity.this;

        /*
         * Permission Check Initialized
         */

        checker = new PermissionChecker(this);

        final TextView engText = (TextView)findViewById(R.id.engText);

        findViewById(R.id.album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(ViewActivity.this);

                ad.setMessage("사진을 불러올 방식을 선택하세요.");
                ad.setPositiveButton("카메라", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        captureCamera();
                    }
                }).setNegativeButton("앨범", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getAlbum();
                    }
                }).setNeutralButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();


            }
        });

        imageView = (ImageView) findViewById(R.id.imageView);

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mCurrentPhotoPath)) {
                    if (InternetConnection.checkConnection(mContext)) {
                        new AsyncTask<Void, Integer, Boolean>() {
                            ProgressDialog progressDialog;

                            protected void onPreExecute() {
                                super.onPreExecute();
                                progressDialog = new ProgressDialog(ViewActivity.this);
                                progressDialog.setMessage("서버로 보내는 중입니다.");
                                progressDialog.show();
                            }

                            protected Boolean doInBackground(Void... params) {
                                try {
                                    JSONObject jsonObject = JSONParser.uploadImage(mCurrentPhotoPath);
                                    if (jsonObject != null) {
                                        return jsonObject.getString("result").equals("success");
                                    }
                                } catch (Exception e) {
                                    Log.i("TAG", "에러 : " + e.getMessage());
                                }
                                return false;
                            }

                            protected void onPostExecute(Boolean bool) {
                                super.onPostExecute(bool);
                                if (progressDialog != null) {
                                    progressDialog.dismiss();
                                }

                                if(bool == true) {
                                    try {
                                        Toast.makeText(getApplicationContext(), R.string.string_upload_success, Toast.LENGTH_LONG).show();
                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), R.string.string_upload_fail, Toast.LENGTH_LONG).show();
                                    } finally {
                                        imageView.invalidate();         // 수정된 view 갱신하기
                                        imageView.setVisibility(View.VISIBLE);



                                        Thread thread = new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    url = new URL("http://133.186.144.151/result.txt");
                                                } catch (MalformedURLException e) {
                                                    e.printStackTrace();
                                                }

                                                InputStream is = null;
                                                try {
                                                    is = url.openStream();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                InputStreamReader isr = new InputStreamReader(is);

                                                final BufferedReader reader = new BufferedReader(isr);
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            String eng_words = reader.readLine();

                                                            Toast.makeText(ViewActivity.this, eng_words, Toast.LENGTH_LONG).show();

                                                            engText.setText(eng_words);

                                                        } catch (MalformedURLException e) {
                                                            e.printStackTrace();
                                                        }catch(IOException e){
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                        try {
                                            thread.sleep(4000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }finally {
                                            thread.start();
                                        }

                                    }
                                }

                            }

                        }.execute();
                    } else {
                        Toast.makeText(getApplicationContext(), "인터넷을 연결하세요", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "먼저 파일을 업로드하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

            ////////////////////////

        speaker = (ImageButton) findViewById(R.id.speaker);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR) {
                    // 언어를 선택한다.
                    tts.setLanguage(Locale.ENGLISH);

                }
            }
        });

        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.setSpeechRate(0.8f);
                tts.setPitch(1.3f);
                tts.speak(engText.getText().toString(),TextToSpeech.QUEUE_FLUSH, null);

            }
        });
            findViewById(R.id.addDic).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        String[] img_routes = mCurrentPhotoPath.split("/");
                        Intent intent = getIntent();
                        user_id = intent.getStringExtra("user_id");
                        user_name = intent.getStringExtra("user_name");
                        String eng_word = engText.getText().toString();

                        String img_route;

                        img_route = img_routes[img_routes.length - 1];


                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewActivity.this);
                                        builder.setMessage("나의 사전에서 확인하시겠습니까?")
                                                .setCancelable(true)        // 뒤로 버튼 클릭시 취소 가능 설정
                                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                    // 확인 버튼 클릭시 설정
                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                        Intent intent = new Intent(ViewActivity.this, DicActivity.class);
                                                        intent.putExtra("user_id", user_id);
                                                        intent.putExtra("user_name", user_name);
                                                        ViewActivity.this.startActivity(intent);
                                                    }
                                                })
                                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                                    // 취소 버튼 클릭시 설정
                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog dialog = builder.create();    // 알림창 객체 생성
                                        dialog.show();    // 알림창 띄우기
                                    } else if ((!success) || (engText.getText().equals("결과"))) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewActivity.this);
                                        builder.setMessage("나의 사전 추가에 실패했습니다.")
                                                .setNegativeButton("다시 시도", null)
                                                .create()
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        };
                        DicRegisterRequest DicregisterRequest = new DicRegisterRequest(user_id, eng_word, img_route, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(ViewActivity.this);
                        queue.add(DicregisterRequest);
                    }


            });
        }


    public void getAlbum() {
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_PICK);


        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Image to UPload");

        startActivityForResult(chooserIntent, REQUEST_TAKE_ALBUM);
    }

    private void captureCamera() {
        //String state = Environment.getExternalStorageState();
        //if(Environment.MEDIA_MOUNTED.equals(state)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                if (photoFile != null) {
                    photoURI = FileProvider.getUriForFile(ViewActivity.this, "com.example.owen_kim.dictionary.fileprovider", photoFile);

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }

            } else {
                Toast.makeText(ViewActivity.this, "외장메모리 미지원", Toast.LENGTH_LONG).show();
                return;
            }
        //}

    }

    String imageFileName;

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        imageFileName = "TEST_" + timeStamp + ".jpg";
        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/Dictionary/" + imageFileName);


        if (!storageDir.exists()) {
            Log.i("mCurrentPhotoPath1", storageDir.toString());
            storageDir.mkdirs();
        }

        File imageFile = new File(storageDir, imageFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();
        Log.i("mCurrentPhotoPath", mCurrentPhotoPath);
        galleryAddPic();

        return imageFile;

    }

    public void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setType("image/jpeg");
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
        //Toast.makeText(getApplicationContext(), "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data == null){
            Toast.makeText(this, "이미지를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
        }

        if (resultCode == RESULT_OK && requestCode == REQUEST_TAKE_ALBUM) {
            if (data == null){
                Toast.makeText(this, "이미지를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            albumURI = data.getData();

            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(albumURI, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mCurrentPhotoPath = cursor.getString(columnIndex);

                imageFilePath = mCurrentPhotoPath;

                Picasso.with(mContext).load(new File(mCurrentPhotoPath))
                        //.resize(256, 256)
                        //.transform(PicassoTransformations.resizeTransformation)
                        //.resizeDimen(R.dimen.image_size, R.dimen.image_size)
                        //.resizeDimen(300,300)
                        .fit()
                        .centerCrop()
                        .into(imageView);
                cursor.close();

            } else {
                Toast.makeText(this, "이미지를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
                galleryAddPic();
                getAlbum();
            }

            imageView.setVisibility(View.VISIBLE);
        }

        if (resultCode == RESULT_OK && requestCode == REQUEST_TAKE_PHOTO) {

            try{
                //Uri imageUri = data.getData();
                Uri imageUri = photoUri;

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                try {
                    if (cursor != null) {
                        cursor.moveToFirst();

                        int index = cursor.getColumnIndex(filePathColumn[0]);
                        mCurrentPhotoPath = cursor.getString(index);

                        Picasso.with(mContext).load(new File(mCurrentPhotoPath)).resize(256, 256)
                                .into(imageView);
                        cursor.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                imageView.setVisibility(View.VISIBLE);
            }catch (Exception e){

                  galleryAddPic();
                  getAlbum();
            }

        }
    }
}


/*
class PicassoTransformations{
    public static int targetWidth = 200;

    public static Transformation resizeTransformation = new Transformation() {
        @Override
        public Bitmap transform(Bitmap source) {
            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetHeight = (int) (targetWidth * aspectRatio);
            Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "resizeTransformation#" + System.currentTimeMillis();
        }
    };
}
*/
/*
    private String createImageFile(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        Uri uri = Uri.fromFile(new File(storageDir, imageFileName));
        return uri.toString();
    }
*/

/*
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + "dictionaries");
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    public static void createThumbnail(Bitmap bitmap, String strFilePath, String filename){
        File file = new File(strFilePath);
        if(!file.exists()){
            file.mkdirs();
        }
        File fileCahceItem = new File(strFilePath + filename);
        OutputStream out = null;

        try{
            int height = bitmap.getHeight();
            int width = bitmap.getWidth();

            fileCahceItem.createNewFile();
            out = new FileOutputStream(fileCahceItem);
            bitmap = Bitmap.createScaledBitmap(bitmap, 160,height/(width/160), true);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try{
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void startPermissionsActivity(String extraPermissions) {
        MainActivity.startActivityForResult(this, 0, extraPermissions);
    }

    */
