package com.sai.ioc.util;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;

public class ClassUtils {

    /**
     * Filters no-bean classes with the specified classes.
     * <p>
     * A valid bean is <b>NOT</b>:
     * <ul>
     * <li>an annotation</li>
     * <li>interface</li>
     * <li>abstract</li>
     * </ul>
     *
     * @param classes the specified classes to filter
     */
    public static void filterClasses(final Collection<Class<?>> classes) {
        final Iterator<Class<?>> iterator = classes.iterator();
        while (iterator.hasNext()) {
            final Class<?> clazz = iterator.next();
            if (clazz.isAnnotation() || !isConcrete(clazz)) {
                iterator.remove();
            }
        }
    }

    public static boolean isConcrete(final Class<?> clazz) {
        final int modifiers = clazz.getModifiers();

        return !Modifier.isAbstract(modifiers) && !Modifier.isInterface(modifiers);
    }
}
