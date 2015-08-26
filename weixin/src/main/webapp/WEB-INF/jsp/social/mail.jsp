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
	$.getJSON("<c:url value="/user/circle2.do" />?page="+pageNo,function(result){
	    if(result.length>0) {
		    $.each(result, function(i, message){
			      $("#messages").append("<li><a href=\"index.html\"><h3>"+message.nickname+"</h3><p><strong>You've been invited to a meeting at Filament Group in Boston, MA</strong></p><p>Hey Stephen, if you're available at 10am tomorrow, we've got a meeting with the jQuery team.</p><p class=\"ui-li-aside\"><strong>6:24</strong>PM</p></a></li>");
			    });
		    $("#messages").listview("refresh");
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