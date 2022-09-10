package com.bo.a1_counter.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0007J\b\u0010\t\u001a\u00020\bH\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\n"}, d2 = {"Lcom/bo/a1_counter/activity/MyObserver;", "Landroidx/lifecycle/LifecycleObserver;", "lifecycle", "Landroidx/lifecycle/Lifecycle;", "(Landroidx/lifecycle/Lifecycle;)V", "getLifecycle", "()Landroidx/lifecycle/Lifecycle;", "activityStart", "", "activityStop", "app_debug"})
public final class MyObserver implements androidx.lifecycle.LifecycleObserver {
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.Lifecycle lifecycle = null;
    
    public MyObserver(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.Lifecycle lifecycle) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.Lifecycle getLifecycle() {
        return null;
    }
    
    @androidx.lifecycle.OnLifecycleEvent(value = androidx.lifecycle.Lifecycle.Event.ON_START)
    public final void activityStart() {
    }
    
    @androidx.lifecycle.OnLifecycleEvent(value = androidx.lifecycle.Lifecycle.Event.ON_STOP)
    public final void activityStop() {
    }
}