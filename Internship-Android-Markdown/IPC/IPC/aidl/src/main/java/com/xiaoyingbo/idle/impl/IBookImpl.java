package com.xiaoyingbo.idle.impl;

import android.app.Application;
import android.os.Build;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.xiaoyingbo.idle.Book;
import com.xiaoyingbo.idle.IBookInterface;

public class IBookImpl extends IBookInterface.Stub {
    Book book = null;

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void setBook(Book book) throws RemoteException {
        this.book = book;
        Log.d("aidl-demo", "AIDLService setBook:" + book.toString() +" "+ Application.getProcessName());
    }

    @Override
    public Book getBook() throws RemoteException {
        return book;
    }

}
