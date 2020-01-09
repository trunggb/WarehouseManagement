package beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import entities.User;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("deprecation")
@ManagedBean(name="userBean")
@SessionScoped
public class UserBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6445092217844175676L;
	
	@Getter
	@Setter
	private User loginUser;
	
}
