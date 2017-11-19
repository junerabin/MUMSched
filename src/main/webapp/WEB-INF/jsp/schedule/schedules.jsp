<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Schedules</title>
<style>
.container {
	padding-left: 50px;
	padding-top: 20px;
}
</style>
</head>
<body>
	<div class="container">
		<table class="table table-hover">
			<thead>
				<tr>
					<th></th>
					<th><spring:message code="entry.name" /></th>
					<th><spring:message code="schedule.status" /></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${schedules}" var="schedule" varStatus="loop">
					<tr>
						<td>${loop.index + 1}</td>
						<td>${schedule.entry.name}</td>
						<td>${schedule.status}</td>
						<td><a href='javascript:viewScheduleDetail(${schedule.id})'><span class="glyphicon glyphicon-pencil"></span>
						</a>
						<c:if test="${isFaculty == null}">
							| <a href='javascript:showConfirmDeleteScheduleDialog(${schedule.id})'><span class="glyphicon glyphicon-trash"></span></a>
						</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div id="confirmDeleteScheduleDialog">
		<spring:message code="schedule.modal.confirmdelete.content" />
		<input type="hidden" id="scheduleId">
	</div>

	<script>
		$(document).ready(function() {
			$("#confirmDeleteScheduleDialog").dialog({
				title : "<spring:message code='schedule.modal.confirmdelete.title' />",
				autoOpen : false,
				height : "auto",
				width : "auto",
				modal: true,
		     	buttons: {
		        	Yes: function() {
		        		var ajaxUrl = "/mumsched/deleteSchedule/" + $("#scheduleId").val();
		    			$.ajax({
		    				type : "GET",
		    				url : ajaxUrl,
		    				success : function(data) {
		    					$("#confirmDeleteScheduleDialog").dialog("close");
		    					loadSchedules();
		    				},
		    				error : function(e) {
		    					alert(e.responseJSON["message"]);
		    					$("#confirmDeleteScheduleDialog").dialog("close");
		    				}
		    			});
			        },
			        Cancel: function() {
			        	$(this).dialog("close");
			        }
		      }
			});
		});

		var showConfirmDeleteScheduleDialog = function(id) {
			$("#scheduleId").val(id);
			$("#confirmDeleteScheduleDialog").dialog("open");
		}
		
		var viewScheduleDetail = function(id) {
			loadScheduleDetail(id);
		}
	</script>
</body>
</html>
