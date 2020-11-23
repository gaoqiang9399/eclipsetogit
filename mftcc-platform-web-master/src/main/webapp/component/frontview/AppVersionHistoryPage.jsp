<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<% 
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
</head>
<script type="text/javascript">
	$(function(){
		$.ajax({
			url:webPath+'/mfAppUpdateSetting/getHistoryVersion',
			type:'post',
			data:"",
			async:false,
			dataType:'json',
			success:function(data){
				if(data.flag="success"){
					var fileNames=data.fileNames;
					var htmlStr=""
					for(var i =0;i<fileNames.length;i++){
						if (i%4==0){  
							htmlStr+='<tr>';
						}
						htmlStr+='<td align="center">'+fileNames[i]+'</td>'
						if(i%4==3){
							htmlStr+='</tr>'
						}
					}
					$("#tab").append(htmlStr);
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	});
</script>
	<body class="bg-white" style="overflow-y: hidden;">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div col-xs-6 pull-right"">
						<div class="znsearch-div">
							<div class="input-group pull-right">
							</div>
						</div>
						</div>
					</div>
				</div>
				<!--页面显示区域-->
				<div class="row clearfix">
					<div class="col-md-12 column">
						<div id="content" class="table_content"  style="height: auto;">
							<table style="" id="tablist" width="100%" border="0" align="center" cellspacing="1" class="ls_list" title="tablekindlist0001">
								<thead style="">
									<tr>
										<th scope="col" align="center" name="" sorttype="0">版本</th>
										<th scope="col" colspan="1" name="" align="center">版本</th>
										<th scope="col" align="center" name="" sorttype="0">版本</th>
										<th scope="col" colspan="1" name="	" align="center">版本</th>
									</tr>
								</thead>
								<tbody id="tab">
									
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
	</body>
</html>