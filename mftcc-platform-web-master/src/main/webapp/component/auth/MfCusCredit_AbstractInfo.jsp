<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String cusNo = (String)request.getParameter("cusNo");
	String creditAppId = (String)request.getParameter("creditAppId");
	String busEntrance = (String)request.getParameter("busEntrance");
	String contextPath = request.getContextPath();
%>
<script type="text/javascript">
var cusNo = '<%=cusNo%>';
var creditAppId = '<%=creditAppId%>';
var busEntrance = '<%=busEntrance%>';
var htmlStr="";
var cusName = "";
	$(function(){
		if(typeof(creditAppId) != "undefined" && creditAppId != "null" && creditAppId != "" && creditAppId != "@creditAppId@"){
			$.ajax({
				url:webPath+"/mfCusCreditApply/getCreditInfoAjax",
				data:{creditAppId:creditAppId},
				type:"POST",
				dataType:"json",
				success:function(data) {
					if(data.flag == 'success'){
						var creditInfo = "";
						var creditAmt = "";
						var creditTerm = "";
						var creditSts = "";
						if(data.creditType == '1'){
							creditInfo = data.mfCusCreditApply;
							creditAmt = creditInfo.creditSum/10000;
							creditTerm = creditInfo.creditTerm;
						}else{
							creditInfo = data.mfCusCreditAdjustApply;
							creditAmt = creditInfo.adjCreditSum/10000;
							creditTerm = creditInfo.adjCreditTerm;
						}
						cusName = creditInfo.cusName;
                        var cusName = creditInfo.cusName;
                        if (cusName.length > 8) {
                            cusName = cusName.substring(0, 8) + "...";
                        }
						creditSts = creditInfo.creditSts;
						htmlStr = '<div class="col-xs-3 col-md-3 column">'
								+ '<button type="button" class="btn btn-font-app padding_left_15" onclick="getCusCreditInfo(\''+ creditAppId + '\');">'
								+ '<i class="i i-applyinfo font-icon"></i>'
								+ '<div class="font-text">授信信息</div>' + '</button>' + '</div>';
						htmlStr = htmlStr + '<div class="col-xs-9 col-md-9 column">'
									+ '<div class="row clearfix"><div class="col-xs-10 col-md-10 column margin_top_20">'
						htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getCusCreditInfo(\''+ creditAppId + '\');" title="'+ creditInfo.cusName + '">' + cusName + '</button>';
						htmlStr = htmlStr
								+ '</div><div class="col-xs-2 col-md-2 column">'
								+ '<button type="button" class="btn btn-font-qiehuan"  style="margin-top: -5px;" onclick="getCusCreditInfo(\''+ creditAppId + '\');"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
								+ '</div></div>';
						var unitStr = "个月";
						htmlStr = htmlStr + '<p>'
								+ '<span class="content-span"><i class="i i-rmb"></i>'+ creditAmt+ '</span><span class="unit-span">万</span>'
								+ '<span class="content-span"><i class="i i-richengshezhi"></i>'+ creditTerm + '</span>' + '<span class="unit-span">'+ unitStr + '</span>'
								+ '</p>' 
								+ '</div>';
						//处理客户分类印证
						var ranHtml = "";
						if(busEntrance == 'cus_credit'){
							if(creditSts == '1'){
								rankHtml = '<div class="i i-warehouse classify_type" style="color:#32b5cb;top:170px;"><div class="classify_type_name" >'+"申请中"+'</div></div>';
							}else if(creditSts == '2'){
								rankHtml = '<div class="i i-warehouse classify_type" style="color:#32b5cb;top:170px;"><div class="classify_type_name" >'+"审批中"+'</div></div>';
							}else if(creditSts == '3'){
								rankHtml = '<div class="i i-warehouse classify_type" style="color:#32b5cb;top:170px;"><div class="classify_type_name" >'+"审批通过"+'</div></div>';
							}else if(creditSts == '4'){
								rankHtml = '<div class="i i-warehouse classify_type" style="color:#32b5cb;top:170px;"><div class="classify_type_name" >'+"已否决"+'</div></div>';
							}else if(creditSts == '5'){
								rankHtml = '<div class="i i-warehouse classify_type" style="color:#32b5cb;top:170px;"><div class="classify_type_name" >'+"已生效"+'</div></div>';
							}else if(creditSts == '7'){
                                rankHtml = '<div class="i i-warehouse classify_type" style="color:#32b5cb;top:170px;"><div class="classify_type_name" >'+"已生效"+'</div></div>';
                            }
						}else{
							if(creditSts == '1'){
								rankHtml = '<div class="i i-warehouse classify_type" style="color:#32b5cb;top:170px;"><div class="classify_type_name" >'+"申请中"+'</div></div>';
							}else if(creditSts == '2'){
								rankHtml = '<div class="i i-warehouse classify_type" style="color:#32b5cb;top:170px;"><div class="classify_type_name" >'+"审批中"+'</div></div>';
							}else if(creditSts == '3'){
								rankHtml = '<div class="i i-warehouse classify_type" style="color:#32b5cb;top:170px;"><div class="classify_type_name" >'+"审批通过"+'</div></div>';
							}else if(creditSts == '4'){
								rankHtml = '<div class="i i-warehouse classify_type" style="color:#32b5cb;top:170px;"><div class="classify_type_name" >'+"已否决"+'</div></div>';
							}else if(creditSts == '5'){
								rankHtml = '<div class="i i-warehouse classify_type" style="color:#32b5cb;top:170px;"><div class="classify_type_name" >'+"已生效"+'</div></div>';
							}
						}
						htmlStr = htmlStr + rankHtml;
						$("#cusCreditInfo").html(htmlStr);
					}
				},
				error:function() {
					alert(top.getMessage("FAILED_SAVE"),0);
				}
			});
		}else{
			$("#cusCreditInfo").hide();
			$("#creditLineDiv").hide();
		}
		
		
	});
	
	function getCusCreditInfo(creditAppId){
		top.LoadingAnimate.start();
		window.location.href=webPath+"/mfCusCreditApply/getCusCreditView?cusNo="+cusNo+"&creditAppId="+creditAppId+"&busEntrance=credit"+"&entrance=credit";
	}
</script>

<!--客户摘要信息2-->
<div id="cusCreditInfo"
	class="row clearfix has-content padding_bottom_20 padding_top_10">
</div>
<div class="row clearfix" id="creditLineDiv">
	<div class="col-xs-12 col-md-12 column">
		<div class="line-div"></div>
	</div>
</div>


