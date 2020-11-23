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
    width: 218px;
    padding: 6px 0 6px 6px;
    display: inline;
    margin-right: 20px;
}
.titlesty{
	padding-left: 30px;
	padding-right:20px;
	padding-top:10px;
}
.divstyle1{
	margin-top: 15px;
	margin-left:30px;
}
.inputstyle1{
	padding-left:20px;
}
.inputstyle2{
 	width: 100% !important; 
    padding: 6px 0 6px 6px;
    height: 34px !important;
    border-radius: 4px !important;
}
.inputstyle3{
 	width: 90px !important; 
    padding: 6px 0 6px 6px;
    height: 34px !important;
    border-radius: 4px !important;
}
#pzInfotable tr td {
    border: 1px solid #e9ebf2;
}
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
.operating{
	margin: 0 0 0px;
}
</style>

<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
<%-- <link rel="stylesheet" href="${webPath}/component/finance/voucher/css/ui.min.css" /> --%>
<link rel="stylesheet" href='${webPath}/component/finance/menthed/css/CwMonthEnd.css'/>
<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
<script type="text/javascript" src="${webPath}/component/finance/paramset/js/CwVchRuleMst_getRuleMstDetail.js"></script>
<title>新增业务记账规则</title>

	
</head>
<body class="overflowHidden bg-white">

	<div class="container form-container">
		
		<div class="scroll-content">
				<div id='month_step_body' class='month_step step_display'>
				<form id="monthForm">
					<div id='month'>
						<div class="row vch_item">
							<div class="col-md-12 titlesty">
								<span class="txt">业务类型：</span>
								<!-- <input type="text" value="" id="ywtype"class="inputstyle2" /> -->
								<select class="form-control vch-select mgr" id="txType" name="txType">
									
								</select>
								<span class="txt">业务:</span>
								<!-- <input type="text" value="" id="yewu" readonly="readonly"  class="inputstyle2"  /> -->
								<select class="form-control vch-select mgr" id="ywName" name="ywName">
									
								</select>
								<span class="txt">凭证字:</span>
								<select class="form-control vch-select mgr" id="pzType" name="pzType">
								</select>
								<input type="text" name="traceNo" value="" id="traceNo" style="display: none">
							</div>
						</div>
					</div>
					<div class="divstyle1 clearfix" id="adaptProduct0">
						<!-- <span class="txt">适用交易：</span> -->
						<span class="txt" style="float:left">适用交易：</span>
						<div style="padding-left: 6em">
							<div id="adaptProduct"></div>
						</div>
						<!-- <input type="checkbox" class="inputstyle1" value="1">全部
						<input type="checkbox" class="inputstyle1" value="2">抵押
						<input type="checkbox" class="inputstyle1"  value="3">质押 -->
					</div>
				</form>	
				
			</div>

		<div  class="table_content tabstyle" style="height: auto; ">

		<table id="pzInfotable" width="100%" border="1" align="center" style="border: 1px solid #e9ebf2;"
			cellspacing="1" class="ls_list" title="yewushezhi">
			<thead>
				<tr>
					
					<th scope="col" width="15%" align="center" name="accName">摘要</th>
					<th scope="col" width="6%" align="center" name="accName">借/贷</th>
					<th scope="col" width="24%" align="center" name="accName">科目</th>
					<th scope="col" width="16%" align="center" name="accName">取值</th>
					<th scope="col" width="23%" align="center" name="accName">现金流量</th>
					<th scope="col" width="6%" align="center" name="priceTaxFlag" class="priceTaxFlag">是否计税</th>
					<th scope="col" width="10%" align="center" name="accName">操作</th>
				</tr>
			
			</thead>
			<tbody id="tab" >
			<tr id="0"> 
				<td  align="center" class="zhaiyao">
					<input type="text" class="inputstyle2 edit_subject" name="zhaiyao"value=""/>
				</td>
				 <td  align="center" class="dcind" >
					 <select class="inputstyle3 dcind">
					 	<option value="1">借</option>
					 	<option value="2">贷</option>
					 </select>
				 </td>
				 <td  align="center" class="accNo">
				 	<input class="form-control form-warp accNo" style="width: 91%;" type="text"  id="accNo" name="accNo"  autocomplete="off"  />
					<span class="glyphicon glyphicon-search search-addon comitem_select"></span>
				</td>
