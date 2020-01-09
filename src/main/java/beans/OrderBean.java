package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import entities.Location;
import entities.Order;
import entities.OrderDetail;
import entities.OrderStatus;
import entities.Product;
import entities.Role;
import entities.User;
import lombok.Getter;
import lombok.Setter;
import services.DialogService;
import services.LogService;
import services.OrderDetailService;
import services.OrderService;
import services.ProductService;
import services.UserService;

@SuppressWarnings("deprecation")
@ManagedBean(name = "orderBean")
@ViewScoped
public class OrderBean implements Serializable {

	private static final int NUM_PROD = 5;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4098294323352760786L;

	@ManagedProperty(value = "#{userBean}")
	@Setter
	private UserBean userBean;

	@Getter
	@Setter
	private List<Order> orders;

	@Getter
	@Setter
	private Order processedOrder;

	@Getter
	@Setter
	private List<User> drivers;

	@Getter
	@Setter
	private List<Order> ordersProcessed;

	@Getter
	@Setter
	private transient List<Order> orderFiltered;

	@Getter
	@Setter
	private User loginUser;

	@Getter
	@Setter
	private User driver = new User();

	@Getter
	@Setter
	private List<Product> products;

	@Getter
	@Setter
	private List<OrderDetail> orderDetails;

	@Getter
	@Setter
	private List<Product> selectedProducts;

	@Getter
	@Setter
	private Order removedOrder;

	@Getter
	@Setter
	private String note;

	@Getter
	@Setter
	private List<Location> availableReception;

	@Getter
	@Setter
	private Integer orderId;

	@Getter
	@Setter
	private List<String> allStatus = new ArrayList<>();

	@Inject
	OrderService orderService;

	@Inject
	ProductService productService;

	@Inject
	DialogService dialogService;

	@Inject
	OrderDetailService orderDetailService;

	@EJB
	UserService userService;

	@EJB
	LogService logService;

	Logger logger = Logger.getLogger(ProductService.class);

	@PostConstruct
	public void init() {
		if (Objects.isNull(userBean.getLoginUser())) {
			PrimeFaces.current().executeScript("top.redirectTo('index.xhtml')");
		} else {
			this.loginUser = userBean.getLoginUser();
			this.orders = orderService.findAll();
			this.ordersProcessed = orders.stream().filter(o -> OrderStatus.PROCESSING.equals(o.getStatus()))
					.collect(Collectors.toList());
			if (loginUser.getRole().equals(Role.USER)) {
				this.orders = orders.stream()
						.filter(order -> order.getCreator().getEmail().equals(loginUser.getEmail()))
						.collect(Collectors.toList());
			} else if (loginUser.getRole().equals(Role.DRIVER)) {
				this.orders = ordersProcessed.stream()
						.filter(order -> order.getShipper().getEmail().equals(loginUser.getEmail()))
						.collect(Collectors.toList());
			}
			OrderStatus[] statuses = OrderStatus.values();
			for (OrderStatus orderStatus : statuses) {
				allStatus.add(orderStatus.toString());
			}

			List<User> users = userService.findAll();
			this.drivers = users.stream().filter(x -> x.getRole() == Role.DRIVER).collect(Collectors.toList());

			this.products = productService.findAll();
			this.orderDetails = new ArrayList<>();
			for (int i = 0; i < NUM_PROD; i++) {
				Product product = Product.builder().id(-1).build();
				OrderDetail orderDetail = OrderDetail.builder().product(product).amount(0).build();
				this.orderDetails.add(orderDetail);
			}
		}

	}

	public void onClickProductButton() {
		PrimeFaces.current().executeScript("top.redirectTo('product.xhtml')");
		logger.debug("Get " + orders.size() + " orders from database");
	}

	public void onClickCreateOrder() {
		// Get data from create dialog
		for (OrderDetail orderDetail : orderDetails) {
			Optional<Product> optionalProduct = productService.find(orderDetail.getProduct().getId());
			if (optionalProduct.isPresent()) {
				Product product = optionalProduct.get();
				int remain = product.getAmount() - orderDetail.getAmount();
				if (remain >= 0 && orderDetail.getAmount() > 0) {
					orderDetail.setProduct(product);
					product.setAmount(remain);
					productService.update(product);
				}
			}
		}

		orderDetails = orderDetails.stream().filter(detail -> Objects.nonNull(detail.getProduct().getName()))
				.collect(Collectors.toList());
		User user = this.loginUser;
		Order order = Order.builder().createdDate(new Date()).creator(user).note(note).status(OrderStatus.CREATED)
				.orderDetails(orderDetails).build();

		this.orderService.createOrder(order);
		PrimeFaces.current().executeScript("showSuccessMessage('Order created succesfully!')");
		PrimeFaces.current().executeScript("reloadPage();");
	}

