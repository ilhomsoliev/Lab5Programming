package com.ilhomsoliev.lab5.commands;

import com.ilhomsoliev.lab5.exceptions.CollectionIsEmptyException;
import com.ilhomsoliev.lab5.managers.CollectionManager;
import com.ilhomsoliev.lab5.utility.console.Console;
/**
 * Команда 'print_ascending'. Выводит элементы коллекции в порядке возрастания.
 *
 * @author ilhom_soliev
 */
public class PrintAscending extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public PrintAscending(Console console, CollectionManager collectionManager) {
        super("print_ascending", "вывести элементы коллекции в порядке возрастания");
        this.console = console;
        this.collectionManager = collectionManager;
    }


    @Override
    public boolean apply(String[] arguments) {
        try {
            collectionManager.printElementsAscending();
            return true;
        } catch (CollectionIsEmptyException e) {
            console.printError("Collection is empty!");
        }
        return false;
    }
}
