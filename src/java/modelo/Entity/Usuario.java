/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.Entity;

import java.io.Serializable;
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
@Table(name = "USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByRut", query = "SELECT u FROM Usuario u WHERE u.rut = :rut"),
    @NamedQuery(name = "Usuario.findByNombres", query = "SELECT u FROM Usuario u WHERE u.nombres = :nombres"),
    @NamedQuery(name = "Usuario.findByApellidopaterno", query = "SELECT u FROM Usuario u WHERE u.apellidopaterno = :apellidopaterno"),
    @NamedQuery(name = "Usuario.findByApellidomaterno", query = "SELECT u FROM Usuario u WHERE u.apellidomaterno = :apellidomaterno"),
    @NamedQuery(name = "Usuario.findByDireccion", query = "SELECT u FROM Usuario u WHERE u.direccion = :direccion"),
    @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password"),
    @NamedQuery(name = "Usuario.findByCorreo", query = "SELECT u FROM Usuario u WHERE u.correo = :correo"),
    @NamedQuery(name = "Usuario.findByTelefono", query = "SELECT u FROM Usuario u WHERE u.telefono = :telefono"),
    @NamedQuery(name = "Usuario.findByEstado", query = "SELECT u FROM Usuario u WHERE u.estado = :estado")})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "RUT")
    private String rut;
    @Basic(optional = false)
    @Column(name = "NOMBRES")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "APELLIDOPATERNO")
    private String apellidopaterno;
    @Basic(optional = false)
    @Column(name = "APELLIDOMATERNO")
    private String apellidomaterno;
    @Basic(optional = false)
    @Column(name = "DIRECCION")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "CORREO")
    private String correo;
    @Column(name = "TELEFONO")
    private int telefono;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private int estado;
    @JoinColumn(name = "TIPOUSUARIO_IDTIPOUSUARIO", referencedColumnName = "IDTIPOUSUARIO")
    @ManyToOne(optional = false)
    private Tipousuario tipousuarioIdtipousuario;
    @JoinColumn(name = "COMUNA_IDCOMUNA", referencedColumnName = "IDCOMUNA")
    @ManyToOne(optional = false)
    private Comuna comunaIdcomuna;
    @JoinColumn(name = "ASOCIACION_IDASOCIACION", referencedColumnName = "IDASOCIACION")
    @ManyToOne(optional = false)
    private Asociacion asociacionIdasociacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profesorRut")
    private List<Practica> practicaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alumnoRut")
    private List<Practica> practicaList1;

    public Usuario() {
    }

    public Usuario(String rut) {
        this.rut = rut;
    }

    public Usuario(String rut, String nombres, String apellidopaterno, String apellidomaterno, String direccion, String password, int estado) {
        this.rut = rut;
        this.nombres = nombres;
        this.apellidopaterno = apellidopaterno;
        this.apellidomaterno = apellidomaterno;
        this.direccion = direccion;
        this.password = password;
        this.estado = estado;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidopaterno() {
        return apellidopaterno;
    }

    public void setApellidopaterno(String apellidopaterno) {
        this.apellidopaterno = apellidopaterno;
    }

    public String getApellidomaterno() {
        return apellidomaterno;
    }

    public void setApellidomaterno(String apellidomaterno) {
        this.apellidomaterno = apellidomaterno;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
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

    public Asociacion getAsociacionIdasociacion() {
        return asociacionIdasociacion;
    }

    public void setAsociacionIdasociacion(Asociacion asociacionIdasociacion) {
        this.asociacionIdasociacion = asociacionIdasociacion;
    }

    @XmlTransient
    public List<Practica> getPracticaList() {
        return practicaList;
    }

    public void setPracticaList(List<Practica> practicaList) {
        this.practicaList = practicaList;
    }

    @XmlTransient
    public List<Practica> getPracticaList1() {
        return practicaList1;
    }

    public void setPracticaList1(List<Practica> practicaList1) {
        this.practicaList1 = practicaList1;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.rut == null && other.rut != null) || (this.rut != null && !this.rut.equals(other.rut))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Entity.Usuario[ rut=" + rut + " ]";
    }
    
}
