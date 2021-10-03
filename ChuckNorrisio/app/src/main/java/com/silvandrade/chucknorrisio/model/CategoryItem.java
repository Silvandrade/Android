package com.silvandrade.chucknorrisio.model;

import android.support.annotation.NonNull;
import android.widget.TextView;
import com.silvandrade.chucknorrisio.R;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class CategoryItem extends Item<ViewHolder> {

    private final String categoryName;
    private final int bgColor;

    public CategoryItem (String categoryName, int bgColor) {
        this.categoryName = categoryName;
        this.bgColor = bgColor;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) { // Pega a referência do objeto do layout.
        final TextView textViewCategory = viewHolder.itemView.findViewById(R.id.text_view_category);
        textViewCategory.setText(this.categoryName);
        viewHolder.itemView.setBackgroundColor(bgColor);
    }

    @Override
    public int getLayout() { // Infla o layout.
        return R.layout.card_category_main;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
