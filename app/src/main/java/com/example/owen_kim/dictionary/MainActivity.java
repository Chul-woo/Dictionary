package com.example.owen_kim.dictionary;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.owen_kim.dictionary.APIS.BackPressCloseSystem;
import com.example.owen_kim.dictionary.APIS.PermissionChecker;
import com.example.owen_kim.dictionary.APIS.User;

import java.util.ArrayList;
import java.util.List;

import static com.example.owen_kim.dictionary.LoadingActivity.permissions;


public class MainActivity extends AppCompatActivity {
    private ImageButton bt_Login; //임시로 로그인화면으로 가기위해 연결해 놓음
    private Button bt_Study;
    private Button bt_MyDic; //나의 사전으로 가는 버튼
    private Button bt_Game;
    private Button bt_FriendDic;

    public static String uid;

    public static final int PERMISSIONS_GRANTED = 0;
    public static final int PERMISSIONS_DENIED = 1;

    private static final int PERMISSION_REQUEST_CODE = 0;
    private static final String PACKAGE_URL_SCHEME = "package:";

    private PermissionChecker checker;
    private boolean requiresCheck;

    private static final String EXTRA_PERMISSIONS = "com.example.blues.dictionary.LoadingActivity.permissions";

    private static final int MULTIPLE_PERMISSIONS = 101; //권한 동의 여부 문의 후 CallBack 함수에 쓰일 변수

    private BackPressCloseSystem backPressCloseSystem;

    String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        checker = new PermissionChecker(this);
        requiresCheck = true;

        bt_MyDic = (Button)findViewById(R.id.myDic);
        bt_Login = (ImageButton)findViewById(R.id.imageButton4);
        bt_Study = (Button)findViewById(R.id.study);
        bt_FriendDic = (Button) findViewById(R.id.frDic);
        bt_Game = (Button) findViewById(R.id.game);

        //로그인 정보 받아오기
        final TextView nameText = (TextView) findViewById(R.id.nameText);

        User user = new User(MainActivity.this);
        final Intent intent = getIntent();
        final String user_id = intent.getStringExtra("user_id");
        //String user_pw = intent.getStringExtra("user_pw");
        user_name = intent.getStringExtra("user_name");
        user.setId(user_name);
        nameText.setText(user.getId());
        uid = user_name;


        //로그인화면 액티비티 이동
        bt_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        //학습하기화면 액티비티 이동
        bt_Study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("user_name", user_name);
                startActivity(intent);

            }
        });

        //게임화면 액티비티 이동
        bt_Game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);

            }
        });
        //나의 사전 버튼 눌렀을때 액티비티 이동
        bt_MyDic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DicActivity.class);
                intent.putExtra("user_id",user_id);
                intent.putExtra("user_name", user_name);
                startActivity(intent);
            }
        });

        bt_FriendDic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FriendListActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);

            }
        });



        //뒤로가기 두번 종료
        backPressCloseSystem = new BackPressCloseSystem(this);


    }

    private void logout(){
        new User(MainActivity.this).removeUser();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    public void onBackPressed(){
        backPressCloseSystem.onBackPressed();
    }


    private void requestPermissions(String[] permissions) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    private void allPermissionsGranted(){
        setResult(PERMISSIONS_GRANTED);
    }

    private boolean checkPermission(){
        int result;
        List<String> permissionList = new ArrayList<>();
        for(String pm : permissions){
            result = ContextCompat.checkSelfPermission(this,pm);

            //사용자가 해당 권한을 가지고 있지 않을 경우 리스트에 해당 권한명 추가
            if(result != PackageManager.PERMISSION_GRANTED){
                permissionList.add(pm);
            }
        }

        //권한이 추가되었으면 해당 리스트가 empty가 아니므로 request 즉 권한을 요청합니다.
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }

    //아래는 권한 요청 Callback 함수입니다. PERMISSION_GRANTED로 권한을 획득했는지 확인할 수 있습니다. 아래에서는 !=를 사용했기에
    //권한 사용에 동의를 안했을 경우를 if문으로 코딩되었습니다.

    public void onRequestPermissionResult(int requestCode, String permissions[], int[] grantResults){
        if(requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)){
            requiresCheck = true;
            allPermissionsGranted();
        }else{
            requiresCheck = false;
            showMissingPermissionDialog();
        }
    }

    private boolean hasAllPermissionsGranted(int[] grantResults){
        for(int grantResult : grantResults){
            if(grantResult == PackageManager.PERMISSION_DENIED){
                return false;
            }
        }
        return true;
    }

    private void showMissingPermissionDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        dialogBuilder.setTitle(R.string.help);
        dialogBuilder.setMessage(R.string.string_help_text);
        dialogBuilder.setNegativeButton(R.string.quit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(PERMISSIONS_DENIED);
                finish();
            }
        });

        dialogBuilder.show();
    }



    public String[] getPermissions() {
        return getIntent().getStringArrayExtra(EXTRA_PERMISSIONS);
    }

    public static void startActivityForResult(ViewActivity viewActivity, int requestCode, String... extraPermissions) {
        Intent intent = new Intent(viewActivity, MainActivity.class);
        intent.putExtra(EXTRA_PERMISSIONS, extraPermissions);
        ActivityCompat.startActivityForResult(viewActivity, intent, requestCode, null);
    }

    protected void onResume(){
        super.onResume();
        if (requiresCheck) {
            String[] permission = permissions;  // LoadingActivity에서 있는 것을 넣기

            if(checker.lacksPermission(permission)){
                //Toast.makeText(this, "2번",Toast.LENGTH_LONG).show();
                requestPermissions(permission);
            }else{
                //Toast.makeText(this, "3번",Toast.LENGTH_LONG).show();
                allPermissionsGranted();
            }
        }else{
            requiresCheck = true;
        }
    }
}
