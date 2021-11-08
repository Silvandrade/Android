package com.silvandrade.recyclerasync;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListUtil;

public class DataCallback extends AsyncListUtil.DataCallback<Model> {

    private final ModelSource modelSource;

    public DataCallback(ModelSource modelSource) {
        this.modelSource = modelSource;
    }

    @Override
    public void fillData(@NonNull Model[] data, int startPosition, int itemCount) { // Chamando no thread em background para pegar mais dados.
        Log.d("Recycler Async", this.getClass().getName() + " - fillData() called.");
        for(int i = 0; i < itemCount; i++) {
            data[i] = modelSource.getItem(startPosition + i);
        }
    }

    @Override
    public int refreshData() { // Chamado no thread em background na inicialização para retornar o numero total de itens de dados.
        Log.d("Recycler Async", this.getClass().getName() + " - refreshData() called.");
        return modelSource.getCount();
    }

    public void close() {
        modelSource.close();
    }
}
