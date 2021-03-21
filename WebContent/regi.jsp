<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
.center{
	margin: auto;
	width: 60%;
	border: 3px solid #0000ff;
	padding: 10px;
}

</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

</head>
<body>
<h2>회원가입</h2>
<p>환영합니다. 홍길이 홈페이지입니다.</p>

<div class="center">

<form action="member?param=regiAf" method="post">

<table border="1">
<tr>
	<td>아이디</td>
	<td>
		<input type="text" name="id" id="_id" size="20">
		<input type="button" name="btn" id="btn" value="중복확인">
		<br>
		<p id="idcheck" style="font-size: 8px"></p>
		<!-- id 확인(ajax) p, button : 이미 존재하는 아이디인지 확인 -->
	</td>
</tr>
<tr>
	<td>패스워드</td>
	<td>
		<input type="password" name="pwd" id="_pwd" size="20">
	</td>
</tr>
<tr>
	<td>이름</td>
	<td>
		<input type="text" name="name" size="20">
	</td>
</tr>
<tr>
	<td>이메일</td>
	<td>
		<input type="text" name="email" size="20">
	</td>
</tr>
<tr>
	<td colspan="2">
		<input type="submit" value="회원가입"> 
	</td>
</tr>

</table>


<!-- 중복확인 버튼을 클릭하였을때  -->
<script type="text/javascript">
$(document).ready(function() {
	
	$("#btn").click(function() {
		
		$.ajax({
			// ////////////////////send
			url:"member?param=idcheck",  // data.jsp에 접근한다.
			type:"post",		// get/post -> servlet에서 중요
		//	data: "id=" + $("#_id").val();
			data: { "id":$("#_id").val() },
		//	data: { "id":"abc"},
			/////////////////////////
			
			/////////////////////////recv
			success:function(data){ // status와 xhr은 빼고 사용해도 코드가 실행 된다. 
		//		alert("success");
				
				if( data.b == true){
					$("#idcheck").css("color", "#ff0000");
					$("#idcheck").html("사용할수 없는 아이디입니다.");
					$("#_id").val(""); // ID칸을 빈칸으로 만들기위한 코드
				}else{
					$("#idcheck").css("color", "#0000ff");
					$("#idcheck").html("사용할수 있는 아이디입니다.");
				}
			},
			error:function(/* xhr, status, error */){ // error는 매개변수를 사용을 하지 않아도 코드가 실행 된다.
				alert("error");
			},
			complete:function(xhr, status){
			//	alert("통신 종료");
			}
			////////////////////////
		});
	});
})
</script>
</form>

</div>
</body>
</html>