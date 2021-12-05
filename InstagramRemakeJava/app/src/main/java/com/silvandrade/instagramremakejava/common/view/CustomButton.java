package com.silvandrade.instagramremakejava.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.silvandrade.instagramremakejava.R;

public class CustomButton extends FrameLayout {

    private AppCompatButton button;
    private ProgressBar progressBar;
    private String text;

    public CustomButton(@NonNull Context context) {
        super(context);
        setup(context, null);
    }

    public CustomButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs);
    }

    public CustomButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs);
    }

    private void setup(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // Recupera o objeto FrameLayout da classe.
        inflater.inflate(R.layout.button_load, this, true); // Infla o layout button_load.xml dentro do objeto FrameLayout da classe.

        // Referencia os atributos do arquivo attrs.xml
        @SuppressLint("Recycle") TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomButton, 0, 0);

        text = typedArray.getString(R.styleable.CustomButton_text); // Recupera o valor do atributo definido em values/attrs.

        button = (AppCompatButton) getChildAt(0); // Captura o primeiro objeto da classe que foi inserido ao inflar o layout.
        button.setText(text);
        button.setEnabled(false);

        progressBar = (ProgressBar) getChildAt(1); // Captura o segundo objeto da classe que foi inserido ao inflar o layout.

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) { // Pintar a cor da progressbar deoendendo da versão do android.
            final Drawable wrap = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
            DrawableCompat.setTint(wrap, ContextCompat.getColor(context, android.R.color.white));
            progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrap));
        } else {
            progressBar.getIndeterminateDrawable().setColorFilter(
                    ContextCompat.getColor(context, android.R.color.white), PorterDuff.Mode.SRC_IN
            );
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        button.setEnabled(enabled);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) { // Sobrescreve o setOnClick do FrameLayout.
        button.setOnClickListener(l); // Sobrescreve o setOnClick do AppCompartButton.
    }

    public void showProgress(boolean enabled) {
        progressBar.setVisibility(enabled ? VISIBLE: GONE); // Exibe ou esconde a Progressbar.
        button.setText(enabled ? "" : text); // Exibe ou esconde o texto do AppCompatButton.
    }
}
