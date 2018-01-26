package com.example.greendaoorm;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    private Button writeDiary;
    private ListView diaryList;
    private DiaryAdapter diaryAdapter;
    private DiaryDao mDiaryDao;
    private MyApplication app;
    private Cursor mCursor;
    private Button setPassword;
    private Dialog mDialog;
    private SharedPreferences sharedPreferences;
    private boolean isCheckPassword = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("pass",MODE_PRIVATE);
        writeDiary = (Button)findViewById(R.id.writeDiary);
        writeDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this,WriteActivity.class),101);
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });
        setPassword = (Button)findViewById(R.id.setPassword);
        setPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        diaryList = (ListView)findViewById(R.id.diaryList);
        diaryAdapter =  new DiaryAdapter(MainActivity.this);
        diaryList.setAdapter(diaryAdapter);
    }
    private void onRefresh(){
        mCursor = MyApplication.db.query(mDiaryDao.getTablename(),mDiaryDao.getAllColumns(),null,null,null,null,DiaryDao.Properties.Time .columnName+ " desc");
        diaryAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("wanlijun","onResume");
//        if(app == null){
//            app = MyApplication.getInstance();
//        }
            mDiaryDao = MyApplication.getDao();
            Log.i("wanlijun", "tablename1:" + mDiaryDao.getTablename());
            mCursor = MyApplication.db.query(mDiaryDao.getTablename(), mDiaryDao.getAllColumns(), null, null, null, null, DiaryDao.Properties.Time.columnName + " desc");
            diaryAdapter.notifyDataSetChanged();
    }
    private void showDialog(){
        mDialog = new Dialog(MainActivity.this,R.style.dialog);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.set_password,null);
        TextView digitPasswordTv =(TextView)view.findViewById(R.id.digitPasswordTv);
        TextView noPasswordTv = (TextView)view.findViewById(R.id.noPasswordTv);
        digitPasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                startActivityForResult(new Intent(MainActivity.this,PasswordActivity.class),102);
            }
        });
        noPasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("password","");
                editor.putBoolean("isSet",false);
                editor.commit();
                mDialog.dismiss();
            }
        });
        mDialog.setContentView(view);
        mDialog.setCancelable(false);
        mDialog.show();
    }



    class DiaryAdapter extends BaseAdapter{
        private Context mContext;
        public DiaryAdapter(Context context){
            this.mContext = context;
        }
        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if(view == null){
                view = LayoutInflater.from(mContext).inflate(R.layout.item_layout,null);
                viewHolder = new ViewHolder(view);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
            if(viewHolder != null){
                if(mCursor != null && mCursor.moveToPosition(i)){
                    String title = mCursor.getString(mCursor.getColumnIndexOrThrow(DiaryDao.Properties.Title.columnName));
                    String time = mCursor.getString(mCursor.getColumnIndexOrThrow(DiaryDao.Properties.Time.columnName));
                    viewHolder.title.setText(title);
                    viewHolder.time.setText(time);
                    viewHolder.itemLayout.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            Log.i("wanlijun","onLongClick:"+i);
                            mCursor.moveToPosition(i);
                            Long id = mCursor.getLong(mCursor.getColumnIndexOrThrow(DiaryDao.Properties.Id.columnName));
                            mDiaryDao.deleteByKey(id);
                            onRefresh();
                            return false;
                        }
                    });
                }
            }
            return view;
        }

        @Override
        public int getCount() {
            if(mCursor != null && mCursor.moveToFirst()){
                return mCursor.getCount();
            }else {
                return 0;
            }
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public Object getItem(int i) {
            if(mCursor != null && mCursor.moveToPosition(i)){
                return  mCursor;
            }else{
                return null;
            }
        }
    }
class ViewHolder{
        TextView title;
        TextView time;
        RelativeLayout itemLayout;
        public ViewHolder(View view){
            title = (TextView)view.findViewById(R.id.title);
            time = (TextView)view.findViewById(R.id.time);
            itemLayout = (RelativeLayout)view.findViewById(R.id.itemLayout);
            view.setTag(this);
        }
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("wanlijun","onActivityResult");
        if(requestCode == 101 || requestCode == 102){
            isCheckPassword = false;
        }
    }
}
