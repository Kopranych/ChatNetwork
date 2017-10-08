package com.company.view.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatWindowTest extends JFrame {

    public JTextArea outTextArea;
    public JTextField inTextField;
    protected JTextField textIP;
    protected JTextField textPort;
    protected JLabel labelIP;
    protected JLabel labelPort;
    protected JPanel panelOutput;
    protected JPanel panelIPadress;
    protected JButton buttonSend;
    private JPanel upPanel;
    private JPanel login;
    private JLabel name;
    private JTextField enterName;
    private JTextArea nameArea;

    private DataOutputStream dataOutputStream;
    protected Socket socket;
    public static boolean isOn;
    public static String adressIP; //= "127.0.0.1";
    public static int port; //= 8082;
    public static volatile boolean isGetIP = false;
    public static volatile boolean isGetPotr = false;
    private String nameLogin;

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

        name = new JLabel("LogIn");
        login = new JPanel(new GridLayout(1, 3));
        login.add(name);
        login.add(nameArea = new JTextArea());
        nameArea.setEditable(false);
        login.add(enterName = new JTextField());

        upPanel = new JPanel(new GridLayout(2, 1));
        upPanel.add(panelIPadress);
        upPanel.add(login);
        container.add(BorderLayout.NORTH, upPanel);

        textIP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adressIP = textIP.getText();
                if (adressIP.equals("127.0.0.1"))
                    isGetIP = true;
                textIP.setText("");
            }
        });

        textPort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                port = Integer.parseInt(textPort.getText());
                if (port == 8082)
                    isGetPotr = true;
                textPort.setText("");
            }
        });


        buttonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (nameLogin == null) {
                        outTextArea.append("Ввеите свое имя \n");
                        outTextArea.setCaretPosition(outTextArea.getDocument().getLength());
                    } else {
                        dataOutputStream.writeUTF(nameLogin + " : " + inTextField.getText());
                        dataOutputStream.flush();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                    isOn = false;
                }catch (NullPointerException e2){
                    if(dataOutputStream == null){
                        outTextArea.append("Установите соединение \n");
                        outTextArea.setCaretPosition(outTextArea.getDocument().getLength());
                    }
                }
                inTextField.setText("");
            }
        });

        enterName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameLogin = enterName.getText();
                enterName.setText("");
                nameArea.setText(nameLogin);
            }
        });

        inTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (nameLogin == null) {
                        outTextArea.append("Ввеите свое имя \n");
                        outTextArea.setCaretPosition(outTextArea.getDocument().getLength());
                    } else {
                        dataOutputStream.writeUTF(nameLogin + " : " + inTextField.getText());
                        dataOutputStream.flush();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                    isOn = false;
                }catch (NullPointerException e2){
                    if(dataOutputStream == null){
                        outTextArea.append("Установите соединение \n");
                        outTextArea.setCaretPosition(outTextArea.getDocument().getLength());
                    }
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
