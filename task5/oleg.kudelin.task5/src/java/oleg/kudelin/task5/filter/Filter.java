/*
 * @(#)Filter
 *
 * @version 1.0 08.09.2013
 *
 * Copyright notice
 */
package oleg.kudelin.task5.filter;

import java.util.Collection;

/**
 * Абстрактный класс фильтра
 *
 * @author Куделин Олег
 *
 */
abstract public class Filter<T> {

    /**
     * Шаблон строки для поиска
     */
    protected String patternString;

    public String getPatternString() {
        return patternString;
    }

    public void setPatternString(String patternString) {
        this.patternString = patternString;
    }

    /**
     * Перебирает коллекцию sourceList и всех членов, которые удовлетворяют
     * шаблону копирует в filtredList. filtredList очищается
     *
     * @param sourceList - источник
     * @param filtredList - приемник
     */
    abstract public void filter(Collection<T> filtredList, Collection<T> sourceList);
}
