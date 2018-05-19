package spbau.eliseeva.XUnit;

import javafx.application.Platform;
import spbau.eliseeva.XUnit.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** The main class for running tests using annotations.*/
public class Main {
    /**
     * Takes directory name and class name, loads the class and runs all test
     * methods with before/after/beforeClass/afterClass methods. Prints results.
     * @param args ignored
     */
    public static void main(String[] args) {
        System.out.println("Write directory name:");
        Scanner scanner = new Scanner(System.in);
        String directory = scanner.nextLine();
        System.out.println("Write class name:");
        String className = scanner.nextLine();
        Class<?> clazz;
        try {
            clazz = (new URLClassLoader(new URL[]{Paths.get(directory).toUri().toURL()})).loadClass(className);
        } catch (ClassNotFoundException | MalformedURLException e) {
            e.printStackTrace();
            return;
        }
        List<Method> testMethods = getMethods(clazz, Test.class);
        List<Method> beforeMethods = getMethods(clazz, Before.class);
        List<Method> afterMethods = getMethods(clazz, After.class);
        List<Method> beforeClassMethods = getMethods(clazz, BeforeClass.class);
        List<Method> afterClassMethods = getMethods(clazz, AfterClass.class);
        if (beforeClassMethods.size() > 1) {
            System.out.println("Too many BeforeClass methods.");
            return;
        }
        if (afterClassMethods.size() > 1) {
            System.out.println("Too many AfterClass methods.");
            return;
        }
        if (beforeMethods.size() > 1) {
            System.out.println("Too many Before methods.");
            return;
        }
        if (afterMethods.size() > 1) {
            System.out.println("Too many After methods.");
            return;
        }

        List<Report> reports = new ArrayList<>();
        Object instance;
        try {
            instance = clazz.newInstance();
            for (Method beforeClassMethod : beforeClassMethods) {
                beforeClassMethod.invoke(instance);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.err.println("Problems with beforeClass method");
            return;
        }
        for (Method method : testMethods) {
            reports.add(runTest(clazz, method, beforeMethods, afterMethods));
        }
        try {
            instance = clazz.newInstance();
            for (Method afterClassMethod : afterClassMethods) {
                afterClassMethod.invoke(instance);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.err.println("Problems with afterClass method");
            return;
        }
        for (Report report : reports) {
            System.out.println("Test: " + report.testName);
            System.out.println("Message: "+ report.message);
            System.out.println("Time: " + report.time);
            System.out.println("Success: " + report.isSuccess);
            System.out.println();
        }
    }

    /**
     * Takes class and returns methods with needed annotation.
     * @param clazz class to find mehod
     * @param annotation method to find
     * @return list of needed methods
     */
    private static List<Method> getMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
        ArrayList<Method> methods = new ArrayList<>();
        for (Method method : clazz.getMethods()) {
            if (!Modifier.isStatic(method.getModifiers()) &&
                    Modifier.isPublic(method.getModifiers()) &&
                    method.getReturnType().equals(Void.TYPE) &&
                    method.getParameterCount() == 0 &&
                    method.getAnnotation(annotation) != null) {
                methods.add(method);
            }
        }
        return methods;
    }

    /** Class to save the information about the results*/
    private static class Report {
        private String message;
        private String testName;
        private long time;
        private boolean isSuccess;
        private Report(String testName, String message, long time, boolean isSucess) {
            this.testName = testName;
            this.message = message;
            this.time = time;
            this.isSuccess = isSucess;
        }
    }

    /**
     * Runts test method in the class.
     * @param clazz class to run test
     * @param testMethod test to run
     * @param beforeMethods methods to run before
     * @param afterMethods methods to run after
     * @return Report object with results
     */
    private static Report runTest(Class clazz, Method testMethod, List<Method> beforeMethods, List<Method> afterMethods) {
        Test testAnnotation = testMethod.getAnnotation(Test.class);
        if (!testAnnotation.ignore().equals("")) {
            return new Report(testMethod.getName(), "Ignored: " + testAnnotation.ignore(), 0, true);
        }
        Object instance = null;
        Exception testException = null;
        try {
            instance = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        long timeBegin = System.currentTimeMillis();
        try {
            for (Method beforeMethod : beforeMethods) {
                beforeMethod.invoke(instance);
            }
            testMethod.invoke(instance);
            for (Method afterMethod : afterMethods) {
                afterMethod.invoke(instance);
            }
        } catch (IllegalAccessException e) {
            System.err.println("Problems with before, test or after method.");
            Platform.exit();
        } catch (InvocationTargetException e) {
            testException = (Exception) e.getCause();
        }
        long time = System.currentTimeMillis() - timeBegin;
        if (testException == null && !testAnnotation.expected().equals(Test.Empty.class)) {
            return new Report(testMethod.getName(), "No exception, was waited: " + testAnnotation.expected().getName(), time, false);
        }
        if (testException != null && !testAnnotation.expected().equals(testException.getClass())) {
            return new Report(testMethod.getName(), "Exception: " + testAnnotation.expected().getName(), time, false);
        }
        return new Report(testMethod.getName(), "Done", time, true);
    }
}
