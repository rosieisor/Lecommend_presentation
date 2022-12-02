package controller.lecture;


import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.Controller;

import model.service.UserManager;

public class LectureController implements Controller {
	@Override
    public String execute(HttpServletRequest request, HttpServletResponse response)	throws Exception {
		
    	UserManager manager = UserManager.getInstance();
		//List<Community> commList = manager.findCommunityList();
		
		// commList 객체를 request에 저장하여 커뮤니티 리스트 화면으로 이동(forwarding)
		//request.setAttribute("commList", commList);				
		return "/lecture/list.jsp";        
    }
}



