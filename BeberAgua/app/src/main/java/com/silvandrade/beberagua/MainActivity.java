package com.silvandrade.beberagua;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private EditText editTextNumberInterval;
    private Button buttonNotify;
    private int hour;
    private int minute;
    private int interval;
    private boolean activated;
    private static final String KEY_NOTIFY = "activated";
    private static final String KEY_INTERVAL = "interval";
    private static final String KEY_HOUR = "hour";
    private static final String KEY_MINUTE = "minute";
    private static final String DB_NAME = "db";

    /*
        A função principal desta Classe é criar uma alarme que ira disparar um PendingIntent (broadcast) que contém uma intent (message).
        A seguinte construção é gerada nesta classe: Alarm(PendingIntent(Intent)).
        O Alarme define o tempo e recebe a PendingIntent (broadcast) a ser disparado.
        O Broadcast contém o emissor (context) e a mensagem (intent).
        A Notificação contém o destinatário (NotificationPublisher) e os dados da mensagem.
    */

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeActionBar();
        createLayoutReferences();
        sharedPreferencesIsActivated();

        buttonNotify.setOnClickListener(v -> { // Definindo escuta de clique no botão.

            if (formIsInvalidated()) { // Validando o input de dados.
                return;
            }

            getDataInLayout(); // Pegando os dados do Layout para as variáveis.
            alarmIsActivated(this.activated);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) { // Verficando se foi clicado no item voltar.
            finish(); // Finalizando a activity.
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            int color = ContextCompat.getColor(MainActivity.this, R.color.teal_200); // Pegando a cor do arquivo de recursos color.xml
            ColorDrawable colorDrawable = new ColorDrawable(color);

            actionBar.setDisplayHomeAsUpEnabled(true); // Criando o item voltar na action bar.
            actionBar.setBackgroundDrawable(colorDrawable); // Mudando a cor da action bar.
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void sharedPreferencesIsActivated() {
        SharedPreferences preferences = createPreferences();
        // Capturando valor da chave ou setando valor padrão.
        this.activated = preferences.getBoolean(KEY_NOTIFY, false);

        if (this.activated) { // Alterando as definições de UI e do Banco.
            changeStyleButton();
            getDataInDataBase(preferences);
            setDataInLayout();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void alarmIsActivated(boolean activated) {
        SharedPreferences preferences;
        SharedPreferences.Editor editor;
        Intent message;
        PendingIntent broadcast;

        preferences = createPreferences(); // Criando um bando de dados.
        editor = createEditor(preferences); // Criando um objeto que altera o bando de dados.
        message = createMessage(MainActivity.this); // Criando uma Intent de notificação que será enviada para nossa classe de escuta.
        broadcast = createBroadcast(MainActivity.this, message); // Criando uma Pending Intent que executara nossa Message no futuro.

        if (!activated) { // Alterando as definições da UI, do Banco e criando Alarme.
            changeStyleButton();
            saveDataInDataBase(editor);
            createAlarmBroadcast(this.interval, broadcast); // Passando uma Pending Intent (broadcast) que contém nossa Intent (message) para o Alarme de disparo.
            messageToast(R.string.activated_alarm);
        } else { // Alterando as definições da UI, do Banco e excluindo Alarme.
            defaultStyleButton();
            removeDataInDataBase(editor);
            cancelAlarmBroadcast(broadcast);
            messageToast(R.string.desactivated_alarm);
        }

        editor.apply(); // Aplicando as alterações no banco de dados.
        this.activated = !activated; // Alterando o status da aplicação.
    }

    private SharedPreferences createPreferences() {
        return getSharedPreferences(MainActivity.DB_NAME, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor createEditor(SharedPreferences preferences) {
        return preferences.edit();
    }

    private boolean formIsInvalidated() {
        String interval = editTextNumberInterval.getText().toString();

        boolean isInvalid = interval.isEmpty() || interval.equals("0") || interval.equals("00") || interval.equals("000");
        if (isInvalid) {
            messageToast(R.string.error_msg);
        }
        return isInvalid;
    }

    private void messageToast(int resId) {
        Toast.makeText(MainActivity.this, resId, Toast.LENGTH_LONG).show();
    }

    private void createLayoutReferences() {
        // Referenciando os objetos do meu layout.
        editTextNumberInterval = MainActivity.this.findViewById(R.id.edit_text_number_interval);
        buttonNotify = MainActivity.this.findViewById(R.id.btn_notify);
        timePicker = MainActivity.this.findViewById(R.id.timer_picker);
        timePicker.setIs24HourView(true);
    }

    private Intent createMessage(Context context) {
        // Definindo uma intenção para escutar as chamadas de Broadcasts Receiver do android.
        Intent message = new Intent(context, NotificationPublisher.class);
        message.putExtra(NotificationPublisher.KEY_MESSAGE, this.getResources().getText(R.string.alarm)); // Passando dados na intenção.
        message.putExtra(NotificationPublisher.KEY_MESSAGE_ID, 1);
        return message;
    }

    private PendingIntent createBroadcast(Context context, Intent message) {
        return PendingIntent.getBroadcast(context, 0, message, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private void createAlarmBroadcast(int timeInMillis, PendingIntent broadcast) {
        // Pegando o tempo atual, convertendo o intervalo de milissegundos para segundos.
//        long second = SystemClock.elapsedRealtime() + timeInMillis * 1000;
        long second = timeInMillis * 1000;
        long minute = second * 60;
        Calendar calendar = createCalendar();

        // Criando o alarme que irá disparar a mensagem de broadcast no tempo definido.
        final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, second, broadcast);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), minute, broadcast);
    }

    private void cancelAlarmBroadcast(PendingIntent broadcast) {
        final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(broadcast);
    }

    private Calendar createCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeStyleButton() {
        buttonNotify.setBackgroundResource(R.drawable.dw_button_background_activated);
        buttonNotify.setText(R.string.pause);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void defaultStyleButton() {
        buttonNotify.setBackgroundResource(R.drawable.dw_button_background_default);
        buttonNotify.setText(R.string.notify);
    }

    private void getDataInDataBase(SharedPreferences preferences) {
        interval = preferences.getInt(KEY_INTERVAL, 0);
        hour = preferences.getInt(KEY_HOUR, timePicker.getCurrentHour());
        minute = preferences.getInt(KEY_MINUTE, timePicker.getCurrentMinute());
    }

    private void saveDataInDataBase(SharedPreferences.Editor editor) {
        editor.putBoolean(KEY_NOTIFY, true);
        editor.putInt(KEY_INTERVAL, interval);
        editor.putInt(KEY_HOUR, hour);
        editor.putInt(KEY_MINUTE, minute);
    }

    private void removeDataInDataBase(SharedPreferences.Editor editor) {
        editor.putBoolean(KEY_NOTIFY, false);
        editor.remove(KEY_INTERVAL);
        editor.remove(KEY_HOUR);
        editor.remove(KEY_MINUTE);
    }

    private void getDataInLayout() {
        hour = timePicker.getCurrentHour();
        minute = timePicker.getCurrentMinute();
        interval = Integer.parseInt(editTextNumberInterval.getText().toString());
    }

    private void setDataInLayout() {
        editTextNumberInterval.setText(String.valueOf(interval));
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
    }
}