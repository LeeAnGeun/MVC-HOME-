<%@page import="calendar.CalendarDto"%>
<%@page import="java.util.List"%>
<%@page import="calendar.CalendarDao"%>
<%@page import="util.UtilEx"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
Object ologin = session.getAttribute("login");
MemberDto mem = null;

if(ologin == null){ // 로그인 세션이 없을경우 
	%>
	<script>
	alert('로그인을 해 주십시오');
	location.href = "login.jsp"; // 다시 login창으로 돌아간다.
	</script>	
	<%
}

mem = (MemberDto)ologin;
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4 align="right" style="background-color: #f0f0f0">환영합니다. <%=mem.getId() %>님</h4>

<h2>일정관리</h2>
<%
	Calendar cal = Calendar.getInstance();
	int year = (int)request.getAttribute("year");
	int month = (int)request.getAttribute("month");
	List<String> image = (List)request.getAttribute("image");
	
	int dayOfWeek = (int)request.getAttribute("dayOfWeek");
	List<CalendarDto> list = (List)request.getAttribute("list");
%>

<div align="center">

<table border="1">
<col width="100"><col width="100"><col width="100"><col width="100">
<col width="100"><col width="100"><col width="100">

<tr height="100">
	<td colspan="7" align="center" style="padding-top: 20px">
		<%=image.get(0) %>&nbsp;&nbsp;<%=image.get(1) %>&nbsp;&nbsp;
		
		<font color="black" style="font-size: 50px"> 
			<%=String.format("%d년&nbsp;&nbsp;%d월", year, month) %>
		</font>
		
		<%=image.get(2) %>&nbsp;&nbsp;<%=image.get(3) %>
	</td>
</tr>

<tr height="50">
	<th align="center">일</th>
	<th align="center">월</th>
	<th align="center">화</th>
	<th align="center">수</th>
	<th align="center">목</th>
	<th align="center">금</th>
	<th align="center">토</th>
</tr> 

<tr height="100" align="left" valign="top">
<%
// 위쪽 빈칸
for(int i=1; i < dayOfWeek; i++){
	%>
	<td style="background-color: #cecece"></td>
	<%
}
// 날짜
int lastday = (int)request.getAttribute("lastday"); // 마지막 날짜
System.out.println("lastday = " + lastday);
for(int i=1; i<=lastday; i++){
	%>
	<td>
		<%=UtilEx.calllist(year, month, i) %>&nbsp;&nbsp;<%=UtilEx.showPen(year, month, i) %>
		<%=UtilEx.makeTable(year, month, i, list) %>
	</td>
	<%
	if( (i + dayOfWeek-1) % 7 == 0 && i != lastday){ // 줄을 바꿔주기 위해 테이블을 새로 생성 && i가 마지막 날짜일때는 개행을 하면 안된다.
		%>
		</tr><tr height="100" align="left" valign="top"> 
		<%
	}
}
%>

<%-- 밑의 빈칸 --%>

<%
int weekday = (int)request.getAttribute("weekday");
for(int i=0; i< 7 - weekday; i++){
	%>
		<td style="background-color: #cecece"></td>
	<%
}
%>
</tr>
</table>
</div>

</body>
</html>