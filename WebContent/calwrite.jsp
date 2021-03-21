<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
MemberDto mem = (MemberDto)session.getAttribute("login");

if(mem == null){ // 로그인 세션이 없을경우 
	%>
	<script>
	alert('로그인을 해 주십시오');
	location.href = "login.jsp"; // 다시 login창으로 돌아간다.
	</script>	
	<%
}
%>

<%
	System.out.println("여기는 들어왔다아!!!!");
	Calendar cal = Calendar.getInstance();

	String year = (String)request.getAttribute("year");
	String month = (String)request.getAttribute("month");
	String day = (String)request.getAttribute("day"); 
	
	cal.set(Calendar.MONTH, Integer.parseInt(month)-1); // 파라미터로 얻어온 month로 다시 Calendar클래스에 MONTH를 다시 세팅한다.
	
	List<Integer> date = (List)request.getAttribute("date");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h2>일정 추가</h2>

<div align="center">

<form action="cal?param=calwriteAf" method="post">

<table border="1">
<col width="200"><col width="500">
<tr>
	<th>ID</th>
	<td>
		<%=mem.getId() %>
		<input type="hidden" name="id" value="<%=mem.getId() %>">
	</td>
</tr>
<tr>
	<th>제목</th>
	<td>
		<input type="text" size="60" name="title">
	</td>
</tr>
<tr>
	<th>일정</th>
	<td>
		<select name="year">
		<%
			for(int i = date.get(0) - 5;i <= date.get(0) + 5; i++){
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
				<option <%=(date.get(3) + "").equals(i + "")?"selected='selected'":"" %> value="<%=i %>" > <!-- 파라미터로 넘어온 년도와 같은 것이 selected되게하는 코드 -->
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
				<option <%=(date.get(4) + "").equals(i + "")?"selected='selected'":"" %> value="<%=i %>" > <!-- 파라미터로 넘어온 년도와 같은 것이 selected되게하는 코드 -->
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
		<textarea rows="20" cols="60" name="content"></textarea>
	</td>
</tr>

<tr>
	<td colspan="2">
		<input type="submit" value="일정추가">
	</td>
</tr>.
</table>
</form>

</div>
</body>
</html>










