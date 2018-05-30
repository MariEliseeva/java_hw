package spbau.eliseeva.ftp;

import javafx.scene.control.Alert;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/** Client for getting file or list of them from server.*/
public class FTPClient {
    private int portNumber;
    private String hostName;

    /**
     * Creates new client
     * @param portNumber number of port
     * @param hostName name of host
     */
    public FTPClient(int portNumber, String hostName) {
        this.portNumber = portNumber;
        this.hostName = hostName;
    }

    /**
     * Returns answer to a list request
     * @param fromUser name of directory
     * @return result of list request
     * @throws IOException if problems with reading or writing data
     */
    public Map<String, Boolean> listAnswer(String fromUser) throws IOException {
        Socket socket = new Socket(InetAddress.getByName(hostName), portNumber);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeInt(1);
        out.writeUTF(fromUser);
        out.flush();
        int size = in.readInt();
        Map<String, Boolean> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            map.put(in.readUTF(), in.readBoolean());
        }
        return map;
    }

    /**
     * Does get request.
     * @param fromUser name of file on server
     * @param fileName name of file to save
     * @throws IOException if problems with files
     */
    public void getAnswer(String fromUser, String fileName) throws IOException {
        Socket socket = new Socket(InetAddress.getByName(hostName), portNumber);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeInt(2);
        out.writeUTF(fromUser);
        out.flush();
        long size = in.readLong();
        File folder = new File("results");
        if (!folder.exists()) {
            folder.mkdir();
        }
        File file = new File("results/" + fileName);
        OutputStream outputStream = new FileOutputStream(file);
        if (size != 0) {
            int c;
            byte[] buffer = new byte[1024];
            while ((c = in.read(buffer)) == 1024) {
                outputStream.write(buffer, 0, c);
            }
            outputStream.write(buffer, 0, c);
        }
        outputStream.flush();
        outputStream.close();
    }
}
