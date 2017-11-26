package com.example.jspark.group_contacts;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;

import java.util.ArrayList;

/**
 * Created by jspark on 2017-08-28.
 */

public class Activity_add_ListView_Adapter extends BaseAdapter {
     ArrayList<Activity_add_ListView_Item> listViewItemList = new ArrayList<>();
    private int lastPosition=-1;

    public Activity_add_ListView_Adapter(){

    }


    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();


        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_add_listview_item, parent, false);
        }


        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView titleTextView = (TextView) convertView.findViewById(R.id.contact_Title);
        final TextView numberTextView = (TextView)convertView.findViewById(R.id.contact_Number);
        final ImageView iconImageView1 = (ImageView) convertView.findViewById(R.id.phone);
        final ImageView iconImageView2 = (ImageView) convertView.findViewById(R.id.sms);
        ImageView iconImageView3 = (ImageView) convertView.findViewById(R.id.modify);
        ImageView iconImageView4 = (ImageView) convertView.findViewById(R.id.delete);

        Animation g_l_r_a = AnimationUtils.loadAnimation(context,R.anim.grouplistviewitem_anim);
        Animation g_l_l_a = AnimationUtils.loadAnimation(context,R.anim.grouplistviewitem_right);
        Animation up_from_bottom = AnimationUtils.loadAnimation(context,R.anim.up_from_bottom);
        Animation down_from_top = AnimationUtils.loadAnimation(context,R.anim.down_from_top);
        Animation animation = AnimationUtils.loadAnimation(context,
                (pos > this.lastPosition) ?
                        R.anim.up_from_bottom : R.anim.down_from_top);
        convertView.setAnimation(animation);
        this.lastPosition = pos;




        //버튼클릭시
        //전화버튼
        iconImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Phone_Number = numberTextView.getText().toString();
                final Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + Phone_Number));
                Animation animation_rotate = AnimationUtils.loadAnimation(context,R.anim.rotate_rot);
                iconImageView1.startAnimation(animation_rotate);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            iconImageView1.getContext().startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 1000);


            }
        });
        //문자버튼
        iconImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Phone_Number = numberTextView.getText().toString();
                final Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.putExtra("sms_body", ""); // 보낼 문자
                sendIntent.putExtra("address", Phone_Number); // 받는사람 번호
                sendIntent.setType("vnd.android-dir/mms-sms");
                Animation animation_rotate = AnimationUtils.loadAnimation(context,R.anim.rotate_rot);
                iconImageView2.startAnimation(animation_rotate);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            iconImageView2.getContext().startActivity(sendIntent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 1000);


            }
        });
        //수정

        //삭제
        iconImageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewItemList.remove(pos);
                notifyDataSetChanged();
            }
        });

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        Activity_add_ListView_Item listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영

        titleTextView.setText(listViewItem.getTitle());
        numberTextView.setText(listViewItem.getNumber());
        iconImageView1.setImageDrawable(listViewItem.getPhone());
        iconImageView2.setImageDrawable(listViewItem.getSms());
        iconImageView3.setImageDrawable(listViewItem.getModify());
        iconImageView4.setImageDrawable(listViewItem.getDelete());

        return convertView;

    }

    public void addItem(String title, String number,Drawable phone_icon,Drawable sms_icon,Drawable modify_icon, Drawable delete_icon) {

        Activity_add_ListView_Item item = new Activity_add_ListView_Item();
        item.setTitle(title);
        item.setNumber(number);
        item.setPhone(phone_icon);
        item.setSms(sms_icon);
        item.setModify(modify_icon);
        item.setDelete(delete_icon);
        listViewItemList.add(item);
    }

    public void removeItem(int i) {
        listViewItemList.remove(i);
    }
//    public void modifyItem(Drawable icon, String title, Drawable modify_icon, Drawable delete_icon, int n) {
//        Group_ListView_Item item = new Group_ListView_Item();
//        item.setIcon(icon);
//        item.setTitle(title);
//        item.setModify(modify_icon);
//        item.setDelete(delete_icon);
//        group_listView_items.set(n, item);
//    }
}
