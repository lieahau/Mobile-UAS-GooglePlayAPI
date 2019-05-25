package id.ac.umn.budimanputrajaya;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class NotificationHelper {
    private NotificationManager notificationManager;
    private String NOTIFICATION_CHANNEL;
    private NotificationCompat.Builder notificationBuilder;

    public NotificationHelper(Context context){
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NOTIFICATION_CHANNEL = context.getString(R.string.channel_name);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL, NOTIFICATION_CHANNEL, NotificationManager.IMPORTANCE_MAX);
            // Configure the notification channel.
            notificationChannel.setDescription("Budiman Putra Jaya - 00000012802");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL);
    }

    public void createNotification(final String appName, final String imgUrl, final int id){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                sendNotification(appName, imgUrl, id);
            }
        }, 5000);
    }

    private void sendNotification(final String appName, final String imgUrl, final int id){
        Picasso.get().load(imgUrl).networkPolicy(NetworkPolicy.OFFLINE).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                notificationBuilder
                        .setContentTitle("You just registered app!")
                        .setContentText(appName+" has been registered successfully!")
                        .setContentInfo("Information")
                        .setSmallIcon(android.R.drawable.star_on)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                        .setWhen(System.currentTimeMillis())
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setAutoCancel(true);
                notificationManager.notify(id, notificationBuilder.build());
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                notificationBuilder
                        .setContentTitle("You just registered app!")
                        .setContentText(appName+" has been registered successfully!")
                        .setContentInfo("Information")
                        .setSmallIcon(android.R.drawable.star_on)
                        .setWhen(System.currentTimeMillis())
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setAutoCancel(true);
                notificationManager.notify(id, notificationBuilder.build());
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }
}
