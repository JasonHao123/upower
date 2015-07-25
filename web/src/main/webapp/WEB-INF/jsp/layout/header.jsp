<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page pageEncoding="UTF-8" %>
	<div data-role="header" class="jqm-header">
<h2><a href="../" title="jQuery Mobile Demos home"><img src="<c:url value="/resources/img/jquery-logo.png" />" alt="jQuery Mobile"></a></h2>
<p><span class="jqm-version"></span> Demos</p>
<div data-role="navbar">
	<ul>
		<li><a href="<c:url value="/personal/index.do" />" <c:if test="${navi=='' || navi=='job' }">class="ui-btn-active"</c:if> >个人消息</a></li>
		<li><a href="<c:url value="/company/index.do" />" <c:if test="${navi=='company' }">class="ui-btn-active"</c:if> >群消息</a></li>
<!--				<li><a href="<c:url value="/agent/index.do" />" <c:if test="${navi=='agent' }">class="ui-btn-active"</c:if> >消息推广</a></li>
		 
 		<li><a href="<c:url value="/talent/index.do" />" <c:if test="${navi=='talent' }">class="ui-btn-active"</c:if> >找人才</a></li>
-->
		<li><a data-ajax="false" href="<c:url value="/user/index.do" />" <c:if test="${navi=='user' }">class="ui-btn-active"</c:if> >我的...</a></li>
	</ul>
</div><!-- /navbar -->
<c:choose>
<c:when test="${leftPane!=''}">
  		<a href="#" class="jqm-navmenu-link ui-btn ui-btn-icon-notext ui-corner-all ui-icon-bars ui-nodisc-icon ui-alt-icon ui-btn-left">

<spring:message code="${leftPane}" text="${leftPane}" />
</a>
</c:when>
<c:otherwise>
<a href="#" class="jqm-navmenu-link ui-btn ui-btn-icon-notext ui-corner-all ui-icon-back" data-rel="back"><spring:message code="page.button.title.back" text="Back" /></a>
</c:otherwise>
</c:choose><c:if test="${rightPane!=''}">
   		<a href="#" class="jqm-search-link ui-btn ui-btn-icon-notext ui-corner-all ui-icon-search ui-nodisc-icon ui-alt-icon ui-btn-right">

<spring:message code="${rightPane}" text="${rightPane}" />
</a>
</c:if>
	</div><!-- /header -->