package poi;

import java.io.IOException;

public class Test {

    static  void Excep() {
        try {
            System.out.println("IO");
            throw new IOException();
        } catch (IOException e) {
            System.out.println("other");
            throw new RuntimeException();
        } catch (RuntimeException e) {
            System.out.println("Run");
        }
    }

    public static void main(String[] args) {
//        System.out.println(Runtime.getRuntime().totalMemory());
//        System.out.println(Runtime.getRuntime().freeMemory());

        try {
            Excep();
        } catch (RuntimeException e) {
            System.out.println("Run in main");
        }
    }
}
