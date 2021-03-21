package calendar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.UtilEx;

public class CalController extends HttpServlet{

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
		
		// 
		if(param.equals("calendarlist")) {
			System.out.println("doProcess callist");
			
			String id = req.getParameter("id");
			System.out.println("id = " + id);
			
//			Calendar 클래스를 가져오는 두가지 방법
			Calendar cal = Calendar.getInstance();
//			Calendar cal = new GregorianCalendar();
			
			cal.set(Calendar.DATE, 1);	// 오늘 날짜를 -> 1일로 초기화해주는 설정
			
			String syear = req.getParameter("year"); // 다시 들어오는 값이 있는 경우
			String smonth = req.getParameter("month"); // 다시 들어오는 값이 있는 경우
			
			System.out.println("syear = " + syear);
			System.out.println("smonth = " + smonth);
				
			
			int year = cal.get(Calendar.YEAR);
			if(UtilEx.nvl(syear) == false){ // parameter가 넘어와서 syear가 값이 있을 경우
				year = Integer.parseInt(syear);
			}
			
			int month = cal.get(Calendar.MONTH) + 1;
			if(UtilEx.nvl(smonth) == false){
				month = Integer.parseInt(smonth);
			}
			
			if(month < 1){
				month = 12;
				year--;
			}else if(month > 12){
				month = 1;
				year++;
			}
		
			req.setAttribute("year", year);
			req.setAttribute("month", month);
		
			cal.set(year, month-1, 1); // 연 월 일 세팅 완료
			
			// image 설정
			// <<  	year--
			String pp = String.format("<a href='%s&year=%d&month=%d'><img src='image/left.gif'></a>", "cal?param=calendarlist", year-1, month);
			
			// <	month--
			String p = String.format("<a href='%s&year=%d&month=%d'><img src='image/prec.gif'></a>", "cal?param=calendarlist", year, month-1);
			
			// >	month++
			String n = String.format("<a href='%s&year=%d&month=%d'><img src='image/next.gif'></a>", "cal?param=calendarlist", year, month+1);
			
			// >>	year++
			String nn = String.format("<a href='%s&year=%d&month=%d'><img src='image/last.gif'></a>", "cal?param=calendarlist", year+1, month);
			
			List<String> image = new ArrayList<String>();
			
			image.add(pp);
			image.add(p);
			image.add(n);
			image.add(nn);
			
			req.setAttribute("image", image);
			
			// 요일
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			int lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 마지막 날짜
			
			System.out.println("dayOfWeek = " + dayOfWeek);
			
			req.setAttribute("dayOfWeek", dayOfWeek);
			req.setAttribute("lastday", lastday);
			
			CalendarDao dao = CalendarDao.getInstance();
			List<CalendarDto> list = dao.getCalendalList(id, year + UtilEx.two(month + ""));
			System.out.println("list = " + list);
			
			req.setAttribute("list", list);
			
			cal.set(Calendar.DATE, lastday); // 그 달의 마지막 날짜로 세팅
			int weekday = cal.get(Calendar.DAY_OF_WEEK);
			
			req.setAttribute("weekday", weekday);
			
			req.getRequestDispatcher("calendarlist.jsp").forward(req, resp);
		}
		
		if(param.equals("calwrite")) {
			System.out.println("여기는 들어왔다222");
			String year = req.getParameter("year");
			String month = req.getParameter("month");
			String day = req.getParameter("day");
			
			req.setAttribute("year", year);
			req.setAttribute("month", month);
			req.setAttribute("day", day);
			
			Calendar cal = Calendar.getInstance();
			
			int tyear = cal.get(Calendar.YEAR);
			int tmonth = cal.get(Calendar.MONTH) + 1;
			int tday = cal.get(Calendar.DATE);
			int thour = cal.get(Calendar.HOUR_OF_DAY);
			int tmin = cal.get(Calendar.MINUTE);
			
			List<Integer> date = new ArrayList<Integer>();
			
			date.add(tyear);
			date.add(tmonth);
			date.add(tday);
			date.add(thour);
			date.add(tmin);
			
			req.setAttribute("date", date);
			
			req.getRequestDispatcher("calwrite.jsp").forward(req, resp);
		}
		
		if(param.equals("calwriteAf")) {
			req.setCharacterEncoding("utf-8");

			String id = req.getParameter("id");
			String title = req.getParameter("title");
			String content = req.getParameter("content");

			String year = req.getParameter("year");
			String month = req.getParameter("month");
			String day = req.getParameter("day");
			String hour = req.getParameter("hour");
			String min = req.getParameter("min");
			
			String rdate = year + UtilEx.two(month) + UtilEx.two(day) + UtilEx.two(hour) + UtilEx.two(min);
			
			System.out.println("rdate = " + rdate);
			CalendarDao dao = CalendarDao.getInstance();

			boolean isS = dao.addCalendar(new CalendarDto(id, title, content, rdate));
			
			resp.sendRedirect("cal?param=calendarlist&id=" + id + "&year=" + year + "&month=" + month);
			
			if(isS) {
				System.out.println("일정이 추가되었습니다.");
			}else {
				System.out.println("일정 추가에 실패하였습니다");
			}
		}
		
		if(param.equals("caldetail")) {
			req.setCharacterEncoding("utf-8");
			
			int seq = Integer.parseInt( req.getParameter("seq") );
			
			System.out.println(seq);

			CalendarDao dao = CalendarDao.getInstance();
			CalendarDto dto = dao.getCalendar(seq);
			
			req.setAttribute("dto", dto);
			
			req.getRequestDispatcher("caldetail.jsp").forward(req, resp);
		}
		
		if(param.equals("deleteCal")) {
			int seq = Integer.parseInt( req.getParameter("seq") );
			
			System.out.println(seq);
			
			CalendarDao dao = CalendarDao.getInstance();
			dao.deleteCalendar(seq);
			
			resp.sendRedirect("cal?param=calendarlist");
		}
		
		if(param.equals("updateCal")) {
			int seq = Integer.parseInt( req.getParameter("seq") );
			
			System.out.println(seq);
			
			CalendarDao dao = CalendarDao.getInstance();
			// dao.updateCalendar(dto)
		}
	}
}
