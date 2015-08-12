<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page pageEncoding="UTF-8"%>

<div role="main">
<p>${body }</p>
<label>${userInfo.nickname}</label>
<label>${userInfo.country }-${userInfo.province }-${userInfo.city }</label>
<label>${userInfo.sex}</label>
<img alt="" src="${userInfo.headimagurl }">
</div>