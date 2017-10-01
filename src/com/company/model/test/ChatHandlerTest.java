package com.company.model.test;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ChatHandlerTest extends Thread {
    protected Socket socket;
    protected DataInputStream dis;
    protected DataOutputStream dos;
    public static int countClient = 1;
    protected boolean isOn;

    protected static List<ChatHandlerTest> listHandlers = Collections.synchronizedList(new ArrayList<>());

    public ChatHandlerTest(Socket socket) throws IOException {
        this.socket = socket;
        dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    public void run() {
        isOn = true;
        if (listHandlers.size() == 2) {
            System.out.println("Превышен лимит подключения");

            try {
                dos.writeUTF("exceeded connection limit");
                dos.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            listHandlers.add(this);
            ++countClient;

            try {
                while (isOn) {
                    String massage = dis.readUTF();
                    System.out.println("получили сообщение: " + massage);
                    String validate = validateForbiddenWords(massage);

                    if (!(validate.equals("Ok"))) {
                        System.out.println("Недопустимое высказывание " + validate + "\n" +
                                "Прощайте!"
                        );
                        dos.writeUTF("Недопустимое высказывание " + validate + "\n" +
                                "Прощайте!");
                        listHandlers.remove(this);
                        countClient--;
                        dos.close();
                        socket.close();
                        isOn = false;
                    } else
                        broadcast(massage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                listHandlers.remove(this);
                countClient--;
                try {
                    dos.close();
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
        ArrayList<String> tempMassegeList = new ArrayList<String>();

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

    protected static void broadcast(String massage) {
        synchronized (listHandlers) {
            Iterator<ChatHandlerTest> iter = listHandlers.iterator();

            while (iter.hasNext()) {
                ChatHandlerTest client = iter.next();
                try {
                    synchronized (client.dos) {
                        client.dos.writeUTF(massage);
                    }
                    client.dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    client.isOn = false;
                }
            }
        }
    }
}
