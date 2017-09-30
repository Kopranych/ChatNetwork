package com.company;

import com.company.model.ChatClientApp;

import java.io.*;
import java.net.Socket;

public class MainClient {

    public static void main(String[] args) throws IOException {
        ChatClientApp chatClient;
        InputStream dis;
        OutputStream dos;

//        if(args.length!=2){
//            throw   new RuntimeException("Введите: <адресс сервера> <номер порта>");
//        }

            Socket socket = new Socket("127.0.0.1", 8082);
            dis = socket.getInputStream();
            dos = socket.getOutputStream();
            chatClient = new ChatClientApp("MyChat", socket, dis, dos);
            chatClient.setVisible(true);
//            try {
//                if (dos != null)
//                    dos.close();
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//            try{
//               socket.close();
//            }catch (IOException e){
//                e.printStackTrace();
//            }


    }
}
