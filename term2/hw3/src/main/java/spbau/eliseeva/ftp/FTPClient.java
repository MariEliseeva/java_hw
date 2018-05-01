package spbau.eliseeva.ftp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class FTPClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Write host name.\n");
        String hostName = scanner.nextLine();
        System.out.print("Write port number.\n");
        int portNumber = Integer.parseInt(scanner.nextLine());

        Socket socket = null;
        String fromUser = scanner.nextLine();
        while (!fromUser.equals("exit")) {
            try {
                socket = new Socket(InetAddress.getByName(hostName), portNumber);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF(fromUser);
                out.flush();
                String fromServer = in.readUTF();
                System.out.println("Server: " + fromServer);
                fromUser = (new Scanner(System.in)).nextLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
