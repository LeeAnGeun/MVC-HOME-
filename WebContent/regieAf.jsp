<%@page import="dto.MemberDto"%>
<%@page import="dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// request.setCharacterEncoding;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
Boolean isS = (Boolean)request.getAttribute("isS");
System.out.println(isS);

if(isS == true){ // 회원가입이 성공했을 경우
%>
	<script type="text/javascript">
	alert("성공적으로 가입되었습니다.");
	location.href = "member?param=login";
	</script>
<%	
}else{ // 회원가입이 실패했을 경우
%>
	<script type="text/javascript">
	alert("다시 기입해 주십시오");
	location.href = "member?param=regi";
	</script>
<%
}
%>


</body>
</html>