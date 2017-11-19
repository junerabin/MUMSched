<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
			<input id="createBlock" type="button" value="Create Block"
				class="btn btn-info">
		</div>
		<br />
		<table class="table table-hover">
			<thead>
				<tr>
					<th></th>
					<th><spring:message code="block.name" /></th>
					<th><spring:message code="block.entry" /></th>
					<th><spring:message code="block.startDate" /></th>
					<th><spring:message code="block.endDate" /></th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${listBlock}" var="block" varStatus="loop">
					<tr>
						<td>${loop.index + 1}</td>
						<td>${block.name}</td>
						<td>
						<c:forEach items="${block.entries}" var="entry" >
							${entry.name }
							<br/>
						</c:forEach>
						</td>
						<td><fmt:formatDate value="${block.startDate}" pattern="MM/dd/yyyy " /></td>
						<td><fmt:formatDate value="${block.endDate}" pattern="MM/dd/yyyy " /></td>						
						<td><a href='javascript:editBlock(${block.id})'><span class="glyphicon glyphicon-pencil"></span> </a>|
							<a href='javascript:deleteBlock(${block.id })'><span class="glyphicon glyphicon-trash"></span></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<div id="blockDialog"></div>
	<div id="deleteBlockDialog"></div>
	
	<script>
		$(document).ready(function() {
			$(function() {
				$("#blockDialog").dialog({
					title: "<spring:message code='block.title' />",
					autoOpen: false,
					height: "auto",
					width: "800px",
					
				});
				$("#createBlock").on("click", function() {
					showBlockDialog("create");
				});
			});
		});
		
		$(document).ready(function() {
			$(function() {
				$("#deleteBlockDialog").dialog({
					title: "<spring:message code='block.delete' />",
					autoOpen: false,
					height: "auto",
					width: "auto",
					
				});
				$("#createBlock").on("click", function() {
					showBlockDialog("create");
				});
			});
		});
		
		var showBlockDialog = function(action, id) {
			var ajaxUrl = "/mumsched/createblock";
			if (action == 'edit') {
				ajaxUrl = "/mumsched/editblock/" + id; 
			}
			$.ajax({
				type : "GET",
				url : ajaxUrl,
				success : function(data) {
					var blockDialog = $("#blockDialog");
					blockDialog.html(data);
					blockDialog.dialog("open");
				},

				error : function(e) {
					alert('Error: ' + e);
				}
			});
		}
		
		var editBlock = function(id) {
			showBlockDialog("edit", id);
		}
		
		var deleteBlock = function(id){
			$.ajax({
				type : "GET",
				url : "/mumsched/deleteblock/" + id,
				success : function(data) {	
					var blockDialog = $("#deleteBlockDialog");
					blockDialog.html(data);
					blockDialog.dialog("open");
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
