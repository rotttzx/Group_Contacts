package com.example.jspark.group_contacts;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by jspark on 2017-08-29.
 */

public class Group_ListView_Adapter extends BaseAdapter {
    private ArrayList<Group_ListView_Item> group_listView_items = new ArrayList<>();

    public Group_ListView_Adapter(){

    }

    @Override
    public int getCount() {
        return group_listView_items.size();
    }

    @Override
    public Object getItem(int position) {
        return group_listView_items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();


        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_listview_item, parent, false);
        }


        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
         final ImageView iconImageView = (ImageView) convertView.findViewById(R.id.group_ImageIcon);
        final TextView titleTextView = (TextView) convertView.findViewById(R.id.Group_Title);
        final ImageView iconImageView1 = (ImageView) convertView.findViewById(R.id.Group_Title_Modify);
        final ImageView iconImageView2 = (ImageView) convertView.findViewById(R.id.Group_Title_delete);

        //


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        Group_ListView_Item listViewItem = group_listView_items.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());
        iconImageView1.setImageDrawable(listViewItem.getModify());
        iconImageView2.setImageDrawable(listViewItem.getDelete());


        Animation g_l_r_a = AnimationUtils.loadAnimation(context,R.anim.grouplistviewitem_anim);
        Animation g_l_l_a = AnimationUtils.loadAnimation(context,R.anim.grouplistviewitem_right);



//        iconImageView.startAnimation(g_l_r_a);
//        titleTextView.startAnimation(g_l_r_a);
//        iconImageView1.startAnimation(g_l_l_a);
//        iconImageView2.startAnimation(g_l_l_a);










//        iconImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//                iconImageView.getContext().startActivity(intent);
//            }
//        });

        iconImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aa = titleTextView.getText().toString();
                AlertDialog.Builder Group_Add_Dialog = new AlertDialog.Builder(context);
                Group_Add_Dialog.setTitle("그룹명");
                Group_Add_Dialog.setMessage("새로운 그룹명을 입력해주세요");
                final EditText editText12 = new EditText(context);
                ImageView imageView = new ImageView(context);
                editText12.setText(aa);
                Group_Add_Dialog.setView(editText12);

                Group_Add_Dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String Group_Name = editText12.getText().toString();
                        if (Group_Name.equals("")) {
                            Toast.makeText(context, "그룹명을 입력해주세요", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            modifyItem(null, Group_Name, ContextCompat.getDrawable(context, R.drawable.icon_modify), ContextCompat.getDrawable(context, R.drawable.icon_delete),pos);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }

                    }
                });
                Group_Add_Dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "취소 버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = Group_Add_Dialog.create();
                dialog.show();

            }
        });
        //삭제아이콘
        final View finalConvertView = convertView;
        iconImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder ald = new AlertDialog.Builder(context);
                ald.setTitle("삭제");
                ald.setMessage("그룹을 삭제하시겠습니까?");
                ald.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Animation animation = AnimationUtils.loadAnimation(context,android.R.anim.slide_out_right);
                        animation.setDuration(1200);
//
                        finalConvertView.startAnimation(animation);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                group_listView_items.remove(pos);
                                notifyDataSetChanged();
                            }
                        }, 1000);



                    }
                });
                ald.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "'취소'버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = ald.create();
                dialog.show();



            }
        });




        return convertView;
    }


    public void addItem(Drawable icon, String title, Drawable modify_icon, Drawable delete_icon) {
        Group_ListView_Item item = new Group_ListView_Item();
        item.setIcon(icon);
        item.setTitle(title);
        item.setModify(modify_icon);
        item.setDelete(delete_icon);
        group_listView_items.add(item);
    }

    public void removeItem(int i) {
        group_listView_items.remove(i);
    }
    public void modifyItem(Drawable icon, String title, Drawable modify_icon, Drawable delete_icon, int n) {
        Group_ListView_Item item = new Group_ListView_Item();
        item.setIcon(icon);
        item.setTitle(title);
        item.setModify(modify_icon);
        item.setDelete(delete_icon);
        group_listView_items.set(n, item);
    }

    public void addImage(Drawable icon,int n){
        Group_ListView_Item item = new Group_ListView_Item();




    }


}
