package com.example.greendaoorm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

public class WelcomeActivity extends AppCompatActivity {
    private LinearLayout leftLayout;
    private LinearLayout rightLayout;
    private Button enterBtn;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在setContentView之前执行
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        sharedPreferences = getSharedPreferences("pass",MODE_PRIVATE);
        leftLayout = (LinearLayout)findViewById(R.id.leftLayout);
        rightLayout = (LinearLayout)findViewById(R.id.rightLayout);
        enterBtn = (Button)findViewById(R.id.enterBtn);
        Animation leftAnimation = AnimationUtils.loadAnimation(WelcomeActivity.this,R.anim.translate_left);
        Animation rightAnimation = AnimationUtils.loadAnimation(WelcomeActivity.this,R.anim.translate_right);
        leftAnimation.setFillAfter(true);
        rightAnimation.setFillAfter(true);
        leftLayout.setAnimation(leftAnimation);
        rightLayout.setAnimation(rightAnimation);
        leftAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                enterBtn.setVisibility(View.VISIBLE);
                enterBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isSet = sharedPreferences.getBoolean("isSet",false);
                        if(isSet){
                            checkPassword();
                        }else {
                            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                            finish();
                        }
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


    private void checkPassword(){
        final EditText editText = new EditText(WelcomeActivity.this);
        editText.setHint("请输入密码");
        editText.setPadding(10,50,10,10);
        editText.setBackground(getResources().getDrawable(R.drawable.edit_shape));
        AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
        builder.setTitle("请输入密码")
                .setIcon(getResources().getDrawable(android.R.drawable.ic_lock_lock))
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String password = sharedPreferences.getString("password","");
                        Log.i("wanlijun", "password:" + password);
                        String input = editText.getText().toString().trim();
                        if(password.equals(input)){
                            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                            finish();
                        }else{
                            //对话框不消失
                            try {
                                Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                                field.setAccessible(true);
                                field.set(dialogInterface,false);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            editText.setError("密码错误");
                        }
                    }
                });
        builder.setCancelable(false);
        builder.create().show();
    }
}
