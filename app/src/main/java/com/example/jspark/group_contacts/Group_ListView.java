package com.example.jspark.group_contacts;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;


/**
 * Created by jspark on 2017-08-29.
 */

public class Group_ListView extends MainActivity {
    ListView listView;
    Group_ListView_Adapter adapter;
    int REQ_CODE_SELECT_MODIFY = 0;
    int REQ_CODE_iMAGE_MODIFY = 1;
    ProgressDialog asyncDialog;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    int cc = 0;
    Handler backhandler = new Handler();
    BackPressCloseHandler backPressCloseHandler;
    private FloatingActionButton fab_menu, fab_listadd, fab_logout;
    Animation FabOpen, FabClose, FabRClockwise, FabRanticlockwise;
    boolean isOpen = false;

    Thread ImageThread;
    Handler mhandler = new Handler();
    int aa =0;
    ImageView adview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grouplist_fab);






        backPressCloseHandler = new BackPressCloseHandler(this);


        Intent intent = getIntent();
        String A = intent.getStringExtra("그룹명");

        final int[] images = {R.drawable.ad_google, R.drawable.ad_naver3, R.drawable.ad_daum};
        adview = (ImageView) findViewById(R.id.AdView);
        adview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aa%3==0){
                    Intent intent_google = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.co.kr"));
                    startActivity(intent_google);
                }else if(aa%3==1){
                    Intent intent_naver = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"));
                    startActivity(intent_naver);
                }else if(aa%3==2){
                    Intent intent_daum = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.daum.net"));
                    startActivity(intent_daum);
                }

            }
        });
        //광고 이미지 스레드
        ImageThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    aa++;
                    mhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (aa % 3 == 0) {
                                adview.setImageResource(images[0]);
                            } else if (aa % 3 == 1) {
                                adview.setImageResource(images[1]);
                            } else if (aa % 3 == 2) {
                                adview.setImageResource(images[2]);
                            }


                        }
                    });
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        ImageThread.start();

//        color_listview = (LinearLayout) findViewById(R.id.btn_layout);
//        final String[] color = {"#B0C4DE", "#ADD8E6", "#87CEFA"};

//        //배경화면 색상변경 스레드
//        BackgroundThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    cc++;
//                    backhandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (cc % 3 == 0) {
//                                color_listview.setBackgroundColor(Color.parseColor(color[0]));
//                            } else if (cc % 3 == 1) {
//                                color_listview.setBackgroundColor(Color.parseColor(color[1]));
//                            } else if (cc % 3 == 2) {
//                                color_listview.setBackgroundColor(Color.parseColor(color[2]));
//                            }
//
//                        }
//                    });
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        BackgroundThread.start();


        // Adapter 생성
        adapter = new Group_ListView_Adapter();
        // 리스트뷰 참조 및 Adapter달기
        listView = (ListView) findViewById(R.id.group_listView);
        listView.setAdapter(adapter);


        //fab버튼 에 관한것
        fab_menu = (FloatingActionButton) findViewById(R.id.fab_menu);
        fab_listadd = (FloatingActionButton) findViewById(R.id.fab_listadd);
        fab_logout = (FloatingActionButton) findViewById(R.id.fab_logout);

        fab_listadd.setVisibility(View.INVISIBLE);
        fab_logout.setVisibility(View.INVISIBLE);

        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        FabRClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        FabRanticlockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        fab_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    fab_menu.startAnimation(FabRanticlockwise);
                    fab_listadd.startAnimation(FabClose);
                    fab_logout.startAnimation(FabClose);
                    fab_listadd.setClickable(true);
                    fab_logout.setClickable(true);
                    isOpen = false;

                } else {
                    fab_menu.startAnimation(FabRClockwise);
                    fab_listadd.startAnimation(FabOpen);
                    fab_logout.startAnimation(FabOpen);
                    fab_listadd.setClickable(true);
                    fab_logout.setClickable(true);
                    isOpen = true;
                }


            }
        });
