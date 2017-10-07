package com.company;

import com.company.control.InTextFieldActionListener;
import com.company.model.test.ChatClientObject;
import com.company.model.test.ChatClientTest;
import com.company.view.test.ChatWindowObject;
import com.company.view.test.ChatWindowTest;

import java.io.*;
import java.net.Socket;

public class MainTest {

    public static void main(String[] args) throws IOException {
        ChatClientObject chatClient;
        ChatWindowObject chatWindow;

//      if(args.length!=2){
//          throw   new RuntimeException("Введите: <адресс сервера> <номер порта>");
//      }
        chatWindow = new ChatWindowObject("MyChat");
        chatWindow.setVisible(true);
        while(!chatWindow.isGetPotr);
        System.out.println("Connected");
        Socket socket = new Socket(chatWindow.adressIP, chatWindow.port);
        chatClient = new ChatClientObject(socket);
        chatClient.setWindowObject(chatWindow);
        chatWindow.setClientObject(chatClient);
        chatWindow.setSocket(socket);

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

