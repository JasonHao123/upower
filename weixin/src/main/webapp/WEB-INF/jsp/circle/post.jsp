<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>
<link rel="stylesheet" href="<c:url value="/resources/css/redactor.css" />" />
<script src="<c:url value="/resources/js/redactor.js" />" type="text/javascript" charset="utf-8"></script>
<script src="<c:url value="/resources/js/imagemanager.js" />" type="text/javascript" charset="utf-8"></script>
<script src="<c:url value="/resources/js/filemanager.js" />" type="text/javascript" charset="utf-8"></script>
<script src="<c:url value="/resources/js/fontcolor.js" />" type="text/javascript" charset="utf-8"></script>
<script src="<c:url value="/resources/js/fontfamily.js" />" type="text/javascript" charset="utf-8"></script>
<script src="<c:url value="/resources/js/fontsize.js" />" type="text/javascript" charset="utf-8"></script>
<script src="<c:url value="/resources/js/fullscreen.js" />" type="text/javascript" charset="utf-8"></script>

<link rel="stylesheet" href="<c:url value="/resources/css/jquery.raty.css" />">
<script src="<c:url value="/resources/js/jquery.raty.js" />"></script>
  <script>
		$( document ).on( "pagecreate", "#myPage", function() {
	        $('#message').redactor({
	        	imageManagerJson: '<c:url value="/rest/images.do" />',
	    		imageUpload: '<c:url value="/rest/upload.do" />',
	    		focus: true,
                fileUpload: '<c:url value="/rest/uploadFile.do" />',
                fileManagerJson: '<c:url value="/rest/files.do" />',
                plugins: ['imagemanager','filemanager','fontcolor','fontsize','fontfamily','fullscreen']
	    	});
	        
	        $('#targetScore').raty({
	      	  half     : true,
	      	  score: ${postMessageForm.rating},
	    	  targetScore: '#rating',
	      	});
	        
	        $("#save").click(function() {
	        	if($("#status").val()=="") {
	        		$("#status").val("DRAFT");
	        	}
	        	$("#postMessageForm").submit();
	        });
	        $("#publish").click(function() {
	        	$("#status").val("PUBLISHED");
	        	$("#postMessageForm").submit();
	        });
			
		});
		
</script>
		<div role="main" class="ui-content jqm-content">
			<spring:hasBindErrors name="postMessageForm">
		<p>
			<b><spring:message code="page.label.fixerror"
					text="Please fix all errors!" /></b>
		</p>
	</spring:hasBindErrors>
		<form id="postMessageForm"  method="POST" data-ajax="false">
		<input type="hidden" name="id" value="${postMessageForm.id}">
		<input id="status" type="hidden" name="status" >		
		<label>标题:</label>
		<spring:bind path="postMessageForm.title">
			<input type="text" name="title" value="<c:out value="${status.value}" />">
			<font color="red"><c:out value="${status.errorMessage}" /></font>
		</spring:bind>
				<label>分类:</label>
		<select name="category">
		<c:forEach items="${categories}" var="category">
			<option value="${category.id}" <c:if test="${postMessageForm.category == category.id}">selected</c:if> >${category.name}</option>
			</c:forEach>
		</select>
		<spring:bind path="postMessageForm.content">
			<textarea id="message" name="content" rows="5" cols="20"><c:out value="${status.value}" /></textarea>
			<font color="red"><c:out value="${status.errorMessage}" /></font>
		</spring:bind>

<div data-role="collapsible">
    <h4>对象群体</h4>
<fieldset data-role="controlgroup" data-type="horizontal" data-mini="true">
    <legend>性别:</legend>
        <input type="radio" name="sex" id="all" value="" <c:if test="${empty postMessageForm.sex}">checked="checked" </c:if>>
        <label for="all">不限</label>
        <input type="radio" name="sex" id="male" value="1" <c:if test="${1== postMessageForm.sex}">checked="checked" </c:if>>
        <label for="male">男</label>
        <input type="radio" name="sex" id="female" value="2" <c:if test="${2== postMessageForm.sex}">checked="checked" </c:if>>
        <label for="female">女</label>
</fieldset>
    <div data-role="rangeslider">
        <label for="range-1a">年龄（0-100 表示不限）:</label>
        <input type="range" name="minAge" id="range-1a" min="0" max="100" value="${ postMessageForm.minAge}">
        <label for="range-1b">Rangeslider:</label>
        <input type="range" name="maxAge" id="range-1b" min="0" max="100" value="${ postMessageForm.maxAge}">
    </div>

<label for="slider-fill">社交距离（0：表示不限）:</label>
<input type="range" name="distance" id="slider-fill" value="${ postMessageForm.distance}" min="0" max="7" step="1" data-highlight="true">
<label for="slider-fill">友好度:</label>
<div id="targetScore"></div>
<input name="rating" id="rating" type="hidden">
</form>
</div>
	<div class="ui-grid-a">
		<div class="ui-block-a">
			<button data-ajax="false" id="save" <c:if test="${postMessageForm.status =='PUBLISHED'}">disabled="disabled"</c:if>>保存</button>
		</div>
		<div class="ui-block-b">
			<button data-ajax="false" id="publish">发布</button>
		</div>

	</div>

    </div><!-- /content -->