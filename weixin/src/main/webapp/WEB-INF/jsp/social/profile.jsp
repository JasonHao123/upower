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
	<c:choose>
	<c:when test="${isSelf}">
	$('#edit').click(function() {
		$.mobile.navigate("<c:url value="/user/profile/edit.do" />");
	});
	</c:when>
	<c:otherwise>
$('#addFriend').click(function() {
	$.mobile.navigate("<c:url value="/user/addfriend.do" />?id=${profile.id}");
});
$('#contact').click(function() {
	$.mobile.navigate("conversation.do?id=${profile.id}");
});
</c:otherwise>
</c:choose>

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
});
//-->
</script>
<div role="main" class="ui-content jqm-content">
<label>Nickname:${profile.nickname}</label>
<label>Age: ${profile.age }</label>
<label>Social Distance: ${distance}</label>
<label>City:<c:forEach items="${profile.location }" var="location">${location}</c:forEach> </label>
<label>Hobby:<c:forEach items="${profile.hobby }" var="hobby">${hobby}</c:forEach> </label>
<label>Rating: </label><div id="userRating"></div>
<c:choose>
<c:when test="${isSelf}">
<button id="edit">Edit</button>
</c:when>
<c:otherwise>
<div class="ui-grid-b">
<div class="ui-block-a"><button id="addFriend" <c:if test="${isFriend}">disabled="disabled"</c:if> >Add Friend</button></div>
<div class="ui-block-b"><button id="contact">Conversation</button></div>
<div class="ui-block-c"><button id="comment">Comment</button></div>
</div>
</c:otherwise>
</c:choose>

<h2>Comments:</h2>
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