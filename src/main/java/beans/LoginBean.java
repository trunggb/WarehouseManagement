package beans;

import java.io.Serializable;
import java.util.Optional;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;

import entities.User;
import lombok.Getter;
import lombok.Setter;
import services.EncryptionService;
import services.UserService;

@SuppressWarnings("deprecation")
@ManagedBean(name="loginBean")
@ViewScoped
public class LoginBean implements Serializable{
	/**
	 * 		rfillan0
	 * 		wazVPz
	 */
	
	
	@ManagedProperty(value = "#{userBean}")
	@Setter
	private UserBean userBean;

	private static final long serialVersionUID = 6445092217844175676L;

	@Getter
	@Setter
	private String userName;
	
	@Getter
	@Setter
	private String passWord;
	
	@EJB
	UserService userService;
	
	public void onClickLoginButton() {
		
		String encrypted = EncryptionService.encrypt(passWord);
		Optional<User> user = userService.checkValidUser(userName, encrypted);
		if(user.isPresent()) {
			this.userBean.setLoginUser(user.get());
			PrimeFaces.current().executeScript("top.redirectTo('order.xhtml')");
		}
	}
	
}
