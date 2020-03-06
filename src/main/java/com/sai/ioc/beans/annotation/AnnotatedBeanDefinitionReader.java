package com.sai.ioc.beans.annotation;

import com.sai.ioc.beans.AbstractBeanDefinitionReader;
import com.sai.ioc.beans.BeanDefinition;
import com.sai.ioc.beans.BeanReference;
import com.sai.ioc.beans.PropertyValue;
import com.sai.ioc.beans.factory.AbstractBeanFactory;
import com.sai.ioc.beans.factory.BeanDefinitionRegistry;
import com.sai.ioc.context.AbstractApplicationContext;
import com.sai.ioc.util.ClassPathResolver;
import com.sai.ioc.util.ClassUtils;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Level;

import java.io.DataInputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class AnnotatedBeanDefinitionReader extends AbstractBeanDefinitionReader {

    private String scanPath;

    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry, String scanPath) {
        super(registry);
        this.scanPath = scanPath;
    }

    @Override
    public void loadBeanDefinition() throws Exception {
        discover(scanPath);
    }

    private void discover(String scanPath) throws Exception {
        if (StringUtils.isBlank(scanPath)) {
            throw new IllegalStateException("Please specify the [scanPath]");
        }

        final Set<Class<?>> ret = new LinkedHashSet<>();
        final String[] splitPaths = scanPath.split(",");

        final Set<URL> urls = new LinkedHashSet<URL>();

        for (String path : splitPaths) {
            path = path.replaceAll("\\.", "/") + "/**/*.class";
            urls.addAll(ClassPathResolver.getResources(path));
        }

        for (final URL url : urls) {
            final DataInputStream classInputStream = new DataInputStream(url.openStream());
            final ClassFile classFile = new ClassFile(classInputStream);
            final String className = classFile.getName();

            final AnnotationsAttribute annotationsAttribute = (AnnotationsAttribute) classFile.getAttribute(AnnotationsAttribute.visibleTag);
            if (null == annotationsAttribute) {
                continue;
            }

            final Annotation[] annotations = annotationsAttribute.getAnnotations();
            boolean maybeBeanClass = false;
            for (final Annotation annotation : annotations) {
                final String typeName = annotation.getTypeName();
                if (typeName.equals(Component.class.getName())) {
                    maybeBeanClass = true;
                    break;
                }
            }

            if (maybeBeanClass) {
                Class<?> clz = null;
                clz = Thread.currentThread().getContextClassLoader().loadClass(className);
                ret.add(clz);
            }
        }

        ClassUtils.filterClasses(ret);

//        ret.forEach(this::registerBeanDefinitions);
        ret.forEach(m -> {
            try {
                registerBeanDefinitions(m);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void registerBeanDefinitions(Class<?> clazz) throws Exception {

        BeanDefinition beanDefinition = new BeanDefinition();
        final String className = clazz.getName();
        final String classSimpleName = clazz.getSimpleName();
        beanDefinition.setBeanClassName(className);
        java.lang.annotation.Annotation[] annotations = clazz.getDeclaredAnnotations();
        for (java.lang.annotation.Annotation annotation : annotations) {
            if (annotation instanceof Component) {
                Object bean = clazz.newInstance();
                Component component = (Component) annotation;
                String name = component.value();
                if (name.isEmpty()) {
                    name = classSimpleName.substring(0, 1).toLowerCase() + classSimpleName.substring(1);
                }
                //TODO:bean properties
                processProperty(clazz, beanDefinition);
                getRegistry().registerBeanDefinition(name, beanDefinition);
                break;
            }
        }
    }

    private void processProperty(Class<?> clazz, BeanDefinition beanDefinition) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            java.lang.annotation.Annotation[] annotations = field.getAnnotations();
            for (java.lang.annotation.Annotation annotation : annotations) {
                if (annotation instanceof Value) {
                    String name = field.getName();
                    String value = ((Value) annotation).value();
                    if (value.length() > 0) {
                        beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
                    } else {
                        throw new IllegalArgumentException("Configuration problem: <property> element for property '"
                                + name + "' must specify a ref or value");
                    }
                }
                if (annotation instanceof Autowired) {
                    String name = field.getName();
                    String ref = field.getName();
                    if (ref != null && ref.length() > 0) {
                        BeanReference beanReference = new BeanReference(ref);
                        beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
                    } else {
                        throw new IllegalArgumentException("Configuration problem: <property> element for property '"
                                + name + "' must specify a ref or value");
                    }
                }
            }
        }
    }
}
