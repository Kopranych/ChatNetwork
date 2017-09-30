package com.company.model.test;

import com.company.model.ChatHandler;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServerTest extends JFrame {
    public ChatServerTest(int port) throws IOException {

        ServerSocket service = new ServerSocket(port);
        try {

            while (true){
                Socket socket = service.accept();
                System.out.println("Подключился клиент "+ socket.getRemoteSocketAddress());
                ChatHandlerTest handler = new ChatHandlerTest(socket);
                handler.start();
            }

        }catch(IOException e){
            e.printStackTrace();
        }finally {
            service.close();
        }
    }

    public static void main(String[] args) {
        try {
            ChatServerTest superServer = new ChatServerTest(8082);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
