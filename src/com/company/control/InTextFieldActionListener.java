package com.company.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import com.company.model.test.ChatClientTest;
import com.company.view.ChatWindow;

public class InTextFieldActionListener implements ActionListener {
    protected ChatClientTest chatClient;
    protected ChatWindow chatWindow;


    public InTextFieldActionListener(ChatClientTest chatClient, ChatWindow chatWindow) {
        this.chatClient = chatClient;
        this.chatWindow = chatWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            chatClient.outputStream.writeUTF(chatWindow.inTextField.getText());
            chatClient.outputStream.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
            chatWindow.isOn = false;

        }
        chatWindow.inTextField.setText("");
    }
}
