package com.company.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import com.company.model.ChatClient;
import com.company.view.ChatWindow;

public class InActionListener implements ActionListener {
    protected ChatClient chatClient;
    protected ChatWindow chatWindow;

    public InActionListener(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            chatClient.outputStream.writeUTF(chatWindow.inTextField.getText());
            chatClient.outputStream.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        chatWindow.inTextField.setText("");
    }
}
