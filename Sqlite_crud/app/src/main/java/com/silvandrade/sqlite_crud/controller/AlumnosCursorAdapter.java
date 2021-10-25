package com.silvandrade.sqlite_crud.controller;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.silvandrade.sqlite_crud.R;

public class AlumnosCursorAdapter extends CursorAdapter {

    public AlumnosCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_alumno, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Referências da interface gráfica.
        TextView textViewId = (TextView) view.findViewById(R.id.text_view_item_id);
        TextView textViewName = (TextView) view.findViewById(R.id.text_view_item_name);
        TextView textViewLastName = (TextView) view.findViewById(R.id.text_view_item_lastname);
        TextView textViewDegree = (TextView) view.findViewById(R.id.text_view_item_degree);
        TextView textViewGroup = (TextView) view.findViewById(R.id.text_view_item_group);
        TextView textViewTurn = (TextView) view.findViewById(R.id.text_view_item_turn);


        // Recupernado dados do banco.
        String id = cursor.getString((int) cursor.getColumnIndex("ID"));
        String name = cursor.getString((int) cursor.getColumnIndex("NOMBRE"));
        String lastName = cursor.getString((int) cursor.getColumnIndex("APELLIDO"));
        String degree = cursor.getString((int) cursor.getColumnIndex("GRADO"));
        String group = cursor.getString((int) cursor.getColumnIndex("GRUPO"));
        String turn = cursor.getString((int) cursor.getColumnIndex("TURNO"));

        // Repassando os dados para a interface gráfica.
        textViewId.setText(id);
        textViewName.setText(name);
        textViewLastName.setText(lastName);
        textViewGroup.setText(group);
        textViewDegree.setText(degree);
        textViewTurn.setText(turn);
    }
}
