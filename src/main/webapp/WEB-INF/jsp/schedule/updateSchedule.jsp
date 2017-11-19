<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form:form id="editScheduleForm" method="POST"
	modelAttribute="scheduleInfo" action="/mumsched/updateSchedule"
	role="form">
	<form:input type="hidden" path="scheduleId" />
	<jsp:include page="scheduleDetail.jsp" />
	<c:if test="${isFaculty == null}">
		<div class="form-group">
			<div class="col-sm-2">
				<form:select class="form-control" path="status">
					<form:option value="DRAFT" />
					<form:option value="UNDER REVIEW" />
					<form:option value="APPROVED" />
				</form:select>
			</div>

			<input type="submit" value="Update" class="btn btn-primary" />
		</div>
	</c:if>
</form:form>

<script>
	$('#editScheduleForm').submit(function(event) {
		event.preventDefault(); // Prevent the form from submitting via the browser
		var form = $(this);
		$.ajax({
			type : form.attr('method'),
			url : form.attr('action'),
			data : form.serialize(),
			success : function(data) {
				loadSchedules();
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	});
</script>