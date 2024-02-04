package com.ilhomsoliev.lab5.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.ilhomsoliev.lab5.models.Ticket;
import com.ilhomsoliev.lab5.utility.LocalDateAdapter;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

import com.ilhomsoliev.lab5.utility.console.Console;

/**
 * Использует файл для сохранения и загрузки коллекции.
 *
 * @author ilhom_soliev
 */
public class DumpManager {
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    private final String fileName;
    private final String folderName;
    private final Console console;

    public DumpManager(String fileName, Console console) {
        this.fileName = fileName;
        Path path = Paths.get(fileName);
        Path parentDir = path.getParent();
        if (parentDir != null) {
            this.folderName = parentDir.toString();
        } else {
            this.folderName = "";
            System.out.println("No parent directory found.");
        }
        this.console = console;
    }

    /**
     * Записывает коллекцию в файл.
     *
     * @param collection коллекция
     */
    public void writeCollection(Collection<Ticket> collection) {
        System.out.println(folderName + "/out.txt");
        try (PrintWriter collectionPrintWriter = new PrintWriter(folderName + "/out.txt")) {
            collectionPrintWriter.println(gson.toJson(collection));
            console.println("Коллекция успешна сохранена в файл!");
        } catch (IOException exception) {
            console.printError("Загрузочный файл не может быть открыт!");
        }
    }

    /**
     * Считывает коллекцию из файл.
     *
     * @return Считанная коллекция
     */
    public Collection<Ticket> readCollection() {
        System.out.println(fileName);
        if (fileName != null && !fileName.isEmpty()) {
            try (var fileReader = new FileReader(fileName)) {
                var collectionType = new TypeToken<PriorityQueue<Ticket>>() {
                }.getType();
                var reader = new BufferedReader(fileReader);

                var jsonString = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.equals("")) {
                        jsonString.append(line);
                    }
                }

                if (jsonString.length() == 0) {
                    jsonString = new StringBuilder("[]");
                }

                PriorityQueue<Ticket> collection = gson.fromJson(jsonString.toString(), collectionType);

                console.println("Коллекция успешна загружена!");
                return collection;

            } catch (FileNotFoundException exception) {
                console.printError("Загрузочный файл не найден!");
            } catch (NoSuchElementException exception) {
                console.printError("Загрузочный файл пуст!");
            } catch (JsonParseException exception) {
                console.printError("В загрузочном файле не обнаружена необходимая коллекция!");
            } catch (IllegalStateException | IOException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        } else {
            console.printError("Аргумент командной строки с загрузочным файлом не найден!");
        }
        return new PriorityQueue<>();
    }
}
