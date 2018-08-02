package com.example.owen_kim.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.owen_kim.dictionary.APIS.BackPressCloseSystem;
import com.example.owen_kim.dictionary.Requests.LoginRequest;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener{

    Button loginButton, registerButton;
    EditText idText, pwText;

    private BackPressCloseSystem backPressCloseSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idText = (EditText) findViewById(R.id.idText);
        pwText = (EditText) findViewById(R.id.pwText);
        pwText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        pwText.setTransformationMethod(PasswordTransformationMethod.getInstance());


        loginButton = (Button) findViewById(R.id.signIn);
        registerButton = (Button) findViewById(R.id.signUp);

        idText.setOnKeyListener(this);
            /*@Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER)){
                    pwText.requestFocus();
                    return true;
                }
                return false;
            }
        });*/

        pwText.setOnKeyListener(this);
            /*@Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER)){
                    loginButton.performClick();
                    return true;
                }
                return false;
            }
        });*/


        registerButton.setOnClickListener(this);

        loginButton.setOnClickListener(this);
           /* @Override
            public void onClick(View v) {

                final String user_id = idText.getText().toString();
                final String user_pw =  pwText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                String user_id = jsonResponse.getString("user_id");
                                String user_pw = jsonResponse.getString("user_pw");
                                String user_name = jsonResponse.getString("user_name");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("user_id", user_id);
                                intent.putExtra("user_pw", user_pw);
                                intent.putExtra("user_name",user_name);
                                LoginActivity.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("로그인에 실패하였습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();

                            }
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(user_id, user_pw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });*/

        //뒤로가기 두번 종료
        backPressCloseSystem = new BackPressCloseSystem(this);


    }

    public void onBackPressed(){
        backPressCloseSystem.onBackPressed();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signIn:

                final String user_id = idText.getText().toString().trim();
                final String user_pw =  pwText.getText().toString().trim();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                String user_id = jsonResponse.getString("user_id");
                                String user_pw = jsonResponse.getString("user_pw");
                                String user_name = jsonResponse.getString("user_name");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("user_id", user_id);
                                intent.putExtra("user_pw", user_pw);
                                intent.putExtra("user_name",user_name);
                                LoginActivity.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("로그인에 실패하였습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();

                            }
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(user_id, user_pw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

                break;
            case R.id.signUp:

                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);

                break;

        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        switch (view.getId()){
            case R.id.idText:

                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER)){
                    pwText.requestFocus();
                    return true;
                }

                break;
            case R.id.pwText:

                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER)){
                    loginButton.performClick();
                    return true;
                }

                break;
        }
        return false;
    }
}
