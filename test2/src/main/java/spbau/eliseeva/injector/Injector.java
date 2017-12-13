package spbau.eliseeva.injector;

import java.lang.reflect.Constructor;
import java.util.*;

public class Injector {
    public static Object initialize(String rootClassName, List<String> implementationNames)
            throws Exception {
        ArrayList<Class<?>> implementations = new ArrayList<>();
        for (String elem : implementationNames) {
            implementations.add(Class.forName(elem));
        }
        Class<?> rootClass = Class.forName(rootClassName);
        classes = implementations;
        classes.add(rootClass);
        visited = new HashMap<>();
        way = new HashSet<>();
        return initialize(rootClass);
    }

    private static HashMap<Class<?>, Object> visited;
    private static HashSet<Class<?>> way;
    private static ArrayList<Class<?>> classes;

    private static Object initialize(Class<?> currentClass) throws Exception {
        if (visited.containsKey(currentClass)) {
            return visited.get(currentClass);
        }
        if (way.contains(currentClass)) {
            throw new InjectionCycleException();
        }
        way.add(currentClass);
        Constructor<?> constructor = currentClass.getConstructors()[0];
        Class<?>[] parameters = constructor.getParameterTypes();
        Object[] parametersObjects = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            Class<?> parameter = getImplementation(parameters[i]);
            parametersObjects[i] = initialize(parameter);
        }
        Object result  = constructor.newInstance(parametersObjects);
        way.remove(currentClass);
        visited.put(currentClass, result);
        return result;
    }

    private static Class<?> getImplementation(Class<?> class1) throws Exception {
        Class<?> answer = null;
        for (Class<?> elem : classes) {
            if (elem.equals(class1)) {
                if (answer == null) {
                    answer = elem;
                } else {
                    throw new AmbiguousImplementationException();
                }
            } else if (class1.isInterface()) {
                if (isImplements(class1, elem)) {
                    if (answer == null) {
                        answer = elem;
                    } else {
                        throw new AmbiguousImplementationException();
                    }
                }
            } else if (isExtends(class1, elem)) {
                if (answer == null) {
                    answer = elem;
                } else {
                    throw new AmbiguousImplementationException();
                }
            }
        }
        if (answer == null) {
            throw new ImplementationNotFoundException();
        }
        return answer;
    }

    private static boolean isExtends(Class<?> extClass, Class<?> class1) {
        while (class1 != null) {
            if (class1.equals(extClass)) {
                return true;
            }
            class1 = class1.getSuperclass();
        }
        return false;
    }

    private static boolean isImplements(Class<?> implInterface, Class<?> class1) {
        while (class1 != null) {
            for (Class<?> elem : class1.getInterfaces()) {
                if (implInterface.equals(elem) || isImplements(implInterface, elem)) {
                    return true;
                }
            }
            class1 = class1.getSuperclass();
        }
        return false;
    }
}
