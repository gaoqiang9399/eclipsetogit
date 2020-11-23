<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>报表项详情编辑</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<style type="text/css">
			body{
				overflow: hidden;
			}
			.deploy_content{
				height: calc(100% - 65px);
			}
			
			.footer{
				bottom: 0px;
			    position: fixed;
			    width: 100%;
			    z-index: 100;
			}
			#tablist select{
				height: 32px;
				width: 90%;
			}
			#tablist input{
				width: 90%;
				height: 32px;
			}
			
			#tablist a{
				margin: 0 5px;
			}
			
			.afterS{
			    height: 35px;
			    line-height: 35px;
			    margin-left: 15px;
			}
			
			.search-addon {
			    left: -22px;
			    font-size: 14px;
			}
			.hPaddingl{
				padding-left: 30px;
			}
			.hiddenFront{
				display: none;
			}
		</style>
		<script type="text/javascript" >
		var reportItemId = '${dataMap.reportItemId}'
		var isInput = '${dataMap.isInput}'
		var hcUseFlag = '${dataMap.hcUseFlag}'
		var operationType = '0';
		var reportTypeId = '';
		$(function(){
			reportTypeId = $('input[name=reportTypeId]').val();
			operationType = $('select[name=operationType]').val();
			if('001'!=reportTypeId){
				var lastRow = $('#cwDeployForm').find('tr:eq(1)');
				lastRow.find('td:gt(1)').html('');
				if('002'==reportTypeId){
					var lable = $('<label class="control-label">现金流方向</label>');
					//0流入，1流出
					var isPut = $('<div class="input-group"><select name="isInput" title="现金流方向" class="form_select form-control" mustinput="0" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();"><option value="0">流入</option><option value="1">流出</option></select></div>')
					if(isInput==1){
						isPut = $('<div class="input-group"><select name="isInput" title="现金流方向" class="form_select form-control" mustinput="0" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();"><option value="1">流出</option><option value="0">流入</option></select></div>')
					}
					lastRow.find('td:eq(2)').html(lable);
					lastRow.find('td:eq(3)').html(isPut);
				}
			}
			var typeObj = $('select[name=reportItemType]');
			typeObj.after('<span class="afterS">' + typeObj.find("option:selected").text() + '</span>').hide();
			$("body").removeClass("hiddenFront");// 解决页面js加载导致表单变化 
			//加载列表
			myCustomScrollbar({
		    	obj:"#content",//页面内容绑定的id
		    	url:webPath+"/cwReportItem/getItemCalcSetListAjax",//列表数据查询的url
		    	tableId:"tablefinreport0003",//列表数据查询的table编号
		    	tableType:"thirdTableTag",//table所需解析标签的种类
		    	data:{'reportItemId': reportItemId},//指定参数
		    	ownHeight:true,
		    	callback:function(options, data){
		    		if(data.ipage.pageCounts==0){
		    			$('#tabList tbody tr').remove();
		    			insertRow(null);
		    		}else{
		    			
			    		$('#content').find("select option[value='']").remove();//去掉空白
			    		$('#content').find("input").after('<span class="glyphicon glyphicon-search search-addon" for="calcItem"></span>').attr('onclick', 'autoComPleter(this, 0)');//去掉空白
			    		 //onclick="autoComPleter(this, '0')" 
			    		
			    		//$('#content').find("input").after('<span class="glyphicon glyphicon-search search-addon" for="calcItem"></span>').attr('readonly', true);//去掉空白
		    		}
		    		$('#content').height('249px');
		    		$('.footer_loader').remove();
		    	}//方法执行完回调函数（取完数据做处理的时候）
		    });
			//空白项 与 现金流量表 查询项不展示公式
			if('0'==operationType || ('1'==operationType && '002'==reportTypeId)){
			}else{
				$('.cw_deploy_list').removeClass('hidden');
	    		$('.table-float-head').remove();
				if('1'==operationType){
					$("select[name=gsType] option[value='2']").remove();
				}
				
				$("select[name=gsType]").change(function(){
					$(this).parent().next().children('input').val('');
				});
				
				$(".search-addon").click(function(){
					openDialog(this)
				});
			}
		});
		
		//获取 查询条件（方法名固定写法）
		function getFilterValArr(){ 
			return JSON.stringify($('#cwDeployForm').serializeArray());
		}
		
		// 增加一行
		function insertRow(obj) {
			var $divtr = $('<tr></tr>');
			var $divtd1 = $('<td width="20%" align="center"></td>');
			var $divtd2 = $('<td align="left"><input type="text" name="calcItem"  onclick=\"autoComPleter(this, 0)\"  value=""><span class="glyphicon glyphicon-search search-addon" onclick="openDialog(this)"></span></td>');
			var $divtd3 = $('<td width="20%" align="center"><select name="calcSign" title="calcSign"><option value="1">加</option> <option value="2">减</option></select></td>');
			var $divtd4 = $('<td width="10%" align="center"><a onclick="javascript:insertRow(this);"><i class="i i-jia2"></i></a><a onclick="javascript:removeRow(this);"><i class="i i-x42"></i></a></td>');
			var $gsTyep = $('<select name="gsType" title="gsType"><option value="1"> 科目 </option></select>');
			var $bbx = $('<option value="2">报表项</option>');
			//2017年9月26日  修改
			var operationTypeN = $('select[name=operationType]').val();
			if(operationTypeN=='2'){
				$gsTyep.append($bbx);
			}
			$divtd1.append($gsTyep);
			$divtr.append($divtd1).append($divtd2).append($divtd3).append($divtd4);
			if(obj==null){
				$('#tabList tbody').append($divtr);
			}else{
				$(obj).parents('tr').after($divtr);
			}
		}
		
		// 删除一行
		function removeRow(obj) {
			var len = $("#tablist tbody tr").length;
			$(obj).parents('tr').remove();
			if(len == 1){
				insertRow(null);
			}
		}
		
		function openDialog(obj){
			var optype = $(obj).parent().prev().children('select').val();
			if('1'==optype){
				openComItemDialog('0', function(data){
					$(obj).prev('input').val(data.showName);
				});
			}else{
				openReportItemDialog(reportTypeId, function(data){
					$(obj).prev('input').val(data.reportItemId + '/' + data.reportName);
				})
			}
		}
		
		
		function ajaxUpdateDeploy(formid){
			var dataParam = $('#cwDeployForm').serializeArray()
			var detils = [];
			$("#tablist tbody tr").each(function(i) {
				//判断是否输入科目与报表项
				var calcItem = $(this).children("td:eq(1)").children("input").val();
				var calcempty = typeof(calcItem) == "undefined" || calcItem == "";
				if(!calcempty) {
//					
					var gsType = $(this).children("td:eq(0)").children("select").val();
					var calcSign = $(this).children("td:eq(2)").children("select").val();
					
					var detil = {};
					detil.gsType=gsType;
					detil.calcSign=calcSign;
					detil.calcItem=calcItem;
					detils.push(detil);
				}
			});
			var deploys = {};
			deploys.name="deploys";
			deploys.value=JSON.stringify(detils);
			dataParam.push(deploys);
			jQuery.ajax({
				url:webPath+"/cwReportItem/updateDeployAjax",
				data:{ajaxData:JSON.stringify(dataParam)},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						  window.top.alert(top.getMessage("SUCCEED_OPERATION"),1);
						  myclose_click();
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(data){
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
			
		}
		
		//运算类型切换，显示运算公式
		function changeOperationType(obj){
			var type = $(obj).val();
			var ishow = $('.cw_deploy_list').is(':visible');
			if('0'==type && ishow){
				 $('.cw_deploy_list').addClass('hidden');
			}else{
				$('.cw_deploy_list').removeClass('hidden');
				if('1'==type){
					$("select[name=gsType] option[value='2']").remove();
				}else{
					$('.cw_deploy_list').find("select[name=gsType]").each(function(){
						if(this.options.length==1){
							this.add(new Option('报表项', '2'));
						}
					});
				}
			}
		}
	
 	myCustomScrollbarForForm({
		obj:".scroll-content",
		advanced : {
			theme : "minimal-dark",
			updateOnContentResize : true
		}
	}); 
	/* $(".scroll-content").mCustomScrollbar({
		advanced:{
			theme:"minimal-dark",
			updateOnContentResize:true
		}
	}); */
		</script>
	</head>
<body class="overflowHidden bg-white hiddenFront"> <!--hiddenFront样式 解决页面js加载导致表单变化 -->
<div class="container form-container">
	
	<div class="col-md-10 col-md-offset-1 column margin_top_20  scroll-content">
		<div class="deploy_content">
		<div class="bootstarpTag">
			<form method="post" id="cwDeployForm" theme="simple" name="cwDeployForm" action="">
						<dhcc:bootstarpTag property="formfinreport0002" mode="query" />
	<!-- 			<div class="table_content"> -->
	<!-- 			</div> -->
				<br>
				<div class="cw_deploy_list hidden">
					<div class="scroll-content">
					<h4 class="hPaddingl">报表项公式：</h4>
					<div id="content" class="table_content "  style="height: 200px;">
		<%-- 				<dhcc:tableTag property="tablefinreport0001" paginate="CwSearchReportList" head="true"></dhcc:tableTag> --%>
					</div>
					</div>
				</div>
			</form>
			</div>
		</div>
	</div>
	</div>
	<div class="formRowCenter footer">
<!-- 		  -->
		<dhcc:thirdButton value="保存" action="保存" onclick="ajaxUpdateDeploy('#cwDeployForm');"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	</div>
	</div>
</body>
</html>