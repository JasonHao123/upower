
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
	$.getJSON("<c:url value="/circle/messages.do" />?page="+pageNo,function(result){
	    if(result.length>0) {
		    $.each(result, function(i, message){
		    	var status = "";
		    	if(message.status=="DRAFT") {
		    		status = "已保存";
		    	}else if(message.status=="PUBLISHED") {
		    		status = "已发布";
		    	}
			      $("#messages").append("<li><a data-ajax='false' href='<c:url value="/circle/post.do" />?id="+message.id+"' ><h2>"+message.category.name +"</h2>   <p style='white-space:pre-wrap;'>"+message.title+"</p> <p class=\"ui-li-aside\">"+status +"</p> </a></li>");
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


		<!-- TODO: This should become an external panel so we can add input to markup (unique ID) -->
		<div data-role="panel" class="jqm-search-panel" data-position="right" id="right"
			data-display="overlay" data-theme="a">
        <ul data-role="listview" data-inset="true" id="messages">
        	<li><a href="#">
            	<h2>iOS 6.1</h2>
                <p>Apple released iOS 6.1</p>
                <p class="ui-li-aside">Apple</p>
            </a></li>

        </ul>

		</div>
		<!-- /panel -->