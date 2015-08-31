<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page pageEncoding="UTF-8" %>
    <link type="text/css" rel="Stylesheet" href="<c:url value="/resources/jqwidgets/styles/jqx.base.css" />" />

    <script type="text/javascript" src="<c:url value="/resources/jqwidgets/jqxcore.js" />" ></script>
    <script type="text/javascript" src="<c:url value="/resources/jqwidgets/jqxdata.js" />" ></script>
    <script type="text/javascript" src="<c:url value="/resources/jqwidgets/jqxchart.core.js" />" ></script>
    <script type="text/javascript" src="<c:url value="/resources/jqwidgets/jqxdraw.js" />" ></script>

     
<script type="text/javascript">

wx.ready(function(){
    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
	wx.onMenuShareTimeline({
	    title: '${profile.nickname}社交圈的性别年龄分析', // 分享标题
	    link: 'http://www.weaktie.cn<c:url value="/public/result.do"><c:param name="key" value="${result.key}" /></c:url>', // 分享链接
	    imgUrl: '${profile.headimgurl}', // 分享图标
	    success: function () { 
	        // 用户确认分享后执行的回调函数

	    },
	    cancel: function () { 
	        // 用户取消分享后执行的回调函数
	    	// alert("cancelled");
	    }
	});
    
	wx.onMenuShareAppMessage({
		title:'${profile.nickname}社交圈的性别年龄分析',
		desc: '友势力是一个分析人际关系，并利用人际关系网络提供差异化服务的平台', // 分享标题
	    link: 'http://www.weaktie.cn<c:url value="/public/result.do"><c:param name="key" value="${result.key}" /></c:url>', // 分享链接
	    imgUrl: '${profile.headimgurl}', // 分享图标
	    success: function () { 
	        // 用户确认分享后执行的回调函数
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
<script type="text/javascript">
<!--
$( document ).on( "pagecreate", "#myPage", function() {
    var  sexData = [
      <c:forEach items="${result.data}" var="item">
      <c:if test="${item.series=='SEX'}">
      { key:'${item.key}', value:${item.value}},
      </c:if>
		</c:forEach>
      ];
    
    var  ageData = [
                    <c:forEach items="${result.data}" var="item">
                    <c:if test="${item.series=='AGE'}">
                    { key:'${item.key}', value:${item.value}},
                    </c:if>
              		</c:forEach>
                    ];
    
 
    
    var sexSettings = {
            title: "性别分析",
            description: "社交距离：${result.distance}",
            padding: { left: 5, top: 5, right: 5, bottom: 5 },
            titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
            source: sexData,
            xAxis:
            {
                dataField: 'key',
                valuesOnTicks: false
            },
            colorScheme: 'scheme01',
            seriesGroups:
                [
                   
                    {
                        type: 'pie',
                        series: [
                                { dataField: 'value', displayText: 'key'}
                               
                            ]
                    }
                ]
        };
    // select the chartContainer DIV element and render the chart.
    $('#jqxChartSex').jqxChart(sexSettings);
    
    
    var ageSettings = {
            title: "年龄分析",
            description: "社交距离：${result.distance}",
            padding: { left: 5, top: 5, right: 5, bottom: 5 },
            titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
            source: ageData,
            xAxis:
            {
                dataField: 'key',
                valuesOnTicks: false
            },
            colorScheme: 'scheme01',
            seriesGroups:
                [
                   
                    {
                        type: 'pie',
                        series: [
                                { dataField: 'value', displayText: 'key'}
                               
                            ]
                    }
                ]
        };
	
    $('#jqxChartAge').jqxChart(ageSettings);
    
    
	
});
//-->
</script>
<div role="main" class="ui-content jqm-content">
<h2>${profile.nickname}社交圈的性别年龄分析</h2>

      <div id="jqxChartSex" style="width: 100%; height: 300px;">
      </div>
      <div id="jqxChartAge" style="width: 100%; height: 300px;">
      </div>
</div>