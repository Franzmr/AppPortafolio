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
@Table(name = "TIPOPRACTICA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipopractica.findAll", query = "SELECT t FROM Tipopractica t"),
    @NamedQuery(name = "Tipopractica.findByIdtipopractica", query = "SELECT t FROM Tipopractica t WHERE t.idtipopractica = :idtipopractica"),
    @NamedQuery(name = "Tipopractica.findByNombretipopractica", query = "SELECT t FROM Tipopractica t WHERE t.nombretipopractica = :nombretipopractica"),
    @NamedQuery(name = "Tipopractica.findByHoras", query = "SELECT t FROM Tipopractica t WHERE t.horas = :horas")})
public class Tipopractica implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDTIPOPRACTICA")
    private BigDecimal idtipopractica;
    @Basic(optional = false)
    @Column(name = "NOMBRETIPOPRACTICA")
    private String nombretipopractica;
    @Basic(optional = false)
    @Column(name = "HORAS")
    private BigInteger horas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipopracticaIdtipopractica")
    private List<Practica> practicaList;

    public Tipopractica() {
    }

    public Tipopractica(BigDecimal idtipopractica) {
        this.idtipopractica = idtipopractica;
    }

    public Tipopractica(BigDecimal idtipopractica, String nombretipopractica, BigInteger horas) {
        this.idtipopractica = idtipopractica;
        this.nombretipopractica = nombretipopractica;
        this.horas = horas;
    }

    public BigDecimal getIdtipopractica() {
        return idtipopractica;
    }

    public void setIdtipopractica(BigDecimal idtipopractica) {
        this.idtipopractica = idtipopractica;
    }

    public String getNombretipopractica() {
        return nombretipopractica;
    }

    public void setNombretipopractica(String nombretipopractica) {
        this.nombretipopractica = nombretipopractica;
    }

    public BigInteger getHoras() {
        return horas;
    }

    public void setHoras(BigInteger horas) {
        this.horas = horas;
    }

    @XmlTransient
    public List<Practica> getPracticaList() {
        return practicaList;
    }

    public void setPracticaList(List<Practica> practicaList) {
        this.practicaList = practicaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipopractica != null ? idtipopractica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipopractica)) {
            return false;
        }
        Tipopractica other = (Tipopractica) object;
        if ((this.idtipopractica == null && other.idtipopractica != null) || (this.idtipopractica != null && !this.idtipopractica.equals(other.idtipopractica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Entity.Tipopractica[ idtipopractica=" + idtipopractica + " ]";
    }
    
}
