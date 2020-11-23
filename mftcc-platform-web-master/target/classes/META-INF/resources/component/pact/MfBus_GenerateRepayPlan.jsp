<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="${webPath}/component/include/tableFour.js?v=${cssJsVersion}"></script>
		<link rel="stylesheet" href="${webPath}/themes/view/css/tableFour.css" />
	<%-- <%Map<String,Object> dataMap = (Map<String,Object>)request.getAttribute("dataMap"); 
		String pactBeginDate = (String)dataMap.get("pactBeginDate");
		String pactEndDate = (String)dataMap.get("pactEndDate");
	%> --%>	
	<style type="text/css">
	.content_form{
		padding-top: 0px;
	}
	</style>
	</head>
<body class="overflowHidden">
   <div class="bigform_content">
		<div class="content_table">
			<div class="input_btn">
				<dhcc:button value="保存" action="保存" onclick="generateRepayPlanaaa(this,'${webPath}/mfBusFincApp/getPlanByPactTermAjax');"></dhcc:button>
			</div>
			<div class="content_form">
				<%-- <form method="post" theme="simple" name="operform" action="">
					<div class="content_Btn">
						<dhcc:button value="删除" action="删除" onclick="ajaxDeleteFour(this.form,'${webPath}/mfBusFincApp/deleteAjax')"></dhcc:button>
						<dhcc:button value="保存" action="保存" onclick="ajaxSaveFour(this.form,'${webPath}/mfBusFincApp/updateAjax')"></dhcc:button>
						<dhcc:button value="关闭" action="关闭" onclick="colseBtnFour(this)"></dhcc:button>
					</div>--%>
					<div style="display: none;">
						<dhcc:formTag property="formfincapp003" mode="query"></dhcc:formTag>
					</div>
			   <%--  </form> --%> 
				<table  width="100%" border="0" align="center"
					cellpadding="0" cellspacing="0" class="from_w"
					title="formfincapp003">
					<tbody>
						<tr>
							<td align="right" class="tdlable">合同ID&nbsp;</td>
							<td align="left" class="tdvalue">${mfBusFincApp.pactId }</td>
							<td align="right" class="tdlable">支用ID&nbsp;</td>
							<td align="left" class="tdvalue">${mfBusFincApp.fincId }</td>
						</tr>
						<tr>
							<td align="right" class="tdlable">放款金额&nbsp;</td>
							<td align="left" class="tdvalue">${mfBusFincApp.putoutAmt }</td>
							<td align="right" class="tdlable">还款方式&nbsp;</td>
							<td align="left" class="tdvalue" id="repayType-td">按计划</td>
						</tr>
						<tr>
							<td align="right" class="tdlable">申请利率&nbsp;</td>
							<td align="left" class="tdvalue">${mfBusFincApp.fincRate }%</td>
							<td align="right" class="tdlable">利率类型&nbsp;</td>
							<td align="left" class="tdvalue" id="rateType-td">年利率</td>
						</tr>
						<tr>
							<td align="right" class="tdlable">计息方式&nbsp;</td>
							<td align="left" class="tdvalue" id="icType-td">固定利率</td>
							<td align="right" class="tdlable">合同期限&nbsp;</td>
							<td align="left" class="tdvalue">${mfBusPact.termMonth }月${mfBusPact.termDay }日</td>
						</tr>
						<tr>
							<td align="right" class="tdlable">还款逾期是否罚息&nbsp;</td>
							<td align="left" class="tdvalue"><select name="isforcible"
								title="还款逾期是否罚息" class="form_select" mustinput=""
								onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()"
								onkeydown="enterKey();">
									<option value="1">是</option>
									<option value="0">否</option>
							</select></td>
							<td align="right" class="tdlable">发放日期&nbsp;</td>
							<td align="left" class="tdvalue"><input type="text"
								title="发放日期" name="putoutDate" datatype="0" mustinput=""
								onblur="func_uior_valTypeImm(this);"
								onclick="fPopUpCalendarDlg();" onmousedown="enterKey()"
								onkeydown="enterKey();" value="20160613"></td>
						</tr>
						<tr>
							<td align="right" class="tdlable">还款日期&nbsp;</td>
							<td align="left" class="tdvalue"><input type="radio"
								name="isFixDate" title="还款日期" mustinput="" datatype="0"
								value="2" onblur="func_uior_valTypeImm(this);"
								onchange="fixDateTypeChange()" onmousedown="enterKey()"
								onkeydown="enterKey();"> 实际到期日 <input type="radio"
								name="isFixDate" title="还款日期" mustinput="" datatype="0"
								value="1" onblur="func_uior_valTypeImm(this);"
								onchange="fixDateTypeChange()" onmousedown="enterKey()"
								onkeydown="enterKey();"> 固定还款日</td>
							<td align="right" class="tdlable">到期日期&nbsp;</td>
							<td align="left" class="tdvalue"><select name="endDateFinc"
								title="到期日期" class="form_select" mustinput=""
								onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()"
								onkeydown="enterKey();">
							</select></td>
						</tr>
						<tr style="display: none;">
							<td align="right" class="tdlable">固定还款日&nbsp;</td>
							<td align="left" class="tdvalue"><input type="text"
								title="固定还款日" name="fixDate" datatype="0" mustinput=""
								onblur="func_uior_valTypeImm(this);"
								onclick="fPopUpCalendarDlg();" onmousedown="enterKey()"
								onkeydown="enterKey();"></td>
							<td align="right" class="tdlable">预先收取利息&nbsp;</td>
							<td align="left" class="tdvalue"><input type="radio"
								name="prepayTakeIncom" title="预先收取利息" mustinput="" datatype="0"
								value="0" onblur="func_uior_valTypeImm(this);"
								onmousedown="enterKey()" onkeydown="enterKey();"> 合并第一期
								<input type="radio" name="prepayTakeIncom" title="预先收取利息"
								mustinput="" datatype="0" value="1"
								onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()"
								onkeydown="enterKey();"> 放款时收取 <input type="radio"
								name="prepayTakeIncom" title="预先收取利息" mustinput="" datatype="0"
								value="2" onblur="func_uior_valTypeImm(this);"
								onmousedown="enterKey()" onkeydown="enterKey();"> 独立一期</td>
						</tr>
						<tr>
							<td align="right" class="tdlable">计划期数&nbsp;</td>
							<td align="left" class="tdvalue"><input type="text"
								title="计划期数" name="term" datatype="0" mustinput=""
								onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()"
								onkeydown="enterKey();"></td>
							<td align="right" class="tdlable">pactTermType&nbsp;</td>
							<td align="left" class="tdvalue"><input type="text"
								title="pactTermType" name="pactTermType" datatype="0"
								mustinput="" onblur="func_uior_valTypeImm(this);"
								onmousedown="enterKey()" onkeydown="enterKey();" value="0">
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="table_show">
					<dhcc:tableTag property="tablerepayplan0002" paginate="mfBusRepayPlanList" head="true"></dhcc:tableTag>
			</div>
		</div>
    </div>
    <script type="text/javascript">
    $(function(){
    	$("#icType-td").text($("select[name=icType]").find("option:selected").text());
    	$("#rateType-td").text($("select[name=rateType]").find("option:selected").text());
    	$("#repatType-td").text($("select[name=repayType]").find("option:selected").text());
    	
    	var pactBeginDate = '${mfBusPact.beginDate}';
    	<%-- var pactEndDate = '<%=pactEndDate%>'; --%>
    	$(".content_form").find('input[name=putoutDate]').val(pactBeginDate);
    	$(".content_form").find('input[name=fixDate]').parents("tr").hide();
    	//tr1
    	if($(".content_form").find('select[name=repayType]').find('option:selected').val() != '6'){
    		$(".content_form").find('input[name=term]').parents("tr").hide();
    	}
    });
    function fixDateTypeChange(){
    	if($(".content_form").find('input[name=isFixDate]:checked').val() =='1' ){
    		$(".content_form").find('input[name=fixDate]').parents("tr").show();
    	}else{
    		$(".content_form").find('input[name=fixDate]').parents("tr").hide();
    	}
    };
    
    function putOutDateChange(){
    	//放款日期改变时，到期日期的select相应改变，并且pactTermType的值也要随之发生改变。
    	//但是现在不知道如何进行日期计算，暂无法得到到期日期
    };
    function fixDateChange(){
    	//固定还款日改变时，到期日期的select相应改变，并且pactTermType的值也要随之发生改变。	
    };
    
    function generateRepayPlan(obj,url){
    	 
    	var dataParam = JSON.stringify($(".content_form").find('form').serializeArray());
    	var tableId = $(".content_form").find(".ls_list").attr("title");
    	$.ajax({
    		url:url,
    		data:{ajaxData:dataParam,tableId:tableId},
    		type:"POST",
			dataType:"json",
			beforeSend:function(){
				
			},success:function(data){
				if(data.flag == 'success'){
					if(data.tableData!=undefined&&data.tableData!=null){
						var tableHtml = $(data.tableData).find("tbody").html();
						$(".content_form").find(".ls_list tbody").html(tableHtml);
					 }
				}else{
					alert(top.getMessage("FAILED_OPERATION"," "));
				}
			},error:function(){
				alert(top.getMessage("FAILED_OPERATION"," "));
			}
    	});
    };
    function saveRepayPlan(obj,url){
    	
    };
    </script>
</body>
</html>