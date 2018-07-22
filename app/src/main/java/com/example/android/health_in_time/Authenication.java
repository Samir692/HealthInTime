package com.example.android.health_in_time;

/**
 * Created by samir692 on 1/7/18.
 */


import android.content.Context;
import android.content.SharedPreferences;

import com.example.android.health_in_time.database_sql.DatabaseHandler;
import com.securepreferences.SecurePreferences;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Authenication {

    private final SecureRandom rand;

    private boolean constantTimeEquals(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }

        return result == 0;

    }

    public Authenication() {
        super();
        this.rand = new SecureRandom();
    }

    public boolean authenticate(Context context, String username, String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {


        DatabaseHandler dbhand = new DatabaseHandler(context);
        Contacts user = dbhand.getPassAndSalt(username);

        if (user == null || user.get_password() == null){
            System.out.println("USER IS EMPTYYYYYYYYYYYYYYY");
            return false;
        }

        char[] char_pass = password.toCharArray();

        byte[] got_salt = user.get_salt();

        if (got_salt.length <= 0){
            System.out.println("SAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAALTTTTTTTTTT");
            return false;
        }


        System.out.println("same_salt = " + got_salt);

        byte[] hash = hashPassword(char_pass, got_salt);

        //System.out.println("input_password = " + char_pass);

        System.out.println("input password hashed = " + hash);
        System.out.println("stored password hashed = " + user.get_password());

        return constantTimeEquals(hash, user.get_password());
    }

    public Contacts register(String username, String nickname, String email, char[] password)
            throws Exception {

        byte[] salt = new byte[16];
        rand.nextBytes(salt);

        byte[] hash = hashPassword(password, salt);

        System.out.println("registered password =  " +  hash);
        System.out.println("registered salt =  " +  salt);

        Contacts user = new Contacts();
        user.set_username(username);
        user.set_nick_name(nickname);
        user.set_email(email);
        user.set_password(hash);
        user.set_salt(salt);


        return user;
    }

    public byte[] hashPassword(char[] password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 50;

        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, 256);
        SecretKeyFactory skf =
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return skf.generateSecret(spec).getEncoded();
    }
}

