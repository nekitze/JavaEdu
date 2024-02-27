package edu.school21.reflection.app;

import java.lang.reflect.*;
import org.reflections.Reflections;
import org.reflections.scanners.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ClassesHandler {
    private final String packageName;
    private Set<Class> classes;

    public ClassesHandler(String packageName) {
        this.packageName = packageName;
        findAllClassesUsingReflectionsLibrary();
    }

    public Set<Class> getClasses() {
        return classes;
    }

    public Field[] getFields(String className) {
        Optional<Class> targetClass = classes.stream()
                .filter(c -> c.getSimpleName().equals(className))
                .findFirst();
        if (!targetClass.isPresent()) {
            throw new RuntimeException("Класс с именем " + className + " не найден");
        } else {
            return targetClass.get().getDeclaredFields();
        }
    }

    public Method[] getMethods(String className) {
        Optional<Class> targetClass = classes.stream()
                .filter(c -> c.getSimpleName().equals(className))
                .findFirst();
        if (!targetClass.isPresent()) {
            throw new RuntimeException("Класс с именем " + className + " не найден");
        } else {
            return targetClass.get().getDeclaredMethods();
        }
    }

    public Set<Class> findAllClassesUsingReflectionsLibrary() {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        classes = new HashSet<>(reflections.getSubTypesOf(Object.class));
        return classes;
    }

    public Object createObject(String className) {
        Optional<Constructor> constructor = classes.stream()
                .filter(c -> c.getSimpleName().equals(className))
                .map(c -> {
                    try {
                        return c.getConstructor();
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                })
                .findFirst();
        if (!constructor.isPresent()) {
            throw new RuntimeException("Класс с именем " + className + " не найден");
        } else {
            try {
                return constructor.get().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setField(Object object, Field field, String value) {
        field.setAccessible(true);
        Type fieldType = field.getType();
        try {
            if(fieldType.equals(String.class)) {
                field.set(object, value);
            } else if (fieldType.equals(Integer.TYPE)) {
                field.set(object, Integer.valueOf(value));
            } else if (fieldType.equals(Double.TYPE)) {
                field.set(object, Double.valueOf(value));
            } else if (fieldType.equals(Boolean.TYPE)) {
                field.set(object, Boolean.valueOf(value));
            } else if (fieldType.equals(Long.TYPE)) {
                field.set(object, Long.valueOf(value));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Class getClass(String className) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
