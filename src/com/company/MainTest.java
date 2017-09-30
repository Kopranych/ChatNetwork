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

        Socket socket = new Socket("127.0.0.1", 8082);
        dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        chatClient = new ChatClientTest(socket, dis, dos);
        chatWindow = new ChatWindowTest("MyChat", dos, socket);
        chatClient.setChatWindow(chatWindow);

        chatWindow.setVisible(true);
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
