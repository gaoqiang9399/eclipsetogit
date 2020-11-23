<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/ui.min.css" />
		<style type="text/css">
		.itemS1{
			padding-left: 5px;
		}
		.itemS2{
			padding-left: 30px;
		}
		.itemS3{
			padding-left: 45px;
		}
		.itemS4{
			padding-left: 55px;
		}
		.itemS5{
			padding-left: 65px;
		}
		.itemS6{
			padding-left: 75px;
		}
		.strong{
			font-weight: bold;
		}
		p{
			margin: 0px;
		}
		#tablist tr td a{
			line-height: auto;
		}
		.editbox {
		    width: 100%;
		    height: 30px; 
		    line-height: 30px; 
		    border-width: 1px;
		    border-style: solid;
		    border-color: rgb(221, 221, 221);
		    border-image: initial;
		    padding: 6px 4px;
		}
		</style>
		<script type="text/javascript" >
		var reports = {'001':'资产负债表查询', '002':'现金流量表查询', '003':'利润表查询', '004':'营业费用明细表查询', '005':'利润及利润分配表查询'};
		var reportTypeId = '${dataMap.reportTypeId}';
		var hcUseFlag = '${dataMap.hcUseFlag}';
		//yht showType设置
		var basePValue='${dataMap.basePValue}';
		var pCode="${dataMap.pCode}";
		$(function(){
			$('#report_title').text(reports[reportTypeId]);
			$('#report_big').text(reports[reportTypeId].replace('查询', '设置'));
			if('001'==reportTypeId){
				//加载列表
				myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfCusReportItem/getSetReportListAjax",//列表数据查询的url
			    	tableId:"tablesetreport0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	data:{"reportTypeId": reportTypeId},//指定参数
			    	ownHeight:true,
			    	callback:function(){
			    		getTableTr();//显示行次
			    		addTDEvent();
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			}else{
				//加载列表
				myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfCusReportItem/getSetReportListAjax",//列表数据查询的url
			    	tableId:"tablesetreport0002",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	data:{"reportTypeId": reportTypeId},//指定参数
			    	ownHeight:true,
			    	callback:function(){
			    		//addTDEvent();
			    		getTableTr();//显示行次
			    		addTDEvent();
			    		
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			}
			//yhtbug
			valPvalueSelect();
			//初始化更多查询 控件
			$('.footer_loader').remove();
		});
		//给showType参数赋初始值,并绑定change事件,change立即保存
		function valPvalueSelect(){
			$("#colPvalue").val(basePValue);
			$("#colPvalue").change(function(){
				$.ajax({
					url:webPath+"/cwSysParam/changePvalueByCodeAjax",
					data:'pcode='+pCode+'&pvalue='+$("#colPvalue").val(),
					dataType:'json',
					type:'post',
					success:function(data){
						if(data.flag=='success'){
							window.top.alert("设置成功，仅对此之后的报表生效！", 1);
						}else{
							alert(top.getMessage("FAILED_OPERATION", ''), 1);
						}
					}
				})
			})
		}
		//修改报表项  与 公式
		function ajaxEditItem(obj){
			var Url= "${webPath}/mfCusReportItem/toDeployPage?reportItemId=";
			var txt = $(obj).attr('reportName');
			var reportItemId = $(obj).attr('reportItemId');
			window.parent.openBigForm(Url + reportItemId, txt+'详情',closeCallBackn, 90,90);
		}
		//新增报表项
		function ajaxAddItem(obj){
			var Url= "${webPath}/mfCusReportItem/toReportItemAdd?reportTypeId="+reportTypeId+"&reportItemId=";
			var reportItemId = $(obj).attr('reportItemId');
			window.parent.openBigForm(Url + reportItemId, '新增',closeCallBack, 90, 90);
		}

		function updateOrder(obj){
			var param = {'reportItemId':$(obj).attr('reportItemId'), 'order': $(obj).attr('data-type')}
			jQuery.ajax({
				url:webPath+"/mfCusReportItem/updateOrderAjax",
				data:{ajaxData: JSON.stringify(param)},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						alert(top.getMessage("SUCCEED_OPERATION"),1);
						updateTableData();//重新加载列表数据
					}else if(data.flag == "error"){
						alert(data.msg,0);
					}
				},error:function(data){
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
		
		function closeCallBackn() {
			// myclose();
            updateTableData();//重新加载列表数据
		};
		function closeCallBack() {
			// alert(1);
			//updateTableData();//重新加载列表数据
			showReportItem();
			// myclose();
		};
		
// 			function insertAjax(obj){
// 				ajaxInput(obj,"${webPath}/mfCusReportItem/insertAjax?reportTypeId=<s:property value='reportTypeId'/>reportItemId=<s:property value='reportItemId'/>");
// 			}
		</script>
	</head>
<body>
	<!--标记点 未后退准备-->
	<dhcc:markPoint markPointName="CwVoucherMst_List" />
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<ol class="breadcrumb pull-left">
						<li class="avtive" id="report_big"></li>
					</ol>
				</div>
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div">
					<div class="column mysearch-div" id="pills">
						<div class="col-xs-4 ">
							<div class="pull-left">
								<button class="btn btn-info" reportItemId="" onclick="ajaxAddItem(this)">新增</button>
							</div>
							<div class="pull-left" style="padding-left: 20px">
							    <span class="txt"> 展示禁用报表项</span>	<input type="checkbox" id ="showReportItem" name="showReportItem" onclick="showReportItem()" value="展示禁用项" />
							</div>
						</div>
						<div class="search-title hidden"><span value=""></span></div>
						<div class="col-xs-8" style="padding-right: 15px;">
								<div class="col-xs-4"></div>
								<div class="col-xs-3">
								</div>
								<div class="col-xs-3">
								</div>
								<div class="col-xs-1">
									<%--<button class="btn btn-default" reportItemId="" onclick="showReportView(this)" style="margin-left:-5px;">预览</button>--%>
								</div>
								<div class="col-xs-1">
									<%--<button class="btn btn-default hidden" reportItemId="" id="openTr" onclick="openTr(this)" style="margin-left:-5px;">启用行次</button>--%>
									<%--<button class="btn btn-default hidden" reportItemId="" id="closeTr" onclick="closeTr(this)" style="margin-left:-5px;">禁用行次</button>--%>
								</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content grid-wrap cw_voucher_list"
					style="height: auto;">
					<!--待定是否放置自定义table标签?-->
<%-- 					<c:if test='${dataMap.reportTypeId=="001"}'> --%>
<%-- 						<dhcc:tableTag property="tablefinreport0001" --%>
<%-- 							paginate="CwSearchReportList" head="true"></dhcc:tableTag> --%>
<%-- 					</c:if> --%>
<%-- 					<c:if test='${dataMap.reportTypeId!="001"}'> --%>
<%-- 						<dhcc:tableTag property="tablefinreport0002" --%>
<%-- 							paginate="CwSearchReportList" head="true"></dhcc:tableTag> --%>
<%-- 					</c:if> --%>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	function showReportItem(){
		var itemflag = $("#showReportItem").is(':checked');
		var flags = "0";
		if(itemflag){
			flags = 1;
		}
			if('001'==reportTypeId){
				//加载列表
				myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfCusReportItem/getSetReportListAjax?itemflag="+flags,//列表数据查询的url
			    	tableId:"tablesetreport0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	data:{"reportTypeId": reportTypeId},//指定参数
			    	ownHeight:true,
			    	callback:function(){
			    		getTableTr();
			    		addTDEvent();
			    		//addTDEventNew();
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			}else{
				//加载列表
				myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfCusReportItem/getSetReportListAjax?itemflag="+flags,//列表数据查询的url
			    	tableId:"tablesetreport0002",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	data:{"reportTypeId": reportTypeId},//指定参数
			    	ownHeight:true,
			    	callback:function(){
			    		getTableTr();
			    		addTDEvent();
			    	//	addTDEventNew();
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			}
			$('.footer_loader').remove();
		//
	}
	
</script>
<script type="text/javascript">
var oldVal = '';
function addTDEvent(){
	$("#tablist>tbody").off('click');
	$("#tablist>tbody").on('click', 'td', function(){
		var trObj = $(this).parents('tr');
		var trIndex = trObj.index();
		var tdIndex = $(this).index();
		var text = $(this).text().replace(/(^\s*)|(\s*$)/g, "");
		var inputCount = $(this).children("input").length;
		var inputStr;
		var reportid = $(this).children("input").val();
		if(reportTypeId=='001'){
			if(tdIndex <= 5){
				oldVal = text;
				if(tdIndex==1){
					inputStr = $("<input type='text' class='editbox' onkeydown='enterKey();' autocomplete='off'>");
				}else if(tdIndex==4){
					inputStr = $("<input type='text' class='editbox' onkeydown='enterKey();' autocomplete='off'> ");
				}
				$(this).html(inputStr).addClass('pad0');
				var  hangci = "<input type=\"text\" id=\"fuTrId\" class=\"hangci\" hidden value="+reportid+">";
				$(this).append(hangci);	
				$(this).children('.editbox').focus().val(text);
				//alert("focus--000====");
			}
		}else if(reportTypeId=='003'){
			if(tdIndex <= 5){
				oldVal = text;
				if(tdIndex==1){
					inputStr = $("<input type='text' class='editbox' onkeydown='enterKey();' autocomplete='off'>");
				}
				$(this).html(inputStr).addClass('pad0');
				var  hangci = "<input type=\"text\" id=\"lrTrId\" class=\"hangci\" hidden value="+reportid+">";
				$(this).append(hangci);	
				$(this).children('.editbox').focus().val(text);
			}
		}else if(reportTypeId=='002'){
			if(tdIndex <= 5){
				oldVal = text;
				if(tdIndex==2){
					inputStr = $("<input type='text' class='editbox' onkeydown='enterKey();' autocomplete='off'>");
				}
				$(this).html(inputStr).addClass('pad0');
				var  hangci = "<input type=\"text\" id=\"xjllTrId\" class=\"hangci\" hidden value="+reportid+">";
				$(this).append(hangci);	
				$(this).children('.editbox').focus().val(text);
			}
		}
		/* alert(reportid); */
	});
	//光标离开事件，触发保存
	 $("#tablist>tbody").off('blur');
  	 $("#tablist>tbody").on('blur', '.editbox', function(){
  	 	//alert("lost-----");
		var trObj = $(this).parents('tr');
		var trIndex = trObj.index();
		var val = $(this).val().replace(/(^\s*)|(\s*$)/g, "");
		var valflag = isNaN(val);
		if(valflag){
			val = "";
		}
		var $parent = $(this).parent();
		var reportid = $parent.children(".hangci").val()
		var ismoney = $(this).hasClass('money');
		$parent.html(val).removeClass('pad0');
 		var  hangci = "<input type=\"text\" class=\"hangci\" hidden value="+reportid+">";
 		$parent.append(hangci);
 		if(reportid=='undefined'){
 			return;
 		}
		var numflag = isNaN($parent.text());
		if(numflag){
			return;
		}
		 if(reportid&&oldVal != val){
			updateHangCi($parent);
		} 
	});  
	
}

function updateHangCi(tdobj){
	var dataParm = getTrData(tdobj);
		jQuery.ajax({
			url:webPath+"/mfCusReportItem/updateHangCiForItemAjax",
			data:{ajaxData: JSON.stringify(dataParm)},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					alert(top.getMessage("SUCCEED_SAVE"), 1);
					//updateTableData();//重新加载列表数据
				}else if(data.flag == "error"){
					 alert(data.msg,0);
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	
}
function getTrData(tdObj){
	var data = {};
	
	data.hangci=tdObj.text();
	data.reportData=tdObj.children('input').val();
	//alert(data.hangci+"---"+data.reportData);
	return data;
}
</script>
<script type="text/javascript">
	var oldVal = '';
//function addTDEventNew(){
/* 	$("#tablist>tbody").delegate('td',"click", function() {
			//$("#tablist>tbody").unbind('click');
			$(this).unbind("click");
			$(this).unbind("blur");
			$(this).bind("click", function() {
				var trObj = $(this).parents('tr');
				var trIndex = trObj.index();
				var tdIndex = $(this).index();
				var text = $(this).text().replace(/(^\s*)|(\s*$)/g, "");
				var inputCount = $(this).children("input").length;
				var inputStr;
				var reportid = $(this).children("input").val();
				if(reportTypeId=='001'){
				if(tdIndex <= 5){
					oldVal = text;
					if(tdIndex==1){
						inputStr = $("<input type='text' class='editbox' onkeydown='enterKey();' autocomplete='off'>");
					}else if(tdIndex==4){
						inputStr = $("<input type='text' class='editbox' onkeydown='enterKey();' autocomplete='off'> ");
					}
					$(this).html(inputStr).addClass('pad0');
					var  hangci = "<input type=\"text\" id=\"fuTrId\" class=\"hangci\" hidden value="+reportid+">";
					$(this).append(hangci);	
					$(this).children('.editbox').focus().val(text);
					alert('ssss-----jjjjj----addTDEventNew');
				}
				}else if(reportTypeId=='003'){
					if(tdIndex <= 5){
						oldVal = text;
						if(tdIndex==1){
							inputStr = $("<input type='text' class='editbox' onkeydown='enterKey();' autocomplete='off'>");
						}
						$(this).html(inputStr).addClass('pad0');
						var  hangci = "<input type=\"text\" id=\"lrTrId\" class=\"hangci\" hidden value="+reportid+">";
						$(this).append(hangci);	
						$(this).children('.editbox').focus().val(text);
					}
				}else if(reportTypeId=='002'){
					if(tdIndex <= 5){
						oldVal = text;
						if(tdIndex==2){
							inputStr = $("<input type='text' class='editbox' onkeydown='enterKey();' autocomplete='off'>");
						}
						$(this).html(inputStr).addClass('pad0');
						var  hangci = "<input type=\"text\" id=\"xjllTrId\" class=\"hangci\" hidden value="+reportid+">";
						$(this).append(hangci);	
						//$(this).children('.editbox').focus().val(text);
					}
				}
				alert('click---'+reportTypeId);
			});
			
		}); */

	
	//光标离开事件，触发保存
	//$("#tablist>tbody").unbind('click');
/* 	$("#tablist>tbody").delegate('.editbox',"blur", function() {
		$(this).unbind("blur");
			$(this).bind("blur", function() {
				var trObj = $(this).parents('tr');
				var trIndex = trObj.index();
				var val = $(this).val().replace(/(^\s*)|(\s*$)/g, "");
				var valflag = isNaN(val);
				if(valflag){
					val = "";
				}
				var $parent = $(this).parent();
				var reportid = $parent.children(".hangci").val()
				var ismoney = $(this).hasClass('money');
				$parent.html(val).removeClass('pad0');
				var  hangci = "<input type=\"text\" class=\"hangci\" hidden value="+reportid+">";
				$parent.append(hangci);
				
				if(reportid=='undefined'){
					addTDEventNew();
					return;
				}
				var numflag = isNaN($parent.text());
				if(numflag){
					addTDEventNew();
					return;
				}
				if(reportid&&oldVal != val){
					//updateHangCi($parent);
				} 
				alert(1);
				//addTDEventNew();
		});
	
	});   */ 
//}

</script>

<script type="text/javascript">
//利润表显示
	
	function getTableTr(){
		if(hcUseFlag==1){
				$("#closeTr").removeClass("hidden");
				$("#openTr").addClass("hidden");
			}else{
				$("#openTr").removeClass("hidden");
				$("#closeTr").addClass("hidden");
			}
		var tit = "";
		//alert(reportTypeId+"---"+hcUseFlag);
		if(reportTypeId=='001'){
			tit = "showName,zcTr,itemAAmt,itemBAmt,fzTr,itemAAmt2,itemBAmt2,showName2";
		}else if(reportTypeId=='003'){
			tit = "showName,lrTr,itemAAmt";//显示
			if(hcUseFlag==0){//禁用行次
				tit = "showName,itemAAmt";//显示
			}
			//$('#batchRe_btn').removeAttr('disabled').addClass('btn-primary');
   		}else{
   			tit = "showName,xjllTr,itemAAmt";
   			if(hcUseFlag==0){//禁用行次
				tit = "showName,itemAAmt";//显示
			}
			//$('#batchRe_btn').attr('disabled', true).removeClass('btn-primary');
   		}
   		 if(hcUseFlag!=1){
   			tit = "showName,itemAAmt,itemBAmt,itemAAmt2,itemBAmt2,showName2";
   		} 
   		//alert(tit);
   		
		$(".search-title").find("span").attr("value", tit);
		showTable(false, ''); 
		
	}
	//启用行次
	function openTr(obj){
		var reportTypeIdjson = {'reportTypeId':reportTypeId};
		jQuery.ajax({
			url:webPath+"/mfCusReportItem/updateTrOpenAjax",
			data:reportTypeIdjson,
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				
				if(data.flag == "success"){
					$("#closeTr").removeClass("hidden");
					$("#openTr").addClass("hidden");
					location.reload();//刷新列表
					//showslist();
					//updateTableData();//重新加载列表数据
				}else if(data.flag == "error"){
					 alert(data.msg,0);
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	}
	//禁用行次
	function closeTr(obj){
		var reportTypeIdjson = {'reportTypeId':reportTypeId};
		jQuery.ajax({
			url:webPath+"/mfCusReportItem/updateTrCloseAjax",
			data:reportTypeIdjson,
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					$("#openTr").removeClass("hidden");
					$("#closeTr").addClass("hidden");
					location.reload();//刷新列表
					//showslist();
					//updateTableData();//重新加载列表数据
				}else if(data.flag == "error"){
					 alert(data.msg,0);
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	}
						
</script>

</html>