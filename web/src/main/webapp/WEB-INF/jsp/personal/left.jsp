<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page pageEncoding="UTF-8" %>

  <script>  
		$( document ).on( "pagecreate", "#myPage", function() {
			if("${cate}"!="") {
	        	$("#category${cate}").addClass("ui-btn-active");
			}else {
				$("#category").addClass("ui-btn-active");
			}
		});
</script>

	    <div data-role="panel" class="jqm-navmenu-panel" data-position="left" data-display="overlay" data-theme="a">
	    	<ul id="messageCategory" class="jqm-list ui-alt-icon ui-nodisc-icon">
				<li data-icon="plus"><a href="<c:url value="/personal/post.do" />" data-ajax="false">创建新消息</a></li>
				<li data-role="list-divider">消息过滤</li>
				<li data-icon="user"><a id="category-1" href="<c:url value="/personal/index.do"><c:param name="category" value="-1" /></c:url>" data-ajax="false">我发布的消息</a></li>
				<li><a id="category" href="<c:url value="/personal/index.do" />" data-ajax="false">全部</a></li>
				
				<c:forEach items="${categories}" var="category">
				<li><a id="category${category.id}" href="<c:url value="/personal/index.do"><c:param name="category" value="${category.id}" /></c:url>" data-ajax="false">${category.name}</a></li>
				</c:forEach>
		     </ul>
		</div><!-- /panel -->