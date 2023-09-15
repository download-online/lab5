package ru.jefremov.prog.interaction;

import java.util.Scanner;

/**
 * Класс, отвечающий за пользовательский ввод.
 */
public class InteractiveSubmitter implements Submitter<String> {
    private final Scanner scanner = new Scanner(System.in);
    @Override
    public boolean hasNext() {
        return scanner.hasNext();
    }

    @Override
    public String next() {
        return scanner.nextLine();
    }
}
