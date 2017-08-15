#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package}.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import br.com.framework.domain.api.BaseEntity;

/**
 * Implementação abstrata da {@link BaseEntity}.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <PK> Tipo da chave primária.
 */
@MappedSuperclass
public abstract class BaseEntityImpl<PK extends Serializable> implements BaseEntity<PK> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1148828065002495027L;
	
	private PK id;
	private Long versaoEntidade;

	@Transient
    public PK getId() {
    	return this.id;
    }

    public void setId(PK id) {
        this.id = id;
    }

	/* (non-Javadoc)
	 * @see br.com.framework.domain.api.BaseEntity${symbol_pound}getVersaoEntidade()
	 */
	@Override
	@Version
    @Column(name="VERSAO_ENTIDADE", nullable = false)
	public Long getVersaoEntidade() {
		return versaoEntidade;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.domain.api.BaseEntity${symbol_pound}setVersaoEntidade(java.lang.Long)
	 */
	@Override
	public void setVersaoEntidade(Long versaoEntidade) {
		this.versaoEntidade = versaoEntidade;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object${symbol_pound}hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object${symbol_pound}equals(java.lang.Object)
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
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
