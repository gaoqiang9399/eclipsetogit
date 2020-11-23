<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE>
<html>
<head>
<title>财务报表</title>

<link rel="stylesheet" href="${webPath}/component/report/css/MfReport1.css" />
<script type="text/javascript" src="../paramset/js/CwParamEntrance.js"></script>
<style type="text/css">
	.queryBtn{
	    height: 70px;
	    width: 220px;
	    vertical-align: middle;
	}
	.setBtn{
		position: absolute;
	    top: 22px;
	    right: 8px;
	    height: 26px;
	    line-height: 26px;
	    display: none;
	}
	.setBtn i{
		font-size: 25px;
		color: #FFF;
/* 		color: #32b5cb; */
	}
</style>

<script type="text/javascript">
	var cashOpen = '0';
	function openReport(type){
		if(type=='002' && cashOpen=='0'){
			//alert('现金流量分析已禁用，请先到参数设置开启', 2);
			alert(top.getMessage("NOT_CW_CASHFLOW_USE", ""));
		}else{
			$.get("${webPath}/cwInitSystem/getSysInitFlagAjax", function(data){
				if(data.flag == "success"){
					if(data.data.balInit=='0'){
						//alert('余额未初始化，请先录入初始余额',1);
						alert(top.getMessage("FIRST_OPERATION", '录入初始余额，余额未初始化'), 0);
					}else if(data.data.maxClose=='0'){
						//alert('不存在已结账期，请先进行月末结账',1);
						alert(top.getMessage("FIRST_OPERATION", '月末结账，已结账期不存在'), 0);
					}else{
						if(data.data.cashCnt!='0' && type=='002'){
							//top.getMessage("CONFIRM_CW_VOUCHER_WRITEOFF", '')本期存在未分析凭证，是否继续生成报表!
							alert(top.getMessage("CONFIRM_CW_VOUCHER_WRITEOFF", ''),2,function(){
								window.location.href =  '${webPath}/cwReportItem/toSearchReportPage?reportTypeId='+type;
							});
						}else{
							window.location.href =  '${webPath}/cwReportItem/toSearchReportPage?reportTypeId='+type;
						}
					}
				}else if(data.flag == "error"){
					
					 alert(1111);
					 alert(data.msg,0);
				}
			});
		}
	}
	
	function setReport(obj, type){
		window.location.href =  '${webPath}/cwReportItem/toReportItemSet?reportTypeId='+type;
	}
	
	$(function(){
		$('.rotate-obj').bind('mouseover',function(){
	    	$(this).find('.setBtn').css('display','block');
		}).bind('mouseleave',function(){
			$(this).find('.setBtn').css('display','none');
		});
		//现金流量分析是否启用
		$.get("${webPath}/cwSysParam/getByIdAjax", {pcode : "10000"}, function(data){
			if(data.flag == "success"){
				if(data.formData.pvalue=='1'){
					cashOpen = '1';
				}
			}else if(data.flag == "error"){
				 alert(data.msg,0);
			}
		});
		
	})
	
</script>
<body>
<div class="container">
	<div class="row clearfix report-body">
<!-- 		<div class="report-title">财务报表</div> -->
		<div class="rotate-body">
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac1">
					<div class="queryBtn"  onclick="openReport('001');">
						<div class="rotate-tubiao i i-bi1"></div>
						<div class="rotate-des" >
							资产负债表
						</div>
					</div>
					<span class="setBtn" onclick="setReport(this,'001');" >
						<i class="i i-chilun">
							<a href="#"></a>
						</i>
					</span>
				</div>
			</div>
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac3">
					<div class="queryBtn" onclick="openReport('003');" >
						<div class="rotate-tubiao i i-bi1"></div>
						<div class="rotate-des" >
							利润表
						</div>
					</div>
					<span class="setBtn" onclick="setReport(this,'003');" >
						<i class="i i-chilun">
							<a href="#"></a>
						</i>
					</span>
				</div>
			</div>
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac4">
					<div class="queryBtn" onclick="openReport('002');"  >
						<div class="rotate-tubiao i i-bi1"></div>
						<div class="rotate-des" >
							现金流量表
						</div>
					</div>
					<span class="setBtn" onclick="setReport(this,'002');" >
						<i class="i i-chilun">
							<a href="#"></a>
						</i>
					</span>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>