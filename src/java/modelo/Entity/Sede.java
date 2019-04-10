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
@Table(name = "SEDE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sede.findAll", query = "SELECT s FROM Sede s"),
    @NamedQuery(name = "Sede.findByIdsede", query = "SELECT s FROM Sede s WHERE s.idsede = :idsede"),
    @NamedQuery(name = "Sede.findByNombresede", query = "SELECT s FROM Sede s WHERE s.nombresede = :nombresede"),
    @NamedQuery(name = "Sede.findByDireccion", query = "SELECT s FROM Sede s WHERE s.direccion = :direccion"),
    @NamedQuery(name = "Sede.findByEstado", query = "SELECT s FROM Sede s WHERE s.estado = :estado")})
public class Sede implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDSEDE")
    private BigDecimal idsede;
    @Basic(optional = false)
    @Column(name = "NOMBRESEDE")
    private String nombresede;
    @Basic(optional = false)
    @Column(name = "DIRECCION")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private BigInteger estado;
    @JoinColumn(name = "COMUNA_IDCOMUNA", referencedColumnName = "IDCOMUNA")
    @ManyToOne(optional = false)
    private Comuna comunaIdcomuna;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sedeIdsede")
    private List<Asociacion> asociacionList;

    public Sede() {
    }

    public Sede(BigDecimal idsede) {
        this.idsede = idsede;
    }

    public Sede(BigDecimal idsede, String nombresede, String direccion, BigInteger estado) {
        this.idsede = idsede;
        this.nombresede = nombresede;
        this.direccion = direccion;
        this.estado = estado;
    }

    public BigDecimal getIdsede() {
        return idsede;
    }

    public void setIdsede(BigDecimal idsede) {
        this.idsede = idsede;
    }

    public String getNombresede() {
        return nombresede;
    }

    public void setNombresede(String nombresede) {
        this.nombresede = nombresede;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public BigInteger getEstado() {
        return estado;
    }

    public void setEstado(BigInteger estado) {
        this.estado = estado;
    }

    public Comuna getComunaIdcomuna() {
        return comunaIdcomuna;
    }

    public void setComunaIdcomuna(Comuna comunaIdcomuna) {
        this.comunaIdcomuna = comunaIdcomuna;
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
        hash += (idsede != null ? idsede.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sede)) {
            return false;
        }
        Sede other = (Sede) object;
        if ((this.idsede == null && other.idsede != null) || (this.idsede != null && !this.idsede.equals(other.idsede))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Entity.Sede[ idsede=" + idsede + " ]";
    }
    
}
