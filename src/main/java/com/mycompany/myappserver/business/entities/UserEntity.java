/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myappserver.business.entities;

import com.mycompany.myappserver.business.base.BaseEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
/**
 *
 * @author Alexandra
 */
@Entity
@Table(name = "\"user\"")
@NamedQueries({
    @NamedQuery(name = "UserEntity.findAll", query = "SELECT u FROM UserEntity u"),
    @NamedQuery(name = "UserEntity.findByNombre", query = "SELECT u FROM UserEntity u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "UserEntity.findByApellido", query = "SELECT u FROM UserEntity u WHERE u.apellido = :apellido"),
    @NamedQuery(name = "UserEntity.findByCedula", query = "SELECT u FROM UserEntity u WHERE u.cedula = :cedula")})
public class UserEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cedula")
    private Integer cedula;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "apellido")
    private String apellido;
    
//    @OneToMany(mappedBy = "user")
    @Transient
    private List<TarjetaCreditoEntity> tarjetaCreditoEntityList;

    public UserEntity() {
    }

    public UserEntity(Integer cedula) {
        this.cedula = cedula;
    }

    public UserEntity(Integer cedula, String nombre, String apellido) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getCedula() {
        return cedula;
    }

    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }

    public List<TarjetaCreditoEntity> getTarjetaCreditoEntityList() {
        return tarjetaCreditoEntityList;
    }

    public void setTarjetaCreditoEntityList(List<TarjetaCreditoEntity> tarjetaCreditoEntityList) {
        this.tarjetaCreditoEntityList = tarjetaCreditoEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cedula != null ? cedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserEntity)) {
            return false;
        }
        UserEntity other = (UserEntity) object;
        if ((this.cedula == null && other.cedula != null) || (this.cedula != null && !this.cedula.equals(other.cedula))) {
            return false;
        }
        return true;
    }
    
}
