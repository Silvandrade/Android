package com.silvandrade.recyclerasync;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListUtil;
import androidx.recyclerview.widget.RecyclerView;

public class AsyncAdapterFilm extends RecyclerView.Adapter<AsyncAdapterFilm.ViewHolder> {

    private final AsyncListUtil listUtil;
    private final DataCallback dataCallback;
    private final ScrollListener scrollListener;

    public AsyncAdapterFilm(ModelSource modelSource, RecyclerView recyclerView) {
        ViewCallback viewCallback = new ViewCallback(recyclerView);
        dataCallback = new DataCallback(modelSource);
        listUtil = new AsyncListUtil(Film.class, 20, dataCallback, viewCallback);
        scrollListener = new ScrollListener(listUtil);
    }

    public void onStart(RecyclerView recyclerView) {
        Log.d("Recycler Async", this.getClass().getName() + " - onStart() called.");
        recyclerView.addOnScrollListener(scrollListener);
        listUtil.refresh();
    }

    public void onStop(RecyclerView recyclerView) {
        Log.d("Recycler Async", this.getClass().getName() + " - onStop() called.");
        recyclerView.removeOnScrollListener(scrollListener);
        dataCallback.close();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Film film = (Film) listUtil.getItem(position);

        if (film != null) {
            holder.title.setText(film.getTitle());
            holder.content.setText(film.getContent());
        } else {
            holder.title.setText("Carregando título...");
            holder.content.setText("Carregando conteúdo...");
        }
    }

    @Override
    public int getItemCount() {
        return listUtil.getItemCount();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_view_item_title);
            content = itemView.findViewById(R.id.text_view_item_content);
        }
    }
}
