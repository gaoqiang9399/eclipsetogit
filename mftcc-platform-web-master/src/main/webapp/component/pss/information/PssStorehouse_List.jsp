<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%-- <link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script> --%>
<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
<script type="text/javascript">
	$(function(){
		//加载列表
		myCustomScrollbar({
			obj:"#content",//页面内容绑定的id
			url:webPath+"/pssStorehouse/getListPageAjax",//列表数据查询的url
			tableId:"tablepssstorehouse0001",//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			pageSize:12,//加载默认行数(不填为系统默认行数)
			topHeight:100,//顶部区域的高度，用于计算列表区域高度。
			callback:function(){
			    		$("table").tableRcswitcher({
			    		name:"flag",onText:"启用",offText:"停用"});
			    	}//方法执行完回调函数（取完数据做处理的时候）
		});
	    addCheckAllEvent(); 
	});
	
	function getDetailPage(url){
		top.openBigForm(url,"详情",updateTableData);			
	};
		
	//批量删除
	function deleteBatch(){
		var alloTransIds = getCheckedATIds();
		if(alloTransIds != ''){
			var url = webPath+'/cwVoucherMst/deleteBatchAjax';
			var dataParam = '[{"name":"voucherNos","value":"'+alloTransIds+'"}]'; 
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						if(data.data.failed !=''){
							alert(top.getMessage("FAILED_DELETE") + '凭证'+data.data.failed ,2);
						}else{
							alert(top.getMessage("SUCCEED_OPERATION") ,1);
						}
						 updateTableData();//重新加载列表数据
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(data){
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	};
		
	//去除表头 点击事件 换成 全选事件
	var isCheckAll = false;
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

	function addStorehouse(){
		//window.location = webPath+'/PssStorehouseAction_getInputPage.action';
		top.openBigForm(webPath+"/pssStorehouse/getInputPage","新增",updateTableData);
	};
</script>
</head>
<body>
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div column">
					<div class="show-btn" style="float:left">
						<dhcc:pmsTag pmsId="pss-storehouse-insert">
							<button type="button" class="btn btn-primary" onclick="addStorehouse();">新增</button>
						</dhcc:pmsTag>
					</div>
				</div>
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder=仓库名称/仓库地址"/>
				</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div id="content" class="table_content" style="height: auto;"></div>
	</div>
</body>
</html>