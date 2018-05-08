package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

//==> 회원관리 Controller
@Controller
public class PurchaseController {
	
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	
	//setter Method 구현 않음
	public PurchaseController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping("/addPurchaseView.do")
	public ModelAndView addUserView(@RequestParam("prodNo") int prodNo) throws Exception{

		System.out.println("/addPurchaseView.jsp");
		
		Product product = productService.getProduct(prodNo);
		
		System.out.println("product : "+product );
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/addPurchaseView.jsp");
		modelAndView.addObject("product", product);
		
		return modelAndView;
	}
	
	@RequestMapping("/addPurchase.do")
	public ModelAndView addPurchase( @ModelAttribute("purchase") Purchase purchase,
																		  @ModelAttribute("product") Product product,
																		  @RequestParam("buyerId") String buyerId,
																		  @RequestParam("purchaseQuantity") int purchaseQuantity,
																		  @RequestParam("productQuantity") int productQuantity,
																		  @RequestParam("prodNo") int prodNo,
																		  @RequestParam("price") int price) throws Exception {

		System.out.println("/addPurchase.do");
		
		//Business Logic
		purchase.setPurchaseProd(productService.getProduct(prodNo));
		purchase.setBuyer(userService.getUser(buyerId));
		purchase.setPurchasePrice(purchaseQuantity*price);
		
		//다른로직
		product.setProdNo(prodNo);
		product.setProdQuantity(productQuantity-purchaseQuantity);
		
		purchaseService.addPurchase(purchase);
		productService.updateProductQuantity(product);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/addPurchase.jsp");
		modelAndView.addObject("purchase", purchase);
		modelAndView.addObject("product", product);
		
		return modelAndView;
	}
	
