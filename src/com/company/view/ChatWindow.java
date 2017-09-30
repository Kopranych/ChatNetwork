package com.company.view;

import com.company.model.test.ChatClientTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;

public class ChatWindow extends JFrame {
    protected ChatClientTest chatClient;
    public JTextArea outTextArea;
    public JTextField inTextField;
    public DataOutputStream dataOutputStream;
    public static boolean isOn;

    public void setChatClient(ChatClientTest chatClient) {
        this.chatClient = chatClient;
    }

    public ChatWindow(String title, DataOutputStream dos) {
        super(title);
        dataOutputStream = dos;
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(BorderLayout.CENTER, outTextArea = new JTextArea());
        outTextArea.setEditable(false);
        container.add(BorderLayout.SOUTH, inTextField = new JTextField());

        inTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    chatClient.outputStream.writeUTF(inTextField.getText());
                    chatClient.outputStream.flush();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    isOn = false;
                }
                inTextField.setText("");
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                isOn = false;
                try {
                    dataOutputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    chatClient.socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                super.windowClosed(e);
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,500);
        inTextField.requestFocus();

    }

}
