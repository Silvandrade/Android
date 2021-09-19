package com.silvandrade.netflixremake.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {

    private final WeakReference<ImageView> imageViewWeakReference; // Definindo referência fraca para minha ImagemView por estar manipulando assincrono.

    public ImageDownloaderTask(ImageView imageView) { // Construtor da classe recebe a referência da minha ImageView.
        this.imageViewWeakReference = new WeakReference<>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        // Fazendo a requisição Web.
        String urlImage = params[0]; // Pegando a referência da Url que vem como parâmentro.
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
