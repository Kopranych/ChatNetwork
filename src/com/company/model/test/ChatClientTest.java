package com.company.model.test;

import com.company.view.ChatWindowTest;

import java.io.*;
import java.net.Socket;

public class ChatClientTest implements Runnable {
    public ChatWindowTest chatWindow;
    public Socket socket;
    private DataInputStream inputStream;
    public DataOutputStream outputStream;

    public void setChatWindow(ChatWindowTest chatWindow) {
        this.chatWindow = chatWindow;
    }

    public ChatClientTest(Socket s, DataInputStream dis, DataOutputStream dos) {
        socket = s;
        inputStream = dis;
        outputStream = dos;
        (new Thread(this)).start();
    }

    @Override
    public void run() {
        chatWindow.isOn = true;

        try {
            while (chatWindow.isOn) {
                String line = inputStream.readUTF();
                System.out.println("Пришло с сервера " + line);
//                if(line.equals("exceeded connection limit")){
//                    outputStream.close();
//                    socket.close();
//                    chatWindow.isOn = false;
//                    System.exit(0);
//                }
                chatWindow.outTextArea.append(line + "\n");
                chatWindow.outTextArea.setCaretPosition(chatWindow.outTextArea.getDocument().getLength());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            chatWindow.inTextField.setVisible(false);
            chatWindow.validate();
        }

    }
}
