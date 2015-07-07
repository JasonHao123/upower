<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page pageEncoding="UTF-8"%>

<div role="main" class="ui-content jqm-content">
	<spring:hasBindErrors name="form">
		<p>
			<b><spring:message code="page.label.fixerror"
					text="Please fix all errors!" /></b>
		</p>
	</spring:hasBindErrors>
	<form id="myForm" method="POST" data-ajax="false">
		<input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
			value="<c:out value="${_csrf.token}"/>" /> <label
			for="signupUsername"><spring:message
				code="page.label.login.username" text="Username" /></label>

		<spring:bind path="form.name">
			<input id="signupUsername" type="text" name="name"
				placeholder="<spring:message code="page.label.login.username" text="Username" />"
				required value="<c:out value="${status.value}" />">
			<a href="#" class="icon ticker"></a>
			<font color="red"><c:out value="${status.errorMessage}" /></font>
		</spring:bind>

		<div class="ui-grid-b ui-responsive">
			<div class="ui-block-a">
				<input type="submit"
					value="<spring:message
			code="page.label.signup.submit" />">
			</div>
			<div class="ui-block-b">
				<input type="reset"
					value="<spring:message
			code="page.label.signup.reset" />" />
			</div>

		</div>
	</form>
</div>
