package de.thro.inf;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer extends ServerSocket implements Runnable {

    public EchoServer(int port) throws IOException {
        super(port);
    }

    public static void main(String[] args) throws Exception {
        EchoServer echoServer = new EchoServer(12500);
        echoServer.run();
    }

    public void run() {
        while (true) {
            try {
                Socket socket = accept();
                Thread client = new Thread(() -> {
                    try {
                        while (true) {
                            Scanner scanner = new Scanner(socket.getInputStream());
                            byte[] readBytes = (scanner.nextLine()).getBytes();
                            socket.getOutputStream().write(readBytes);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception ex) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                client.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}