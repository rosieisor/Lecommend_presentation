package controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import model.dto.StudentDTO;
import model.service.ExistingUserException;
import model.service.UserManager;

public class JoinUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(JoinUserController.class);
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
       	if (request.getMethod().equals("GET")) {	
    		// GET request: 회원정보 등록 form 요청	
    		log.debug("JoinForm Request");

    		//List<Community> commList = UserManager.getInstance().findCommunityList();	// 커뮤니티 리스트 검색
			//request.setAttribute("commList", commList);	
		
			return "/user/joinForm.jsp";        	
	    }	
       	
 
    	// POST request (회원정보가 parameter로 전송됨)
       	StudentDTO user = new StudentDTO(
       		request.getParameter("userId"),
			request.getParameter("password"),
			request.getParameter("major"));
		
        log.debug("Create User : {}", user);

		try {
			UserManager manager = UserManager.getInstance();
			manager.create(user);
	        return "redirect:/home";	// 성공 시 사용자 리스트 화면으로 redirect
	        
		} catch (ExistingUserException e) {	// 예외 발생 시 회원가입 form으로 forwarding
            request.setAttribute("joinFailed", true);
			request.setAttribute("exception", e);
			request.setAttribute("user", user);
			return "/user/joinForm.jsp";
		}
		
    }
    
}
