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
@Table(name = "TIPOUSUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipousuario.findAll", query = "SELECT t FROM Tipousuario t"),
    @NamedQuery(name = "Tipousuario.findByIdtipousuario", query = "SELECT t FROM Tipousuario t WHERE t.idtipousuario = :idtipousuario"),
    @NamedQuery(name = "Tipousuario.findByNombretipousuario", query = "SELECT t FROM Tipousuario t WHERE t.nombretipousuario = :nombretipousuario")})
public class Tipousuario implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDTIPOUSUARIO")
    private BigDecimal idtipousuario;
    @Basic(optional = false)
    @Column(name = "NOMBRETIPOUSUARIO")
    private String nombretipousuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipousuarioIdtipousuario")
    private List<Usuario> usuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipousuarioIdtipousuario")
    private List<Empleador> empleadorList;

    public Tipousuario() {
    }

    public Tipousuario(BigDecimal idtipousuario) {
        this.idtipousuario = idtipousuario;
    }

    public Tipousuario(BigDecimal idtipousuario, String nombretipousuario) {
        this.idtipousuario = idtipousuario;
        this.nombretipousuario = nombretipousuario;
    }

    public BigDecimal getIdtipousuario() {
        return idtipousuario;
    }

    public void setIdtipousuario(BigDecimal idtipousuario) {
        this.idtipousuario = idtipousuario;
    }

    public String getNombretipousuario() {
        return nombretipousuario;
    }

    public void setNombretipousuario(String nombretipousuario) {
        this.nombretipousuario = nombretipousuario;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipousuario != null ? idtipousuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipousuario)) {
            return false;
        }
        Tipousuario other = (Tipousuario) object;
        if ((this.idtipousuario == null && other.idtipousuario != null) || (this.idtipousuario != null && !this.idtipousuario.equals(other.idtipousuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Entity.Tipousuario[ idtipousuario=" + idtipousuario + " ]";
    }
    
}
