<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/include/message.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/jquery.placeholder.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link type="text/css" rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
		
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<%-- <link type="text/css" rel="stylesheet" href="${webPath}/themes/view/css/rcswitcherModify.css" /> --%>
		<style type="text/css">
			.col_border{
				border: 2px solid #429DDF;
				margin: 5px 5px;
				padding:0;
			}
			.table_content .ls_list thead th {
			        color: #000;
				    font-size: 12px;
				    border: none;
				    background: none;
				    font-weight: bold;
				    border-bottom: 1px solid #ccc;
				    padding: 0 5px;
			}
			.col_border{
				border:2px solid #F2F5FB;
				border-radius: 4px;
				margin: 5px;
				height: 100%;
			}
			.tab_title{
				color: #00b5f2;
			}
			.tab_title h5{
				margin: 0;
				padding: 0;
				padding-top: 4px;
				color:#6D96B1;
				
			}
			.row{
				margin: 0;
				padding: 0;
			}
			.row.title_btn {
			    margin-bottom: 5px;
			    margin-top: 8px;
			}
			.tab_btn input[type="button"]{
			    background-color: #1e94cc;
			    border: medium none;
			    border-radius: 2px;
			    color: #fff;
			    height: 22px;
			    margin: 1px 5px 0;
			    padding: 0 10px;
			    width: auto;
			    float: right;
			}
			.table_content .ls_list tbody {
				font-size: 12px;
			}
			.table_content .ls_list tr {
			    line-height: 37px;
			}
			.table_content .ls_list tbody tr td{
			    padding:0 5px;
			}
			input, button, select, textarea{
				line-height:normal;
			}
			.table_content .ls_list tr:nth-child(2n) {
			    background-attachment: scroll;
			    background-clip: border-box;
			    background-color: #f9f9f9;
			    background-image: none;
			    background-origin: padding-box;
			    background-position: 0 0;
			    background-repeat: repeat;
			    background-size: auto auto;
			}
			.table_content .ls_list tbody tr:hover{
				background:#f5f5f5;
			}
			/*.swraper.light{
				margin-left: 10px;
				top: -2px;
			}
			.swraper.light .stoggler {
				font-size: 12px;
			}
			.swraper.light {
				border-radius: 15px;
			}
			.swraper.light .stoggler .sblob {
				border-radius: 15px;
			}
			.swraper.light .stoggler.on {
			    background: rgba(0, 0, 0, 0) linear-gradient(#4894cd, #4894cd) repeat scroll 0 0;
			}
			.swraper.light .stoggler{
			    background: rgba(0, 0, 0, 0) linear-gradient(#cfcfcf, #f5f5f5) repeat scroll 0 0;
			}
			.swraper.light .stoggler .sblob {
			    background: rgba(0, 0, 0, 0) linear-gradient(#f5f5f5, #cfcfcf) repeat scroll 0 0;
			    border-radius: 99px;
			}
			.swraper .stoggler .slabel-on,.swraper .stoggler .slabel-off{
				font-size: 11px;
			}*/
			td>span.showtext{
			    border-radius: 4px;
			    padding: 2px 5px;
			    color: white;
    			font-weight: bold;
			}
			.blue td>span.showtext{
				background-color: #88C1E9;
			}
			.green td>span.showtext{
				background-color: #92E988;
			}
			.red td>span.showtext{
				background-color: #D84C29;
			}
			.orange td>span.showtext{
				background-color: #FFA668;
			}
			.hidden_long {
			    -moz-binding: url("ellipsis.xml#ellipsis");
			    display: inline-block;
			    font-size: 12px;
			    max-width: 200px;
			    overflow-x: hidden;
			    overflow-y: hidden;
			    text-overflow: ellipsis;
			    white-space: nowrap;
			}
			.swraper.lease .stoggler.on{ background:#1e94cc;}
		</style>
		<script type="text/javascript">
			var intiTab = function(){
				$.ajax({
					type:"post",
					url:webPath+"/secAuditConfig/getAllData",
					data:{tableId:"tablesec0001",tableType:"thirdTableTag"},
					async:false,
					success:function(data){
						if(data.flag=="success"){
							$("#pwSet").find(".ls_list tbody").html($(data.pwSetTableData).find("tbody").html());
							$("#pwError").find(".ls_list tbody").html($(data.pwErrorTableData).find("tbody").html());
							$("#pwUpdate").find(".ls_list tbody").html($(data.pwUpdateTableData).find("tbody").html());
							$("#access").find(".ls_list tbody").html($(data.accessTableData).find("tbody").html());
						}else{
							alert("加载失败");
						}
					},error:function(){
						
					}
				});
			}
			$(function(){
				intiTab();
				var isUseindex = $("#pwSet th[name='isUse']").index();
				var itemValuesIndex = $("#pwSet th[name='itemValues']").index();
				var isEditIndex = $("#pwSet th[name='isEdit']").index();
				var codeTypeIndex = $("#pwSet th[name='codeType']").index();
				var itemNameIndex = $("#pwSet th[name='itemName']").index();
				$(".table_content .ls_list tbody tr").each(function(){
					var thisTr = $(this);
					var val = thisTr.find("td").eq(isUseindex).html().trim();
					var isEditVal = thisTr.find("td").eq(isEditIndex).html().trim();
					if(isEditVal=="是"){
						var itemValuesVal  = thisTr.find("td").eq(itemValuesIndex).html().trim();
						var tdWidth = thisTr.find("td").width();
						thisTr.find("td").eq(itemValuesIndex).html('<input type="text" name="itemValues" value="'+itemValuesVal+'" style="width:99%;text-align: center;"/>');
					}
					if(val=="0"){
						thisTr.find("td").eq(isUseindex).html('<input type="checkbox" name="isUse" value="0"/>');
					}else if(val=="1"){
						thisTr.find("td").eq(isUseindex).html('<input type="checkbox" name="isUse" value="1" checked/>');
					}
					thisTr.find("td").eq(isUseindex).children().rcSwitcher({
						width: 50,
						height: 20,
						theme: 'lease',
						blobOffset: 1,
						onText:'是',
						offText:'否'
					});
					var codeTypeVal = thisTr.find("td").eq(codeTypeIndex).html().trim();
					thisTr.find("td").eq(codeTypeIndex).html('<span class="showtext">'+codeTypeVal+'</span>');
					var itemNameVal = thisTr.find("td").eq(itemNameIndex).html().trim();
					thisTr.find("td").eq(itemNameIndex).attr("title",itemNameVal);
					thisTr.find("td").eq(itemNameIndex).html('<span class="hidden_long">'+itemNameVal+'</span>');
				});
			});
			function saveTab(obj){
				$obj = $(obj);
				console.log("点击");
				var updateFlag = false;
				var datas = [];
				$obj.parents(".table_content").find(".ls_list tbody tr").each(function(index){
					if($(this).find("td").eq(0).find("input[type='checkbox']").is(':checked')){
						var entity = {};
						if(!updateFlag){
							updateFlag = true;
						}
						var itemNoindex = $("#pwSet th[name='itemNo']").index();
						var codeTypeIndex = $("#pwSet th[name='codeType']").index();
						var itemNo = $(this).find("td").eq(itemNoindex).text().trim();
						var codeType = $(this).find("td").eq(codeTypeIndex).text().trim();
						entity.itemNo = itemNo;
						entity.codeType = codeType;
						$(this).find("td").each(function(index){
							if(index>0){
								if($(this).find("input").attr("type")=="text"){
									var name = $(this).find("input").attr("name");
									entity[name] = $(this).find("input").val();
								}else if($(this).find("input").attr("type")=="checkbox"){
									var name = $(this).find("input").attr("name");
									entity[name] = $(this).find("input").is(':checked')?'1':'0';
								}
							}
						});
						datas.push(entity);
					}
				});
				
				if(updateFlag){
					$.ajax({
						type:"post",
						url:webPath+"/secAuditConfig/updateListAjax",
						data:{ajaxData:JSON.stringify(datas)},
						async:true,
						success:function(data){
							if(data.flag=="success"){
								//$.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
								alert(top.getMessage("SUCCEED_OPERATION"),1);
								$obj.parents(".table_content").find(".ls_list tbody tr").each(function(index){
									if($(this).find("td").eq(0).find("input[type='checkbox']").is(':checked')){
										$(this).find("td").eq(0).find("input[type='checkbox']").prop("checked",false);
									}
								});
							}else if(data.flag=="error"){
								//$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
								alert(top.getMessage("FAILED_OPERATION"," "),0);
							}
						},error:function(){
							//$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
							alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
					});
				}else{
					//$.myAlert.Alert("保存前请选择");
					alert("保存前请选择",0);
				}
			}
		</script>
	</head>
	<body>
		<div class="content">
			<div class="row">
				<div class="col-lg-6" style="height: 93.8%;">
					<div id="pwSet" class="table_content col_border">
						<div class="row title_btn">
							<div class="col-lg-3 tab_title">
								<h5>密码校验规则</h5>
							</div>
							<div class="col-lg-9 tab_btn">
								<input type="button" value="保存" onclick="saveTab(this)"/>
							</div>
						</div>
						<div class="row">
							<div class="col-lg-12 blue">
								<dhcc:tableTag paginate="secAuditConfigList" property="tablesec0001" head="true"></dhcc:tableTag>
							</div>
						</div>
					</div>
				</div>
				<div class="col-lg-6">
					<div class="row">
						<div class="col-lg-12 col_border" style="height: 25%;min-height: 120px">
							<div id="pwUpdate" class="table_content">
								<div class="row title_btn">
									<div class="col-lg-3 tab_title">
										<h5>安全选项设置</h5>
									</div>
									<div class="col-lg-9 tab_btn">
										<input type="button" value="保存" onclick="saveTab(this)"/>
									</div>
								</div>
								<div class="row">
									<div class="col-lg-12 green">
										<dhcc:tableTag paginate="secAuditConfigList" property="tablesec0001" head="true"></dhcc:tableTag>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-12 col_border" style="height:25%;min-height: 120px;">
							<div id="pwError" class="table_content">
								<div class="row title_btn">
									<div class="col-lg-3 tab_title">
										<h5>密码错误设置</h5>
									</div>
									<div class="col-lg-9 tab_btn">
										<input type="button" value="保存" onclick="saveTab(this)"/>
									</div>
								</div>
								<div class="row">
									<div class="col-lg-12 red">
										<dhcc:tableTag paginate="secAuditConfigList" property="tablesec0001" head="true"></dhcc:tableTag>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-12 col_border" style="height: 40%;min-height: 140px">
							<div id="access" class="table_content">
								<div class="row title_btn">
									<div class="col-lg-3 tab_title">
										<h5>访问记录设置</h5>
									</div>
									<div class="col-lg-9 tab_btn">
										<input type="button" value="保存" onclick="saveTab(this)"/>
									</div>
								</div>
								<div class="row">
									<div class="col-lg-12 orange">
										<dhcc:tableTag paginate="secAuditConfigList" property="tablesec0001" head="true"></dhcc:tableTag>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>