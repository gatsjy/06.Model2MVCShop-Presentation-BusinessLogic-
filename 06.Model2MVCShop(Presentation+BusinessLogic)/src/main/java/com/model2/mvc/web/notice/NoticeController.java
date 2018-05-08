package com.model2.mvc.web.notice;

import java.util.Map;

import javax.servlet.http.Cookie;
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

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Notice;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.notice.NoticeService;
import com.model2.mvc.service.product.ProductService;

//==> 회원관리 Controller
@Controller
public class NoticeController {
	
	///Field
	@Autowired
	@Qualifier("noticeServiceImpl")
	private NoticeService noticeService;
	//setter Method 구현 않음
		
	public NoticeController(){
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
	
	@RequestMapping("/addNotice.do")
	public String addUser( @ModelAttribute("notice") Notice notice ) throws Exception {

		System.out.println("/addNotice.do");
		
		//Business Logic
		noticeService.addNotice(notice);
		
		return "forward:/listNotice.do";
	}
	
	@RequestMapping("/listNotice.do")
	public String listProduct( @ModelAttribute("search") Search search ,
												Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/listNoticedo.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=noticeService.getNoticeList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/notice/listNotice.jsp";
	}
	
	@RequestMapping("/getNotice.do")
	public String getUser( @RequestParam("noticeNo") int noticeNo , Model model, HttpServletRequest request ) throws Exception {
		
		System.out.println("/getNotice.do");
		
		//Business Logic
		Notice notice = noticeService.getNotice(noticeNo);
		
		notice.setNoticeHits(notice.getNoticeHits()+1);
		
		noticeService.updateNoticeHits(notice);
		
		// Model 과 View 연결
		model.addAttribute("notice", notice);
		
		return "forward:/notice/getNotice.jsp";
	}
	
	@RequestMapping("/updateNoticeView.do")
	public String updateUserView(  @RequestParam("noticeNo") int noticeNo , Model model ) throws Exception{

		System.out.println("/updateNoticeView.do");

		//Business Logic
		Notice notice = noticeService.getNotice(noticeNo);
		
		// Model 과 View 연결
		model.addAttribute("notice", notice);
		
		return "forward:/notice/updateNotice.jsp";
	}

	@RequestMapping("/updateNotice.do")
	public String updateProduct( @ModelAttribute("notice") Notice notice
																																			) throws Exception{

		System.out.println("/updateNotice.do");
		
		//Business Logic
		noticeService.updateNotice(notice);
		
		
		return "redirect:/listNotice.do?noticeNo="+notice.getNoticeNo();
	}
}