<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page pageEncoding="UTF-8"%>

<div role="main">
<ul data-role="listview">
<h1>请选择以下用户类型</h1>
<li><a href="<c:url value="/signupuser.do" />">个人用户</a></li>
<li><a href="<c:url value="/signupagent.do" />" >人才中介</a></li>
<li><a  href="<c:url value="/signupcompany.do" />" >企业用户</a></li>
</ul>
</div>