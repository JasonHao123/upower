<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>
<link rel="stylesheet" href="<c:url value="/resources/css/jquery.raty.css" />">
<script src="<c:url value="/resources/js/jquery.raty.js" />"></script>
  <script>
		$( document ).on( "pagecreate", "#myPage", function() {

			
		});
		
</script>
		<div role="main" class="ui-content jqm-content">

<h1>${message.id}-${message.title}</h1>

<h2><a href="<c:url value="/user/profile.do" ><c:param name="id" value="${message.author.id}" /></c:url>" >${message.author.nickname}(${message.distance})</a></h2>
<p>${message.content}</p>
	<div class="ui-grid-a"><div class="ui-block-a"><button>回复</button></div>
		<div class="ui-block-b"><button>扩散</button></div>
	</div>
<h2>Comments:</h2>
					<ul data-role="listview" data-theme="d" data-divider-theme="d">
						<li><a href="index.html">
								<h3>Stephen Weber</h3>
								<p><strong>You've been invited to a meeting at Filament Group in Boston, MA</strong></p>
								<p>Hey Stephen, if you're available at 10am tomorrow, we've got a meeting with the jQuery team.</p>
								<p class="ui-li-aside"><strong>6:24</strong>PM</p>
						</a></li>
						<li><a href="index.html">
							<h3>jQuery Team</h3>
							<p><strong>Boston Conference Planning</strong></p>
							<p>In preparation for the upcoming conference in Boston, we need to start gathering a list of sponsors and speakers.</p>
							<p class="ui-li-aside"><strong>9:18</strong>AM</p>
						</a></li>
						<li><a href="index.html">
							<h3>Avery Walker</h3>
							<p><strong>Re: Dinner Tonight</strong></p>
							<p>Sure, let's plan on meeting at Highland Kitchen at 8:00 tonight. Can't wait! </p>
							<p class="ui-li-aside"><strong>4:48</strong>PM</p>
						</a></li>
						<li><a href="index.html">
							<h3>Amazon.com</h3>
							<p><strong>4-for-3 Books for Kids</strong></p>
							<p>As someone who has purchased children's books from our 4-for-3 Store, you may be interested in these featured books.</p>
							<p class="ui-li-aside"><strong>12:47</strong>PM</p>
						</a></li>
					</ul>

</div>