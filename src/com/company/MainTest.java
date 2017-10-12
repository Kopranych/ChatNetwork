package com.company;

import com.company.control.InTextFieldActionListener;
import com.company.model.test.ChatClientObject;
import com.company.model.test.ChatClientTest;
import com.company.view.test.ChatWindowObject;
import com.company.view.test.ChatWindowTest;
import com.jtattoo.plaf.texture.TextureLookAndFeel;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class MainTest {

    public static void main(String[] args) throws IOException {
        try {
            UIManager.setLookAndFeel(new TextureLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        ChatClientTest chatClient;
        ChatWindowTest chatWindow;

        chatWindow = new ChatWindowTest("MyChat");
        chatWindow.setVisible(true);
        while(!chatWindow.isGetPotr);
        Socket socket = new Socket(chatWindow.adressIP, chatWindow.port);
        System.out.println("Connected");
        chatClient = new ChatClientTest(socket);
        chatClient.setChatWindow(chatWindow);
        chatWindow.setDataOutputStream(new DataOutputStream(new BufferedOutputStream(socket.getOutputStream())));
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

