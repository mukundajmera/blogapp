package com.example.blogapp;

//eger patter
public class Singleton {
    public final String CONSTANT = "This is Singleton class";
    private static Singleton instance;

    private Singleton(){}

    public static synchronized Singleton getInstance(){
        //run only for the first time double lock
        synchronized (Singleton.class){
            if(instance == null){
                instance = new Singleton();
            }
        }
        return instance;
    }
}
