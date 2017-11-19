<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style>
.form-group {
	padding-left: 60px;
}
</style>

<form:form id="generateScheduleForm" method="POST"
	modelAttribute="scheduleInfo" action="/mumsched/generateSchedule"
	role="form">
	<div class="form-group">
		<div class="col-sm-2">
			<form:select class="form-control" path="entryId">
				<form:option value="0">
					<spring:message code="select.default.message" />
				</form:option>
				<form:options items="${scheduleInfo.entries}" itemValue="id"
					itemLabel="name" />
			</form:select>
		</div>
	</div>
	<div class="form-group">
		<input type="submit"
			value='<spring:message code="schedule.button.generate" />'
			class="btn btn-primary" />
	</div>
</form:form>

<div id="unselectedWarningDialog">
	<spring:message code="schedule.modal.unselectedentry.content" />
</div>

<script>
	$('#generateScheduleForm').submit(function(event) {
		event.preventDefault(); // Prevent the form from submitting via the browser
		var form = $(this);
		var selectedEntryValue = $('#entryId option:selected').val();
		if (selectedEntryValue == 0) {
			$("#unselectedWarningDialog").dialog("open");
			return;
		}
		$.ajax({
			type : form.attr('method'),
			url : form.attr('action'),
			data : form.serialize(),
			success : function(data) {
				loadSchedule(data);
			},
			error : function(e) {
				alert(e.responseJSON["message"]);
			}
		});
	});

	$("#unselectedWarningDialog")
			.dialog(
					{
						title : '<spring:message code="schedule.modal.unselectedentry.title" />',
						autoOpen : false,
						height : "auto",
						width : "auto",
						modal : true,
						buttons : {
							Ok : function() {
								$(this).dialog("close");
							}
						}
					});
</script>