<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/js/openDiv.js"></script>
		
		
		
	</head>
<body>
		<!--页面显示区域-->
		<div class="row clearfix">
			<div class="col-md-12 column">
				<!-- <div id="content" class="table_content cw_voucher_list"
					style="height: auto;">
					
				</div> -->
			
			</div>
			
		</div>
	</div>
	<div class="scroll-content">
	<div id="zhenglitableDiv" class="table_content pzdivsty">

		<table id="reportCheck" width="100%" border="0" align="center"
			cellspacing="1" class="ls_list" title="reportCheck">
			<thead>
				<tr>
					<th scope="col" width="30%" align="center" name="accNo">编号</th>
					<th scope="col" width="30%" align="center" name="accName">名称</th>

				</tr>
			</thead>
			<tbody id="tab">
				
			</tbody>
		</table>
	</div>
	</div>
	
	
	<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<%-- <dhcc:thirdButton value="取消" action="取消" typeclass="myclose" onclick="myclose();"></dhcc:thirdButton> --%>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
	
</body>
<script type="text/javascript">
	var data = '${dataMap.dataJson}';
	$(function(){
		if(data){
			var accList = $.parseJSON('${dataMap.dataJson}');
			if(accList.length>0){
				for(var i = 0;i < accList.length; i++){
					var $tr = $('<tr><td align="center" width="30%">'+accList[i].accNo+'</td><td align="center" width="70%">'+accList[i].accName+'</td></tr>');
					$("#tab").append($tr);
				}
			}else{
				var $tr = $('<tr><td style="text-align: center;" colspan="2">暂无数据</td></tr>');
				$("#tab").append($tr);
			}
		}else{
			var $tr = $('<tr><td style="text-align: center;" colspan="2">暂无数据</td></tr>');
			$("#tab").append($tr);
		}
		
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		
	});
	
	function myclose(){
		$(parent.document).find("#showDialog .close").click();
	}
</script>

</html>