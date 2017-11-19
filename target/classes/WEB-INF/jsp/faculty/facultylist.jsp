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
<title>Faculty List</title>
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
				<input id="createFaculty" type="button" value="Create Faculty"
					class="btn btn-info">
			</div>
		</sec:authorize>

		<br />
		<table class="table table-hover">
			<thead>
				<tr>
					<th></th>
					<th><spring:message code="faculty.firstname" /></th>
					<th><spring:message code="faculty.lastname" /></th>
					<th><spring:message code="faculty.dob" /></th>
					<th><spring:message code="faculty.email" /></th>
					<th><spring:message code="faculty.courses" /></th>
					<th><spring:message code="faculty.blocks" /></th>
					<th><spring:message code="faculty.action" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${faculties}" var="faculty" varStatus="loop">
					<tr>
						<td>${loop.index + 1}</td>
						<td>${faculty.firstName}</td>
						<td>${faculty.lastName}</td>
						<td><fmt:formatDate pattern="MM/dd/yyyy"
								value="${faculty.dob}" /></td>
						<td>${faculty.email}</td>
						<td>
							<table>
								<tbody>
									<c:forEach items="${faculty.courses}" var="course">
										<tr>
											<td>${course.courseName}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</td>
						<td>${faculty.targetBlock}</td>
						<td><a href='javascript:editFaculty(${faculty.id})'><span
								class="glyphicon glyphicon-pencil"></span> </a> <sec:authorize
								access="hasRole('ROLE_ADMIN')"> |
								<a href='javascript:deleteFaculty(${faculty.id})'><span
									class="glyphicon glyphicon-trash"></span></a>
							</sec:authorize></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div id="facultyDialog"></div>
	<div id="deleteFacultyDialog"></div>

	<script>
		$(document).ready(function() {
			$(function() {
				$("#facultyDialog").dialog({
					title : "<spring:message code='faculty.modal.title' />",
					autoOpen : false,
					height : "auto",
					width : "800px",

				});
				$("#createFaculty").on("click", function() {
					showFacultyDialog("create");
				});
			});
		});

		$(document).ready(function() {
			$(function() {
				$("#deleteFacultyDialog").dialog({
					title : "<spring:message code='faculty.modal.title' />",
					autoOpen : false,
					height : "auto",
					width : "auto",

				});
				$("#createFaculty").on("click", function() {
					showFacultyDialog("create");
				});
			});
		});

		var showFacultyDialog = function(action, userid) {
			var ajaxUrl = "/mumsched/newupdatefaculty";
			if (action == 'edit') {
				ajaxUrl = "/mumsched/edit-faculty-" + userid;
			}
			$.ajax({
				type : "GET",
				url : ajaxUrl,
				success : function(data) {
					var facultyDialog = $("#facultyDialog");
					facultyDialog.html(data);
					facultyDialog.dialog("open");
				},

				error : function(e) {
					alert('Error: ' + e);
				}
			});
		}

		var editFaculty = function(userid) {
			showFacultyDialog("edit", userid);
		}

		var deleteFaculty = function(userid) {
			$.ajax({
				type : "GET",
				url : "/mumsched/delete-faculty-" + userid,
				success : function(data) {
					var facultyDialog = $("#deleteFacultyDialog");
					facultyDialog.html(data);
					facultyDialog.dialog("open");
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