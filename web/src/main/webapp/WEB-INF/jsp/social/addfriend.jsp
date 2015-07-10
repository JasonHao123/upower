<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page pageEncoding="UTF-8"%>

<div role="main" class="ui-content jqm-content">
<c:choose>
<c:when test="${self}">
<h2>邀请好友</h2>
<p>快把此页面分享给好友或分享到朋友圈，让朋友们添加你为好友。别忘了添加好友后看看自己的势力状况啊！</p>

<p style="color: red">链接有效期为7天，过期后请重新生成链接再次分享给好友</p>

</c:when>
<c:otherwise>
<h2>添加好友</h2>
<p>${link.user.nickname} 希望与你成为朋友，请选择好友类型并添加</p>
<c:choose>
<c:when test="${not expired}">
<form action="#" method="get">

<div class="ui-field-contain"><label for="select-custom-19">Friendship Type:</label><select name="select-custom-19" id="select-custom-19" multiple="multiple" data-native-menu="false">
<option>Choose options</option>
<option value="0">Ｎormal</option>
<option value="1">Colleague</option>
<option value="2" >Friend</option>
<option value="3" >Business partner</option>
<option value="4">Classmate</option>
<option value="5">Relative</option>
<option value="6">Secret</option>
</select>
</div>
</form>

<button>Add</button>
</c:when>
<c:otherwise>
<p style="color: red">该邀请已经过期，请你的朋友发送最新的邀请链接</p>
</c:otherwise>
</c:choose>
</c:otherwise>
</c:choose>

</div>