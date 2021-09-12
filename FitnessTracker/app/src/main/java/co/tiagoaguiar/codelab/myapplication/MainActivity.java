package co.tiagoaguiar.codelab.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

	private RecyclerView rvMain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		List<MainItem> mainItems = new ArrayList<>();
		mainItems.add(new MainItem(1, R.drawable.ic_baseline_emoji_people_24, R.string.label_imc, Color.GREEN, R.drawable.btn_circle_background));
		mainItems.add(new MainItem(2, R.drawable.ic_baseline_pregnant_woman_24, R.string.label_tmb, Color.YELLOW, R.drawable.btn_circle_background));

		rvMain = findViewById(R.id.rv_main); // Referenciando minha RecyclerView.
		rvMain.setLayoutManager(new GridLayoutManager(this, 2)); // Definindo o comportamento de exibição do RecyclerView.

		Adapter adapter = new Adapter(mainItems); // Criando o Adapter.
		adapter.setListener(id -> { // Usando o método implementado no Adapter.
			switch (id) {
				case 1:
					// Executando a intenção e criando a nova Activity.
					startActivity(new Intent(MainActivity.this, ImcActivity.class));
					break;
				case 2:
					// Executando a intenção e criando a nova Activity.
					startActivity(new Intent(MainActivity.this, TmbActivity.class));
					break;
			}
		});

		rvMain.setAdapter(adapter); // Definindo o Adapter da RecyclerView.
	}

	// Recebe a referência de uma celular do RecyclerView e vincula uma ViewHolder.
	private static class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> { // Vincula os dados a ViewHolder e a ViewHolder a RecyclerView.

		private final List<MainItem> mainItems;
		private	OnItemClickListener listener; // Definindo variável da Interface OnItemClickListener.

		public Adapter(List<MainItem> mainItems) {
			this.mainItems = mainItems;
		}

		@NonNull
		@org.jetbrains.annotations.NotNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
			// Referenciando o objeto singleton LayoutInflater da minha MainActivity.
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            /* Usando o método inflate para inflar a View dentro do meu RecyclerView.
                O primeiro argumento se refere à View que será inflada, neste caso será um LayoutLinear;
                O segundo argumento é uma referência ao local onde a View será inflanda, neste caso no meu RecyclerView. */
			View view = inflater.inflate(R.layout.main_item, parent, false);
			return new ViewHolder(view); // Criando o objeto ViewHolder passando como argumento a View que será vinculada à ViewHolder.

//			return ViewHolder(getLayoutInflater().inflate(R.layout.main_item, parent, false));
		}

		public void setListener(OnItemClickListener listener) {
			this.listener = listener; // Definindo o objeto.
		}

		@Override
		public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewHolder holder, int position) {
			MainItem item = this.mainItems.get(position);

			holder.textView.setText(item.getTextId());
			holder.imageView.setImageResource(item.getDrawableId());
			holder.container.setBackgroundColor(item.getColor());
			holder.container.setBackgroundResource(item.getBackgroundId());

			// Usando o mêtodo setOnClickListener do meu objeto btnImc e passando um objeto como argumento que sera criado quando houver click no botão.
			holder.container.setOnClickListener(v -> {
				listener.onClick(item.getId());
			});
		}

		@Override
		public int getItemCount() {
			return this.mainItems.size(); // Definindo a quantidade de itens da RecyclerView.
		}

		// View de cadas celula do RecyclerView.
		private static class ViewHolder extends RecyclerView.ViewHolder { // Classe que possui a referência das Views componete de cada Item da RecyclerView.

			TextView textView = itemView.findViewById(R.id.main_label_item);
			ImageView imageView = itemView.findViewById(R.id.main_img_item);
			LinearLayout container = itemView.findViewById(R.id.main_item);

			public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
				super(itemView); // Passando a View que será vinculada a ViewHolder.
			}
		}
	}
}