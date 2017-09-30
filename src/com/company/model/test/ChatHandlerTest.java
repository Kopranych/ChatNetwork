package com.company.model.test;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ChatHandlerTest extends Thread {
    protected Socket socket;
    protected DataInputStream dis;
    protected DataOutputStream dos;
    protected boolean isOn;

    protected static List<ChatHandlerTest> listHandlers = Collections.synchronizedList(new ArrayList<>());

    public ChatHandlerTest(Socket socket) throws IOException {
        this.socket = socket;
        dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    public void run() {
        isOn = true;
        if (listHandlers.size() == 2) {
            System.out.println("Превышен лимит подключения");

            try {
                dos.writeUTF("exceeded connection limit");
                dos.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            listHandlers.add(this);

            try {
                while (isOn) {
                    String message = dis.readUTF();
                    System.out.println("получили сообщение: " + message);
                    broadcast(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
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

    protected static void broadcast(String massage) {
        synchronized (listHandlers) {
            Iterator<ChatHandlerTest> iter = listHandlers.iterator();

            while (iter.hasNext()) {
                ChatHandlerTest client = iter.next();
                try {
                    synchronized (client.dos) {
                        client.dos.writeUTF(massage);
                    }
                    client.dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    client.isOn = false;
                }
            }
        }
    }
}
