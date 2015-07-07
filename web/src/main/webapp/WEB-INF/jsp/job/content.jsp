<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>
<link rel="stylesheet" href="<c:url value="/resources/css/listview-grid.css" />">
	<div role="main" class="ui-content">

<form>

     <input placeholder="Input keywords/position/company" type="search" name="search-1" id="search-1" value="">
</form>
 <div class="j_myOperating">
    <div id="newfeed" class="j_boxe">
        <a href="<c:url value="/user/company/register.do" />">
            <img src="<c:url value="/resources/img/4.2_homeIcon_1.png"/>">
            <span>注册公司</span>
        </a>
        <b class="newNumber"></b>
    </div>
    <div class="j_boxe">
        <a onclick="MyApply_Event();">
            <img src="<c:url value="/resources/img/4.2_homeIcon_2.png"/>">
            <span>申请记录</span>
        </a>
    </div>
    <div class="j_boxe">
        <a onclick="Refresh_resume();">
            <img src="<c:url value="/resources/img/4.2_homeIcon_3.png"/>">
            <span>刷新简历</span>
        </a>

    </div>
    <div class="j_boxe">
        <a onclick="MyResume_Event();">
            <img src="<c:url value="/resources/img/4.2_homeIcon_4.png"/>">
            <span>我的简历</span>
        </a>
    </div>
    <div class="j_boxe">
        <a onclick="JobSearch();" href="/searchjob/startsearch?cityCode=521">
            <img src="<c:url value="/resources/img/4.2_homeIcon_5.png"/>">
            <span>职位搜索</span>
        </a>
    </div>
    <div class="j_boxe">
        <a onclick="RecommendedPosts();">
            <img src="<c:url value="/resources/img/4.2_homeIcon_6.png"/>">
            <span>职位推荐</span>
        </a>
    </div>
    <div class="j_boxe">
        <a onclick="MyCollect_Event();">
            <img src="<c:url value="/resources/img/4.2_homeIcon_7.png"/>">
            <span>我的收藏</span>
        </a>
    </div>
    <div class="j_boxe">
        <a onclick="SalaryQueryNum();" href="/salaryquery">
            <img src="<c:url value="/resources/img/4.2_homeIcon_8.png" />">
            <span>薪酬查询</span>
        </a>
    </div>
</div>
        </div>