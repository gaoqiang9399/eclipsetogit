<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/rateNorm/findByPageAjax",//列表数据查询的url
			    	tableId:"tablenmd0003",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
			 
			 function getBigPage(url){
			 	window.top.openBigForm(url,"基准利率",function() {
					window.top.cloesBigForm(window,"","iframepage");
				});
			 }
		</script>
	</head>
	<body>
		<div>
			<div style="vertical-align: bottom;display: block;" class="tabCont">
				<strong >基准利率</strong>
				<div class="search-group">
					<div class="search-lable" id="pills">
							<!--我的筛选按钮-->
							<dhcc:thirdButton value="新增" action="RateNorm001" onclick="window.top.openBigForm('${webPath}/rateNorm/input','基准利率新增')"></dhcc:thirdButton>
						<div class="filter-btn-group">
							<!--自定义查询输入框-->
							<input placeholder="智能搜索"  id="filter_in_input"  class="filter_in_input" type="text" />
							<div class="filter-sub-group">
								<button id="filter_btn_search"  class="filter_btn_search"  type="button" ><i class="i i-fangdajing"></i></button>
								<button id="fiter_ctrl_btn" class="filter_btn_myFilter" type="button"  ><i class="fa fa-caret-down"></i></button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;">
			<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
		</div>
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>	
	<script type="text/javascript">
			addDefFliter("0", "生效标识", "sts", "RATE_STS", "0,1,2");
			addDefFliter("0", "利率类型", "rateType", "RATE_TYPE", "1,2,3");
	</script>
	<script type="text/javascript">filter_dic =[{"optCode":"rateName","optName":"中文说明","dicType":"val"},{"optCode":"begDate","optName":"起始日期","dicType":"date"},{"optCode":"endDate","optName":"到期日期","dicType":"date"},{"optCode":"rateType","optName":"利率类型","parm":[{"optName":"年利率","optCode":"1"},{"optName":"月利率","optCode":"2"},{"optName":"日利率","optCode":"3"}],"dicType":"y_n"},{"optCode":"rateVal","optName":"基准利率值","dicType":"num"},{"optCode":"sts","optName":"状态","parm":[{"optName":"未生效","optCode":"0"},{"optName":"正常","optCode":"1"},{"optName":"注销","optCode":"2"}],"dicType":"y_n"}];</script>
</html>
