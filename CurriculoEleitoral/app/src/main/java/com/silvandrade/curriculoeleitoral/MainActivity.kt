package com.silvandrade.curriculoeleitoral

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val categoriesPolitical: MutableList<CategoryPolitical> = ArrayList()
        val deputados: MutableList<Political> = ArrayList()
        val senadores: MutableList<Political> = ArrayList()
        val presidentes: MutableList<Political> = ArrayList()

        presidentes.add(Political(1, "Presidente","JAIR BOLSONARO", "Rio de Janeiro, RJ", "Não Informado", "Não Informado", R.drawable.photo_presidente_bolsonaro))
        categoriesPolitical.add(CategoryPolitical("Presidente", presidentes))

        senadores.add(Political(4, "Senador","FLAVIO BOLSONARO", "Rio de Janeiro, RJ", "Não Informado", "Não Informado", R.drawable.photo_senador_flavio))
        senadores.add(Political(5, "Senador","CID GOMES", "Sobral, CE", "Não Informado", "Não Informado", R.drawable.photo_senador_cid_gomes))
        senadores.add(Political(4, "Senador","FLAVIO BOLSONARO", "Rio de Janeiro, RJ", "Não Informado", "Não Informado", R.drawable.photo_senador_flavio))
        senadores.add(Political(5, "Senador","CID GOMES", "Sobral, CE", "Não Informado", "Não Informado", R.drawable.photo_senador_cid_gomes))
        senadores.add(Political(4, "Senador","FLAVIO BOLSONARO", "Rio de Janeiro, RJ", "Não Informado", "Não Informado", R.drawable.photo_senador_flavio))
        senadores.add(Political(5, "Senador","CID GOMES", "Sobral, CE", "Não Informado", "Não Informado", R.drawable.photo_senador_cid_gomes))
        categoriesPolitical.add(CategoryPolitical("Senador", senadores))

        deputados.add(Political(1, "Deputado Federal","BIA KICIS", "Resende, RJ", "Advogada; Procuradora de justiça", "Superior", R.drawable.photo_deputado_bia_kicis))
        deputados.add(Political(2, "Deputado Federal","EDUARDO BOLSONARO", "Rio de Janeiro, RJ", "Policial", "Superior", R.drawable.photo_deputado_eduardo))
        deputados.add(Political(3, "Deputado Federal","CELIO MOURA", "Arapuá, MG", "Advogado", "Superior", R.drawable.photo_deputado_celio_moura))
        deputados.add(Political(1, "Deputado Federal","BIA KICIS", "Resende, RJ", "Advogada; Procuradora de justiça", "Superior", R.drawable.photo_deputado_bia_kicis))
        deputados.add(Political(2, "Deputado Federal","EDUARDO BOLSONARO", "Rio de Janeiro, RJ", "Policial", "Superior", R.drawable.photo_deputado_eduardo))
        deputados.add(Political(3, "Deputado Federal","CELIO MOURA", "Arapuá, MG", "Advogado", "Superior", R.drawable.photo_deputado_celio_moura))
        deputados.add(Political(1, "Deputado Federal","BIA KICIS", "Resende, RJ", "Advogada; Procuradora de justiça", "Superior", R.drawable.photo_deputado_bia_kicis))
        deputados.add(Political(2, "Deputado Federal","EDUARDO BOLSONARO", "Rio de Janeiro, RJ", "Policial", "Superior", R.drawable.photo_deputado_eduardo))
        deputados.add(Political(3, "Deputado Federal","CELIO MOURA", "Arapuá, MG", "Advogado", "Superior", R.drawable.photo_deputado_celio_moura))
        categoriesPolitical.add(CategoryPolitical("Deputado Federal", deputados))

//        for(i in 1..5) {
//            for(j in 1..7) {
//                val political = Political(j, "Bia Kicis $j", "Resende, RJ", "Advogada; Procuradora de justiça", "Superior", R.drawable.photo_deputado_bia_kicis)
//                politicals.add(political)
//            }
//
//            val category = CategoryPolitical("Deputado Federal $i", politicals)
//            categoriesPolitical.add(category)
//        }

        val categoryRecyclerView: RecyclerView = findViewById<RecyclerView>(R.id.main_recycler_view)
        val categoryPoliticalAdapter = CategoryPoliticalAdapter(categoriesPolitical)

        categoryRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        categoryRecyclerView.adapter = categoryPoliticalAdapter

    }

    private class PoliticalAdapter(private val politicals: List<Political>):
        RecyclerView.Adapter<PoliticalAdapter.PoliticalHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoliticalHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
            return PoliticalHolder(view)
        }

        override fun onBindViewHolder(holder: PoliticalHolder, position: Int) {
            val political = politicals[position]
            holder.imageView.setImageResource(political.photo)
        }

        override fun getItemCount(): Int {
            return politicals.size
        }

        private class PoliticalHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById(R.id.item_photo_image_view)
        }
    }

    private inner class CategoryPoliticalAdapter(private val categoriesPolitical: List<CategoryPolitical>):
        RecyclerView.Adapter<CategoryPoliticalAdapter.CategoryPoliticalHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryPoliticalHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.category_political_item, parent, false)
            return CategoryPoliticalHolder(view)
        }

        override fun onBindViewHolder(holder: CategoryPoliticalHolder, position: Int) {
            val categoryPolitical = categoriesPolitical[position]
            val politicalAdapter = PoliticalAdapter(categoryPolitical.politicals)

            holder.textView.text = categoryPolitical.name

            holder.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
            holder.recyclerView.adapter = politicalAdapter
        }

        override fun getItemCount(): Int {
            return categoriesPolitical.size
        }

        private inner class CategoryPoliticalHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val textView = itemView.findViewById<TextView>(R.id.category_title_text_view)
            val recyclerView = itemView.findViewById<RecyclerView>(R.id.category_recycler_view)
        }
    }
}