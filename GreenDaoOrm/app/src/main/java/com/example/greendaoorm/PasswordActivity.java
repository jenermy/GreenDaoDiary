package com.example.greendaoorm;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {
    private EditText inputEt;
    private EditText makesureEt;
    private Button savePasswordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        inputEt = (EditText)findViewById(R.id.inputEt);
        makesureEt = (EditText)findViewById(R.id.makesureEt);
        savePasswordBtn = (Button)findViewById(R.id.savePasswordBtn);
        savePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String one = inputEt.getText().toString().trim();
                String two = makesureEt.getText().toString().trim();
                if(TextUtils.isEmpty(one)){
                    inputEt.setError("请输入密码");
                    return;
                }
                if(TextUtils.isEmpty(two)){
                    makesureEt.setError("请确认密码");
                    return;
                }
                if(!one.equals(two)){
                    makesureEt.setError("两次输入密码不一致");
                    return;
                }
                SharedPreferences preferences = getSharedPreferences("pass",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("password",one);
                editor.putBoolean("isSet",true);
                editor.commit();
                Toast.makeText(PasswordActivity.this,"密码设置成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
