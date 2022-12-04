package model.service;

import java.sql.SQLException;
import java.util.List;

import model.dao.jdbc.StudentDAO;
import model.dto.LectureDTO;
import model.dto.StudentDTO;


public class UserManager {
	private static UserManager userMan = new UserManager();
	private StudentDAO userDAO;

	private UserManager() {
		try {
			userDAO = new StudentDAO();

		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	
	public static UserManager getInstance() {
		return userMan;
	}
	
	public int create(StudentDTO user) throws SQLException, ExistingUserException {
		if (userDAO.existingUser(user.getStuID()) == true) {
			throw new ExistingUserException(user.getStuID() + "는 존재하는 아이디입니다.");
		}
		return userDAO.create(user);
	}

	public int update(StudentDTO user) throws SQLException, UserNotFoundException {
		return userDAO.update(user);
	}
	
	public int remove(String userId) throws SQLException, UserNotFoundException {
		return userDAO.remove(userId);
	}
	
	public StudentDTO findUser(String userId)
		throws SQLException, UserNotFoundException {
		StudentDTO user = userDAO.findUser(userId);
		
		if (user == null) {
			throw new UserNotFoundException(userId + "는 존재하지 않는 아이디입니다.");
		}		
		return user;
	}

	public List<LectureDTO> findStudentDibList(String stuid) throws SQLException {
			return userDAO.findStudentDibList(stuid);
	}
	
	public List<LectureDTO> findStudentDibList(int currentPage, int countPerPage, String stuid)
		throws SQLException {
		return userDAO.findStudentDibList(currentPage, countPerPage, stuid);
	}

	public boolean login(String userId, String password)
		throws SQLException, UserNotFoundException, PasswordMismatchException {
		StudentDTO user = userDAO.findUser(userId);
		
		if (!user.matchPassword(password)) {
			throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
		}
		return true;
	}
	
	
	public StudentDAO getUserDAO() {
		return this.userDAO;
	}
	
}
