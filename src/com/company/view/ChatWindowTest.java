package com.company.view;

import com.company.model.test.ChatClientTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatWindowTest extends JFrame {

    public JTextArea outTextArea;
    public JTextField inTextField;
    protected JPanel panelOutput;
    protected JButton buttonSend;
    protected DataOutputStream dataOutputStream;
    protected Socket socket;
    public static boolean isOn;



    public ChatWindowTest(String title, DataOutputStream dos, Socket socket) {
        super(title);
        dataOutputStream = dos;
        this.socket = socket;
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(BorderLayout.CENTER, outTextArea = new JTextArea());
        outTextArea.setEditable(false);
        panelOutput = new JPanel();
        panelOutput.setLayout(new GridLayout(1,1));
        panelOutput.add(inTextField = new JTextField());
        panelOutput.add(buttonSend = new JButton("Send"));
        container.add(BorderLayout.SOUTH, panelOutput);

        buttonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dataOutputStream.writeUTF(inTextField.getText());
                    dataOutputStream.flush();
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
                    dataOutputStream.writeUTF(inTextField.getText());
                    dataOutputStream.flush();
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
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                super.windowClosed(e);
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(400,500);
        inTextField.requestFocus();

    }

}
