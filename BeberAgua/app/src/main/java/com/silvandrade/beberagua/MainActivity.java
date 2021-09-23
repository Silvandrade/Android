package com.silvandrade.beberagua;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private EditText editTextNumberInterval;
    private Button buttonNotify;
    private int hour;
    private int minute;
    private int interval;
    private boolean activated;
    private int color;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referenciando os objetos do meu layout.
        timePicker = findViewById(R.id.timer_picker);
        editTextNumberInterval = findViewById(R.id.edit_text_number_interval);
        buttonNotify = findViewById(R.id.btn_notify);

        timePicker.setIs24HourView(true);

        // Declarando uma classe, dando nome do banco e definindo um contexto privado para outros Apps.
        SharedPreferences preferences = getSharedPreferences("db", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit(); // Definindo um editor de registro.

        activated = preferences.getBoolean("activated", false); // Capturando valor da chave e definindo um retorno default.

        // Se o banco estiver ativo recuperar os dados.
        if(activated) {
            changeColorButtonBlack();
            getDataInDataBase(preferences);
            setDataInLayout();
        }

        // Definindo escuta de clique no botão.
        buttonNotify.setOnClickListener(v -> {
            final String sInterval = editTextNumberInterval.getText().toString();

            // Validando o input de dados.
            if (sInterval.isEmpty()) {
                Toast.makeText(this, R.string.error_msg, Toast.LENGTH_LONG).show();
                return;
            }

            getDataInLayout(sInterval);

            // Alterando  as propriedades do botão e armazenando ou removendo dados.
            if(!activated) {
                changeColorButtonBlack();
                saveDataInDataBase(editor);
            } else {
                changeColorButtonBlue();
                removeDataInDataBase(editor);
            }

            editor.apply();
            activated = !activated;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeColorButtonBlack() {
        color = ContextCompat.getColor(this, android.R.color.black);
        buttonNotify.setBackgroundTintList(ColorStateList.valueOf(color));
        buttonNotify.setText(R.string.pause);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeColorButtonBlue() {
        color = ContextCompat.getColor(this, R.color.teal_200);
        buttonNotify.setBackgroundTintList(ColorStateList.valueOf(color));
        buttonNotify.setText(R.string.notify);
    }

    private void getDataInDataBase(SharedPreferences preferences) {
        // Recuperando so dados do banco de dados.
        interval = preferences.getInt("interval", 0);
        hour = preferences.getInt("hour", timePicker.getCurrentHour());
        minute = preferences.getInt("minute", timePicker.getCurrentMinute());
    }

    private void saveDataInDataBase(SharedPreferences.Editor editor) {
        // Guardando meus registros no banco de dados compartilhado.
        editor.putBoolean("activated", true);
        editor.putInt("interval", interval);
        editor.putInt("hour", hour);
        editor.putInt("minute", minute);
    }

    private void removeDataInDataBase(SharedPreferences.Editor editor) {
        // Removendo os dados do banco.
        editor.putBoolean("activated", false);
        editor.remove("interval");
        editor.remove("hour");
        editor.remove("minute");
    }

    private void getDataInLayout(String sInterval) {
        // Recuperando os dados do Layout.
        hour = timePicker.getCurrentHour();
        minute = timePicker.getCurrentMinute();
        interval = Integer.parseInt(sInterval);
    }

    private void setDataInLayout() {
        // Passando os dados recuperados para os objetos do layout.
        editTextNumberInterval.setText(String.valueOf(interval));
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
    }
}