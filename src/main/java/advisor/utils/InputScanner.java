package advisor.utils;

import java.util.Scanner;

public final class InputScanner {
    private static final Scanner SCANNER = new Scanner(System.in);
    
    private InputScanner() {}
    
    public static String[] getStringInput() {
        return SCANNER.nextLine().toLowerCase().split(" ");
    }
}
