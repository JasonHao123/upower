<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>
<link rel="stylesheet" href="<c:url value="/resources/css/listview-grid.css" />">
<div role="main" class="ui-content jqm-content">
社交圈范围设定
核心（好友）
圈子

个人信息可见范围
<form>
<h3>朋友圈设置</h3>
<label for="slider-2">朋友圈范围:</label>
<input type="range" name="slider-2" id="slider-2" value="3" min="1" max="5" data-highlight="true">
<label for="slider-2">群组中朋友圈范围:</label>
<input type="range" name="slider-3" id="slider-3" value="5" min="1" max="7" data-highlight="true">
<label for="flip-2">允许朋友圈好友给我发消息:</label>
<select name="flip-2" id="flip-2" data-role="slider">
<option value="off">Off</option>
<option value="on">On</option>
</select>
</form>
</div>