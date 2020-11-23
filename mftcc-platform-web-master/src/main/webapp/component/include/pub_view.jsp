<%@page import="cn.mftcc.util.DateUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<%@ include file="message.jsp"%>
<%@ page import="app.component.common.SysGlobalParams"%>
<%@ page import="app.component.sys.entity.MfSysCompanyMst"%>
<%
	// ↓ 使用规范命名的变量。
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ webPath + "/";
	String reportUrl = "/REPORT_ISSUE/";
	MfSysCompanyMst mfSysCompanyMst=( MfSysCompanyMst) SysGlobalParams.get("COMPANY");
%>

<script type="text/javascript">
var webPath = "${webPath}";
var today = "<%=DateUtil.getDate("yyyy-MM-dd")%>";
var loadingGifPath = "<%=mfSysCompanyMst.getLoadAnimationImg()%>";
var loadingSmallGifPath = "<%=mfSysCompanyMst.getLoadAnimationSmallImg()%>";
</script>


<script type="text/javascript"
	src="${webPath}/component/include/SysConstant.js"></script>
<%--jquery表单序列化--%>
<script type="text/javascript"
	src="${webPath}/themes/view/js/jquery.serializejson.min.js"></script>
<%--日期控件:
	选择月-----onClick="laydatemonth(this)"
	选择日-----onClick="fPopUpCalendarDlg(),若选择日期需要从当前日期开始，则使用fPopUpCalendarDlg({beginDate:1})"
	选择日(时分秒)-----onClick="laydateTimer(this)"
	选择年-----onClick="laydateYear()"
--%>
<script type="text/javascript"
	src="${webPath}/component/include/laydate/laydate.dev.js"></script>
<script type="text/javascript"
	src="${webPath}/component/include/laydate/laydate.month.js"></script>
<script type="text/javascript"
	src="${webPath}/component/include/laydate/laydate.year.js"></script>
<%--自动下拉菜单
	onclick="prodAutoMenu(this,url,callback)"
--%>
<script type="text/javascript"
	src="${webPath}/component/include/jquery.autocompleter.js"></script>
<script type="text/javascript"
	src="${webPath}/component/include/jquery.autocompleter.unbound.js"></script>
<link rel="stylesheet"
	href="${webPath}/component/include/autocompleter.css?v=${cssJsVersion}" />

<%--滚动条js 和鼠标滚动事件js--%>
<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.min.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js?v=${cssJsVersion}"></script>
<!-- 自定义JS 基础JS -->
<script type='text/javascript' src='${webPath}/component/include/base.js'></script>
<!-- 自定义js 失去焦点校验 保存校验 -->
<script type="text/javascript" src='${webPath}/component/include/uior_val.js?v=${cssJsVersion}'> </script>
<script type="text/javascript" src='${webPath}/component/include/xcqi_cal.js'> </script>
<%--详情表单双击修改--%>
<script type='text/javascript' src="${webPath}/component/include/dbClick.js?v=${cssJsVersion}"></script>
<%--表单操作--%>
<script type="text/javascript" src="${webPath}/component/include/form.js?v=${cssJsVersion}"></script>
<%--格式化--%>
<script type="text/javascript" src="${webPath}/component/include/format.js?v=${cssJsVersion}"></script>
<%--自定义证件验证--%>
<script type="text/javascript" src="${webPath}/component/include/idvalidate.js"></script>
<%--页面块布局--%>
<script type="text/javascript" src="${webPath}/component/include/freewall.js"></script>
<%--加密算法--%>
<script type="text/javascript" src="${webPath}/component/include/encryptJS/MD5.js"></script>
<%--时间轴--%>
<script type="text/javascript" src="${webPath}/component/include/formline.js"></script>
<!-- 弹层关闭的方法 -->
<script type="text/javascript" src='${webPath}/component/include/closePopUpBox.js'> </script>
<%--字体图标--%>
<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.min.css" />
<%-- <link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.css" /> --%>
<link rel="stylesheet" href="${webPath}/UIplug/iconmoon/style.css" />
<%--bootstrap框架--%>
<script type="text/javascript"
	src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${webPath}/themes/factor/css/BS-factor.css?v=${cssJsVersion}" />
<%--滚动样式--%>
<link rel="stylesheet"
	href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css?v=${cssJsVersion}"
	type="text/css" />
<%-- <link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/style.css" type="text/css" /> --%>
<%--我的筛选样式--%>
<link rel="stylesheet" href="${webPath}/themes/view/css/view-main.css?v=${cssJsVersion}" />
<!-- 大表单样式 -->
<link rel="stylesheet" href="${webPath}/themes/view/css/bigForm.css?v=${cssJsVersion}" />
<!-- 大表单时间轴 -->
<link rel="stylesheet" href="${webPath}/themes/view/css/formline.css?v=${cssJsVersion}" />
<!-- 流程审批公共样式 -->
<link rel="stylesheet" href="${webPath}/component/wkf/css/wkf.css?v=${cssJsVersion}" />
<!-- 多选样式 -->
<link rel="stylesheet" href="${webPath}/component/include/mulitselector/mulitSelect.css?v=${cssJsVersion}" />
<!-- 获取请求头信息，判断浏览器 -->
<script src="${webPath}/UIplug/userAgent/userAgent.js" language="javascript" type="text/javascript"></script>
<%--布局自适应--%>
<script type="text/javascript"
	src="${webPath}/component/include/autolayout.js"></script>
