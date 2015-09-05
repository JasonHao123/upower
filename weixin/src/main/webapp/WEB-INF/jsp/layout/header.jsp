<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page pageEncoding="UTF-8" %>
	<div data-role="header" class="jqm-header">
<h2><a href="../" title="jQuery Mobile Demos home">${title}</a></h2>

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
   		<a href="#" class="jqm-search-link ui-btn ui-corner-all  ui-nodisc-icon ui-alt-icon ui-btn-right">

<spring:message code="${rightPane}" text="${rightPane}" />
</a>
</c:if>
	</div><!-- /header -->