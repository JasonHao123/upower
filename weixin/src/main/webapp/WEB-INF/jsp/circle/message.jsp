<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page pageEncoding="UTF-8"%>
<link rel="stylesheet" href="<c:url value="/resources/css/redactor.css" />" />
<script src="<c:url value="/resources/js/redactor.js" />" type="text/javascript" charset="utf-8"></script>
<script src="<c:url value="/resources/js/imagemanager.js" />" type="text/javascript" charset="utf-8"></script>
<script src="<c:url value="/resources/js/filemanager.js" />" type="text/javascript" charset="utf-8"></script>
<script src="<c:url value="/resources/js/fontcolor.js" />" type="text/javascript" charset="utf-8"></script>
<script src="<c:url value="/resources/js/fontfamily.js" />" type="text/javascript" charset="utf-8"></script>
<script src="<c:url value="/resources/js/fontsize.js" />" type="text/javascript" charset="utf-8"></script>
<script src="<c:url value="/resources/js/fullscreen.js" />" type="text/javascript" charset="utf-8"></script>
<script src="<c:url value="/resources/js/video.js" />" type="text/javascript" charset="utf-8"></script>

<link rel="stylesheet" href="<c:url value="/resources/css/jquery.raty.css" />">
<script src="<c:url value="/resources/js/jquery.raty.js" />"></script>
  <script type="text/javascript">
  var busy = false;
  var pageNo = 0;
  function getData() {
  	if(pageNo==0) {
  		$("#messages").empty();
  	}
  	$.getJSON("<c:url value="/circle/comments.do" ><c:param name="id" value="${message.id}" /></c:url>&page="+pageNo,function(result){
  	    if(result.length>0) {
  		    $.each(result, function(i, message){
	           var msg ='<p><span><a data-ajax="false" href="<c:url value="/social/home.do?id=" />'+message.author.id+'">'+message.author.nickname+'</a>'
	           +'</span>（<span class="score'+pageNo+'" rating="'+message.distance.rating+'" >社交距离：'+message.distance.distance+'&nbsp;&nbsp;</span>）<span style="float: right;">'+message.createDate+'</span></p>'
	   +message.content
	   +'<div class="ui-grid-a">'
	   +'<div class="ui-block-a">'
	   +'		<a data-ajax="false" class="ui-btn ui-shadow ui-corner-all agree'+pageNo+'" comment="'+message.id+'">赞</a>'
	   +'	</div>'
	   +'	<div class="ui-block-b">'
	   +'		<a data-ajax="false" class=" ui-btn ui-shadow ui-corner-all reply'+pageNo+'" author="'+message.author.nickname+'" comment="'+message.id+'">回复</a>'
	   +'	</div>'
	   +'</div> '
	   +'<hr>';
  		    	
  			      $("#messages").append(msg);
  			    });
  			$(".score"+pageNo).each(function(a,b){
  	    		$(b).raty({		  
  			    	  half     : true,
  			    	  score: $(b).attr("rating"),
  			    	  readOnly:true
  			    	});
  	    	});
  			
  			$(".agree"+pageNo).each(function(a,b){
  	    		$(b).click(function() {
  	    			alert($(b).attr("comment"))
  	    		});
  	    	});
  			
  			$(".reply"+pageNo).each(function(a,b){
  				$(b).click(function() {
  					$("#commentTitle").html("回复"+$(b).attr("author"))
  	    			$("#comment").css("display","block");
  					$("#comment")[0].scrollIntoView(true);
					$("#commentScore").val($(b).attr("comment"));
  	    		});
  	    	});
  			pageNo = pageNo + 1;
  	    }
  		busy = false;
  	  });
  }
  
  
		$( document ).on( "pagecreate", "#myPage", function() {
				$("#reply").click(function() {
  					$("#commentTitle").html("回复${message.author.nickname}")
  	    			$("#comment").css("display","block");
  					$("#comment")[0].scrollIntoView(true);
					$("#commentScore").val("");
  	    		});
	        $('#messageContent').redactor({
	        	imageManagerJson: '<c:url value="/rest/images.do" />',
	    		imageUpload: '<c:url value="/rest/upload.do" />',
	    		focus: true,
                fileUpload: '<c:url value="/rest/uploadFile.do" />',
                fileManagerJson: '<c:url value="/rest/files.do" />',
                videoManagerJson: '<c:url value="/rest/videos.do" />',
                plugins: ['imagemanager','filemanager','fontcolor','fontsize','fontfamily','fullscreen','video']
	    	});
	        
    		$("#score").raty({		  
		    	  half     : true,
		    	  score: ${message.socialDistance.rating},
		    	  readOnly:true
		    	});
	    	$("#loadMore").click(function() {
	    		getData();
	    	});
	    	
	    	$("#send").click(function() {
	    		$.post( "<c:url value="/circle/comment.do" />", { id: ${message.id}, comment: $("#commentScore").val(),message:$("#messageContent").val() })
	    		  .done(function( message ) {
	    			  
	   	           var msg ='<p><span><a data-ajax="false" href="<c:url value="/social/home.do?id=" />'+message.author.id+'">'+message.author.nickname+'</a>'
		           +'</span>（<span class="score_'+message.id+'" rating="'+message.distance.rating+'" >社交距离：'+message.distance.distance+'&nbsp;&nbsp;</span>）<span style="float: right;">'+message.createDate+'</span></p>'
		   +message.content
		   +'<div class="ui-grid-a">'
		   +'<div class="ui-block-a">'
		   +'		<a data-ajax="false" class="ui-btn ui-shadow ui-corner-all btn'+message.id+'" >赞</a>'
		   +'	</div>'
		   +'	<div class="ui-block-b">'
		   +'		<a data-ajax="false" class=" ui-btn ui-shadow ui-corner-all btn'+message.id+'" >回复</a>'
		   +'	</div>'
		   +'</div> '
		   +'<hr>';
	  		    	$(".btn"+message.id).each(function() {
	  		    		$(this).button({
	  		    		  disabled: true
	  		    		});
	  		    	});
	  			      $("#messages").prepend(msg);
	  	
	  			$(".score_"+message.id).each(function(a,b){
	  	    		$(b).raty({		  
	  			    	  half     : true,
	  			    	  score: $(b).attr("rating"),
	  			    	  readOnly:true
	  			    	});
	  	    	});

	    				$("#messageContent").val("")
	    				$("#commentScore").val("")
	    				$("#comment").css("display","none");
	    		  });
	    	});
	    	$("#cancel").click(function() {
				$("#messageContent").val("")
				$("#commentScore").val("")
				$("#comment").css("display","none");
	    	});
	    	$(document).on("scrollstop",function(){
	    		//Check the user is at the bottom of the element
	    		if($(window).scrollTop() + $(window).height() >= $(this).height() && !busy) {
	    		 
	    		    // Now we are working, so busy is true
	    		    busy = true;
	    		    setTimeout(function() {
	    		        // This is the Ajax function                    
	    		  //      getData();
	    		 
	    		    }, 500);
	    		 
	    		}
	    		});
	    	getData();
			
		});
		
