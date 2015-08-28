<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>
<link rel="stylesheet" href="<c:url value="/resources/css/jquery.raty.css" />">
<script src="<c:url value="/resources/js/jquery.raty.js" />"></script>
<script type="text/javascript">
<!--
$( document ).on( "pagecreate", "#myPage", function() {

$('#userRating').raty({
	  half     : true,
	  score: 4,
	  readOnly: true
	});
$(".ui-li-aside").each(function() {
	$(this).raty({
		  
		  half     : true,
		  score: 3.5,
		  readOnly:true
		});
});
<c:if test="${not isSelf}">
	$("#disRating").raty({
		  
		  half     : true,
		  score: 4,
		  readOnly:true
		});
</c:if>

$("#stars").raty({
	  
	  half     : true,
	  score: 0
	});

});
//-->
</script>
<div role="main" class="ui-content jqm-content">
<c:if test="${not empty profile.headimgurl}" >
<div style="float: right">
<img src="${profile.headimgurl }" height="130px" />
</div>
</c:if>
<label>昵称:${profile.nickname}<c:choose>
<c:when test="${profile.sex==1}">（男）</c:when>
<c:when test="${profile.sex==2}">（女）</c:when>
<c:otherwise>（性别未知）</c:otherwise></c:choose>
</label>
<label>年龄: ${profile.age }</label>
<label>城市:<c:forEach items="${profile.location }" var="location">${location}</c:forEach> </label>
<label>爱好:<c:forEach items="${profile.hobby }" var="hobby">${hobby}</c:forEach> </label>
<c:if test="${not isSelf}">
<div id="disRating" style="font-size: 14;padding-bottom: 5px;">社交距离: ${distance}&nbsp;&nbsp;&nbsp;</div>
</c:if>
<div id="userRating" style="font-size: 14;padding-bottom: 5px;">评价: </div>
<c:choose>
<c:when test="${isSelf}">
<div class="ui-grid-b">
<div class="ui-block-a"><a data-role="button" data-ajax="false" href="<c:url value="/social/profile/edit.do" />" >编辑</a></div>
<div class="ui-block-b"><a data-role="button" data-ajax="false" href="<c:url value="/social/invite.do" />">邀请好友</a></div>
<div class="ui-block-c"><a data-role="button" data-ajax="false" href="<c:url value="/social/power.do" />">社交分析</a></div>
</div>
</c:when>
<c:otherwise>
<div class="ui-grid-b">
<div class="ui-block-a"><a data-role="button" data-ajax="false" href="<c:url value="/social/addfriend.do"><c:param name="id" value="${profile.id}" /></c:url>" ><c:choose><c:when test="${isFriend}">修改关系</c:when><c:otherwise>添加好友</c:otherwise></c:choose></a></div>
<div class="ui-block-b"><a data-role="button" data-ajax="false" href="<c:url value="/social/conversation.do"><c:param name="id" value="${profile.id}" /></c:url>">聊天记录</a></div>
<div class="ui-block-c"><a data-role="button" data-ajax="false" href="<c:url value="/social/power.do" />">社交分析</a></div>
</div>
</c:otherwise>
</c:choose>

<h2>评价:</h2>
					<ul data-role="listview" data-theme="d" data-divider-theme="d">
						<li><a href="<c:url value="/user/profile.do" ><c:param name="id" value="3" /></c:url>" data-ajax="false">
								<h3>Stephen Weber</h3>
								<p><strong>You've been invited to a meeting at Filament Group in Boston, MA</strong></p>
								<p>Hey Stephen, if you're available at 10am tomorrow, we've got a meeting with the jQuery team.</p>
								<p class="ui-li-aside"></p>
						</a></li>
						<li><a href="<c:url value="/user/profile.do" ><c:param name="id" value="4" /></c:url>" data-ajax="false">
							<h3>jQuery Team</h3>
							<p><strong>Boston Conference Planning</strong></p>
							<p>In preparation for the upcoming conference in Boston, we need to start gathering a list of sponsors and speakers.</p>
							<p class="ui-li-aside"></p>
						</a></li>
						<li><a href="<c:url value="/user/profile.do" ><c:param name="id" value="5" /></c:url>" data-ajax="false">
							<h3>Avery Walker</h3>
							<p><strong>Re: Dinner Tonight</strong></p>
							<p>Sure, let's plan on meeting at Highland Kitchen at 8:00 tonight. Can't wait! </p>
							<p class="ui-li-aside"></p>
						</a></li>
						<li><a href="<c:url value="/user/profile.do" ><c:param name="id" value="6" /></c:url>" data-ajax="false">
							<h3>Amazon.com</h3>
							<p><strong>4-for-3 Books for Kids</strong></p>
							<p>As someone who has purchased children's books from our 4-for-3 Store, you may be interested in these featured books.</p>
							<p class="ui-li-aside"></p>
						</a></li>
					</ul>
</div>


<div id="commentFooter" data-role="footer" data-position="fixed" data-tap-toggle="false" >
	<div id="stars">评价：</div>
		
  		<input id="messageContent" type="text" />

   		<a id="send" href="#" class=" ui-btn  ui-corner-all ui-btn-right" style="top: -0.4em">发送</a>

	</div><!-- /footer -->