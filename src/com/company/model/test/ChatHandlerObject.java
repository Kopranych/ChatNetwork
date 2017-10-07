package com.company.model.test;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ChatHandlerObject extends ChatHandlerTest {
    private Message message;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private String login;
    private String time;
    public static int countClient = 1;
    protected boolean isOn;
    protected static List<ChatHandlerObject> listClient = Collections.synchronizedList(new ArrayList<>());

    public ChatHandlerObject(Socket socket) throws IOException {
        super(socket);
        this.socket = socket;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        isOn = true;
        if (listClient.size() == 2) {
            System.out.println("Превышен лимит подключения");

            try {
                message.setMessage("exceeded connection limit");
                outputStream.writeObject(message);
                outputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            listClient.add(this);

            ++countClient;

            try {
                while (isOn) {
                    message = (Message) inputStream.readObject();
                    login = message.getLogin();
                    String message = this.message.getMessage();
                    System.out.println(this.message.getTime()+ "От клиента " + login + " получили сообщение: " + message);
                    String validate = validateForbiddenWords(message);

                    if (!(validate.equals("Ok"))) {
                        System.out.println("Недопустимое высказывание " + validate + "\n" +
                                "Прощайте!"
                        );
                        this.message.setMessage("Недопустимое высказывание " + validate + "\n" +
                                "Прощайте!");
                        outputStream.writeObject(message);
                        outputStream.close();
                        listClient.remove(this);
                        countClient--;
                        socket.close();
                        isOn = false;
                    } else
                        broadcast(this.message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                listClient.remove(this);
                countClient--;
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private String validateForbiddenWords(String massage) throws IOException {
        String[] tempMassage = massage.split(" ");

        BufferedReader readerFile = new BufferedReader(new FileReader
                ("C:\\JavaDevelopment\\Chat\\src\\com\\company\\model\\test\\forbidden_words.txt"));
        String wordFile;
        ArrayList<String> wordFileList = new ArrayList<String>();

        while ((wordFile = readerFile.readLine()) != null) {
            wordFileList.add(wordFile);
        }
        for (int i = 0; i < tempMassage.length; i++) {
            for (String s : wordFileList) {
                if (tempMassage[i].equals(s)) {
                    return tempMassage[i];
                }
            }

        }
        return "Ok";
    }

    protected static void broadcast(Message message) {
        synchronized (listClient) {
            Iterator<ChatHandlerObject> iter = listClient.iterator();

            while (iter.hasNext()) {
                ChatHandlerObject client = iter.next();
                try {
                    synchronized (client.outputStream) {
                        client.outputStream.writeObject(message);
                    }
                    client.outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    client.isOn = false;
                }
            }
        }
    }
}