</script>

<div role="main" class="ui-content  jqm-content">
<h2 align="center">${message.title }</h2>
<p><span>${message.category.name}</span> <span style="float: right;">${message.lastUpdate }</span></p>
<p><span><a data-ajax="false" href="<c:url value="/social/home.do"><c:param name="id" value="${message.author.id}" /></c:url>">${message.author.nickname}</a></span>（<span  id="score" rating="${message.socialDistance.rating}" >社交距离：${message.socialDistance.distance}&nbsp;&nbsp;</span>）</p>
<hr>
${message.content }	
    <div class="ui-grid-a">
		<div class="ui-block-a">
			<button data-ajax="false" id="save" >赞</button>
		</div>
		<div class="ui-block-b">
			<button data-ajax="false" id="reply">回复</button>
		</div>
	</div> 					
<hr>
	<div id="messages">

</div>

    <button id="loadMore">加载更多...</button>  

</div>

<div id="comment" data-role="footer" data-position="fixed" data-tap-toggle="false" style="display:none" >
	 <h3 id="commentTitle"></h3>
	 <input type="hidden" id="commentScore">
 <textarea id="messageContent" name="content" rows="5" cols="20"><c:out value="${status.value}" /></textarea>
    		<a id="cancel" href="#" class=" ui-btn  ui-corner-all ui-btn-left" style="top: -0.4em">取消</a>
 
   		<a id="send" href="#" class=" ui-btn  ui-corner-all ui-btn-right" style="top: -0.4em">发送</a>

	</div><!-- /footer -->
