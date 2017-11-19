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
<title>Entry</title>
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
		<input id="createEntry" type="button" value="Create Entry"
			class="btn btn-info">
	</div>
		<br />
	<table class="table table-hover">
			<thead>
				<tr>
					<th></th>
					<th><spring:message code="entry.name" /></th>
					<th><spring:message code="entry.numberOfFPP" /></th>
					<th><spring:message code="entry.numberOfMPP" /></th>					
					<th><spring:message code="entry.percentOfOPT" /></th>
					<th><spring:message code="entry.startDate"></spring:message>
					<th><spring:message code="entry.endDate"></spring:message>
					<th><spring:message code="entry.action" /></th>		
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${listEntry}" var="entry" varStatus="loop">
					<tr>
						<td>${loop.index + 1}</td>
						<td>${entry.name}</td>
						<td>${entry.totalFpp}</td>
						<td>${entry.totalMpp}</td>
						<td>${entry.percentOpt}</td>
						<%-- <td>
						<c:forEach items="${entry.blocks}" var="block" >
							${block.name }
							<br/>
						</c:forEach>
						</td> --%>
						<td><fmt:formatDate value="${entry.startDate}" pattern="MM/dd/yyyy " /></td>
						<td><fmt:formatDate value="${entry.endDate}" pattern="MM/dd/yyyy " /></td>
						<td><a href='javascript:editEntry(${entry.id})'><span class="glyphicon glyphicon-pencil"></span> </a>|
							<a href='javascript:deleteEntry(${entry.id })'><span class="glyphicon glyphicon-trash"></span></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>
<div id="entryDialog"></div>
<div id="deleteEntryDialog"></div>
<script>

	$(document).ready(function() {
		$(function() {
			$("#entryDialog").dialog({
				title: "<spring:message code='entry.model.title' />",
				autoOpen: false,
				height: "auto",
				width: "800px",
				
			});
			$("#createEntry").on("click", function() {
				showEntryDialog("create");
			});
		});
	});
	
	var editEntry = function(id) {
		showEntryDialog("edit", id);
	}
	
	var showEntryDialog = function(action, id) {
			var ajaxUrl = "/mumsched/createentry";
			if (action == 'edit') {
				ajaxUrl = "/mumsched/editentry/" + id; 
			}
			$.ajax({
				type : "GET",
				url : ajaxUrl,
				success : function(data) {
					var courseDialog = $("#entryDialog");
					courseDialog.html(data);
					courseDialog.dialog("open");
				},

				error : function(e) {
					alert('Error: ' + e);
				}
			});
		}
	
	$(document).ready(function() {
		$(function() {
			$("#deleteEntryDialog").dialog({
				title: "<spring:message code='entry.delete' />",
				autoOpen: false,
				height: "auto",
				width: "auto",
				
			});
			$("#createCourse").on("click", function() {
				showCourseDialog("create");
			});
		});
	});
	
	var deleteEntry = function(id){
		$.ajax({
			type : "GET",
			url : "/mumsched/deleteentry/" + id,
			success : function(data) {	
				var courseDialog = $("#deleteEntryDialog");
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