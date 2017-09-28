package com.company.model;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ChatHandler extends Thread {
    protected Socket socket;
    protected DataInputStream dis;
    protected DataOutputStream dos;
    protected boolean isOn;

    protected static List<ChatHandler> listHandlers = Collections.synchronizedList(new ArrayList<>());

    public ChatHandler(Socket socket) throws IOException {
        this.socket = socket;
        dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    public void run(){
        isOn = true;
        listHandlers.add(this);
        while (isOn) {
            try {
                String message = dis.readUTF();
                broadcast(message);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                listHandlers.remove(this);
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected static void broadcast(String massage){
        synchronized (listHandlers) {
            Iterator<ChatHandler> iter = listHandlers.iterator();

            while (iter.hasNext()){
                ChatHandler c = iter.next();
                try {
                    synchronized (c.dos) {
                        c.dos.writeUTF(massage);
                    }
                    c.dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    c.isOn = false;
                }
            }
        }
    }
}
