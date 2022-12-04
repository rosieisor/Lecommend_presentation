package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.user.UserSessionUtils;
import model.dto.StudentDTO;
import model.service.UserManager;


public class HomeController implements Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 로그인 여부 확인
    	if (UserSessionUtils.hasLogined(request.getSession())) {
    		return "redirect:/home";    
            //return "redirect:/user/login/form";		// login form 요청으로 redirect
        }
    	
    	String userId = (String) request.getAttribute("userid");
		UserManager manager = UserManager.getInstance();
		StudentDTO user = manager.findUser(userId);
		
		request.setAttribute("user", user);				
		request.setAttribute("curUserId", 
				UserSessionUtils.getLoginUserId(request.getSession()));		

		// 사용자 리스트 화면으로 이동(forwarding)
		return "redirect:/home";        
    }
	
}
