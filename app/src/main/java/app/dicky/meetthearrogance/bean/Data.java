package app.dicky.meetthearrogance.bean;

import android.graphics.drawable.Drawable;

/**
 * copyright© www.pemt.com.cn
 * Created by eng005 on 2016/7/14
 */
public class Data {
    String name;
    boolean nameIsTrue;
    public String appName;
    public boolean isVirus;
    public String packagename;
    public String description;
    public Drawable appicon;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public boolean isVirus() {
        return isVirus;
    }

    public void setVirus(boolean virus) {
        isVirus = virus;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Drawable getAppicon() {
        return appicon;
    }

    public void setAppicon(Drawable appicon) {
        this.appicon = appicon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNameIsTrue() {
        return nameIsTrue;
    }

    public void setNameIsTrue(boolean nameIsTrue) {
        this.nameIsTrue = nameIsTrue;
    }
}
