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
@Table(name = "NOTAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notas.findAll", query = "SELECT n FROM Notas n"),
    @NamedQuery(name = "Notas.findByIdnotas", query = "SELECT n FROM Notas n WHERE n.idnotas = :idnotas"),
    @NamedQuery(name = "Notas.findByNota1", query = "SELECT n FROM Notas n WHERE n.nota1 = :nota1"),
    @NamedQuery(name = "Notas.findByNota2", query = "SELECT n FROM Notas n WHERE n.nota2 = :nota2"),
    @NamedQuery(name = "Notas.findByNota3", query = "SELECT n FROM Notas n WHERE n.nota3 = :nota3"),
    @NamedQuery(name = "Notas.findByNotafinal", query = "SELECT n FROM Notas n WHERE n.notafinal = :notafinal")})
public class Notas implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDNOTAS")
    private BigDecimal idnotas;
    @Column(name = "NOTA1")
    private double nota1;
    @Column(name = "NOTA2")
    private double nota2;
    @Column(name = "NOTA3")
    private double nota3;
    @Column(name = "NOTAFINAL")
    private double notafinal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "notasIdnotas")
    private List<Practica> practicaList;

    public Notas() {
    }

    public Notas(BigDecimal idnotas) {
        this.idnotas = idnotas;
    }

    public BigDecimal getIdnotas() {
        return idnotas;
    }

    public void setIdnotas(BigDecimal idnotas) {
        this.idnotas = idnotas;
    }

    public double getNota1() {
        return nota1;
    }

    public void setNota1(double nota1) {
        this.nota1 = nota1;
    }

    public double getNota2() {
        return nota2;
    }

    public void setNota2(double nota2) {
        this.nota2 = nota2;
    }

    public double getNota3() {
        return nota3;
    }

    public void setNota3(double nota3) {
        this.nota3 = nota3;
    }

    public double getNotafinal() {
        return notafinal;
    }

    public void setNotafinal(double notafinal) {
        this.notafinal = notafinal;
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
        hash += (idnotas != null ? idnotas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notas)) {
            return false;
        }
        Notas other = (Notas) object;
        if ((this.idnotas == null && other.idnotas != null) || (this.idnotas != null && !this.idnotas.equals(other.idnotas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Entity.Notas[ idnotas=" + idnotas + " ]";
    }
    
}
