package DT.repository;

import DT.repository.entities.Service;
import DT.repository.entities.ServiceUse;
import DT.repository.entities.UserGroup;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author kalyani
 */
@Stateful
public class JPAServiceRepositoryImpl implements ServiceRepository {
    
    //private static final String PERSISTENCE_UNIT = "CreditTask";
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addService(Service service){
        entityManager.persist(service);}
    
    @Override
    public void removeService(Service service) throws Exception {        
            entityManager.remove(entityManager.merge(service));
        }  
    
    @Override
    public void editService(Service service) throws Exception {
      entityManager.merge(service);  
    }
    @Override
    public List<Service> searchService(Service service){
        Query query = this.entityManager.createNamedQuery(Service.FIND_SERVICE);
        query.setParameter("no", service.getServiceNo());
        query.setParameter("name", service.getServiceName());
        query.setParameter("type", service.getType());
        query.setParameter("desc", service.getDescription());
        System.out.print(query);
        return query.getResultList();
    }
    
    @Override
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Service> getAllServices() {
        return this.entityManager.createNamedQuery(Service.FIND_ALL).getResultList();
    }
    
//    @Override    
//    public int count() {
//        javax.persistence.criteria.CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
//        javax.persistence.criteria.Root<Service> rt = cq.from(Service.class);
//        cq.select(entityManager.getCriteriaBuilder().count(rt));
//        javax.persistence.Query q = entityManager.createQuery(cq);
//        return ((Long) q.getSingleResult()).intValue();
//    }
////    
    @Override
    public UserGroup searchUserById(int id) {
        //UserGroup user = this.entityManager.find(UserGroup.class, id);  
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
        Root employee = criteriaQuery.from(UserGroup.class);
        criteriaQuery.where(criteriaBuilder.equal(employee.get("userId"), criteriaBuilder.parameter(Long.class, "userId")));
        Query query = entityManager.createQuery(criteriaQuery);
        query.setParameter("userId", id);
        UserGroup result2 = (UserGroup)query.getSingleResult();
        return result2;    
    }
    
//    @Override
//    public List<UserGroup> searchUserByFname(String fName) throws Exception {
//        return this.entityManager.createNamedQuery(UserGroup.GET_BY_FNAME).setParameter("fName", fName).getResultList();           
//    }
//    
//     @Override
//    public List<UserGroup> searchUserByLname(String lName) throws Exception {
//        return this.entityManager.createNamedQuery(UserGroup.GET_BY_LNAME).setParameter("lName", lName).getResultList();           
//    }
    
    @Override
    public List<UserGroup> searchUser(UserGroup ug) throws Exception {
        Query query = this.entityManager.createNamedQuery(UserGroup.SEARCH);
        query.setParameter("id", ug.getUserId());
        query.setParameter("fName", ug.getFirstName());
        query.setParameter("lName", ug.getLastName());
        query.setParameter("email", ug.getEmail());
        query.setParameter("type", ug.getEmail());
        System.out.print(query);
        return query.getResultList();          
    }
    
    @Override
    public List<UserGroup> getAllUser(){
        return this.entityManager.createNamedQuery(UserGroup.GET_ALL).getResultList();
    }
    
    @Override
    public void addUser(UserGroup user) throws Exception { 
        entityManager.persist(user);
    }
    
     @Override
    public void removeUser(UserGroup user) throws Exception {        
            entityManager.remove(entityManager.merge(user));
        }  
    
    @Override
    public void editUser(UserGroup user) throws Exception {
      entityManager.merge(user);  
    }

    @Override
    public void close() {
    }
//    @Override
//    public List<UserGroup> findAllWorker() {
//        return this.entityManager.createNamedQuery("GET_ALL", UserGroup.class).getResultList();
//    }
    @Override
    public void addServiceUse(ServiceUse serviceUse) {
        this.entityManager.persist(serviceUse);
    }
    @Override
    public List<ServiceUse> getServiceUse() {
        return this.entityManager.createNamedQuery(ServiceUse.ALL_RECORDS).getResultList();
    }
    @Override
    public List<ServiceUse> getServiceUseByUser(UserGroup the_user) {
        return this.entityManager.createNamedQuery(ServiceUse.GET_BY_USER).setParameter("the_user", the_user).getResultList();
    }
    @Override
    public List<UserGroup> getUserByEmail(String email) {
        return this.entityManager.createNamedQuery(UserGroup.GET_BY_EMAIL).setParameter("email", email).getResultList();
    }
    @Override
    public List<UserGroup> login(Map details) {
        Query query = this.entityManager.createNamedQuery("getUserByEmailAndPassword", UserGroup.class);
        query.setParameter("email", details.get("email"));
        query.setParameter("password", details.get("password"));
        return query.getResultList();
    }
}