<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>应收账款转让列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="${webPath}/component/include/tableFour.js?v=${cssJsVersion}"></script>
		<link rel="stylesheet" href="${webPath}/themes/view/css/tableFour.css" />
	</head>
<body class="overflowHidden">
   <div class="bigform_content">
   		<!-- <h3>应收账款列表</h3> -->
		<div class="content_table">
			<div class="input_btn">
				 <dhcc:button value="新增" action="新增" onclick="top.window.openBigForm(webPath+'/mfAccntTransfer/input?pactId=${pactId}','应收账款转让',myclose);"></dhcc:button>
			 </div>
			<div class="table_show">
				<dhcc:tableTag property="tableaccnttrans0001" paginate="mfAccntTransferList" head="true"></dhcc:tableTag>
			</div>
		</div>
    </div>
    <script type="text/javascript">
    /* $(function(){
    	alert('1');
    }); */
    var pactId = '${pactId}';
    function ajaxInprocess(obj,ajaxUrl){
    	var contentForm = $(obj).parents(".content_table");
    	var tableId = contentForm.find(".ls_list").attr("title");
    	jQuery.ajax({
			url:ajaxUrl,
			data:{tableId:tableId,pactId:pactId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					$.myAlert.Alert(top.getMessage("SUCCEED_INSERT_PROCESS"));
					 if(data.tableData!=undefined&&data.tableData!=null){
							var tableHtml = $(data.tableData).find("tbody").html();
						 	contentForm.find(".ls_list tbody").html(tableHtml);
					}
				}else if(data.flag=="error"){
					if(alertFlag){
						 window.parent.window.$.myAlert.Alert(data.msg);
					}else{
						$.myAlert.Alert(data.msg);
					}
				}
			},error:function(data){
				if(alertFlag){
					 window.parent.window.$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
				}else{
					$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
				}
			}
		});
    };
    </script>
</body>
</html>