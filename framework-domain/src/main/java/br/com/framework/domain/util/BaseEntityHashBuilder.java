package br.com.framework.domain.util;

import java.io.Serializable;

import org.slf4j.Logger;

import br.com.framework.domain.api.BaseEntity;

/**
 * Classe utilitária para geração de hashCode das entidades. Caso o id esteja setado, ele será utilizado para geração do hashCode, 
 * caso contrário, será utilizado um java.rmi.dgc.VMID.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
public class BaseEntityHashBuilder implements Serializable {
	
    private static final long serialVersionUID = 1L;
    private transient Object technicalId;

    public int hash(Logger log, BaseEntity<?> baseEntity) {
        if (technicalId == null) {
            if (baseEntity.isIdSet()) {
                technicalId = baseEntity.getId();
            } else {
                technicalId = new java.rmi.dgc.VMID();
                log.warn("DEVELOPER: hashCode is not safe." //
                        + "If you encounter this message you should take the time to carefuly " //
                        + "review the equals/hashCode methods for: " + baseEntity.getClass().getCanonicalName() //
                        + " You may consider using a business key.");
            }
        }
        return technicalId.hashCode();
    }
}
