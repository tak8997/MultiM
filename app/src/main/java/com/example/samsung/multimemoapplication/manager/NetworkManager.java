package com.example.samsung.multimemoapplication.manager;

/**
 * Created by Tak on 2017. 2. 21..
 */

public class NetworkManager {
    private static NetworkManager instance;
    public static NetworkManager getInstance() {
        if(instance == null)
            instance = new NetworkManager();

        return instance;
    }

//    public void login(String id, String password, ) {
//
//    }
}
