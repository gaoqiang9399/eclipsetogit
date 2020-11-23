<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
<link rel="stylesheet" href="${webPath}/component/pss/stock/css/PssStock_common.css">
<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
<script type="text/javascript" >
	var isCheckAll = false;

	$(function(){
		//加载列表
		myCustomScrollbar({
		   	obj:"#content",//页面内容绑定的id
		   	url:webPath+"/pssCostAdjustBill/findByPageAjax",//列表数据查询的url
		   	tableId:"tablepsscostadjustbill0001",//列表数据查询的table编号
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
		
	});
			
	//获取 查询条件（方法名固定写法）
	/* function getFilterValArr(){ 
		return JSON.stringify($('#pssCABListForm').serializeArray());
	}; */
	
	//新增
	function addCAB(){
		top.isFresh = true;
		window.parent.openBigForm(webPath+'/pssCostAdjustBill/input', '成本调整单', function(){
			if(top.isFresh){
				updateTableData();//重新加载列表数据				
			}
		});
	};
	
	//批量删除
	function deleteBatch(){
		var costAdjustIds = getCheckedCAIds();
		if(costAdjustIds != ''){
			var url = webPath+'/pssCostAdjustBill/deleteCABBatchAjax';
			var dataParam = '[{"name":"costAdjustIds","value":"'+costAdjustIds+'"}]'; 
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
	
	//获取调拨单选择的调拨单ID
	function getCheckedCAIds(){
		var costAdjustIds = "";
        var costAdjustId = "";
        var vals=0;
        $(".table_content").find(':checkbox').each(function() {
        	if($(this).is(":checked")){
        		costAdjustId = $(this).val().substring(13);
        		if(costAdjustId != "" && costAdjustId != null){
            		costAdjustIds = costAdjustIds + "," + costAdjustId;
            		vals++;
        		}
        	}
        });
        if(vals==0){
        	alert(top.getMessage("FIRST_SELECT_FIELD","需要操作的调拨单"), 1);
        }else{
        	costAdjustIds = costAdjustIds.substring(1);
        }
        return costAdjustIds;
	};
	
	/* function my_Search(){
		updateTableData();
		isCheckAll = false;
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
	
	//详情
	function getCAB(url){
		top.isFresh = true;
		window.parent.openBigForm(url, '成本调整单', function(){
			if(top.isFresh){
				updateTableData();//重新加载列表数据				
			}
		});
	};

</script>
</head>
<body>
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div column">
					<div class="show-btn" style="float:left">
						<dhcc:pmsTag pmsId="pss-cost-adjust-insert">
							<button type="button" class="btn btn-primary" onclick="addCAB();">新增</button>
						</dhcc:pmsTag>
						<!-- <button type="button" class="btn btn-default" onclick="javascript:alert('建设中，敬请关注...',1);">导出</button> -->
						<dhcc:pmsTag pmsId="pss-cost-adjust-delete">
							<button type="button" class="btn btn-default" onclick="deleteBatch();">删除</button>
						</dhcc:pmsTag>
					</div>
				</div>
				<!-- 我的筛选选中后的显示块 -->
				<%-- <div class="search-div">
					<div class="col-xs-9 column mysearch-div" id="pills">
						<div class="mod-toolbar-top">
							<div class="left">
								<form id="pssCABListForm">
									<input class="items-btn" type="text" id="pssQuery" name="pssQuery" placeholder="单据号/备注" size="25">
									<span class="txt">日期：</span>
									<input class="items-btn pss-date" type="text" id="pssStartDate" name="pssStartDate" readonly value="${dataMap.pssStartDate }">
									<span class="txt">至</span>
									<input class="items-btn pss-date" type="text" id="pssEndDate" name="pssEndDate" readonly value="${dataMap.pssEndDate }">
									<!-- <span class="txt">仓库：</span>
									<select class="items-btn" name="storehouseId" id="storehouseId" autocomplete="off" style = "width:200px;"></select> -->
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
	}/* ,{
		"optCode" : "storehouseId",
		"optName" : "仓库",
		"parm" : ${pssStorehouseJsonArray},
		"dicType" : "y_n"
	} */
	];
</script>
</html>