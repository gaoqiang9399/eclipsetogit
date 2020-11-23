<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
	</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<form class="form-horizontal" role="form" id="cwListForm">
				<div class="col-md-12 column">
					<div class="search-div">
						<div class="col-xs-2 column">
							
								<div class="form-group">
										 <label for="firstname" class="col-sm-4 control-label">会计期间：</label>
										<div class="col-sm-4">
				 							<input type="text" class="form-control" name="week"  id="week" onclick="laydatemonth(this);">
				 							
										</div>
										<div class="col-sm-4">
											<input type="button" class="btn btn-info" onclick="searchData()" value="确定">
										</div>
								</div>
						
						</div>
						<div class="col-xs-7 column"></div>
						<div class="col-xs-3 column znsearch-div">
						<div class="input-group pull-right">
							<i class="i i-fangdajing"></i>
							<input type="text" class="form-control" id="filter_in_input" placeholder="智能搜索" name="search">
							<span class="input-group-addon" id="filter_btn_search">搜索</span>
						</div>
						</div>
				    </div>
			   </div>
			</form>
		  </div>
		<!--页面显示区域-->
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content cw_voucher_list"  style="height: auto;">
					<!--待定是否放置自定义table标签?-->
<%-- 					<dhcc:tableTag property="tablevoucher0001" paginate="CwVoucherMstList" head="true"></dhcc:tableTag> --%>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" >
		
			var myDate = new Date();
			var mon=myDate.getMonth()+1;
			if(mon<10){
				mon="0"+mon;
			}
			var searchWeek =myDate.getFullYear()+mon;
			$(function(){
				$("#week").val(searchWeek);
				initTable();
			});
			function initTable(){
				//加载列表
				myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/cwVoucherMst/getChongXiaoListAjax",//列表数据查询的url
			    	tableId:"tablechongxiao0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
// 			    	myFilter:false, //是否有我的筛选(列表列动态切换)
			    	pageSize:10,//加载默认行数(不填为系统默认行数)
			    	ownHeight:false,
			    	callback:function(){
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			}
			//获取 查询条件（方法名固定写法）
			function getFilterValArr(){ 
				return JSON.stringify($('#cwListForm').serializeArray());
			}
			function searchData(){
				initTable();
			}
			//弹窗查看凭证详情
			function toChongXiao(url){
				window.parent.openBigForm(url, '凭证冲销',closeCallBack);
			}
		//关闭弹窗
		function closeCallBack() {
			initTable();
		};
		</script>
</html>