<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


	<style type="text/css">
.vch-select {
    width: 100px;
    padding: 6px 0 6px 6px;
    display: inline;
    margin-right: 20px;
} 
.titlesty{
	padding-left: 30px;
	padding-right:20px;
	padding-top:10px;
}
/* .divstyle1{
	margin-top: 15px;
	margin-left:30px;
} */
/* .inputstyle2{
 	width: 137px !important; 
    padding: 6px 0 6px 6px;
    height: 34px !important;
    border-radius: 4px !important;
} */
.inputstyle3{
 	width: 90px !important; 
    padding: 6px 0 6px 6px;
    height: 34px !important;
    border-radius: 4px !important;
}
/* #pzInfotable tr td {
    border: 1px solid #e9ebf2;
} */
 .tabstyle{
  margin-top:20px;
}
.inputstyle3{
	display:initial !important;
}
.search-addon{
	    left: -7%;
   		 top: 12px;
		}
input{
	border: 1px solid #ccc;
}
label{
	font-weight: lighter;
	padding-right: 8px;
}
.search-addon{
	left:-13px !important;
	top:13px !important;
}

 .ui-icon-plus {
    background-position: -80px 0;
}
 .ui-icon-trash {
    background-position: -64px 0;
}
 .ui-icon {
    display: inline-block;
    margin: 0 5px;
    cursor: pointer;
    color:black;
    padding-left: 10px;
} 
/* .operating{
	margin: 0 0 0px;
}
.red-color{
	 color:red;
}*/
	 .vch-date{
		    width: 110px;
	   		padding: 6px;
	    	display: inline;
	    	background-color: #FFF !important;
	    	cursor: auto;
			background: url(${webPath}/component/finance/voucher/images/datepicker_icon.png) right 0 no-repeat #FFF;
		}
</style>
<%-- <link rel="stylesheet" href="${webPath}/component/finance/voucher/css/ui.min.css" /> --%>
<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
<link rel="stylesheet" href='${webPath}/component/finance/menthed/css/CwMonthEnd.css'/>
<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
<title>新增按月计税页面</title>

