package com.silvandrade.recyclerasync;

public interface ModelSource {
    int getCount(); // Total de itens na fonte de dados.
    Model getItem(int position); // Retorna um item de dados na posição da sua coleção.
    void close(); // Liberar recursos da fonte.
}
