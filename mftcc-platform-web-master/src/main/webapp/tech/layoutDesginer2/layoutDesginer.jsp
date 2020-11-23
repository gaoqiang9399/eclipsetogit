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
		<link rel="stylesheet" type="text/css" href="${webPath}/tech/layoutDesginer2/css/style.css" />
		<link rel="stylesheet" href="${webPath}/themes/bootstrap/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="${webPath}/tech/layoutDesginer2/css/layoutDesginer.css" />
		<link rel="stylesheet" type="text/css" href="${webPath}/tech/layoutDesginer2/css/toolbar.css" />
		<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="${webPath}/tech/layoutDesginer2/js/freewall.js"></script>
		<link rel="stylesheet" href="${webPath}/layout/view/themes/Font-Awesome/css/font-awesome.min.css" />
		<link rel="stylesheet" href="${webPath}/tech/layoutDesginer2/css/chartsQuery.css" />
		<link rel="stylesheet" href="${webPath}/tech/layoutDesginer2/css/suofang.css" />
		<script type="text/javascript" src="${webPath}/tech/layoutDesginer2/js/customfw.js"></script>
		<script type="text/javascript" src="${webPath}/themes/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${webPath}/tech/layoutDesginer2/js/layoutDesginer.js"></script>
		<script type="text/javascript" src="${webPath}/tech/layoutDesginer2/js/layoutDesginer2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/layoutDesginer2/js/echarts-all.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/myAlert.js"></script>
		<script type="text/javascript" src="${webPath}/tech/layoutDesginer2/js/suofang.js"></script>
		<script type="text/javascript">
		var editSts = "add";
		var fileName = '<%=fileName%>';
		var webPath = '${webPath}';
		var configPath = "${webPath}/tech/layoutDesginer2/config/config-charts.json";
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
			var cellDatas= '<%=cellDatas%>';
			var bdata= '<%=blockDatas%>';
			var $freewall = $("#freewall");
			if(bdata!="null"||cellDatas!="null"){
				cellDatas = eval("("+cellDatas+")");
				bdata = eval("("+bdata+")");
				editSts = "update";
				blockDatas = bdata;
				for(var i in cellDatas){
					var temp = "<div class='cell fw-float' cellid='"+cellDatas[i].cellid+"' style=' top :" + cellDatas[i].top + "; left :" + cellDatas[i].left + "; width:"+cellDatas[i].width+"; height: "+cellDatas[i].height+"; background-color: #EBEBEB; z-index:100' data-handle='.handle'><div class='cover'><div class='handle'><span>"+cellDatas[i].name+"</span><i class='icon-cogs jt-down'></i></div></div><i class='bottomright'></i><div class='info'></div></div>";
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
		</script>
	</head>

	<body onselectstart="return false">
		<div class="D-toolbar">
			<div style="position: absolute; width: 100%; top: 0px;">
			<!-- 拖拽菜单 -->
		    	<ul class="rightLine">
		    		<div class="D-header" style="background-color: #1d9c73;">控件</div>
		        	<li><a id="taddCell" class="D-toolbtn"><i class="fa fa-object-group"></i>拖拽添加</a></li>
		            <li><a class="D-toolbtn"><i class="Tico-skin"></i></i>换肤</a></li>
		            <li><a class="D-toolbtn"><i class="Tico-trp"></i>标签</a></li>
		        </ul>
		        <ul class="rightLine">
		        	<div class="D-header" style="background-color: #FFAA25;">控件</div>
		        	<li><a class="D-toolbtn"><i class="Tico-add"></i>拖拽添加</a></li>
		        </ul>
		        <div class="toolBtn">
		        	<button class="toolBtn-light">打开</button>
		        	<button id="save" class="toolBtn-dark">保存</button>
		        </div>
		        <!-- 配置菜单 -->
		        
	        </div>
	    </div>
		<!--cell-->
		<div class="layout">
			<div id="freewall" class="free-wall-sheji">
			</div>
			<section class="overlay" id="overlay">
	            <button id="save_block" class="close"><span class="fa fa-remove"></span></button>
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