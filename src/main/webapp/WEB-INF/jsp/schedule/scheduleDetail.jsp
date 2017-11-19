<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Schedule Detail</title>
<style>
.container {
	padding-left: 50px;
	padding-top: 20px;
}
</style>
</head>
<body>
	<div class="container">
		<div class="form-group">
			<c:out value="${scheduleInfo.entryName}" />
		</div>
		<table class="table table-hover">
			<thead>
				<tr>
					<th></th>
					<th><spring:message code="schedule.block.name" /></th>
					<th><spring:message code="schedule.block.dates" /></th>
					<th colspan="2"><spring:message code="schedule.courses.name" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${scheduleInfo.blockInfos}" var="blockInfo"
					varStatus="loop">
					<tr>
						<td>${loop.index + 1}</td>
						<td>${blockInfo.blockName}</td>
						<td>${blockInfo.blockPeriod}</td>
						<td>
							<table class="table table-bordered">
								<c:forEach items="${blockInfo.sectionInfos}" var="sectionInfo">
									<tr>
										<td>${sectionInfo.courseName}</td>
										<td>${sectionInfo.courseCode}</td>
										<td>${sectionInfo.facultyName}</td>
										<td>${sectionInfo.capacity}</td>
									</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>
