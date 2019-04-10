/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "COMUNA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comuna.findAll", query = "SELECT c FROM Comuna c"),
    @NamedQuery(name = "Comuna.findByIdcomuna", query = "SELECT c FROM Comuna c WHERE c.idcomuna = :idcomuna"),
    @NamedQuery(name = "Comuna.findByNombrecomuna", query = "SELECT c FROM Comuna c WHERE c.nombrecomuna = :nombrecomuna")})
public class Comuna implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDCOMUNA")
    private BigDecimal idcomuna;
    @Basic(optional = false)
    @Column(name = "NOMBRECOMUNA")
    private String nombrecomuna;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comunaIdcomuna")
    private List<Usuario> usuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comunaIdcomuna")
    private List<Empleador> empleadorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comunaIdcomuna")
    private List<Sede> sedeList;

    public Comuna() {
    }

    public Comuna(BigDecimal idcomuna) {
        this.idcomuna = idcomuna;
    }

    public Comuna(BigDecimal idcomuna, String nombrecomuna) {
        this.idcomuna = idcomuna;
        this.nombrecomuna = nombrecomuna;
    }

    public BigDecimal getIdcomuna() {
        return idcomuna;
    }

    public void setIdcomuna(BigDecimal idcomuna) {
        this.idcomuna = idcomuna;
    }

    public String getNombrecomuna() {
        return nombrecomuna;
    }

    public void setNombrecomuna(String nombrecomuna) {
        this.nombrecomuna = nombrecomuna;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @XmlTransient
    public List<Empleador> getEmpleadorList() {
        return empleadorList;
    }

    public void setEmpleadorList(List<Empleador> empleadorList) {
        this.empleadorList = empleadorList;
    }

    @XmlTransient
    public List<Sede> getSedeList() {
        return sedeList;
    }

    public void setSedeList(List<Sede> sedeList) {
        this.sedeList = sedeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcomuna != null ? idcomuna.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comuna)) {
            return false;
        }
        Comuna other = (Comuna) object;
        if ((this.idcomuna == null && other.idcomuna != null) || (this.idcomuna != null && !this.idcomuna.equals(other.idcomuna))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Entity.Comuna[ idcomuna=" + idcomuna + " ]";
    }
    
}
