<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page pageEncoding="UTF-8"%>
<c:set var="leftPane" scope="request">
	<tiles:insertAttribute name="leftPane" ignore="true" />
</c:set>
<c:set var="rightPane" scope="request">
	<tiles:insertAttribute name="rightPane" ignore="true" />
</c:set>
<c:set var="navi" scope="request">
	<tiles:insertAttribute name="navi" ignore="true" />
</c:set>
<c:set var="titleKey">
	<tiles:insertAttribute name="title" ignore="true" />
</c:set>
<c:set var="title" scope="request">
<spring:message code="${titleKey}" text="${titleKey}" />&nbsp;-&nbsp;<spring:message code="site.name" text="Smart Knowledgebase" />
</c:set>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">

<title>${title}</title>
	<link rel="shortcut icon" href="<c:url value="/resources/favicon.ico" />">	
	<link rel="stylesheet" href="<c:url value="/resources/css/jquery.mobile-1.4.5.min.css" />">
	<link rel="stylesheet" href="<c:url value="/resources/css/jqm-demos.css" />">
	<link rel="stylesheet" href="<c:url value="/resources/css/jquery-ui.css" />">
	<link rel="stylesheet" href="<c:url value="/resources/css/jquery.tagit.css" />">
<!-- 	<link rel="stylesheet" href="<c:url value="/resources/css/listview-grid.css" />"> 
	<link rel="stylesheet" href="<c:url value="/resources/css/redactor.css" />">
 --> 
	<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
	<script src="<c:url value="/resources/js/jquery.js" />"></script>
	<script src="<c:url value="/resources/js/index.js" />"></script>
	<script src="<c:url value="/resources/js/jquery.mobile-1.4.5.min.js" />"></script>
	<script src="<c:url value="/resources/js/jquery-ui.js" />" type="text/javascript" charset="utf-8"></script>
	<script src="<c:url value="/resources/js/tag-it.js" />" type="text/javascript" charset="utf-8"></script>
	<script src="<c:url value="/resources/js/redactor.js" />" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<div data-role="page" id="myPage" class="jqm-demos jqm-home">

<tiles:insertAttribute name="header" />
<tiles:insertAttribute name="content" />	


		<c:if test="${leftPane!=''}">
		<tiles:insertAttribute name="left" />
		</c:if>
		<tiles:insertAttribute name="footer" />
		<c:if test="${rightPane!=''}">
		<tiles:insertAttribute name="right" />
		</c:if>

</div><!-- /page -->

</body>
</html>