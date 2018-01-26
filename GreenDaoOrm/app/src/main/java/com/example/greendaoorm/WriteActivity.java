package com.example.greendaoorm;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class WriteActivity extends AppCompatActivity {
    private EditText editTitle;
    private EditText editContent;
    private Button saveBtn;
    MyApplication app;
    private DiaryDao mDiaryDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        app = MyApplication.getInstance();
        mDiaryDao = app.getDao();
        editTitle = (EditText)findViewById(R.id.editTitle);
        editContent = (EditText)findViewById(R.id.editContent);
        saveBtn = (Button)findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTitle.getText().toString();
                String content = editContent.getText().toString();
                Diary diary = new Diary(null,title,content,getTime());
                mDiaryDao.insert(diary);
                finish();
            }
        });
    }
    private String getTime(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        String time = calendar.get(Calendar.YEAR)+"年"
                + month +"月"
                + calendar.get(Calendar.DAY_OF_MONTH)+"日"
                + calendar.get(Calendar.HOUR_OF_DAY) + "时"
                + calendar.get(Calendar.MINUTE) +"分";
        return  time;
    }
}
