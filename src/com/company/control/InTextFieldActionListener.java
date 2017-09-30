package com.company.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import com.company.model.test.ChatClientTest;
import com.company.view.ChatWindowTest;

public class InTextFieldActionListener implements ActionListener {
    protected ChatClientTest chatClient;
    protected ChatWindowTest chatWindow;


    public InTextFieldActionListener(ChatClientTest chatClient, ChatWindowTest chatWindow) {
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
