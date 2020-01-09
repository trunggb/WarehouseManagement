package services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import entities.Product;
import entities.ProductStatus;

public class ProductServiceTest {
	
	@InjectMocks
	ProductService productService;
	
	private EntityManager entityManager;
	
	private Product product;
	
	@Before
	public void init() {
		entityManager = Mockito.mock(EntityManager.class);
		productService = Mockito.spy(new ProductService());
		product = Product.builder()
				.id(1)
				.name("Winner V1")
				.weight(50)
				.status(ProductStatus.AVAILABLE)
				.inDate(new Date())
				.build();
	}


	@Test
	public void testAdd_shouldReturnTrue_whenProductIsGiven() {
		Mockito.doReturn(true).when((GenericService<Product>) productService).create(product);
		boolean actual = productService.add(product);
		assertTrue(actual);
	}
	
	@Test
	public void testAdd_shouldReturnFalse_whenProductIsNull() {
		Mockito.doReturn(false).when((GenericService<Product>) productService).create(null);
		boolean actual = productService.add(null);
		assertFalse(actual);
	}
	
	@Test
	public void testUpdate_shouldReturnTrue_whenProductIsGiven() {
		Mockito.doReturn(true).when((GenericService<Product>) productService).update(product);
		boolean actual = productService.update(product);
		assertTrue(actual);
	}
	
	@Test
	public void testUpdate_shouldReturnFalse_whenProductIsNull() {
		Mockito.doReturn(false).when((GenericService<Product>) productService).update(null);
		boolean actual = productService.update(null);
		assertFalse(actual);
	}
	
//	@Test
//	public void testRemove_shouldReturnTrue_whenProductIsGiven() {
//		Mockito.doReturn(true).when((GenericService<Product>) productService).delete(product);
//		boolean actual = productService.remove(product);
//		assertTrue(actual);
//	}
//	
//	@Test
//	public void testRemove_shouldReturnFalse_whenProductIsNull() {
//		Mockito.doReturn(false).when((GenericService<Product>) productService).delete(null);
//		boolean actual = productService.remove(null);
//		assertFalse(actual);
//	
//	}
//	@Ignore
//	@Test
//	public void testFind_shouldReturnOptionalOfProduct_whenIdIsValid() {
//		int id = 1;
//		Mockito.when(entityManager.find(Product.class, id)).thenReturn(product);
//		Optional<Product> actual = productService.find(id);
//		Optional<Product> expected = Optional.of(product);
//		assertEquals(expected, actual);
//	}
	
}
