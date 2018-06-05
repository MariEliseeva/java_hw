package spbau.eliseeva.ftp;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/** Client for getting file or list of them from server.*/
public class FTPClient {
    /**
     * Scans host name and port number and connects. Starts communication with the server.
     * @param args ignored
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Write host name.\n");
        String hostName = scanner.nextLine();
        System.out.print("Write port number.\n");
        int portNumber = Integer.parseInt(scanner.nextLine());
        try {
            checkConnection(hostName, portNumber);
            communicateWithServer(scanner, hostName, portNumber);
        } catch (IOException e) {
            System.err.println("Problems with connection or reading and writing.");
        }
    }

    /**
     * While user has not printed "exit" waits for new command and sends requests to server.
     * @param scanner System.in^ to read from
     * @param hostName name of host
     * @param portNumber port to connect
     * @throws IOException thrown if problems with reading or writing, for example when connection is lost.
     */
    private static void communicateWithServer(Scanner scanner, String hostName, int portNumber) throws IOException {
        String[] fromUser = scanner.nextLine().split(" ");
        while (!fromUser[0].equals("exit")) {
            if (!fromUser[0].equals("list") && !fromUser[0].equals("get") || fromUser.length != 2) {
                System.out.println("Wrong command.");
                fromUser = scanner.nextLine().split(" ");
                continue;
            }
            Socket socket = new Socket(InetAddress.getByName(hostName), portNumber);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            if (fromUser[0].equals("list")) {
                out.writeInt(Command.LIST.ordinal());
                out.writeUTF(fromUser[1]);
                out.flush();
                listAnswer(in);
            } else {
                out.writeInt(Command.GET.ordinal());
                out.writeUTF(fromUser[1]);
                out.flush();
                getAnswer(in, fromUser[1]);
            }
            System.out.println();
            fromUser = scanner.nextLine().split(" ");
        }
    }

    /**
     * Writes and answer for get request.
     * @param in input stream to read data from
     * @throws IOException thrown if problems with reading, for example when connection is lost.
     */
    private static void listAnswer(DataInputStream in) throws IOException {
        int size = in.readInt();
        if (size == 0) {
            System.out.println("No such directory or the directory is empty.");
            return;
        }
        System.out.print(size);
        System.out.print(' ');
        for (int i = 0; i < size; i++) {
            System.out.print('(' + in.readUTF() + ' ');
            System.out.print(in.readBoolean());
            System.out.print(')');
        }
    }

    /**
     * Writes and answer for list request.
     * @param in input stream to read data from
     * @throws IOException thrown if problems with reading, for example when connection is lost.
     */
    private static void getAnswer(DataInputStream in, String fileName) throws IOException {
        long size = in.readLong();
        if (size == 0) {
            System.out.println("File does not exist, empty or file is directory.");
            return;
        }
        System.out.print(size);
        File file = new File("results/" + fileName);
        file.getParentFile().mkdirs();
        OutputStream outputStream = new FileOutputStream(file);
        int c;
        byte[] buffer = new byte[1024];
        while ((c = in.read(buffer)) == 1024) {
            outputStream.write(buffer, 0, c);
        }
        outputStream.write(buffer, 0, c);
        outputStream.close();
    }

    /**
     * Checks that connection exists, should write "connected" on the screen.
     * @param hostName name of host
     * @param portNumber port to connect
     * @throws IOException thrown if problems with reading, for example when connection is lost.
     */
    private static void checkConnection(String hostName, int portNumber) throws IOException {
        Socket socket = new Socket(InetAddress.getByName(hostName), portNumber);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeInt(Command.CONNECT.ordinal());
        out.flush();
        System.out.println(in.readUTF());
    }
}
