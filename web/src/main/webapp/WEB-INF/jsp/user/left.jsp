<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page pageEncoding="UTF-8" %>

	    <div data-role="panel" class="jqm-navmenu-panel" data-position="left" data-display="overlay" data-theme="a">
	    	<ul class="jqm-list ui-alt-icon ui-nodisc-icon">
<li data-icon="user"><a href="<c:url value="/user/profile.do" />">个人信息</a></li>
<li  data-icon="plus"><a href="<c:url value="/social/addfriend.do" />">邀请好友</a></li>
<li data-role="collapsible" data-enhanced="true" data-collapsed-icon="carat-d" data-expanded-icon="carat-u" data-iconpos="right" data-inset="false" class="ui-collapsible ui-collapsible-themed-content ui-collapsible-collapsed">
	<h3 class="ui-collapsible-heading ui-collapsible-heading-collapsed">
		<a href="#" class="ui-collapsible-heading-toggle ui-btn ui-btn-icon-right ui-btn-inherit ui-icon-carat-d">
			社交圈子<span class="ui-collapsible-heading-status"> click to expand contents</span>
		</a>
	</h3>
	<div class="ui-collapsible-content ui-body-inherit ui-collapsible-content-collapsed" aria-hidden="true">
		<ul>
			<li ><a href="../controlgroup/" data-ajax="false">我的好友</a></li>
			<li ><a href="../controlgroup-dynamic/" data-ajax="false">我的圈子</a></li>
			<li ><a href="../controlgroup-dynamic/" data-ajax="false">我的群组</a></li>
			<li ><a href="../controlgroup-dynamic/" data-ajax="false">社交圈分析</a></li>
		</ul>
	</div>
</li>

<li data-role="collapsible" data-enhanced="true" data-collapsed-icon="carat-d" data-expanded-icon="carat-u" data-iconpos="right" data-inset="false" class="ui-collapsible ui-collapsible-themed-content ui-collapsible-collapsed">
	<h3 class="ui-collapsible-heading ui-collapsible-heading-collapsed">
		<a href="#" class="ui-collapsible-heading-toggle ui-btn ui-btn-icon-right ui-btn-inherit ui-icon-carat-d">
			消息管理<span class="ui-collapsible-heading-status"> click to expand contents</span>
		</a>
	</h3>
	<div class="ui-collapsible-content ui-body-inherit ui-collapsible-content-collapsed" aria-hidden="true">
		<ul>
			<li ><a href="../controlgroup/" data-ajax="false">私信</a></li>
			<li ><a href="../controlgroup-dynamic/" data-ajax="false">好友申请</a></li>
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
			<li ><a href="../controlgroup/" data-ajax="false">系统设置</a></li>
			<li ><a href="../controlgroup-dynamic/" data-ajax="false">社交设置</a></li>
			<li ><a href="../controlgroup-dynamic/" data-ajax="false">消息设置</a></li>
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
			<li ><a href="../controlgroup/" data-ajax="false">余额查询</a></li>
			<li ><a href="../controlgroup-dynamic/" data-ajax="false">交易记录</a></li>
		</ul>
	</div>
</li>
<li  data-icon="power"><a href="<c:url value="/j_spring_security_logout" />">退出</a></li>
		     </ul>
		</div><!-- /panel -->