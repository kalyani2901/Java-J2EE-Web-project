package DT.repository;

import DT.repository.entities.*;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public interface ServiceRepository {

    public void addService(Service service) throws Exception;
    
    public void removeService(Service service) throws Exception;

    public void editService(Service service) throws Exception;

    public List<Service> searchService(Service s) throws Exception;

    public List<Service> getAllServices();

    //public int count();
    
    public void addUser(UserGroup user) throws Exception;

    public UserGroup searchUserById(int no);

//    public List<UserGroup> searchUserByFname(String fName) throws Exception;
//
//    public List<UserGroup> searchUserByLname(String lName) throws Exception;
//    
    public List<UserGroup> searchUser(UserGroup ug) throws Exception;
            
    public List<UserGroup> getAllUser();

    public void removeUser(UserGroup user) throws Exception;

    public void editUser(UserGroup user) throws Exception;

    public void close();
    public List<UserGroup> login(Map details);
    //public List<UserGroup> findAllWorker();
    public void addServiceUse(ServiceUse serviceUse);
    public List<ServiceUse> getServiceUseByUser(UserGroup user);
    public List<UserGroup> getUserByEmail(String email);
    public List<ServiceUse> getServiceUse();
}
