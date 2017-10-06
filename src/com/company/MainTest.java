package com.company;

import com.company.control.InTextFieldActionListener;
import com.company.model.test.ChatClientTest;
import com.company.view.ChatWindowTest;

import java.io.*;
import java.net.Socket;

public class MainTest {

    public static void main(String[] args) throws IOException {
        ChatClientTest chatClient;
        ChatWindowTest chatWindow;
        DataInputStream dis;
        DataOutputStream dos;
        InTextFieldActionListener inTextListener;

//      if(args.length!=2){
//          throw   new RuntimeException("Введите: <адресс сервера> <номер порта>");
//      }
        chatWindow = new ChatWindowTest("MyChat");
        chatWindow.setVisible(true);
        while(!chatWindow.isGetPotr);
        System.out.println("Connected");
        Socket socket = new Socket(chatWindow.adressIP, chatWindow.port);
        dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        chatWindow.setSocket(socket);
        chatWindow.setDataOutputStream(dos);
        chatClient = new ChatClientTest(socket, dis, dos);

        chatClient.setChatWindow(chatWindow);


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

