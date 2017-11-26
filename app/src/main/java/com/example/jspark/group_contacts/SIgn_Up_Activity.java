package com.example.jspark.group_contacts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jspark on 2017-09-02.
 */

public class SIgn_Up_Activity extends MainActivity {
    SharedPreferences sp;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);
        final EditText id_editText = (EditText) findViewById(R.id.Input_id);
        final EditText pw_editText = (EditText) findViewById(R.id.Input_pw);
        final EditText email_editText = (EditText) findViewById(R.id.Input_email);

        sp = getSharedPreferences("Login", MODE_PRIVATE);
        editor = sp.edit();

        Button Ok_btn = (Button) findViewById(R.id.signUp_Ok);
        Button Cancle_btn = (Button) findViewById(R.id.signUp_Cancle);
        Ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder Group_Add_Dialog = new AlertDialog.Builder(SIgn_Up_Activity.this);
                Group_Add_Dialog.setTitle("회원가입");
//                Group_Add_Dialog.setMessage("회원가입을 하시겠습니까?");
                if (id_editText.getText().toString().equals("")) {
                    Toast.makeText(SIgn_Up_Activity.this, "아이디를 설정하세요", Toast.LENGTH_SHORT).show();
                } else if (pw_editText.getText().toString().equals("")) {
                    Toast.makeText(SIgn_Up_Activity.this, "비밀번호를 설정하세요", Toast.LENGTH_SHORT).show();
                } else if (sp.getString("id", "").equals(id_editText.getText().toString())) {
                    Toast.makeText(SIgn_Up_Activity.this, "이미 있는 아이디 입니다", Toast.LENGTH_SHORT).show();
                } else {
                    final TextView textView_id = new TextView(SIgn_Up_Activity.this);
                    textView_id.setText("아이디는 " + id_editText.getText().toString() + "\n비밀번호는 " + pw_editText.getText().toString() + "입니다");
                    Group_Add_Dialog.setView(textView_id);
                    Group_Add_Dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editor.putString("id",id_editText.getText().toString());
                            editor.putString("pw", pw_editText.getText().toString());
                            editor.putString("email", email_editText.getText().toString());
                            editor.apply();
                            Toast.makeText(SIgn_Up_Activity.this, "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show();
                            finish();


                        }
                    });
                    Group_Add_Dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(SIgn_Up_Activity.this, "취소 버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = Group_Add_Dialog.create();
                    dialog.show();
                }

            }
        });
        Cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //엔터 클릭시 다음 에디트텍스트로 넘어가게 만듬
        id_editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    pw_editText.requestFocus();
                    pw_editText.setCursorVisible(true);
                    return true;
                }
                return false;
            }
        });
        pw_editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    email_editText.requestFocus();
                    email_editText.setCursorVisible(true);
                    return true;
                }
                return false;
            }
        });
        email_editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    id_editText.requestFocus();
                    id_editText.setCursorVisible(true);
                    return true;
                }
                return false;
            }
        });//에딧텍스트 역할 끝

    }
}
