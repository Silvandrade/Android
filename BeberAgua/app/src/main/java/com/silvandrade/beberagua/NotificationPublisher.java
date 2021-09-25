package com.silvandrade.beberagua;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class NotificationPublisher extends BroadcastReceiver {

    public static final String KEY_MESSAGE = "key_message";
    public static final String KEY_MESSAGE_ID = "key_message_id";

    /*
        Essa classe é responsável por escutar a mensagem da PendingIntent (broadcast).
        Coleta a informação de quem enviou a mensagem MainActivity (context) e a Intent (message)
    */

    @Override
    public void onReceive(Context context, Intent message) {

        // Pegando os dados da intent (message) passada pela MainActivity.
        String text = message.getStringExtra(KEY_MESSAGE);
        int id = message.getIntExtra(KEY_MESSAGE_ID, 0);

        // Ao criando uma PendingIntent que será disparada ao clicar na notificação abrir o aplicativo.
        Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = createGetActivity(context, intent);
        createNotification(context, id, text, pendingIntent);
    }

    private PendingIntent createGetActivity(Context context, Intent intent) {
        return PendingIntent.getActivity(context, 0, intent, 0);
    }

    private void createNotification(Context context, int id, String text, PendingIntent pendingIntent) {
        NotificationManager notificationManager;
        Notification notification;

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE); // Criando o gerenciador da notificação.
        notification = getNotification(context, text, notificationManager, pendingIntent); // Criando a notificação.
        notificationManager.notify(id, notification); // Disparando a notificação.
    }

    private Notification getNotification(Context context, String content, NotificationManager notificationManager, PendingIntent intent) {
        Notification.Builder builder =
                new Notification.Builder(context.getApplicationContext())
                .setContentText(content)
                .setContentIntent(intent)
                .setTicker("Alerta")
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher);

        // Para o android maior que 8 é necessário criar um canal de comunicação.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "YOUR_CHANNEL_ID";
            final NotificationChannel channel = new NotificationChannel(channelId, "Channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }
        return  builder.build();
    }
}
