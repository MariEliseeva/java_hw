package spbau.eliseeva.md5;

import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** The class calculates MD5 code in one threaded mode.*/
public class OneThreadMD5 {
    /**
     * Calculates MD5 code. If a dir -- codes a name and go inside, if a file -- code the things inside.
     * @param file file to calculate MD5 code
     * @return MD5 code
     * @throws IOException  thrown if problems with files
     * @throws NoSuchAlgorithmException thrown if problems with MD5 algorithm
     */
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
