<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<style>
.navbar-fixed-left {
	width: 140px;
	position: fixed;
	border-radius: 0;
	height: 100%;
}

.navbar-fixed-left .navbar-nav>li {
	float: none; /* Cancel default li float: left */
	width: 139px;
}

.navbar-fixed-left+.container {
	padding-right: 160px;
}

/* On using dropdown menu (To right shift popuped) */
.navbar-fixed-left .navbar-nav>li>.dropdown-menu {
	margin-top: -50px;
	margin-left: 140px;
}
</style>
<nav class="navbar navbar-inverse navbar-fixed-left">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="#"><spring:message
					code="application.name" /></a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li><a href="javascript:loadEntries()"><spring:message
								code="menu.entry" /></a></li>
					<li><a href="javascript:loadBlocks()"><spring:message
								code="menu.block" /></a></li>
					<li><a href="javascript:loadCourses()"><spring:message
								code="menu.course" /></a></li>
					<li><a href="javascript:loadSections()"><spring:message
								code="menu.section" /></a></li>
				</sec:authorize>

				<sec:authorize
					access="hasRole('ROLE_ADMIN') or hasRole('ROLE_FACULTY')">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false"><spring:message code="menu.faculty" />
							<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<sec:authorize access="hasRole('ROLE_ADMIN')">
<%-- 																<li><a href="javascript:viewFacultyProfile()"><spring:message
											code="menu.faculty.viewprofile" /></a></li> --%>
								<li><a href="javascript:loadSchedules()"><spring:message
											code="menu.faculty.viewschedule" /></a></li>
							</sec:authorize>
							<sec:authorize
								access="hasRole('ROLE_ADMIN') or hasRole('ROLE_FACULTY')">
								<li><a href="javascript:loadFaculties()"><spring:message
											code="menu.faculty.managefaculty" /></a></li>
							</sec:authorize>
						</ul></li>
				</sec:authorize>

				<sec:authorize
					access="hasRole('ROLE_ADMIN') or hasRole('ROLE_STUDENT')">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false"><spring:message code="menu.student" />
							<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<%-- 							<li><a href="javascript:studentViewSchedule()"><spring:message
										code="menu.student.viewschedule" /></a></li> --%>
							<li><a href="javascript:loadStudents();"><spring:message
										code="menu.student.view" /></a></li>
							<%-- 							<li><a href='javascript:loadRegisterSection(${studentId})'><spring:message
										code="menu.student.register" /></a></li> --%>
						</ul></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false"><spring:message code="menu.schedule" />
							<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="javascript:loadSchedules()"><spring:message
										code="menu.schedule.view" /></a></li>
							<li><a href="javascript:viewScheduleGeneration()"><spring:message
										code="menu.schedule.generate" /></a></li>
						</ul></li>
				</sec:authorize>
				<li><a href="javascript:logout()"><span
						class="glyphicon glyphicon-log-out"></span> <spring:message
							code="logout.label" /></a></li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>