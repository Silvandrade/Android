package co.tiagoaguiar.codelab.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class TmbActivity extends AppCompatActivity {

    private EditText editTextHeight;
    private EditText editTextWeight;
    private EditText editTextAge;
    private Spinner spinner;
    private Button btnCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmb);

        editTextHeight = findViewById(R.id.edit_tmb_height);
        editTextWeight = findViewById(R.id.edit_tmb_weight);
        editTextAge = findViewById(R.id.edit_tmb_age);
        spinner = findViewById(R.id.spinner_tmb_lifestyle);

        btnCalc = findViewById(R.id.btn_tmb_calc);

        btnCalc.setOnClickListener(v -> {
            if(!validateForm()) { // Verifica se o preenchimento dos campos é válido.
                // Exibe uma mensagem de erro de validação.
                Toast.makeText(TmbActivity.this, R.string.fields_invalidation_form, Toast.LENGTH_LONG).show();
                return; // Interrompe a continuidade da execução do código.
            }

            int height = Integer.parseInt(editTextHeight.getText().toString());
            int weight = Integer.parseInt(editTextWeight.getText().toString());
            int age = Integer.parseInt(editTextAge.getText().toString());

            double tmbResult = calculateTmb(height, weight, age); // Retornando o valor do IMC.
            double tmbResponseId = tmbResponse(tmbResult); // Retornado o valor do item do arquivo de recurso.

            // Criando um botão de ok com o arquivo de recurso do android e implemendo um ouvinte de clique.
            AlertDialog dialog = new AlertDialog.Builder(TmbActivity.this)
                    .setTitle(getString(R.string.tmb_response, tmbResult)) // Criando o título da caixa e usando o método getString para mudar dinâmicamente o valor no arquivo de recursos.
                    .setMessage(getString(R.string.tmb_response, tmbResponseId)) // Criando a mensagem usando como argumento o valor do item do arquivo de recurso.
                    .setPositiveButton(android.R.string.ok, (dialog1, which) -> {}) // Botão positive fica na direita.
                    .setNegativeButton(R.string.save, (dialog1, which) -> { // Botão negative fica na esquerda.

                        new Thread(() -> { // Código será executado em um bloco de instrução do processador separada para não segurar a Thread principal.
                            long calcId = SqlHelper.getInstance(TmbActivity.this).addItem("tmb", tmbResult);
                            runOnUiThread(() -> { // Devolvendo a execução para Thread principal.
                                if(calcId > 0) {
                                    Toast.makeText(TmbActivity.this, R.string.salved, Toast.LENGTH_LONG).show();
                                    openListCalcActivity();
                                }
                            });
                        }).start();
                    })
                    .create();

            dialog.show();

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); // Definindo um objeto gerenciador do teclado para manipular recursos de serviços.
            inputMethodManager.hideSoftInputFromWindow(editTextHeight.getWindowToken(), 0); // Solicitando o fechamento do teclado passando como argumento o objeto que solicitou sua abertura.
        });
    }

    private double calculateTmb(int height, int weight, int age) {
        return 66 + (weight - 13.8) + (5 * height) - (6.8 * age);
    }

    private double tmbResponse(double tmbResult) {
        int index = spinner.getSelectedItemPosition();

        switch (index) {
            case 0:
                return tmbResult * 1.2;
            case 1:
                return tmbResult * 1.375;
            case 2:
                return tmbResult * 1.55;
            case 3:
                return tmbResult * 1.725;
            case 4:
                return tmbResult * 1.9;
            default:
                return 0;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // Método para inflar o Layout de menu.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // Método para pegar o click do item do menu.
        if(item.getItemId() == R.id.menu_list) {
            openListCalcActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openListCalcActivity() {
        Intent intent = new Intent(TmbActivity.this, ListCalcActivity.class); // Abrindo a ActivityListCalc.
        intent.putExtra("type", "tmb"); // Passando dados para a ActivityListCalc.
        startActivity(intent); // Executando.
    }

    private Boolean validateForm() { // Definindo os valores válidos nos campos do formulário.
        return  (!editTextHeight.getText().toString().startsWith("0")
                && !editTextWeight.getText().toString().startsWith("0")
                && !editTextAge.getText().toString().startsWith("0")
                && !editTextHeight.getText().toString().isEmpty()
                && !editTextWeight.getText().toString().isEmpty()
                && !editTextAge.getText().toString().isEmpty());
    }
}