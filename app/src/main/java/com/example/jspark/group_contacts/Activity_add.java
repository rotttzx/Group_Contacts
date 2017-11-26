package com.example.jspark.group_contacts;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

import static com.example.jspark.group_contacts.R.id.Group_Name;

/**
 * Created by jspark on 2017-08-23.
 */

public class Activity_add extends MainActivity {
    int REQ_CODE_SELECT_ALBUM = 0;
    int REQ_CODE_SELECT_CAMERA = 1;
    int REQ_CODE_SELECT_CROP = 2;
    int REQ_TELEPHONE = 3;
    ListView GrouplistView;
    ListView AddlistView;
    Activity_add_ListView_Adapter adapter;
    Activity_add_ListView_Item listView_item;
    SharedPreferences sp2;
    SharedPreferences.Editor editor2;
    Group_ListView_Adapter adapter2;
    int gn;
    ProgressDialog asyncDialog_Image;
    Thread InputThread;
    Handler InputHandler = new Handler();
    ProgressDialog asyncDialog;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        listView_item = new Activity_add_ListView_Item();
        // Adapter 생성
        adapter = new Activity_add_ListView_Adapter();
        adapter2 = new Group_ListView_Adapter();
        // 리스트뷰 참조 및 Adapter달기
        AddlistView = (ListView) findViewById(R.id.add_listView);
        GrouplistView = (ListView) findViewById(R.id.group_listView);
        AddlistView.setAdapter(adapter);

        final TextView textView = (TextView) findViewById(Group_Name);
        Intent intent = getIntent();
        String Group_Name1 = intent.getStringExtra("제목");


        gn = intent.getIntExtra("리스트번호", 0);
        SharedPreferences sp44 = getSharedPreferences("listnum", MODE_PRIVATE);
        SharedPreferences.Editor editor12 = sp44.edit();
        editor12.putInt("listnum", gn);
        editor12.apply();

        System.out.println("xxxxxxxxxxxxxxx" + gn);
        textView.setText(Group_Name1);


        sp2 = getSharedPreferences("SaveContact", MODE_PRIVATE);
        int t = sp2.getInt("ListNumber2" + (gn * 1000), 0);


        for (int i = 0; i <= t; i++) {
            String ti = sp2.getString("a" + i + (gn * 1000), null);
            String nu = sp2.getString("b" + i + (gn * 1000), null);
            adapter.addItem(ti, nu, ContextCompat.getDrawable(this, R.drawable.ic_phone),
                    ContextCompat.getDrawable(this, R.drawable.ic_sms), ContextCompat.getDrawable(this, R.drawable.icon_modify), ContextCompat.getDrawable(this, R.drawable.icon_delete));
            adapter.notifyDataSetChanged();


        }


        //이미지뷰를 동그란 테두리 주기 저장한 이미지 넣기
        final ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        imageView.setBackground(new ShapeDrawable(new OvalShape()));
        imageView.setClipToOutline(true);


        Bitmap bitmap = intent.getParcelableExtra("img2");
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);

        Drawable d = imageView.getDrawable();
        if (bitmap == null) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_photo));
        } else {
            imageView.setImageDrawable(drawable);
        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_ALBUM);
            }
        });

        //전화걸기

//        Button b1 = (Button) findViewById(R.id.btn_call);//전화버튼
//        Button b2 = (Button) findViewById(R.id.btn_mail);//문자버튼
        Button b3 = (Button) findViewById(R.id.View_Contact);//연락처 추가
//        Button b4 = (Button) findViewById(R.id.Add_Contact);//새로운 연락처 추가
        Button b5 = (Button) findViewById(R.id.Save_Contact);//그룹저장


//        //전화걸기
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TextView textView1 = (TextView) findViewById(R.id.Contact_Number);
//                String Phone_Number = textView1.getText().toString();
//                Intent intent = new Intent(Intent.ACTION_CALL);
//                intent.setData(Uri.parse("tel:" + Phone_Number));
//                try {
//                    startActivity(intent);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        //문자버튼
//        b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TextView textView = (TextView) findViewById(R.id.Contact_Number);
//                String Phone_Number = textView.getText().toString();
//                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//                sendIntent.putExtra("sms_body", ""); // 보낼 문자
//                sendIntent.putExtra("address", Phone_Number); // 받는사람 번호
//                sendIntent.setType("vnd.android-dir/mms-sms");
//                try {
//                    startActivity(sendIntent);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
        //연락처추가
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setData(Uri.parse("content://com.android.contacts/data/phones"));
                intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, REQ_TELEPHONE);

            }
        });
