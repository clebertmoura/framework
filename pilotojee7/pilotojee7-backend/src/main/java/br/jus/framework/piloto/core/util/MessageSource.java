/*
 * Source code generated by Celerio, a Jaxio product.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Follow us on twitter: @jaxiosoft
 * Need commercial support ? Contact us: info@jaxio.com
 * Template celerio-pack-backend:src/main/java/util/MessageSource.p.vm.java
 */
package br.jus.framework.piloto.core.util;

import java.util.Locale;

public interface MessageSource {
    void setBasenames(String... basenames);

    String getMessage(String key, Locale locale);

    String getMessage(String key, Object[] args, Locale locale);
}