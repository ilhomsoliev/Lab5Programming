package com.ilhomsoliev.lab5.models.blanks;

import com.ilhomsoliev.lab5.exceptions.IncorrectInputInScriptException;
import com.ilhomsoliev.lab5.exceptions.InvalidFormException;
import com.ilhomsoliev.lab5.exceptions.MustBeNotEmptyException;
import com.ilhomsoliev.lab5.exceptions.NotInDeclaredLimitsException;
import com.ilhomsoliev.lab5.managers.CollectionManager;
import com.ilhomsoliev.lab5.models.Coordinates;
import com.ilhomsoliev.lab5.models.Ticket;
import com.ilhomsoliev.lab5.models.TicketType;
import com.ilhomsoliev.lab5.models.Venue;
import com.ilhomsoliev.lab5.utility.Interrogator;
import com.ilhomsoliev.lab5.utility.console.Console;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static com.ilhomsoliev.lab5.utility.Interrogator.fileMode;

/**
 * Форма продукта.
 *
 * @author ilhom_soliev
 */
public class TicketForm extends Form<Ticket> {
    private final Console console;
    private final CollectionManager collectionManager;

    private final long MIN_PRICE = 0;

    public TicketForm(Console console, CollectionManager collectionManager) {
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public Ticket build() throws IncorrectInputInScriptException, InvalidFormException {
        var product = new Ticket(
                askName(),
                askCoordinates(),
                LocalDate.now(),
                askPrice(),
                askDiscount(),
                askRefundable(),
                askTicketType(),
                askVenue()
        );
        if (!product.validate()) throw new InvalidFormException();
        return product;
    }

    private String askName() throws IncorrectInputInScriptException {
        String name;
        var fileMode = fileMode();
        while (true) {
            try {
                console.println("Введите название ticket:");
                console.ps2();
                name = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(name);
                if (name.isEmpty()) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Название не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                console.printError("Название не может быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return name;
    }

    private Coordinates askCoordinates() throws IncorrectInputInScriptException, InvalidFormException {
        return new CoordinatesForm(console).build();
    }

    private Long askPrice() throws IncorrectInputInScriptException {
        var fileMode = fileMode();
        long price;
        while (true) {
            try {
                console.println("Введите цену ticket:");
                console.ps2();

                var strPrice = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(strPrice);

                price = Long.parseLong(strPrice);
                if (price < MIN_PRICE) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Цена ticket не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                console.printError("Цена ticket должна быть больше нуля!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                console.printError("Цена ticket должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return price;
    }

    private int askDiscount() throws IncorrectInputInScriptException {
        int discount;
        var fileMode = fileMode();
        while (true) {
            try {
                console.println("Введите discount of ticket:");
                console.ps2();

                String partNumberStr = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(partNumberStr);
                if (partNumberStr.isEmpty()) {
                    throw new IllegalStateException("");
                }
                discount = Integer.parseInt(partNumberStr);
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Номер части не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return discount;
    }

    private boolean askRefundable() throws IncorrectInputInScriptException {
        boolean isRefundable;
        while (true) {
            try {
                console.println("is ticket Refundable (1/0): ");
                console.ps2();

                var strPrice = Interrogator.getUserScanner().nextLine().trim();
                if (strPrice.equals("1")) isRefundable = true;
                else if (strPrice.equals("0")) isRefundable = false;
                else throw new IncorrectInputInScriptException();
                break;
            } catch (IncorrectInputInScriptException e) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return isRefundable;
    }

    private TicketType askTicketType() throws IncorrectInputInScriptException {
        return new TicketTypeForm(console).build();
    }

    private Venue askVenue() throws IncorrectInputInScriptException, InvalidFormException {
        return new VenueForm(console).build();
    }
}
