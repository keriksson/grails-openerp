package org.grails.plugins.openerp.client

import java.lang.annotation.*

/**
 *
 * @author kristofer.eriksson@openfellas.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

public @interface ClientSession {
}
