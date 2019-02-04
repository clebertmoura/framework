package br.com.framework.piloto.core.util;

import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * ResourcesUtil allows you to retrieve localized resources for the locale present in the current thread of execution.
 * You can either inject it or use the static method ResourcesUtil.getInstance().
 */
@ApplicationScoped
@Named
@Startup
public class ResourcesUtil {

    private static ResourcesUtil instance;
    private MessageSource messageSource;

    public ResourcesUtil() {
        instance = this;
        messageSource = createMessageSource();
    }

    /**
     * Call it from code that cannot get a reference to the ResourcesUtil
     * singleton instance through dependency injection.
     */
    public static ResourcesUtil getInstance() {
        return instance;
    }

    /**
     * Return the {@link MessageSource} that backs this ResourcesUtil.
     */
    public MessageSource getMessageSource() {
        return messageSource;
    }

    /**
     * Return the property value for the contextual locale.
     * If no value is found, the passed key is returned.
     */
    public String getProperty(String key) {
        return messageSource.getMessage(key, LocaleHolder.getLocale());
    }

    public String getPropertyWithArrayArg(String key, Object[] arrayArg) {
        return messageSource.getMessage(key, arrayArg, LocaleHolder.getLocale());
    }

    public String getProperty(String key, Object arg1) {
        return messageSource.getMessage(key, new Object[] { arg1 }, LocaleHolder.getLocale());
    }

    // we do not use var args to please EL + passing var args from method to method does not work well
    // with array of Object as an Object[] is also an Object.

    public String getProperty(String key, Object arg1, Object arg2) {
        return messageSource.getMessage(key, new Object[] { arg1, arg2 }, LocaleHolder.getLocale());
    }

    public String getProperty(String key, Object arg1, Object arg2, Object arg3) {
        return messageSource.getMessage(key, new Object[] { arg1, arg2, arg3 }, LocaleHolder.getLocale());
    }

    public String getProperty(String key, Object arg1, Object arg2, Object arg3, Object arg4) {
        return messageSource.getMessage(key, new Object[] { arg1, arg2, arg3, arg4 }, LocaleHolder.getLocale());
    }

    public String getProperty(String key, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
        return messageSource.getMessage(key, new Object[] { arg1, arg2, arg3, arg4, arg5 }, LocaleHolder.getLocale());
    }

    public String getProperty(String key, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6) {
        return messageSource.getMessage(key, new Object[] { arg1, arg2, arg3, arg4, arg5, arg6 }, LocaleHolder.getLocale());
    }

    /**
     * Same as getProperty() but use the count to choose the proper key.
     * Used when the message varies depending on the context. For example: 'there is no result' vs 'there is one result' vs 'there are n results'
     * @param key the base key
     */
    public String getPluralableProperty(String key, int count) {
        if (key == null) {
            return "";
        }

        switch (count) {
        case 0:
            return getProperty(key + "_0");
        case 1:
            return getProperty(key + "_1");
        default:
            return getProperty(key + "_n", count);
        }
    }

    private MessageSource createMessageSource() {
        MessageSource msgSource = new DefaultMessageSource();
        msgSource.setBasenames( //
                // global
                "localization/messages", //
                "localization/application", //
                // entities
                "localization/domain/Acao", //
                "localization/domain/Assunto", //
                "localization/domain/Classe", //
                "localization/domain/Competencia", //
                "localization/domain/Localidade", //
                "localization/domain/Lote", //
                "localization/domain/OrgaoJulgador", //
                "localization/domain/Parametro", //
                "localization/domain/PoloAtivo", //
                "localization/domain/Sitacao", //
                "localization/domain/Sitlote", //
                // enums
                "localization/domain/SituacaoAcaoEnum", //
                "localization/domain/SituacaoLoteEnum", //
                // pages
                "localization/pages/concurrentModificationResolution", //
                "localization/pages/home", //
                "localization/pages/login", //
                // validation
                "ValidationMessages", //
                "javax/faces/Messages", //
                "org/hibernate/validator/ValidationMessages");
        return msgSource;
    }
}