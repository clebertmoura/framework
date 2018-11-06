package br.jus.framework.domain.util;

import java.io.Serializable;

import org.slf4j.Logger;

import br.jus.framework.domain.api.BaseEntity;

/**
 * Na primeira vez que o {@link #hash(Logger, BaseEntity)} é executado, é verificado se a chave primária está presente ou não.
 * <ul>
 * <li>Se estiver: ela é utilizada para geração do hash</li>
 * <li>Se não: we use a VMID during the entire life of this instance even if later on this instance is assigned a primary key.</li>
 * </ul>
 */
public class BaseEntityHashBuilder implements Serializable {
	
    private static final long serialVersionUID = 1L;
    private Object technicalId;

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
