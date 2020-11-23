<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+webPath+"/";
%>
<html>
	<head>
		<title>统计</title>
		<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/jquery.placeholder.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
		<link type="text/css" rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
		<%--字体图标--%>
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.min.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.css" />
		<style type="text/css">
			.body_hiden{
				overflow: hidden;
			}
			.content_style{
	    		margin-top: 7px;
	    		margin-bottom:13px;	
			}
			.circle-text{
    			font-size: 14px;
			}
			.row_outer{
			}
			.dashbox .panel-body {
			    padding: 0;
			}
			.dashbox .panel-left {
				width: 30%;
				float: left;
				color: #FFFFFF;
				padding: 10px;
				text-align: center;
			}
			.dashbox .panel-right {
				width: 70%;
				float: right;
				position: relative;
				text-align: left;
			}
			.dashbox .panel-right .number {
				color: #666666;
			    font-size: 28px;
			    font-weight: 400;
			    padding: 0 10px 0 0;
			    text-align: right;
			}
			.dashbox .panel-right .label {
				position: absolute;
				right: 5px;
				top: 5px;
				font-size: 11px;
			}
			.label {
				height: 20px;
				line-height: 1.15;
			}
			.label-success {
				background-color: #a8bc7b;
			}
			.dashbox .panel-left.red i {
			    color: #d9534f;
			    padding-left: 30px;
			}
			.dashbox .panel-left.blue i {
			    color: #009bed;
			    padding-left: 30px;
			}
			.dashbox .panel-right .title {
			    text-transform: uppercase;
			    font-size: 12px;
			}
			.panel {
			    margin-bottom: 0px;
			}
			.panel-top,.panel-bottom{
				width: 100%;
				display: table;
			}
			.panel-top{
				background-color:#f4f4f4;
			}
			.panel-top .title{
				font-size: 12px;
				float: left;
				padding: 2px 5px; 
			}
			.label {
			    border-radius: 0.25em;
			    color: #fff;
			    display: inline;
			    font-size: 75%;
			    font-weight: 700;
			    line-height: 1;
			    padding: none;
			    text-align: center;
			    float: right;
			    vertical-align: baseline;
			    white-space: nowrap;
			}
			.label .float-size{
				font-style: normal;
			}
			.label-up{
				color: #DB534F;
				font-size: 12px;
			}
			.label-down{
				color: #AABE7B;
				font-size: 12px;
			}
			.btn {
			    -moz-user-select: none;
			    background-image: none;
			    border-radius:0px;
			    cursor: pointer;
			    display: inline-block;
			    font-size: 14px;
			    font-weight: 400;
			    line-height: 1.42857;
			    margin-bottom: 0;
			    text-align: center;
			    vertical-align: middle;
			    white-space: nowrap;
			    width: 100%;
			    padding: 0px;
			}
			.btns{
				width:100%;
				line-height: 22px;
			}
			.btnc{
				border:1px solid #D6D5DA;
				text-decoration: none;
				margin-bottom:2px;
				border-radius:2px;
			}
			.btnc_b_bottom{
				margin-bottom:0px;
			}
			.btnc input[type=button]{
				border: medium none;
			    cursor: pointer;
			    padding: 1px;
			    width: 100%;
			    background-color: #f0f0f0;
			   font-size: 12px;
			   color:#666;
			   
			}
			.btns .selected input[type=button]{
				background-color: #009de1;
				color:#fff;
			}
		</style>
		<script>
			var initCount = function(){
				var titles = ['当日登陆总人数','当日登陆次数','当日新增审批数','当日审批通过数','当日登陆失败总次数'];
				$.ajax({
					type:"get",
					url:webPath+"/secUserMarkInfo/getAllLoginAjax",
					async:false,
					success:function(data){
						if(data.flag=="success"){
							var datas = [];
							var i = 0;
							$.each(data.datas, function(index,data) {
								createShow(data.today,data.before,titles[index],index,'');
							});
						}else if(data.flag=="error"){
							
						}
					},error:function(){
						
					}
				});
			}
			var createShow = function(current,history,title,i,faName){
				var $show_context = $(".show_context").eq(i);
				var percent = 0;
				var upDownFlag = false;
				if(history==""||history==null||history==0){
					history = 1;
				}
				if(current!==null&&current!=""){
					if(current>history){
						upDownFlag = true;
						percent = (current-history)/history*100
					}else{
						upDownFlag = false;
						percent = (history-current)/history*100
					}
				}else{
					current = 0;
					history = 0;
				}
				var $html = $('<div class="dashbox panel panel-default"></div>');
				var $panel_body = $('<div class="panel-body"></div>');
				var $panel_top = $('<div class="panel-top"></div>');
				var $title = $('<span class="title"></span>');
					$title.text(title);
				var $label = $('<span class="label"></span>');
				var $floatSize = $('<i class="float-size"></i>');
					$floatSize.text(percent+"%");
					$label.append($floatSize);
				var $fa_up = $('<i class="fa fa-arrow-up"></i>');
				var $fa_down = $('<i class="fa fa-arrow-down"></i>');
				if(upDownFlag){
					$label.addClass("label-up");
					$label.append($fa_up);
				}else{
					$label.addClass("label-down");
					$label.append($fa_down);
				}
				$panel_top.append($title);
				$panel_top.append($label);
				var $panel_bottom = $('<div class="panel-bottom"></div>');
				var $panel_left = $('<div class="panel-left blue"></div>');
				var $panel_left_fa =$('<i class="fa '+faName+'fa-tv fa-2x"></i>');
				$panel_left.append($panel_left_fa);
				var $panel_right =  $('<div class="panel-right"></div>');
				var $namber = $('<div class="number"></div>');
					$namber.text(current);
				$panel_right.append($namber);
				$panel_bottom.append($panel_left);
				$panel_bottom.append($panel_right);
				$panel_body.append($panel_top);
				$panel_body.append($panel_bottom);
				$html.append($panel_body);
				$show_context.html($html);
			}
			function jump(obj,url){
				$(obj).parents(".btns").find(".selected").removeClass("selected");
				$(obj).parent().addClass("selected");
				$(".content").find("iframe").attr("src",url);
			}
			$(function(){
				$("a").click(function(){
					var url = $(this).attr("href");
					$(".content").find("iframe").attr("src",url);
					alert(url);
					return false;
				});
				initCount();
				var height = $(".htmleaf-container").outerHeight(true);
				$(".content").find("iframe").css("height","calc(100% - "+height+"px)");
				$(window).resize(function() {
					var height = $(".htmleaf-container").outerHeight(true);
					$(".content").find("iframe").css("height","calc(100% - "+height+"px)");
				});
			});
		</script>
	</head>
	<body class="body_hiden">
		<div class="content htmleaf-container content_style">
			<div class="row row_outer">
				<div class="col-lg-6">
					<div class="row">
						<div class="col-xs-4 show_context">
						</div>
						<div class="col-xs-4 show_context">
						</div>
						<div class="col-xs-4 show_context">
						</div>
					</div>
				</div>
				<div class="col-lg-6">
					<div class="row">
						<div class="col-xs-4 show_context">
						</div>
						<div class="col-xs-4 show_context">
						</div>
						<div class="col-xs-4">
							<div class="btns">
								<div class="btn btnc">
									<input type="button" onclick="jump(this,webPath+'/secUserApptime/getListPage')" value="用户操作查询"/>
								</div>
								<div class="btn btnc">
									<input type="button" onclick="jump(this,webPath+'/secAuditInfoLog/getListPage')" value="角色变更"/>
								</div>
								<div class="btn btnc btnc_b_bottom selected">
									<input type="button" onclick="jump(this,webPath+'/secUserMarkInfo/getListPage')" value="用户登陆情况"/>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="content">
			<iframe  src=webPath+"/secUserMarkInfo/getListPage" border="0" style="border:none;height: calc(100% - 120px);margin: 0 3px; width: calc(100% - 6px);"></iframe>
		</div>
	</body>
</html>
