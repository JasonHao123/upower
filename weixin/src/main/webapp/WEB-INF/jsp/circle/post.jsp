<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>
<link rel="stylesheet" href="<c:url value="/resources/css/redactor.css" />" />
<script src="<c:url value="/resources/js/redactor.js" />" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="<c:url value="/resources/css/jquery.raty.css" />">
<script src="<c:url value="/resources/js/jquery.raty.js" />"></script>
  <script>
		$( document ).on( "pagecreate", "#myPage", function() {
	        $('#message').redactor({
	    		imageGetJson: '<c:url value="/rest/images.do" />',
	    		imageUpload: '<c:url value="/rest/upload.do" />',
	    		fileUpload: '<c:url value="/rest/uploadFile.do" />'
	    	});
	        
	        $('#targetScore').raty({
	      	  half     : true,
	      	  score: 0
	      	});
			
		});
		
</script>
		<div role="main" class="ui-content jqm-content">
		<form id="myForm"  method="POST" data-ajax="false">
		<label>Title:</label>
		<input name="title">

<textarea id="message" name="message" rows="5" cols="20"></textarea>
<div data-role="collapsible">
    <h4>推送范围设置</h4>
<fieldset data-role="controlgroup" data-type="horizontal" data-mini="true">
    <legend>性别:</legend>
        <input type="radio" name="radio-choice-b" id="radio-choice-c" value="list" checked="checked">
        <label for="radio-choice-c">不限</label>
        <input type="radio" name="radio-choice-b" id="radio-choice-d" value="grid">
        <label for="radio-choice-d">男</label>
        <input type="radio" name="radio-choice-b" id="radio-choice-e" value="gallery">
        <label for="radio-choice-e">女</label>
</fieldset>
    <div data-role="rangeslider">
        <label for="range-1a">年龄（0-100 表示不限）:</label>
        <input type="range" name="range-1a" id="range-1a" min="0" max="100" value="0">
        <label for="range-1b">Rangeslider:</label>
        <input type="range" name="range-1b" id="range-1b" min="0" max="100" value="100">
    </div>

<label for="slider-fill">社交距离（0：表示不限）:</label>
<input type="range" name="slider-fill" id="slider-fill" value="0" min="0" max="7" step="1" data-highlight="true">
<label for="slider-fill">友好度:</label>
<div id="targetScore"></div>

</div>
	<div class="ui-grid-a">
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