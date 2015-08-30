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
	  score: 3
	});
});
//-->
</script>
<div role="main" class="ui-content jqm-content">

<h2>添加好友</h2>
<p>您正在申请添加${user.nickname}为好友</p>
<form id="myForm"  method="POST" data-ajax="false">
	<input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
		value="<c:out value="${_csrf.token}"/>" /> 
		<input type="hidden" name="userId" value="${user.id}" >
		<label for="friendshipType">关系类型</label>
<div class="ui-field-contain">
<select name="friendshipType" id="friendshipType" multiple="multiple" data-native-menu="false">
<option>Choose options</option>
<c:forEach items="${friendshipTypes}" var="type">
<option value="${type.id}"

>${type.name }</option>

</c:forEach>
</select>
</div>
<label>友好度</label>
<div id="targetScore"></div>
<input id="target-score" name="rating" type="hidden" >
<c:if test="${empty addFriendRequestForm.id}">
<label>消息内容</label>
<textarea rows="4" cols="20" name="message">
</textarea>
</c:if>
	<div class="ui-grid-a ui-responsive">
		<div class="ui-block-a">
			<input type="submit" value="保存">
		</div>
		<div class="ui-block-b">
			<input type="reset" value="重置" />
		</div>

	</div>
	</form>

</div>
