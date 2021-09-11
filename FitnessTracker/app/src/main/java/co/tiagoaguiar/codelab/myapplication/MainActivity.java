package co.tiagoaguiar.codelab.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

	private View btnImc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnImc = findViewById(R.id.btn_imc);

		// Usando o mêtodo setOnClickListener do meu objeto btnImc e passando um objeto como argumento que sera criado quando houver click no botão.
		btnImc.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Definindo um objeto de intenção que recebe o contexto atual e a classe a ser executada.
				Intent intent = new Intent(MainActivity.this, ImcActivity.class);
				// Executando a intenção e criando a nova Activity.
				startActivity(intent);
			}
		});
	}
}