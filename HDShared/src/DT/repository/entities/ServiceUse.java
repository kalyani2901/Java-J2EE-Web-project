/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DT.repository.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kalyani
 */
@Entity
@Table(name = "service_use")
@NamedQueries({
    @NamedQuery(name = ServiceUse.ALL_RECORDS, query = "SELECT su FROM ServiceUse su"),
    @NamedQuery(name = ServiceUse.GET_BY_USER,
            query = "SELECT su FROM ServiceUse su where su.user =:the_user")})
@XmlRootElement
public class ServiceUse implements Serializable{
    public static final String ALL_RECORDS = "service_use.allRecords";
    public static final String GET_BY_USER = "service_use.getByUser";
    
    @Id
    @Column(name = "service_use_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int serviceUseId;
    private String useDate;
    @ManyToOne
    @JoinColumn(name = "the_user", nullable = false)
    private UserGroup user;
    @ManyToOne
    @JoinColumn(name = "used_service", nullable = false)
    private Service services;
    @Column(name = "linked_worker")
    private String linkWoker;
    private boolean Status;
    
    public int getServiceUseId() {
        return serviceUseId;
    }

    public void setServiceUseId(int serviceUseId) {
        this.serviceUseId = serviceUseId;
    }

    public String getUseDate() {
        return useDate;
    }

    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }

    public UserGroup getUser() {
        return user;
    }

    public void setUser(UserGroup user) {
        this.user = user;
    }

    public Service getServices() {
        return services;
    }

    public void setServices(Service services) {
        this.services = services;
    }

    public String getLinkWoker() {
        return linkWoker;
    }

    public void setLinkWoker(String linkWoker) {
        this.linkWoker = linkWoker;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean Status) {
        this.Status = Status;
    }
    
    
}
