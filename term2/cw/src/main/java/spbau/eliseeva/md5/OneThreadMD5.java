package spbau.eliseeva.md5;

import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class OneThreadMD5 {
    public static byte[] calculateOneThread(File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest messageDigest = null;
        messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        if (file.isDirectory()) {
            messageDigest.update(file.getName().getBytes());
            for (File element : file.listFiles()) {
                messageDigest.update(calculateOneThread(element));
            }
            return messageDigest.digest();
        }
        DigestInputStream digestInputStream = null;
        try {
            digestInputStream = new DigestInputStream(new FileInputStream(file.getAbsolutePath()), messageDigest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] buffer = new byte[digestInputStream.available()];
        digestInputStream.read(buffer, 0, digestInputStream.available());
        return digestInputStream.getMessageDigest().digest();
    }
}
