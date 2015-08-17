<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page pageEncoding="UTF-8" import="org.apache.commons.codec.digest.DigestUtils" %>



<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">

wx.config({
    debug: true,
    appId: '${appId}',
    timestamp:'${timestamp}',
    nonceStr: '${noncestr}',
    signature: '${signature}',
    jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
});

wx.ready(function(){
    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
	wx.onMenuShareTimeline({
	    title: '${profile.nickname}邀请您成为好友', // 分享标题
	    link: '<c:url value="/weixin/accept"><c:param name="id" value="${uuid}" /></c:url>', // 分享链接
	    imgUrl: '${profile.headimgurl}', // 分享图标
	    success: function () { 
	        // 用户确认分享后执行的回调函数
	        $.ajax( "<c:url value="/weixin/invite2"><c:param name="id" value="${uuid}" /><c:param name="type" value="timeline" /></c:url>" )
			  .done(function() {
			    alert( "success" );
			  })

	    },
	    cancel: function () { 
	        // 用户取消分享后执行的回调函数
	    	// alert("cancelled");
	    }
	});
    
	wx.onMenuShareAppMessage({
	    title: '${profile.nickname}邀请您成为好友', // 分享标题
	    link: '<c:url value="/weixin/accept"><c:param name="id" value="${uuid}" /></c:url>', // 分享链接
	    imgUrl: '${profile.headimgurl}', // 分享图标
	    success: function () { 
	        // 用户确认分享后执行的回调函数
	        $.ajax( "<c:url value="/weixin/invite2"><c:param name="id" value="${uuid}" /><c:param name="type" value="app" /></c:url>" )
			  .done(function() {
			    alert( "success" );
			  })
	    },
	    cancel: function () { 
	        // 用户取消分享后执行的回调函数
	    	// alert("cancelled");
	    }
	});
});
wx.error(function(res){
	$("#errorMsg").html("微信API初始化失败，请重新刷新页面！");
    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。

});
</script>

<div role="main" class="ui-content">
<p id="errorMsg" style="color: red;"></p>
<h2>邀请好友</h2>
<p>快把此页面分享给好友或分享到朋友圈，让朋友们添加你为好友。别忘了添加好友后看看自己的势力状况啊！</p>

<p style="color: red">链接有效期为7天，过期后请重新生成链接再次分享给好友</p>

</div>
