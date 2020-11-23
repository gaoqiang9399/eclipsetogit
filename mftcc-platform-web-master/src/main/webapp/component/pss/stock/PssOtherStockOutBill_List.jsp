<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
<link rel="stylesheet" href="${webPath}/component/pss/stock/css/PssStock_common.css">
<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
<script type="text/javascript" >
	var isCheckAll = false;
	var basePath = '<%=basePath %>';
	
	$(function(){
		//加载列表
		myCustomScrollbar({
		   	obj:"#content",//页面内容绑定的id
		   	url:webPath+"/pssOtherStockOutBill/findByPageAjax",//列表数据查询的url
	    	tableId:"tablepssotherstockoutbill0001",//列表数据查询的table编号
		   	tableType:"thirdTableTag",//table所需解析标签的种类
		   	pageSize:30,//加载默认行数(不填为系统默认行数)
		   	topHeight:100,//顶部区域的高度，用于计算列表区域高度。
		   	callback : function(){
				isCheckAll = false;
				
				//停止checkBox事件冒泡
				$("#tablist>tbody tr").find(':checkbox').bind('click', function(event) {
					event.stopPropagation();
				});
			}
		});
	    addCheckAllEvent();
	    
	    $('.pss-date').on('click', function(){
			fPopUpCalendarDlg({
				isclear: true,
				/* min: currDate.substring(0, 8) + '01 00:00:00', //最小日期
				max: currDate + ' 23:59:59', //最大日期 */
				choose:function(data){
				}	
			});
		});
	    
	    $('#pssImpATB').hover(function(){
	    	$('#pssImpATB').find('button:eq(0) span').addClass('triangle-up');
	    	$('#pssImpATB').find('button:eq(0) span').removeClass('triangle-down');
	    	$('#pssHideImpATB').addClass('pss-imp-left').addClass('pss_block');
	    },function(){
	    	$('#pssImpATB').find('button:eq(0) span').addClass('triangle-down');
	    	$('#pssImpATB').find('button:eq(0) span').removeClass('triangle-up');
	    	$('#pssHideImpATB').removeClass('pss-imp-left').removeClass('pss_block');
	    });
	    
	    $('#pssChkATB').hover(function(){
	    	$('#pssChkATB').find('button:eq(0) span').addClass('triangle-up');
	    	$('#pssChkATB').find('button:eq(0) span').removeClass('triangle-down');
	    	$('#pssHideChkATB').addClass('pss-chk-left').addClass('pss_block');
	    },function(){
	    	$('#pssChkATB').find('button:eq(0) span').addClass('triangle-down');
	    	$('#pssChkATB').find('button:eq(0) span').removeClass('triangle-up');
	    	$('#pssHideChkATB').removeClass('pss-chk-left').removeClass('pss_block');
	    });
		
	});
			
	//获取 查询条件（方法名固定写法）
	/* function getFilterValArr(){ 
		return JSON.stringify($('#pssOSOBListForm').serializeArray());
	}; */
			
	//批量审核
	function checkPssOSOB(){
		var otherStockOutIds = getCheckedOSOIds();
		if(otherStockOutIds != ''){
			var url = webPath+'/pssOtherStockOutBill/checkOSOBBatchAjax';
			var dataParam = '[{"name":"otherStockOutIds","value":"'+otherStockOutIds+'"}]';
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						if(data.data.failed !=''){
							alert(data.data.failed ,2);
						}else{
							alert(top.getMessage("SUCCEED_OPERATION") ,1);
						}
						 updateTableData();//重新加载列表数据
						 isCheckAll = false;
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(data){
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	};
			
	//批量反审核
	function reCheckPssOSOB(){
		var otherStockOutIds = getCheckedOSOIds();
		if(otherStockOutIds != ''){
			var url = webPath+'/pssOtherStockOutBill/reCheckOSOBBatchAjax';
			var dataParam = '[{"name":"otherStockOutIds","value":"'+otherStockOutIds+'"}]';
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						if(data.data.failed !=''){
							alert(data.data.failed ,2);
						}else{
							alert(top.getMessage("SUCCEED_OPERATION") ,1);
						}
						 updateTableData();//重新加载列表数据
						 isCheckAll = false;
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(data){
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	};
			
	//批量删除
	function deleteBatch(){
		//alert('建设中，敬请关注...',1);
		//return;
		var otherStockOutIds = getCheckedOSOIds();
		if(otherStockOutIds != ''){
			var url = webPath+'/pssOtherStockOutBill/deleteOSOBBatchAjax';
			var dataParam = '[{"name":"otherStockOutIds","value":"'+otherStockOutIds+'"}]';
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						if(data.data.failed !=''){
							alert(data.data.failed ,2);
						}else{
							alert(top.getMessage("SUCCEED_OPERATION") ,1);
						}
						 updateTableData();//重新加载列表数据
						 isCheckAll = false;
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(data){
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	};
	
	/* function my_Search(){
		updateTableData();
		isCheckAll = false;
	};
	
	function senior_Search(){
		alert('建设中，敬请关注...',1);
	}; */
			
	//去除表头 点击事件 换成 全选事件
	function addCheckAllEvent() {
		 $(".table-float-head").delegate("th:first-child","click",function(){
			if (isCheckAll) {
				$(".table_content").find(':checkbox').each(function() {
					this.checked = false;
				});
				isCheckAll = false;
			} else {
				$(".table_content").find(':checkbox').each(function() {
					this.checked = true;
				});
				isCheckAll = true;
			}
		});
	};
	
	//获取其他出库单选择的其他出库单ID
	function getCheckedOSOIds(){
		var otherStockOutIds = "";
        var otherStockOutId = "";
        var vals=0;
        $(".table_content").find(':checkbox').each(function() {
        	if($(this).is(":checked")){
        		otherStockOutId = $(this).val().substring(16);
        		if(otherStockOutId != "" && otherStockOutId != null){
            		otherStockOutIds = otherStockOutIds + "," + otherStockOutId;
            		vals++;
        		}
        	}
        });
        if(vals==0){
        	alert(top.getMessage("FIRST_SELECT_FIELD","需要操作的其他出库单"), 1);
        }else{
        	otherStockOutIds = otherStockOutIds.substring(1);
        }
        return otherStockOutIds;
	};
	
	//新增
	function addOSOB(){
		top.isFresh = true;
		window.parent.openBigForm(webPath+'/pssOtherStockOutBill/input', '新增其他出库单', function(){
			if(top.isFresh){
				updateTableData();//重新加载列表数据				
			}
		});
	};
	
	//导入
	function exportPssOSOB(){
		alert('建设中，敬请关注...',1);
	};
	
	//导出
	function importPssOSOB(){
		alert('建设中，敬请关注...',1);
	};
	
	//详情
	function getOSOB(url){
		top.isFresh = true;
		window.parent.openBigForm(url, '查看其他出库单', function(){
			if(top.isFresh){
				updateTableData();//重新加载列表数据				
			}
		});
	};
	
	var batchPrintBill = function(){		
		var otherStockOutIds = getCheckedOSOIds();
		if (otherStockOutIds != '') {
			
			window.top.alert("请确认是否打印其他出库单<br/>如果未安装打印插件,请点击<a href=\"javascript:void(0);\" onclick=\"window.location.href = webPath+'/pssPrintBill/downLoadPrintPlugin';\">下载</a>", 2, function() {
				var fileNamePrefix = "'QTCKD'";
				var templateFileName = "'templateFile_pssqtckd_batch.doc'";
				var printBizType = "'QTCKD'";
				window.location.href = 'PageOffice://|'+basePath+'component/pss/batchPrint/batchPrintEntrace.jsp?printBizType='+printBizType+'&templateFileName='+templateFileName+'&fileNamePrefix='+fileNamePrefix+'&pssBillsJson='+encodeURIComponent(JSON.stringify(otherStockOutIds.split(",")))+'|width=1200px;height=800px;||';
			});
			
		}
	};
	
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<div class="show-btn" style="float:left">
						<dhcc:pmsTag pmsId="pss-other-stock-out-insert">
							<button type="button" class="btn btn-primary" onclick="addOSOB();">新增</button>
						</dhcc:pmsTag>
						<dhcc:pmsTag pmsId="pss-other-stock-out-check">
							<span id="pssChkATB">
								<button type="button" class="btn btn-default" onclick="checkPssOSOB();">
									审核
									<span class="triangle-down"></span>
								</button>
								<button id="pssHideChkATB" type="button" class="pss-hide-btn" onclick="reCheckPssOSOB();">
									反审核
								</button>
							</span>
						</dhcc:pmsTag>
						<dhcc:pmsTag pmsId="pss-other-stock-out-print">
							<button type="button" class="btn btn-default" onclick="batchPrintBill();">打印</button>
						</dhcc:pmsTag>
						<%-- <dhcc:pmsTag pmsId="pss-other-stock-out-export">
							<span id="pssImpATB">
								<button type="button" class="btn btn-default" onclick="exportPssOSOB();">
									导入
									<span class="triangle-down"></span>
								</button>
								<button id="pssHideImpATB" type="button" class="pss-hide-btn" onclick="importPssOSOB();">
									导出
									<span class="triangle-none"></span>
								</button>
							</span>
						</dhcc:pmsTag> --%>
						<dhcc:pmsTag pmsId="pss-other-stock-out-delete">
							<button type="button" class="btn btn-default" onclick="deleteBatch();">删除</button>
						</dhcc:pmsTag>
					</div>
				</div>
				<!-- 我的筛选选中后的显示块 -->
				<%-- <div class="search-div">
					<div class="col-xs-9 column mysearch-div" id="pills">
						<div class="mod-toolbar-top">
							<div class="left">
								<form id="pssOSOBListForm">
									<input class="items-btn" type="text" id="pssQuery" name="pssQuery" placeholder="单据号/备注" size="25">
									<span class="txt">日期：</span>
									<input class="items-btn pss-date" type="text" id="pssStartDate" name="pssStartDate" readonly value="${dataMap.pssStartDate }">
									<span class="txt">至</span>
									<input class="items-btn pss-date" type="text" id="pssEndDate" name="pssEndDate" readonly value="${dataMap.pssEndDate }">
									<a class="ui-btn" onclick="senior_Search();" id="senior_psssearch">高级搜索</a>
									<a class="ui-btn" onclick="my_Search();" id="psssearch">查询</a>
								</form>
							</div>
						</div>
					</div>
				</div> --%>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=单据号/备注" />
				</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content" style="height: auto;"></div>
			</div>
		</div>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript">
	/*我的筛选加载的json*/
	filter_dic = [ {
		"optName" : "单据日期",
		"parm" : [],
		"optCode" : "billDate",
		"dicType" : "date"
	},{
		"optCode" : "auditSts",
		"optName" : "审核状态",
		"parm" : ${pssAuditStsedJsonArray},
		"dicType" : "y_n"
	}
	];
</script>
</html>