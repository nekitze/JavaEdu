package edu.school21.reflection.app;

import java.lang.reflect.*;
import java.util.*;
import java.util.logging.LogManager;
import java.util.stream.Collectors;

public class Program {
    private static ClassesHandler classesHandler;
    private static Object object;

    public static void main(String[] args) {
        LogManager.getLogManager().reset();
        classesHandler = new ClassesHandler("edu.school21.reflection.classes");
        startUserDialog();
    }

    public static void startUserDialog() {
        Scanner scanner = new Scanner(System.in);

        showClasses();

        System.out.println("Enter class name:");
        String className = scanner.next();
        showClassInfo(className);

        System.out.println("Let's create an object.");
        createObject(className);

        System.out.println("Enter name of the field for changing:");
        String methodName = scanner.next();
        changeField(methodName);

        System.out.println("Enter name of the method for call:");
        String methodSignature = scanner.next();
        callMethod(methodSignature);
    }

    public static void showClasses() {
        System.out.println("Classes:");

        Set<Class> classes = classesHandler.getClasses();
        for (Class c : classes) {
            System.out.println("\t- " + c.getSimpleName());
        }

        showDelimiter();
    }

    public static void showClassInfo(String className) {
        showDelimiter();

        Field[] fields = classesHandler.getFields(className);
        Method[] methods = classesHandler.getMethods(className);

        System.out.println("fields:");
        for (Field f : fields) {
            System.out.println("\t" + f.getType().getSimpleName() + " " + f.getName());
        }

        System.out.println("methods:");
        for (Method m : methods) {
            System.out.print("\t" + m.getReturnType().getSimpleName() + " " + m.getName());
            List<String> parameterTypes = Arrays.stream(m.getParameterTypes())
                    .map(Class::getSimpleName)
                    .collect(Collectors.toList());
            String parameters = parameterTypes.toString()
                    .replaceAll("\\[", "")
                    .replaceAll("]", "");
            System.out.println("(" + parameters + ")");
        }

        showDelimiter();
    }

    public static void createObject(String className) {
        Scanner scanner = new Scanner(System.in);

        Field[] fields = classesHandler.getFields(className);

        object = classesHandler.createObject(className);
        for (Field field : fields) {
            System.out.println(field.getName() + ":");
            String value = scanner.next();
            classesHandler.setField(object, field, value);
        }

        System.out.println("Object created: " + object);
        showDelimiter();
    }

    public static void changeField(String fieldName) {
        Scanner scanner = new Scanner(System.in);
        Field field = null;
        try {
            field = object.getClass().getDeclaredField(fieldName);
            System.out.println("Enter " + field.getType() + " value:");

            String value = scanner.next();
            classesHandler.setField(object, field, value);

            System.out.println("Object updated: " + object);
            showDelimiter();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static void callMethod(String methodSignature) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Object> parameters = new ArrayList<>();

        String methodName = methodSignature.split("\\(")[0];
        String[] parametersTypesString = methodSignature.split("\\(")[1].replace(")", "").split(", |,");

        Method method = null;
        Class[] parameterTypes = getParameterTypesFromMethodSignature(parametersTypesString);
        try {
            method = object.getClass().getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        for (Object parameterType : parameterTypes) {
            System.out.println("Enter " + parameterType + " value");
            String parameter = scanner.next();

            if (parameterType.equals(String.class)) {
                parameters.add(parameter);
            } else if (parameterType.equals(Integer.TYPE)) {
                parameters.add(Integer.valueOf(parameter));
            } else if (parameterType.equals(Double.TYPE)) {
                parameters.add(Double.valueOf(parameter));
            } else if (parameterType.equals(Boolean.TYPE)) {
                parameters.add(Boolean.valueOf(parameter));
            } else if (parameterType.equals(Long.TYPE)) {
                parameters.add(Long.valueOf(parameter));
            }
        }

        try {
            Object returnValue = method.invoke(object, parameters.toArray());
            if (returnValue != null) {
                System.out.println("Method returned:");
                System.out.println(returnValue);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        showDelimiter();
    }

    public static Class[] getParameterTypesFromMethodSignature(String[] parameters) {
        ArrayList<Class> parameterTypes = new ArrayList<>();

        for (String parameter : parameters) {
            if (parameter.equals("String")) {
                parameterTypes.add(String.class);
            } else if (parameter.equals("int") || parameter.equals("Integer")) {
                parameterTypes.add(Integer.TYPE);
            } else if (parameter.equalsIgnoreCase("Double")) {
                parameterTypes.add(Double.TYPE);
            } else if (parameter.equalsIgnoreCase("Boolean")) {
                parameterTypes.add(Boolean.TYPE);
            } else if (parameter.equalsIgnoreCase("Long")) {
                parameterTypes.add(Long.TYPE);
            }
        }

        return parameterTypes.toArray(new Class[0]);
    }

    public static void showDelimiter() {
        System.out.println("---------------------");
    }
}
