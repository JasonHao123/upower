<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>
    <script>
		$( document ).on( "pagecreate", "#myPage", function() {

			$("#category1").change(function(evt) {
				var companyId = this.value;
				$("#category2").empty();
				$("<option value=''></option>").appendTo("#category2");
				if(companyId!="") {

					$.ajax({
						url: "<c:url value="/rest/category/list.do" />",
						dataType: "json",

						data: {
							parent: companyId
						},
						success : function(data) {
							$(data).each(function(index,item) {
								$("<option value='"+item.id+"'>"+item.name+"</option>").appendTo("#category2");
							});
							
						}
					});
				}
				$('#category2').selectmenu('refresh',true);
			});
			
			$("#company").change(function(evt) {
				var companyId = this.value;
				$("#department").empty();
				$("<option value=''></option>").appendTo("#department");
				if(companyId!="") {

					$.ajax({
						url: "<c:url value="/rest/department/list.do" />",
						dataType: "json",

						data: {
							companyId: companyId
						},
						success : function(data) {
							$(data).each(function(index,item) {
								$("<option value='"+item.id+"'>"+item.name+"</option>").appendTo("#department");
							});
							
						}
					});
				}
				$('#department').selectmenu('refresh',true);
			});
	        $('#content3').redactor({
	    		imageGetJson: '<c:url value="/spring/files/listImages" />',
	    		imageUpload: '<c:url value="/spring/files/uploadImage" />',
	    		clipboardUploadUrl: '/webUpload/redactor/clipboardUpload/',
	    		fileUpload: '<c:url value="/spring/files/fileUpload" />'
	    	});
	        
            $('#locations').tagit({
        			autocomplete : {
        				source : function(request, response) {
        					$.ajax({
        						url: "<c:url value="/rest/location/search.do" />",
        						dataType: "json",
        						crossDomain: true,
        						data: {
        							prefix: request.term
        						},
        						success : function(data) {
        							response($.map(data, function(item) {
        								return {
        									label : item.name,
        									value : item.name
        								}
        							}));
        						}
        					});
        				},
        				delay : 1000,
        				minLength : 2
        			},
        			allowSpaces : true,
        			fieldName: "location"
        		});
            
            $('#features').tagit({
    			autocomplete : {
    				source : function(request, response) {
    					$.ajax({
    						url: "http://gd.geobytes.com/AutoCompleteCity",
    						dataType: "jsonp",
    						crossDomain: true,
    						data: {
    							q: request.term
    						},
    						success : function(data) {
    							response($.map(data, function(item) {
    								return {
    									label : item,
    									value : item
    								}
    							}));
    						}
    					});
    				},
    				delay : 1000,
    				minLength : 2
    			},
    			allowSpaces : true,
    			fieldName: "feature"
    		});
            
            $('#desiredSkills').tagit({
    			autocomplete : {
    				source : function(request, response) {
    					$.ajax({
    						url: "<c:url value="/rest/skill/search.do" />",
    						dataType: "json",
    						data: {
    							prefix: request.term
    						},
    						success : function(data) {
    							response($.map(data, function(item) {
    								return {
    									label : item.name,
    									value : item.name
    								}
    							}));
    						}
    					});
    				},
    				delay : 1000,
    				minLength : 2
    			},
    			allowSpaces : true,
    			fieldName: "desiredSkill"
    		});
            
            $('#requiredSkills').tagit({
    			autocomplete : {
    				source : function(request, response) {
    					$.ajax({
    						url: "<c:url value="/rest/skill/search.do" />",
    						dataType: "json",
    						data: {
    							prefix: request.term
    						},
    						success : function(data) {
    							response($.map(data, function(item) {
    								return {
    									label : item.name,
    									value : item.name
    								}
    							}));
    						}
    					});
    				},
    				delay : 1000,
    				minLength : 2
    			},
    			allowSpaces : true,
    			fieldName: "requiredSkill"
    		});
		});
    </script>
	<style>
		.ui-filter-inset {
			margin-top: 0;
		}
    </style>
	<div role="main" class="ui-content  jqm-content">
  <spring:hasBindErrors name="User">
 <p>   <b><spring:message code="page.label.fixerror" text="Please fix all errors!" /></b></p>
  </spring:hasBindErrors>
  	<form data-ajax="false" method="post">	
	<h3>Basic Info</h3>
		<label for="title">Company:<span style="float:right"><a href="#" >Add</a></span></label>
		<select id="company" name="company">
			<option></option>
			<c:forEach items="${companies}" var="company">
			<option value="${company.id}">${company.name}</option>
			</c:forEach>
		</select>
		<label for="title">Department/Division:</label>
		<select id="department" name="department">
		</select>
		<label for="title">Category 1:</label>
		<select id="category1" name="category1">
			<option></option>
			<c:forEach items="${categories}" var="category">
			<option value="${category.id}">${category.name}</option>
			</c:forEach>
		</select>
		<label for="title">Category 2:</label>
		<select id="category2" name="category2">
			<option></option>
		</select>
				<label for="title">工作性质</label>
		<select name="workType">
			<option></option>
			<c:forEach items="${jobTypes}" var="jobType">
			<option value="${jobType.id}">${jobType.name}</option>
			</c:forEach>
		</select>
		<label for="title">Title:</label>
		<input type="text" name="title">


		<label for="title">招聘人数:</label>
