<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/webPath.jsp" %>
<%
String path = request.getContextPath();
String blockDatas = (String)request.getAttribute("blockDatas");
String cellDatas = (String)request.getAttribute("cellDatas");
String fileName  = (String)request.getAttribute("fileName");
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="${webPath}/tech/layoutDesginer/css/style.css" />
			<link rel="stylesheet" type="text/css" href="${webPath}/themes/view/css/view-main.css"/>
		<link rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="${webPath}/tech/layoutDesginer/css/layoutDesginer.css" />
		<link rel="stylesheet" type="text/css" href="${webPath}/tech/layoutDesginer/css/toolbar.css" />
		<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="${webPath}/tech/layoutDesginer/js/freewall.js"></script>
		<link rel="stylesheet" href="${webPath}/layout/view/themes/iconfont/css/iconfont.css" />
		<link rel="stylesheet" href="${webPath}/tech/layoutDesginer/css/chartsQuery.css" />
		<link rel="stylesheet" href="${webPath}/tech/layoutDesginer/css/suofang.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/themes/css/menu.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/page/css/screen.css" />
		<script type="text/javascript" src="${webPath}/tech/layoutDesginer/js/customfw.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${webPath}/tech/layoutDesginer/js/layoutDesginer.js"></script>
		<script type="text/javascript" src="${webPath}/tech/layoutDesginer/js/layoutDesginer2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/layoutDesginer/js/echarts-all.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/myAlert.js"></script>
		<script type="text/javascript" src="${webPath}/tech/layoutDesginer/js/suofang.js"></script>
		<script type="text/javascript" src="${webPath}/tech/layoutDesginer/js/jquery.form.js"></script>
		<script type="text/javascript">
		var editSts = "add";
		var wall;
		var fileName = '<%=fileName%>';
		var webPath = '${webPath}';
		var cellDatas= '<%=cellDatas%>';
		var bdata= '<%=blockDatas%>';
		cellDatas = eval("("+cellDatas+")");
		bdata = eval("("+bdata+")");
		var configPath = "${webPath}/tech/layoutDesginer/config/config-charts.json";
		var optmenuData = {
		  title: '标题',
		  tooltip: '提示',
		  legend: '图例',
		  toolbox: '工具',
		  xAxis: 'X轴',
		  yAxis: 'Y轴',
		  series: '数据'
		};
		window.onload = function(){
			$("#openjsp").change(function(){
	            $("#openjspform").ajaxSubmit({  
	                success:function(data){
	                    cellDatas= data.cellDatas;
						bdata= data.blockDatas;
						fileName = data.fileName;
						reFreeWall(); 
	                }  
	            });  
			});
			initBtn();
			initFreeWall();
		};
		function initFreeWall(){
			wall = new Freewall("#freewall");
			wall.reset({
				draggable: true,
				selector: '.cell',
				cellW: 20,
				cellH: 20,
				animate: true,
				fixSize: 0,
				gutterX: 8,
				gutterY: 8,
				onResize: function() {
					wall.refresh();
				}
			});
			wall.fillHoles();
			wall.fitWidth();
		}
		function reFreeWall(){
			if(bdata!="null"||cellDatas!="null"){
				var $freewall = $("#freewall");
				$freewall.html("");
				editSts = "update";
				blockDatas = bdata;
				for(var i in cellDatas){
					var trash = '<b class="cC0 nui-ico nui-ico-smartTrash tableTrash" sign="trash"><b class="nui-ico nui-ico-smartTrash-head"></b><b class="nui-ico nui-ico-smartTrash-body"></b></b>';
					var temp = "<div class='cell fw-float' cellid='"+cellDatas[i].cellid+"' style=' top :" + cellDatas[i].top + "; left :" + cellDatas[i].left + "; width:"+cellDatas[i].width+"; height: "+cellDatas[i].height+"; background-color: #EBEBEB; z-index:100' data-handle='.handle'><div class='cover'><div class='handle-line handle'><span>"+cellDatas[i].name+"</span><i class='i i-xuanzhuan1 jt-down'></i><i class='smartTrash jt-down'>"+trash+"</i></div></div><i class='bottomright'></i><div class='info'></div></div>";
					$freewall.append(temp);
					if(blockDatas[cellDatas[i].cellid].plugintype=="charts"){
						var chart = blockDatas[cellDatas[i].cellid].chart;
						var myChart = prodChart($("body .fw-float"),chart);
						chart.chartObj = myChart;
					}
					onDrop($("body .fw-float"), wall);
				}
				wall.refresh();
			}
		}
		function trigger(btn){
			var $btn = $(btn);
			if($btn.attr("name")=="open"){
				parent.$('.middle-north').trigger('layoutpaneclose');
				$btn.attr("name","close");
			}else{
				parent.$('.middle-north').trigger('layoutpaneopen');
				$btn.attr("name","open");
			}
		}
		
		function initBtn(){
				$("#isViewPoint").click(function(){
					if($(this).hasClass("active")){
						$(this).removeClass("active")
						$(this).find("span").attr("class","text");
						$("#wrapper").hide();
						$(".layout").css("top","48px");
					}else{
						$(this).addClass("active");
						$(this).find("span").attr("class","text-active");
						$("#wrapper").show();
						$(".layout").css("top","82px");
					}
				});
		}
		function showScorll(){
			if($("#freewall").css("overflow")=="hidden"){
				$("#freewall").css("overflow","auto");
			}else{
				$("#freewall").css("overflow","hidden");
			}
		}
		</script>
		<style type="text/css">
			.bottomright {
				border-bottom: 9px solid #c1c1c1;
			    border-left: 9px solid transparent;
			    bottom: 4px;
			    cursor: se-resize;
			    position: absolute;
			    right: 4px;
			    z-index: 10;
			}
			.jt-down{
				float: right;
				cursor: pointer;
				margin-right: 5px;
			}
			.fieldShow{
				width:50px !important;
			}
		</style>
	</head>

	<body onselectstart="return false">
		<div class="D-toolbar">
			<div style="position: absolute; width: 100%; top: 0px;">
			<!-- 拖拽菜单 -->
		    	<ul class="rightLine">
		    		<div class="D-header" style="background-color: #1d9c73;">控件</div>
		        	<li><a id="taddCell" class="D-toolbtn"><i class="i i-objectgroup"></i>拖拽添加</a></li>
		            <li><a class="D-toolbtn"><i class="Tico-skin"></i></i>换肤</a></li>
		            <li><a class="D-toolbtn"><i class="Tico-trp"></i>标签</a></li>
		        </ul>
		        <ul class="rightLine">
		        	<div class="D-header" style="background-color: #FFAA25;">控件</div>
		        	<li><a class="D-toolbtn"><i class="Tico-add"></i>拖拽添加</a></li>
		        </ul>
		        <ul class="rightLine">
		        	<div class="D-header" style="background-color: #7DCCF6;">全局设置</div>
		        	<li>
						<button id = "isViewPoint" class="btn btn-small btn-white" data-toggle="class:show">
							<span class="text">显示视角</span>
						</button>
					</li>
		        </ul>
		        <div class="toolBtn">
		        
		        	<button class="toolBtn-light" onclick="showScorll()" name="open">滚动轴开关</button>
		        	<button class="toolBtn-light" onclick="trigger(this)" name="open">折叠</button>
		        	<button class="toolBtn-light" onclick="$('#openjsp').click();">打开</button>
		        	<form id="openjspform" action="openDesginer.action" 
              			enctype="multipart/form-data" method="post" style="display:none;">
		        		<input type="file" id="openjsp" name="upload" accept=".jsp"/>
		        	</form>
		        	<button id="save" class="toolBtn-dark">保存</button>
		        </div>
		        <!-- 配置菜单 -->
		        
	        </div>
	    </div>
	    <!-- 视角 -->
	    <div id="wrapper" style="display:none;">
			<ul class="lavaLamp">
				<li class="current-cat">
					<a onclick="return false" href="">菜单一</a>
				</li>
				<li>
					<a onclick="return false" href="">菜单二</a>
				</li>
				<li>
					<a onclick="return false" href="">菜单三</a>
				</li>
				<li class="back" style="left: 0px; width: 100px; overflow: hidden;">
					<div class="left"></div>
				</li>
			</ul>
			<div class="app-screen">
				<button class="drop-down i i-jiantou8" type="button"></button>
				<input type="button" value="业务变更">
				<input type="button" value="资料上传">
				<input type="button" value="业务提交">
			</div>
		</div>
		<!--cell-->
		<div class="layout">
			<div id="freewall" class="free-wall-sheji">
			</div>
			<section class="overlay" id="overlay">
	            <button id="save_block" class="close"><span class="i i-x2"></span></button>
	            <div class="cell-config">
            		<div class="tabbable" id="tabs-88426">
						<ul class="nav nav-tabs">
							<li class="active">
								<a href="#panel-405459" data-toggle="tab">参数</a>
							</li>
							<li>
								<a href="#panel-895581" data-toggle="tab">控件</a>
							</li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="panel-405459">
								<table class="table table-hover table-plugin">
									<tr>
										<td width="15%">模块编号</td>
										<td width="35%" class="filter_col_input">
											<input type="text" class="form-control" name="cellid" />
										</td>
										<td width="15%">模块名称</td>
										<td width="35%" class="filter_col_input">
											<input type="text" class="form-control" name="cellname" />
										</td>
									</tr>
									<tr>
										<td>模块类型</td>
										<td class="filter_col_input">
											<input type="text" class="form-control" name="celltype" />
										</td>
										<td>模块状态</td>
										<td class="filter_col_input">
											<input type="text" class="form-control" name="cellsts" />
										</td>
									</tr>
									<tr>
										<td>不显示条件</td>
										<td class="filter_col_input">
											<input type="text" class="form-control" name="dispalySts" />
										</td>
									</tr>
								</table>
							</div>
							<div class="tab-pane" id="panel-895581">
								<table class="table table-hover table-plugin">
									<tr>
										<td width="10%">控件类型</td>
										<td width="35%" class="filter_col_input">
											<div class="btn-group fltype-group">
												<button class="btn btn-default dropdown-toggle" name="plugintype" data-toggle="dropdown">
													<span>选择控件类型</span>
													<span class="caret"></span>
												</button>
												<ul class="dropdown-menu plugin-group">
													<li>
														<a name="form">表单</a>
													</li>
													<li>
														<a name="charts">图形图表</a>
													</li>
												</ul>
											</div>
										</td>
										<td width="20%"></td>
										<td width="35%" class="filter_col_input">
										</td>
									</tr>
								</table>
									<div class="plugin-query">
										<div class="cpt-tab">
											<ul class="dtm-tab0">
											</ul>
											<div class="dtm-tab0-coni">
												<div class="cpt-tab" name="title">
													<table class="table table-hover">
														<tr>
															<td width="15%">主标题文本</td>
															<td width="25%" class="filter_col_input">
																<input type="text" class="form-control" value="" name="text"/>
															</td>
															<td width="15%">副标题文本</td>
															<td width="25%" class="filter_col_input">
																<input type="text" class="form-control" value="" name="subtext"/>
															</td>
														</tr>
													</table>
												</div>
												<div class="cpt-tab" name ="tooltip" style="display: none;">
													<table class="table table-hover">
														<tr>
															<td width="15%">是否使用提示</td>
															<td width="25%" class="filter_col_input">
																<input type="radio" name="tooltip-show" value="true">是
																<input type="radio" name="tooltip-show" value="false">否
															</td>
															<td width="15%">格式模板</td>
															<td width="25%" class="filter_col_input">
																<input type="text" class="form-control" name="formatter" value="" readonly="readonly"/>
															</td>
														</tr>
													</table>
												</div>
												<div class="cpt-tab" name="legend" style="display: none;">
													<table class="table table-hover">
														<tr>
															<td width="15%">是否显示图例</td>
															<td width="25%" class="filter_col_input">
																<input type="radio" name="legend-show" value="true">是
																<input type="radio" name="legend-show" value="false">否
															</td>
															<td width="15%"> 图例选择模式</td>
															<td width="25%" class="filter_col_input">
																<input type="radio" name="selectedMode" value="false">不可选
																<input type="radio" name="selectedMode" value="single">单选
																<input type="radio" name="selectedMode" value="multiple">多选
															</td>
														</tr>
													</table>
												</div>
												<div class="cpt-tab" name="toolbox" style="display: none;">
													<table class="table table-hover">
														<tr>
															<td width="15%">是否显示工具箱</td>
															<td width="25%" class="filter_col_input">
																<input type="radio" name="toolbox-show" value="true">是
																<input type="radio" name="toolbox-show" value="false">否
															</td>
															<td width="15%">
															</td>
															<td width="25%" class="filter_col_input">
															</td>
														</tr>
														<tr>
															<td width="15%">功能按钮设定</td>
															<td width="25%" class="filter_col_input" colspan="3">
																<!--<input type="checkbox" name="feature" value="mark">绘制辅助线
																<input type="checkbox" name="feature" value="dataZoom">选区缩放-->
																<input type="checkbox" name="feature" value="dataView">切换至数据视图
																<input type="checkbox" name="feature" value="restore">还原
																<input type="checkbox" name="feature" value="saveAsImage">保存为图片
															</td>
														</tr>
													</table>
												</div>
												<div class="cpt-tab" name="xAxis" style="display: none;">
													<table class="table table-hover">
														<tr>
															<td width="15%">坐标轴类型</td>
															<td width="25%" class="filter_col_input">
																<input type="radio" name="xAxis-type" value="category">类目型
																<input type="radio" name="xAxis-type" value="value">数值型
																<input type="radio" name="xAxis-type" value="time">时间型
															</td>
															<td width="15%">坐标轴名称</td>
															<td width="25%" class="filter_col_input">
																<input type="text" class="form-control" name="name"/>
															</td>
														</tr>
													</table>
												</div>
												<div class="cpt-tab" name="yAxis" style="display: none;">
													<table class="table table-hover">
														<tr>
															<td width="15%">坐标轴类型</td>
															<td width="25%" class="filter_col_input">
																<input type="radio" name="yAxis-type" value="category">类目型
																<input type="radio" name="yAxis-type" value="value">数值型
																<input type="radio" name="yAxis-type" value="time">时间型
															</td>
															<td width="15%">坐标轴名称</td>
															<td width="25%" class="filter_col_input">
																<input type="text" class="form-control" name="name"/>
															</td>
														</tr>
													</table>
												</div>
												<div class="cpt-tab" name="series" style="display: none;">
													<table class="table table-hover">
														<tr>
															<td width="15%">数据URL</td>
															<td width="25%" class="filter_col_input" colspan="3">
																<input type="text" class="form-control" name="url"/>
															</td>
														</tr>
														<tr>
															<td width="15%">数据标线类型</td>
															<td width="25%" class="filter_col_input" colspan="3">
																<input type="radio" checked="checked" name="markLine" value="false"><span>无标线</span>
																<input type="radio" name="markLine" value="max"><span>最大值</span>
																<input type="radio" name="markLine" value="min"><span>最小值</span>
																<input type="radio" name="markLine" value="average"><span>平均值</span>
															</td>
														</tr>
													</table>
												</div>
											</div>
										</div>
									</div>
							</div>
						</div>
					</div>
	            </div>
    	    </section>
		</div>
	</body>

</html>