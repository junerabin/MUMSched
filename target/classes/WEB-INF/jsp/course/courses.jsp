<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List Course</title>
<style>
.container {
	padding-left: 50px;
	padding-top: 20px;
}
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<input id="createCourse" type="button" value="Create Course"
				class="btn btn-info">
		</div>
		<br />
		<table class="table table-hover">
			<thead>
				<tr>
					<th></th>
					<th><spring:message code="course.name" /></th>
					<th><spring:message code="course.code" /></th>
					<th><spring:message code="course.prerequisite" /></th>
					<th><spring:message code="course.targetblock" /></th>
					<th><spring:message code="course.faculty" /></th>
					<th><spring:message code="course.action" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${listCourse}" var="course" varStatus="loop">
					<tr>
						<td>${loop.index + 1}</td>
						<td>${course.courseName}</td>
						<td>${course.courseCode}</td>
						<td>
							<c:forEach items="${course.coursesPre}" var="coursePre">
								${coursePre.courseName}
								<br/>
							</c:forEach>
						</td>
						<td>${course.targetBlock}</td>
						<td>
							<c:forEach items="${course.faculties }" var="faculty">
								${faculty.firstName }
								<br/>
							</c:forEach>
						</td>
						<td><a href='javascript:editCourse(${course.id})'><span class="glyphicon glyphicon-pencil"></span> </a>|
							<a href='javascript:deleteCourse(${course.id })'><span class="glyphicon glyphicon-trash"></span></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div id="courseDialog"></div>
	<div id="deleteCourseDialog"></div>

	<script>
		$(document).ready(function() {
			$(function() {
				$("#courseDialog").dialog({
					title: "<spring:message code='course.modal.title' />",
					autoOpen: false,
					height: "auto",
					width: "800px",
					
				});
				$("#createCourse").on("click", function() {
					showCourseDialog("create");
				});
			});
		});
		
		$(document).ready(function() {
			$(function() {
				$("#deleteCourseDialog").dialog({
					title: "<spring:message code='course.modal.delete' />",
					autoOpen: false,
					height: "auto",
					width: "auto",
					
				});
				$("#createCourse").on("click", function() {
					showCourseDialog("create");
				});
			});
		});
		
		var showCourseDialog = function(action, id) {
			var ajaxUrl = "/mumsched/createcourse";
			if (action == 'edit') {
				ajaxUrl = "/mumsched/edit/" + id; 
			}
			$.ajax({
				type : "GET",
				url : ajaxUrl,
				success : function(data) {
					var courseDialog = $("#courseDialog");
					courseDialog.html(data);
					courseDialog.dialog("open");
				},

				error : function(e) {
					alert('Error: ' + e);
				}
			});
		}
		
		var editCourse = function(id) {
			showCourseDialog("edit", id);
		}
		
		var deleteCourse = function(id){
			$.ajax({
				type : "GET",
				url : "/mumsched/delete/" + id,
				success : function(data) {	
					var courseDialog = $("#deleteCourseDialog");
					courseDialog.html(data);
					courseDialog.dialog("open");
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
