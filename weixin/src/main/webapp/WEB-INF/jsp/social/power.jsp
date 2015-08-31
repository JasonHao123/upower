<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page pageEncoding="UTF-8"%>

<div role="main" class="ui-content  jqm-content">
	<spring:hasBindErrors name="powerForm">
		<p style="color: red;">
			<b><spring:message code="page.label.fixerror2"
					text="请修正后，再提交" /></b>
		</p>
	</spring:hasBindErrors>
	<form data-ajax="false" method="post">
		<h3>查询条件</h3>
		
		<label for="slider-fill">关系范围<a href="#popupInfo" data-rel="popup" data-transition="pop" class="my-tooltip-btn ui-btn ui-alt-icon ui-nodisc-icon ui-btn-inline ui-icon-info ui-btn-icon-notext" title="Learn more">Learn more</a>:</label> 
		 <spring:bind path="powerForm.distance">
		 <input type="range" name="distance"
			id="slider-fill" value="<c:out value="${status.value}" />" min="1" max="7" step="1"
			data-highlight="true">
		<font color="red"><c:out value="${status.errorMessage}"/></font>
						</spring:bind>
		<label for="title">分析类别<a href="#popupInfo2" data-rel="popup" data-transition="pop" class="my-tooltip-btn ui-btn ui-alt-icon ui-nodisc-icon ui-btn-inline ui-icon-info ui-btn-icon-notext" title="Learn more">Learn more</a>:</label>
		 <spring:bind path="powerForm.type">
		 <select required="true"
			id="category1" name="type">
			<option value="" data-placeholder="true">请选择...</option>
			<option value="SEX">性别年龄</option>
			<option value="LOCATION">地区</option>
			<option value="PROFESSION">职业</option>
			<option value="SOCIAL">关系网络</option>
			<option value="NETWORK">网络结构</option>
	<!--	<option value="PROFESSION">基本信息</option>
	 		<option value="SOCIAL">关系网络</option>
			<option value="SOCIAL">网络结构</option>
			 -->
		</select> 
<font color="red"><c:out value="${status.errorMessage}"/></font>
						</spring:bind>
		<div class="ui-grid-a">
			<div class="ui-block-a">
				<input type="submit" value="保存" data-theme="a">
			</div>
			<div class="ui-block-b">
				<input type="reset" value="重置" data-theme="a">
			</div>
		</div>
	</form>
	
		<div data-role="popup" id="popupInfo" class="ui-content" data-theme="a" style="max-width:350px;">
		  <p><strong>关系范围</strong>是指将要分析的关系层级，1级表示只分析自己的好友，2级将分析自己的朋友以及朋友的朋友，以此类推.</p>
		</div>
		<div data-role="popup" id="popupInfo2" class="ui-content" data-theme="a" style="max-width:350px;">
			<p>根据<strong>分析类别</strong>指定的类别，分析由<strong>关系范围</strong>定义的朋友圈中好友的相应信息进行统计</p>
		</div>
</div>