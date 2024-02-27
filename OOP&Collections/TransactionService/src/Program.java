import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        Menu menu;
        if (args.length > 0 && args[0].equals("--profile=dev")) {
            menu = new DevMenu();
        } else {
            menu = new Menu();
        }
        menu.processUserInput();
    }
}