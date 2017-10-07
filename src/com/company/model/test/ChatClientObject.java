package com.company.model.test;

import com.company.view.test.ChatWindowObject;

import java.io.*;
import java.net.Socket;

public class ChatClientObject extends ChatClientTest {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Message message;
    private static String login;
    private ChatWindowObject windowObject;

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public Message getMessage() {
        return message;
    }

    public void setWindowObject(ChatWindowObject windowObject) {
        this.windowObject = windowObject;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        ChatClientObject.login = login;
    }

    public ChatClientObject(Socket s) throws IOException {
        super(s);
        socket = s;
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        (new Thread(this)).start();
    }

    @Override
    public void run() {
        windowObject.isOn = true;
        try {
            while (windowObject.isOn) {
                message = (Message) inputStream.readObject();
                windowObject.outTextArea.append("[ " + message.getTime() + " ] [ " + message.getLogin() + " ] " + message.getMessage()+ "\n");
                windowObject.outTextArea.setCaretPosition(windowObject.outTextArea.getDocument().getLength());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            windowObject.inTextField.setVisible(false);
            windowObject.validate();
        }

    }

}
