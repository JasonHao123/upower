<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>

<script type="text/javascript">
var busy = false;
var pageNo = 0;
function getData() {
	if(pageNo==0) {
		$("#messages").empty();
	}
	$.getJSON("<c:url value="/social/conversation2.do" ><c:param name="id" value="${id}" /></c:url>&page="+pageNo,function(result){
	    if(result.length>0) {
		    $.each(result, function(i, message){
		    	var dataTheme = "data-theme=\"a\"";
		    	if(message.self) {
		    		dataTheme = "data-theme=\"c\"";
		    	}
			      $("#messages").append("<li "+dataTheme+" ><h3>"+message.from.nickname+"</h3><p style='white-space:pre-wrap;'>"+message.message+"</p><p class=\"ui-li-aside\"><strong>"+message.createDate+"</strong></p></li>");
			    });
		    $("#messages").listview("refresh");
			pageNo = pageNo + 1;
	    }
		busy = false;
	  });
}
$( document ).on( "pagecreate", "#myPage", function() {
	$("#send").click(function() {
		$.post( "<c:url value="/social/mail.do" />", { id: ${id},message:$("#messageContent").val() })
		  .done(function( message ) {
			  
		    	  var item = $("<li data-theme=\"c\"><h3>"+message.from.nickname+"</h3><p style='white-space:pre-wrap;'>"+message.message+"</p><p class=\"ui-li-aside\"><strong>"+message.createDate+"</strong></p></li>");		    	  
		    	  $(item).prependTo( $( "#messages" ) );
		    	  item[0].scrollIntoView(true);
	    	 	 $("#messages").listview("refresh");			    
				$("#messageContent").val("")
		  });
	});

	
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
});

</script>
	<div role="main" class="ui-content jqm-content">
        <ul data-role="listview" data-inset="true" id="messages">
        	<li><a href="#">
            	<img src="<c:url value="/resources/img/apple.png" />" class="ui-li-thumb">
            	<h2>iOS 6.1</h2>
                <p>Apple released iOS 6.1</p>
                <p class="ui-li-aside">Apple</p>
            </a></li>

        </ul>
        </div>
 <div data-role="footer" data-position="fixed" data-tap-toggle="false" >

  		<textarea id="messageContent" rows="1" cols="30" style="" ></textarea>

<a id="send" href="#" class=" ui-btn  ui-corner-all ui-btn-right" style="top: -0.4em">发送</a>
	</div><!-- /footer -->