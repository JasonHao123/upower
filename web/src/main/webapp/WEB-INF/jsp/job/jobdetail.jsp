<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>
<style>
<!--

-->
</style>
<script type="text/javascript">
<!--
$( document ).on( "pageinit", function(event) {
	$("#apply").click(function() {
		// alert("add to bookmark");
							$.ajax({
						url: "<c:url value="/user/job/apply.do" />",
						dataType: "text",

						data: {
							id: ${id}
						},
						success : function(data) {
							alert("add to bookmark");							
						}
					});
		
	});
	$("#bookmark").click(function() {
		// alert("add to bookmark");
							$.ajax({
						url: "<c:url value="/user/job/bookmark.do" />",
						dataType: "text",

						data: {
							id: ${id}
						},
						success : function(data) {
							alert("add to bookmark");							
						}
					});
		
	});
	
});
$( document ).on( "scrollstop", function(event) {
//	movePanel($(this).scrollTop()+$(window).height);
	
});
function movePanel(position) {
	$( "#popupUndismissible" ).popup( "open");
	$( "#popupUndismissible" ).popup( "reposition", {x:100,y:position});
}
//-->
</script>
<link rel="stylesheet" href="<c:url value="/resources/css/jobdetail.css" />">
	<div role="main" class="ui-content jqm-content">
<div data-role="popup" id="popupUndismissible" class="ui-content" style="max-width:280px" data-dismissible="false">

</div>
        <h1>${job.title}</h1>

        <p>It's easy to extend the basic grid styles into a custom responsive layout by using CSS media queries to adjust the layout and design across various screen width breakpoints.</p>


        ${job.description}

        <!-- view source utilty wrapper -->
        <div >

            <div class="rwd-example">

                <!-- Lead story block -->
                <div class="ui-block-a">
                    <div class="ui-body ui-body-d">
                        <h2>Apple schedules 'iPad Mini' event for October 23</h2>
                        <p>One of the worst-kept secrets in tech has been confirmed: Apple will hold an event October 23 in San Jose, California, at which the company is widely expected to unveil a smaller, cheaper version of its popular iPad called "Mini".</p>
                    </div>
                </div>

                <!-- secondary story block #1 -->
                <div class="ui-block-b">
                    <div class="ui-body ui-body-d">
                        <h4>Microsoft Surface tablet goes on sale for $499</h4>
                        <p>The Microsoft Surface tablet picture has come into focus. The Redmond giant filled in the blanks on the new tablet's availability and specs.</p>
                    </div>
                </div>

                <!-- secondary story block #2 -->
                <div class="ui-block-c">
                    <div class="ui-body ui-body-d">
                        <h4>AOL unveils Alto, an email service that syncs 5 accounts</h4>
                        <p>AOL, struggling to shed its outdated image, is reimagining one of the most visibly aging parts of its platform: Its email service. </p>
                    </div>
                </div>

            </div><!-- /rwd-example -->
	<div class="ui-grid-b ui-responsive">
		<div class="ui-block-a">
			<a href="#" id="apply"  >Apply</a>
		</div>
		<div class="ui-block-b">
			<a href="#" id="bookmark"  >Bookmark</a>
		</div>

	</div>
        </div><!-- /data-demo -->

</div>