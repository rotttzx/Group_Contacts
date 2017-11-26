package com.example.jspark.group_contacts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jspark on 2017-09-01.
 */

public class LogIn_Activity extends MainActivity {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    CheckBox logincb;
    Boolean loginchecked;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);









        final int[] images = {R.drawable.ad1, R.drawable.ad2, R.drawable.ad3,R.drawable.ad4};





        SharedPreferences loginsp = getSharedPreferences("로그인확인", MODE_PRIVATE);
        SharedPreferences.Editor logineditor = loginsp.edit();
        logincb = (CheckBox) findViewById(R.id.autologin);

        if (loginsp.getBoolean("login_ok", false) == true) {
            Intent intent = new Intent(LogIn_Activity.this, Group_ListView.class);
            startActivity(intent);
            Toast.makeText(LogIn_Activity.this, "자동로그인 상태입니다", Toast.LENGTH_SHORT).show();
            finish();
        }


        Button login_btn = (Button) findViewById(R.id.loginButton);
        Button signUp_btn = (Button) findViewById(R.id.signUpButton);
        final EditText editText = (EditText) findViewById(R.id.login_id);
        final EditText editText1 = (EditText) findViewById(R.id.login_pw);

        //엔터 클릭시 다음에디트 텍스트로 넘어가게 만듬
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    editText1.requestFocus();
                    editText1.setCursorVisible(true);
//                    InputMethodManager imm = (InputMethodManager) getSystemService(LogIn_Activity.this.INPUT_METHOD_SERVICE);
//                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_IMPLICIT);
                    return true;
                }
                return false;
            }
        });
        editText1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    editText.requestFocus();
                    editText.setCursorVisible(true);
//                    InputMethodManager imm = (InputMethodManager) getSystemService(LogIn_Activity.this.INPUT_METHOD_SERVICE);
//                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return true;
                }
                return false;
            }
        });//에딧텍스트 역할 끝

//로그인버튼 이벤트
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = getSharedPreferences("Login", MODE_PRIVATE);
                Intent intent1 = new Intent(LogIn_Activity.this, MainActivity.class);
                Intent intent = new Intent(LogIn_Activity.this, Group_ListView.class);
                if (loginValidation(editText.getText().toString(), editText1.getText().toString()) == true) {
                    Toast.makeText(LogIn_Activity.this, "로그인 되셨습니다", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_activity_rightin,R.anim.anim_activity_notmove);
                    finish();

                } else if (editText.getText().toString().equals("")) {
                    Toast.makeText(LogIn_Activity.this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();

                } else if (editText1.getText().toString().equals("")) {
                    Toast.makeText(LogIn_Activity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();

                } else if (!editText.getText().toString().equals(sp.getString("id", ""))) {
                    Toast.makeText(LogIn_Activity.this, "아이디가 없습니다", Toast.LENGTH_SHORT).show();
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake_anim);
                    editText.startAnimation(animation);

                } else if (!editText1.getText().toString().equals(sp.getString("pw", ""))) {
                    Toast.makeText(LogIn_Activity.this, "비밀번호가 다릅니다", Toast.LENGTH_SHORT).show();
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake_anim);
                    editText1.startAnimation(animation);


                } else {
                    Toast.makeText(LogIn_Activity.this, "회원이 아닙니다 회원가입을 해주세요", Toast.LENGTH_LONG).show();
                }

            }
        });
        //회원가입버튼 이벤트
        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn_Activity.this, SIgn_Up_Activity.class);
                startActivity(intent);
            }
        });


    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        EditText editText = (EditText)findViewById(R.id.login_id);
//        EditText editText1 = (EditText)findViewById(R.id.login_pw);
//        String id = editText.getText().toString();
//        String pw = editText1.getText().toString();
//        sp = getSharedPreferences("LogIn",MODE_PRIVATE);
//        editor = sp.edit();
//        editor.putString("id",id);
//        editor.putString("pw",pw);
//        editor.apply();
//    }

    private boolean loginValidation(String id, String password) {
        if (sp.getString("id", "").equals(id) && sp.getString("pw", "").equals(password)) {
            // login success
            return true;
        } else if (sp.getString("id", "").equals(null)) {
            // sign in first
            Toast.makeText(LogIn_Activity.this, "아이디가 없습니다", Toast.LENGTH_SHORT).show();
            return false;
        } else if (sp.getString("pw", "").equals(null)) {
            Toast.makeText(LogIn_Activity.this, "비밀번호가 다릅니다", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            // login failed
            return false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView textView = (TextView)findViewById(R.id.textView2);
        textView.startAnimation(new CameraAnim());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        final EditText editText = (EditText) findViewById(R.id.login_id);
        final EditText editText1 = (EditText) findViewById(R.id.login_pw);
        editText.setText("");
        editText1.setText("");


    }

    @Override
    protected void onStop() {
        super.onStop();


        SharedPreferences loginsp = getSharedPreferences("로그인확인", MODE_PRIVATE);
        SharedPreferences.Editor logineditor = loginsp.edit();

        logincb = (CheckBox) findViewById(R.id.autologin);
        if (logincb.isChecked()) {
            logineditor.putBoolean("login_ok", true);
            logineditor.apply();
            loginchecked = true;
        } else if (logincb.isChecked() == false) {

        }
        logincb.setChecked(false);
    }

    class CameraAnim extends Animation {
        float cx, cy;

        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            cx = width/2;         // 좌표를 뷰의 중앙으로 지정
            cy = height/2;
            setDuration(2000);

            setInterpolator(new LinearInterpolator());
        }
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            Camera cam = new Camera();
            /**
             *  360도 * 시간 - 시간이 0~1이므로 점진적으로 360도 회전
             */

            cam.rotateY(360 * interpolatedTime);

            Matrix matrix = t.getMatrix();
            cam.getMatrix(matrix);
            // 회전 중심을 이미지 중심으로 하기 위해 카메라를 회전하기 전 중심을
            matrix.preTranslate(-cx, -cy);    // 원점으로 옮기고
            matrix.postTranslate(cx, cy);    // 회전 후 다시 원래 위치
        }
    }

}


