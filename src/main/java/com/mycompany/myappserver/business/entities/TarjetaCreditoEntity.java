/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myappserver.business.entities;

import com.mycompany.myappserver.business.base.BaseEntity;
import com.mycompany.myappserver.util.JsonDateSerializer;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author Alexandra
 */
@Entity
@Table(name = "tarjeta_credito")
@NamedQueries({
    @NamedQuery(name = "TarjetaCreditoEntity.findAll", query = "SELECT t FROM TarjetaCreditoEntity t"),
    @NamedQuery(name = "TarjetaCreditoEntity.findByBin", query = "SELECT t FROM TarjetaCreditoEntity t WHERE t.bin = :bin"),
    @NamedQuery(name = "TarjetaCreditoEntity.findByProducto", query = "SELECT t FROM TarjetaCreditoEntity t WHERE t.producto = :producto"),
    //@NamedQuery(name = "TarjetaCreditoEntity.findByFechaExpiracion", query = "SELECT t FROM TarjetaCreditoEntity t WHERE t.fechaExpiracion = :fechaExpiracion"),
    @NamedQuery(name = "TarjetaCreditoEntity.findByTcClaro", query = "SELECT t FROM TarjetaCreditoEntity t WHERE t.tcClaro = :tcClaro")})
public class TarjetaCreditoEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "bin")
    private Integer bin;
    
    @Column(name = "producto")
    private Integer producto;
    
   /* @Column(name = "fecha_expiracion")
    @Temporal(TemporalType.DATE)
    private Date fechaExpiracion;*/
    
    @Size(max = 2147483647)
    @Column(name = "tc_claro")
    private String tcClaro;
    
    @Column(name = "cvv")
    private Integer cvv;
    
    @JoinColumn(name = "user", referencedColumnName = "cedula")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private UserEntity user;

    public TarjetaCreditoEntity() {
    }

    public TarjetaCreditoEntity(Integer bin) {
        this.bin = bin;
    }

    public Integer getBin() {
        return bin;
    }

    public void setBin(Integer bin) {
        this.bin = bin;
    }

    public Integer getProducto() {
        return producto;
    }

    public void setProducto(Integer producto) {
        this.producto = producto;
    }

   /* @JsonSerialize(using=JsonDateSerializer.class)
    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }*/

    public String getTcClaro() {
        return tcClaro;
    }

    public void setTcClaro(String tcClaro) {
        this.tcClaro = tcClaro;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bin != null ? bin.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TarjetaCreditoEntity)) {
            return false;
        }
        TarjetaCreditoEntity other = (TarjetaCreditoEntity) object;
        if ((this.bin == null && other.bin != null) || (this.bin != null && !this.bin.equals(other.bin))) {
            return false;
        }
        return true;
    }
    
}
