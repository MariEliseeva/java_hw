package spbau.eliseeva.XUnit.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** An annotation for test methods.*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
    /**
     * @return class which is expected to be thrown.
     */
    Class expected() default Empty.class;

    /**
     * @return the reason to ignore the test.
     */
    String ignore() default "";

    /**
     * An empty exception, used for default value for expected.
     */
    class Empty extends Exception {
    }
}