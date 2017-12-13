package spbau.eliseeva.injector;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/** The class has only one method, which purpose is to create
 * an object of a given class, using given implementations.*/
public class Injector {
    /**
     * Creates Class</?> objects for given class names,
     * then call a function, based of the depth first search to
     * create an object of the root class.
     * @param rootClassName class to create an object
     * @param implementationNames names of implementations, which we can use.
     * @return object of a given class
     */
    public static Object initialize(String rootClassName, List<String> implementationNames)
            throws ClassNotFoundException, IllegalAccessException,
            AmbiguousImplementationException, ImplementationNotFoundException,
            InstantiationException, InjectionCycleException, InvocationTargetException {
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

    /** Classes, for which we already have an object.*/
    private static HashMap<Class<?>, Object> visited;

    /** Classes, for which we are creating an object now. Used to detect cycles.*/
    private static HashSet<Class<?>> way;

    /** Given implementations, which we can use (and create if want to use)*/
    private static ArrayList<Class<?>> classes;

    /**
     * Creates an object of a given class, recursively calling creation
     * of objects in parameters.
     * @param currentClass class, for which we are creating object
     * @return object of the class
     */
    private static Object initialize(Class<?> currentClass)
            throws InjectionCycleException, AmbiguousImplementationException, ImplementationNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
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

    /**
     * Find an appropriate implementation for a given class/interface,
     * looking on classes, which we given to initialise.
     * @param class1 class or interface to find an implementation to
     * @return class which implements given
     */
    private static Class<?> getImplementation(Class<?> class1)
            throws AmbiguousImplementationException, ImplementationNotFoundException {
        Class<?> answer = null;
        if (class1.isInterface()) {
            for (Class<?> elem : classes) {
                if (isImplements(class1, elem)) {
                    if (answer == null) {
                        answer = elem;
                    } else {
                        throw new AmbiguousImplementationException();
                    }
                }
            }
        } else {
            for (Class<?> elem : classes) {
                if (isExtends(class1, elem)) {
                    if (answer == null) {
                        answer = elem;
                    } else {
                        throw new AmbiguousImplementationException();
                    }
                }
            }
        }

        if (answer == null) {
            throw new ImplementationNotFoundException();
        }
        return answer;
    }

    /**
     * Checks if one class extends another -- go to parent until they become same.
     * @param extClass class needed to be extended
     * @param class1 class to chech
     * @return true if extends
     */
    private static boolean isExtends(Class<?> extClass, Class<?> class1) {
        while (class1 != null) {
            if (class1.equals(extClass)) {
                return true;
            }
            class1 = class1.getSuperclass();
        }
        return false;
    }


    /**
     * Checks if a class implements interface.
     * Looks on interfaces of given class, if not found -- go to parent.
     * @param implInterface interface to be implemented
     * @param class1 class to check
     * @return true if implements
     */
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
