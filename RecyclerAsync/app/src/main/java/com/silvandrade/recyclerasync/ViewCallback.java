package com.silvandrade.recyclerasync;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewCallback extends AsyncListUtil.ViewCallback {

    private final RecyclerView recyclerView;

    public ViewCallback(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDataRefresh() { // Sinaliza o recyclerview de que os dados foram carregados.
        Log.d("Recycler Async", this.getClass().getName() + " - onItemLoaded() called.");
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onItemLoaded(int position) { // Sinaliza o recyclerview de que os dados de um item foram alterados.
        Log.d("Recycler Async", this.getClass().getName() + " - onDataRefresh() called.");
        recyclerView.getAdapter().notifyItemChanged(position);
    }

    @Override
    public void getItemRangeInto(@NonNull int[] outRange) { // Determina a posição dos dados visíveis para definir o momento de buscar novos itens ou liberar itens.
        Log.d("Recycler Async", this.getClass().getName() + " - getItemRangeInto() called.");
        if(outRange == null) {
            return;
        }

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();

        if(manager instanceof LinearLayoutManager) {
            LinearLayoutManager linear = (LinearLayoutManager) manager;
            outRange[0] = linear.findFirstVisibleItemPosition(); // Retornar a posição do primeiro ítem visível.
            outRange[1] = linear.findLastVisibleItemPosition(); // Retornar a posição do ultimo ítem visível.
        }

        if(outRange[0] == -1 && outRange[1] == -1) { // Se não houver itens visíveis.
            outRange[0] = 0;
            outRange[1] = 0;
        }
    }
}
