<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript">
	$(function() {
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/recallBase/findByPageAjax",//列表数据查询的url
			tableId : "tablerec0005",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			data:{recallSts:'1'},
			myFilter : true
		//是否有我的筛选
		});
	});
</script>
<style>
.table_content .ls_list tr.selected{
		background-color:#F7F7F7;
}
</style>
</head>
<body >
<dhcc:markPoint markPointName="RecallBase_ListSend"/>
	<div>
		<div style="vertical-align: bottom; display: block;" class="tabCont">
			<strong>催收任务指派列表</strong>
			
			<div class="search-group">
				<!--我的筛选选中后的显示块-->
				<div class="search-lable" id="pills">
				<dhcc:thirdButton value="按检索结果指派"  action="按检索结果指派" onclick="getSysUserList(this,'3','');"></dhcc:thirdButton>
				<dhcc:thirdButton value="部分指派"  action="部分指派" onclick="getSysUserList(this,'2','');"></dhcc:thirdButton>
					<!--我的筛选按钮-->
					<div class="filter-btn-group">
						<!--自定义查询输入框-->
						<input id="filter_in_input" placeholder="智能搜索" class="filter_in_input" type="text" />
						<div class="filter-sub-group">
							<button id="filter_btn_search" class="filter_btn_search" type="button">
								<i class="i i-fangdajing"></i>
							</button>
							<button id="fiter_ctrl_btn" class="filter_btn_myFilter"
								type="button">
								<i class="i i-jiantou7"></i>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div id="content" class="table_content" style="height: auto;">
			<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
		</div>
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
		 <!-- 弹出 begin -->
		  <div id="gridSystemModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridModalLabel">
		    <div class="modal-dialog" role="document">
		      <div class="modal-content">
		        <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		          <h4 class="modal-title" id="gridModalLabel">用户列表</h4>
		        </div>
		        <div class="modal-body" >
		         <div class="bs-example" data-example-id="striped-table" >
		         <!-- 
				   <div class="table_content check_user">
		    			<dhcc:tableTag paginate="sysUserList" property="tablesys5003" head="true"></dhcc:tableTag>
		    			<div class="pageer" pageNo="1" pageSum="1" pageSize="20"></div>
		    		</div>
		    		 -->
		    		 <dhcc:formTag property="formrec0007" mode="query" />
		  		</div>
		        </div>
				<div class="modal-footer">
		          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
		          <button type="button" class="btn btn-primary" onclick="SelectedUser()">确认</button>
		        </div>
		      </div><!-- /.modal-content -->
		    </div><!-- /.modal-dialog -->
		  </div><!-- /.modal -->
		<!-- 弹出 end --> 
	</div>
	<script type="text/javascript" src="${webPath}/component/rec/js/recallBase.js"></script>
	<script type="text/javascript">
		filter_dic =[{"optCode":"CUS_TYPE","optName":"客户类型","parm":[{"optName":"对公","optCode":"1"},{"optName":"个人","optCode":"2"}],"dicType":"y_n"},{"optCode":"PROD_TYPE","optName":"产品类别","parm":[{"optName":"乘用车","optCode":"1"},{"optName":"商用车","optCode":"2"},{"optName":"工程机械","optCode":"3"},{"optName":"设备类","optCode":"4"},{"optName":"飞机类","optCode":"5"},{"optName":"轮船类","optCode":"6"}],"dicType":"y_n"},{"optCode":"LEASE_TYPE","optName":"租赁方式","parm":[{"optName":"直接租赁","optCode":"1"},{"optName":"售后回租","optCode":"2"},{"optName":"厂商租赁","optCode":"3"},{"optName":"委托租赁","optCode":"4"}],"dicType":"y_n"},{"optCode":"VOU_TYPE","optName":"担保方式","parm":[{"optName":"信用担保","optCode":"1"},{"optName":"保证担保","optCode":"2"},{"optName":"抵押担保","optCode":"3"},{"optName":"质押担保","optCode":"4"}],"dicType":"y_n"},{"optCode":"REPAY_TYPE","optName":"还款方式","parm":[{"optName":"普通贷(等额本息)","optCode":"1"},{"optName":"等额本金","optCode":"2"},{"optName":"平息贷","optCode":"3"},{"optName":"尾款贷","optCode":"4"},{"optName":"跳跃贷","optCode":"5"},{"optName":"自定义还款","optCode":"A"}],"dicType":"y_n"},{"optCode":"TOTAL_AMT","optName":"总价款","dicType":"num"},{"optCode":"ACTUAL_AMT","optName":"实际融资额","dicType":"num"},{"optCode":"FST_PAY_RIO","optName":"首付比例","dicType":"num"},{"optCode":"FST_PAY_AMT","optName":"首付金额","dicType":"num"},{"optCode":"DEPOSIT_RIO","optName":"保证金比例","dicType":"num"},{"optCode":"DEPOSIT_AMT","optName":"保证金金额","dicType":"num"},{"optCode":"FINAL_RIO","optName":"尾款比例","dicType":"num"},{"optCode":"FINAL_AMT","optName":"尾款金额","dicType":"num"},{"optCode":"TERM_MON","optName":"期限月","dicType":"num"},{"optCode":"EXECUTE_RATE","optName":"执行利率","dicType":"num"},{"optCode":"START_DATE","optName":"起租日期","dicType":"num"},{"optCode":"END_DATE","optName":"到期日期","dicType":"num"}];
		addDefFliter("0","租赁方式","leaseType","LEASE_TYPE","1,2,3,4");
		addDefFliter("0","产品类别","prodType","PROD_TYPE","1,2,3,4,5,6");
		addDefFliter("0","还款方式","repayType","REPAY_TYPE","1,2,3,4,5,A");
	</script>
</body>
</html>
