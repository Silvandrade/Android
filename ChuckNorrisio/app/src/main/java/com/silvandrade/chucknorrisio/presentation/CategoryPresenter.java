package com.silvandrade.chucknorrisio.presentation;

import com.silvandrade.chucknorrisio.Colors;
import com.silvandrade.chucknorrisio.MainActivity;
import com.silvandrade.chucknorrisio.datasource.CategoryRemoteDataSource;
import com.silvandrade.chucknorrisio.model.CategoryItem;
import java.util.ArrayList;
import java.util.List;

public class CategoryPresenter implements CategoryRemoteDataSource.ListCategoriesCallback {

    private final MainActivity mainActivity;
    private final CategoryRemoteDataSource dataSource;
    private static List<CategoryItem> response = new ArrayList<>();

    public CategoryPresenter(MainActivity mainActivity, CategoryRemoteDataSource dataSource) {
        this.mainActivity = mainActivity;
        this.dataSource = dataSource;
    }

    public void requestAll() {
        this.mainActivity.showProgressDialog(); // Exibindo a barra de progresso.
        this.dataSource.findAll(this); // Executando a consulta dos dados passando a instância que implementa o callback interface.
//        this.request(); // Chamar um servidor.
    }

    @Override
    public void onSuccess(List<String> response) {
        List<CategoryItem> items = new ArrayList<>();

        for(String item: response) { // Convertendo minha lista de Strings retornada pela classe CategoryRemoteDataSource em objetos da classe CategoryItem.
            items.add(new CategoryItem(item, Colors.randomColor()));
        }

        mainActivity.showCategories(items); // Enviando os items para exibição.
    }

    @Override
    public void onError(String message) {
        this.mainActivity.showFailure(message);
    }

    @Override
    public void onComplete() {
        this.mainActivity.hideProgressDialog(); // Escondendo a barra de progresso.
    }
}
