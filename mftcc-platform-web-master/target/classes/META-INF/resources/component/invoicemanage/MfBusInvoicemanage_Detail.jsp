<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<css type="text/css" src="component/invoicemanage/css/invoiceGrid.css"></css>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" >
			var invoiceId = '${mfBusInvoicemanage.invoiceId}';
			var cusNo='${mfBusInvoicemanage.cusNo}';
            var appId='${mfBusInvoicemanage.appId}';
            var pactId='${mfBusInvoicemanage.pactId}';
			var invoiceAmount = '${mfBusInvoicemanage.invoiceTotaltaxprice}';
			invoiceAmount = parseFloat(invoiceAmount).toFixed(2);
			invoiceAmount = invoiceAmount.replace(".","");
			var invoiceStatus = ${mfBusInvoicemanage.invoiceStatus};
			//解析发票金额各个位数的数字
			$(function () {
			        var trInvoiceAmount = $("#invoiceTotaltaxprice").find("td");
			        for (var j = trInvoiceAmount.length-1;j >= 0 ;j--){
			            /*if(j == 9){
                            trInvoiceAmount.eq(j).html("0");
                        }*/if (j-(trInvoiceAmount.length-invoiceAmount.length)+1 >= 0){
                            trInvoiceAmount.eq(j+1).html(invoiceAmount.substring(j-(trInvoiceAmount.length-invoiceAmount.length)+1,j-(trInvoiceAmount.length-invoiceAmount.length)+2));
						}
					};
			        getNowFormatDate();
			        //开过发票不可修改
			        if(invoiceStatus == 2){
                        $('input').attr("readonly","readonly");
                        $('input').each(function () {
                            this.style.borderBottom = "none";
                        })
					}
            });
			//保存发票已开状态
			function ajaxSaveDetail() {
			    var ext2 = $("#ext2").val();
			    var ext3 = $("#ext3").val();
			    var ext4 = $("#ext4").val();
			    var ext5 = $("#ext5").val();
			    var ext7 = $("#ext7").val();
			    var ext8 = $("#ext8").val();
			    var ext9 = $("#ext9").val();
                var dataInput = {
                    invoiceId:invoiceId,
                    ext2:ext2,
                    ext3:ext3,
                    ext4:ext4,
                    ext5:ext5,
                    ext7:ext7,
                    ext8:ext8,
                    ext9:ext9
                };
                var path = webPath + "/mfBusInvoicemanage/saveInvoiceDetailByPageAjax";
                $.get(path,dataInput,function (data) {
                    if (data.flag == "success") {
                        window.top.alert(data.msg, 3);
                        myclose_click();
                        updateTableData();
                    } else {
                        alert(data.msg, 0);
                    }
                });
            };
			//获取当前时间
            function getNowFormatDate() {
                var date = new Date();
                var seperator1 = "-";
                var seperator2 = ":";
                var month = date.getMonth() + 1;
                var strDate = date.getDate();
                if (month >= 1 && month <= 9) {
                    month = "0" + month;
                }
                if (strDate >= 0 && strDate <= 9) {
                    strDate = "0" + strDate;
                }
                var currentdate = date.getFullYear() + "年" + month + "月" + strDate
                    + "日" /*+ date.getHours() + seperator2 + date.getMinutes()
                    + seperator2 + date.getSeconds()*/;
                $("#currentdate").text(currentdate);
            }
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span12">
						<div align="center"><h3>开具发票通知单</h3></div>
						<div align="right" id="currentdate"></div>
						<div class="mydiv" style="margin: auto;">
						<table class="table table-bordered" id="table">
							<tbody>
							<tr>
								<td align="center"><small>经办部门</small></td>
								<td align="center"><small>${mfBusInvoicemanage.brName}</small></td>
								<td style="text-align: center;"><small>经办人</small></td>
								<td align="center"><small>${mfBusInvoicemanage.opName}</small></td>
								<td colspan="5" rowspan="1" align="center"><small>发票类型</small></td>
								<c:if test="${mfBusInvoicemanage.invoiceType == 1}"><td colspan="5" rowspan="1" align="center"><small>专用</small></td></c:if>
								<c:if test="${mfBusInvoicemanage.invoiceType == 2}"><td colspan="5" rowspan="1" align="center"><small>普通</small></td></c:if>
								<c:if test="${mfBusInvoicemanage.invoiceType == 3}"><td colspan="5" rowspan="1" align="center"><small>无票</small></td></c:if>
								<c:if test="${mfBusInvoicemanage.invoiceType == null}"><td colspan="5" rowspan="1" align="center"><small></small></td></c:if>
							</tr>
							<tr>
								<td colspan="1" rowspan="2" style="text-align: center; line-height: 35px"><small><br>发票金额</small></td>
								<td colspan="3" rowspan="2" style="text-align: center; line-height: 35px">
									<small><br>人民币（大写）：${mfBusInvoicemanage.invoiceAmountUpperCase}</small>
								</td>
								<td align="center"><small>千 <span><br>万</span></small></td>
								<td align="center"><small>百<br> 万</small></td>
								<td align="center"><small>十<br> 万</small></td>
								<td style="text-align: center; line-height: 45px"><small>万</small></td>
								<td style="text-align: center; line-height: 45px"><small>千</small></td>
								<td style="text-align: center; line-height: 45px"><small>百</small></td>
								<td style="text-align: center; line-height: 45px"><small>十</small></td>
								<td style="text-align: center; line-height: 45px"><small>元</small></td>
								<td style="text-align: center; line-height: 45px"><small>角</small></td>
								<td style="text-align: center; line-height: 45px"><small>分</small></td>
							</tr>
							<tr id="invoiceTotaltaxprice">
								<td style="text-align: center; line-height: 45px"><small></small></td>
								<td style="text-align: center; line-height: 45px"><small></small></td>
								<td style="text-align: center; line-height: 45px"><small></small></td>
								<td style="text-align: center; line-height: 45px"><small></small></td>
								<td style="text-align: center; line-height: 45px"><small></small></td>
								<td style="text-align: center; line-height: 45px"><small></small></td>
								<td style="text-align: center; line-height: 45px"><small></small></td>
								<td style="text-align: center; line-height: 45px"><small></small></td>
								<td style="text-align: center; line-height: 45px"><small></small></td>
								<td style="text-align: center; line-height: 45px"><small></small></td>
							</tr>
							<tr>
								<td colspan="3" rowspan="3">
									<small>
									<br>借据号： ${mfBusInvoicemanage.fincShowId}<br>
									合同号：${mfBusInvoicemanage.pactNo}</small>
								</td>
								<td align="center"><small>购货单位名称</small></td>
								<td colspan="10" rowspan="1"><small>${mfBusInvoicemanage.cusName}</small></td>
							</tr>
							<tr>
								<td align="center"><small>纳税人识别号</small></td>
								<%--<td colspan="10" rowspan="1"><small><input type="text" id="ext7" style="outline:medium;border:none;border-bottom:#8D8D8D 1px solid;width: 80%" value="${mfBusInvoicemanage.ext7}"></small></td>--%>
								<td colspan="10" rowspan="1"><small>${mfBusInvoicemanage.idNum}</small></td>

							<%--<c:if test="${mfBusInvoicemanage.ext7 == null}">
									<td colspan="10" rowspan="1"><small><input type="text" id="ext7" style="outline:medium;border:none;border-bottom:#8D8D8D 1px solid;width: 80%" value="${mfBusInvoicemanage.ext7}"></small></td>
								</c:if>
								<c:if test="${mfBusInvoicemanage.ext7 != null}">
									<td colspan="10" rowspan="1"><small><input type="text" style="outline:medium;border:none;" readonly = "readonly" value="${mfBusInvoicemanage.ext7}"></small></td>
								</c:if>--%>
							</tr>
							<tr>
								<td align="center"><small>开户行及帐号</small></td>
								<td colspan="10" rowspan="1"><small>${map.bankAndNumber}</small></td>
							</tr>
							<tr>
								<td colspan="1" rowspan="2" style="text-align: center; line-height: 25px"><small>审<br> 批</small></td>
								<td><small>部门负责人：&nbsp;<input type="text" id="ext6" style="outline:medium;border:none;border-bottom:#8D8D8D 1px solid;" value="${mfBusInvoicemanage.ext6}"></small></td>
								<td><small>分管副总经理：&nbsp;<input type="text" id="ext3" style="outline:medium;border:none;border-bottom:#8D8D8D 1px solid;" value="${mfBusInvoicemanage.ext3}"></small></td>
								<td colspan="6" rowspan="2" style="text-align: center; line-height: 55px;"><small>总经理：&nbsp;<input type="text" id="ext8" style="outline:medium;border:none;border-bottom:#8D8D8D 1px solid;line-height: 20px;" value="${mfBusInvoicemanage.ext8}"></small></td>
								<td colspan="5" rowspan="2" style="text-align: center; line-height: 55px;"><small>董事长：&nbsp;<input type="text" id="ext9" style="outline:medium;border:none;border-bottom:#8D8D8D 1px solid;line-height: 20px;" value="${mfBusInvoicemanage.ext9}"></small></td>
								<%--<c:if test="${mfBusInvoicemanage.ext2 == null}">
									<td><small>部门负责人：&nbsp;<input type="text" id="ext2" style="outline:medium;border:none;border-bottom:#8D8D8D 1px solid;" value="${mfBusInvoicemanage.ext2}"></small></td>
								</c:if>
								<c:if test="${mfBusInvoicemanage.ext2 != null}">
									<td><small>部门负责人：&nbsp;<input type="text" style="outline:medium;border:none;" readonly = "readonly" value="${mfBusInvoicemanage.ext2}"></small></td>
								</c:if>
								<c:if test="${mfBusInvoicemanage.ext3 == null}">
									<td><small>分管副总经理：&nbsp;<input type="text" id="ext3" style="outline:medium;border:none;border-bottom:#8D8D8D 1px solid;" value="${mfBusInvoicemanage.ext3}"></small></td>
								</c:if>
								<c:if test="${mfBusInvoicemanage.ext3 != null}">
									<td><small>分管副总经理：&nbsp;<input type="text" style="outline:medium;border:none;" readonly = "readonly" value="${mfBusInvoicemanage.ext3}"></small></td>
								</c:if>
								<c:if test="${mfBusInvoicemanage.ext8 == null}">
									<td colspan="6" rowspan="2" style="text-align: center; line-height: 55px;"><small>总经理：&nbsp;<input type="text" id="ext8" style="outline:medium;border:none;border-bottom:#8D8D8D 1px solid;line-height: 20px;" value="${mfBusInvoicemanage.ext8}"></small></td>
								</c:if>
								<c:if test="${mfBusInvoicemanage.ext8 != null}">
									<td colspan="6" rowspan="2" style="text-align: center; line-height: 55px;"><small>总经理：&nbsp;<input type="text" style="outline:medium;border:none;line-height: 20px;" readonly = "readonly" value="${mfBusInvoicemanage.ext8}"></small></td>
								</c:if>
								<c:if test="${mfBusInvoicemanage.ext9 == null}">
									<td colspan="5" rowspan="2" style="text-align: center; line-height: 55px;"><small>董事长：&nbsp;<input type="text" id="ext9" style="outline:medium;border:none;border-bottom:#8D8D8D 1px solid;line-height: 20px;" value="${mfBusInvoicemanage.ext9}"></small></td>
								</c:if>
								<c:if test="${mfBusInvoicemanage.ext9 != null}">
									<td colspan="5" rowspan="2" style="text-align: center; line-height: 55px;"><small>董事长：&nbsp;<input type="text" style="outline:medium;border:none;line-height: 20px;" readonly = "readonly" value="${mfBusInvoicemanage.ext9}"></small></td>
								</c:if>--%>
							</tr>
							<tr>
								<td><small>财务部复核：&nbsp;<input type="text" id="ext4" style="outline:medium;border:none;border-bottom:#8D8D8D 1px solid;" value="${mfBusInvoicemanage.ext4}"></small></td>
								<td><small>财务部负责人：&nbsp;<input type="text" id="ext5" style="outline:medium;border:none;border-bottom:#8D8D8D 1px solid;" value="${mfBusInvoicemanage.ext5}"></small></td>
								<%--<c:if test="${mfBusInvoicemanage.ext4 == null}">
									<td><small>财务部复核：&nbsp;<input type="text" id="ext4" style="outline:medium;border:none;border-bottom:#8D8D8D 1px solid;" value="${mfBusInvoicemanage.ext4}"></small></td>
								</c:if>
								<c:if test="${mfBusInvoicemanage.ext4 != null}">
									<td><small>财务部复核：&nbsp;<input type="text" style="outline:medium;border:none;" readonly = "readonly" value="${mfBusInvoicemanage.ext4}"></small></td>
								</c:if>
								<c:if test="${mfBusInvoicemanage.ext5 == null}">
									<td><small>财务部负责人：&nbsp;<input type="text" id="ext5" style="outline:medium;border:none;border-bottom:#8D8D8D 1px solid;" value="${mfBusInvoicemanage.ext5}"></small></td>
								</c:if>
								<c:if test="${mfBusInvoicemanage.ext5 != null}">
									<td><small>财务部负责人：&nbsp;<input type="text" style="outline:medium;border:none;" readonly = "readonly" value="${mfBusInvoicemanage.ext5}"></small></td>
								</c:if>--%>
								</td>
							</tr>
							</tbody>
						</table>
						</div>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
				<dhcc:thirdButton value="打印" action="打印" onclick="MfBusInvoicemanage_List.printApproveTable(${mfBusInvoicemanage.fincId},invoiceId,cusNo,appId,pactId)"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click()"></dhcc:thirdButton>
	   		</div>	
	   	</div>
		<script type="text/javascript" src="${webPath}/component/invoicemanage/js/MfBusInvoicemanage_List.js"></script>
	</body>
</html>