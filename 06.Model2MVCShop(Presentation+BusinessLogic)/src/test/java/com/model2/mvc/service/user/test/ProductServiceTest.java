package com.model2.mvc.service.user.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;


/*
 *	FileName :  UserServiceTest.java
 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
 * �� @ContextConfiguration : Meta-data location ����
 * �� @Test : �׽�Ʈ ���� �ҽ� ����
 */
@RunWith(SpringJUnit4ClassRunner.class)
//==> Meta-Data �� �پ��ϰ� Wiring ����...
//@ContextConfiguration(locations = { "classpath:config/context-*.xml" })
@ContextConfiguration(locations = {	"classpath:config/context-common.xml",
																		"classpath:config/context-aspect.xml",
																		"classpath:config/context-mybatis.xml",
																		"classpath:config/context-transaction.xml" })
//@ContextConfiguration(locations = { "classpath:config/context-common.xml" })
public class ProductServiceTest {

	//==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	//@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
		product.setProdName("gkswndks");
		product.setProdQuantity(50);
		
		productService.addProduct(product);
		
		//==> API Ȯ��
		Assert.assertEquals("gkswndks", product.getProdName());
		Assert.assertEquals(50, product.getProdQuantity());

	}
	
	//@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();
		product = productService.getProduct(10000);

		//==> console Ȯ��
		System.out.println(productService.getProduct(10000));
		
		//==> API Ȯ��
		Assert.assertEquals(100000, product.getPrice());
		
	}	
		
		//@Test
		 public void testUpdateProduct() throws Exception{
			 
				Product product = productService.getProduct(10000);
				Assert.assertNotNull(product);

				product.setProdName("change");
				product.setProdDetail("changeDeatail");
				product.setPrice(100000);
				product.setFileName("changefileName");
				product.setProdNo(10000);
			
				productService.updateProduct(product);
				
				//==> console Ȯ��
				System.out.println(product);
					
				//==> API Ȯ��
				Assert.assertEquals("change", product.getProdName());
				Assert.assertEquals("changeDeatail", product.getProdDetail());
			 }
		 
		 //@Test
		 public void testUpdateProductQuantity() throws Exception{
			 
				Product product = productService.getProduct(10019);

				product.setProdQuantity(455);
			
				productService.updateProduct(product);
				
				//==> console Ȯ��
				System.out.println(product);
					
				//==> API Ȯ��
				Assert.assertEquals(50, product.getProdQuantity());
			 }
		
		@Test
		 public void testGetProductListAll() throws Exception{
			 
		 	Search search = new Search();
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	Map<String,Object> map = productService.getProductList(search);
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	Assert.assertEquals(3, list.size());
		 	
			//==> console Ȯ��
		 	//System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	search.setSearchCondition("0");
		 	search.setSearchKeyword("");
		 	map = productService.getProductList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	Assert.assertEquals(3, list.size());
		 	
		 	//==> console Ȯ��
		 	System.out.println(list);
		 	
		 	totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 }
		
		//@Test
		 public void testGetProdListByProdName() throws Exception{
			 
		 	Search search = new Search();
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	Map<String,Object> map = productService.getProductList(search);
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	//Assert.assertEquals(3, list.size());
		 	
			//==> console Ȯ��
		 	//System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	search.setSearchCondition("1");
		 	search.setSearchKeyword("����");
		 	System.out.println(search);
		 	map = productService.getProductList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	//Assert.assertEquals(0, list.size());
		 	
			//==> console Ȯ��
		 	System.out.println(list);
		 	
		 	totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
			System.out.println("=======================================");
		 }
		
}