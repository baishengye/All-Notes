package com.bo.a1_counter.viewModel;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u0012\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0014J\u0006\u0010\u001b\u001a\u00020\u0019J\u0006\u0010\u001c\u001a\u00020\u0019R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b8F\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001f\u0010\r\u001a\u0010\u0012\f\u0012\n \u000f*\u0004\u0018\u00010\u000e0\u000e0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\nR\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000e0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\nR\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00140\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\n\u00a8\u0006\u001d"}, d2 = {"Lcom/bo/a1_counter/viewModel/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "countReserved", "", "(I)V", "_counter", "Landroidx/lifecycle/MutableLiveData;", "counter", "Landroidx/lifecycle/LiveData;", "getCounter", "()Landroidx/lifecycle/LiveData;", "refreshLiveData", "", "refreshResult", "Lcom/bo/a1_counter/activity/User;", "kotlin.jvm.PlatformType", "getRefreshResult", "user", "getUser", "userIdLiveData", "", "userLiveData", "userName", "getUserName", "clear", "", "userId", "plusOne", "refresh", "app_debug"})
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    private final androidx.lifecycle.MutableLiveData<java.lang.Object> refreshLiveData = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.bo.a1_counter.activity.User> refreshResult = null;
    private final androidx.lifecycle.MutableLiveData<com.bo.a1_counter.activity.User> userLiveData = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.String> userName = null;
    private final androidx.lifecycle.MutableLiveData<java.lang.String> userIdLiveData = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.bo.a1_counter.activity.User> user = null;
    private final androidx.lifecycle.MutableLiveData<java.lang.Integer> _counter = null;
    
    public MainViewModel(int countReserved) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.bo.a1_counter.activity.User> getRefreshResult() {
        return null;
    }
    
    public final void refresh() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.String> getUserName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.bo.a1_counter.activity.User> getUser() {
        return null;
    }
    
    public final void getUser(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Integer> getCounter() {
        return null;
    }
    
    public final void plusOne() {
    }
    
    public final void clear() {
    }
}