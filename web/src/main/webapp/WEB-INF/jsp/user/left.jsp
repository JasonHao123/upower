<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page pageEncoding="UTF-8" %>
	    <div data-role="panel" class="jqm-navmenu-panel" data-position="left" data-display="overlay" data-theme="a">
	    	<ul class="jqm-list ui-alt-icon ui-nodisc-icon">
<li data-icon="home"><a href="<c:url value="/job/index.do" />">Home</a></li>
<li data-icon="user"><a href="<c:url value="/user/profile.do" />">用户信息页</a></li>
<sec:authorize access="hasRole('ROLE_TALENT')">
<li data-role="collapsible" data-enhanced="true" data-collapsed-icon="carat-d" data-expanded-icon="carat-u" data-iconpos="right" data-inset="false" class="ui-collapsible ui-collapsible-themed-content ui-collapsible-collapsed">
	<h3 class="ui-collapsible-heading ui-collapsible-heading-collapsed">
		<a href="#" class="ui-collapsible-heading-toggle ui-btn ui-btn-icon-right ui-btn-inherit ui-icon-carat-d">
		    工作机会<span class="ui-collapsible-heading-status"> click to expand contents</span>
		</a>
	</h3>
	<div class="ui-collapsible-content ui-body-inherit ui-collapsible-content-collapsed" aria-hidden="true">
		<ul>
			<li ><a href="../checkboxradio-radio/" data-ajax="false">我收藏的职位</a></li>
			<li ><a href="../checkboxradio-radio/" data-ajax="false">推荐给我的职位</a></li>
			<li ><a href="../checkboxradio-radio/" data-ajax="false">我应聘的职位</a></li>
			<li ><a href="../checkboxradio-radio/" data-ajax="false">面试机会</a></li>
		</ul>
	</div>
</li>
<li data-role="collapsible" data-enhanced="true" data-collapsed-icon="carat-d" data-expanded-icon="carat-u" data-iconpos="right" data-inset="false" class="ui-collapsible ui-collapsible-themed-content ui-collapsible-collapsed">
	<h3 class="ui-collapsible-heading ui-collapsible-heading-collapsed">
		<a href="#" class="ui-collapsible-heading-toggle ui-btn ui-btn-icon-right ui-btn-inherit ui-icon-carat-d">
			简历管理<span class="ui-collapsible-heading-status"> click to expand contents</span>
		</a>
	</h3>
	<div class="ui-collapsible-content ui-body-inherit ui-collapsible-content-collapsed" aria-hidden="true">
		<ul>
			<li ><a href="../controlgroup/" data-ajax="false">Controlgroup</a></li>
			<li ><a href="../controlgroup-dynamic/" data-ajax="false">Dynamic controlgroups</a></li>
		</ul>
	</div>
</li>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_COMPANY')">
<li data-role="collapsible" data-enhanced="true" data-collapsed-icon="carat-d" data-expanded-icon="carat-u" data-iconpos="right" data-inset="false" class="ui-collapsible ui-collapsible-themed-content ui-collapsible-collapsed">
	<h3 class="ui-collapsible-heading ui-collapsible-heading-collapsed">
		<a href="#" class="ui-collapsible-heading-toggle ui-btn ui-btn-icon-right ui-btn-inherit ui-icon-carat-d">
			企业管理<span class="ui-collapsible-heading-status"> click to expand contents</span>
		</a>
	</h3>
	<div class="ui-collapsible-content ui-body-inherit ui-collapsible-content-collapsed" aria-hidden="true">
		<ul>
			<li><a href="<c:url value="/user/company/list.do" />" data-ajax="false">公司信息</a></li>
			<li><a href="../collapsible-dynamic/" data-ajax="false">管理招聘专员</a></li>
			<li><a href="../collapsible-dynamic/" data-ajax="false">猎头合作意向</a></li>
			<li><a href="../collapsible-dynamic/" data-ajax="false">猎头合作管理</a></li>
		</ul>
	</div>
</li>
<li data-role="collapsible" data-enhanced="true" data-collapsed-icon="carat-d" data-expanded-icon="carat-u" data-iconpos="right" data-inset="false" class="ui-collapsible ui-collapsible-themed-content ui-collapsible-collapsed">
	<h3 class="ui-collapsible-heading ui-collapsible-heading-collapsed">
		<a href="#" class="ui-collapsible-heading-toggle ui-btn ui-btn-icon-right ui-btn-inherit ui-icon-carat-d">
			招聘管理<span class="ui-collapsible-heading-status"> click to expand contents</span>
		</a>
	</h3>
	<div class="ui-collapsible-content ui-body-inherit ui-collapsible-content-collapsed" aria-hidden="true">
		<ul>
			<li ><a href="<c:url value="/user/job/post.do" />" data-ajax="false">发布职位</a></li>
			<li ><a href="<c:url value="/user/job/list.do" />" data-ajax="false">我发布的职位</a></li>
			<li ><a href="../checkboxradio-radio/" data-ajax="false">简历筛选</a></li>			
			<li ><a href="../checkboxradio-radio/" data-ajax="false">面试管理</a></li>
		</ul>
	</div>
