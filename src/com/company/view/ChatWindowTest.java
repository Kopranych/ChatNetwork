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
    private JTextField textIP;
    private JTextField textPort;
    private JLabel labelIP;
    private JLabel labelPort;
    protected JPanel panelOutput;
    protected JPanel panelIPadress;
    protected JButton buttonSend;
    protected DataOutputStream dataOutputStream;
    protected Socket socket;
    public static boolean isOn;
    public static String adressIP = "127.0.0.1";
    public static int port = 8082;
    public static boolean isGetIP = false;
    public static boolean isGetPotr = false;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public ChatWindowTest(String title) {
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
        container.add(BorderLayout.NORTH, panelIPadress);

        textIP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adressIP = textIP.getText();
                isGetIP = true;
                textIP.setText("");
            }
        });
        textPort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                port = Integer.parseInt(textPort.getText());
                isGetPotr = true;
                textPort.setText("");
            }
        });


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
        setSize(400, 500);
        inTextField.requestFocus();

    }

}
