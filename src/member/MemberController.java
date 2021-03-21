package member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import dto.MemberDto;
import net.sf.json.JSONObject;


public class MemberController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	// doGet과 doPost를 상관없이 사용하고싶을 경우 밑에와 같이 하나에 메소드를 더 생성하면 된다.
	public void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		System.out.println("MemberController doProcess");
		
		String param = req.getParameter("param");
		
		// 로그인 화면 이동
		if(param.equals("login")) {
			resp.sendRedirect("login.jsp");
		}
		// 회원가입 화면이동
		else if(param.equals("regi")) {
			resp.sendRedirect("regi.jsp");
		}
		
		// 중복 ID 판별
		if(param.equals("idcheck")) {
			String id = req.getParameter("id");
			
			MemberDao dao = MemberDao.getInstance();
			
			boolean b = dao.getId(id);
			
			System.out.println(b);
			JSONObject jObj = new JSONObject();
			
			jObj.put("b", b);
			
			resp.setContentType("application/x-json; charset=UTF-8");
			resp.getWriter().print(jObj);
		}
		
		// 회원가입 확인
		else if( param.equals("regiAf")) {
			String id = req.getParameter("id");
			String pwd = req.getParameter("pwd");
			String name = req.getParameter("name");
			String email = req. getParameter("email");
			
		//	System.out.println(id + " " + pwd + " " + name + " " + email);
			
			MemberDao dao = MemberDao.getInstance();
			MemberDto dto = new MemberDto(id, pwd, name, email, 0);
			
			Boolean isS = dao.addMember(dto);
			
			req.setAttribute("isS", isS);
			req.getRequestDispatcher("regieAf.jsp").forward(req, resp);
		}
		
		// 로그인 확인
		if(param.equals("loginAf")) {
			String id = req.getParameter("id");
			String pwd = req.getParameter("pwd");
			
			MemberDao dao = MemberDao.getInstance();
			req.getSession().setAttribute("login", new MemberDto(id, pwd, null, null, 0));
			Boolean b = dao.checkmember(id, pwd);
			
			req.setAttribute("b", b);
			req.getRequestDispatcher("loginAf.jsp").forward(req, resp);
		}

		// 게시판 화면 이동
		else if(param.equals("bblist")) {
			resp.sendRedirect("bbs?param=searchbbs");
		}
	}
}