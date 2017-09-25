package com.company.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ChatClient implements Runnable {
    public Socket socket;
    private DataInputStream inputStream;
    public DataOutputStream outputStream;

    public ChatClient(Socket s, DataInputStream dis, DataOutputStream dos){
        socket = s;
        inputStream = dis;
        outputStream = dos;

        (new Thread(this)).start();


    }
    @Override
    public void run() {

    }
}
