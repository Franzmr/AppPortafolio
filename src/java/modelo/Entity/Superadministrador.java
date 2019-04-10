/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.Entity;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Francisco
 */
@Entity
@Table(name = "SUPERADMINISTRADOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Superadministrador.findAll", query = "SELECT s FROM Superadministrador s"),
    @NamedQuery(name = "Superadministrador.findByIdadministrador", query = "SELECT s FROM Superadministrador s WHERE s.idadministrador = :idadministrador"),
    @NamedQuery(name = "Superadministrador.findByNombreusuario", query = "SELECT s FROM Superadministrador s WHERE s.nombreusuario = :nombreusuario"),
    @NamedQuery(name = "Superadministrador.findByPassword", query = "SELECT s FROM Superadministrador s WHERE s.password = :password"),
    @NamedQuery(name = "Superadministrador.findByEstado", query = "SELECT s FROM Superadministrador s WHERE s.estado = :estado")})
public class Superadministrador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDADMINISTRADOR")
    private String idadministrador;
    @Column(name = "NOMBREUSUARIO")
    private String nombreusuario;
    @Basic(optional = false)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private BigInteger estado;

    public Superadministrador() {
    }

    public Superadministrador(String idadministrador) {
        this.idadministrador = idadministrador;
    }

    public Superadministrador(String idadministrador, String password, BigInteger estado) {
        this.idadministrador = idadministrador;
        this.password = password;
        this.estado = estado;
    }

    public String getIdadministrador() {
        return idadministrador;
    }

    public void setIdadministrador(String idadministrador) {
        this.idadministrador = idadministrador;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigInteger getEstado() {
        return estado;
    }

    public void setEstado(BigInteger estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idadministrador != null ? idadministrador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Superadministrador)) {
            return false;
        }
        Superadministrador other = (Superadministrador) object;
        if ((this.idadministrador == null && other.idadministrador != null) || (this.idadministrador != null && !this.idadministrador.equals(other.idadministrador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Entity.Superadministrador[ idadministrador=" + idadministrador + " ]";
    }
    
}
