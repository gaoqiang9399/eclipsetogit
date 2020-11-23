<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>试算平衡页面</title>
	
	<style type="text/css">
		.balsd{
			height: 80px;
		}
		.basps{
			padding-left: 270px;
			line-height: 80px;
			color:red;
			display: none;
		}
		.pasbala{
			padding-left: 270px;
			line-height: 80px;
			color:#32B5CB;
			display: none;
		}
	</style>
	</head>
	<body class="overflowHidden bg-white">
		<div class="balsd">
			<span class="basps" id="nobalance" ><font>您录入的初始余额不平衡！</font></span>
			<span class="pasbala" id="yesbalance"><font>恭喜您，您录入的初始余额平衡！</font></span>
			
		</div>
		<div class="table_content">
		<table id="pzInfotable" width="100%" border="0" align="center"
			cellspacing="1" class="ls_list" >
			<thead>
				<tr>
					<th scope="col" width="25%" align="center" name="accNo">项目</th>
					<th scope="col" width="25%" align="center" name="accName">借方金额</th>
					<th scope="col" width="25%" align="center" name="accName">贷方金额</th>
					<th scope="col" width="25%" align="center" name="accName">差额</th>
				</tr>
			</thead>
			<tbody id="tab">
				<tr>
					<td width="25%" align="center">期初余额</td>
					<td width="25%" align="center"><span id="jieamt"></span></td>
					<td width="25%" align="center"><span id="daiamt"></span></td>
					<td width="25%" align="center"><span id="chaamt"></span></td>
				</tr>
				
			</tbody>
		</table>
		</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_showDialog();"></dhcc:thirdButton>
				<!-- myclose_showDialog   myclose_click-->
			</div>
		</div>
	</body>
	<script type="text/javascript">
	//var dataMap = '${dataMap}';
	var jsondata = '${jsondata}';
	$(function(){
		var smap = $.parseJSON(jsondata);
		$("#jieamt").text(smap.datamap.jieamt);
		$("#daiamt").text(smap.datamap.daiamt);
		$("#chaamt").text(smap.datamap.chaamt);
		var flag =smap.datamap.chaflag;
		if(flag==1){//不平衡
			$("#nobalance").show();
		}else{
			$("#yesbalance").show();
		}
		
		/* $.ajax({
			url:'${webPath}/cwInitBal/checkBalDataAjax',
			type:'post',
			data:null,
			async:false,
			dataType:'json',
			error:function(){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			},
			success:function(data){
				if(data.flag == "success"){
					$("#jieamt").text(data.datamap.jieamt);
					$("#daiamt").text(data.datamap.daiamt);
					$("#chaamt").text(data.datamap.chaamt);
					var flag = data.datamap.chaflag;
					if(flag==1){//不平衡
						$("#nobalance").show();
					}else{
						$("#yesbalance").show();
					}
				}
			}
		}); */
	});
	
		
	</script>
</html>