<input type="number" data-clear-btn="true" name="numberOfVacancy" pattern="[0-9]*" id="number-4" value="">
	
		<label for="locations">Location:</label>
<ul id="locations" class="ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset"></ul>
<label>Job description</label>
<textarea id="content3" name="description" rows="5" cols="20"></textarea>
<label>Features<span style="float:right"><a href="#" >Popular features</a></span></label>
<ul id="features" class="ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset"></ul>
<h3>Requirement</h3>
<label>学历</label>
<select>
	<option></option>
				<c:forEach items="${educationLevels}" var="jobType">
			<option value="${jobType.id}">${jobType.name}</option>
			</c:forEach>
</select>
		 <div data-role="rangeslider">
        <label for="range-1a">Working experience</label>
        <input type="range" name="minExperience" id="range-1a" min="0" max="35" value="0">
        <label for="range-1b">Rangeslider:</label>
        <input type="range" name="maxExperience" id="range-1b" min="0" max="35" value="35">
    </div>
<label>Required Skills</label>
<ul id="requiredSkills" class="ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset"></ul>

<label>Desired Skills</label>
<ul id="desiredSkills" class="ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset"></ul>

<h3>Benefit Info</h3>
	<div data-role="rangeslider">
        <label for="range-1a">Salary(K RMB):</label>
        <input type="range" name="minSalary" id="range-1a" min="0" max="100" value="0">
        <label for="range-1b">Rangeslider:</label>
        <input type="range" name="maxSalary" id="range-1b" min="0" max="100" value="100">
    </div>
	<label>基本工资</label>
	<input type="number" data-clear-btn="true" pattern="[0-9]*"  value="12">
	<div class="ui-field-contain">
	 <label for="range-1a">奖金</label>
<input type="checkbox">
</div>
 <label for="range-1a">年假天数</label>
<input type="number" data-clear-btn="true" pattern="[0-9]*"  value="5">
 <label for="range-1a">基本保险</label>
<select>
	<option></option>
	<option>五险一金</option>
</select>
<div class="ui-field-contain">
 <label for="range-1a">商业保险</label>
<input type="checkbox">
</div>
<div class="ui-field-contain">
 <label for="range-1a">企业年金</label>
<input type="checkbox">
</div>
<div class="ui-grid-a">
<div class="ui-block-a"><input type="submit" value="Submit" data-theme="a"></div>
<div class="ui-block-b"><input type="reset" value="Reset" data-theme="b"></div>
</div>
</form>
	</div>