</head>
<body class="overflowHidden bg-white">

	<div class="container form-container">
		
		<div class="scroll-content">
				<div id='month_step_body' class='month_step step_display'>
				<form id="monthForm">
					<div id='month'>
						<div class="row vch_item">
							<div class="col-md-12 titlesty">
								<span class="txt">选择期间：</span>
								<select class="form-control vch-select mgr" id="weeks" name="weeks">
									
								</select>
								<!-- <input type="text" class="form-control vch-date" id="weeks" onclick=""  name="weeks" autocomplete="off" onkeydown="enterKey();" style="width: 140px; display: inline-block;" readonly>
								 -->
								<span class="txt">税率：</span>
								<input type="text" class="form-control" id="taxRate" name="taxRate" autocomplete="off"  onkeydown="enterKey();" style="width: 60px; display: inline-block; "  readonly>%
								
								
								<!-- <input class="form-control vch-select mgr" id="weeks" name="weeks">
								</input> -->
							<!-- 	<br>
								科目金额=科目当期发生额=价税合计<br>
								科目金额=金额+科目金额*税率（即税额） -->
							</div>
						</div>
					</div>
				</form>	
				
			</div>
	
		<div  class="table_content tabstyle" style="height: auto; ">

		<table id="priceTax" width="100%" border="1" align="center" style="border: 1px solid #e9ebf2;"
			cellspacing="1" class="ls_list" title="yewupingzheng">
			<thead>
				<tr>
					<th scope="col" width="10%" align="center" name="accName">借/贷</th>
					<th scope="col" width="29%" align="center" name="accName">科目</th>
					<th scope="col" width="17%" align="center" name="accName">价税合计</th>
					<th scope="col" width="17%" align="center" name="accName">金额(不含税)</th>
					<!-- <th scope="col" width="15%" align="center" name="accName">税率</th> -->
					<th scope="col" width="17%" align="center" name="taxAmt">税额</th>
					<th scope="col" width="10%" align="center" name="accName">操作</th>
				</tr>
			
			</thead>
			<tbody id="tab" >
			<tr> 
				 <td  align="center" class="dcind" >
					 <select class="inputstyle3 dcind" id="priceTaxDcind">
					 	<option value="1">借</option>
					 	<!-- <option value="2">贷</option> -->
					 </select>
				 </td>
				 <td  align="center" class="accNo">
				 	<input class="form-control form-warp accNo" placeholder="请选择计税科目"  style="width: 91%;" type="text"  id="accNo1" name="accNo1"  onclick="autoComPleter(this, '2')"  autocomplete="off"  />
					<span class="glyphicon glyphicon-search search-addon comitem_select"></span>
				</td>
				<td  align="center" class="" >
					 <span id="priceTaxAmt" class="priceTaxAmt"><span>
				</td>
				<td  align="center"  >
					 <span id="totalPriceAmt" class="priceAmt"></span>
				</td>
				<td align="center" ><span id="tatalTaxAmt"></span></td>
				<td></td>
				<!-- <td><p class='operating' data-id='3'><a class='i i-jia1 ui-icon ui-icon-plus' title='新增'></a>
				<a class='i i-lajitong ui-icon ui-icon-trash' title='删除'></a></p></td> -->
			</tr>
			<tr> 
				 <td  align="center" class="dcind" >
					 <select class="inputstyle3 dcind">
					 	<option value="2">贷</option>
					 	<!-- <option value="1">借</option> -->
					 </select>
				 </td>
				 <td  align="center" class="accNo">
				 	<input class="form-control form-warp accNo" placeholder="请选择明细科目" style="width: 91%;" type="text" onchange="changeAccount(this);"  id="accNo" name="accNo"  onclick="autoComPleter(this, '2',accountFun)"  autocomplete="off"  />
					<span class="glyphicon glyphicon-search search-addon comitem_select"></span>
				</td>
				<td  align="center">
					 <span id="priceTaxAmt" class="priceTaxAmt"><span>
				</td>
				<td  align="center">
					 <span id="priceAmt" class="priceAmt"></span>
				</td>
				<td align="center" >
					<span id="taxAmt"></span>
				</td>
			<!-- 	<td  align="center" class="payItems" >
					<span></span>
				</td> -->
				<td><p class='operating' data-id='3'><a class='i i-jia1 ui-icon ui-icon-plus' title='新增'></a>
				<a class='i i-lajitong ui-icon ui-icon-trash' title='删除'></a></p></td>
			</tr>
			</tbody>
		</table>
	</div>
	</div>
	<div class="formRowCenter"  >
		<dhcc:thirdButton value="保存" action="保存" onclick="addCwPriceTax();"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" ></dhcc:thirdButton>
	</div>
		
	</div>
