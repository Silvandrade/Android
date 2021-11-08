package com.silvandrade.recyclerasync;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AsyncAdapterCategory extends RecyclerView.Adapter<AsyncAdapterCategory.ViewHolder> {

    private final DataCallback dataCallback;
    private final ViewCallback viewCallback;
    private final AsyncListUtil listUtil;
    private final ScrollListener scrollListener;

    public AsyncAdapterCategory(ModelSource modelSource, RecyclerView recyclerView) {
        viewCallback = new ViewCallback(recyclerView);
        dataCallback = new DataCallback(modelSource);
        listUtil = new AsyncListUtil(Category.class, 3, dataCallback, viewCallback);
        scrollListener = new ScrollListener(listUtil);
    }

    public void onStart(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(scrollListener);
        listUtil.refresh();
    }

    public void onStop(RecyclerView recyclerView) {
        recyclerView.removeOnScrollListener(scrollListener);
        dataCallback.close();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = (Category) listUtil.getItem(position);

        if(category != null) {
            holder.titleCategory.setText(category.name);
            holder.adapterFilm.onStart(holder.recyclerViewFilms);
        } else {
            holder.titleCategory.setText("Carregando...");
        }
    }

    @Override
    public int getItemCount() {
        return listUtil.getItemCount();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleCategory;

        RecyclerView recyclerViewFilms;
        AsyncAdapterFilm adapterFilm;
        private ModelSource modelSource;
        private AdminDB adminDB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleCategory = itemView.findViewById(R.id.text_view_category_title);
            recyclerViewFilms = itemView.findViewById(R.id.recycler_view_category_films);

            recyclerViewFilms.setLayoutManager(
                    new LinearLayoutManager(
                            itemView.getContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                    )
            );

            adminDB = new AdminDB(itemView.getContext());
            modelSource = new DataSource(adminDB.getReadDB(), "Film");
            adapterFilm = new AsyncAdapterFilm(modelSource, recyclerViewFilms);
            recyclerViewFilms.setAdapter(adapterFilm);

//            recyclerViewFilms.addOnScrollListener(new OnScrollListener() {
//
//                @Override
//                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                    super.onScrollStateChanged(recyclerView, newState);
//                    Log.d("Rolagem", "Scroll rolagem");
//                    listUtil.onRangeChanged();
//                }
//            });
        }
    }
}
