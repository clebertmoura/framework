/**
 * 
 */
package br.com.framework.domain.api;

import java.util.Date;

/**
 * Interface base da entidade de registro das revisões das entidades de domínio. 
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public interface RevisionLogEntity extends BaseEntity<Long> {

    public Date getRevisionDate();

    public long getTimestamp();
    public void setTimestamp(long timestamp);

    public String getLogin();
	public void setLogin(String login);
	
	public Date getTimeStampAsDate();
}
