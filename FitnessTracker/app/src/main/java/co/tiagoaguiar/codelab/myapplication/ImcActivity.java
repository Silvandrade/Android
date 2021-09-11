package co.tiagoaguiar.codelab.myapplication;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ImcActivity extends AppCompatActivity {

    private EditText editTextHeight;
    private EditText editTextWeight;
    private Button btnCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);

        editTextHeight = findViewById(R.id.edit_imc_height);
        editTextWeight = findViewById(R.id.edit_imc_weight);
        btnCalc = findViewById(R.id.btn_calc);

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateForm()) { // Verifica se o preenchimento dos campos é válido.
                    // Exibe uma mensagem de erro de validação.
                    Toast.makeText(ImcActivity.this, R.string.fields_invalidation_form, Toast.LENGTH_LONG).show();
                    return; // Interrompe a continuidade da execução do código.
                }

                int height = Integer.parseInt(editTextHeight.getText().toString());
                int weight = Integer.parseInt(editTextWeight.getText().toString());

                double imcResult = calculate(height, weight); // Retornando o valor do IMC.
                int imcResponseId = imcResponse(imcResult); // Retornado o valor do item do arquivo de recurso.

//                Toast.makeText(ImcActivity.this, imcResponseId, Toast.LENGTH_LONG).show(); // Exibindo a mensagem com o IMC e a descrição.

                AlertDialog dialog = new AlertDialog.Builder(ImcActivity.this)
                        .setTitle(getString(R.string.imc_response, imcResult)) // Criando o título da caixa e usando o método getString para mudar dinâmicamente o valor no arquivo de recursos.
                        .setMessage(imcResponseId) // Criando a mensagem usando como argumento o valor do item do arquivo de recurso.
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // Criando um botão de ok com o arquivo de recurso do android e implemendo um ouvinte de clique.
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();

                dialog.show();

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); // Definindo um objeto gerenciador do teclado para manipular recursos de serviços.
                inputMethodManager.hideSoftInputFromWindow(editTextHeight.getWindowToken(), 0); // Solicitando o fechamento do teclado passando como argumento o objeto que solicitou sua abertura.
            }
        });
    }

    @StringRes // Anotação para exigir que o retorno desta função seja obrigatoriamente um arquivo de resource.
    private int imcResponse(double imcResult) {
        if(imcResult<15)
            return R.string.imc_severely_low_weight;
        else if(imcResult<16)
            return R.string.imc_very_low_weight;
        else if(imcResult<18.5)
            return R.string.imc_low_weight;
        else if(imcResult<25)
            return R.string.normal;
        else if(imcResult<30)
            return R.string.imc_high_weight;
        else if(imcResult<35)
            return R.string.imc_so_high_weight;
        else if(imcResult<40)
            return R.string.imc_severely_high_weight;
        else
            return R.string.imc_extreme_weight;
    }

    private double calculate(int height, int weight) { // Calculando o IMC.
        return (double) weight / (((double) height / 100) * ((double) height /100));
    }

    private Boolean validateForm() { // Definindo os valores válidos nos campos do formulário.
        return  (!editTextHeight.getText().toString().startsWith("0")
                && !editTextWeight.getText().toString().startsWith("0")
                && !editTextHeight.getText().toString().isEmpty()
                && !editTextWeight.getText().toString().isEmpty());
    }
}