package com.silvandrade.netflixremake.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import com.silvandrade.netflixremake.R;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {

    private final WeakReference<ImageView> imageViewWeakReference; // Definindo referência fraca para minha ImagemView por estar manipulando assincrono.
    private boolean shadowEnabled;

    public ImageDownloaderTask(ImageView imageView) { // Construtor da classe recebe a referência da minha ImageView.
        this.imageViewWeakReference = new WeakReference<>(imageView);
    }

    public void setShadowEnabled(boolean shadowEnabled) {
        this.shadowEnabled = shadowEnabled;
    }

    @Override
    protected Bitmap doInBackground(String... urlParams) {

        // Fazendo a requisição Web.
        String urlImage = urlParams[0]; // Pegando a referência da Url que vem como parâmentro.
        HttpsURLConnection urlConnection = null;
        try {
            // Requisitando URL.
            URL requestUrl = new URL(urlImage);
            urlConnection = (HttpsURLConnection) requestUrl.openConnection(); // Abrindo conexão com a página.
            urlConnection.setReadTimeout(2000);
            urlConnection.setConnectTimeout(2000);

            int urlResponseCode = urlConnection.getResponseCode(); // Capturando a resposta de conexão com a página.
            if(urlResponseCode != 200) { // Se o status da conexão não tiver sucesso.
                return null;
            }
            // Convertendo o inputStream em Bitmap.
            InputStream inputStream = urlConnection.getInputStream(); // Captura a referência do InputStream.
            if(inputStream != null) { // Se tiver retornado alguma imagem.
                return BitmapFactory.decodeStream(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect(); // Fechando a conexão independente de sucesso ou falha.
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(isCancelled()) { // Verificar se a requisição não está sendo cancelada.
            bitmap = null;
        }

        ImageView imageView = imageViewWeakReference.get(); // Capturando a referêcia do meu ImageView.

        if(imageView != null && bitmap != null) {
            // Trocando a imagem e mantendo o shadows.
            if (shadowEnabled) {
                LayerDrawable layerDrawable = (LayerDrawable) ContextCompat.getDrawable(imageView.getContext(), R.drawable.shadows);
                if (layerDrawable != null) {
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap); // Criando um Bitmap Desenhavel.
                    layerDrawable.setDrawableByLayerId(R.id.cover_drawable, bitmapDrawable); // Setando a referência do desenhavel no arquivo shandow.xml
                    imageView.setImageDrawable(layerDrawable); // Passando o desenhavel para o ImageView.
//            ((ImageView) findViewById(R.id.image_view_cover)).setImageDrawable(layerDrawable); // Acessando o ImageView de movie_item.xml e atualizando a imagem.
                }
            } else {
                // Escalando as dimensões do bitmap.
                if(bitmap.getWidth() < imageView.getWidth() || bitmap.getHeight() < imageView.getHeight()) {
                    Matrix matrix = new Matrix();
                    matrix.postScale((float) imageView.getWidth() / (float) bitmap.getWidth(),
                            (float) imageView.getHeight() / (float) bitmap.getHeight());

                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight());
                }

                imageView.setImageBitmap(bitmap); // Atribuindo imagem ao ImageView.
            }
        }
    }
}
