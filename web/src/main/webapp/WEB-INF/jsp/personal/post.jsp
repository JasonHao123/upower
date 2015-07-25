<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>
<link rel="stylesheet" href="<c:url value="/resources/css/redactor.css" />" />
<script src="<c:url value="/resources/js/redactor.js" />" type="text/javascript" charset="utf-8"></script>
  <script>
		$( document ).on( "pagecreate", "#myPage", function() {
	        $('#message').redactor({
	    		imageGetJson: '<c:url value="/spring/files/listImages" />',
	    		imageUpload: '<c:url value="/spring/files/uploadImage" />',
	    		clipboardUploadUrl: '/webUpload/redactor/clipboardUpload/',
	    		fileUpload: '<c:url value="/spring/files/fileUpload" />'
	    	});
			
		});
		
</script>
		<div role="main" class="ui-content jqm-content">
		<form id="myForm"  method="POST" data-ajax="false">
		<label>Title:</label>
		<input name="title">
		<label>Type:</label>
		<select name="type">
		<c:forEach items="${categories}" var="category">
			<option value="${category.id}">${category.name}</option>
			</c:forEach>
		</select>
<textarea id="message" name="message" rows="5" cols="20"></textarea>
	<div class="ui-grid-a ui-responsive">
		<div class="ui-block-a">
			<input type="submit" value="<spring:message
			code="page.label.signup.submit" />">
		</div>
		<div class="ui-block-b">
			<input type="reset" value="<spring:message
			code="page.label.signup.reset" />" />
		</div>

	</div>
</form>
    </div><!-- /content -->