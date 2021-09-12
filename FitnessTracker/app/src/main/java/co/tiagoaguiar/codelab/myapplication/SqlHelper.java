package co.tiagoaguiar.codelab.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SqlHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "fitness_tracker.db";
    private static final int DB_VERSION = 1;
    private static SqlHelper INSTANCE; // Padrão Singleton. (Uma única instância de objeto)

    // Definido para criar uma única instância do objeto da classe.
    static SqlHelper getInstance(Context context) {
        if(INSTANCE == null) { // Verifica se já existe uma instância na nossa variável.
            INSTANCE = new SqlHelper(context); // Se não existir será instanciada.
        }
        return INSTANCE; // Retorna a instância.
    }

    // Construtor definido como privado para que objetos não possam ser instanciados de fora da classe.
    private SqlHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // Será executado apenas quando não hover um banco de dados criado com o nome definido.
        db.execSQL(
                "CREATE TABLE tbl_Calc(id INTEGER primary key, type_calc TEXT, result DECIMAL, created_date DATATIME)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { // Será executado quando a versão do banco for diferente.
        Log.d("teste", "on Upgrade disparado.");
    }

    public List<Register> getRegisterBy(String type) {
        List<Register> registers = new ArrayList<>(); // Variável que vai armazenar todos os registros do meu banco.

        SQLiteDatabase db = getReadableDatabase(); // Referenciando uma instância do banco de dados para leitura.
        // Me retorna um ponteiro para todas as linhas do meu select onde type_calc = type.
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_calc WHERE type_calc = ?", new String[]{ type }); // ? = type.

        try {
            if(cursor.moveToFirst()) { // Movendo o ponteiro para primeira linha do select.
                do { // Percorrendo todas as linhas do select.
                    Register register = new Register(); // Instanciando um matriz de objetos da classe que vai armazernar os dados da tabela.
                    // Capturando os registros.
                    register.setType(cursor.getString(cursor.getColumnIndex("type_calc")));
                    register.setResponse(cursor.getDouble(cursor.getColumnIndex("result")));
                    register.setCreatedDate(cursor.getString(cursor.getColumnIndex("created_date")));

                    registers.add(register); // Armazenando os registros do select na matriz.
                } while (cursor.moveToNext()); // Enquanto houver linha para se mover.
            }
        } catch (Exception e) {
            Log.e("SQLite", e.getMessage(), e); // Escrevendo mensagem de erro no log caso ocorra.
        } finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close(); // Fechando a leitura do banco.
        }

        return registers;
    }

    public long addItem(String type, double response) {
        SQLiteDatabase db = getWritableDatabase(); // Referenciando uma instância do banco de dados para escrita.

        long calcId = 0;

        try { // Código que será executado normalmente.
            db.beginTransaction(); // Abrindo a conexão do banco.

            ContentValues values = new ContentValues();
            values.put("type_calc", type);
            values.put("result", response);

            // Definindo um objeto de formatação de data.
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("pt", "BR"));
            String now = simpleDateFormat.format(new Date()); // Usando o método do objeto para salvar a data formatada na variável.
            values.put("created_date", now);

            calcId = db.insertOrThrow("tbl_calc", null, values); // Inserindo os dados na tabela ou gerando uma exceção.
            db.setTransactionSuccessful(); // Efetivando a transação.
        } catch (Exception e) { // Caso haja algum erro.
            Log.e("SQLite", e.getMessage(), e); // Lançando a mensagem de erro caso ocorra.
        } finally { // Para executar o código havendo ou não exceção.
            if(db.isOpen()) // Se o banco estiver aberto.
                db.endTransaction(); // Fechando a conexão.
        }
        return calcId;
    }
}
