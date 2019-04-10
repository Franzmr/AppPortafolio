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
@Table(name = "CARRERA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Carrera.findAll", query = "SELECT c FROM Carrera c"),
    @NamedQuery(name = "Carrera.findByIdcarrera", query = "SELECT c FROM Carrera c WHERE c.idcarrera = :idcarrera"),
    @NamedQuery(name = "Carrera.findByNombrecarrera", query = "SELECT c FROM Carrera c WHERE c.nombrecarrera = :nombrecarrera"),
    @NamedQuery(name = "Carrera.findByEstado", query = "SELECT c FROM Carrera c WHERE c.estado = :estado")})
public class Carrera implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDCARRERA")
    private BigDecimal idcarrera;
    @Basic(optional = false)
    @Column(name = "NOMBRECARRERA")
    private String nombrecarrera;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private BigInteger estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "carreraIdcarrera")
    private List<Asociacion> asociacionList;

    public Carrera() {
    }

    public Carrera(BigDecimal idcarrera) {
        this.idcarrera = idcarrera;
    }

    public Carrera(BigDecimal idcarrera, String nombrecarrera, BigInteger estado) {
        this.idcarrera = idcarrera;
        this.nombrecarrera = nombrecarrera;
        this.estado = estado;
    }

    public BigDecimal getIdcarrera() {
        return idcarrera;
    }

    public void setIdcarrera(BigDecimal idcarrera) {
        this.idcarrera = idcarrera;
    }

    public String getNombrecarrera() {
        return nombrecarrera;
    }

    public void setNombrecarrera(String nombrecarrera) {
        this.nombrecarrera = nombrecarrera;
    }

    public BigInteger getEstado() {
        return estado;
    }

    public void setEstado(BigInteger estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Asociacion> getAsociacionList() {
        return asociacionList;
    }

    public void setAsociacionList(List<Asociacion> asociacionList) {
        this.asociacionList = asociacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcarrera != null ? idcarrera.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Carrera)) {
            return false;
        }
        Carrera other = (Carrera) object;
        if ((this.idcarrera == null && other.idcarrera != null) || (this.idcarrera != null && !this.idcarrera.equals(other.idcarrera))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Entity.Carrera[ idcarrera=" + idcarrera + " ]";
    }
    
}