</body>
<script type="text/javascript">

	var dataMap = '${dataMap}';
	
	var mapJson = '${dataMap.mapJson}';
	var monthList = '${dataMap.monthList}';
	
	$(function(){
		//doneInitmethod();
		//CwVchRuleMstAddJs.init();
		
		if(monthList){
			var monList = $.parseJSON(monthList);
			//alert(monList.length);
			var month = "";
			for(var j=0;j<monList.length;j++){
				//alert(monList[j]);
				month+="<option>"+monList[j]+"</option>";
			}
			$("#weeks").append(month);
		}
		
		if(mapJson){
			var map = $.parseJSON(mapJson);
			$("#weeks").val(map.weeks);
			$("#taxRate").val(map.priceTaxRate);
			var itemlist = map.itemlist;
			if(itemlist){
				var itemStr = "";
				for(var i = 0 ;i<itemlist.length;i++){
					var accStr = "";
					var oppStr = "";
					var taxStr = "";
					var dcIndStr = "";
					var dcIndId= "";
					if(itemlist[i].dcInd==1){
					 	dcIndStr = "<option value=\"1\">借</option>";//<option value=\"2\">贷</option>
					}else{
						dcIndStr = "<option value=\"2\">贷</option>";//<option value=\"1\">借</option>
					}
					if(i==0){
						accStr += "<input class=\"form-control form-warp accNo\" placeholder=\"请选择计税科目\"  style=\"width: 91%;\" type=\"text\"";
						accStr += "value=\""+itemlist[i].accNo+"/"+itemlist[i].accName+"\" id=\"accNo1\" name=\"accNo1\"  onclick=\"autoComPleter(this, '2')\"  autocomplete=\"off\"  />";
						taxStr = "<span id=\"tatalTaxAmt\">";
						dcIndId = "id=\"priceTaxDcind\"";
					}else{
						accStr += "<input class=\"form-control form-warp accNo\" placeholder=\"请选择明细科目\" style=\"width: 91%;\" type=\"text\"";
						accStr += "value=\""+itemlist[i].accNo+"/"+itemlist[i].accName+"\"  onchange=\"changeAccount(this);\"  id=\"accNo\" name=\"accNo\"  onclick=\"autoComPleter(this, '2',accountFun)\"  autocomplete=\"off\" />";
						
						oppStr = "<p class='operating' data-id='3'><a class='i i-jia1 ui-icon ui-icon-plus' title='新增'></a><a class='i i-lajitong ui-icon ui-icon-trash' title='删除'></a></p>";
						
						taxStr = "<span id=\"taxAmt\">";
					}
					itemStr +="<tr> ";
					itemStr +="<td  align=\"center\" class=\"dcind\" >";
					itemStr +="<select class=\"inputstyle3 dcind\" "+dcIndId+">"+dcIndStr+"</select></td>";
					itemStr +="<td  align=\"center\" class=\"accNo\">"+accStr+"<span class=\"glyphicon glyphicon-search search-addon comitem_select\"></span></td>";
					itemStr +="<td  align=\"center\"><span id=\"priceTaxAmt\" class=\"priceTaxAmt\">"+itemlist[i].priceTaxAmt+"</span></td>";//
					itemStr +="<td  align=\"center\"><span id=\"priceAmt\" class=\"priceAmt\">"+itemlist[i].priceAmt+"</span></td>";//
					itemStr +="<td align=\"center\" ><span id=\"taxAmt\">"+taxStr+itemlist[i].taxAmt+"</span></td>";//
					itemStr +="<td>"+oppStr+"</td>";
					itemStr +="</tr>";
				}
			
				$("#tab").html("");
				$("#tab").append(itemStr);  
				$("#weeks").change();
			}
			
		}
		operatEvent();
			
	});
	
	 $("#weeks").on('change',function(){
		//遍历tr
		$("#priceTax tbody tr").each(function(i) {
			
			if(i!=0){
				var accNoHtml = $(this).children("td").next('td').children("input");
				changeAccount(accNoHtml);
			}
		});
	});   
	
	/**
	* 新增价税分离的功能
	
	*/
	function addCwPriceTax(){
		var accno = $("#accNo1").val();
		if(!accno){
			alert(top.getMessage("NOT_FORM_EMPTY","计税科目" ),0);
			return;
		}
		
		var dataMap = {};
		var detils = [];
		$("#priceTax tbody tr").each(function(i) {
			
			if(i!=0){
				var dcind = $(this).children("td").children(".dcind").val();
				var accNo = $(this).children("td").next('td').children("input").val();
				var priceTaxAmt = $(this).children("td").next('td').next('td').children(".priceTaxAmt").text();
				var priceAmt =    $(this).children("td").next('td').next('td').next('td').children(".priceAmt").text();
				//var txType = $("#txType option:selected").val();
				if(accNo){
					var detil = {};
					detil.dcind=dcind;
					detil.accNo=accNo;
					detil.priceTaxAmt=priceTaxAmt;
					detil.priceAmt=priceAmt;
					detils.push(detil);
				}
			}
		});
		
		var weeks = $("#weeks").val();
		var totalPriceAmt = $("#tatalTaxAmt").text();
		var priceTaxDcind = $("#priceTaxDcind option:selected").val();
		dataMap.weeks=weeks;
		dataMap.priceTaxAccNo=accno;
		dataMap.totalPriceAmt=totalPriceAmt;
		dataMap.priceTaxDcind=priceTaxDcind;
		dataMap.detils=detils;
		
		 jQuery.ajax({
			 url:webPath+"/cwPriceTaxSep/insertCwPriceTaxAjax",
			data:{ajaxData : JSON.stringify(dataMap)},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag=='success'){
					$(parent.document).find("#showDialog .close").click();
				}else{
					alert(data.msg,0);
				}
				//var totalAmtJson = data.totalAmtJson;
				//var totalAmtmap = $.parseJSON(totalAmtJson);
				//$("#totalPriceAmt").text(totalAmtmap.totalAmt);
			},error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		}); 
	}
	
	
	$(".cancel").bind("click", function(event) {//取消只移除弹框
		$(parent.document).find("#showDialog .close").click();
	});
	myCustomScrollbarForForm({
		obj:".scroll-content",
		advanced : {
			theme : "minimal-dark",
			updateOnContentResize : true
		}
	});
	
	
	//给类似于放大镜的添加点击事件
	$("#tab").delegate(".comitem_select", "click", function() {
		var that = $(this);
		openComItemDialog('2', function(data) {
			if (data) {
				//that.prev('input').val(data.id);
				that.prev('input').val(data.id+"/"+data.name);
				var inputid = that.prev('input').attr("id");
				if(inputid!='accNo1'){
						changeAccount(that.prev('input'));
				}
			}
		});
	});
	
	
	function changeAccount(obj){
		
		//alert($(obj).val());
		var accval = $(obj).val();
		var weeks = $("#weeks").val();
		var accjson = {'accNo':accval,'weeks':weeks};
		$.ajax({
			url :'${webPath}/cwPriceTaxSep/getAccAmtByWeeksAjax',
			type:'post',
			data:accjson,
			async:false,
			dataType:'json',
			error:function(){
			},
			success:function(data){
				
				//alert('success');
				if(data){
					var amtjson = data.amtJson;
					var weeksmap = $.parseJSON(amtjson);
					$(obj).parent().next('td').children('span').text(weeksmap.weeksAmt);//
					$(obj).parent().next('td').next('td').children('span').text(weeksmap.priceAmt);//
					$(obj).parent().next('td').next('td').next('td').children('span').text(weeksmap.taxAmt);//
					
					//计算金额合计
					CalTotalamt();
				}
				//$(obj).parent().next('td').next('td').next('td').next('td').children('span').text(data.chaBal);//差额
				//alert('success');
			}
		
		});
		
		
	}
	//计算金额合计
	function CalTotalamt(){
		
		var dataMap = {};
		var detils = [];
		$("#priceTax tbody tr").each(function(i) {
			if(i!=0){
				var taxAmt = $(this).children("td").next('td').next('td').next('td').children("#taxAmt").text();
				if(priceAmt){
					var detil = {};
					detil.taxAmt=taxAmt;
					detils.push(detil);
				}
				
			}
		});
		dataMap.detils=detils;
		
		jQuery.ajax({
			url:webPath+"/cwPriceTaxSep/addCalTotalAmtAjax",
			data:{ajaxData : JSON.stringify(dataMap)},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
			
				var totalAmtJson = data.totalAmtJson;
				var totalAmtmap = $.parseJSON(totalAmtJson);
				$("#tatalTaxAmt").text(totalAmtmap.totalAmt);
				/* if(data.flag == "success"){
					alert(totalAmtmap.totalAmt);
					$("#totalPriceAmt").text(totalAmtmap.totalAmt);
				}else if(data.flag == "error"){
					alert(data.msg,0);
				} */
			},error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	
		
	}
	
	 function accountFun(){
		//alert($(obj).val()+"===");
	} 
	
	/**
添加一行，删除一行
 */
function operatEvent() {
	//添加一行
	$('.ui-icon-plus').unbind().on('click', function() {
		var trObj = $(this).parents('tr');
		var nextTr = $(trObj.prop('outerHTML'));
		var balval = nextTr.find('td:eq(1)').children('input').val('');//修改科目
		var balval = nextTr.find('td:eq(2)').children('span').text('');//修改价税合计的金额
		var balval = nextTr.find('td:eq(3)').children('span').text('');//修改价的金额
		var balval = nextTr.find('td:eq(4)').children('span').text('');//修税的金额
		trObj.after(nextTr);
		operatEvent()
	});
	//删除一行
	$('.ui-icon-trash').unbind().on('click', function() {
		var trObj = $(this).parents('tr');
		var trIndex = trObj.index();
		
		/*  if(trIndex==0){
		 }else{
		 } */
		var len = $('#tab tr').length;
		if (len == 2) {
		} else {
			trObj.remove();
		}
		CalTotalamt();
	});
}
	
</script>

</html>