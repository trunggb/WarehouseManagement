package beans;

import java.io.Serializable;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import entities.Product;
import entities.ProductStatus;
import lombok.Getter;
import lombok.Setter;
import services.ProductService;

@SuppressWarnings("deprecation")
@ManagedBean(name="productUpdateBean")
@ViewScoped
public class ProductUpdateBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4098294323352760786L;
	@Setter
	@Getter
	private Product product;
	
	@EJB
	ProductService productService;
	
	final static Logger logger = Logger.getLogger(ProductService.class);
	
	@PostConstruct
	public void init() {
		String productIdAsString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("productId");
		if(StringUtils.isNotBlank(productIdAsString)) {
			Optional<Product> optionalProduct = productService.find(Integer.valueOf(productIdAsString));
			if(optionalProduct.isPresent()) {
				this.product = optionalProduct.get();
			}else {
				logger.info("Can not find product with id: " + productIdAsString);
			}
		}else {
			logger.error("Can not get id of product from list product");
		}
		
	}
	
	public ProductStatus[] getProductStatus() {
		return ProductStatus.values();
	}
	
	public void update() {
		if(this.productService.updateProduct(product)) {
			logger.info("Update product succesfully");
			PrimeFaces.current().executeScript("parent.showSuccessMessage('Product updated succesfully!')");
		}else {
			logger.info("Can not update products");
			PrimeFaces.current().executeScript("parent.showErrorMessage('Product remove failed!')");
		}
		
		PrimeFaces.current().executeScript("parent.reloadPage();");
	}
}
