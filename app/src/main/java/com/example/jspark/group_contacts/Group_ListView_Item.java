package com.example.jspark.group_contacts;

import android.graphics.drawable.Drawable;

/**
 * Created by jspark on 2017-08-29.
 */

public class Group_ListView_Item {
    private Drawable iconDrawable;
    private String titleStr;
    private Drawable modifyDrawable;
    private Drawable deleteDrawable;

    public Drawable getIcon() {
        return this.iconDrawable;
    }

    public String getTitle() {
        return this.titleStr;
    }

    public Drawable getModify() {
        return this.modifyDrawable;
    }

    public Drawable getDelete() {
        return this.deleteDrawable;
    }


    public void setIcon(Drawable icon) {
        iconDrawable = icon;
    }

    public void setTitle(String title) {
        titleStr = title;
    }

    public void setModify(Drawable Modify_icon) {
        modifyDrawable = Modify_icon;
    }

    public void setDelete(Drawable Delete_icon) {
        deleteDrawable = Delete_icon;
    }
}
