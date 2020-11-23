<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%
	String pactNoList = (String) request.getAttribute("pactNoList");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/ui.min.css" />
		<style type="text/css">
		.report_input{
			height:30px;
		}
		.addAmt-input{
			padding: 10% 25%;
		    height: 32px;
		    line-height: 26px;
		}
		</style>
		
		<script type="text/javascript">
			//获取选择的编号
			var pactNoList='<%=pactNoList%>';
			
		</script>
	</head>
	<body class="overflowHidden">
		
		<div class="container">
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content review_list"  style="height: auto;">
						<div class="addAmt-input">
							<label>提升额度：</label>
				        	<input type="text" name="addAmt" value="" class="report_input" id="addAmt"/>
				        	<!-- onkeyup="value=value.replace(/[^\d.]/g,'');" -->
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存"
				onclick="mfAppBatchAddAmount.saveAddAmt();"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"
					onclick="myclose_click();"></dhcc:thirdButton>
		</div>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
		<script type="text/javascript" src="${webPath}/component/hzey/proquota/js/mfAppBatchAddAmount.js"></script>
	</body>
</html>
