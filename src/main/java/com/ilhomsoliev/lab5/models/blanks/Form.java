package com.ilhomsoliev.lab5.models.blanks;


import com.ilhomsoliev.lab5.exceptions.IncorrectInputInScriptException;
import com.ilhomsoliev.lab5.exceptions.InvalidFormException;

/**
 * Абстрактный класс формы для ввода пользовательских данных.
 * @param <T> создаваемый объект
 * @author ilhom_soliev
 */
public abstract class Form<T> {
  public abstract T build() throws IncorrectInputInScriptException, InvalidFormException;
}
