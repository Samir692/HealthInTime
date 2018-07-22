package com.example.android.health_in_time;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by samir692 on 11/30/17.
 */

public class Contacts {

    private int _id;
    private String _username;
    private String _nick_name;
    private byte[] _password;
    private byte[] _salt;
    private String _email;

    public Contacts(){//String _username,  String _nick_name,  String _password, String _email){
        /*
        this._username = _username;
        this._password = _password;
        this._email = _email;
        this._nick_name = _nick_name;
        */
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_nick_name() {
        return _nick_name;
    }

    public void set_nick_name(String _nick_name) {
        this._nick_name = _nick_name;
    }

    public byte[] get_password() {
        return _password;
    }

    public void set_password(byte[] _password) {
        this._password = _password;
    }

    public byte[] get_salt() {
        return _salt;
    }

    public void set_salt(byte[] _salt) {
        this._salt = _salt;
    }

    /*
            public String get_salt_str(){
                System.out.println("GET_SALT_STORE STRING = " + _salt);
                return _salt;
            }

            public byte[] get_salt_byte() {
                byte[] byte_salt = new byte[0];
                try {
                    byte_salt = _salt.getBytes();
                } catch (Exception e) {
                    return byte_salt;
                    //e.printStackTrace();
                }
                return byte_salt;
            }

            public void set_salt_byte(byte[] _salt) {

                try {
                    String saltAsString =  Base64.encodeToString(_salt, Base64.NO_WRAP);;

                    System.out.println("SET_Salt_byte received = "+ _salt);
                    System.out.println("SET_Salt_byte CONVERTED received = " + saltAsString);
                    System.out.println("SET_Salt_byte CONVERTED received CHECKEDDD BACK = "
                            + Base64.decode(saltAsString, Base64.NO_WRAP));
                    this._salt = saltAsString;
                } catch (Exception e) {
                    //e.printStackTrace();
                }

            }

            public void set_salt_str(String _salt){
                this._salt = _salt;
            }
        */
    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }
/*
    public byte[] get_password_byte() {
        byte[] byte_pass = new byte[0];

        try {
            byte_pass = _password.getBytes("UTF-8");
        } catch (Exception e) {
            return byte_pass;
            //e.printStackTrace();
        }
        return byte_pass;
    }

    public String get_password_str(){
        return _password;
    }

    public void set_password_byte(byte[] _password) {

        try {
            String passAsString = new String(_password,"UTF-8");
            this._password = passAsString;
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
        }
    }

    public void set_password_str(String _password){
        this._password = _password;
    }
*/
    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }
}
