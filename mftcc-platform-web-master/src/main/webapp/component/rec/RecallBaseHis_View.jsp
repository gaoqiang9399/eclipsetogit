<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<style>
		label{font-weight:400;}
		.formCol{ margin-top:10px;}
		table{margin:0;}
		.content_form table tr td{ height:30px;line-height:30px; border-top:1px solid #eeeff1;}
		.content_form table tr th{ height:30px;line-height:30px;font-size:12px;color:#6588b1;}
		.formRow{ margin:0;}
		.table_content .ls_list thead th{border-bottom:1px solid #cfd6e6;}
		#lease table{border:2px solid #d5e2f3;padding:0 30px}
		.bigform_content #lease .ls_list thead tr th{border:none;}
		.bigform_content #lease .ls_list tbody tr:nth-child(odd){background:none; }
		.bigform_content #lease .ls_list tr td:first-child{padding-left:10px; }
		.bigform_content #lease .ls_list tr td:last-child{padding-right:20px; }
		.bigform_content #lease .ls_list tbody tr{ border-top:1px solid #eeeff1;}
		#payPln table{ margin-top:20px;}
		.bigform_content #payPln .ls_list tbody tr td{color:#333;}
		.formLbl {
   			 padding-left: 0;
		}
		</style>
		<script type="text/javascript">
			$(function(){$("body").mCustomScrollbar({
				axis:"y",
				advanced:{ 
					updateOnBrowserResize:true 
				}
			});});
		</script>
	</head>
	<body>
		<div class="bigform_content" style="margin-bottom:40px;margin-left:40px;margin-top:0;padding-top:10px;">
			<div>
				<!-- 还款计划展示 -->	
				<div style="width: 60%;float: right;margin:10px 20px 0px 0;border-left:1px dashed #e4e4e4;padding-left:20px; height:90%;" class="content_table" id="payPln" >
					<span>还款计划</span>
						<dhcc:tableTag property="tableacc0206" paginate="acLnPayPlnList" head="true"></dhcc:tableTag>
					</div> 
				<!-- 详细信息展示 -->
				<div style="width: 39%; padding-right: 30px;">
					<form method="post" theme="simple" name="operform" action="">
						<dhcc:bigFormTag property="formrec0005" mode="query"/>
					</form>	
				</div>
			</div>
		</div>
	</body>
</html>