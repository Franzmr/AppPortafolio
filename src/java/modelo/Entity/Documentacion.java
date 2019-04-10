/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Francisco
 */
@Entity
@Table(name = "DOCUMENTACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Documentacion.findAll", query = "SELECT d FROM Documentacion d"),
    @NamedQuery(name = "Documentacion.findByIddocumentacion", query = "SELECT d FROM Documentacion d WHERE d.iddocumentacion = :iddocumentacion"),
    @NamedQuery(name = "Documentacion.findByNombredocumentacion", query = "SELECT d FROM Documentacion d WHERE d.nombredocumentacion = :nombredocumentacion"),
    @NamedQuery(name = "Documentacion.findByFecha", query = "SELECT d FROM Documentacion d WHERE d.fecha = :fecha"),
    @NamedQuery(name = "Documentacion.findByPeso", query = "SELECT d FROM Documentacion d WHERE d.peso = :peso")})
public class Documentacion implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDDOCUMENTACION")
    private BigDecimal iddocumentacion;
    @Basic(optional = false)
    @Column(name = "NOMBREDOCUMENTACION")
    private String nombredocumentacion;
    @Basic(optional = false)
    @Lob
    @Column(name = "DOCUMENTACION")
    private Serializable documentacion;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "PESO")
    private double peso;
    @JoinColumn(name = "PRACTICA_IDPRACTICA", referencedColumnName = "IDPRACTICA")
    @ManyToOne(optional = false)
    private Practica practicaIdpractica;

    public Documentacion() {
    }

    public Documentacion(BigDecimal iddocumentacion) {
        this.iddocumentacion = iddocumentacion;
    }

    public Documentacion(BigDecimal iddocumentacion, String nombredocumentacion, Serializable documentacion, Date fecha, double peso) {
        this.iddocumentacion = iddocumentacion;
        this.nombredocumentacion = nombredocumentacion;
        this.documentacion = documentacion;
        this.fecha = fecha;
        this.peso = peso;
    }

    public BigDecimal getIddocumentacion() {
        return iddocumentacion;
    }

    public void setIddocumentacion(BigDecimal iddocumentacion) {
        this.iddocumentacion = iddocumentacion;
    }

    public String getNombredocumentacion() {
        return nombredocumentacion;
    }

    public void setNombredocumentacion(String nombredocumentacion) {
        this.nombredocumentacion = nombredocumentacion;
    }

    public Serializable getDocumentacion() {
        return documentacion;
    }

    public void setDocumentacion(Serializable documentacion) {
        this.documentacion = documentacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public Practica getPracticaIdpractica() {
        return practicaIdpractica;
    }

    public void setPracticaIdpractica(Practica practicaIdpractica) {
        this.practicaIdpractica = practicaIdpractica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddocumentacion != null ? iddocumentacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Documentacion)) {
            return false;
        }
        Documentacion other = (Documentacion) object;
        if ((this.iddocumentacion == null && other.iddocumentacion != null) || (this.iddocumentacion != null && !this.iddocumentacion.equals(other.iddocumentacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Entity.Documentacion[ iddocumentacion=" + iddocumentacion + " ]";
    }

    public void setFecha(String fecha) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
