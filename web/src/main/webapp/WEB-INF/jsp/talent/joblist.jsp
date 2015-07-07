<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>
<link rel="stylesheet" href="<c:url value="/resources/css/listview-grid.css" />">
	<div role="main" class="ui-content jqm-content">
        <ul data-role="listview" data-inset="true">
        <c:forEach items="${jobs}" var="job">
        	<li><a href="<c:url value="/talent/profile.do"><c:param name="id" value="${job.id}" /></c:url>">
            	<img src="<c:url value="/resources/img/apple.png" />" class="ui-li-thumb">
            	<h2>${job.realname}</h2>
                <p>${job.realname}</p>
                <p class="ui-li-aside">80%</p>
            </a></li>
            </c:forEach>
        	
        </ul>
        </div>