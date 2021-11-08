package com.silvandrade.recyclerasync;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ScrollListener extends RecyclerView.OnScrollListener {

    private final AsyncListUtil<Model> listUtil;

    public ScrollListener(AsyncListUtil<Model> listUtil) {
        this.listUtil = listUtil;
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        Log.d("Rolagem", "Scroll rolagem");
        listUtil.onRangeChanged(); // Chama o mêtodo da classe AsynListUtil sempre que é feita a rolagem nos itens do recyclerview.
    }
}
