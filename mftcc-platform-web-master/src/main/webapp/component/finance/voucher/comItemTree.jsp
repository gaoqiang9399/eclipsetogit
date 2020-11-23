<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	<meta name="renderer" content="webkit|ie-stand|ie-comp">
	<title>会计科目列表</title>
	<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/common.css" />
	<link rel="stylesheet" href="${webPath}/themes/factor/css/list.css" />
	<script type="text/javascript" src="${webPath}/component/risk/layer/layer.js"></script>
	<!-- 科目树 -->
	<link rel="stylesheet" href="${webPath}/UIplug/zTree/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${webPath}/UIplug/zTree/jquery.ztree.all.js"></script>
	<script src="js/common.js"></script>
	<style type="text/css">
		* {
			    -webkit-box-sizing: content-box;
			    -moz-box-sizing: content-box;
			    box-sizing: content-box;
			}
		/* subject-dialog */
		/**2017年7月14日  把以下代码从ui.min.css,filter.css抽离出来 */
.znsearch-div i {
    font-size: 14px;
    color: #D5D9E2;
    position: absolute;
    top: 11px;
    left: 7px;
    z-index: 10;
}

.znsearch-div .input-group-addon {
    border: 1px solid #32b5cb;
    background-color: #32b5cb;
    border-radius: 0px;
    color: #fff;
    padding: 6px 20px;
    cursor: pointer;
} 
.znsearch-div .form-control {
    border: 1px solid #32b5cb;
    border-radius: 0px;
    padding: 6px 12px 6px 24px;
    font-size: 12px;
}

.ui-tab li.cur {
    background-color: #00b8ec;
    font-weight: 700;
    border: 1px solid #00b8ec;
    color: #fff;
}

.ui-tab li {
    float: left;
    padding: 0 16px;
    height: 28px;
    border: 1px solid #cfcfcf;
    border-left: none;
    background-color: #fff;
    text-align: center;
    font-size: 14px;
    line-height: 28px;
    color: #555;
    cursor: pointer;
}	
/**2017年7月14日  把以下代码从ui.min.css,filter.css抽离出来 */	

		body{background-color:#fff;}
		.subject-dialog{width:437px;}
		.subject-dialog .hd{background-position:10;}
		.subject-dialog .cat{float:left;}
		.subject-dialog .cat .cur{color:#2383c0;}
		.subject-dialog .hd .add{float:right;}
		.subject-dialog .bd{ width:437px; height:460px;}
		
		#subject-category{border-left:0;}
		#subject-category li{
			width:28px;
			text-align:center;
			border-top:0;
			width:28px\9\0;
		}
		#subject-category .last{border-right:none;}
		
		#searchDiv {
		    padding: 5px;
			 -webkit-box-sizing: border-box;
		    -moz-box-sizing: border-box;
		    box-sizing: border-box;
		}
		#searchDiv input{
			 -webkit-box-sizing: border-box;
		    -moz-box-sizing: border-box;
		    box-sizing: border-box;
		}
	</style>
	<script type="text/javascript">
		var setting = {
				data: {
					simpleData: {
						enable: !0,
						idKey: "id",
						pIdKey: "parentId",
						rootPId: 0
					},
					key: {
						name: "showName"
					}
				},
				callback: {
					onDblClick: zTreeOnDblClick
				}
	
			};
			
		var zNodes;
	
		function getDataItem(){
			$.ajaxSettings.async = false;
			var dataParam = JSON.stringify($("#comitemForm").serializeArray());
			$.ajax({
				url : webPath+"/cwComItem/getComItemListForTreeAjax",
				data : {
					ajaxData:dataParam
					},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.flag == "success") {
						zNodes = data.items;
						$.fn.zTree.init($("#subject-tree"), setting, zNodes);
					} else {
						alert(data.msg, 0);
					}
				},
				error : function() {
				}
			});
		}
		
		function zTreeOnDblClick(event, treeId, treeNode) {
			var type = '${param.dataType }';	
			if(treeNode.isParent && type=='2'){
			}else{
				top.cwBackData = treeNode;//获取摘要返回父页面
				$(top.window.document).find("#showDialog .close").click();//关闭此页面
				//给单击进来的文本框赋值
			}
		};
	
	$(function(){	
		
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		//选项单击事件
		$("#subject-category li").click(function(){
			$(this).addClass("cur");
			$(this).siblings().removeClass("cur");
			var type = $(this).attr('data-type');
			$('#accType').val(type);
			getDataItem();
		});
		$("#subject-category li").eq(0).click();
	});
	</script>
</head>
<body>
<div class="subject-dialog">
	<form id="comitemForm">
		<div class="hidden">
				<!-- 0:查询全部；1：查询一级科目；2：查询明细科目；3：查询挂辅助核算科目 -->
				<input type="hidden" name="dataType" id="dataType" value="${param.dataType }">
				<input type="hidden" name="accType" id="accType" value="1">
		</div>
		<div id="searchDiv" class="col-xs-12 znsearch-div" >
			<div class="col-xs-5"></div>
			<div class="col-xs-7">
				<div  class="input-group pull-right">
					<i class="i i-fangdajing"></i>
					<input type="text" class="form-control" id="search" name="search" placeholder="智能搜索">
					<span class="input-group-addon" onclick="getDataItem()">搜索</span>
				</div>
			</div>
		</div>
	</form>
	<div class="hd cf">
		<ul class="ui-tab" id="subject-category">
			<li data-type="1" class="cur">资产</li>
			<li data-type="2">负债</li>
			<li data-type="4">权益</li>
			<li data-type="6">损益</li>
			<li data-type="3">共同</li>
			<li data-type="5">成本</li>
			<li data-type="7" class="last">表外</li>
		</ul>
	</div>
	<div class="bd scroll-content">
		<ul id="subject-tree" class="ztree"></ul>
	</div>
<!-- 	<div class="scroll-content">
		<ul id="subject-tree" class="ztree"></ul>
	</div> -->
</div>
</body>

</html>