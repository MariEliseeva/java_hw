package spbau.eliseeva.ftp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class FTPClient {
    public static void main(String[] args) {
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
                Socket socket = new Socket(hostName, portNumber);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                Scanner scanner = new Scanner(System.in)

        ) {
            String fromServer;
            String fromUser;

            while ((fromServer = in.readUTF()) != null) {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("Bye."))
                    break;

                fromUser = scanner.nextLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.writeUTF(fromUser);
                }
            }
        } catch (IOException e) {
        }
    }
}
