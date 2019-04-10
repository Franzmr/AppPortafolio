/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Francisco
 */
@Entity
@Table(name = "PRACTICA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Practica.findAll", query = "SELECT p FROM Practica p"),
    @NamedQuery(name = "Practica.findByIdpractica", query = "SELECT p FROM Practica p WHERE p.idpractica = :idpractica"),
    @NamedQuery(name = "Practica.findByFechainicio", query = "SELECT p FROM Practica p WHERE p.fechainicio = :fechainicio"),
    @NamedQuery(name = "Practica.findByFechatermino", query = "SELECT p FROM Practica p WHERE p.fechatermino = :fechatermino"),
    @NamedQuery(name = "Practica.findByPracticadistancia", query = "SELECT p FROM Practica p WHERE p.practicadistancia = :practicadistancia"),
    @NamedQuery(name = "Practica.findByTareasdesarrollar", query = "SELECT p FROM Practica p WHERE p.tareasdesarrollar = :tareasdesarrollar"),
    @NamedQuery(name = "Practica.findByDepartamento", query = "SELECT p FROM Practica p WHERE p.departamento = :departamento")})
public class Practica implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDPRACTICA")
    private BigDecimal idpractica;
    @Basic(optional = false)
    @Column(name = "FECHAINICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicio;
    @Basic(optional = false)
    @Column(name = "FECHATERMINO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechatermino;
    @Basic(optional = false)
    @Column(name = "PRACTICADISTANCIA")
    private BigInteger practicadistancia;
    @Basic(optional = false)
    @Column(name = "TAREASDESARROLLAR")
    private String tareasdesarrollar;
    @Basic(optional = false)
    @Column(name = "DEPARTAMENTO")
    private String departamento;
    @JoinColumn(name = "PROFESOR_RUT", referencedColumnName = "RUT")
    @ManyToOne(optional = false)
    private Usuario profesorRut;
    @JoinColumn(name = "ALUMNO_RUT", referencedColumnName = "RUT")
    @ManyToOne(optional = false)
    private Usuario alumnoRut;
    @JoinColumn(name = "TIPOPRACTICA_IDTIPOPRACTICA", referencedColumnName = "IDTIPOPRACTICA")
    @ManyToOne(optional = false)
    private Tipopractica tipopracticaIdtipopractica;
    @JoinColumn(name = "NOTAS_IDNOTAS", referencedColumnName = "IDNOTAS")
    @ManyToOne(optional = false)
    private Notas notasIdnotas;
    @JoinColumn(name = "EMPLEADOR_RUT", referencedColumnName = "RUT")
    @ManyToOne(optional = false)
    private Empleador empleadorRut;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "practicaIdpractica")
    private List<Documentacion> documentacionList;

    public Practica() {
    }

    public Practica(BigDecimal idpractica) {
        this.idpractica = idpractica;
    }

    public Practica(BigDecimal idpractica, Date fechainicio, Date fechatermino, BigInteger practicadistancia, String tareasdesarrollar, String departamento) {
        this.idpractica = idpractica;
        this.fechainicio = fechainicio;
        this.fechatermino = fechatermino;
        this.practicadistancia = practicadistancia;
        this.tareasdesarrollar = tareasdesarrollar;
        this.departamento = departamento;
    }

    public BigDecimal getIdpractica() {
        return idpractica;
    }

    public void setIdpractica(BigDecimal idpractica) {
        this.idpractica = idpractica;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechatermino() {
        return fechatermino;
    }

    public void setFechatermino(Date fechatermino) {
        this.fechatermino = fechatermino;
    }

    public BigInteger getPracticadistancia() {
        return practicadistancia;
    }

    public void setPracticadistancia(BigInteger practicadistancia) {
        this.practicadistancia = practicadistancia;
    }

    public String getTareasdesarrollar() {
        return tareasdesarrollar;
    }

    public void setTareasdesarrollar(String tareasdesarrollar) {
        this.tareasdesarrollar = tareasdesarrollar;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Usuario getProfesorRut() {
        return profesorRut;
    }

    public void setProfesorRut(Usuario profesorRut) {
        this.profesorRut = profesorRut;
    }

    public Usuario getAlumnoRut() {
        return alumnoRut;
    }

    public void setAlumnoRut(Usuario alumnoRut) {
        this.alumnoRut = alumnoRut;
    }

    public Tipopractica getTipopracticaIdtipopractica() {
        return tipopracticaIdtipopractica;
    }

    public void setTipopracticaIdtipopractica(Tipopractica tipopracticaIdtipopractica) {
        this.tipopracticaIdtipopractica = tipopracticaIdtipopractica;
    }

    public Notas getNotasIdnotas() {
        return notasIdnotas;
    }

    public void setNotasIdnotas(Notas notasIdnotas) {
        this.notasIdnotas = notasIdnotas;
    }

    public Empleador getEmpleadorRut() {
        return empleadorRut;
    }

    public void setEmpleadorRut(Empleador empleadorRut) {
        this.empleadorRut = empleadorRut;
    }

    @XmlTransient
    public List<Documentacion> getDocumentacionList() {
        return documentacionList;
    }

    public void setDocumentacionList(List<Documentacion> documentacionList) {
        this.documentacionList = documentacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpractica != null ? idpractica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Practica)) {
            return false;
        }
        Practica other = (Practica) object;
        if ((this.idpractica == null && other.idpractica != null) || (this.idpractica != null && !this.idpractica.equals(other.idpractica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Entity.Practica[ idpractica=" + idpractica + " ]";
    }
    
}
