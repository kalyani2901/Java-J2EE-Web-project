/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DT.repository.entities;

import java.io.Serializable;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kalyani
 */

@Entity
@NamedQueries({
    @NamedQuery(name = UserGroup.GET_ALL, query = "SELECT u FROM UserGroup u"),
    @NamedQuery(name = UserGroup.GET_BY_EMAIL, query = "SELECT u FROM UserGroup u where u.email = :email"),
    @NamedQuery(name = "getUserByEmailAndPassword", query = "SELECT u FROM UserGroup u WHERE u.email = :email AND u.password = :password"),
    @NamedQuery(name = UserGroup.SEARCH, query = "SELECT u FROM UserGroup u WHERE u.userId =:id OR u.firstName =:fName OR u.lastName =:lName OR u.email = :email OR u.type=:type")})

@XmlRootElement
public class UserGroup implements Serializable {
    
    public static final String GET_ALL = "UserGroup.getAll";
    public static final String GET_BY_EMAIL = "UserGroup.getByEmail";
    public static final String SEARCH = "UserGroup.search";
    
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
    private String lastName;
    private String firstName;
    @Column(unique=true)
    private String email;
    private String password;
    private String type;
    private String address;
    private String phoneNumber;
    
    
    public UserGroup(int userId, String lastName, String firstName, String email, String password, String type, String address, String phoneNumber) {
        this.userId = userId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.type = type;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
    
    public UserGroup(UserGroup u) {
        this.userId = u.userId;
        this.lastName = u.lastName;
        this.firstName = u.firstName;
        this.email = u.email;
        this.password = u.password;
        this.type = u.type;
        this.address = u.address;
        this.phoneNumber = u.phoneNumber;
    }
    
    public UserGroup() {
        
    }
    
     public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Pattern(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",
            message = "Invalid email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Pattern(regexp = "^\\({0,1}((0|\\+61)(2|4|3|7|8)){0,1}\\){0,1}(\\ |-){0,1}[0-9]{2}(\\ |-){0,1}[0-9]{2}(\\ |-){0,1}[0-9]{1}(\\ |-){0,1}[0-9]{3}$",
    //@Pattern(regexp = "(0[0-9]{10}) | (9[0-9]{8})",
             message = "Invalid Phone Number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @OneToMany(cascade = ALL, mappedBy = "user")
    private Set<ServiceUse> serviceUse;
        
    public Set<ServiceUse> getServiceUses() {
        return serviceUse;
    }

    public void setServiceUses(Set<ServiceUse> serviceUses) {
        this.serviceUse = serviceUses;
    }

    @Override
    public String toString() {
        return userId + " " + lastName + " " + firstName + " " + email + " " + password+ " "+ type + " " + address+ " " + phoneNumber;
    }

}

