<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/appValue.js"></script>
		<script type="text/javascript" >
			var busPleId = '${busPleId}';
			var appSts = '${mfReceivablesRebateApp.appSts}';
			var rebateAppId = '${mfReceivablesRebateApp.rebateAppId}';
			$(function(){
// 				$(".scroll-content").mCustomScrollbar({
// 					advanced : {
// 						updateOnContentResize : true
// 					}
// 				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						updateOnContentResize : true
					}
				});
				//处理暂无数据的情况
				if($('#content tbody tr').length == 0){
				    var thCount = $('#content thead th').length;
					$('#content tbody').append('<tr><td style="text-align: center;" colspan="'+thCount+'">暂无数据</td></tr>');
				}
				if(appSts=="1"){//审批中
					//showWkfFlow($("#wj-modeler2"),rebateAppId);
					showWkfFlowVertical($("#wj-modeler2"),rebateAppId,"8","rebate_approval");
				}else{
					$("#spInfo-block").hide();
				}
			 });
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<!-- 列表上部的按钮区域（单独一行的形式），如有需要去掉注释，改为实际需要即可。
					<div class="btn-div">
						<button type="button" class="btn btn-primary" onclick="applyInsert();">进件</button>
					</div>
					-->
					<!-- 自定义筛选+智能搜索区域，参数请看说明。根据类型不同，在此页面看可以相应的调整布局。
					blockType：
						1——//头部只有自定义筛选的情况（无搜索框）
						2——//仅右侧有搜索框的情况，占3列。左侧由引用页面自定义
						3——//头部左侧自定义筛选，右侧搜索框的情况
						4——//头部左侧自定义筛选（无更多选项，财务模块在用），右侧有搜索框的情况
					placeholder：
						智能搜索框的提示信息内容。
					-->
					<div class="search-div">
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12">
					<div id="content" class="table_content" style="height: auto;">
						<dhcc:tableTag paginate="mfReceivablesRebateAppList" property="tablerecerebate0001" head="true" />
					</div>
				</div>
				<div class="col-md-12 approval-hist" id="spInfo-block">
						<div class="list-table">
						   <div class="title">
								 <span><i class="i i-xing blockDian"></i>审批历史</span>
								 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
						   </div>
						   <div class="content margin_left_15 collapse in " id="spInfo-div">
									<div class="approval-process">
										<div id="modeler1" class="modeler">
											<ul id="wj-modeler2" class="wj-modeler" isApp = "false">
											</ul>
										</div>
									</div>
						   </div>
						</div>
				</div>
			</div>
		</div>
	</body>
</html>
