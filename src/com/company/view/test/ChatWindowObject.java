package com.company.view.test;

import com.company.model.test.ChatClientObject;
import com.company.model.test.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class ChatWindowObject extends ChatWindowTest{
    private JPanel upPanel;
    private JPanel login;
    private JLabel name;
    private JTextField enterName;
    private ChatClientObject clientObject;

    public void setClientObject(ChatClientObject clientObject) {
        this.clientObject = clientObject;
    }

    public ChatWindowObject(String title ) {
        super(title);
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(BorderLayout.CENTER, outTextArea = new JTextArea());
        outTextArea.setEditable(false);

        panelOutput = new JPanel();
        panelOutput.setLayout(new GridLayout(1, 1));
        panelOutput.add(inTextField = new JTextField());
        panelOutput.add(buttonSend = new JButton("Send"));
        container.add(BorderLayout.SOUTH, panelOutput);

        labelIP = new JLabel("IPadress");
        labelPort = new JLabel("Port");
        panelIPadress = new JPanel();
        panelIPadress.setLayout(new GridLayout(1, 4));
        panelIPadress.add(labelIP);
        panelIPadress.add(textIP = new JTextField());
        panelIPadress.add(labelPort);
        panelIPadress.add(textPort = new JTextField());

        name = new JLabel("LogIn");
        login = new JPanel(new GridLayout(1,2));
        login.add(name);
        login.add(enterName = new JTextField());

        upPanel = new JPanel(new GridLayout(2,1));
        upPanel.add(panelIPadress);
        upPanel.add(login);
        container.add(BorderLayout.NORTH, upPanel);

        enterName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientObject.getMessage().setLogin(enterName.getText());
                enterName.setText("");
            }
        });

        textIP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adressIP = textIP.getText();
                if(adressIP.equals("127.0.0.1"))
                    isGetIP = true;
                textIP.setText("");
            }
        });
        textPort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                port = Integer.parseInt(textPort.getText());
                if(port == 8082)
                    isGetPotr = true;
                textPort.setText("");
            }
        });


        buttonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientObject.getMessage().setMessage(inTextField.getText());
                    clientObject.getOutputStream().writeObject(clientObject.getMessage());
                    clientObject.getOutputStream().flush();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    isOn = false;
                }
                inTextField.setText("");
            }
        });

        inTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientObject.getMessage().setMessage(inTextField.getText());
                    clientObject.getOutputStream().writeObject(clientObject.getMessage());
                    clientObject.getOutputStream().flush();
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
                    clientObject.getOutputStream().close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                super.windowClosed(e);
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(400, 500);
        inTextField.requestFocus();

    }
}
