package com.silvandrade.instagramremakejava.common.model;

import android.os.Handler;

import java.util.HashSet;
import java.util.Set;

public class Database {

    private static Set<UserAuth> usersAuth;
    private static Database INSTANCE;
    private UserAuth userAuth;

    private static Set<User> users;

    private OnSuccessListener onSuccessListener;
    private OnFailureListener onFailureListener;
    private OnCompleteListener onCompleteListener;

    private Database() {}

    static { // Criando dados falso para simular um banco de dados.
        usersAuth = new HashSet<>();
        users = new HashSet<>();

//        userAuths.add(new UserAuth("user1@gmail.com", "12345"));
//        userAuths.add(new UserAuth("user2@gmail.com", "23456"));
//        userAuths.add(new UserAuth("user3@gmail.com", "34567"));
//        userAuths.add(new UserAuth("user4@gmail.com", "45678"));
//        userAuths.add(new UserAuth("user5@gmail.com", "56789"));
//        userAuths.add(new UserAuth("user6@gmail.com", "67890"));
    }

    public Database createUser(String name, String email, String password) {
        timeout(() -> {
            UserAuth userAuth = new UserAuth(email, password);
            usersAuth.add(userAuth);

            User user = new User(email, name);
            boolean added = users.add(user);

            if(added) {
                this.userAuth = userAuth;
                onSuccessListener.onSuccess(userAuth);
            } else {
                this.userAuth = null;
                onFailureListener.onFailure(new IllegalArgumentException("Usuário já existe."));
            }

            onCompleteListener.onComplete();
        });

        return this;
    }

    public static Database getInstance() { // Definindo objeto singleton para manter uma instância única do banco.
        if (INSTANCE == null) {
            INSTANCE = new Database();
        }
        return INSTANCE;
    }

    public <T> Database addOnSuccessListener(OnSuccessListener<T> listener) {
        this.onSuccessListener = listener;
        return this;
    }

    public Database addOnFailureListener(OnFailureListener listener) {
        this.onFailureListener = listener;
        return this;
    }

    public Database addOnCompleteListener(OnCompleteListener listener) {
        this.onCompleteListener = listener;
        return this;
    }

    public Database login(String email, String password) { // Simula uma consulta: SELECT User WHERE email = ? AND password = ?
        timeout(() -> {
            final UserAuth userAuth = new UserAuth(email, password);

            if(usersAuth.contains(userAuth)) {
                this.userAuth = userAuth;
                onSuccessListener.onSuccess(userAuth);
            } else {
                this.userAuth = null;
                onFailureListener.onFailure(new IllegalArgumentException("Usuário não encontrado."));
            }

            onCompleteListener.onComplete();
        });
        return this;
    }

    private void timeout(Runnable runnable) { // Simula uma atraso de latência no banco.
        new Handler().postDelayed(runnable, 2000);
    }

    public interface OnSuccessListener<T> {
        void onSuccess(T response);
    }

    public interface OnFailureListener {
        void onFailure(Exception e);
    }

    public interface OnCompleteListener {
        void onComplete();
    }
}
