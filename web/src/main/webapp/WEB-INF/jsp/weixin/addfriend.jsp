<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page pageEncoding="UTF-8"%>
<link rel="stylesheet" href="<c:url value="/resources/css/jquery.raty.css" />">
<script src="<c:url value="/resources/js/jquery.raty.js" />"></script>
<script type="text/javascript">
<!--
$( document ).on( "pagecreate", "#myPage", function() {
$('#targetScore').raty({
	  targetScore: '#target-score',
	  half     : true,
	  readOnly:'${self}',
	  score: '${addFriendForm.rating}'
	});
});
//-->
</script>
<div role="main" class="ui-content">

<h2>添加好友</h2>
<p>${link.user.nickname} 希望与你成为朋友，请选择好友类型并添加</p>
<c:choose>
<c:when test="${not expired}">
<c:choose>
<c:when test="${hasProfile}" >
<form id="myForm"  method="POST" data-ajax="false">
	<input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
		value="<c:out value="${_csrf.token}"/>" /> 
		<label for="friendshipType">关系类型</label>
<div class="ui-field-contain">
<select name="friendshipType" id="friendshipType" multiple="multiple" data-native-menu="false" >
<option>Choose options</option>
<c:forEach items="${friendshipTypes}" var="type">
<option value="${type.id}" <c:if test="${self}"> disabled="disabled"</c:if>
<c:forEach items="${addFriendForm.friendshipType }" var="friendType">
<c:if test="${friendType==type.id}">selected</c:if>
</c:forEach>
>${type.name }</option>

</c:forEach>
</select>
</div>
<label>友好度</label>
<div id="targetScore"></div>

<p><input id="target-score" name="rating" type="hidden" ></p>
	<div class="ui-grid-a ui-responsive">
		<div class="ui-block-a">
			<input type="submit" <c:if test="${self}"> disabled="disabled"</c:if> value="<spring:message
			code="page.label.signup.submit" />">
		</div>
		<div class="ui-block-b">
			<input type="reset" <c:if test="${self}"> disabled="disabled"</c:if> value="<spring:message
			code="page.label.signup.reset" />" />
		</div>

	</div>
	</form>
	</c:when>
	<c:otherwise>
	请先点击此<a href="<c:url value="/user/profile/edit.do" />" >链接</a>完善个人信息再添加好友。
	</c:otherwise>
	</c:choose>
</c:when>
<c:otherwise>
<p style="color: red">该邀请已经过期，请你的朋友发送最新的邀请链接</p>
</c:otherwise>
</c:choose>


</div>
