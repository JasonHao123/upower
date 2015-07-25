<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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

<h2>${message.id}-${message.title}</h2>

<h3>${message.author.nickname}(${message.distance})</h3>
<p>${message.content}</p>
<form>
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
</div>