<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student List</title>
<!-- link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link -->
<!-- link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link -->
</head>
<style>
.container {
	padding-left: 50px;
	padding-top: 20px;
}
</style>
<body>

	<div class="container">
		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<div class="row">
				<input id="createStudent" type="button" value="Create Student"
					class="btn btn-info">
			</div>
		</sec:authorize>

		<br />
		<table class="table table-hover">
			<thead>
				<tr>
					<th></th>
					<th><spring:message code="student.firstname" /></th>
					<th><spring:message code="student.lastname" /></th>
					<th><spring:message code="student.dob" /></th>
					<th><spring:message code="student.email" /></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<c:forEach items="${students}" var="student" varStatus="loop">
						<tr>
							<td>${loop.index + 1}</td>
							<td>${student.firstName}</td>
							<td>${student.lastName}</td>
							<td><fmt:formatDate pattern="MM/dd/yyyy"
									value="${student.dob}" /></td>
							<td>${student.email}</td>
							<td><a href='javascript:editStudent(${student.id})'><span
									class="glyphicon glyphicon-pencil"></span> </a> <sec:authorize
									access="hasRole('ROLE_ADMIN')"> |
								<a href='javascript:deleteStudent(${student.id})'><span
										class="glyphicon glyphicon-trash"></span></a>
								</sec:authorize></td>
						</tr>
					</c:forEach>
			</tbody>
		</table>
	</div>

	<div id="studentDialog"></div>
	<div id="deleteStudentDialog"></div>

	<script>
		$(document).ready(function() {
			$(function() {
				$("#studentDialog").dialog({
					title : "<spring:message code='student.modal.title' />",
					autoOpen : false,
					height : "auto",
					width : "800px",

				});
				$("#createStudent").on("click", function() {
					showStudentDialog("create");
				});
			});
		});

		$(document).ready(function() {
			$(function() {
				$("#deleteStudentDialog").dialog({
					title : "<spring:message code='student.modal.title' />",
					autoOpen : false,
					height : "auto",
					width : "auto",

				});
				$("#createStudent").on("click", function() {
					showStudentDialog("create");
				});
			});
		});

		var showStudentDialog = function(action, userid) {
			var ajaxUrl = "/mumsched/newupdatestudent";
			if (action == 'edit') {
				ajaxUrl = "/mumsched/edit-student-" + userid;
			}
			$.ajax({
				type : "GET",
				url : ajaxUrl,
				success : function(data) {
					var studentDialog = $("#studentDialog");
					studentDialog.html(data);
					studentDialog.dialog("open");
				},

				error : function(e) {
					alert('Error: ' + e);
				}
			});
		}

		var editStudent = function(userid) {
			showStudentDialog("edit", userid);
		}

		var deleteStudent = function(userid) {
			$.ajax({
				type : "GET",
				url : "/mumsched/delete-student-" + userid,
				success : function(data) {
					var studentDialog = $("#deleteStudentDialog");
					studentDialog.html(data);
					studentDialog.dialog("open");
					//loadCourses();
				},

				error : function(e) {
					alert('Error: ' + e);
				}
			});
		}
	</script>
</body>
</html>