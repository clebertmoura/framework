#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import ${package}.cadastro.dao.ImagemDao;
import ${package}.cadastro.domain.Imagem;
import ${package}.cadastro.manager.ImagemManager;
import ${package}.cadastro.service.resource.ImagemResource;
import br.com.framework.service.impl.BaseEntityAuditedResourceEndpointImpl;


/**
 * Endpoint de {@link Imagem}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/imagem")
public class ImagemEndpoint extends BaseEntityAuditedResourceEndpointImpl<Long, Imagem, ImagemResource, ImagemDao, ImagemManager>{

	private static final long serialVersionUID = -1L;
	
	
	@Override
	@Inject
	protected void setManager(ImagemManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(ImagemDao search) {
		super.setSearch(search);
	}

	@Override
	public Imagem fromResource(Imagem entity, ImagemResource resource) {
		if (entity == null) {
			entity = new Imagem();
		}
		// inicializa os atributos da entidade.
		entity.setNome(resource.getNome());
		entity.setContentType(resource.getContentType());
		entity.setFileExtension(resource.getFileExtension());
		entity.setData(resource.getData());
		entity.setLength(resource.getLength());
		// inicializa entidades relacionadas
		return entity;
	}

	@Override
	protected ImagemResource toResource(Imagem entity) {
		return new ImagemResource(entity);
	}
}