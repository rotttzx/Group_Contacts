package com.example.jspark.group_contacts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder Group_Add_Dialog = new AlertDialog.Builder(MainActivity.this);
                Group_Add_Dialog.setTitle("새그룹");
                Group_Add_Dialog.setMessage("그룹명을 입력해주세요");
                final EditText editText12 = new EditText(MainActivity.this);
                Group_Add_Dialog.setView(editText12);

                Group_Add_Dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String Group_Name = editText12.getText().toString();
                        if (Group_Name.equals("")) {
                            Toast.makeText(MainActivity.this, "그룹명을 입력해주세요", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Intent intent = new Intent(MainActivity.this, Group_ListView.class);
                            intent.putExtra("그룹명", Group_Name);
                            startActivity(intent);
                            dialog.dismiss();
                        }

                    }
                });
                Group_Add_Dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "취소 버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = Group_Add_Dialog.create();
                dialog.show();


            }
        });


    }
}
