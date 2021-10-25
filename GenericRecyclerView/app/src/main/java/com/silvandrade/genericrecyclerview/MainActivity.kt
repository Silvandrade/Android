package com.silvandrade.genericrecyclerview

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

        val recyclerView = findViewById<RecyclerView>(R.id.main_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = ItemAdapter()
    }

    private inner class ItemAdapter: RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
            return ItemViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            holder.cabecalho.text = "Cabeçalho $position"
            holder.rodape.text = "Rodapé $position"
        }

        override fun getItemCount(): Int {
            return 10
        }

        private inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val rodape: TextView = itemView.findViewById(R.id.item_text_view_rodape)
            val cabecalho: TextView = itemView.findViewById(R.id.item_text_view_cabeçalho)
            val imagem: ImageView = itemView.findViewById(R.id.item_image_view_user)
        }
    }
}