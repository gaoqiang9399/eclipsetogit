<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
	.pad0{
			padding: 0 !important;
	}
	.editbox {
		width: 100%;
		border: 1px solid #ddd;
		height: 39px;
		line-height: 39px;
		padding: 6px 4px;
	}
	
	.editbox:focus {
		border: 1px solid #00b8ec;
	}
</style>
<title>列表</title>
<script type="text/javascript">
	var corrType = "9";
	$(function() {
		myCustomScrollbar({
			obj : "#content", //页面内容绑定的id
			url:webPath+"/cwVchOutCorrbind/findByPageAjax", //列表数据查询的url
			tableId : "tablecorrbind0001", //列表数据查询的table编号
			tableType : "thirdTableTag", //table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
	    	callback:function(options,data){
//					$("#tablist td[mytitle]:contains('...')").initMytitle();	
				//显示列参考DemoSpecial_List.jsp
				corrType = getMyFilterVal();
				var tit = "0"==corrType ? "typeNo,typeName,itemNo,itemName,corrNo" : "typeNo,itemNo,itemName,corrNo";
				$(".search-title").find("span").attr("value", tit);
				showTable(false, '');
				addTDEvent();
	    	}//方法执行完回调函数（取完数据做处理的时候）
		});
	});
	
	//获取筛选选中的值，不存在默认为1
	function getMyFilterVal(){
		$(".filter-val").each(function(){
	    	var myfilter = eval($(this).find("input[type=hidden]").val());
			if(myfilter!=""){
				corrType = myfilter[0].value;
			}
		});
		return corrType;
	}
	
	function addTDEvent(){
		$("#tablist>tbody").on('click', 'td', function(){
			var tdIndex = $(this).index();
			var text = $(this).text().replace(/(^\s*)|(\s*$)/g, "");
			var inputCount = $(this).children("input").length;
			if(inputCount == 0) {
				var inputStr;
				if(tdIndex == 3){
					inputStr = $("<input type='text' class='editbox' onkeydown='enterKey();' autocomplete='off'>");
					$(this).html(inputStr).addClass('pad0');
					$(this).children('.editbox').focus().val(text);
				}
			}else{
				$(this).children('.editbox').focus();
			}
		});
		
		//光标离开事件，触发保存
		$("#tablist>tbody").on('blur', '.editbox', function(){
			var trObj = $(this).parents('tr');
			var val = $(this).val().replace(/(^\s*)|(\s*$)/g, "");
			var corrObj = trObj.find('input[name=corrNo]');
			var	oldVal = $(corrObj).val();
			$(this).parent().html(val).removeClass('pad0');
			if(oldVal != undefined && val !='' && oldVal != val){
				$(corrObj).val(val);
				updateCorrBindVal(trObj);
			}
		});
		
		
		//保存日记账数据
		function updateCorrBindVal(trObj){
			jQuery.ajax({
				url:webPath+'/cwVchOutCorrbind/updateCorrBindAjax',
				data:{ajaxData: JSON.stringify(trObj.find('input').serializeArray())},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						alert(top.getMessage("SUCCEED_UPDATE"), 1);
						updateTableData();//重新加载列表数据
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(data){
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	}
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
				<div class="search-title hidden"><span value=""></span></div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder=编号/名称" />
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12">
				<div id="content" class="table_content" style="height: auto;"></div>
			</div>
		</div>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript">
	filter_dic = [ {
		"optCode" : "corrType",
		"optName" : "关系类型",
		"parm" : [ {
			"optName" : "科目设置",
			"optCode" : "9"
		}, {
			"optName" : "辅助项目设置",
			"optCode" : "0"
		}, {
			"optName" : "凭证字设置",
			"optCode" : "1"
		}, {
			"optName" : "现金流量设置",
			"optCode" : "2"
		}, {
			"optName" : "帐套设置",
			"optCode" : "3"
		}, ],
		"dicType" : "y_n"
	} ];
</script>
</html>
