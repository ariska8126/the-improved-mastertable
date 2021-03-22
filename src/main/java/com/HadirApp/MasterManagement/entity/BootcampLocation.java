/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author creative
 */
@Entity
@Table(name = "bootcamp_location")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BootcampLocation.findAll", query = "SELECT b FROM BootcampLocation b"),
    @NamedQuery(name = "BootcampLocation.findByBootcampLocationId", query = "SELECT b FROM BootcampLocation b WHERE b.bootcampLocationId = :bootcampLocationId"),
    @NamedQuery(name = "BootcampLocation.findByBootcampLocationName", query = "SELECT b FROM BootcampLocation b WHERE b.bootcampLocationName = :bootcampLocationName"),
    @NamedQuery(name = "BootcampLocation.findByBootcampLocationActive", query = "SELECT b FROM BootcampLocation b WHERE b.bootcampLocationActive = :bootcampLocationActive")})
public class BootcampLocation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bootcamp_location_id")
    private Integer bootcampLocationId;
    @Basic(optional = false)
    @Column(name = "bootcamp_location_name")
    private String bootcampLocationName;
    @Basic(optional = false)
    @Column(name = "bootcamp_location_active")
    private String bootcampLocationActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bootcampLocationId")
    private List<Bootcamp> bootcampList;

    public BootcampLocation() {
    }

    public BootcampLocation(Integer bootcampLocationId) {
        this.bootcampLocationId = bootcampLocationId;
    }

    public BootcampLocation(Integer bootcampLocationId, String bootcampLocationName, String bootcampLocationActive) {
        this.bootcampLocationId = bootcampLocationId;
        this.bootcampLocationName = bootcampLocationName;
        this.bootcampLocationActive = bootcampLocationActive;
    }

    public Integer getBootcampLocationId() {
        return bootcampLocationId;
    }

    public void setBootcampLocationId(Integer bootcampLocationId) {
        this.bootcampLocationId = bootcampLocationId;
    }

    public String getBootcampLocationName() {
        return bootcampLocationName;
    }

    public void setBootcampLocationName(String bootcampLocationName) {
        this.bootcampLocationName = bootcampLocationName;
    }

    public String getBootcampLocationActive() {
        return bootcampLocationActive;
    }

    public void setBootcampLocationActive(String bootcampLocationActive) {
        this.bootcampLocationActive = bootcampLocationActive;
    }

    @XmlTransient
    public List<Bootcamp> getBootcampList() {
        return bootcampList;
    }

    public void setBootcampList(List<Bootcamp> bootcampList) {
        this.bootcampList = bootcampList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bootcampLocationId != null ? bootcampLocationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BootcampLocation)) {
            return false;
        }
        BootcampLocation other = (BootcampLocation) object;
        if ((this.bootcampLocationId == null && other.bootcampLocationId != null) || (this.bootcampLocationId != null && !this.bootcampLocationId.equals(other.bootcampLocationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.HadirApp.MasterManagement.entity.BootcampLocation[ bootcampLocationId=" + bootcampLocationId + " ]";
    }
    
}
