package spbau.eliseeva.XUnit;

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

public class Main {
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

        List<Report> reports = new ArrayList<>();
        Object instance = null;
        try {
            instance = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            for (Method beforeClassMethod : beforeClassMethods) {
                beforeClassMethod.invoke(instance);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        for (Method method : testMethods) {
            reports.add(runTest(clazz, method, beforeMethods, afterMethods));
        }
        try {
            instance = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            for (Method afterClassMethod : afterClassMethods) {
                afterClassMethod.invoke(instance);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        for (Report report : reports) {
            System.out.println("Test: " + report.testName);
            System.out.println("Message: "+ report.message);
            System.out.println("Time: " + report.time);
            System.out.println("Success: " + report.isSuccess);
            System.out.println();
        }
    }

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
            e.printStackTrace();
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