<!-- 行政区划和行业划分 -->
<script type="text/javascript"
	src="${webPath}/component/include/IndustryAndArea.js"></script>
<link rel="stylesheet"
	href="${webPath}/component/include/leaseCheckBox.css?v=${cssJsVersion}" />
<script type="text/javascript"
	src="${webPath}/component/include/leaseCheckBox.js"></script>
<script type="text/javascript"
	src="${webPath}/component/include/jquery.qtip.min.js"></script>
<link rel="stylesheet"
	href="${webPath}/component/include/jquery.qtip.min.css" />
<script type="text/javascript"
	src="${webPath}/component/include/tablelistshowdiv.js"></script>
<!-- 证件号码验证 -->
<script type="text/javascript"
	src='${webPath}/component/include/idCheck.js'> </script>

<!-- ztree -->
<link rel="stylesheet"
	href="${webPath}/UIplug/zTree/zTreeStyle/zTreeStyle.css" />
<script type='text/javascript'
	src='${webPath}/UIplug/zTree/jquery.ztree.all-3.5.min.js'></script>

<script type="text/javascript" src="${webPath}/component/include/popupSelection/popupSelection.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/include/popupSelection/popupList.js?v=${cssJsVersion}" ></script>
<!--帮助组件  -->
<%-- <script type="text/javascript" src="${webPath}/component/help/sysHelp.js" ></script> --%>
<%-- <link rel="stylesheet" href="${webPath}/component/help/sysHelp.css" /> --%>

<%--保理样式 --%>
<script rel="text/javascript"
	src="${webPath}/themes/factor/js/formStyle.js?v=${cssJsVersion}"></script>
<link rel="stylesheet"
	href="${webPath}/themes/factor/css/view-main.css?v=${cssJsVersion}" />
<link rel="stylesheet"
	href="${webPath}/themes/factor/css/bigForm.css?v=${cssJsVersion}" />
<link rel="stylesheet"
	href="${webPath}/themes/factor/css/formline.css?v=${cssJsVersion}" />
<link rel="stylesheet"
	href="${webPath}/themes/factor/css/detail.css?v=${cssJsVersion}" />
<link rel="stylesheet"
	href="${webPath}/themes/factor/css/common.css?v=${cssJsVersion}" />
<link rel="stylesheet"
	href="${webPath}/themes/factor/css/formStyle.css?v=${cssJsVersion}" />
<link rel="stylesheet"
	href="${webPath}/themes/factor/css/factor.css?v=${cssJsVersion}" />

<!-- 弹窗消息模板 -->
<script type="text/javascript"
	src="${webPath}/component/include/getMessage.js"></script>
<!--artdialog插件  -->
<script type="text/javascript"
	src="${webPath}/UIplug/artDialog/dist/dialog-plus-min.js"></script>
<link id="ui-dialog" rel="stylesheet"
	href="${webPath}/UIplug/artDialog/css/ui-dialog${skinSuffix}.css" />
<!-- 引入自定义的弹窗扩展 -->
<script type="text/javascript"
	src="${webPath}/themes/factor/js/dialog.js?v=${cssJsVersion}"></script>

<!--弹窗选择公用js  -->
<script type="text/javascript"
	src="${webPath}/themes/factor/js/selectInfo.js"></script>
<!--字符串工具类公用js  -->
<script type="text/javascript"
	src="${webPath}/component/include/StringUtil.js"></script>
<!--加载动画js  -->
<script type="text/javascript"
	src="${webPath}/themes/factor/js/loadingAnimate.js?v=${cssJsVersion}"></script>
<!--factor自定义的表单相关js  -->
<script type="text/javascript"
	src="${webPath}/themes/factor/js/formFactor.js?v=${cssJsVersion}"></script>
<!--块状信息视角配置 ，在需要用的地方加即可
 <script type="text/javascript" src="${webPath}/themes/factor/js/viewpointggl.js"></script> -->
<!-- 手机号码和证件号码唯一性验证js -->
<script type="text/javascript"
	src='${webPath}/component/include/checkUniqueVal.js'>
	
</script>
<script type="text/javascript">
	/**
	 * 获取当前操作的ifream
	 * @param {Object} timeStamp 时间戳
	 * @param {Object} ifreamHeight 窗体高度
	 */
	window.resizeHeight = function(timeStamp, ifreamHeight, callback) {
		var thisIfreamObj;
		$(window.document).find("iframe").each(
				function(index) {
					var thisObj = $(this);
					var thisWindow = $(this).contents().find("body");
					var thisTimeStamp = $(thisWindow).find("#timeStamp").attr(
							"timeStamp");
					if (thisTimeStamp !== undefined
							&& thisTimeStamp == timeStamp) {
						thisIfreamObj = thisObj;
						$(thisIfreamObj).animate({
							height : ifreamHeight + 40
						}, 500, function() {
							if (callback !== undefined) {
								callback.call(this);
							}
						});
					}
				});
	};
	window.flushIfream = function() {
		window.parent.location.reload();
	};
	// 	var par = $("body",parent.document);
	// 	try{
	// 		if(par.find(".pt-page[name=A]").length>0){
	// 			getHelp();
	// 		}else{
	// 			parent.getHelp();
	// 		}
	// 	}catch(err){
	// 		console.warn("当前页面或者父级页面未引用SysHelp.js文件！ --"+err);
	// 	}
</script>
