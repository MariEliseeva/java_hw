package spbau.eliseeva.ftp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class FTPServer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Write port number.\n");
        int portNumber = Integer.parseInt(scanner.nextLine());
        Thread serverThread = new Thread(() -> (new FTPServer(portNumber)).runServer());
        serverThread.setDaemon(true);
        serverThread.start();
        System.out.println("Press enter to end.\n");
        scanner.nextLine();
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
                        ftpCommunication.processInput(in, out);
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                thread.setDaemon(true);
                thread.start();
            }
        } catch (IOException ignored) {
        }
    }

    private class FTPCommunication {
        public void processInput(DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
            String input = null;
            try {
                input = dataInputStream.readUTF();
            } catch (IOException ignored) {
            }
            assert input != null;
            if (input.charAt(0) == '1') {
                try {
                    list(input.split(" ")[1], dataOutputStream);
                } catch (IOException ignored) {
                }
                return;
            }
            if (input.charAt(0) == '2') {
                try {
                    get(input.split(" ")[1], dataOutputStream);
                } catch (IOException ignored) {
                }
                return;
            }
            try {
                dataOutputStream.writeUTF("wrong command.");
            } catch (IOException ignored) {
            }
        }

        private void get(String fileName, DataOutputStream dataOutputStream) throws IOException {
            FileInputStream fileInputStream;
            try {
                fileInputStream = new FileInputStream(fileName);
            } catch (FileNotFoundException e) {
                dataOutputStream.writeUTF("0");
                return;
            }
            int size = 0;
            int length;
            byte[] buffer = new byte[1024];
            StringBuilder answer = new StringBuilder();
            while ((length = fileInputStream.read(buffer)) != -1) {
                size += length;
            }
            answer.append(String.valueOf(size));
            fileInputStream.close();
            fileInputStream = new FileInputStream(fileName);
            while (fileInputStream.read(buffer) != -1) {
                for (byte c : buffer) {
                   answer.append((char) c);
                }
            }
            dataOutputStream.writeUTF(answer.toString());
            fileInputStream.close();
        }

        private void list(String directoryName, DataOutputStream dataOutputStream) throws IOException {
            File dir = new File(directoryName);
            if (!dir.isDirectory()) {
                dataOutputStream.writeUTF("0\n");
                return;
            }
            StringBuilder answer = new StringBuilder(dir.listFiles().length + " ");
            for (File file : dir.listFiles()) {
                answer.append("(").append(file.getName()).append(" ").append(file.isDirectory()).append(")");
            }
            dataOutputStream.writeUTF(answer + "\n");
        }
    }
}
