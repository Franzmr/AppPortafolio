/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Francisco
 */
@Entity
@Table(name = "ASOCIACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asociacion.findAll", query = "SELECT a FROM Asociacion a"),
    @NamedQuery(name = "Asociacion.findByIdasociacion", query = "SELECT a FROM Asociacion a WHERE a.idasociacion = :idasociacion"),
    @NamedQuery(name = "Asociacion.findByEstado", query = "SELECT a FROM Asociacion a WHERE a.estado = :estado")})
public class Asociacion implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDASOCIACION")
    private BigDecimal idasociacion;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private BigInteger estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asociacionIdasociacion")
    private List<Usuario> usuarioList;
    @JoinColumn(name = "SEDE_IDSEDE", referencedColumnName = "IDSEDE")
    @ManyToOne(optional = false)
    private Sede sedeIdsede;
    @JoinColumn(name = "CARRERA_IDCARRERA", referencedColumnName = "IDCARRERA")
    @ManyToOne(optional = false)
    private Carrera carreraIdcarrera;

    public Asociacion() {
    }

    public Asociacion(BigDecimal idasociacion) {
        this.idasociacion = idasociacion;
    }

    public Asociacion(BigDecimal idasociacion, BigInteger estado) {
        this.idasociacion = idasociacion;
        this.estado = estado;
    }

    public BigDecimal getIdasociacion() {
        return idasociacion;
    }

    public void setIdasociacion(BigDecimal idasociacion) {
        this.idasociacion = idasociacion;
    }

    public BigInteger getEstado() {
        return estado;
    }

    public void setEstado(BigInteger estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public Sede getSedeIdsede() {
        return sedeIdsede;
    }

    public void setSedeIdsede(Sede sedeIdsede) {
        this.sedeIdsede = sedeIdsede;
    }

    public Carrera getCarreraIdcarrera() {
        return carreraIdcarrera;
    }

    public void setCarreraIdcarrera(Carrera carreraIdcarrera) {
        this.carreraIdcarrera = carreraIdcarrera;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idasociacion != null ? idasociacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asociacion)) {
            return false;
        }
        Asociacion other = (Asociacion) object;
        if ((this.idasociacion == null && other.idasociacion != null) || (this.idasociacion != null && !this.idasociacion.equals(other.idasociacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Entity.Asociacion[ idasociacion=" + idasociacion + " ]";
    }
    
}
