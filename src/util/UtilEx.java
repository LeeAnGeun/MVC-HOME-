package util;

import java.util.List;

import calendar.CalendarDto;

public class UtilEx {

	// 날짜를 클릭하면 그날의 일정을 모두 볼 수 있는 callist.jsp로 이동하는 함수
	public static String calllist(int year, int month, int day) {
		String str = "";
		
		str += String.format("&nbsp;<a href='%s?year=%d&month=%d&day=%d'>", 
										"calllist.jsp", year, month, day);
		str += String .format("%2d", day);
		str += "</a>";
		
		// str = <a href='callist.jsp?year=2021&month=3&day=19'>19</a>
		return str;
	}
	
	// 일정을 추가하기 위해서 pen이미지를 클릭하면 calwrite.jsp로 이동하는 함수
	public static String showPen(int year, int month, int day) {
		String str ="";
		
		String image = "<img src='image/pen2.png' width='18px' height='18px'>";
		
		str = String.format("<a href='%s&year=%d&month=%d&day=%d'>%s</a>", 
							"cal?param=calwrite", year, month, day, image);
		// str = <a href='calwrite.jsp?year=2019&month=09&day=19'><img str='image/pen2.png' width='18px' height='18px'></a>
		
		return str;
	}
	
	// 한문자를 두문자로 변경해 주는 함수 => 2021 3 19 -> 3을 03으로 변경하기 위한 함수 -> 1~9 -> 01 ~ 09로 변경해주는 함수
	public static String two(String msg) {
		return msg.trim().length()<2?"0"+msg.trim():msg.trim(); // 글자수가 2보다 작을때 0을 붙여서 반환한다.
	}
	
	// nvl 함수 : 문자열이 비어 있는지 확인 함수
	public static boolean nvl(String msg) {
		return msg == null || msg.trim().equals("")?true:false;
	}
	
	// 달력의 날짜 별로 설정할 테이블을 작성하는 함수 -> 날짜별로 테이블을 만들어주는 함수
	public static String makeTable(int year, int month, int day, List<CalendarDto> list) {
		String str = "";
		
		// 2021 3 19 -> 20210319
		String dates = (year + "") + two(month + "") + two(day + "");
		
		str += "<table>";
		str += "<col width='100'>";
				
		for(CalendarDto dto : list) {
			if(dto.getRdate().substring(0, 8).equals(dates)) {
				str += "<tr>";
				str += "<td style='line-height: 10px; overflow: hidden; border-collapse:collapse;border:1px blue solid'>";
				
				str += "<a href='cal?param=caldetail&seq=" + dto.getSeq() + "'>";
				
				str += "<font style='font-size:8px; color:blue'>"; 
				str += dot3(dto.getTitle());
				str += "</font>";
				
				str += "</a>";
				
				str += "</td>";
				str += "</tr>";
			}
		}
		str += "</table>";
		
		return str;
	}
	
	
	// 일정의 제목이 길 때 -> ...로 처리하는 함수	=>	테이블이 깨지는 현상을 방지한다.
	public static String dot3(String msg) {
		String str = "";
		if(msg.length() >= 7) {
			str = msg.substring(0, 7);
			str += "...";
		}else {
			str = msg.trim();
		}
		
		return str;
	}
}
