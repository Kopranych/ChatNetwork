package com.company.model;

import java.net.Socket;

public class ChatHandler extends Thread {
    Socket socket;

    public ChatHandler(Socket socket) {
        this.socket = socket;
    }
}
