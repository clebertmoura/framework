
package br.com.framework.piloto.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.domain.util.BaseEntityHashBuilder;

/**
 * Implementação abstrata da {@link BaseEntity}.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <PK> Tipo da chave primária.
 */
@MappedSuperclass
@XmlRootElement
public abstract class BaseEntityImpl<PK extends Serializable> implements BaseEntity<PK> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1148828065002495027L;
	
	protected static final Logger log = LoggerFactory.getLogger(BaseEntityImpl.class);
	
	private PK id;
	private Long version;
	
	private BaseEntityHashBuilder hashBuilder = new BaseEntityHashBuilder();
	

	@Transient
    public PK getId() {
    	return this.id;
    }

    public void setId(PK id) {
        this.id = id;
    }
    
    public BaseEntity<PK> id(PK id) {
        setId(id);
        return this;
    }

	/* (non-Javadoc)
	 * @see br.com.framework.domain.api.BaseEntity#getVersaoEntidade()
	 */
	@Override
	@Version
    @Column(name="VERSION", nullable = false)
	public Long getVersion() {
		return version;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.domain.api.BaseEntity#setVersaoEntidade(java.lang.Long)
	 */
	@Override
	public void setVersion(Long versaoEntidade) {
		this.version = versaoEntidade;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.domain.api.BaseEntity#version(java.lang.Long)
	 */
	@Override
	public BaseEntity<PK> version(Long version) {
		setVersion(version);
		return this;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.domain.api.BaseEntity#entityClassName()
	 */
	@Override
	public String entityClassName() {
		return getClass().getSimpleName();
	}

	/* (non-Javadoc)
	 * @see br.com.framework.domain.api.BaseEntity#isIdSet()
	 */
	@Override
	@Transient
	public boolean isIdSet() {
		return id != null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return hashBuilder.hash(log, this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		BaseEntityImpl<PK> other = (BaseEntityImpl<PK>) obj;
		if (getId() == null && other.getId() == null) {
			return this == other;
		} else if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

}
