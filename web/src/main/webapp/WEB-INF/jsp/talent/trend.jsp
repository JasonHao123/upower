<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>

	<div role="main" class="ui-content  jqm-content">
	display search criteria and job market trend here
	<form action="<c:url value="/talent/search.do" />">
		<input type="search" name="q">
	</form>
	</div>