<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">

<title>${title}</title>
	<link rel="shortcut icon" href="<c:url value="/resources/favicon.ico" />">
	<link rel="shortcut icon" href="<c:url value="/resources/favicon.ico" />">	
	<link rel="stylesheet" href="<c:url value="/resources/css/jquery.mobile-1.4.5.min.css" />">
	<link rel="stylesheet" href="<c:url value="/resources/css/jqm-demos.css" />">
	
		<script src="<c:url value="/resources/js/jquery.js" />"></script>
	<script src="<c:url value="/resources/js/index.js" />"></script>
	<script src="<c:url value="/resources/js/jquery.mobile-1.4.5.min.js" />"></script>
	
</head>
<body>
<div data-role="page" id="myPage" class="jqm-demos jqm-home">

<tiles:insertAttribute name="content" />	

<tiles:insertAttribute name="footer" />
	
</div><!-- /page -->

</body>
</html>