<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<%@ include file="/component/include/pub_view.jsp"%>

<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/sec/js/lavalamp.min.js"></script>
		<script type="text/javascript" src="${webPath}/component/sec/js/xixi.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
		<link type="text/css" rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
		<link type="text/css" rel="stylesheet" href="${webPath}/component/sec/css/menu.css" />
		<script type="text/javascript" >
			$(function(){
				$('#tablist').lavaLamp();
				$('#tablist').delegate('.pages-btn','click',function() {
					if(!$(this).hasClass("current-cat")){
						$(this).addClass("current-cat").siblings('.pages-btn').removeClass("current-cat");
						$("#tabs .tabs_list").find("iframe").eq(0).attr("src",$(this).find("a").data("src"));

						/* var curIndex = $(this).index();
						var mlValue = '-' + curIndex * 100 + '%';
						$("#tabs .tabs_list").find("iframe").eq(curIndex).attr("src",$(this).find("a").data("src"));
						$("#tabs .tabs_list").animate({marginLeft:mlValue},0,"easein"); */
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
			    background-color: #32b5cb;
			    margin-top: 47px;
			}
			.content .lavalampparent .lavalamp {
   				 height: 50px;
    			line-height: 50px;
    			background: white;
			}
			.content .lavaLampParent .lavaLamp li.pages-btn.current-cat a{
				color:#32b5cb;
			}
		</style>
	</head>
	<body style="overflow: hidden;background: #F0F5FB;">
		<div class="content">
			<div class="row wrapper lavaLampParent" >
				<ul class="lavaLamp" id="tablist">
					<li name="10001" class="current-cat pages-btn">
						<a  data-src="${webPath}/mfOaCons/getListPage">低值易耗品管理</a>
					</li>
					<li name="10002" class="pages-btn">
						<a  data-src="${webPath}/mfOaConsOperate/getListPage">申领管理</a>
					</li>
					<li name="10003" class="pages-btn">
						<a  data-src="${webPath}/mfOaConsClass/getListPage">类别设置</a>
					</li>
				</ul>
			</div>
			<div id="tabs" class="row" style="width: 100%;overflow: hidden;">
				<div class="tabs_list" style="width:300%;margin-left:0%;position: relative;">
					<div style="width:33.33%;height:calc(100% - 70px);float:left;">
						<iframe src="${webPath}/mfOaCons/getListPage" style="height:calc(100% - 10px);width:100%; "></iframe>
					</div>
					<!-- <div style="width:33.33%;height:calc(100% - 70px);float:left;">
						<iframe src="" style="margin-left:21px;width:calc(100% - 21px) ;height:calc(100% - 10px);"></iframe>
					</div>
					<div style="width:33.33%;height:calc(100% - 70px);float:left;">
						<iframe src="" style="margin-left:21px;width:calc(100% - 21px) ;height:calc(100% - 10px);"></iframe>
					</div> -->
				</div>
			</div>