package ServiceJSFClass;

import DT.repository.ServiceRepository;
import DT.repository.entities.Service;
import ServiceJSFClass.util.JsfUtil;
import DT.repository.entities.UserGroup;
import DT.repository.entities.ServiceUse;
import static ServiceJSFClass.util.JsfUtil.addErrorMessage;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
//import javax.faces.convert.Converter;
//import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpServletRequest;

@Named("serviceController")
@SessionScoped
public class ServiceController implements Serializable {

    private Service currentService;
    private List<Service> services;
    @EJB
    private ServiceRepository serviceRepository;
    private DataModel items = null;
    private int selectedItemIndex;
    private UserGroup currentUser;
    private UserGroup managedUser;
    private List<UserGroup> users;
    private ServiceUse serviceUse;
    private List<ServiceUse> serviceUses;
    
    public ServiceController() {
        
    }

    public List<Service> getAllService() throws Exception {
        services = serviceRepository.getAllServices();
        return services;
    }
    
    public Service getSelected() {
        if (currentService == null) {
            currentService = new Service();
            selectedItemIndex = -1;
        }
        return currentService;
    }

    public String prepareView() throws Exception {
        currentService = (Service) getItems().getRowData();
        selectedItemIndex = getItems().getRowIndex();
        return "/public/pView";
    }
    public String prepareGovView() throws Exception {
        currentService = (Service) getItems().getRowData();
        selectedItemIndex = getItems().getRowIndex();
        return "/worker/govView";
    }

    public String prepareAdd() {
        currentService = new Service();
        selectedItemIndex = -1;
        return "/worker/Create";
    }
    
     public String prepareEdit() throws Exception {
        currentService = (Service) getItems().getRowData();
        selectedItemIndex = getItems().getRowIndex();
        return "/worker/Edit";
    }

    public String addService() {
        try {
            currentService.setStatus("available");
            serviceRepository.addService(currentService);
            JsfUtil.addSuccessMessage("ServiceCreated");
            return prepareAdd();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
   
    public String update() {
        try {
            serviceRepository.editService(currentService);
            JsfUtil.addSuccessMessage("Service Updated");
            return "/worker/govView";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("Persistence Error Occured"));
            return null;
        }
    }

    public String destroy() {
        try {
            currentService = (Service) getItems().getRowData();
            selectedItemIndex = getItems().getRowIndex();
            performDestroy();
            items=null;
            return "/worker/govList";
        } catch (Exception ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

     public String prepareList() {
        currentService = new Service();
        selectedItemIndex = -1;
        if (currentUser.getType().equals("Gov Worker")){
            items=null;    
            return "/worker/govList";
            } else { 
            items=null;
            return "/public/pList";
            }
    }
//     
//    public String destroyAndView() {
//        performDestroy();
//        items=null;
//        updateCurrentItem();
//        if (selectedItemIndex >= 0) {
//            return "/worker/govView";
//        } else {
//            items=null;
//            return "/worker/govList";
//        }
//    }

    private void performDestroy() {
        try {
            currentService.setStatus("removed");
            serviceRepository.editService(currentService);
            JsfUtil.addSuccessMessage("Service Deleted");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Persistence Error Occured");
        }
    }

    private void updateCurrentItem() {
        if (selectedItemIndex >= 0) {
            currentService = services.get(0);
        }
    }
    
    public DataModel getItems() throws Exception {
        if (items == null) {
            items = new ListDataModel(getAllService());
        }
        return items;
    }
    
    public String searchService(){
        try {
            items= null;
            List<Service> sl = serviceRepository.searchService(currentService);
            this.items = new ListDataModel(sl);
            currentService = new Service();
            if (currentUser.getType().equals("Gov Worker")){
                return "/worker/govList";
            } else {
                return "/public/pList";
            }
        } catch (Exception ex) {
            addErrorMessage("Please enter some message for search");
            return null;
        }
    }
  
    public String bookService(Service s){
        if(s.getStatus().equals("available")){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        
        List<UserGroup> us = this.serviceRepository.getAllUser();
        List<UserGroup> workers = new ArrayList<UserGroup>();
        for (UserGroup u : us){
            if (u.getType().equals("Gov Worker")){
                workers.add(u);
            }
        }
        Random r = new Random();
        UserGroup worker = workers.get(r.nextInt(workers.size()));
        
        this.serviceUse.setUser(this.currentUser);
        this.serviceUse.setServices(s);
        this.serviceUse.setUseDate(date);
        this.serviceUse.setLinkWoker(worker.getFirstName() + " " + worker.getLastName());
        
        this.serviceRepository.addServiceUse(this.serviceUse);
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Book Successful !"));
        this.serviceUse = new ServiceUse();
        this.currentService = new Service();
        return "/public/bookingRecord";}
        else {
        this.serviceUse.setUser(this.currentUser);
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Sorry, this service is not available now !"));
        return null;}
    }
    public List<UserGroup> getAllUser() {
        users = serviceRepository.getAllUser();
        return users;
    }   
  
    public UserGroup getUser() {
        return currentUser;
    }

    public void setCurrentUser(UserGroup user) {
        this.currentUser = user;
    }
    
    public UserGroup getSelectedUser() {
        if (currentUser == null) {
            currentUser = new UserGroup();
            selectedItemIndex = -1;
        }
        return currentUser;
    }
    
     public List<UserGroup> getUsers() {
        return users;
    }

    public void setUser(List<UserGroup> users) {
        this.users = users;
    }

    public ServiceUse getServiceUse() {
        return serviceUse;
    }

    public void setServiceUse(ServiceUse serviceUse) {
        this.serviceUse = serviceUse;
    }

    public List<ServiceUse> getServiceUses() {
        return serviceUses;
    }

    public void setServiceUses(List<ServiceUse> serviceUses) {
        this.serviceUses = serviceUses;
    }

    public String login(){
        Map map = new HashMap();
        map.put("email", this.currentUser.getEmail());
        map.put("password", this.currentUser.getPassword());
        List<UserGroup> u = this.serviceRepository.login(map);
        if (u.size() > 0){
            this.currentUser = u.get(0);
            if (u.get(0).getType().equals("Gov Worker")){
                return "/worker/govList";
            } else {
                return "/public/pList";
            }
        } else {
             FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("no user is found. Please try again"));
             return "/index";
        }
    }
    public List<ServiceUse> getServiceUseByUser(){
        //int userId = this.currentUser.getUserId();
        serviceUses = serviceRepository.getServiceUseByUser(this.currentUser);
        return serviceUses;
    }
        public List<ServiceUse> getServiceUseBypUser(UserGroup u){
        //int userId = this.currentUser.getUserId();
        serviceUses = serviceRepository.getServiceUseByUser(u);
        return serviceUses;
    }
        @PostConstruct
    public void init() {
        this.currentService = new Service();
        this.currentUser = new UserGroup();
        this.serviceUse = new ServiceUse();
    }
    public UserGroup loginButton(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String username = request.getRemoteUser();
        currentUser=serviceRepository.getUserByEmail(username).get(0);
        return currentUser;
    }
    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index";
    }

    public UserGroup getManagedUser() {
        return managedUser;
    }

    public void setManagedUser(UserGroup managedUser) {
        this.managedUser = managedUser;
    }
    
}