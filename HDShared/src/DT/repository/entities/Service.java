package DT.repository.entities;
import java.io.Serializable;//import serializable
import java.text.NumberFormat;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kalyani
 */
@Entity //set Service as an entity
@NamedQueries({@NamedQuery(name = Service.FIND_ALL, query = "SELECT s FROM Service s where s.status <> 'removed'"),
               @NamedQuery(name = Service.FIND_SERVICE, query = "SELECT s FROM Service s where s.type like :type OR s.serviceName like :name OR s.serviceNo = :no OR s.description like :desc")})//define query

@XmlRootElement
public class Service implements Serializable {//implement serializable
    
    public static final String FIND_ALL =  "Service.findAll";
    //public static final String FIND_by_type =  "Service.search_by_type";
    public static final String FIND_SERVICE =  "Service.findService";

    @Id
    @Column(name = "serviceNo")
    private int serviceNo;
    private String serviceName;
    @Column(nullable=false)
    private String type;
    private String thumbnail;
    private String description;
    private String status;
    public Service() {

    }

    public Service(int serviceNo, String serviceName, String type, String thumbnail, String description) {
        this.serviceNo = serviceNo;
        this.serviceName = serviceName;
        this.type = type;
        this.thumbnail = thumbnail;
        this.description = description;
        this.status = "available";
    }

    public Service(Service s) {
        this.serviceNo = s.serviceNo;
        this.serviceName = s.serviceName;
        this.type = s.type;
        this.thumbnail = s.thumbnail;
        this.description = s.description;
        this.status = s.status;
    }
    /**
     * @return the value of attribute propertyId
     */
    public int getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(int serviceNo) {
        this.serviceNo = serviceNo;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * @return a string representation of the property object
     */
    @OneToMany(cascade = ALL, mappedBy = "services")
    private Set<ServiceUse> serviceUse;
    
    public Set<ServiceUse> getServiceUses() {
        return serviceUse;
    }

    public void setServiceUses(Set<ServiceUse> serviceUse) {
        this.serviceUse = serviceUse;
    }
    @Override
    public String toString() {
        return serviceNo + " " + serviceName + " " + type + " " + thumbnail + " " + description + " " + status;
    }
}