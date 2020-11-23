package com.example.notepad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notepad.bean.NotepadBean;
import com.example.notepad.database.SQLiteHelper;
import com.example.notepad.utils.DBUtils;

public class RecordActivity extends Activity implements View.OnClickListener {
    ImageView note_back_iv;
    TextView note_time_tv;
    EditText content_et;
    ImageView delete_iv;
    ImageView note_save_iv;
    SQLiteHelper mSQLiteHelper;
    TextView note_name_tv;
    String id;
    String temp_text;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        note_back_iv = (ImageView) findViewById(R.id.iv_back);
        note_time_tv = (TextView) findViewById(R.id.tv_time);
        content_et = (EditText) findViewById(R.id.et_note_content);
        delete_iv = (ImageView) findViewById(R.id.iv_delete);
        note_save_iv = (ImageView) findViewById(R.id.iv_save);
        note_name_tv = (TextView)  findViewById(R.id.tv_note_name);
        note_back_iv.setOnClickListener(this);
        note_save_iv.setOnClickListener(this);
        delete_iv.setOnClickListener(this);
        initData();
    }

    protected void initData(){
        mSQLiteHelper = new SQLiteHelper(this);
        note_name_tv.setText("添加记录");

        Intent intent = getIntent();
        if (intent!=null){
            id = intent.getStringExtra("id");
            if (id!=null){
                note_name_tv.setText("修改记录");
                content_et.setText(intent.getStringExtra("content"));
                note_time_tv.setText(intent.getStringExtra("time"));
                note_time_tv.setVisibility(View.VISIBLE);
            }
            temp_text = content_et.getText().toString();
        }
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_back:
                String noteContent = content_et.getText().toString();
                if (temp_text.equals(noteContent)==false){
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RecordActivity.this).setMessage("保存修改？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (id!=null){   //  修改记录
                                        if (noteContent.length()>0){
                                            if (mSQLiteHelper.updateData(id, noteContent, DBUtils.getTime())){
                                                showToast("保存成功");
                                                setResult(2);
                                                dialog.dismiss();
                                                finish();
                                            } else {
                                                showToast("保存失败");
                                            }
                                        } else {
                                            showToast("修改内容不能为空！");
                                        }
                                    } else {   // 新增记录
                                        if (noteContent.length()>0){
                                            if (mSQLiteHelper.insertData(noteContent, DBUtils.getTime())){
                                                showToast("保存成功");
                                                setResult(2);
                                                dialog.dismiss();
                                                finish();
                                            } else {
                                                showToast("保存失败");
                                            }
                                        } else {
                                            showToast("修改内容不能为空！");
                                        }
                                    }
                                }

                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                    dialog = builder.create();
                    dialog.show();
                }else{
                    finish();
                }

                break;
            case R.id.iv_delete:
                content_et.setText("");
                break;
            case R.id.iv_save:
                noteContent = content_et.getText().toString().trim();
                if (id!=null){   //  修改记录
                    if (noteContent.length()>0){
                        if (mSQLiteHelper.updateData(id, noteContent, DBUtils.getTime())){
                            showToast("保存成功");
                            setResult(2);
                            finish();
                        } else {
                            showToast("保存失败");
                        }
                    } else {
                        showToast("修改内容不能为空！");
                    }
                } else {   // 新增记录
                    if (noteContent.length()>0){
                        if (mSQLiteHelper.insertData(noteContent, DBUtils.getTime())){
                            showToast("保存成功");
                            setResult(2);
                            finish();
                        } else {
                            showToast("保存失败");
                        }
                    } else {
                        showToast("修改内容不能为空！");
                    }
                }
                break;
        }
    }

    public void showToast(String msg){
        Toast.makeText(RecordActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
