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
                Thread thread = new Thread(() -> {
                    try {
                        DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                        processInput(in, out);
                        out.flush();
                    } catch (IOException e) {
                        System.err.println("Problems with connection or reading and writing.");
                    }
                });
                thread.setDaemon(true);
                thread.start();
            }
        } catch (IOException ignored) {
        }
    }

    private void processInput(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
            int command = dataInputStream.readInt();
            switch (command) {
                case 1:
                    list(dataInputStream.readUTF(), dataOutputStream);
                case 2:
                    get(dataInputStream.readUTF(), dataOutputStream);
                case 17:
                    dataOutputStream.writeUTF("connected");
                default:
                    dataOutputStream.writeUTF("wrong command.");
            }
    }

    private void get(String fileName, DataOutputStream dataOutputStream) throws IOException {
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            dataOutputStream.writeLong(0);
            return;
        }
        if ((new File(fileName)).isDirectory()) {
            dataOutputStream.writeLong(0);
            return;
        }
        int size = 0;
        int length;
        byte[] buffer = new byte[1024];
        while ((length = fileInputStream.read(buffer)) != -1) {
            size += length;
        }
        dataOutputStream.writeLong(size);
        fileInputStream.close();
        fileInputStream = new FileInputStream(fileName);
        int c;
        while ((c = fileInputStream.read(buffer)) != -1) {
            dataOutputStream.write(buffer, 0, c);
        }
        fileInputStream.close();
    }

    private void list(String directoryName, DataOutputStream dataOutputStream) throws IOException {
        File dir = new File(directoryName);
        if (!dir.isDirectory()) {
            dataOutputStream.writeInt(0);
            return;
        }
        dataOutputStream.writeInt(dir.listFiles().length);
        for (File file : dir.listFiles()) {
            dataOutputStream.writeUTF(file.getName());
            dataOutputStream.writeBoolean(file.isDirectory());
        }
    }
}
