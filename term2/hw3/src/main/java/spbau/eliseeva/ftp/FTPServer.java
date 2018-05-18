package spbau.eliseeva.ftp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/** Server answering for get and list requests: getting file or list of files in the directory.*/
public class FTPServer {
    /**
     * Scans port number and starts waiting for the connection. Ends the program when enter pressed.
     * @param args ignored
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Write port number.\n");
        int portNumber = Integer.parseInt(scanner.nextLine());
        Thread serverThread = new Thread(() -> runServer(portNumber));
        serverThread.setDaemon(true);
        serverThread.start();
        System.out.println("Press enter to end.\n");
        scanner.nextLine();
    }

    /**
     * Waits for connections and work with requests while not interrupted.
     * @param portNumber port to listen
     */
    private static void runServer(int portNumber) {
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

    /**
     * Read a request from input and do the requested action.
     * @param dataInputStream stream to read request
     * @param dataOutputStream stream to answer
     * @throws IOException thrown if problems with reading or writing, for example when connection is lost.
     */
    private static void processInput(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
            Command command = Command.values()[dataInputStream.readInt()];
            switch (command) {
                case LIST:
                    list(dataInputStream.readUTF(), dataOutputStream);
                    break;
                case GET:
                    get(dataInputStream.readUTF(), dataOutputStream);
                    break;
                case CONNECT:
                    dataOutputStream.writeUTF("connected");
                    break;
                default:
                    dataOutputStream.writeUTF("wrong command.");
            }
    }

    /**
     * Writes in the output size of file and everything inside it.
     * @param fileName file to write in the output
     * @param dataOutputStream stream to answer
     * @throws IOException thrown if problems with writing, for example when connection is lost.
     */
    private static void get(String fileName, DataOutputStream dataOutputStream) throws IOException {
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
        int c;
        byte[] buffer = new byte[1024];
        while ((c = fileInputStream.read(buffer)) == 1024) {
            size += c;
        }
        size += c;
        dataOutputStream.writeLong(size);
        fileInputStream.close();
        fileInputStream = new FileInputStream(fileName);
        while ((c = fileInputStream.read(buffer)) == 1024) {
            dataOutputStream.write(buffer, 0, c);
        }
        dataOutputStream.write(buffer, 0, c);
        fileInputStream.close();
    }

    /**
     * Writes in the output number of elements in the directory and names of all of them with noting if directory or not.
     * @param directoryName directory to find files from
     * @param dataOutputStream stream to answer
     * @throws IOException thrown if problems with writing, for example when connection is lost.
     */
    private static void list(String directoryName, DataOutputStream dataOutputStream) throws IOException {
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
