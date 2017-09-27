package com.company.view;

import com.company.control.InTextFieldActionListener;
import com.company.model.ChatClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;

public class ChatWindow extends JFrame {
    protected ChatClient chatClient;
    public JTextArea outTextArea;
    public JTextField inTextField;
    public static boolean isOn;

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public ChatWindow(String title, DataOutputStream dos) {
        super(title);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(BorderLayout.CENTER, outTextArea = new JTextArea());
        outTextArea.setEditable(false);
        container.add(BorderLayout.SOUTH, inTextField = new JTextField());

        inTextField.addActionListener(new InTextFieldActionListener(chatClient));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                isOn = false;
                try {
                    dos.close();
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
        setVisible(true);
    }

}
