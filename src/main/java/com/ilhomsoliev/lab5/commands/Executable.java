package com.ilhomsoliev.lab5.commands;

/**
 * Интерфейс для всех выполняемых команд.
 * @author ilhom_soliev
 */
public interface Executable {
  /**
   * Выполнить что-либо.
   *
   * @param arguments Аргумент для выполнения
   * @return результат выполнения
   */
  boolean apply(String[] arguments);
}
