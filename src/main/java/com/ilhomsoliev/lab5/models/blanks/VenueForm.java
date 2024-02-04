package com.ilhomsoliev.lab5.models.blanks;

import com.ilhomsoliev.lab5.exceptions.IncorrectInputInScriptException;
import com.ilhomsoliev.lab5.exceptions.InvalidFormException;
import com.ilhomsoliev.lab5.models.Coordinates;
import com.ilhomsoliev.lab5.models.TicketType;
import com.ilhomsoliev.lab5.models.Venue;
import com.ilhomsoliev.lab5.models.VenueType;
import com.ilhomsoliev.lab5.utility.Interrogator;
import com.ilhomsoliev.lab5.utility.console.Console;

import java.util.NoSuchElementException;
/**
 * Форма продукта.
 * @author ilhom_soliev
 */
public class VenueForm extends Form<Venue> {
    private final Console console;

    public VenueForm(Console console) {
        this.console = console;
    }

    @Override
    public Venue build() throws IncorrectInputInScriptException, InvalidFormException {
        var venue = new Venue(askName(), askCapacity(), askVenueType());
        if (!venue.validate()) throw new InvalidFormException();
        return venue;
    }

    private String askName() throws IncorrectInputInScriptException {
        var fileMode = Interrogator.fileMode();
        String name;
        while (true) {
            try {
                console.println("Enter name of the venue:");
                console.ps2();
                var strName = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(strName);
                if (strName.equals(""))
                    throw new IncorrectInputInScriptException();
                else name = strName;
                break;
            } catch (IncorrectInputInScriptException exception) {
                console.printError("Name не распознана!");
                System.exit(0);
            }
        }
        return name;
    }

    private long askCapacity() {
        var fileMode = Interrogator.fileMode();
        long capacity;
        while (true) {
            try {
                console.println("Enter capacity of the venue:");
                console.ps2();
                var value = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(value);
                long k = Long.parseLong(value);
                if (k > 0) {
                    capacity = k;
                } else throw new IncorrectInputInScriptException();
                break;
            } catch (IncorrectInputInScriptException e) {
                console.printError("Capacity не распознана!");
                System.exit(0);
            }
        }
        return capacity;
    }

    private VenueType askVenueType() throws IncorrectInputInScriptException {
        var fileMode = Interrogator.fileMode();
        VenueType venueType;
        while (true) {
            try {
                console.println("list of venue types - " + VenueType.names());
                console.println("Enter type of the venue (or null):");
                console.ps2();
                var value = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(value);
                if (value.equals("") || value.equals("null")) return null;
                venueType = VenueType.valueOf(value.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Мера весов не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                console.printError("Меры весов нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return venueType;
    }
}
