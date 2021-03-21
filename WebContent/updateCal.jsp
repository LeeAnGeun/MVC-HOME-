<%@page import="java.util.Calendar"%>
<%@page import="calendar.CalendarDto"%>
<%@page import="calendar.CalendarDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
int seq = Integer.parseInt( request.getParameter("seq") );
System.out.println(seq);

CalendarDao dao = CalendarDao.getInstance();
CalendarDto dto = dao.getCalendar(seq);
String rdate = dto.getRdate();

String year = rdate.substring(0, 4);
String month = rdate.substring(4, 6);
String day = rdate.substring(6, 8); 
String hour = rdate.substring(8, 10); 
String min = rdate.substring(10, 12); 

if(month.length()>1){
	month = month.substring(1);
}
if(day.length()>1){
	day = day.substring(1);
}
System.out.println(year + " " + month + " " + day + " " + hour + " " + min);
%>

<%
Calendar cal = Calendar.getInstance();
int tyear = cal.get(Calendar.YEAR);
int tmonth = cal.get(Calendar.MONTH) + 1;
int tday = cal.get(Calendar.DATE);
int thour = cal.get(Calendar.HOUR_OF_DAY);
int tmin = cal.get(Calendar.MINUTE);

cal.set(Calendar.MONTH, Integer.parseInt(month)-1); // 파라미터로 얻어온 month로 다시 Calendar클래스에 MONTH를 다시 세팅한다.
%>

<h2>일정 수정</h2>

<div align="center">

<form action="updateCalAf.jsp" method="post">
<input type="hidden" name="seq" value="<%=seq %>">
<table border="1">
<col width="200"><col width="500">
<tr>
	<th>ID</th>
	<td>
		<%=dto.getId() %>
		<input type="hidden" name="id" value="<%=dto.getId() %>">
	</td>
</tr>
<tr>
	<th>제목</th>
	<td>
		<input type="text" size="60" name="title" value="<%=dto.getId() %>">
	</td>
</tr>
<tr>
	<th>일정</th>
	<td>
		<select name="year">
		<%
			for(int i = tyear - 5;i <= tyear + 5; i++){
				%>
				<option <%=year.equals(i + "")?"selected='selected'":"" %> value="<%=i %>" > <!-- 파라미터로 넘어온 년도와 같은 것이 selected되게하는 코드 -->
					<%=i %>
				</option>
				<%
			}
		
		%>
		</select>년
		
		<select name="month">
		<%
			for(int i = 1;i <= 12; i++){
				%>
				<option <%=month.equals(i + "")?"selected='selected'":"" %> value="<%=i %>" > <!-- 파라미터로 넘어온 년도와 같은 것이 selected되게하는 코드 -->
					<%=i %>
				</option>
				<%
			}
		
		%>
		</select>월
		
		<select name="day">
		<%
			for(int i = 1;i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++){ // cal.getActualMaximum(Calendar.DAY_OF_MONTH) 해당 월에 마지막 일수까지
				%>
				<option <%=day.equals(i + "")?"selected='selected'":"" %> value="<%=i %>" > <!-- 파라미터로 넘어온 년도와 같은 것이 selected되게하는 코드 -->
					<%=i %>
				</option>
				<%
			}
		
		%>
		</select>일
		
			<select name="hour">
		<%
			for(int i = 1;i <= 24; i++){ // cal.getActualMaximum(Calendar.DAY_OF_MONTH) 해당 월에 마지막 일수까지
				%>
				<option <%=(hour + "").equals(i + "")?"selected='selected'":"" %> value="<%=i %>" > <!-- 파라미터로 넘어온 년도와 같은 것이 selected되게하는 코드 -->
					<%=i %>
				</option>
				<%
			}
		
		%>
		</select>시
		
			<select name="min">
		<%
			for(int i = 0;i <= 59; i++){ // cal.getActualMaximum(Calendar.DAY_OF_MONTH) 해당 월에 마지막 일수까지
				%>
				<option <%=(min + "").equals(i + "")?"selected='selected'":"" %> value="<%=i %>" > <!-- 파라미터로 넘어온 년도와 같은 것이 selected되게하는 코드 -->
					<%=i %>
				</option>
				<%
			}
		
		%>
		</select>분
	</td>
</tr>

<tr>
	<th>내용</th>
	<td>
		<textarea rows="20" cols="60" name="content"><%=dto.getContent() %></textarea>
	</td>
</tr>

<tr>
	<td colspan="2">
		<input type="submit" value="일정수정">
	</td>
</tr>.
</table>
</form>
</body>
</html>