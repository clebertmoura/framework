package br.com.framework.pilotojee7.cadastro.service.resource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.backend.keycloak.domain.KeycloakGrupo;
import br.com.framework.pilotojee7.backend.keycloak.service.resource.KeycloakGrupoResource;
import br.com.framework.pilotojee7.cadastro.domain.CategoriaNoticia;
import br.com.framework.pilotojee7.cadastro.domain.Imagem;
import br.com.framework.pilotojee7.cadastro.domain.Noticia;
import br.com.framework.pilotojee7.core.enums.SimNao;
import br.com.framework.service.impl.BaseEntityAuditedResourceImpl;

/**
 * Resource da entidade {@link Noticia}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class NoticiaResource extends BaseEntityAuditedResourceImpl<Long, Noticia> {

    private static final long serialVersionUID = -1L;

    private boolean loadData = true;

    private String titulo;
    private String conteudo;
    private String descricao;
    private LocalDateTime dataInicial;
    private LocalDateTime dataFinal;
    private SimNao destaque;
    private List<ImagemResource> imagens;
    private List<KeycloakGrupoResource> grupos;
    private List<CategoriaNoticiaResource> categorias;

    public NoticiaResource() {
        super();
    }

    /**
     * @param entity
     * @param onlyId
     */
    public NoticiaResource(Noticia entity, boolean onlyId) {
        super(entity, onlyId);
        loadExtraFromEntity(entity);
    }

    /**
     * @param entity
     */
    public NoticiaResource(Noticia entity) {
        super(entity);
        loadExtraFromEntity(entity);
    }

    /**
     * @param entity
     * @param onlyId
     * @param loadData
     */
    public NoticiaResource(Noticia entity, boolean onlyId, boolean loadData) {
        super(entity, onlyId);
        this.loadData = loadData;
        loadExtraFromEntity(entity);
    }

    @Override
    public void loadFromEntity(Noticia entity) {
        super.loadFromEntity(entity);
        setTitulo(entity.getTitulo());
        setConteudo(entity.getConteudo());
        setDescricao(entity.getDescricao());
        setDataInicial(entity.getDataInicial());
        setDataFinal(entity.getDataFinal());
        setDestaque(entity.getDestaque());

        Iterator<KeycloakGrupo> itGrupos = entity.getGrupos().iterator();
        while (itGrupos.hasNext()) {
            KeycloakGrupo element = itGrupos.next();
            this.getGrupos().add(new KeycloakGrupoResource(element));
        }

        Iterator<CategoriaNoticia> itCategoria = entity.getCategorias().iterator();
        while (itCategoria.hasNext()) {
            CategoriaNoticia element = itCategoria.next();
            this.getCategorias().add(new CategoriaNoticiaResource(element));
        }
    }

    @Override
    public void loadExtraFromEntity(Noticia entity) {
        super.loadExtraFromEntity(entity);
        Iterator<Imagem> itImagens = entity.getImagens().iterator();
        while (itImagens.hasNext()) {
            Imagem element = itImagens.next();
            if (loadData) {
                this.getImagens().add(new ImagemResource(element, false, true));
            } else {
                this.getImagens().add(new ImagemResource(element, true, false));
            }
        }
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return this.conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /*@JsonFormat(pattern = "dd-MM-yyyy HH:mm.Z")*/
    public LocalDateTime getDataInicial() {
        return this.dataInicial;
    }

    public void setDataInicial(LocalDateTime dataInicial) {
        this.dataInicial = dataInicial;
    }

    /*@JsonFormat(pattern = "dd-MM-yyyy HH:mm.Z")*/
    public LocalDateTime getDataFinal() {
        return this.dataFinal;
    }

    public void setDataFinal(LocalDateTime dataFinal) {
        this.dataFinal = dataFinal;
    }

    public SimNao getDestaque() {
        return this.destaque;
    }

    public void setDestaque(SimNao destaque) {
        this.destaque = destaque;
    }

    public List<ImagemResource> getImagens() {
        if (imagens == null) {
            imagens = new ArrayList<ImagemResource>();
        }
        return this.imagens;
    }

    public void setImagens(List<ImagemResource> imagens) {
        this.imagens = imagens;
    }

    public List<KeycloakGrupoResource> getGrupos() {
        if (grupos == null) {
            grupos = new ArrayList<KeycloakGrupoResource>();
        }
        return this.grupos;
    }

    public void setGrupos(List<KeycloakGrupoResource> grupos) {
        this.grupos = grupos;
    }

    public List<CategoriaNoticiaResource> getCategorias() {
        if (categorias == null) {
            categorias = new ArrayList<CategoriaNoticiaResource>();
        }
        return categorias;
    }

    public void setCategorias(List<CategoriaNoticiaResource> categorias) {
        this.categorias = categorias;
    }
}

