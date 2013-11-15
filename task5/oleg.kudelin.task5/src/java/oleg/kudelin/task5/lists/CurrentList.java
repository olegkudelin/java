/*
 * @(#)CurrentList
 *
 * @version 1.0 08.09.2013
 *
 * Copyright notice
 */
package oleg.kudelin.task5.lists;

import java.util.Collection;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

/**
 * Абстактный класс списка объектов Реализует возможность перехода по страницам
 *
 * @author Куделин Олег
 *
 */
abstract public class CurrentList<T> {

    private final int NUMBER_OUTPUT_ROW = 50;
    private int position = 0;
    /**
     * Бин, содержащий полный список объектов БД и методов для работы с ними
     */
    @Inject
    protected DataBaseObjectList dataBaseObjectList;
    /**
     * Отфильтрованный список
     */
    protected CopyOnWriteArrayList<T> filtredList;
    private static ResourceBundle res = ResourceBundle.getBundle("locale");

    /**
     * Перемещает выводимый список в начало после фильтрации
     */
    public void filtred() {
        position = 0;
        getPrev();
    }

    /**
     * Возвращает текущую позицию в списке
     *
     * @return Текущая позиция
     */
    public int getPosition() {
        return position;
    }

    /**
     * Перевод на следующую страницу
     */
    public void getNext() {
        int nextElementPosition = NUMBER_OUTPUT_ROW * position + NUMBER_OUTPUT_ROW;
        position = (nextElementPosition < filtredList.size()) ? position + 1 : position;
    }

    /**
     * Перевод на предыдущую страницу
     */
    public void getPrev() {
        position = (position - 1 < 0) ? 0 : position - 1;
    }

    /**
     * Возвращает полное число магазинов
     *
     * @return Число магазинов
     */
    public int numberShop() {
        return filtredList.size();
    }

    /**
     * Формирует текст для отображения текущего положения списка
     *
     * @return Текст
     */
    public String currentStatusText() {
        if (filtredList.size() > 0) {
            return String.format(res.getString("currentStateInPagesList"), getFirstPosition() + 1, getLastPosition() + 1, filtredList.size());
        } else {
            return res.getString("itemNotIsSelected");
        }
    }

    /**
     * Удаление объекта
     */
    abstract public void delete(T type);

    /**
     * Добавление объекта
     */
    abstract public void add(T type);

    /**
     * Редактирование объекта
     */
    abstract public void edit(ValueChangeEvent event);

    /**
     * Возвращает список для отображения с текущей позиции и с заданным числом
     * элементов или весь остаток, если их меньше
     *
     * @return Список
     */
    public Collection<T> getOutputList() {
        if (!filtredList.isEmpty()) {
            return new CopyOnWriteArrayList(filtredList.subList(getFirstPosition(), getLastPosition() + 1));
        } else {
            return new CopyOnWriteArrayList();
        }
    }

    private int getFirstPosition() {
        int firstPos = position * NUMBER_OUTPUT_ROW;
        firstPos = (firstPos < filtredList.size()) ? firstPos : 0;
        return firstPos;
    }

    private int getLastPosition() {
        int listLegth = filtredList.size();
        int lastPos = position * NUMBER_OUTPUT_ROW + NUMBER_OUTPUT_ROW;
        if (listLegth > 0) {
            lastPos = (lastPos < listLegth) ? lastPos : listLegth - 1;
        } else {
            lastPos = 0;
        }
        return lastPos;
    }
}