	@RequestMapping("/listPurchase.do")
	public ModelAndView listPurchase( @ModelAttribute("search") Search search, HttpSession session) throws Exception{
		
		System.out.println("/listPurchase.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// userId를 가져오기위한 user Session을 가져옵니다.
		User user = (User)session.getAttribute("user"); 
		
		// Business logic 수행
		Map<String , Object> map=purchaseService.getPurchaseList(search, user.getUserId());
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/listPurchase.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		
		return modelAndView;
	}
	
	@RequestMapping("/getPurchase.do")
	public ModelAndView getPurchase( @RequestParam("tranNo") int tranNo) throws Exception {
		
		System.out.println("/getPurchase.do");
		
		//Business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/getPurchase.jsp");
		modelAndView.addObject("purchase", purchase);
		
		return modelAndView;
	}
	
	@RequestMapping("/updatePurchaseView.do")
	public ModelAndView updateUserView( @RequestParam("tranNo") int tranNo ) throws Exception{

		System.out.println("/updateProductView.do");

		//Business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/updatePurchase.jsp");
		modelAndView.addObject("purchase", purchase);
				
		return modelAndView;
	}
	
	@RequestMapping("/updatePurchase.do")
	public ModelAndView updateProduct( @RequestParam("tranNo") int tranNo,
																			@ModelAttribute("purchase") Purchase purchase , 
																			HttpSession session) throws Exception{

		System.out.println("/updatePurchase.do");
		
		//Business Logic
		purchaseService.updatePurchase(purchase);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/getPurchase.do?tranNo="+tranNo);
		modelAndView.addObject("purchase", purchase);
				
		return modelAndView;
		}
	
	//
	@RequestMapping("/cancelPurchase.do")
	public ModelAndView cancelPurchase( @RequestParam("tranNo") int tranNo,
																			   @RequestParam("prodNo") int prodNo,
																			   @RequestParam("purchaseQuantity") int purchaseQuantity,
																			   @ModelAttribute("purchase") Purchase purchase , 
																			   @ModelAttribute("product") Product product,
																			HttpSession session) throws Exception{

		System.out.println("/cancelPurchase.do");
		
		//Business Logic
		purchase.setTranNo(tranNo);
		purchase.setPurchaseProd(productService.getProduct(prodNo));
		
		int prodQuantity = productService.getProduct(prodNo).getProdQuantity();
		int currentQuantity = purchaseQuantity+prodQuantity;
		product.setProdQuantity(currentQuantity);
		product.setProdNo(prodNo);
		
		//Business Logic 실행
		purchaseService.cancelPurchase(purchase);
		productService.updateProductQuantity(product);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/listPurchase.do");
				
		return modelAndView;
		}
	
	@RequestMapping("/updateTranCode.do")
	public ModelAndView updateTranCode( @RequestParam("tranNo") int tranNo,
																				@RequestParam("tranCode") int tranCode,
																				@ModelAttribute("purchase") Purchase purchase) throws Exception{

		System.out.println("/updateTranCode.do");
		
		//Business Logic
		purchase = purchaseService.getPurchase(tranNo);
		purchaseService.updateTranCode(purchase);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/listPurchase.do");
				
		return modelAndView;
		}
	
	@RequestMapping("/updateTranCodeByProd.do")
	public ModelAndView updateTranCodeByProd( @RequestParam("prodNo") int prodNo,
																				@RequestParam("tranCode") int tranCode,
																				@ModelAttribute("purchase") Purchase purchase) throws Exception{

		System.out.println("/updateTranCodeByProd.do");
		
		//Business Logic
		purchase.setPurchaseProd(productService.getProduct(prodNo));
		purchaseService.updateTranCodeByProd(purchase);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("purchase", purchase);
		modelAndView.setViewName("redirect:/listProduct.do?menu=manage");
				
		return modelAndView;
		}
	
	@RequestMapping("/updateTranCodeByTranNo.do")
	public ModelAndView updateTranCodeByTranNo( @RequestParam("tranNo") int tranNo,
																												@RequestParam("tranCode") int tranCode,
																												@ModelAttribute("purchase") Purchase purchase) throws Exception{

		System.out.println("/updateTranCodeByTranNoAction.do");
		
		//Business Logic
		purchase = purchaseService.getPurchase(tranNo);
		purchaseService.updateTranCodeByTranNo(purchase);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/listSale.do");
				
		return modelAndView;
		}
	
	@RequestMapping("/listSale.do")
	public ModelAndView listSale( @ModelAttribute("search") Search search) throws Exception{
		
		System.out.println("/listSale.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);

		// Business logic 수행
		Map<String , Object> map=purchaseService.getSaleList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/listSale.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		
		return modelAndView;
	}
	
	@RequestMapping("/listCancelPurchase.do")
	public ModelAndView listCancelPurchase( @ModelAttribute("search") Search search,
																					  HttpSession session) throws Exception{
		
		System.out.println("/listSale.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// userId를 가져오기위한 user Session을 가져옵니다.
		User user = (User)session.getAttribute("user");
		
		// Business logic 수행
		Map<String , Object> map=purchaseService.getCancelPurchaseList(search, user.getUserId());
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/listCancelPurchase.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		
		return modelAndView;
	}
	
	
	@RequestMapping("/updatePurchaseReviewView.do")
	public ModelAndView updatePurchaseReviewView( @RequestParam("tranNo") int tranNo ) throws Exception{

		System.out.println("/updatePurchaseReview.do");

		//Business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/addPurchaseReviewView.jsp");
		modelAndView.addObject("purchase", purchase);
				
		return modelAndView;
	}
	
	@RequestMapping("/updatePurchaseReview.do")
	public ModelAndView updateProduct( @RequestParam("tranNo") int tranNo,
			 																 @RequestParam("star") int star,
			 																@RequestParam("review") String review,
			 																@ModelAttribute("purchase") Purchase purchase,
																			HttpSession session) throws Exception{

		System.out.println("/updatePurchaseReview.do");
		
		//Business Logic
		purchaseService.updatePurchaseReview(purchase);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/listPurchase.do");
		modelAndView.addObject("purchase", purchase);
				
		return modelAndView;
		}
}