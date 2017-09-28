package com.company.model;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer extends JFrame {
    public ChatServer (int port) throws IOException {

        ServerSocket service = new ServerSocket(port);
        try {

            while (true){
                Socket socket = service.accept();
                System.out.println("Подключился клиент "+ socket.getInetAddress());
                ChatHandler handler = new ChatHandler(socket);
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
            ChatServer superServer = new ChatServer(8083);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
