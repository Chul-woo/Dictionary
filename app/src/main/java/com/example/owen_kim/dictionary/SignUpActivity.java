package com.example.owen_kim.dictionary;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.owen_kim.dictionary.Requests.RegisterRequest;
import com.example.owen_kim.dictionary.Requests.ValidateRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText userid = (EditText) findViewById(R.id.idText);
        final EditText username = (EditText) findViewById(R.id.nameText);
        final EditText useremail = (EditText) findViewById(R.id.emailText);
        final EditText userpw = (EditText) findViewById(R.id.pwText);
        final EditText cpwText = (EditText) findViewById(R.id.cpwText);

        final Button registerButton = (Button) findViewById(R.id.register);
        final Button validateButton = (Button) findViewById(R.id.validateButton);

        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String user_id = userid.getText().toString().trim();
                final String user_pw = userpw.getText().toString().trim();
                final String user_name = username.getText().toString().trim();
                final String user_email = useremail.getText().toString().trim();
                final String user_cpw = cpwText.getText().toString().trim();

                if(!validate){
                    Toast.makeText(getApplicationContext(), "중복체크를 먼저 해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                if(!user_pw.equals(user_cpw)){

                                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                    builder.setMessage("비밀번호가 틀립니다.")
                                            .setNegativeButton("확인", null)
                                            .create()
                                            .show();

                                }else if(user_id.equals("") || user_pw.equals("") || user_name.equals("") || user_email.equals("") || user_cpw.equals("")){

                                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                    builder.setMessage("빈 칸을 모두 채워주세요.")
                                            .setNegativeButton("확인", null)
                                            .create()
                                            .show();

                                } else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                    builder.setMessage("회원 등록에 성공했습니다.")
                                            .setPositiveButton("확인", null)
                                            .create()
                                            .show();
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                    SignUpActivity.this.startActivity(intent);
                                    SignUpActivity.this.finish();
                                }
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                builder.setMessage("회원 등록에 실패했습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(user_id, user_pw, user_name, user_email, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                queue.add(registerRequest);
            }

        });

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id = userid.getText().toString().trim();
                if(validate){
                    return;
                }
                if(user_id.equals("")){
                    Toast.makeText(getApplicationContext(), "아이디를 채우세요.", Toast.LENGTH_SHORT).show();
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Toast.makeText(getApplicationContext(), "사용할 수 있는 아이디입니다.", Toast.LENGTH_SHORT).show();
                                userid.setEnabled(false);
                                validateButton.setEnabled(false);
                                validate = true;

                            }else{
                                Toast.makeText(getApplicationContext(), "사용할 수 없는 아이디입니다.", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                ValidateRequest validateRequest = new ValidateRequest(user_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                queue.add(validateRequest);

            }
        });

        userid.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER)){
                    username.requestFocus();
                    return true;
                }
                return false;
            }
        });

        username.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER)){
                    useremail.requestFocus();
                    return true;
                }
                return false;
            }
        });

        useremail.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER)){
                    userpw.requestFocus();
                    return true;
                }
                return false;
            }
        });

        userpw.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER)){
                    cpwText.requestFocus();
                    return true;
                }
                return false;
            }
        });

        cpwText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER)){
                    registerButton.performClick();
                    return true;
                }
                return false;
            }
        });

        //뒤로가기
        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                SignUpActivity.this.finish();
            }
        });
    }

}
