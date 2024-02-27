# SpringContext

Implementation of a loosely-coupled system comprised of a set of components (beans) and compliant with IoC/DI principles.

All settings for each component and links between them specified for Spring in context.xml file.

UML diagram of classes is shown below:

An example of code using these classes in a standard way:
```
public class Main {
   public static void main(String[] args) {
       PreProcessor preProcessor = new PreProcessorToUpperImpl();
       Renderer renderer = new RendererErrImpl(preProcessor);
       PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
       printer.setPrefix("Prefix");
       printer.print("Hello!");
   }
}
```

Running this code will deliver the following result:
```
PREFIX HELLO!
```

Using these components with Spring looks as follows:
```
public class Main {
   public static void main(String[] args) {
       ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
       Printer printer = context.getBean(“printerWithPrefix”, Printer.class);
       printer.print("Hello!");
   }
}
```
