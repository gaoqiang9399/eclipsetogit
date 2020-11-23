<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/ui.min.css" />
		<style type="text/css">
			.voucherLink{
			    cursor: pointer;
			    text-decoration: underline;
			    margin: 5px;
			    color: #555;
			}
			.voucherLink:hover{
			    text-decoration: underline;
			}
		</style>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/cwAssets/findByPageAjax",//列表数据查询的url
			    	tableId:"tableassets0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
// 			    	ownHeight:true,
			    	callback:function(options,data){
			    		addVchEvent();
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			    addCheckAllEvent();
			 });
			function callBackFunction(){
				isCheckAll = false;
				updateTableData();
			}
			function addVchEvent(){
				//生成凭证
				$('.createVoucher').off('click').on('click', function(){
					var uid = $(this).data('id') + '';
					var param = JSON.stringify({'which': 'assets', 'uid': uid});
					window.parent.openBigForm(encodeURI('${webPath}/cwVoucherMst/toVoucherAddSet?ajaxData='+param), '凭证新增',callBackFunction);
				});
				
				//凭证详情
				$('.openLink').off('click').on('click', function(){
					var voucherid = $(this).data('voucherid') + '';
					window.parent.openBigForm(encodeURI('${webPath}/cwVoucherMst/toVoucherEdit?which=assets&voucherNo='+voucherid), '凭证详情',callBackFunction);
				});
				
				//凭证删除
				$('.deleteVoucher').off('click').on('click', function(){
					var voucherid = $(this).data('voucherid') + '';
					jQuery.ajax({
						url:webPath+"/cwVoucherMst/deleteAjax",
						data:{voucherNo:voucherid},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							if(data.flag == "success"){
								alert(top.getMessage("SUCCEED_OPERATION") ,1);
								callBackFunction();//重新加载列表数据
							}else if(data.flag == "error"){
								 alert(data.msg,0);
							}
						},error:function(data){
							 alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
					});
				});
			}
		</script>
	</head>
	<body class="overFlowHidden">
		<!--页面显示区域-->
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div column">
					<button type="button" class="btn btn-primary" onclick="finForm_input();">新增</button>
					<button type="button" class="btn btn-primary" onclick="deleteBatch();">删除</button>
					<!-- <button type="button" class="btn btn-primary" onclick="genre();">资产类别</button> -->
					<!-- 				<button type="button" class="btn btn-primary" onclick="toCashier();">生成凭证</button> -->
				</div>
				<div class="search-title hidden"><span value=""></span></div>
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div" id="search-div">
					<!-- begin -->
						<jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder=名称/类别"/>
					<!-- end -->
				</div>
			</div>
		</div>
		<div id="content" class="table_content" style="height: auto;"></div>
	</body>	
	
	<script type="text/javascript">
	function finForm_input(){//新增弹框
		//top.openBigForm('${webPath}/cwAssets/input', '新增', updateTableData);	
		var url = '${webPath}/cwAssets/input';
		$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
		$(top.window.document).find("#showDialog").remove(); 


	
	}
	
	 function getByIdThis(obj, ajaxUrl){//详情弹框
		// top.openBigForm(ajaxUrl, '详情', updateTableData);	
		 $(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src","${webPath}/"+ajaxUrl);
		 $(top.window.document).find("#showDialog").remove(); 
	}
	
	 function genre(){//资产类别
			window.location.href= "${webPath}/cwAssetsClass/getListPage";
	}
	 
	//生成凭证
	function toCashier(){
// 		$('input:checkbox[name=uuid]:checked').each(function(){
// 			var uid = $(this).val().substring(5)+'';
// 			var param = JSON.stringify({'which': 'cwassets', 'uid': uid});
// 			window.parent.openBigForm(encodeURI('${webPath}/cwVoucherMst/toVoucherAddSet?ajaxData='+param), '凭证新增',updateTableData);
// 		});
	}
	 
	//获取凭证选择的凭证编号
	function getCheckedPzNos(){
		var pznos = '';
        var pzno = '';
        var vals=0;
        $(".table_content").find(':checkbox').each(function() {
        	if($(this).is(":checked")){
//         		pzno = eval($(this).val())[0].voucherNo;
        		pzno = $(this).val().substring(5);
        		if(pzno != '' && pzno != null){
            		pznos = pznos + "," + pzno;
            		vals++;
        		}
        	}
        });
        if(vals==0){
        	alert(top.getMessage("FIRST_SELECT_FIELD","需要操作的数据"), 1);
//         	alert("请选择需要操作数据", 1);
        }else{
        	pznos = pznos.substring(1);
        }
        return pznos;
	}
	
	//批量删除
	function deleteBatch(){
		var pznos = getCheckedPzNos();
		if(pznos != ''){
			var url = '${webPath}/cwAssets/deleteBatchsAjax';
			var dataParam = '[{"name":"voucherNos","value":"'+pznos+'"}]'; 
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						alert(top.getMessage("SUCCEED_OPERATION") ,1);
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
 	}
 	
 	
 	//凭证详情
	 function aa (voucherNo){
			window.parent.openBigForm(encodeURI('${webPath}/cwVoucherMst/toVoucherEdit?which=cashier&voucherNo='+voucherNo), '凭证详情',updateTableData);
	}
</script>	
</html>
