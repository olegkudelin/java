/*
* @(#)ClassRunner
*
* @version 1.0 15.08.2013
*
* Copyright notice
*/

package oleg.kudelin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация времени выполнения для пометки класса, 
 * который должн быть запущен
 * 
 * @author Куделин Олег
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)

public @interface ClassRunner {
	String name = "ClassRunner";
}
