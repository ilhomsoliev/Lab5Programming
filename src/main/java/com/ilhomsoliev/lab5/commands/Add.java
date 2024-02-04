package com.ilhomsoliev.lab5.commands;


import com.ilhomsoliev.lab5.exceptions.IncorrectInputInScriptException;
import com.ilhomsoliev.lab5.exceptions.InvalidFormException;
import com.ilhomsoliev.lab5.exceptions.WrongAmountOfElementsException;
import com.ilhomsoliev.lab5.managers.CollectionManager;
import com.ilhomsoliev.lab5.models.blanks.TicketForm;
import com.ilhomsoliev.lab5.utility.console.Console;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 *
 * @author ilhom_soliev
 */
public class Add extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Add(Console console, CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) throw new WrongAmountOfElementsException();
            console.println("* Создание нового 'ticket':");
            collectionManager.addToCollection((new TicketForm(console, collectionManager)).build());
            console.println("'Ticket' успешно добавлен!");
            return true;

        } catch (WrongAmountOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (InvalidFormException exception) {
            console.printError("Поля 'ticket' не валидны! 'Ticket' не создан!");
        } catch (IncorrectInputInScriptException ignored) {
        }
        return false;
    }
}
