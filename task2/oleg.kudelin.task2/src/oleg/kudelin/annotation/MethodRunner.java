/*
* @(#)MethodRunner
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
 * Аннотация времени выполнения для пометки метода, 
 * который должн быть выполнен
 * 
 * @author Куделин Олег
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

public @interface MethodRunner {
	String name = "MethodRunner";
}