//        //새로운 연락처 등록
//        b4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        //저장버튼
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_add.this, Group_ListView.class);
                ImageView icon_imageView = (ImageView) findViewById(R.id.imageView2);
                Drawable d = icon_imageView.getDrawable();
                if (d != null) {
                    Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
                    intent.putExtra("img1", bitmap);
                }
                String title = textView.getText().toString();
                intent.putExtra("리스트번호", gn);
                System.out.println("==============================" + gn);
                intent.putExtra("제목1", title);
                setResult(RESULT_OK, intent);
                System.out.println("==============================" + intent);
                finish();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_SELECT_ALBUM) {
            if (resultCode == RESULT_OK) {
                try {

                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    Bitmap resize = Bitmap.createScaledBitmap(image_bitmap, 160, 160, true);
                    ImageView image = (ImageView) findViewById(R.id.imageView2);
                    image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    image.setImageBitmap(resize);


                    //이미지 추가시 로딩화면
                    LoadingTask_Image task_image = new LoadingTask_Image();
                    task_image.execute();
                    InputThread.interrupt();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == REQ_TELEPHONE) {
            if (resultCode == RESULT_OK) {
                Cursor cursor = getContentResolver().query(data.getData(),
                        new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                ContactsContract.CommonDataKinds.Phone.NUMBER},
                        null, null, null);

                cursor.moveToFirst();
                //이름획득
                String receiveName = cursor.getString(0);
                //전화번호 획득
                String receiveNumber = cursor.getString(1);

                if (receiveName.equals(listView_item.getTitle())) {
                    Toast.makeText(Activity_add.this, "이미 있습니다", Toast.LENGTH_SHORT).show();
                } else {
                    LoadingTask task = new LoadingTask();
                    task.execute();
                    adapter.addItem(receiveName, receiveNumber, ContextCompat.getDrawable(this, R.drawable.ic_phone),
                            ContextCompat.getDrawable(this, R.drawable.ic_sms), ContextCompat.getDrawable(this, R.drawable.icon_modify), ContextCompat.getDrawable(this, R.drawable.icon_delete));
                    adapter.notifyDataSetChanged();
                }
                cursor.close();


            }
        }
    }

    //    @Override
//    protected void onStart() {
//        super.onStart();
//        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
//    }
//
    @Override
    protected void onPause() {
        super.onPause();

        sp2 = getSharedPreferences("SaveContact", MODE_PRIVATE);
        editor2 = sp2.edit();
        try {
            for (int i = 0; i <= AddlistView.getCount(); i++) {
                Activity_add_ListView_Item item = (Activity_add_ListView_Item) AddlistView.getItemAtPosition(i);
                String a = item.getTitle();
                String b = item.getNumber();
                editor2.putString("a" + i + (gn * 1000), a);
                editor2.putString("b" + i + (gn * 1000), b);
                editor2.putInt("ListNumber2" + (gn * 1000), i);
                editor2.apply();
                SharedPreferences sp = getSharedPreferences("Group", MODE_PRIVATE);
            }
        } catch (Exception e) {
            if (AddlistView.getCount() == 0) {
                editor2.clear();
                editor2.apply();
            }

        }


    }

    //AsyncTask 로딩창 띄우기
    private class LoadingTask_Image extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            asyncDialog_Image = new ProgressDialog(Activity_add.this);
            asyncDialog_Image.setTitle("이미지");
            asyncDialog_Image.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog_Image.setMessage("이미지 추가중입니다....");
            asyncDialog_Image.setCanceledOnTouchOutside(false);
            asyncDialog_Image.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                for (int i = 0; i < 5; i++) {
                    //asyncDialog.setProgress(i * 30);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            asyncDialog_Image.dismiss();
            super.onPostExecute(aVoid);


        }
    }

    private class LoadingTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            asyncDialog = new ProgressDialog(Activity_add.this);
            asyncDialog.setTitle("연락처");
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("연락처 추가중입니다....");
            asyncDialog.setCanceledOnTouchOutside(false);
            asyncDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            asyncDialog.dismiss();
            super.onPostExecute(aVoid);


        }


    }


}







