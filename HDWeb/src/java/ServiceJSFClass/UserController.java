package ServiceJSFClass;

import DT.repository.ServiceRepository;
import DT.repository.entities.Service;
import DT.repository.entities.UserGroup;
import ServiceJSFClass.util.JsfUtil;
import static ServiceJSFClass.util.JsfUtil.addErrorMessage;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

@Named("userController")
@RequestScoped
public class UserController implements Serializable {

    //private UserGroup currentUser;
    private UserGroup managedUser;
    private DataModel items = null;
    @EJB
    private ServiceRepository serviceRepository;
    private List<UserGroup> users;
    private int selectedItemIndex;

    public UserController() {
    }

    public List<UserGroup> getAllUser() {
        users = serviceRepository.getAllUser();
        return users;
    }   
  
    public UserGroup getUser() {
        if (managedUser == null) {
            managedUser = new UserGroup();
            selectedItemIndex = -1;
        }return managedUser;
    }

    public void setUser(UserGroup user) {
        this.managedUser = user;
    }
   
     public List<UserGroup> getUsers() {
        return users;
    }

    public void setUser(List<UserGroup> users) {
        this.users = users;
    }

    public String prepareView() {
        managedUser = (UserGroup) getItems().getRowData();
        selectedItemIndex = getItems().getRowIndex();
        return "/worker/ViewUser";
    }

    public String prepareAdd() {
        managedUser = new UserGroup();
        selectedItemIndex = -1;
        return "/worker/CreateUser";
    }
    
     public String prepareEdit() {
        managedUser = new UserGroup();
        managedUser = (UserGroup) getItems().getRowData();
        selectedItemIndex = getItems().getRowIndex();
        return "/worker/EditUser";
    }
    
    public String addUser() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(managedUser.getPassword().getBytes());
        byte byteData[] = md.digest();
        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
        managedUser.setPassword(hexString.toString());
        try {
            serviceRepository.addUser(managedUser);
            JsfUtil.addSuccessMessage("UserCreated");
            return prepareAdd();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Persistence Error Occured");
            return null;
        }
    }
   
    public String update() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(managedUser.getPassword().getBytes());
        byte byteData[] = md.digest();
        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
        managedUser.setPassword(hexString.toString());
        try {
            serviceRepository.editUser(managedUser);
            JsfUtil.addSuccessMessage("User Updated");
            return "/worker/ViewUser";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Persistence Error Occured");
            return null;
        }
    }

    public String destroy() {
        managedUser = (UserGroup) getItems().getRowData();
        selectedItemIndex = getItems().getRowIndex();
        performDestroy();
        items=null;
        return "/worker/ListUser";
    }
        public String destroy(UserGroup ug) {
        managedUser = ug;
        //selectedItemIndex = getItems().getRowIndex();
        performDestroy();
        items=null;
        return "/worker/ListUser";
    }

     public String prepareList() {
        items=null;
        return "/worker/ListUser";
    }

    private void performDestroy() {
        try {
            serviceRepository.removeUser(managedUser);
            JsfUtil.addSuccessMessage("User Deleted");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Persistence Error Occured");
        }
    }

    private void updateCurrentItem() {
        //Int count = serviceRepository.count();
//        if (selectedItemIndex >= count) {
//            // selected index cannot be bigger than number of items:
//            selectedItemIndex = count - 1;
//            // go to previous page if last page disappeared:
//            
//        }
        if (selectedItemIndex >= 0) {
            managedUser = users.get(0);
        }
    }
        
    public DataModel getItems() {
        if (items == null) {
            items = new ListDataModel(getAllUser());
        }
        return items;
    }
    public UserGroup getUser(int id) {
        return serviceRepository.searchUserById(id);
    }


        int getKey(String value) {
            int key;
            key = Integer.parseInt(value);
            return key;
        }

        String getStringKey(int value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

    public String searchUser(UserGroup ug){
        try {
            items= null;
        if(ug.getFirstName().equals("")&&ug.getLastName().equals("")&&ug.getEmail().equals(""))
            {
                int id=ug.getUserId();
                List<UserGroup> ul = new ArrayList<UserGroup>();
                ul.add(serviceRepository.searchUserById(id));
                this.items = new ListDataModel(ul);
            }
        else
            {
                List<UserGroup> ul = serviceRepository.searchUser(ug);
                this.items = new ListDataModel(ul);
            }
            return "/worker/ListUser";
        } catch (Exception ex) {
            addErrorMessage("Please enter some message for search");
            return null;
        }
    }
    }

