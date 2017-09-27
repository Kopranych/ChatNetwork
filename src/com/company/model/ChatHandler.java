package com.company.model;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatHandler extends Thread {
    protected Socket socket;
    protected DataInputStream dis;
    protected DataOutputStream dos;
    protected boolean isOn;

    protected static List<ChatHandler> listHandler = Collections.synchronizedList(new ArrayList<>());

    public ChatHandler(Socket socket) throws IOException {
        this.socket = socket;
        dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    public void run(){
        isOn = true;
        listHandler.add(this);
        while (isOn) {
            try {
                dis.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
