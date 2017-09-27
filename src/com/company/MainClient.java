package com.company;

import com.company.model.ChatClient;
import com.company.view.ChatWindow;

import java.io.*;
import java.net.Socket;

public class MainClient {

    public static void main(String[] args) {
        ChatClient chatClient;
        ChatWindow chatWindow;

        if(args.length!=2){
            throw   new RuntimeException("Введите: <адресс сервера> <номер порта>");
        }
        try {
            Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
            DataInputStream dis;
            DataOutputStream dos;
            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            chatClient = new ChatClient(socket, dis, dos);
            chatWindow = new ChatWindow("MyChat", dos);
            chatClient.setChatWindow(chatWindow);
            chatWindow.setChatClient(chatClient);
            try {
                if (dos != null)
                    dos.close();
            }catch (IOException e){
                e.printStackTrace();
            }
            try{
                socket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
