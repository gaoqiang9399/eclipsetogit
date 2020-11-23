<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/sec/js/lavalamp.min.js"></script>
		<script type="text/javascript" src="${webPath}/component/sec/js/jQuery.easing.js"></script>
		<link type="text/css" rel="stylesheet" href="${webPath}/component/sec/css/menu.css" />
		<%--滚动条js 和鼠标滚动事件js--%>
		<script type="text/javascript" >
			$(function(){
				$('#tablist').lavaLamp();
				$('#tablist').delegate('.pages-btn','click',function() {
					if(!$(this).hasClass("current-cat")){
						$(this).addClass("current-cat").siblings('.pages-btn').removeClass("current-cat");
						var curIndex = $(this).index();
						var mlValue = '-' + curIndex * 100 + '%';
						$("#tabs .tabs_list").find("iframe").eq(curIndex).attr("src",$(this).find("a").data("src"));
						$("#tabs .tabs_list").animate({marginLeft:mlValue},0,"easein");
					}
				});
			});
		</script>
		<style type="text/css">
			.tabs_list iframe{
				border: none;
			}
			.lavaLamp li a{
				font-size: 14px;
				cursor: pointer;
			}
			.row{
				margin: 0;
				padding: 0;
			}
			.lavaLamp li.back {
			    background-color: #009ce1;
			}
			.lavaLamp {
				left:21px;
			}		
		</style>
	</head>
	<body class="overflowHidden">
		<div class="content" style="padding-top:0px;">
			<div class="row wrapper lavaLampParent" >
				<ul class="lavaLamp" id="tablist">
					<li name="10001" class="current-cat pages-btn">
						<a  data-src="${webPath}/secEntrance/secAuditConfig?moveBack=true">安全选项设置</a>
					</li>
					<li name="10002" class="pages-btn">
						<a  data-src="${webPath}/secUserMarkInfo/getStatistical?moveBack=true">安全审计查询</a>
					</li>
					<li name="10003" class="pages-btn">
						<a  data-src="${webPath}/sysLog/getListPage?moveBack=true">系统日志</a>
					</li>
					<li name="10003" class="pages-btn">
						<a  data-src="${webPath}/sysLoginLog/getListPage?moveBack=true">登陆日志</a>
					</li>
				</ul>
			</div>
			<div id="tabs" class="row" style="width: 100%;overflow: hidden;">
				<div class="tabs_list" style="width:400%;margin-left:0%;position: relative;">
					<div style="width:25%;height:calc(100% - 40px);float:left;">
						<iframe src="${webPath}/secEntrance/secAuditConfig" style=";height:calc(100% - 40px);width:100%; margin-top:10px;"></iframe>
					</div>
					<div style="width:25%;height:calc(100% - 40px);float:left;">
						<iframe src="" style="margin-left:21px;width:calc(100% - 21px) ;height:calc(100% - 40px); margin-top:10px;"></iframe>
					</div>
					<div style="width:25%;height:calc(100% - 40px);float:left;">
						<iframe src="" style="margin-left:21px;width:calc(100% - 21px) ;height:calc(100% - 40px);"></iframe>
					</div>
					<div style="width:25%;height:calc(100% - 40px);float:left;">
						<iframe src="" style="width:100%;height:100%;"></iframe>
					</div>
				</div>
			</div>
		</div>
	</body>	
</html>
