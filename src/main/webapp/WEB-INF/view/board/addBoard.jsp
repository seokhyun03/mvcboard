<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>addBoard</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		$('#addfile').click(function(){
			if($('.boardfiles').last().val() == ''){
				alert('빈 파일업로드 태그가 있습니다');
			} else{
				$('#files').append('<input type="file" name="multipartFile" class="boardfiles">') 
			}
		});
		$('#delfile').click(function(){
			if($('.boardfiles').last().val() != ''){
				alert('빈 파일업로드 태그가 없습니다');
			} else {
				$('.boardfiles').last().remove();
			}
		});
	});
</script>
</head>
<body>
	<h1>게시글 입력</h1>
	
	<div>
		<a href="/board/boardList">이전</a>
	</div>
	
	<form action="/board/addBoard" method="post" enctype="multipart/form-data">
		<table border="1">
			<tr>
				<th>memberId</th>
				<td><input type="hidden" name="memberId" value="user">user</td>
			</tr>
			<tr>
				<th>localName</th>
				<td>
					<select name="localName">
						<c:forEach var="l" items="${localNameList}">
							<option value="${l.localName}">${l.localName}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>boardTitle</th>
				<td><input type="text" name="boardTitle"></td>
			</tr>
			<tr>
				<th>boardContent</th>
				<td><textarea cols="50" rows="3" name="boardContent"></textarea></td>
			</tr>
			<tr>
				<th>boardfiles</th>
				<td>
					<button type="button" id="addfile">추가</button>
					<button type="button" id="delfile">삭제</button>
					<div id="files">
						<input type="file" name="multipartFile" class="boardfiles">
					</div>
				</td>
			</tr>
		</table>
		<button type="submit">입력</button>
	</form>
</body>
</html>