package beans;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import entities.Role;
import entities.User;
import lombok.Getter;
import lombok.Setter;
import services.DialogService;
import services.ProductService;
import services.UserService;

@SuppressWarnings("deprecation")
@ManagedBean(name="userManagementBean")
@ViewScoped
public class UserManagementBean implements Serializable {
	/**
	 * 
	 */
	
	@ManagedProperty(value = "#{userBean}")
	@Setter
	private UserBean userBean;
	
	private static final long serialVersionUID = 4098294323352760786L;
	@Getter
	@Setter
	private transient List<User> users;
	
	@Getter
	@Setter
	private transient List<User> usersFiltered;

	@Getter
	@Setter
	private User loginUser;
	
	@Inject
	UserService userService;

	@Inject
	DialogService dialogService;

	Logger logger = Logger.getLogger(ProductService.class);

	@PostConstruct
	public void init() {
		if(Objects.isNull(userBean.getLoginUser()) || userBean.getLoginUser().getRole() != Role.ADMIN) {
			PrimeFaces.current().executeScript("top.redirectTo('index.xhtml')");
		}
		this.loginUser = userBean.getLoginUser();
		this.users = userService.findAll();
	}
	
	public void onClickProductButton() {
		PrimeFaces.current().executeScript("top.redirectTo('product.xhtml')");
	}
	public void onClickOrderButton() {
		PrimeFaces.current().executeScript("top.redirectTo('order.xhtml')");
	}
	
	public void onClickLogoutButton() {
		userBean.setLoginUser(null);
		PrimeFaces.current().executeScript("top.redirectTo('index.xhtml')");
	}
}
