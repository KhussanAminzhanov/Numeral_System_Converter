import java.util.Objects;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        while (!Objects.equals(str, "0")) {
            try {
                System.out.println(Integer.parseInt(str) * 10);
                str = scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid user input: " + str);
                str = scanner.nextLine();
            }
        }
    }
}