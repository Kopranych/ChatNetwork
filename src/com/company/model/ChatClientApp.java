package com.company.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;

public class ChatClientApp extends JFrame implements Runnable {
    protected Socket socket;
    protected InputStream inputStream;
    protected OutputStream outputStream;
    protected BufferedReader bufferedReaderInStream;
    protected JTextArea outTextArea;
    protected JTextField inTextField;
    protected boolean isOn;


    public ChatClientApp(String title, Socket s, InputStream dis, OutputStream dos) {
        super(title);
        socket = s;
        inputStream = dis;
        outputStream = dos;
        bufferedReaderInStream = new BufferedReader(new InputStreamReader(inputStream));

        Container containerWindow = getContentPane();
        containerWindow.setLayout(new BorderLayout());
        containerWindow.add(BorderLayout.SOUTH, inTextField = new JTextField());
        containerWindow.add(BorderLayout.CENTER, outTextArea = new JTextArea());
        outTextArea.setEditable(false);

        inTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    outputStream.write((inTextField.getText() + "\n").getBytes());
                    outputStream.flush();
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
                    dos.close();
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
        setSize(400, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        inTextField.requestFocus();

        (new Thread(this)).start();
    }

    @Override
    public void run() {
        isOn = true;

        try {
            while (isOn) {
                String line = bufferedReaderInStream.readLine();
                System.out.println("Пришло с сервера " + line);
                outTextArea.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inTextField.setVisible(false);
            validate();
        }

    }
}
