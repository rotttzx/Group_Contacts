package com.example.jspark.group_contacts;

import android.graphics.drawable.Drawable;

/**
 * Created by jspark on 2017-08-28.
 */

public class Activity_add_ListView_Item {
    private String contactTitle;
    private String contactNumber;
    private Drawable phoneDrawable;
    private Drawable smsDrawable;
    private Drawable modifyDrawable;
    private Drawable deleteDrawable;


    public String getTitle() {
        return this.contactTitle;
    }
    public String getNumber() {
        return this.contactNumber;
    }
    public Drawable getPhone() {
        return this.phoneDrawable;
    }
    public Drawable getSms() {
        return this.smsDrawable;
    }

    public Drawable getModify() {
        return this.modifyDrawable;
    }

    public Drawable getDelete() {
        return this.deleteDrawable;
    }



    public void setTitle(String title) {
        contactTitle = title;
    }
    public void setNumber(String number) {
        contactNumber = number;
    }
    public void setPhone(Drawable Phone_icon) {
        phoneDrawable = Phone_icon;
    }
    public void setSms(Drawable Sms_icon) {
        smsDrawable = Sms_icon;
    }

    public void setModify(Drawable Modify_icon) {
        modifyDrawable = Modify_icon;
    }

    public void setDelete(Drawable Delete_icon) {
        deleteDrawable = Delete_icon;
    }






}
