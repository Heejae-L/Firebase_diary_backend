package android.org.firebasetest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String channel1ID = "channel1";
    public static final String channel1Name = "Channel 1";
    public static final String channel2ID = "channel2";
    public static final String channel2Name = "Channel 2";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        createChannels();
    }

    public void createChannels() {
        NotificationChannel channel1 = null;
        NotificationChannel channel2 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel1 = new NotificationChannel(
                    channel1ID,
                    channel1Name,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel1.enableLights(true);
            channel1.enableVibration(true);
            channel1.setLightColor(Color.BLUE);  // Use actual color, not resource ID
            channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(channel1);

            channel2 = new NotificationChannel(
                    channel2ID,
                    channel2Name,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel2.enableLights(true);
            channel2.enableVibration(true);
            channel2.setLightColor(Color.BLUE);  // Use actual color, not resource ID
            channel2.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(channel2);
        }

    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannel1Notification(String title, String message){
        // Create an Intent for the activity you want to start
        Intent resultIntent = new Intent(this, ViewDiariesActivity.class);
        // Create the PendingIntent
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                this,
                0, // Request code, can be any integer
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT // Use appropriate flag to update current or create new one
        );
        // Build the notification
        return new NotificationCompat.Builder(getApplicationContext(), channel1ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.vector_logo)
                .setContentIntent(resultPendingIntent) // Set the Intent to be launched when the notification is clicked
                .setAutoCancel(true); // Notification will disappear after clicking on it
    }


    public NotificationCompat.Builder getChannel2Notification(String title, String message){
        return new NotificationCompat.Builder(getApplicationContext(), channel2ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notifications);
    }

    public NotificationCompat.Builder getChannelNotification(){
        return new NotificationCompat.Builder(getApplicationContext(), channel1ID)
                .setSmallIcon(R.drawable.ic_home);
    }
}
