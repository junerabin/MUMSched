<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>User Registration</title>
	<style>
	    .error {
	        color: #ff0000;
	    }
	</style>
</head>
<body>
	<c:if test="${success != null}">
		<h2>${success}</h2>
	</c:if>
	<c:set var="actionUrl" value="registerUser" />
	<c:if test="${isOnLogin}">
		<c:set var="actionUrl" value="login" />
		<h2>${loginFailed}</h2>
	</c:if>
    <form:form method="POST" modelAttribute="user" action="${actionUrl}">
        <form:input type="hidden" path="id" id="id"/>
        <table>
            <tr>
                <td><label for="username">Username: </label> </td>
                <td><form:input path="username" id="username"/></td>
                <td><form:errors path="username" cssClass="error"/></td>
            </tr>
            <tr>
                <td><label for="password">Password: </label> </td>
                <td><form:password path="password" id="password"/></td>
                <td><form:errors path="password" cssClass="error"/></td>
            </tr>
            <tr>
                <td colspan="3">
                    <c:choose>
                        <c:when test="${isOnLogin}">
                            <input type="submit" value="Login"/>
                        </c:when>
                        <c:otherwise>
                            <input type="submit" value="Register"/>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </table>
    </form:form>
    <br/>
</body>
</html>