</li>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_AGENT')">
<li data-role="collapsible" data-enhanced="true" data-collapsed-icon="carat-d" data-expanded-icon="carat-u" data-iconpos="right" data-inset="false" class="ui-collapsible ui-collapsible-themed-content ui-collapsible-collapsed">
	<h3 class="ui-collapsible-heading ui-collapsible-heading-collapsed">
		<a href="#" class="ui-collapsible-heading-toggle ui-btn ui-btn-icon-right ui-btn-inherit ui-icon-carat-d">
			猎头功能<span class="ui-collapsible-heading-status"> click to expand contents</span>
		</a>
	</h3>
	<div class="ui-collapsible-content ui-body-inherit ui-collapsible-content-collapsed" aria-hidden="true">
		<ul>
			<li ><a href="../controlgroup/" data-ajax="false">职位收藏</a></li>
			<li ><a href="../controlgroup-dynamic/" data-ajax="false">我推荐的职位</a></li>
			<li ><a href="../controlgroup-dynamic/" data-ajax="false">应聘状态跟踪</a></li>
			<li ><a href="../controlgroup-dynamic/" data-ajax="false">委托职位</a></li>
			<li ><a href="../controlgroup-dynamic/" data-ajax="false">简历筛选</a></li>
			<li ><a href="../controlgroup-dynamic/" data-ajax="false">企业合作</a></li>
		</ul>
	</div>
</li>
</sec:authorize>
<li data-role="collapsible" data-enhanced="true" data-collapsed-icon="carat-d" data-expanded-icon="carat-u" data-iconpos="right" data-inset="false" class="ui-collapsible ui-collapsible-themed-content ui-collapsible-collapsed">
	<h3 class="ui-collapsible-heading ui-collapsible-heading-collapsed">
		<a href="#" class="ui-collapsible-heading-toggle ui-btn ui-btn-icon-right ui-btn-inherit ui-icon-carat-d">
			好友管理<span class="ui-collapsible-heading-status"> click to expand contents</span>
		</a>
	</h3>
	<div class="ui-collapsible-content ui-body-inherit ui-collapsible-content-collapsed" aria-hidden="true">
		<ul>
			<li ><a href="../controlgroup/" data-ajax="false">我关注的人</a></li>
			<li ><a href="../controlgroup-dynamic/" data-ajax="false">关注我的人</a></li>
			<li ><a href="../controlgroup-dynamic/" data-ajax="false">互相关注的人</a></li>
		</ul>
	</div>
</li>

<li data-role="collapsible" data-enhanced="true" data-collapsed-icon="carat-d" data-expanded-icon="carat-u" data-iconpos="right" data-inset="false" class="ui-collapsible ui-collapsible-themed-content ui-collapsible-collapsed">
	<h3 class="ui-collapsible-heading ui-collapsible-heading-collapsed">
		<a href="#" class="ui-collapsible-heading-toggle ui-btn ui-btn-icon-right ui-btn-inherit ui-icon-carat-d">
			钱包<span class="ui-collapsible-heading-status"> click to expand contents</span>
		</a>
	</h3>
	<div class="ui-collapsible-content ui-body-inherit ui-collapsible-content-collapsed" aria-hidden="true">
		<ul>
			<li ><a href="../controlgroup/" data-ajax="false">Controlgroup</a></li>
			<li ><a href="../controlgroup-dynamic/" data-ajax="false">Dynamic controlgroups</a></li>
		</ul>
	</div>
</li>
<li data-role="collapsible" data-enhanced="true" data-collapsed-icon="carat-d" data-expanded-icon="carat-u" data-iconpos="right" data-inset="false" class="ui-collapsible ui-collapsible-themed-content ui-collapsible-collapsed">
	<h3 class="ui-collapsible-heading ui-collapsible-heading-collapsed">
		<a href="#" class="ui-collapsible-heading-toggle ui-btn ui-btn-icon-right ui-btn-inherit ui-icon-carat-d">
			设置<span class="ui-collapsible-heading-status"> click to expand contents</span>
		</a>
	</h3>
	<div class="ui-collapsible-content ui-body-inherit ui-collapsible-content-collapsed" aria-hidden="true">
		<ul>
			<li ><a href="../controlgroup/" data-ajax="false">Controlgroup</a></li>
			<li ><a href="../controlgroup-dynamic/" data-ajax="false">Dynamic controlgroups</a></li>
		</ul>
	</div>
</li>
<li  data-icon="home"><a href="<c:url value="/j_spring_security_logout" />">通信录</a></li>
<li  data-icon="home"><a href="<c:url value="/j_spring_security_logout" />">消息管理</a></li>
<li  data-icon="home"><a href="<c:url value="/j_spring_security_logout" />">退出</a></li>
		     </ul>
		</div><!-- /panel -->