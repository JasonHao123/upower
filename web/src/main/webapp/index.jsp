<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<style>
.ui-content {
	background:
		url('<c:url value="/resources/img/social/socialnetwork.jpg" />');
	background-size: 100% 100%;
	-moz-background-size: 100% 100%; /* 老版本的 Firefox */
	background-repeat: no-repeat;
}
</style>
<sec:authorize access="isAuthenticated()">
  <meta http-equiv="Refresh" content="0; URL=check.do">
</sec:authorize>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Welcome to This site</title>
<link rel="shortcut icon"
	href="<c:url value="/resources/favicon.ico" />">
<link rel="stylesheet"
	href="<c:url value="/resources/css/jquery.mobile-1.4.5.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/css/jqm-demos.css" />">

<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
<script src="<c:url value="/resources/js/jquery.js" />"></script>
<script src="<c:url value="/resources/js/index.js" />"></script>
<script src="<c:url value="/resources/js/jquery.mobile-1.4.5.min.js" />"></script>

</head>
<body>
	<div data-role="page" id="myPage" class="jqm-demos jqm-home">

		<div data-role="header" class="jqm-header">
		<h2><a href="../" title="jQuery Mobile Demos home"><img src="<c:url value="/resources/img/jquery-logo.png" />" alt="jQuery Mobile"></a></h2>
<p><span class="jqm-version"></span> Demos</p>		
  		<a href="#" class="jqm-navmenu-link ui-btn ui-btn-icon-left ui-corner-all ui-icon-user ui-nodisc-icon ui-alt-icon ui-btn-left" >Login</a>
   		<a href="#" class="jqm-search-link ui-btn ui-btn-icon-left ui-corner-all ui-icon-action ui-nodisc-icon ui-alt-icon ui-btn-right">Signup</a>

		</div>
		<div role="main" class="ui-content jqm-content" style="height: 500px">
		</div>
	    <div data-role="panel" class="jqm-navmenu-panel" data-position="left" data-display="overlay" data-theme="a">

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
		</div><!-- /panel -->
		<div data-role="footer" data-position="fixed" data-tap-toggle="false"
			class="jqm-footer">
			<p>
				jQuery Mobile Demos version <span class="jqm-version"></span>
			</p>
			<p>Copyright 2014 The jQuery Foundation</p>
		</div>
		<!-- /footer -->

		<!-- TODO: This should become an external panel so we can add input to markup (unique ID) -->
		<div data-role="panel" class="jqm-search-panel" data-position="right" id="right"
			data-display="overlay" data-theme="a">
<form id="myForm" action="<c:url value="/signupuser.do" />"  method="POST" data-ajax="false">
	<input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
		value="<c:out value="${_csrf.token}"/>" /> 

	<label for="signupUsername"><spring:message code="page.label.login.username" text="Username" /></label> 

					
				          <input id="signupUsername" type="text" name="username"   placeholder="<spring:message code="page.label.login.username" text="Username" />" required value="<c:out value="${status.value}" />">
			

							<label for="signupPwd"> <spring:message
			code="page.label.login.password" text="Password" />
	</label>

				          <input type="password" name="password" id="signupPwd"  placeholder="Password" required >

							<label for="signupPwdAgain"> <spring:message code="page.label.login.passwordAgain" text="Password Again" />
	</label> 

				          <input type="password" name="passwordAgain" id="signupPwdAgain"   placeholder="<spring:message code="page.label.login.passwordAgain" text="Password Again" />" required >
			
  
	<div class="ui-grid-a ui-responsive">
		<div class="ui-block-a">
			<input type="submit" value="<spring:message
			code="page.label.signup.submit" text="submit" />">
		</div>
		<div class="ui-block-b">
			<input type="reset" value="<spring:message
			code="page.label.signup.reset" text="reset" />" />
		</div>

	</div>
	</form>
		</div>
		<!-- /panel -->
	</div>
	<!-- /page -->

</body>
</html>