<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>
	<div role="main" class="ui-content  jqm-content">
	<h3>今日求职指数：80</h3>
	<form action="<c:url value="/job/search.do" />">

<select id="city" multiple="multiple" data-native-menu="false" >
<option value="choose-one" data-placeholder="true">城市</option>
<c:forEach items="${cities}" var="category">
<c:choose>
	<c:when test="${category.leaf or category.subType ==2}">
		<option value="${category.id}">${category.name}</option>	
	</c:when>
	<c:otherwise>
	<optgroup label="${category.name}" >
		<c:forEach items="${category.children}" var="subCategory">
		<option value="${subCategory.id}">${subCategory.name}</option>
	</c:forEach>
</optgroup>

	</c:otherwise>
</c:choose>
</c:forEach>
</select>
<input type="search" name="q">
<fieldset data-role="collapsible">
<legend>高级搜索</legend>
<label for="select-custom-19">职业:</label>
<select name="select-custom-19" id="select-custom-19" multiple="multiple" data-native-menu="false">
<option value="choose-one" data-placeholder="true">Choose one...</option>
<c:forEach items="${categories}" var="category">
<optgroup label="${category.name}">
<c:forEach items="${category.children}" var="subCategory">
<option value="${subCategory.id}">${subCategory.name}</option>
</c:forEach>
</optgroup>
			</c:forEach>
</select>

<label for="select-custom-20">行业:</label>
<select name="select-custom-20" id="select-custom-20" multiple="multiple" data-native-menu="false">
<option value="choose-one" data-placeholder="true">Choose one...</option>
<c:forEach items="${industries}" var="category">
<optgroup label="${category.name}">
<c:forEach items="${category.children}" var="subCategory">
<option value="${subCategory.id}">${subCategory.name}</option>
</c:forEach>
</optgroup>
			</c:forEach>
</select>

				<label for="title">工作性质</label>
		<select name="workType" multiple="multiple" data-native-menu="false">
			<option></option>
			<c:forEach items="${jobTypes}" var="jobType">
			<option value="${jobType.id}">${jobType.name}</option>
			</c:forEach>
		</select>
		
		<label for="title">工作经验</label>
		<select name="experience" multiple="multiple" data-native-menu="false">
			<option></option>
			<c:forEach items="${experiences}" var="jobType">
			<option value="${jobType.id}">${jobType.name}</option>
			</c:forEach>
		</select>
		
		<label for="title">学历</label>
		<select name="educationLevel" multiple="multiple" data-native-menu="false">
			<option></option>
			<c:forEach items="${educationLevels}" var="level">
			<option value="${level.id}">${level.name}</option>
			</c:forEach>
		</select>
		
		<label for="title">公司性质</label>
		<select name="companyType" multiple="multiple" data-native-menu="false">
			<option></option>
			<c:forEach items="${companyTypes}" var="jobType">
			<option value="${jobType.id}">${jobType.name}</option>
			</c:forEach>
		</select>
</fieldset>
<div class="ui-grid-a"><div class="ui-block-a"><input type="submit" class="ui-shadow ui-btn ui-corner-all" value="搜索" /></div><div class="ui-block-b"><button class="ui-shadow ui-btn ui-corner-all">保存条件</button></div></div>

	</form>
<div data-role="collapsible" data-collapsed="false">
<h4>已保存查询条件查询历史</h4><ul data-role="listview"><li><a href="#">List item 1</a></li>
<li><a href="#">List item 2</a></li>
<li><a href="#">List item 3</a></li>
</ul>
</div>
	</div>