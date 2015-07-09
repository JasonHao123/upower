<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page pageEncoding="UTF-8"%>
<script>
	$(document)
			.on(
					"pagecreate",
					"#myPage",
					function() {

						$("#category1")
								.change(
										function(evt) {
											var companyId = this.value;
											$("#category2").empty();
											$("<option value=''></option>")
													.appendTo("#category2");
											if (companyId != "") {

												$
														.ajax({
															url : "<c:url value="/rest/category/list.do" />",
															dataType : "json",

															data : {
																parent : companyId
															},
															success : function(
																	data) {
																$(data)
																		.each(
																				function(
																						index,
																						item) {
																					$(
																							"<option value='"+item.id+"'>"
																									+ item.name
																									+ "</option>")
																							.appendTo(
																									"#category2");
																				});

															}
														});
											}
											$('#category2').selectmenu(
													'refresh', true);
										});

						$('#locations')
								.tagit(
										{
											autocomplete : {
												source : function(request,
														response) {
													$
															.ajax({
																url : "<c:url value="/rest/location/search.do" />",
																dataType : "json",
																crossDomain : true,
																data : {
																	prefix : request.term
																},
																success : function(
																		data) {
																	response($
																			.map(
																					data,
																					function(
																							item) {
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
											fieldName : "location"
										});

						$('#hobbys')
								.tagit(
										{
											autocomplete : {
												source : function(request,
														response) {
													$
															.ajax({
																url : "<c:url value="/rest/hobby/search.do" />",
																dataType : "json",
																data : {
																	prefix : request.term
																},
																success : function(
																		data) {
																	response($
																			.map(
																					data,
																					function(
																							item) {
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
											fieldName : "hobby"
										});
					});
</script>
<style>
.ui-filter-inset {
	margin-top: 0;
}
</style>
<div role="main" class="ui-content  jqm-content">
	<spring:hasBindErrors name="profileForm">
		<p>
			<b><spring:message code="page.label.fixerror"
					text="Please fix all errors!" /></b>
		</p>
	</spring:hasBindErrors>
	<form data-ajax="false" method="post">
	<input type="hidden" name="id" value="${profileForm.id}">
		<h3>个人信息</h3>
		<label for="title">昵称:</label>
		<spring:bind path="profileForm.nickname">
			<input type="text" name="nickname" value="<c:out value="${status.value}" />">
			<font color="red"><c:out value="${status.errorMessage}" /></font>
		</spring:bind>
		<label for="slider-fill">年龄:</label> <input type="range" name="age"
			id="slider-fill" value="${profileForm.age }" min="0" max="100" step="1"
			data-highlight="true"> <label for="title">职业分类1:</label> <select
			id="category1" name="category1">
			<option></option>
			<c:forEach items="${categories}" var="category">
				<option value="${category.id}">${category.name}</option>
			</c:forEach>
		</select> <label for="title">职业分类:</label> <select id="category2"
			name="category2">
			<option></option>
		</select> <label for="locations">常出没城市:</label>
		<ul id="locations"
			class="ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset"></ul>

		<label>兴趣爱好</label>
		<ul id="hobbys"
			class="ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset"></ul>

		<div class="ui-grid-a">
			<div class="ui-block-a">
				<input type="submit" value="Submit" data-theme="a">
			</div>
			<div class="ui-block-b">
				<input type="reset" value="Reset" data-theme="b">
			</div>
		</div>
	</form>
</div>