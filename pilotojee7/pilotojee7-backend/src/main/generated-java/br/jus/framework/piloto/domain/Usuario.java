/*
 * Source code generated by Celerio, a Jaxio product.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Follow us on twitter: @jaxiosoft
 * Need commercial support ? Contact us: info@jaxio.com
 * Template celerio-pack-backend:src/main/java/domain/Entity.java.e.vm
 * Template is part of Open Source Project: https://github.com/jaxio/javaee-lab
 */
package br.jus.framework.piloto.domain;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.jus.framework.domain.enums.Status;
import br.jus.framework.piloto.core.domain.BaseEntityAuditedImpl;

import com.google.common.base.MoreObjects;

@Entity
@Table(schema = "cadastros", name = "usuario")

@AttributeOverride(name = "version", column = @Column(name = "version") )
@AttributeOverride(name = "creationDate", column = @Column(name = "creation_date") )
@AttributeOverride(name = "lastModificationDate", column = @Column(name = "last_modification_date") )
@AttributeOverride(name = "creationAuthor", column = @Column(name = "creation_author") )
@AttributeOverride(name = "lastModificationAuthor", column = @Column(name = "last_modification_author") )
@AttributeOverride(name = "status", column = @Column(name = "habilitado") )

/**
 * Implementação da entidade de domínio: Usuario
 *
 */
public class Usuario extends BaseEntityAuditedImpl<Long> {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(Usuario.class);

    // Raw attributes
    private String nome;
    private String login;
    private String password;
    private String email;
    // Import enums
    private byte[] fotoBinary;
    private String fotoFileName;
    private String fotoContentType;
    private Long fotoSize;

    @Override
    public String entityClassName() {
        return Usuario.class.getSimpleName();
    }

    // -----------------------
    // Simple Primary Key
    // -----------------------

    /**
     * Returns the simple primary key.
     */
    @Column(name = "id_usuario", precision = 19)
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_usuario")
    @Id
    @SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", schema = "cadastros", allocationSize = 1)
    public Long getId() {
        return super.getId();
    }

    /**
     * Set the composite primary key.
     * @param id the composite primary key.
     */
    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    public Usuario id(Long id) {
        setId(id);
        return this;
    }

    // -- [nome] ------------------------
    @NotEmpty
    @Size(max = 255)
    @Column(name = "nome", nullable = false)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuario nome(String nome) {
        setNome(nome);
        return this;
    }

    // -- [login] ------------------------
    @NotEmpty
    @Size(min = 2, max = 255)
    @Column(name = "login", nullable = false)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Usuario login(String login) {
        setLogin(login);
        return this;
    }

    // -- [password] ------------------------
    @NotEmpty
    @Size(max = 255)
    @Column(name = "\"password\"", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Usuario password(String password) {
        setPassword(password);
        return this;
    }

    // -- [email] ------------------------
    @Email
    @NotEmpty
    @Size(max = 255)
    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Usuario email(String email) {
        setEmail(email);
        return this;
    }

    // -- [fotoBinary] ------------------------
    @Basic(fetch = LAZY)
    @Column(name = "foto_binary")
    @Lob
    public byte[] getFotoBinary() {
        return fotoBinary;
    }

    public void setFotoBinary(byte[] fotoBinary) {
        this.fotoBinary = fotoBinary;
    }

    public Usuario fotoBinary(byte[] fotoBinary) {
        setFotoBinary(fotoBinary);
        return this;
    }

    // -- [fotoFileName] ------------------------
    @Size(max = 255)
    @Column(name = "foto_file_name")
    public String getFotoFileName() {
        return fotoFileName;
    }

    public void setFotoFileName(String fotoFileName) {
        this.fotoFileName = fotoFileName;
    }

    public Usuario fotoFileName(String fotoFileName) {
        setFotoFileName(fotoFileName);
        return this;
    }

    // -- [fotoContentType] ------------------------
    @Size(max = 255)
    @Column(name = "foto_content_type")
    public String getFotoContentType() {
        return fotoContentType;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public Usuario fotoContentType(String fotoContentType) {
        setFotoContentType(fotoContentType);
        return this;
    }

    // -- [fotoSize] ------------------------
    @Digits(integer = 19, fraction = 0)
    @Column(name = "foto_size", precision = 19)
    public Long getFotoSize() {
        return fotoSize;
    }

    public void setFotoSize(Long fotoSize) {
        this.fotoSize = fotoSize;
    }

    public Usuario fotoSize(Long fotoSize) {
        setFotoSize(fotoSize);
        return this;
    }

    /**
     * Apply the default values.
     */
    public Usuario withDefaults() {
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this) //
                .add("id", getId()) //
                .add("nome", getNome()) //
                .add("login", getLogin()) //
                .add("password", "XXXX") //
                .add("email", getEmail()) //
                .add("version", getVersion()) //
                .add("creationDate", getCreationDate()) //
                .add("lastModificationDate", getLastModificationDate()) //
                .add("creationAuthor", getCreationAuthor()) //
                .add("lastModificationAuthor", getLastModificationAuthor()) //
                .add("status", getStatus()) //
                .add("fotoBinary", getFotoBinary()) //
                .add("fotoFileName", getFotoFileName()) //
                .add("fotoContentType", getFotoContentType()) //
                .add("fotoSize", getFotoSize()) //
                .toString();
    }
}