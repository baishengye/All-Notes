package com.bo.app3_notification;

import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.bo.app3_notification.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    ActivityMainBinding activityMainBinding;
    private Button bt;
    private TextView tv_normal;
    private TextView tv_fold;
    private TextView tv_hang;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioGroup radioGroup;
    private NotificationManager notificationManager;
    private NotificationChannel normalChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            normalChannel = new NotificationChannel("normal", "normal", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(normalChannel);

            NotificationChannel importantChannel = new NotificationChannel("important", "important", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(importantChannel);
        }
    }

    @Override
    protected void initViews() {
        super.initViews();

        tv_normal = activityMainBinding.tvNormal;
        tv_fold = activityMainBinding.tvFold;
        tv_hang = activityMainBinding.tvHang;
        radioButton1 = activityMainBinding.rbPublic;
        radioButton2 = activityMainBinding.rbPrivate;
        radioButton2 = activityMainBinding.rbSecret;
        radioGroup = activityMainBinding.rgAll;

    }

    @Override
    protected void initListeners() {
        super.initListeners();

        tv_normal.setOnClickListener(this);
        tv_fold.setOnClickListener(this);
        tv_hang.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    private void selectNotificationLevel(NotificationCompat.Builder builder) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_private:
                builder.setVisibility(NotificationCompat.VISIBILITY_PRIVATE);
                builder.setContentText("private");
                break;
            case R.id.rb_secret:
                builder.setVisibility(NotificationCompat.VISIBILITY_SECRET);
                builder.setContentText("secret");
                break;
            default:
                builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
                builder.setContentText("public");
                break;

        }
    }


    private void sendNormalNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"normal");
        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://bilibili.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.lanucher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.lanucher));
        builder.setAutoCancel(true);
        builder.setContentTitle("普通通知");
        selectNotificationLevel(builder);
        notificationManager.notify(0, builder.build());

    }

    private void sendFoldNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"normal");
        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://bilibili.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.foldleft);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.lanucher));
        builder.setAutoCancel(true);
        builder.setContentTitle("折叠式通知");
        builder.setStyle(new NotificationCompat.DecoratedCustomViewStyle());
        selectNotificationLevel(builder);
        //用RemoteViews来创建自定义Notification视图
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.view_fold);
        Notification notification = builder.build();
        //指定展开时的视图
        notification.bigContentView = remoteViews;
        notificationManager.notify(1, notification);
    }

    private void sendHangNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"important");
        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://bilibili.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.foldleft);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.lanucher));
        builder.setAutoCancel(true);
        builder.setContentTitle("悬挂式通知");
        selectNotificationLevel(builder);
        //设置点击跳转
        Intent hangIntent = new Intent();
        hangIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        hangIntent.setClass(this, MainActivity.class);
        //如果描述的PendingIntent已经存在，则在产生新的Intent之前会先取消掉当前的
        PendingIntent hangPendingIntent = PendingIntent.getActivity(this, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setFullScreenIntent(hangPendingIntent, true);

        notificationManager.notify(2, builder.build());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_normal:
                sendNormalNotification();
                break;
            case R.id.tv_fold:
                sendFoldNotification();
                break;

            case R.id.tv_hang:
                sendHangNotification();
                break;
        }
    }
}