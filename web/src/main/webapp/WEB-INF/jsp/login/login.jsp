<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page pageEncoding="UTF-8"%>

<div role="main">
<p>
	<c:if test="${not empty param.login_error}">
		<font color="red"> Your login attempt was not successful, try
			again.<br /> <br /> Reason: <c:out
				value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />.
		</font>
	</c:if>
</p>

<form id="myForm" action="<c:url value='/j_spring_security_check'/>" method="POST" data-ajax="false">
	<input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
		value="<c:out value="${_csrf.token}"/>" /> 

                <label for="name"><spring:message
			code="page.label.login.username" text="Username" /></label>
            
                <input type="text" id="name" name="j_username" required="true" data-dojo-type="dijit/form/ValidationTextBox"/>
            
                <label for="dob"><spring:message
			code="page.label.login.password" text="Password" /></label>
           
                <input type="password" id="pwd" name="j_password" data-dojo-type="dijit/form/ValidationTextBox"/>
 
                <label for="rem"><spring:message
			code="page.label.login.remember" text="Keep me logged in" /></label>
 
                <input type="checkbox" id="rememberMe" name="j_spring_security_remember_me" data-dojo-type="dijit/form/CheckBox"/>
          
	<div class="ui-grid-a ui-responsive">
		<div class="ui-block-a">
			<input type="submit" value="Login">
		</div>
		<div class="ui-block-b">
			<a data-role="button" href="<c:url value="/signupuser.do" />"><spring:message
			code="page.label.login.signup" text="Signup" /></a>
		</div>
		<div class="ui-block-a">
	<a href="<c:url value="/resetpassword.do" />"><spring:message
			code="page.label.login.reset" text="Forgot password?" /></a>
		</div>
	</div>
	</form>
</div>