<!-- 				 <td  align="center" class="accNo">
				 	<input class="form-control form-warp accNo" style="width: 91%;" type="text" onclick="autoComPleter(this, '2',changeFont)" id="accNo" name="accNo"  autocomplete="off"  />
					<span class="glyphicon glyphicon-search search-addon comitem_select"></span>
				</td> -->
				<td  align="center" class="payItems" >
					 <select class="inputstyle2 payItems" id="payItems">
					 	
					 </select>
				</td>
				<!-- <td  align="center" class="cashflow">
					  <font class="click_search">  
						<input type="text" class="form-control form-warp  cashflow" style="width: 91%;" name="cashFlow" id="cashFlow" value="">
						<span class="glyphicon glyphicon-search search-addon click_search"></span> 
					  </font> 
				</td>  -->
				<td  align="center" class="cashflow" id="showflow1">
				  <font class="click_search" id="flowsearch">  
					<input type="text" class="form-control form-warp  cashflow" style="width: 91%;" name="cashFlow" id="cashFlow" value="">
					<span class="glyphicon glyphicon-search search-addon click_search2"></span> 
				  </font> 
				</td> 
				<td  align="center" id="showflow2" style="display:none">
					  <font class="click_search2" id="flowsearch2">  
						<input type="text" disabled="disabled" class="form-control form-warp  cashflow" style="width: 91%;" name="cashFlow" id="cashFlow" value="">
						<span class="glyphicon glyphicon-search search-addon click_search2"></span> 
					  </font> 
				</td> 
				<td align="center" style="display:none"><input type="hidden" name="uuid" value=""></td>
				<td id="priceTaxFlag" class="priceTaxFlag">
					<select class="inputstyle3 priceTaxFlag">
					 	<option value="2">不计税</option>
					 	<option value="1">计税</option>
					 </select>
				</td>
				<td><p class='operating' data-id='3'><a class='i i-jia1 ui-icon ui-icon-plus' title='新增'></a>
				<a class='i i-lajitong ui-icon ui-icon-trash' title='删除'></a></p></td>
			
			</tr>
	
			</tbody>
		</table>
	</div>
	</div>
	<div class="formRowCenter"  >
		<dhcc:thirdButton value="保存" action="保存" onclick="updateCwPz();"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="mycloseDef()"></dhcc:thirdButton>
	</div>
	</div>
</body>
<script type="text/javascript">
	var traceNo = '${traceNo}';
	var beangson = '${beangson}';
	var wordlist = '${wordlist}';
	var listmapjson = '${listmapJson}';//业务类型
	var paymapjson = '${paymap}';//取值数据
	
	var listmap = $.parseJSON(listmapjson);
	var bean = $.parseJSON(beangson);
	var json = '${jsonlist}';
	var tembean = $.parseJSON(json);
	var wordbean = $.parseJSON(wordlist);
	var paymap = $.parseJSON(paymapjson);//取值数据
	var adaptlistmapJson = '${adaptlistmapJson}';//适用交易
	var bdaptlistmapJson = '${bdaptlistmapJson}';//适用交易
	var adaptlistmap = $.parseJSON(adaptlistmapJson);
	var bdaptlistmap = $.parseJSON(bdaptlistmapJson);
	var rulelist = '${rulelist}';
	var rulelistbean = $.parseJSON(rulelist);
	
	var priceTaxType = '${priceTaxType}';//获取价税分离方式
	
	$(function(){
		//detailInitfun();
		CwVchRuleMstJs.init();
		
		if(priceTaxType==2){
			$("#pzInfotable thead tr").children(".priceTaxFlag").hide();
			$("#tab tr").children(".priceTaxFlag").hide();
		}
		
	});
	
	
</script>
</html>