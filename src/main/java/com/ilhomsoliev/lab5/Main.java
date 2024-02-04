package com.ilhomsoliev.lab5;

import com.ilhomsoliev.lab5.commands.*;
import com.ilhomsoliev.lab5.managers.CollectionManager;
import com.ilhomsoliev.lab5.managers.CommandManager;
import com.ilhomsoliev.lab5.managers.DumpManager;
import com.ilhomsoliev.lab5.models.Ticket;
import com.ilhomsoliev.lab5.utility.Interrogator;
import com.ilhomsoliev.lab5.utility.Runner;
import com.ilhomsoliev.lab5.utility.console.StandardConsole;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Interrogator.setUserScanner(new Scanner(System.in));
        var console = new StandardConsole();
        if (args.length == 0) {
            console.println("Введите имя загружаемого файла как аргумент командной строки");
            System.exit(1);
        }
        var dumpManager = new DumpManager(args[0], console);
        var collectionManager = new CollectionManager(dumpManager);
        Ticket.updateNextId(collectionManager);
        collectionManager.validateAll(console);

        var commandManager = new CommandManager() {{
            register("help", new Help(console, this));
            register("info", new Info(console, collectionManager));
            register("show", new Show(console, collectionManager));
            register("add", new Add(console, collectionManager));
            register("update", new Update(console, collectionManager));
            register("remove_by_id", new RemoveById(console, collectionManager));
            register("clear", new Clear(console, collectionManager));
            register("save", new Save(console, collectionManager));
            register("execute_script", new ExecuteScript(console));
            register("exit", new Exit(console));
            register("remove_first", new RemoveFirst(console, collectionManager));
            register("head", new Head(console, collectionManager));
            register("remove_lower", new RemoveLower(console, collectionManager));
            register("count_by_discount", new CountByDiscount(console, collectionManager));
            register("filter_greater_than_discount", new FilterGreaterThanDiscount(console, collectionManager));
            register("print_ascending", new PrintAscending(console, collectionManager));
        }};
        new Runner(console, commandManager).interactiveMode();

    }
}