	public void viewOrder(Integer orderId) {
		List<OrderDetail> details = orderDetailService.findDetailsByOrderId(orderId);
		int height = 55 + (details.size() + 1) * 35 + 180;
		Map<String, Object> options = dialogService.createDialogOption(550, height);

		Map<String, List<String>> params = new HashMap<>();
		List<String> productId = new ArrayList<>(); // just send one id
		productId.add(String.valueOf(orderId));
		params.put("orderId", productId);
		PrimeFaces.current().dialog().openDynamic("viewOrder", options, params);
	}

	public void updateOrder(Integer orderId) {
		Map<String, Object> options = dialogService.createDialogOption(650, 600);

		Map<String, List<String>> params = new HashMap<>();
		List<String> productId = new ArrayList<>(); // just send one id
		productId.add(String.valueOf(orderId));
		params.put("orderId", productId);
		PrimeFaces.current().dialog().openDynamic("updateOrder", options, params);
	}

	public void onClickRemoveButton(int orderId) {
		Optional<Order> optionalOrder = orderService.find(orderId);
		if (optionalOrder.isPresent()) {
			this.removedOrder = optionalOrder.get();
			PrimeFaces.current().executeScript("PF('remove-order-dialog').show();");
		} else {
			logger.warn("Can not find order with id " + orderId);
		}
	}

	public void onClickProcessOrder(int orderId) {
		Optional<Order> optionalOrder = orderService.find(orderId);
		if (optionalOrder.isPresent()) {
			this.processedOrder = optionalOrder.get();
			PrimeFaces.current().executeScript("PF('process-order-dialog').show();");
		} else {
			logger.warn("Can not find order with id " + orderId);
		}
	}

	public void writeLog() {
//		Log log = Log.builder().action(Action.DELETE).user(userBean.getLoginUser()).logTime(new Date())
//				.note("order  " + o).build();
//		logService.add(log);
	}

	public void processOrder() {
		if (Objects.nonNull(driver.getId())) {
			Optional<User> optionalDriver = userService.findUser(driver.getId());
			if (optionalDriver.isPresent()) {
				this.driver = optionalDriver.get();
				this.processedOrder.setShipper(driver);
				this.processedOrder.setStatus(OrderStatus.PROCESSING);
				orderService.updateOrder(processedOrder);
				PrimeFaces.current().executeScript("showSuccessMessage('Order processed succesfully!')");
				PrimeFaces.current().executeScript("reloadPage()");
			} else {
				PrimeFaces.current().executeScript("showErrorMessage('Order process failed!')");
				logger.warn("Can not find user with id " + driver.getId());
			}
		}

	}

	public void removeOrder() {
		orderService.remove(removedOrder);
		PrimeFaces.current().executeScript("showSuccessMessage('Order removed succesfully!')");
		PrimeFaces.current().executeScript("reloadPage()");
	}

	public void onClickLogoutButton() {
		userBean.setLoginUser(null);
		PrimeFaces.current().executeScript("top.redirectTo('index.xhtml')");
	}

	public void onClickHistoryButton() {
		PrimeFaces.current().executeScript("top.redirectTo('history.xhtml')");
	}

	public void onClickUserButton() {
		PrimeFaces.current().executeScript("top.redirectTo('user.xhtml')");
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {

		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	public void onClickCheckButton(int orderId) {
		Optional<Order> optionalOrder = orderService.find(orderId);
		if (optionalOrder.isPresent()) {
			this.processedOrder = optionalOrder.get();
			this.processedOrder.setStatus(OrderStatus.DONE);
			orderService.updateOrder(processedOrder);
			PrimeFaces.current().executeScript("showSuccessMessage('Order delivery succesfully!')");
			PrimeFaces.current().executeScript("reloadPage()");
		} else {
			logger.warn("Can not find order with id " + orderId);
		}
	}

	public void onClickTimesButton(int orderId) {
		Optional<Order> optionalOrder = orderService.find(orderId);
		if (optionalOrder.isPresent()) {
			this.processedOrder = optionalOrder.get();
			this.processedOrder.setStatus(OrderStatus.FAILURE);
			orderService.updateOrder(processedOrder);
			PrimeFaces.current().executeScript("showSuccessMessage('Order rollback succesfully!')");
			PrimeFaces.current().executeScript("reloadPage()");
		} else {
			logger.warn("Can not find order with id " + orderId);
		}
	}
}