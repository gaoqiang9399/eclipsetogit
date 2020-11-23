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
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
		<style type="text/css">
			.btn-div .more-btn{
				margin-left: 4px;
				float: left;
			}
			.btn-div .show-btn{
				float: left;
			}
			.more-btn > .dropdown_ul{
				min-width: 68px;
				right: 0;
				left: auto;
				z-index: 1002;
			}
			.more-btn > .dropdown_ul > li > a{
				padding: 5px 5px;
			}
			.more-btn > .dropdown_ul > li > a:HOVER{
				background: #e3e7eb;
			}
			.red{
				color: red;
			}
			#filter-menu input {
				vertical-align: baseline;
			}
		</style>
		<script type="text/javascript" >
			var searchWeek = '${dataMap.week}';
			var searchEndWeek = '${dataMap.endWeek}';
			var proofs = '${dataMap.proofs}';
			var fications = '${dataMap.fications}';
			var allVchNo = '';
			$(function(){
				//清空 并 初始化查询期
				clearSearchForm();
				//加载列表
				myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/cwVoucherMst/getVoucherListAjax",//列表数据查询的url
			    	tableId:"tablevoucher0001",//列表数据查询的table编号
			    	tableType:"tableTag",//table所需解析标签的种类
// 			    	myFilter:false, //是否有我的筛选(列表列动态切换)
			    	pageSize:10,//加载默认行数(不填为系统默认行数)
			    	data:{"week":"201612"},//指定参数
			    	ownHeight:true,
			    	callback:function(options,data){
			    		allVchNo = data.ipage.paramsStr;
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
	    		addCheckAllEvent();
				//初始化更多查询 控件
				MoreSearch.init();
				
				//科目弹窗
				$('.comitem_select').on('click', function(){
					var forId = $(this).attr('for');
					openComItemDialog('0', function(data){
						if(data){
							$('#'+forId).val(data.id);
						}
					});
				})
				
				//初始化凭证字查询条件
				if(proofs){
					var vehmarks = eval(proofs);
					var opt = $('#pzProofNo')[0];
					opt.options.length=0;
					opt.add(new Option('全部', ''));
					for(var i=0; i<vehmarks.length; i++){
						opt.add(new Option(vehmarks[i].pzPrefix, vehmarks[i].pzProofNo));
					}
				}
				//初始化辅助核算查询条件
				if(fications){
					var pact = eval(fications);
					var opt = $('#itemsNo')[0];
					opt.options.length=0;
					opt.add(new Option('全部', ''));
					for(var i=0; i<pact.length; i++){
						opt.add(new Option(pact[i].typeName, pact[i].txType));
					}
				}
				
				//*辅助核算弹窗显示
				$('#valueNo_search').on('click', function(){
					var forId = $(this).attr('for');
					var itemsno = $('#itemsNo').val();
					if(itemsno!=''){
						openAssistDialog(itemsno, function(data){
							if(data){
								$('#'+forId).val(data.txCode);
							}
						});
					}
				})
				
// 				bindExportVchPopup();
			});
			
			//获取 查询条件（方法名固定写法）
			function getFilterValArr(){ 
				return JSON.stringify($('#cwListForm').serializeArray());
			}
			
			//弹窗查看凭证详情
			function ajaxGetVoucherByNo(obj, ajaxUrl){
				window.parent.openBigForm(webPath+ajaxUrl + '&params='+allVchNo, '凭证详情',closeCallBack);
			}
			
			//弹窗查看凭证冲销
			function ajaxSterilisaVch(obj, ajaxUrl){
				window.parent.openBigForm(webPath+"/"+ajaxUrl, '凭证冲销',closeCallBack);
			}
			
			//关闭弹窗
			function closeCallBack() {
				isCheckAll=false;
				myclose();
				my_Search();
			};
			
			//获取凭证选择的凭证编号
			function getCheckedPzNos(){
				var pznos = '';
	            var pzno = '';
	            var vals=0;
	            $(".cw_voucher_list").find(':checkbox').each(function() {
	            	if($(this).is(":checked")){
// 	            		pzno = eval($(this).val())[0].voucherNo;
	            		pzno = $(this).val().substring(10);
	            		if(pzno != '' && pzno != null){
		            		pznos = pznos + "," + pzno;
		            		vals++;
	            		}
	            	}
	            });
	            if(vals==0){
	            	alert(top.getMessage("FIRST_SELECT_FIELD","需要操作的凭证"), 1);
// 	            	alert("请选择需要操作的凭证。", 1);
	            }else{
	            	pznos = pznos.substring(1);
	            }
	            return pznos;
			}
			
			//批量审核
			function voucherBatchAudit(){
				var pznos = getCheckedPzNos();
				if(pznos != ''){
					var url = '${webPath}/cwVoucherMst/voucherBatchAuditAjax';
					var dataParam = '[{"name":"voucherNos","value":"'+pznos+'"}]'; 
					jQuery.ajax({
						url:url,
						data:{ajaxData:dataParam},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							if(data.flag == "success"){
								 alert(top.getMessage("SUCCEED_OPERATION"),1);
								 updateTableData();//重新加载列表数据
// 								 addCheckAllEvent();
							}else if(data.flag == "error"){
								 alert(data.msg,0);
							}
						},error:function(data){
							 alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
					});
				}
			}
			
			//批量反审核
			function voucherBatchRevAudit(){
				var pznos = getCheckedPzNos();
				if(pznos != ''){
					var url = webPath+'/cwVoucherMst/voucherBatchRevAuditAjax';
					var dataParam = '[{"name":"voucherNos","value":"'+pznos+'"}]'; 
					jQuery.ajax({
						url:url,
						data:{ajaxData:dataParam},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							if(data.flag == "success"){
								 alert(top.getMessage("SUCCEED_OPERATION"),1);
								 updateTableData();//重新加载列表数据
// 								 addCheckAllEvent();
							}else if(data.flag == "error"){
								 alert(data.msg,0);
							}
						},error:function(data){
							 alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
					});
				}
			}
			
			//批量删除
			function deleteBatch(){
				var pznos = getCheckedPzNos();
				if(pznos != ''){
					var url = webPath+'/cwVoucherMst/deleteBatchAjax';
					var dataParam = '[{"name":"voucherNos","value":"'+pznos+'"}]'; 
					jQuery.ajax({
						url:url,
						data:{ajaxData:dataParam},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							if(data.flag == "success"){
								if(data.data.failed !=''){
// 									alert('删除失败的凭证'+data.data.failed, 2)
									alert(top.getMessage("FAILED_DELETE") + '凭证'+data.data.failed ,2);
								}else{
									alert(top.getMessage("SUCCEED_OPERATION") ,1);
								}
								 updateTableData();//重新加载列表数据
// 								 addCheckAllEvent();
							}else if(data.flag == "error"){
								 alert(data.msg,0);
							}
						},error:function(data){
							 alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
					});
				}
			}
			
			//新增凭证跳转
			function addVoucher(){
				window.location=webPath+"/cwVoucherMst/toVoucherAdd";
			}
			
			//凭证整理
			function voucherRearrang(){
				window.location=webPath+"/cwVoucherMst/goZhengliPage";
				//window.location="${webPath}/component/finance/voucher/pingzhengzhengli.jsp";
			}
			
			function my_Search(){
				/**格式化顶部查询条件*/
				var begin = $('#beginWeek').val();
				var end = $('#endWeek').val();
				if(begin > end){
					alert(top.getMessage("NOT_FORM_TIME", {"timeOne":"开始周期" , "timeTwo": "结束周期"}), 1);
// 					alert('开始周期不能大于结束周期', 1);
					clearSearchForm();
					return false;
				}else{
					setPeriod()
				}
				updateTableData();//重新加载列表数据
				MoreSearch.colseMoreBtn();
// 				addCheckAllEvent();
			}
			
			//去除表头 点击事件 换成 全选事件
			
			var isCheckAll = false;
			function addCheckAllEvent() {
				 $(".table-float-head").delegate("th:first-child","click",function(){
					if (isCheckAll) {
						$(".cw_voucher_list").find(':checkbox').each(function() {
							this.checked = false;
						});
						isCheckAll = false;
					} else {
						$(".cw_voucher_list").find(':checkbox').each(function() {
							this.checked = true;
						});
						isCheckAll = true;
					}
				});
			}

			//重置查询条件
			function clearSearchForm() {
				//把查询条件中参数 清空，并初始化查询条件
				$('#cwListForm').find('.form-control').each(function() {
					$(this).val('');
				});
				$('#beginWeek').val(searchWeek);
				$('#endWeek').val(searchEndWeek);
				setPeriod();
			}

			function setPeriod() {
				/**格式化顶部查询条件*/
				var begin = $('#beginWeek').val();
				var end = $('#endWeek').val();
				if (begin == end) {
					$('#selected-period').html(joinToWeek(begin));
				} else {
					$('#selected-period').html(
							joinToWeek(begin) + " 至 " + joinToWeek(end));
				}
			}
			//组装周期
			function joinToWeek(weeks) {
				return "<span>" + weeks.substring(0, 4) + "年"
						+ weeks.substring(4) + "期<span>";
			}
			//刷新数据列表
			function reloadJsp() {
				clearSearchForm();
				updateTableData();//重新加载列表数据
// 				addCheckAllEvent();
			}

			//现金流量分析
			function cashFlowAnalysis() {
				window.location.href = webPath+"/component/finance/voucher/CwCashFlowAnalysis_List.jsp";
			}
			//凭证导入
			function importVoucher(){
				window.location.href=webPath+"/component/finance/voucher/voucher_import.jsp";
			}
			//按导入模版导出凭证
			function exportVchTemplate(){
				var pznos = getCheckedPzNos();
				if(pznos != ''){
					bindExportVchPopup(pznos);
					$("input[name=exportVch]").next('.pops-value').click();
<%-- 					window.location.href= "${webPath}/cwVchPlateMst/exportVchTemplate?ajaxData=" + pznos; --%>
<%-- 					window.location.href= "${webPath}/cwVchOutSoftver/exportVchTemplate?ajaxData=" + pznos + "&softId=10001"; --%>
				}
			}
			
			//凭证导出弹窗
			function bindExportVchPopup(pznos){
				$("input[name=exportVch]").popupList({
					searchOn: false, //启用搜索
					multiple: false, //false单选，true多选，默认多选
					ajaxUrl:webPath+"/cwVchOutSoftver/getSelectExportVchAjax",// 请求数据URL
					valueElem:"input[name=exportVch]",//真实值选择器
					title: "选择导出版本",//标题
					handle: ".export_vch",//触发器
					changeCallback:function(elem){//回调方法
						BASE.removePlaceholder($("input[name=exportVch]"));
						var sltVal = elem.data("selectData");
						window.location.href= webPath+"/cwVchOutSoftver/exportVchTemplate?ajaxData=" + pznos + "&softId=" + sltVal.softId;
					},
					tablehead:{//列表显示列配置
						"softId":"序号",
						"softName":"软件版本",
						"fileType":"文件类型"
					},
					returnData:{//返回值配置
						disName:"softName",//显示值
						value:"softId"//真实值
					}
				});
			}
		</script>
	</head>
<body>
	<!--标记点 未后退准备-->
	<dhcc:markPoint markPointName="CwVoucherMst_List"/>
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div column">
					<div class="show-btn" style="float:left">
						<button type="button" class="btn btn-primary" onclick="addVoucher();">新增</button>
						<button type="button" class="btn btn-default" onclick="">打印</button>
						<button type="button" class="btn btn-default" onclick="">导出</button>
						<!-- 2017年8月17日  将审核修改成记账 -->
						<button type="button" class="btn btn-default" onclick="voucherBatchAudit();">记账</button>					
					</div>
					<div class="dropdown more-btn" style="float:left; margin-left: 4px;" >
						<button id="dLabel" type="button" class="btn btn-default" data-toggle="dropdown" aria-haspopup="true"  aria-expanded="false">
					   		 更多
						<span class="caret"></span>
						</button>
						<ul class="dropdown-menu dropdown_ul" role="menu" aria-labelledby="dLabel">
							<li>
								<!-- 2017年8月17日 将反审核修改成反记账 -->
								<a class="btn" onclick="voucherBatchRevAudit();">反记账</a>
							</li>
							<li>
								<a class="btn" onclick="voucherRearrang();">整理</a>
							</li>
<!-- 							<li> -->
<!-- 								<a class="btn">生成业务凭证</a> -->
<!-- 							</li> -->
							<li>
								<a class="btn" onclick="importVoucher();">凭证导入</a>
							</li>
							<li>
								<a class="btn" onclick="deleteBatch();">批量删除</a>
							</li>
							<li>
								<a class="btn" class="export_vch" onclick="exportVchTemplate();">按导入模版格<br>式导出</a>
							</li>
							<c:if test='${dataMap.cashOpen=="1"}'>
								<li>
									<a class="btn" onclick="cashFlowAnalysis();">现金流量分析</a>
								</li>
							</c:if>
						</ul>
					</div>
				</div>
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div">
					<div class="col-xs-4 column mysearch-div" id="pills">
						<!-- 更多查询条件功能开始 -->
						<div class="mod-toolbar-top">
							<div class="left">
								<!-- 查询标题 -->
								<span class="txt fl">凭证查询</span>
								<div class="ui-btn-menu fl" id="filter-menu">
									<!-- 显示条件 -->
									<span class="ui-btn menu-btn"><span id="selected-period"></span><b></b>
									</span>
									<!-- 弹窗  -->
									<div class="search_con">
										<input type="hidden" name="exportVch" id="exportVch" >
										<form id="cwListForm">
											<!-- 主要查询条件 -->
											<ul class="filter-list" id="filter-period">
												<li class="li-two-wrap">
													<label>会计期间:</label> 
													<input type="text" class="form-control form-warp cw-week" readonly name="beginWeek" id="beginWeek" autocomplete="off" onclick="laydatemonth(this);" onkeydown="enterKey();">
													<span>至</span> 
													<input type="text" class="form-control form-warp cw-week" readonly name="endWeek" id="endWeek" autocomplete="off" onclick="laydatemonth(this);" onkeydown="enterKey();">
												</li>
											</ul>
											<!-- 展开的更多条件  -->
											<!-- <ul class="filter-list" id="more-conditions" style="display: none;"> -->
											<ul class="filter-list" id="more-conditions" >
												<li class="li-one-wrap">
													<label for="#filter-fromSubject">科目:</label>
													<input type="text" class="form-control form-warp" name="accNo" id="accNo" onclick="autoComPleter(this)" autocomplete="off">
													<span class="glyphicon glyphicon-search search-addon comitem_select" for="accNo"></span>
												</li>
												<li class="li-one-wrap">
													<label for="#filter-fromLevel">凭证日期:</label>
													<input type="text" class="form-control form-warp cw-week" readonly name="voucherDate" id="voucherDate" autocomplete="off" onclick="fPopUpCalendarDlg();" onkeydown="enterKey();">
												</li>
												<li class="li-one-wrap">
													<label for="#filter-fromLevel">凭证字:</label>
													<select class="form-control form-warp" name="pzProofNo" id="pzProofNo" autocomplete="off"></select>
												</li>
												<li class="li-two-wrap">
													<label for="#filter-fromLevel">凭证号:</label> 
													<input type="text" class="form-control form-warp" id="beginNoteNo" name="beginNoteNo" autocomplete="off">
													<span>至</span> 
													<input type="text" class="form-control form-warp" name="endNoteNo" id="endNoteNo" autocomplete="off">
												</li>
												<li class="li-one-wrap">
													<label for="#filter-fromLevel">凭证状态:</label>
													<select class="form-control form-warp" name="pzFlag" id="pzFlag" autocomplete="off">
													<option value="">请选择</option>
													<option value="0">已制单</option>
													<option value="4">已审核</option>
													<option value="5">已作废</option>
													</select>
												</li>
												<li class="li-two-wrap">
													<label for="#filter-fromLevel">辅助核算:</label> 
													<select class="form-control form-warp" name="itemsNo" id="itemsNo" autocomplete="off"></select>
													<span><b></b></span> 
													<input type="text" class="form-control form-warp " name="itemsValueNo" id="itemsValueNo" autocomplete="off">
													<span class="glyphicon glyphicon-search search-addon" id="valueNo_search" for="itemsValueNo"></span>
												</li>
												<li class="li-two-wrap">
													<label for="#filter-fromLevel">金额:</label> 
													<input type="text" class="form-control form-warp" id="beginAmt" name="beginAmt" autocomplete="off">
													<span>至</span> 
													<input type="text" class="form-control form-warp" name="endAmt" id="endAmt" autocomplete="off">
												</li>
											</ul>
											<br>
										</form>
										<!-- 展开收起、重置、确定按钮 -->
										<div class="btns">
											<a href="#" id="conditions-trigger" class="conditions-trigger conditions-expand" tabindex="-1">收起更多<b></b></a> 
<!-- 											<a href="#" id="conditions-trigger" class="conditions-trigger conditions-expand" tabindex="-1">更多条件<b></b></a>  -->
											<a class="ui-btn ui-btn-sp" id="filter-submit" onclick="my_Search();">确定</a> 
											<a class="ui-btn" id="filter-reset" onclick="clearSearchForm();" tabindex="-1" style="display: inline;">重置</a>
										</div>
									</div>
								</div>
								<a onclick="reloadJsp();" class="ui-btn ui-btn-refresh fl" id="refresh"><b></b></a>
							</div>
						</div>
						<!-- 更多查询条件功能结束 -->
					</div>
				</div>
			</div>
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
</html>