package bbs;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BbsDao;
import dto.BbsDto;

public class BbsController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	public void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		System.out.println("BbsController doProcess");
		
		String param = req.getParameter("param");
		
		// 게시물 목록으로 이동
		if(param.equals("bbslist")) {
			resp.sendRedirect("bbslist.jsp");
		}
		
		// 게시물로 이동
		if(param.equals("detail")) {
			String sseq = req.getParameter("seq");
			int seq = Integer.parseInt(sseq);
			
			BbsDao dao = BbsDao.getInstance();
			
			dao.readcount(seq);		// 조회수 늘리기
			
			BbsDto dto = dao.getBbs(seq);			
			
			req.setAttribute("bbs", dto);
			
			req.getRequestDispatcher("bbsdetail.jsp").forward(req, resp);
		}
		
		// 게시물 삭제
		if(param.equals("delete")) {
			int seq = Integer.parseInt( req.getParameter("seq") );
			System.out.println(seq);
			
			BbsDao dao = BbsDao.getInstance();
			dao.removeBbs(seq);
			
			resp.sendRedirect("bbs?param=searchbbs");
		}
		
		// 게시물 수정 작성
		if(param.equals("update")) {
			int seq = Integer.parseInt( req.getParameter("seq") );
			System.out.println(seq);

			BbsDao dao = BbsDao.getInstance();
			BbsDto bbs = dao.getBbs(seq);
			req.setAttribute("bbs", bbs);
			req.getRequestDispatcher("updatebbs.jsp").forward(req, resp);
		}
		
		// 게시물 수정 저장
		if(param.equals("updateAf")) {
			System.out.println("여기까지는 들어옴22");
			int seq = Integer.parseInt( req.getParameter("seq"));
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			System.out.println(seq + " " + title + " " + content);			
			
			BbsDao dao = BbsDao.getInstance();
			dao.updateBbs(seq, title, content);
			resp.sendRedirect("bbs?param=searchbbs");
		}
		
		// 댓글 작성
		if(param.equals("answer")) {
			int seq = Integer.parseInt( req.getParameter("seq"));
			
			BbsDto bbs = BbsDao.getInstance().getBbs(seq);
			System.out.println(bbs.toString());
			
			req.setAttribute("bbs", bbs);
			req.getRequestDispatcher("answer.jsp").forward(req, resp);
		}
		
		// 댓글 저장
		if(param.equals("answerAf")) {
			int seq = Integer.parseInt(req.getParameter("seq"));
			String id = req.getParameter("id");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			BbsDao dao = BbsDao.getInstance();
			dao.answer(seq, new BbsDto(id, title, content));
			
			resp.sendRedirect("bbs?param=searchbbs");	
		}
		
		// 글쓰기
		if(param.equals("bbswrite")) {
			resp.sendRedirect("bbswrite.jsp");
		}
		
		// 글쓰기 저장
		if(param.equals("bbswriteAf")) {
			System.out.println("여기");
			String id = req.getParameter("id");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			System.out.println(id + title + content);
			
			BbsDao dao = BbsDao.getInstance();
			
			dao.addBbslist(new BbsDto(id, title, content));
			
			resp.sendRedirect("bbs?param=searchbbs");
		}
		
		// 게시물 보기
		if(param.equals("searchbbs")) {
			System.out.println("여기는 들어옴");
			String choice = req.getParameter("choice");
			String search = req.getParameter("search");
			String spageNumber = req.getParameter("pageNumber");
			System.out.println("spageNumber = " + spageNumber);
			int pageNumber = 0; // 현재 페이지를 가르키는 변수

			if(spageNumber != null && !spageNumber.equals("")){ // 페이지 넘버를 클릭했을 경우
				pageNumber = Integer.parseInt(spageNumber);
			}
			System.out.println("pageNumber : " + pageNumber);
			if(choice == null) {
				choice = "";
			}
			if(search == null) {
				search = "";
			}
			
			BbsDao dao = BbsDao.getInstance();
			List<BbsDto> list = dao.getBbsPagingBbs(choice, search, pageNumber);
			req.setAttribute("list", list);
			
			int len = dao.getAllBbs(choice, search);
			int bbsPage = len / 10;		// 23 -> 2
			if((len % 10) > 0){
				bbsPage = bbsPage + 1;
			}
			req.setAttribute("bbsPage", bbsPage + "");
			req.setAttribute("pageNumber", pageNumber + "");
			req.setAttribute("choice", choice);
			req.setAttribute("search", search);
			req.getRequestDispatcher("bbslist.jsp").forward(req, resp);
		}
	}
}
