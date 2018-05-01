package spbau.eliseeva.ftp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class FTPServer {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        Thread serverThread = new Thread(() -> (new FTPServer(1025)).runServer());
        serverThread.run();
        System.out.println("Press enter to end.");
        keyboard.nextLine();
        serverThread.interrupt();
    }

    private final int portNumber;

    private FTPServer(int portNumber) {
        this.portNumber = portNumber;
    }

    private void runServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            while (!Thread.interrupted()) {
                Socket clientSocket = serverSocket.accept();
                FTPCommunication ftpCommunication = new FTPCommunication();
                Thread thread = new Thread(() -> {
                    try {
                        DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                        out.writeUTF(ftpCommunication.processInput(in.readUTF()));
                        out.flush();
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                thread.run();
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class FTPCommunication {
        String processInput(String input) {
            if (input.charAt(0) == 1) {
                return list(input.split(" ")[1]);
            }
            if (input.charAt(0) == 2) {
                return get(input.split(" ")[1]);
            }
            return null;
        }

        private String get(String fileName) {
            try {
                FileInputStream fileInputStream = new FileInputStream(fileName);
            } catch (FileNotFoundException e) {
                return "0";
            }
            String
        }

        private String list(String directoryName) {
            return input;
        }
    }
}
