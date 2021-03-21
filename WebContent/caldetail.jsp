<%@page import="calendar.CalendarDto"%>
<%@page import="calendar.CalendarDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

</head>
<body>
<%

CalendarDto dto = (CalendarDto)request.getAttribute("dto"); 
String rdate = dto.getRdate();
%>



<div align="center">
<h3>일정 내용</h3>
<table border="1">
<col width="200"><col width="500">
<tr>
	<th>ID</th>
	<td>
		<%=dto.getId() %>
		<input type="hidden" name="id" value="<%=dto.getId() %>" readonly="readonly">
	</td>
</tr>
<tr>
	<th>제목</th>
	<td>
		<input type="text" size="60" name="title" value="<%=dto.getTitle() %>" readonly="readonly">
	</td>
</tr>
<tr>
	<th>일정</th>
	<td><%=rdate.substring(0, 4) %>년  <%=rdate.substring(4, 6) %>월  <%=rdate.substring(6, 8) %>일  <%=rdate.substring(8, 10) %>시  <%=rdate.substring(10, 12) %>분</td>
</tr>
<tr>
	<th>내용</th>
	<td>
		<textarea rows="20" cols="60" name="content" readonly="readonly"><%=dto.getContent() %></textarea>
	</td>
</tr>
</table>

<input type="button" id="deleteBtn" value="일정 삭제">
<input type="button" id="updateBtn" value="일정 수정">
<input type="button" id="listBtn" value="일정 목록">

</div>
<script type="text/javascript">
$(document).ready(function() {
	
	$("#deleteBtn").click(function() {
		location.href="cal?param=deleteCal&seq=<%=dto.getSeq() %>"
	});
	$("#updateBtn").click(function() {
		location.href="cal?param=updateCal&seq=<%=dto.getSeq() %>"	
	});
	$("#listBtn").click(function() {
		location.href="cal?param=calendarlist"
	});
		
});
</script>
</body>
</html>









