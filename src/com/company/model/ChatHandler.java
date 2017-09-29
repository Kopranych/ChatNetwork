package com.company.model;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ChatHandler extends Thread {
    protected Socket socket;
    protected InputStream dis;
    protected OutputStream dos;
    protected BufferedReader bufferedReaderInStream;
    protected boolean isOn;

    protected static List<ChatHandler> listHandlers = Collections.synchronizedList(new ArrayList<>());

    public ChatHandler(Socket socket) throws IOException {
        this.socket = socket;
        dis = socket.getInputStream();
        bufferedReaderInStream = new BufferedReader(new InputStreamReader(dis));
        dos = socket.getOutputStream();
    }

    public void run() {
        isOn = true;
        listHandlers.add(this);

        try {
            while (isOn) {
                String message = bufferedReaderInStream.readLine();
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

    protected static void broadcast(String massage) {
        synchronized (listHandlers) {
            Iterator<ChatHandler> iter = listHandlers.iterator();

            while (iter.hasNext()) {
                ChatHandler client = iter.next();
                try {
                    synchronized (client.dos) {
                        client.dos.write((massage + "\n").getBytes());
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
