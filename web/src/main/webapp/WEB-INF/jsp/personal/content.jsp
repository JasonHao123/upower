<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
	$.getJSON("<c:url value="/personal/messages.do"><c:param name="category" value="${cate}" /></c:url>&page="+pageNo,function(result){
	    if(result.length>0) {
		    $.each(result, function(i, message){
			      $("#messages").append("<li><a data-ajax='false' href='<c:url value="/personal/message.do" />?id<c:if test="${cate==-1}">2</c:if>="+message.id+"'><h3>"+message.author.nickname +"("+message.distance +"度)</h3><p><strong>"+message.title+"</strong></p><p>"+message.content+"</p><p class=\"ui-li-aside\">"+message.category.name +"</p></a></li>");

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
        </ul>
    </div><!-- /content -->