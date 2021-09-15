package co.tiagoaguiar.codelab.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListCalcActivity extends AppCompatActivity {

    private List<Register> registers;
    private RecyclerView rvCalcList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_calc);

        Bundle extra = getIntent().getExtras(); // Recuperando o type passado pela ImcActivity quando chamou a ListCalcActivity.

        if(extra != null) { // Verificando se algum dado foi passado.
            String type = extra.getString("type"); // Recuperando o valor pela chave.

//            registers = SqlHelper.getInstance(this).getRegisterBy(type);

            new Thread(() -> {
                // Passando o valor como argumento de consulta que vai me retornar uma lista de registro.
                registers = SqlHelper.getInstance(this).getRegisterBy(type);
                runOnUiThread(() -> {
                    // Prosseguindo a execução da Thread principal.
                    rvCalcList = findViewById(R.id.rv_calc_list);
                    rvCalcList.setLayoutManager(new LinearLayoutManager(this));

                    Adapter adapter = new Adapter(registers);
                    rvCalcList.setAdapter(adapter);
                });
            }).start();
        }
    }

    private static class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private final List<Register> registers;

        public Adapter(List<Register> registers) {
            this.registers = registers;
        }

        @NonNull
        @NotNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull ListCalcActivity.ViewHolder holder, int position) {

            Register register = registers.get(position);

            Date dateSalved;
            String formatDate = "";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("pt", "BR"));
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", new Locale("pt", "BR"));

            try {
                dateSalved = sdf.parse(register.getCreatedDate());
                assert dateSalved != null;
                formatDate = sdf2.format(dateSalved);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            holder.textView.setText(formatDate  + " - " + register.getType() + " - " + register.getResponse());
            if(position % 2 == 1) {
                holder.textView.setBackgroundResource(R.drawable.btn_circle_background);
            } else {
                holder.textView.setBackgroundResource(R.drawable.btn_circle_background);
                holder.textView.setBackgroundColor(Color.WHITE);
            }
        }

        @Override
        public int getItemCount() {
            return registers.size();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView = itemView.findViewById(android.R.id.text1);

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}