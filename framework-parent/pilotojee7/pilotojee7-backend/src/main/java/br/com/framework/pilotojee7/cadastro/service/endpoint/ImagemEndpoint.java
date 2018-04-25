package br.com.framework.pilotojee7.cadastro.service.endpoint;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import br.com.framework.pilotojee7.cadastro.dao.ImagemDao;
import br.com.framework.pilotojee7.cadastro.domain.Imagem;
import br.com.framework.pilotojee7.cadastro.manager.ImagemManager;
import br.com.framework.pilotojee7.cadastro.service.resource.ImagemResource;
import br.com.framework.service.impl.BaseEntityResourceEndpointImpl;

/**
 * Endpoint de {@link Imagem}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Stateless
@Path("/imagem")
public class ImagemEndpoint
		extends BaseEntityResourceEndpointImpl<Long, Imagem, ImagemResource, ImagemDao, ImagemManager> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1173742094500363316L;

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
		return createFromResource(entity, resource);
	}

	/**
	 * Cria a partir do resource.
	 * 
	 * @param entity
	 * @param resource
	 * @return
	 */
	public Imagem createFromResource(Imagem entity, ImagemResource resource) {
		if (entity == null) {
			entity = new Imagem();
		}
		entity.setNome(resource.getNome());
		entity.setContentType(resource.getContentType());
		entity.setFileExtension(resource.getFileExtension());
		entity.setLength(resource.getLength());
		byte[] data = null;
		if (resource.getData() != null) {
			data = Base64.decodeBase64(resource.getData());
			entity.setLength(data.length);
		}
		entity.setData(data);
		return entity;
	}

	@Override
	public ImagemResource toResource(Imagem entity) {
		return new ImagemResource(entity);
	}

	@GET
	@Path("/download/{id:[0-9][0-9]*}")
	@Produces({ "image/jpeg", "image/png" })
	public Response download(@PathParam("id") Long id) {
		Imagem imagem = getSearch().findById(id).getUniqueResult();
		ResponseBuilder response;
		try {
			BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(imagem.getData()));
			response = Response.ok(bis);

			response.header("Content-Disposition",
					"attachment; filename=" + imagem.getId() + "." + imagem.getFileExtension());
			return response.build();
		} catch (Exception e) {
			// TODO EntityOperation-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response upload(MultipartFormDataInput input) {
		Response response = null;

		String fileName = "";
		String fileContentType = "";
		String fileExtension = "";
		int fileLength = 0;
		byte[] bytes = null;

		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("file");

		boolean finalizou = false;
		for (InputPart inputPart : inputParts) {
			try {
				MultivaluedMap<String, String> header = inputPart.getHeaders();

				fileName = getFileName(header);

				fileExtension = FilenameUtils.getExtension(fileName);

				// convert the uploaded file to inputstream
				InputStream inputStream = inputPart.getBody(InputStream.class, null);

				MediaType mediaType = inputPart.getMediaType();
				fileContentType = mediaType.toString();
				fileExtension = mediaType.getSubtype();

				bytes = IOUtils.toByteArray(inputStream);
				fileLength = bytes.length;

				finalizou = true;
			} catch (IOException e) {
				response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(createGenericError(e)).build();
				finalizou = false;
			}
		}

		if (finalizou) {
			Imagem imagem = new Imagem();
			imagem.setNome(fileName);
			imagem.setContentType(fileContentType);
			imagem.setFileExtension(fileExtension);
			imagem.setLength(fileLength);
			imagem.setData(bytes);

			try {
				imagem = getManager().insert(imagem);

				response = Response
						.created(UriBuilder.fromResource(getClass()).path(String.valueOf(imagem.getId())).build())
						.build();
			} catch (ConstraintViolationException ce) {
				response = Response.status(Status.PRECONDITION_FAILED).entity(createViolationErrors(ce)).build();
			} catch (Exception e) {
				if (e.getCause() instanceof ConstraintViolationException) {
					response = Response.status(Status.PRECONDITION_FAILED)
							.entity(createViolationErrors((ConstraintViolationException) e.getCause())).build();
				} else {
					response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(createGenericError(e)).build();
				}
			}
		}

		return response;

	}

	@GET
	@Path("/downloadAuthGET/{id:[0-9][0-9]*}")
	@Produces({ "image/jpeg", "image/png" })
	public Response downloadAuthGET(@PathParam("id") Long id) {
		ResponseBuilder response;

		Imagem imagem = getSearch().findById(id).getUniqueResult();

		try {
			BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(imagem.getData()));
			response = Response.ok(bis);

			response.header("Content-Disposition",
					"attachment; filename=" + imagem.getId() + "." + imagem.getFileExtension());
			return response.build();
		} catch (Exception e) {
			// TODO EntityOperation-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * header sample { Content-Type=[image/png], Content-Disposition=[form-data;
	 * name="file"; filename="filename.extension"] }
	 **/
	// get uploaded filename, is there a easy way in RESTEasy?
	private String getFileName(MultivaluedMap<String, String> header) {
		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");

				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}
}
