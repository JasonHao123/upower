<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>
<link rel="stylesheet" href="<c:url value="/resources/css/listview-grid.css" />">
<div role="main" class="ui-content jqm-content">
<label>Nickname</label>
<label>${profile.nickname}</label>
<c:choose>
<c:when test="${not isSelf}">
<a href="<c:url value="/social/addfriend.do" ><c:param name="id" value="${profile.id}" /></c:url>">follow</a>
</c:when>
<c:otherwise>
<a href="<c:url value="/user/profile/edit.do" ><c:param name="id" value="${profile.id}" /></c:url>">Edit</a>

</c:otherwise>
</c:choose>
</div>