//        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this,R.anim.anim_layout_controller);
//        listView.setLayoutAnimation(controller);
//        adapter.addItem(ContextCompat.getDrawable(Group_ListView.this, R.drawable.ic_photo), A, ContextCompat.getDrawable(Group_ListView.this, R.drawable.icon_modify), ContextCompat.getDrawable(Group_ListView.this, R.drawable.icon_delete));


        sp = getSharedPreferences("Group", MODE_PRIVATE);
        int listNumber = sp.getInt("listnumberl", 0);

        System.out.println("zzzzzzzzzzz" + sp.getInt("listNumberl",0));
        for (int i = 0; i <= listNumber; i++) {
            String z = sp.getString("" + i, "");
            String image =  sp.getString("imagestrings"+i, "");
            Bitmap bitmap = StringToBitMap(image);
            Drawable drawable = new BitmapDrawable(bitmap);
            adapter.addItem(drawable, z, ContextCompat.getDrawable(Group_ListView.this, R.drawable.icon_modify), ContextCompat.getDrawable(Group_ListView.this, R.drawable.icon_delete));
            adapter.notifyDataSetChanged();
        }



        fab_listadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder Group_Add_Dialog = new AlertDialog.Builder(Group_ListView.this);
                Group_Add_Dialog.setTitle("새그룹");
                Group_Add_Dialog.setMessage("그룹명을 입력해주세요");
                final EditText editText12 = new EditText(Group_ListView.this);
                Group_Add_Dialog.setView(editText12);

                Group_Add_Dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String Group_Name = editText12.getText().toString();
                        if (Group_Name.equals("")) {
                            Toast.makeText(Group_ListView.this, "그룹명을 입력해주세요", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            LoadingTask task = new LoadingTask();
                            task.execute();
                            adapter.addItem(null, Group_Name, ContextCompat.getDrawable(Group_ListView.this, R.drawable.icon_modify), ContextCompat.getDrawable(Group_ListView.this, R.drawable.icon_delete));
                            dialog.dismiss();
                        }

                    }
                });
                Group_Add_Dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Group_ListView.this, "취소 버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = Group_Add_Dialog.create();
                dialog.show();

            }
        });


        fab_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences loginsp = getSharedPreferences("로그인확인", MODE_PRIVATE);
                SharedPreferences.Editor logineditor = loginsp.edit();
                logineditor.clear();
                logineditor.apply();

                AlertDialog.Builder Group_Add_Dialog = new AlertDialog.Builder(Group_ListView.this);
                Group_Add_Dialog.setTitle("로그아웃");
                Group_Add_Dialog.setMessage("로그아웃하시겠습니까?");
                final TextView textView = new TextView(Group_ListView.this);
                Group_Add_Dialog.setView(textView);

                Group_Add_Dialog.setPositiveButton("확인",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Group_ListView.this, LogIn_Activity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                Group_Add_Dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Group_ListView.this, "취소 버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = Group_Add_Dialog.create();
                dialog.show();


            }
        });

        //리스트뷰 선택시
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Group_ListView.this, Activity_add.class);
                Group_ListView_Item item = (Group_ListView_Item) listView.getItemAtPosition(position);
                int aoaoa = (int) adapter.getItemId(position);
                String aoa = item.getTitle();
                Drawable d = item.getIcon();
                if (d != null) {
                    Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
                    intent.putExtra("img2", bitmap);
                }
                intent.putExtra("제목", aoa);
                intent.putExtra("리스트번호", aoaoa);
                System.out.println("==============================" + aoaoa);
                startActivityForResult(intent,REQ_CODE_iMAGE_MODIFY);
                overridePendingTransition(R.anim.anim_activity_rightin,R.anim.anim_activity_notmove);
                adapter.notifyDataSetChanged();

            }
        });


//       // 이미지 아이콘 클릭후 가져오기
//
//        ImageView imageView = (ImageView)adapter.getView() findViewById(R.id.group_ImageIcon);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//                startActivityForResult(intent,REQ_CODE_iMAGE_MODIFY );
//
//
//            }
//        });


    }

    @Override
    protected void onPause() {
        super.onPause();

        Toast.makeText(getApplicationContext(), "onpause", Toast.LENGTH_SHORT).show();


        sp = getSharedPreferences("Group", MODE_PRIVATE);
        editor = sp.edit();
        try {
            for (int i = 0; i <= listView.getCount(); i++) {
                Group_ListView_Item item = (Group_ListView_Item) listView.getItemAtPosition(i);
                Drawable icon = item.getIcon();
                Bitmap b = getBitmapFromDrawable((BitmapDrawable) icon);
                String a = item.getTitle();
                String image = BitMapToString(b);
                editor.putString("imagestrings"+i, image);
                editor.putString("" + i, a);
                editor.putInt("listnumberl", i);
                editor.apply();

                System.out.println("zzzzzzzzzzzz" + sp.getInt("listnumberl",0));
                System.out.println("zzzzzzzzzzzz" + i+1);

            }
        } catch (Exception e) {
            if (listView.getCount() == 0) {

            }

        }
        fab_listadd.clearAnimation();
        fab_logout.clearAnimation();
        fab_menu.clearAnimation();

    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();

    }

    @Override
    protected void onRestart() {
        isOpen = false;
        super.onRestart();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_CODE_iMAGE_MODIFY) {


                SharedPreferences sp44 = getSharedPreferences("listnum",MODE_PRIVATE);
                SharedPreferences.Editor editor12 = sp44.edit();
                int a = sp44.getInt("listnum",0);

                    Bitmap bitmap = data.getParcelableExtra("img1");
                    Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                    String title = data.getStringExtra("제목1");
                    System.out.println("==============================" + bitmap);
                    adapter.modifyItem(drawable,title,ContextCompat.getDrawable(Group_ListView.this, R.drawable.icon_modify),ContextCompat.getDrawable(Group_ListView.this, R.drawable.icon_delete),a);
                    adapter.notifyDataSetChanged();



            }

        }


    }

    private class LoadingTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            asyncDialog = new ProgressDialog(Group_ListView.this);
            asyncDialog.setTitle("그룹");
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("그룹 추가중입니다....");
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

    //백버튼
    public class BackPressCloseHandler {
        private long backKeyPressedTime = 0;
        private Toast toast;
        private Activity activity;

        public BackPressCloseHandler(Activity context) {
            this.activity = context;
        }

        public void onBackPressed() {
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis();
                showGuide();
                return;
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                activity.finish();
                finish();
                toast.cancel();
            }
        }

        public void showGuide() {
            toast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    public Bitmap getBitmapFromDrawable(BitmapDrawable drawable){
        Bitmap b = drawable.getBitmap();
        return b;
    }





}
