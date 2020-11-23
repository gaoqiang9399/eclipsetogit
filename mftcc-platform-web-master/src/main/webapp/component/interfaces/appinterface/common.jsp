<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="wx" uri="/WEB-INF/tld/weChat.tld"%>
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+webPath+"/";
%>
<!DOCTYPE html>
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<script type="text/javascript" src="${webPath}/wechat/libs/jQueryWeUI/lib/jquery-2.1.4.js"></script>
<!-- FastClick消除点击延时提高程序的运行效率 -->
<script type="text/javascript" src="${webPath}/wechat/libs/jQueryWeUI/lib/fastclick.js"></script>
<!-- jQuery WeUI 组建 -->
<script type="text/javascript" src="${webPath}/wechat/libs/jQueryWeUI/js/jquery-weui.js"></script>
<!-- 移动触摸滑动插件 -->
<script type="text/javascript" src="${webPath}/wechat/libs/jQueryWeUI/js/swiper.min.js"></script>
<!-- 控制台打印工具，调试的时候使用 -->
<%-- <script type="text/javascript" src="${webPath}/wechat/libs/vConsole-2.5.1/vconsole.min.js"></script>
 --%>
<!-- 解析列表 -->
<script type="text/javascript" src="${webPath}/wechat/include/wx_Parselist.js"></script>
<!-- 字体图库 -->
<link rel="stylesheet" href="${webPath}/layout/view/themes/iconfont/css/iconfont.css" />
<!-- jQuery WeUI 组建 -->
<link rel="stylesheet" href="${webPath}/wechat/libs/jQueryWeUI/lib/weui.css"/>
<link rel="stylesheet" href="${webPath}/wechat/libs/jQueryWeUI/css/jquery-weui.css"/>
<script type="text/javascript">
	//解决ios select会跳
	window.addEventListener('load', function() { 
		FastClick.attach(document.body); 
	}, false);
	/* $(function() {  
	    FastClick.attach(document.body); //实例化fastclick
	});  */
</script>
<!-- weui css重写 -->
<link rel="stylesheet" href="${webPath}/component/interfaces/appinterface/common/css/weui.override.css"/>
<link rel="stylesheet" href="${webPath}/component/interfaces/appinterface/common/css/common.css"/>
<link rel="stylesheet" href="${webPath}/UIplug/iconmoon/style.css"/>
<script src="http://g.alicdn.com/dingding/open-develop/1.6.9/dingtalk.js"></script>
<script src="https://g.alicdn.com/ilw/ding/0.9.9/scripts/dingtalk.js"></script>
<!-- 自定义js 失去焦点校验 保存校验 -->
<script type="text/javascript" src="${webPath}/component/include/uior_val1.js"></script>
<script type="text/javascript" src="${webPath}/component/include/xcqi_cal.js"></script>
<script type="text/javascript" src="${webPath}/component/include/idCheck.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/include/idvalidate.js"></script>

<script type="text/javascript" src="${webPath}/component/interfaces/appinterface/js/salt-router.js"></script>
<script type="text/javascript" src="${webPath}/component/interfaces/appinterface/js/MfAppBusApply.js"></script>
<script type="text/javascript" src="${webPath}/component/interfaces/appinterface/js/DingUtil.js"></script>
