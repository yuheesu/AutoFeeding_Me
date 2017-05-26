package com.example.heejung.autofeeding;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by uhees on 2017-05-15.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private int YOURAPP_NOTIFICATION_ID;
    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, R.string.app_name, Toast.LENGTH_SHORT).show();
        showNotification(context, R.drawable.img, "알람", "먹이줄시간입니다.");
//        Intent mServiceintent = new Intent(context, AlarmButtonService.class);
//        context.startService(mServiceintent);
    }

    private void showNotification(Context context, int statusBarIconID, String statusBarTextID, String detailedTextID) {
        Intent contentintent = new Intent(context, MainActivity.class);


        PendingIntent theappIntent = PendingIntent.getActivity(context, 0, contentintent, PendingIntent.FLAG_UPDATE_CURRENT);

        CharSequence from = "개밥주시개";
        CharSequence message = "먹이줄시간입니다~~";
        Notification mnotif;
        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notif = new Notification(statusBarIconID, null, System.currentTimeMillis());

        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentIntent(theappIntent);
        builder.setSmallIcon(statusBarIconID);
        builder.setContentTitle(from);
        builder.setContentText(message);
        builder.setAutoCancel(true);
        builder.setTicker("알림~");
        builder.setWhen(System.currentTimeMillis());
        builder.build();
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
//        Notification notification = builder.build();
//        notif.setLatestEvenInfo(context, from, message, theappIntent);
        //  notif.ledARGB = Color.GREEN;
        mnotif = builder.getNotification();
        nm.notify(1234, mnotif);

    }
}


//class AlarmSoundService extends Service{
//
//    public AlarmSoundService(){
//
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId){
//        Toast.makeText(this, "알람이 울립니다.", Toast.LENGTH_SHORT).show();
//        return START_NOT_STICKY;
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//}
