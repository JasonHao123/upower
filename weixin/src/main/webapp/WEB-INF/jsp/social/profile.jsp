<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>
<link rel="stylesheet" href="<c:url value="/resources/css/jquery.raty.css" />">
<script src="<c:url value="/resources/js/jquery.raty.js" />"></script>

<script type="text/javascript">
<!--
var busy = false;
var pageNo = 0;
function getData() {
	if(pageNo==0) {
		$("#comments").empty();
	}
	$.getJSON("<c:url value="/social/comments.do" ><c:param name="id" value="${profile.id}" /></c:url>&page="+pageNo,function(result){
	    if(result.length>0) {
	    	
		    $.each(result, function(i, message){
		    	  var item = "<li id=\"comment"+message.id+"\"><h3>"+message.author.nickname+"</h3><p style='white-space:pre-wrap;'>"+message.message+"</p><p rating=\""+message.rating+"\" class=\"ui-li-aside\"></p></li>";		    	  
		    	  $(item).appendTo( $( "#comments" ) );
		    	
			    });
		    $("#comments").listview("refresh");
		    $(".ui-li-aside").each(function(a,b){
	    		$(b).raty({		  
			    	  half     : true,
			    	  score: $(b).attr("rating"),
			    	  readOnly:true
			    	});
	    	});
			pageNo = pageNo + 1;
	    }
		busy = false;
	  });
}

$( document ).on( "pagecreate", "#myPage", function() {
	
	$(document).on("scrollstop",function(){
		//Check the user is at the bottom of the element
		if($(window).scrollTop() + $(window).height() >= $(this).height() && !busy) {
		 
		    // Now we are working, so busy is true
		    busy = true;
		    setTimeout(function() {
		        // This is the Ajax function                    
		        getData();
		 
		    }, 500);
		 
		}
		});
	getData();

$('#userRating').raty({
	  half     : true,
	  score: ${userRating},
	  readOnly: true
	});
<c:if test="${not isSelf}">
	$("#disRating").raty({
		  
		  half     : true,
		  score: ${distance.rating},
		  readOnly:true
		});
</c:if>

	$("#stars").raty({
	  targetScore: '#commentScore',
	  half     : true,
	  score: 0
	});
	
	$("#send").click(function() {
		$.post( "<c:url value="/social/comment.do" />", { id: ${profile.id}, rating: $("#commentScore").val(),message:$("#messageContent").val() })
		  .done(function( message ) {
			  if($("#comment"+message.id).length>0) {
				 var item  = $("#comment"+message.id)[0];
				  $(item).empty();
				  $(item).html("<h3>"+message.author.nickname+"</h3><p style='white-space:pre-wrap;'>"+message.message+"</p><p rating=\""+message.rating+"\" class=\"ui-li-aside\"></p>");
				  item.scrollIntoView(true);
			  }else {
		    	  var item = $("<li id=\"comment"+message.id+"\"><h3>"+message.author.nickname+"</h3><p style='white-space:pre-wrap;'>"+message.message+"</p><p rating=\""+message.rating+"\" class=\"ui-li-aside\"></p></li>");		    	  
		    	  $(item).appendTo( $( "#comments" ) );
		    	  item[0].scrollIntoView(true);
			  }
			  
	    	  $("#comments").listview("refresh");
			    $(".ui-li-aside").each(function(a,b){
		    		$(b).raty({		  
				    	  half     : true,
				    	  score: $(b).attr("rating"),
				    	  readOnly:true
				    	});
		    	});

				$("#messageContent").val("")
		  });
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
<label>城市:<c:choose><c:when test="${not empty profile.province }">${profile.province}<c:if test="${not empty profile.city}">-${profile.city}</c:if></c:when><c:otherwise>未知</c:otherwise></c:choose></label>
<label style="display: none;">城市:<c:forEach items="${profile.location }" var="location">${location}</c:forEach> </label>
<label>爱好:<c:forEach items="${profile.hobby }" var="hobby">${hobby}</c:forEach> </label>
<c:if test="${not isSelf }">
<div id="disRating" style="font-size: 14;padding-bottom: 5px;">社交距离: ${distance.distance}&nbsp;&nbsp;&nbsp;</div>
</c:if>
<div id="userRating" style="font-size: 14;padding-bottom: 5px;">评价: </div>
<c:choose>
<c:when test="${isSelf}">
<div class="ui-grid-a">
<div class="ui-block-a"><a data-role="button" data-ajax="false" href="<c:url value="/social/profile/edit.do" />" >编辑</a></div>
<div class="ui-block-b"><a data-role="button" data-ajax="false" href="<c:url value="/social/invite.do" />">邀请好友</a></div>
</div>
</c:when>
<c:otherwise>
<div class="ui-grid-a">
<div class="ui-block-a"><a data-role="button" data-ajax="false" href="<c:url value="/social/addfriend.do"><c:param name="id" value="${profile.id}" /></c:url>" ><c:choose><c:when test="${isFriend}">修改关系</c:when><c:otherwise>添加好友</c:otherwise></c:choose></a></div>
<div class="ui-block-b"><a data-role="button" data-ajax="false" href="<c:url value="/social/conversation.do"><c:param name="id" value="${profile.id}" /></c:url>">聊天记录</a></div>
</div>
</c:otherwise>
</c:choose>

<h2>评价:</h2>
					<ul data-role="listview" data-theme="d" data-divider-theme="d" id="comments">
						<li>
								<h3>Stephen Weber</h3>
								<p><strong>You've been invited to a meeting at Filament Group in Boston, MA</strong></p>
								<p>Hey Stephen, if you're available at 10am tomorrow, we've got a meeting with the jQuery team.</p>
								<p class="ui-li-aside"></p>
						</li>
						<li>
							<h3>jQuery Team</h3>
							<p><strong>Boston Conference Planning</strong></p>
							<p>In preparation for the upcoming conference in Boston, we need to start gathering a list of sponsors and speakers.</p>
							<p class="ui-li-aside"></p>
						</li>

					</ul>
</div>

<c:if test="${not isSelf}">
<div id="commentFooter" data-role="footer" data-position="fixed" data-tap-toggle="false" >
	<div id="stars"><input id="commentScore" name="rating" type="hidden" >评价：</div>
		
  		<input id="messageContent" type="text" />

   		<a id="send" href="#" class=" ui-btn  ui-corner-all ui-btn-right" style="top: -0.4em">发送</a>

	</div><!-- /footer -->
</c:if>