package br.com.framework.pilotojee7.backend.monitor;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.com.framework.model.qualifiers.DataRepository;

@Stateless
@Path("/monitor")
public class MonitorEndpoint {
	
	@Inject @DataRepository
	private EntityManager entityManager;

	@GET
	@Path("/check")
	public Response check() {
		return Response.ok().build();
	}
	
}
