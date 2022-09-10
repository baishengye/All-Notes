package com.xiaoyingbo.file;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private int age;
    private String name;

    public User(int age, String name) {
        this.age=age;
        this.name=name;
    }

    protected User(Parcel in) {
        age = in.readInt();
        name = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(age);
        dest.writeString(name);
    }
}
