/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Francis
 */
@Entity
@Table(name = "SERVICE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Service.findAll", query = "SELECT s FROM Service s"),
    @NamedQuery(name = "Service.findByServiceno", query = "SELECT s FROM Service s WHERE s.serviceno = :serviceno"),
    @NamedQuery(name = "Service.findByDescription", query = "SELECT s FROM Service s WHERE s.description = :description"),
    @NamedQuery(name = "Service.findByServicename", query = "SELECT s FROM Service s WHERE s.servicename = :servicename"),
    @NamedQuery(name = "Service.findByThumbnail", query = "SELECT s FROM Service s WHERE s.thumbnail = :thumbnail"),
    @NamedQuery(name = "Service.findByType", query = "SELECT s FROM Service s WHERE s.type = :type"),
    @NamedQuery(name = "Service.findByStatus", query = "SELECT s FROM Service s WHERE s.status = :status")})
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SERVICENO")
    private Integer serviceno;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 255)
    @Column(name = "SERVICENAME")
    private String servicename;
    @Size(max = 255)
    @Column(name = "THUMBNAIL")
    private String thumbnail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TYPE")
    private String type;
    @Size(max = 255)
    @Column(name = "STATUS")
    private String status;

    public Service() {
    }

    public Service(Integer serviceno) {
        this.serviceno = serviceno;
    }

    public Service(Integer serviceno, String type) {
        this.serviceno = serviceno;
        this.type = type;
    }

    public Integer getServiceno() {
        return serviceno;
    }

    public void setServiceno(Integer serviceno) {
        this.serviceno = serviceno;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serviceno != null ? serviceno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Service)) {
            return false;
        }
        Service other = (Service) object;
        if ((this.serviceno == null && other.serviceno != null) || (this.serviceno != null && !this.serviceno.equals(other.serviceno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Service[ serviceno=" + serviceno + " ]";
    }
    
}
