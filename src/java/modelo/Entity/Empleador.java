/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.Entity;

import java.io.Serializable;
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
@Table(name = "EMPLEADOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleador.findAll", query = "SELECT e FROM Empleador e"),
    @NamedQuery(name = "Empleador.findByRut", query = "SELECT e FROM Empleador e WHERE e.rut = :rut"),
    @NamedQuery(name = "Empleador.findByRazonsocial", query = "SELECT e FROM Empleador e WHERE e.razonsocial = :razonsocial"),
    @NamedQuery(name = "Empleador.findByNombrefantasia", query = "SELECT e FROM Empleador e WHERE e.nombrefantasia = :nombrefantasia"),
    @NamedQuery(name = "Empleador.findByDireccion", query = "SELECT e FROM Empleador e WHERE e.direccion = :direccion"),
    @NamedQuery(name = "Empleador.findByNombrejefe", query = "SELECT e FROM Empleador e WHERE e.nombrejefe = :nombrejefe"),
    @NamedQuery(name = "Empleador.findByCorreo", query = "SELECT e FROM Empleador e WHERE e.correo = :correo"),
    @NamedQuery(name = "Empleador.findByTelefono", query = "SELECT e FROM Empleador e WHERE e.telefono = :telefono"),
    @NamedQuery(name = "Empleador.findByPassword", query = "SELECT e FROM Empleador e WHERE e.password = :password"),
    @NamedQuery(name = "Empleador.findByEstado", query = "SELECT e FROM Empleador e WHERE e.estado = :estado")})
public class Empleador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "RUT")
    private String rut;
    @Basic(optional = false)
    @Column(name = "RAZONSOCIAL")
    private String razonsocial;
    @Basic(optional = false)
    @Column(name = "NOMBREFANTASIA")
    private String nombrefantasia;
    @Basic(optional = false)
    @Column(name = "DIRECCION")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "NOMBREJEFE")
    private String nombrejefe;
    @Column(name = "CORREO")
    private String correo;
    @Column(name = "TELEFONO")
    private BigInteger telefono;
    @Basic(optional = false)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private BigInteger estado;
    @JoinColumn(name = "TIPOUSUARIO_IDTIPOUSUARIO", referencedColumnName = "IDTIPOUSUARIO")
    @ManyToOne(optional = false)
    private Tipousuario tipousuarioIdtipousuario;
    @JoinColumn(name = "COMUNA_IDCOMUNA", referencedColumnName = "IDCOMUNA")
    @ManyToOne(optional = false)
    private Comuna comunaIdcomuna;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleadorRut")
    private List<Practica> practicaList;

    public Empleador() {
    }

    public Empleador(String rut) {
        this.rut = rut;
    }

    public Empleador(String rut, String razonsocial, String nombrefantasia, String direccion, String nombrejefe, String password, BigInteger estado) {
        this.rut = rut;
        this.razonsocial = razonsocial;
        this.nombrefantasia = nombrefantasia;
        this.direccion = direccion;
        this.nombrejefe = nombrejefe;
        this.password = password;
        this.estado = estado;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getNombrefantasia() {
        return nombrefantasia;
    }

    public void setNombrefantasia(String nombrefantasia) {
        this.nombrefantasia = nombrefantasia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombrejefe() {
        return nombrejefe;
    }

    public void setNombrejefe(String nombrejefe) {
        this.nombrejefe = nombrejefe;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public BigInteger getTelefono() {
        return telefono;
    }

    public void setTelefono(BigInteger telefono) {
        this.telefono = telefono;
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

    public Tipousuario getTipousuarioIdtipousuario() {
        return tipousuarioIdtipousuario;
    }

    public void setTipousuarioIdtipousuario(Tipousuario tipousuarioIdtipousuario) {
        this.tipousuarioIdtipousuario = tipousuarioIdtipousuario;
    }

    public Comuna getComunaIdcomuna() {
        return comunaIdcomuna;
    }

    public void setComunaIdcomuna(Comuna comunaIdcomuna) {
        this.comunaIdcomuna = comunaIdcomuna;
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
        hash += (rut != null ? rut.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleador)) {
            return false;
        }
        Empleador other = (Empleador) object;
        if ((this.rut == null && other.rut != null) || (this.rut != null && !this.rut.equals(other.rut))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Entity.Empleador[ rut=" + rut + " ]";
    }
    
}
