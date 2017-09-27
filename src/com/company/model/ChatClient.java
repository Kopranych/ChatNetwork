package com.company.model;

import com.company.view.ChatWindow;

import java.io.*;
import java.net.Socket;

public class ChatClient implements Runnable {
    public ChatWindow chatWindow;
    public Socket socket;
    private DataInputStream inputStream;
    public DataOutputStream outputStream;

    public void setChatWindow(ChatWindow chatWindow) {
        this.chatWindow = chatWindow;
    }

    public ChatClient(Socket s, DataInputStream dis, DataOutputStream dos){
        socket = s;
        inputStream = dis;
        outputStream = dos;
        (new Thread(this)).start();
    }

    @Override
    public void run() {
        chatWindow.isOn = true;

        while (chatWindow.isOn){
            try {
                String line = inputStream.readUTF();
                chatWindow.outTextArea.append(line + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                chatWindow.inTextField.setVisible(false);
                chatWindow.validate();
            }
        }
    }
}
