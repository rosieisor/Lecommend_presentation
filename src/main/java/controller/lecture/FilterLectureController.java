package controller.lecture;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import controller.Controller;


public class FilterLectureController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(FilterLectureController.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		return "/lecture/filterForm.jsp";
	}
}
