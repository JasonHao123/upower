<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>
<link rel="stylesheet" href="<c:url value="/resources/css/listview-grid.css" />">
	<div role="main" class="ui-content jqm-content">
        <ul data-role="listview" data-inset="true">
        <c:forEach items="${departments}" var="company">
 
        	<li><a href="<c:url value="/user/department/list.do" ><c:param name="id" value="${company.id}" /></c:url>">
            	<img src="<c:url value="/resources/img/apple.png" />" class="ui-li-thumb">
            	<h2>${company.name}</h2>
                <p>Apple released iOS 6.1</p>
                <p class="ui-li-aside">iOS</p>
            </a></li>
                   </c:forEach>

        	<li><a href="<c:url value="/user/department/add.do" ><c:param name="companyId" value="${companyId}" /></c:url>" data-ajax="false">
            	<h2>Add</h2>
                
            </a></li>
        </ul>
        </div>