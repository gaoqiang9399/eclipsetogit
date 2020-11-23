<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/webPath.jsp" %>
<%
String path = request.getContextPath();
String contextPath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String layout = "layout/view";
//String sysDate = (String)request.getSession().getAttribute("sysDate");//这个日期是数据库存的日期
%>
<%-- <jsp:include page="/creditapp/talkjs.jsp"></jsp:include> --%>
<!DOCTYPE html>
<html>
  <head>
	    <script type="text/javascript">
	  		var pageType = "set";
	  		//var webPath = "${webPath}";
	  		var loadingGifPath = "${mfSysCompanyMst.loadAnimationImg}";
	  		var loadingSmallGifPath = "${mfSysCompanyMst.loadAnimationSmallImg}";
	    </script>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<title>开发者平台</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/formStyle.css?v=${cssJsVersion}" />
		<link rel="stylesheet" href="${webPath}/themes/view/css/view-main.css" />
		<link rel="stylesheet" href="${webPath}/themes/view/css/formline.css" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/formline.css?v=${cssJsVersion}" />
		
		<link rel="shortcut icon" type="image/x-icon" href="${webPath}/favicon.ico" />
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="${webPath}/layout/ie8/js/es5-shim.js"></script>
		<script type="text/javascript" src="${webPath}/layout/ie8/js/es5-sham.js"></script>
		<script type="text/javascript" src="${webPath}/layout/ie8/js/json2.js"></script>
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/jquery.layout.min.js"></script>
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/zl.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/jquery.autocompleter.js"></script>
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/notification.js"></script>
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/fixedDiv.js" ></script>
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/fixedDivFun.js" ></script>
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/moveBack.js" ></script>
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/view.js" ></script>
		<script type="text/javascript" src="${webPath}/dwr/engine.js"></script>
		<script type='text/javascript' src="${webPath}/dwr/util.js"></script>  
  		<script type="text/javascript" src="${webPath}/dwr/interface/MessagePush.js"></script> 
  		<script type="text/javascript" src="${webPath}/<%=layout%>/js/fakeLoader.js"></script>
  		
  		<%--加密算法--%>
		<script type="text/javascript" src="${webPath}/component/include/encryptJS/MD5.js"></script>
  		
  		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" type="text/css"/>
  		<script type="text/javascript" src="${webPath}/<%=layout%>/js/jAlert.js"></script>
		<%--bootstrap--%>
		<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/js/openDiv.js" ></script>
		<link rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/layout.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/menu.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/page/css/fakeLoader.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/iconmoon/style.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/pageInfo.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/A2B.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/autocompleter.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/animate.min.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/notification.css" />
		<%-- <script type="text/javascript" src="${webPath}/component/help/sysHelp.js" ></script> --%>
		<script type="text/javascript" src="${webPath}/layout/view/js/warns.js" ></script>
		<%-- <link rel="stylesheet" href="${webPath}/component/help/sysHelp.css" /> --%>
		<link rel="stylesheet" href="${webPath}/tech/wkf/css/modelAlert.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/fixedDiv.css" />
		<link rel="stylesheet" href="${webPath}/tech/wkf/css/processDetail.css" />
		<!-- 弹窗消息模板 -->
		<script type="text/javascript" src="${webPath}/component/include/getMessage.js"></script>
		<!--artDialog控件  -->
		<script type="text/javascript" src="${webPath}/UIplug/artDialog/dist/dialog-plus-min.js"></script>
  		<link id="ui-dialog" rel="stylesheet" href="${webPath}/UIplug/artDialog/css/ui-dialog${skinSuffix}.css" />
		<script type="text/javascript" src="${webPath}/themes/factor/js/dialog.js?v=${cssJsVersion}"></script>
  		
  		<!--加载动画js  -->
  		<script type="text/javascript" src="${webPath}/themes/factor/js/loadingAnimate.js?v=${cssJsVersion}"></script>
  		<link id="" rel="stylesheet" href="${webPath}/themes/view/css/filter${skinSuffix}.css" />
		<link id="BS-factor" rel="stylesheet" href="${webPath}/themes/factor/css/BS-factor${skinSuffix}.css?v=${cssJsVersion}" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/layout${skinSuffix}.css?v=${cssJsVersion}" />
	    <link id="A2B" rel="stylesheet" href="${webPath}/themes/factor/css/A2B${skinSuffix}.css" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/viewUserDef${skinSuffix}.css?v=${cssJsVersion}" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/pageInfo.css" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/modelAlert.css" />
		<script>
	/* 	MfSysCompanyMst mfSysCompanyMst=( MfSysCompanyMst) SysGlobalParams.get("COMPANY"); 
		try {
			if(supports_html5_storage()){
				var recWarnd = new RecWarnd(${calendarDaylist});
			}
		} catch (e) {
			console.log("获取本地存储失败，日程功能可能不能使用！-" + e.message + "-" + e.name);
		} */
		function onPageLoad(){  
	       	var userId = '${regNo}';
	        MessagePush.onPageLoad(userId);  
        }
		function showMessage(msgType,msgCount,autoMessage,taskInfo,username){  
			 rzzl.showMessages(msgCount,username,msgType,autoMessage,taskInfo); 
     	}
		function closePop(){
			var dm = document.getElementById("b1_iframe").contentWindow;
			if($(dm.document).find(".task_select").length>0){
				var $task = $(dm.document).find(".task_select");
				var data = $task.data("info");
				$task.removeClass("task_correct").removeClass("task_select").data("open",true).find(".task_contents").animate({height:"25px"},300,function(){
					$(this).parents(".task_style").find(".task_ctrl").slideUp(function(){
						$(this).empty();
						document.getElementById("b1_iframe").contentWindow.mcSelector.mCustomScrollbar("update");
					});
				});
				document.getElementById("b1_iframe").contentWindow.taskB.changeTaskSts(data);
			}
		}
		
		function showPageInfo(value){  
			rzzl.setPageInfo(value, "#pageInfoContent");
		}
	    	
			/**系统退出**/
	    	function sysQuit(){
	    		window.top.location.href = webPath+"/logout.action";
	    	}
	    	
	    	
			var path = '${webPath}';
			var viewMenuData = eval("("+'<%=request.getSession().getAttribute("viewMenuData") %>'+")");
			var myLayout;
			$(document).ready(function() {
				rzzl.setBox_A(".pt-page[name='A'] iframe");
							myLayout = $('body').layout({
					center__paneSelector: ".outer-center",
					west__paneSelector: ".outer-west",
					west__size: 100,
					spacing_open: 0,
					west__childOptions: {
						center__paneSelector: ".outer-west-center",
						north__paneSelector: ".outer-west-logo",
						north__size: 55,
						spacing_open: 0
					},
					center__childOptions: {
						center__paneSelector: ".middle-center",
						north__paneSelector: ".middle-north",
						north__size:56,
						spacing_open: 0,
						spacing_closed:0,
						north__childOptions: {
							center__paneSelector: ".middle-north-center",
							west__paneSelector: ".middle-north-west",
							east__paneSelector: ".middle-north-east",
							west__size: 440,
							east__size: 80,
							spacing_open: 0
						}
					}
				});
				// add by LiuYF 文字被后边的div覆盖。不知道怎么改layout的单个z-index属性。
				$(".middle-north-west").css("z-index","1");
				rzzl.initMenu(".menu_B");
				$(".pageInfo").click(function(){
	 				$(this).hide();
					$("#nope").val("");
					$("#nope").show();
					$("#nope").focus();
				});
				//MessagePush.sendMessageAuto("0000:0000", "DL0001:DL0001","测试消息");
				/* var cn = 1;
				rzzl.showMessages(cn,"管理员","0","测试消息");
				cn++;
				var si = setInterval(function(){
					rzzl.showMessages(cn,"管理员","0","测试消息");
					cn++;
 				},240000); */
				rzzl.switchB.init($(".switchB"));
 				$("#setView").show();
 				$(".outer-center").show();
			});
			function whichTransitionEvent(el){
			    var t;
			    var transitions = {
			      'transition':'transitionend',
			      'OTransition':'oTransitionEnd',
			      'MozTransition':'transitionend',
			      'WebkitTransition':'webkitTransitionEnd'
			    };

			    for(t in transitions){
			        if( el.style[t] !== undefined ){
			            return transitions[t];
			        }
			    }
			}
				//消息中心
			function getMessagePage(){
				rzzl.skipPage(webPath+"/sysTaskInfo/getListPage");
				LoadingAnimate.stop();
				$(".menu-active").removeClass("menu-active");
			}
		</script>
		<style type="text/css">
			div[name='A']{
/* 				filter: alpha(opacity=0); */
				background-color:#fff;
			}
		</style>
	</head>
	<%@ include file="/component/include/pub_view_table.jsp"%>
	<body class="bgView" onLoad="onPageLoad();dwr.engine.setActiveReverseAjax(true);dwr.engine.setNotifyServerOnPageUnload(true);">
		<div class="outer-center" style="display:none;">
			<div class="middle-center pt-perspective">
				<div class="pt-page" name="A">
					<iframe id="a_iframe" src="${webPath}/sysSkip/skipToC?entranceNo=99" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" name="iframepage"></iframe>
				</div>
				<div class="pt-page pt-page-current" name="B">
					<div class="pg" name="B1">
						<iframe id="b1_iframe"  src="${webPath}/sysTaskInfo/getListPage" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" name="b1_iframe"></iframe>
					</div>
					<div class="pg" name="B2">
						<iframe id="b2_iframe" src="${webPath}/bwmTaskRoleRel/getListPage" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" width="100%" height="100%" name="b2_iframe"></iframe>
					</div>
				</div>
				<div class="switchB">
					<div class="A"></div>
					<div class="B"></div>
					<div class="current"></div>
				</div>
			</div>
				<div id="middle-north" class="middle-north">
				<!-- 切换层 -->
				<div class="middle-north-west TopBar-chevron" style="height:55px;">
					<span class="logodes" >${mfSysCompanyMst.systemName}</span>
					 <img src="${webPath}/${mfSysCompanyMst.propagandaLanguageImg}" style="width: 214px;height: 39px; position: relative; top: 15px; margin-left:283px;" />
				</div>
				<!-- 详情 -->
				<div class="middle-north-center">
					<div class="pull-right">
						 <ul class="nav navbar-nav">
						 	 <!--个人中心-->
						 	 <li  id="perDa" class="user-menu">
								<a href="#">
								  <img  id="headImgShow" class="user-image ">
								  <span>欢迎您，${regName}</span>
								</a>
							</li>
						 </ul>
					</div>
			    </div>
				<!-- <div class="middle-north-east" >
					<div class="pull-right">
						 <ul class="nav navbar-nav">
							工具箱
							<li class="toolbox-li">
            					<a href="#" onClick="getfixedDiv();"><i class="i i-menu menu"></i></a>
         					 </li>
						 </ul>
					</div>
				</div> -->
			</div>
		</div>
		<div class="outer-west" style="overflow:visible!important;">
			<!-- LOGO-->
			<!-- 视角-->
			<div class="set-page outer-west-center" style="overflow:visible;display: table;" id="devView">
				<div class="outer-west-logo" style=" height:55px; width: 100px; display: table-cell;vertical-align: middle;">
					 <img  src="${webPath}/${mfSysCompanyMst.systemLogoImg}" class="logoimg" > 
				</div>
				<!-- <ul class="menu_B">
				</ul> -->
				<ul class ="menu_A" style="width:120px;">
					<li class = "menu-active">
						<a href="${webPath}/sysDevView/setC?setType=developer" pagetype="diannao" onclick="return false">开发者平台&nbsp;&nbsp;&nbsp;</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	
	<div style='display:none;'>
					<img id="headImgShow1" class="account-img ">
				</div>
	
</body>


<script type="text/javascript">
	var viewFlag = "set";
	var opNo = "${opNo}";
	var url= webPath+"/sysUser/getByIdAjax?opNo="+opNo+"&query=query";
    var queryMenueArr = new Array ();
	$(function() {
		$('#myModal').modal('hide');
		view.init(); 
	});	
</script>
</html>