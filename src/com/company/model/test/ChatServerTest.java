package com.company.model.test;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServerTest extends JFrame {
    private JLabel labelAmountClient;
    private JTextArea textAmountClient;

    public ChatServerTest(String title, int port) throws IOException {
        super(title);
        Container container =  getContentPane();
        container.setLayout(new GridLayout(2, 1));
        container.add(labelAmountClient = new JLabel("Количество подключенных клиентов"));
        container.add(textAmountClient = new JTextArea());
        textAmountClient.setEditable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,200);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        ServerSocket service = new ServerSocket(port);
        try {

            while (true){
                Socket socket = service.accept();
                System.out.println("Подключился клиент "+ socket.getRemoteSocketAddress());
                ChatHandlerObject handler = new ChatHandlerObject(socket);
                handler.start();
                textAmountClient.setText(Integer.toString(ChatHandlerTest.countClient));
            }

        }catch(IOException e){
            e.printStackTrace();
        }finally {
            service.close();
        }
    }

    public static void main(String[] args) {
        try {
            ChatServerTest superServer = new ChatServerTest("SERVER",8082);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
