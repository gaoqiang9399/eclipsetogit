<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


	<style type="text/css">
	.titlesty{
		padding-left: 30px;
		padding-right:20px;
		padding-top:10px;
	}
	.inputstyle3{
	 	width: 90px !important; 
	    padding: 6px 0 6px 6px;
	    height: 34px !important;
	    border-radius: 4px !important;
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
	 .vch-date{
		    width: 110px;
	   		padding: 6px;
	    	display: inline;
	    	background-color: #FFF !important;
	    	cursor: auto;
			background: url(${webPath}/component/finance/voucher/images/datepicker_icon.png) right 0 no-repeat #FFF;
		}
</style>
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
								<input type="text" class="form-control" id="weeks" name="weeks" autocomplete="off"  onkeydown="enterKey();" style="width: 140px; display: inline-block;" readonly>
								
								<span class="txt">税率：</span>
								<input type="text" class="form-control" id="taxRate" name="taxRate" autocomplete="off"  onkeydown="enterKey();" style="width: 60px; display: inline-block; "  readonly>%
								
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
					<th scope="col" width="30%" align="center" name="accName">科目</th>
					<th scope="col" width="20%" align="center" name="accName">价税合计</th>
					<th scope="col" width="20%" align="center" name="accName">金额(不含税)</th>
					<th scope="col" width="20%" align="center" name="accName">税额</th>
				<!-- 	<th scope="col" width="10%" align="center" name="accName">操作</th> -->
				</tr>
			
			</thead>
			<tbody id="tab" >
			<!-- <tr> 
				 <td  align="center" class="dcind" >
					<span id="dcind"></span>
				 </td>
				 <td  align="center" class="accNo">
					<span id="accNo"></span>
				</td>
				<td  align="center" class="" >
					 <span id="priceTaxAmt" class="priceTaxAmt"><span>
				</td>
				<td  align="center"  >
					 <span id="totalPriceAmt" class="priceAmt"></span>
				</td>
			</tr> -->
			<!-- <tr> 
				 <td  align="center" class="dcind" >
				 	
				 </td>
				 <td  align="center" class="accNo">
				 	<input class="form-control form-warp accNo" placeholder="请选择科目" style="width: 91%;" type="text" onchange="changeAccount(this);"  id="accNo" name="accNo"  onclick="autoComPleter(this, '0',accountFun)"  autocomplete="off"  />
					<span class="glyphicon glyphicon-search search-addon comitem_select"></span>
				</td>
				<td  align="center">
					 <span id="priceTaxAmt" class="priceTaxAmt"><span>
				</td>
				<td  align="center">
					 <span id="priceAmt" class="priceAmt"></span>
				</td> -->
			<!-- 	<td  align="center" >
					<span></span>
				</td>
				<td  align="center" class="payItems" >
					<span></span>
				</td> -->
				<!-- <td><p class='operating' data-id='3'><a class='i i-jia1 ui-icon ui-icon-plus' title='新增'></a>
				<a class='i i-lajitong ui-icon ui-icon-trash' title='删除'></a></p></td> -->
			</tr>
			</tbody>
		</table>
	</div>
	</div>
	<div class="formRowCenter"  >
		<%-- <dhcc:thirdButton value="保存" action="保存" onclick="addCwPriceTax();"></dhcc:thirdButton> --%>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" ></dhcc:thirdButton>
	</div>
		
	</div>
	</body>
	<script type="text/javascript">
	
		var dataMap = '${dataMap}';
		var mapJson = '${dataMap.mapJson}';
		var data = $.parseJSON(mapJson);
		$(function(){
			/* $("#accNo").text(data.accShowName);
			$("#totalPriceAmt").text(data.priceTaxAmt);
			$("#dcind").text(data.accShowDcind); */
			
			var itemlist = data.itemlist;
			var itemstr = "";
			$("#taxRate").val(itemlist[0].taxRate);
			$("#weeks").val(data.weeks);
			for(var i=0;i<itemlist.length;i++){
				itemstr +="<tr><td  align=\"center\"><span id=\"dcind\">"+itemlist[i].dcInd+"</span></td>";
				itemstr +="<td  align=\"center\"><span id=\"accNo\">"+itemlist[i].accNo+"/"+itemlist[i].accName+"</span></td>";
				itemstr +="<td  align=\"center\"><span id=\"priceTaxAmt\">"+itemlist[i].priceTaxAmt+"<span></td>";
				itemstr +="<td  align=\"center\"><span id=\"priceAmt\">"+itemlist[i].priceAmt+"</span></td>";
				itemstr +="<td  align=\"center\"><span id=\"priceAmt\">"+itemlist[i].taxAmt+"</span></td>";
				itemstr +="</tr>";
			}
			$("#tab").append(itemstr);
		});
		
	
	
		$(".cancel").bind("click", function(event) {//取消只移除弹框
			$(parent.document).find("#showDialog .close").click();
		});
		
		
	</script>
	
</html>