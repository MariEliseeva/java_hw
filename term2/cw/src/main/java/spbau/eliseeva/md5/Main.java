package spbau.eliseeva.md5;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String [] args) throws NoSuchAlgorithmException, IOException {
        Scanner scanner = new Scanner(System.in);
        String fileName;
        System.out.println("Write a name of file: ");
        fileName = scanner.nextLine();
        long t = System.currentTimeMillis();
        System.out.println("ForkJoin result:");
        System.out.println(Arrays.toString(new ForkJoinPool(4).invoke(new ForkJoinMD5Task(new File(fileName)))));
        System.out.print("ForkJoin time: ");
        System.out.println(System.currentTimeMillis() - t);
        t = System.currentTimeMillis();
        System.out.println("One thread result:");
        System.out.println(Arrays.toString(OneThreadMD5.calculateOneThread(new File(fileName))));
        System.out.print("One thread time: ");
        System.out.println(System.currentTimeMillis() - t);
    }
}
