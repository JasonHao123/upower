<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>
<link rel="stylesheet" href="<c:url value="/resources/css/jquery.raty.css" />">
<script src="<c:url value="/resources/js/jquery.raty.js" />"></script>
<script type="text/javascript">
<!--
$( document ).on( "pagecreate", "#myPage", function() {
$('#targetScore').raty({
	  targetScore: '#target-score',
	  half     : true,
	  score: 3
	});
});
//-->
</script>
<div role="main" class="ui-content jqm-content">
<form method="post" data-ajax="false">
<h2>隐私设置</h2>
<label>谁可以查看我的个人信息</label>
<select name="profileVisibility" id="profileVisibility" >
<c:forEach items="${profileVisibility}" var="type">
<option value="${type.id}"
<c:if test="${settingsForm.profileVisibility==type.id}">selected</c:if>
>${type.name }</option>

</c:forEach>
</select>
<label for="flip-3">显示对我的评论:</label>
<select name="flip-3" id="flip-3" data-role="slider">
<option value="off">Off</option>
<option value="on">On</option>
</select>
<label for="flip-4">显示对我的标签:</label>
<select name="flip-4" id="flip-4" data-role="slider">
<option value="off">Off</option>
<option value="on">On</option>
</select>
<h2>朋友圈设置</h2>
<label for="slider-2">朋友圈范围:</label>
<input type="range" name="personalCircal" id="slider-2" value="${settingsForm.personalCircal}" min="1" max="5" data-highlight="true">
<label for="slider-3">群朋友圈范围:</label>
<input type="range" name="groupCircle" id="slider-3" value="${settingsForm.groupCircle}" min="1" max="7" data-highlight="true">

<h2>消息设置</h2>
<!-- 
<label for="flip-2">接收朋友圈消息:</label>
<select name="flip-2" id="flip-2" data-role="slider">
<option value="off">Off</option>
<option value="on">On</option>
</select>
 -->
<label for="friendshipType">接受消息类型</label>
<div class="ui-field-contain">
<select name="acceptMessageTypes" id="messageType" multiple="multiple" data-native-menu="false">
<option>Choose options</option>
<c:forEach items="${categories}" var="type">
<option value="${type.id}"
<c:forEach items="${settingsForm.acceptMessageTypes }" var="friendType">
<c:if test="${friendType==type.id}">selected</c:if>
</c:forEach>
>${type.name }</option>

</c:forEach>
</select>
</div>
<!-- 
<h2>私信设置</h2>

<label for="flip-3">允许朋友圈好友给我发私信:</label>
<select name="flip-3" id="flip-3" data-role="slider">
<option value="off">Off</option>
<option value="on">On</option>
</select>
<label for="friendshipType">可以给我私信的关系类型</label>
<div class="ui-field-contain">
<select name="friendshipType" id="friendshipType" multiple="multiple" data-native-menu="false">
<option>Choose options</option>
<c:forEach items="${friendshipTypes}" var="type">
<option value="${type.id}"
<c:forEach items="${addFriendForm.friendshipType }" var="friendType">
<c:if test="${friendType==type.id}">selected</c:if>
</c:forEach>
>${type.name }</option>

</c:forEach>
</select>
</div>

<label>可以给我私信的最低友好度</label>
<div id="targetScore"></div>
<p><input id="target-score" name="rating" type="hidden" ></p>
 -->
 <div class="ui-grid-a ui-responsive">
		<div class="ui-block-a">
			<input type="submit" value="<spring:message
			code="page.label.signup.submit" />">
		</div>
		<div class="ui-block-b">
			<input type="reset" value="<spring:message
			code="page.label.signup.reset" />" />
		</div>

	</div>
</form>
</div>