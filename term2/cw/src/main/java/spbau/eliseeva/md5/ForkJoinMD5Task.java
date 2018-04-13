package spbau.eliseeva.md5;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ForkJoinMD5Task extends RecursiveTask<Byte []> {
    private final File file;
    private final MessageDigest messageDigest;
    public ForkJoinMD5Task(File file) throws NoSuchAlgorithmException {
        this.file = file;
        messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
    }
    @Override
    protected Byte[] compute() {
        byte[] bytes = new byte[0];
        if (file.isDirectory()) {
            messageDigest.update(file.getName().getBytes());
            List<ForkJoinMD5Task> tasksList = new ArrayList<>();
            for (File element : file.listFiles()) {
                ForkJoinMD5Task task = null;
                try {
                    task = new ForkJoinMD5Task(element);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                task.fork();
                tasksList.add(task);
            }
            for (ForkJoinMD5Task task : tasksList) {
                Byte[] byteObjects = task.join();
                byte[] newBytes = new byte[byteObjects.length];
                int j = 0;
                for(Byte b: byteObjects) {
                    newBytes[j++] = b;
                }
                messageDigest.update(newBytes);
            }
            bytes = messageDigest.digest();
        } else {
            try {
                bytes = OneThreadMD5.calculateOneThread(file);
            } catch (IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        Byte[] byteObjects = new Byte[bytes.length];
        int i = 0;
        for(byte b: bytes) {
            byteObjects[i++] = b;
        }
        return byteObjects;
    }
}