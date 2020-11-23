<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/include/message.jsp" %>
<%
String contextPath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+webPath+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<link type="text/css" rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
		<style type="text/css">
			.col_border{
				border: 2px solid #429DDF;
				margin: 0px 0px;
			}
			.table_content .ls_list thead th {
			    background: rgba(0, 0, 0, 0) none repeat scroll 0 0;
			    border-bottom:1px solid #e9ebf2;
			    color: #000000;
			    font-size: 12px;
			    font-weight: normal;
			}
			.col_border{
				border:2px solid #F2F5FB;
				border-radius: 0px;
				margin: 0px;
				height: 100%;
			}
			.tab_title{
				color: #32b5cb;
			}
			.tab_title h5{
				margin: 0;
				padding: 0;
				padding-left: 10px;
				padding-top: 4px;
				
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
			    background-color: #54aced;
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
			    line-height: 40px;
			}
			.table_content .ls_list tr:nth-child(2n) {
			    background-attachment: scroll;
			    background-clip: border-box;
			    background-color: #f7f8fc;
			    background-image: none;
			    background-origin: padding-box;
			    background-position: 0 0;
			    background-repeat: repeat;
			    background-size: auto auto;
			}
			.table_content .ls_list tr:nth-child(2n) {
			    background-attachment: scroll;
			    background-clip: border-box;
			    background-color: #f7f8fc;
			    background-image: none;
			    background-origin: padding-box;
			    background-position: 0 0;
			    background-repeat: repeat;
			    background-size: auto auto;
			}
			
			.table_content .ls_list tr:hover {
			    background: #f0fdff;
			}	
			.swraper.light{
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
			}
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
			.saverow {
   				background: rgba(0, 0, 0, 0) url("component/pms/imgs/tipsico.png") no-repeat scroll -65px -1px;
    			height: 23px;
   				 margin-top: 1px !important;
    			width: 56px;
    			display: none;
    		}
    		.updrow {
   				background: rgba(0, 0, 0, 0) url("component/pms/imgs/tipsico.png") no-repeat scroll 1px 1px;
    			height: 20px;
				width: 10px;
    		}
    		.colrow {
    			background: rgba(0, 0, 0, 0) url("component/pms/imgs/tipsico.png") no-repeat scroll -75px -51px;
    			height: 23px;
    			margin-top: 1px !important;
    			width: 46px;
    			display: none;
			}
				
			.btn {
			  padding: 6px 10px;
			}
		</style>
		<script type="text/javascript">
		/*一次只可编辑一行，以下几个参数是用来存当前编辑行的信息，
		当有某行A在编辑状态时，再点击其他行B进行编辑，则先把行A取消编辑状态，信息还原，再把行B设置为编辑状态  */
		//是否有在编辑的行
		var isEditAll = false;
		//编辑行参数值
		var warmValueAll = 0;
		//编辑行的isStart
		var isStartAll = 0;
		//编辑状态的行
		var editTrAll;
			var intiTab = function(){
				$.ajax({
					type:"post",
					url:webPath+"/collateralWarningDetail/findByPageAjax",
					data:{tableId:"tabledlcollateralwarningdetail0002",tableType:"thirdTableTag",warnNo:"${warnNo}"},
					async:false,
					success:function(data){
						if(data.flag=="success"){
							$("#warnParmSet").find(".ls_list tbody").html($(data.tableData).find("tbody").html());
						}else{
							alert("加载失败");
						}
					},error:function(){
						
					}
				});
			}
			/*<button name="save" class="btn saverow btn-mini showBtn curSelectedBtn" href="#"></button>
				<button name="cancel" class="btn colrow btn-mini showBtn curSelectedBtn" href="#"></button>
				<button name="update" class="btn updRow btn-mini showBtn curSelectedBtn" href="#"></button>
				*/
			$(function(){
				intiTab();
				var editBtnIndex = $("#warnParmSet th[name='editBtn']").index();
				$(".table_content .ls_list tbody tr").each(function(){
					var thisTr = $(this);
					
					thisTr.find("td").eq(editBtnIndex).html('<button name="save" class="btn saverow btn-mini showBtn curSelectedBtn showBtna" onclick="saveTab(this);"></button>	<button name="cancel" class="btn colrow btn-mini showBtn curSelectedBtn showBtna" onclick="cancel(this);"></button><button name="update" class="btn updRow btn-mini showBtn curSelectedBtn showBtnb" onclick="edit(this)"></button>');

				});
				
			});
			function edit(obj){
				//如果有行在编辑状态，则先把原来的行还原
				if(isEditAll){
					if(editTrAll == undefined ){
						
					}else{
						var isStartindex = $("#warnParmSet th[name='isStart']").index();
						var warnUnitIndex = $("#warnParmSet th[name='warnUnit']").index();
						var itemValuesIndex = $("#warnParmSet th[name='warnValue']").index();
						var editBtnIndex = $("#warnParmSet th[name='editBtn']").index();
						var thisTr1 = editTrAll;
						var thisTd1 = thisTr1.find("td").eq(editBtnIndex);
						
						thisTd1.find(".showBtna").hide();
						thisTd1.find(".showBtnb").show();
						//var value = thisTr1.find('td').eq(itemValuesIndex).find("input").val();
						thisTr1.find('td').eq(itemValuesIndex).html(warmValueAll);
						thisTr1.find('td').eq(isStartindex).html(isStartAll);
					}
				}else{
					
				}
				isEditAll = true;
				var isStartindex = $("#warnParmSet th[name='isStart']").index();
				var warnUnitIndex = $("#warnParmSet th[name='warnUnit']").index();
				var itemValuesIndex = $("#warnParmSet th[name='warnValue']").index();
				var isEditIndex = $("#warnParmSet th[name='isEdit']").index();
				var thisTr = $(obj).parents("tr");
				var thisTd = $(obj).parents("td");
				thisTd.find(".showBtna").show();
				thisTd.find(".showBtnb").hide();
				
				editTrAll = thisTr;
				
				var val = thisTr.find("td").eq(isStartindex).html().trim();
				isStartAll = val;
				
				//var isEditVal = thisTr.find("td").eq(isEditIndex).html().trim();
				//if(isEditVal=="是"){
					var itemValuesVal  = thisTr.find("td").eq(itemValuesIndex).html().trim();
					warmValueAll = itemValuesVal;
					console.log("warmValueAll="+warmValueAll);
					var tdWidth = thisTr.find("td").width();
					thisTr.find("td").eq(itemValuesIndex).html('<input type="text" name="warmValue" value="'+itemValuesVal+'" style="width:99%;text-align: center;"/>');
				//}
				
				
				if(val=="禁用"){
					thisTr.find("td").eq(isStartindex).html('<input type="checkbox" name="isStart" value="0"/>');
				}else if(val=="启用"){
					thisTr.find("td").eq(isStartindex).html('<input type="checkbox" name="isStart" value="1" checked/>');
				}
				thisTr.find("td").eq(isStartindex).children().rcSwitcher({
					width: 50,
					height: 18,
					theme: 'light',
					blobOffset: 1,
					onText:'是',
					offText:'否'
				});
			};
			function cancel(obj){
				if(editTrAll == undefined ){
				}else{
					var isStartindex = $("#warnParmSet th[name='isStart']").index();
					var itemValuesIndex = $("#warnParmSet th[name='warmValue']").index();
					var editBtnIndex = $("#warnParmSet th[name='editBtn']").index();
					var thisTr1 = editTrAll;
					var thisTd1 = thisTr1.find("td").eq(editBtnIndex);
					
					thisTd1.find(".showBtna").hide();
					thisTd1.find(".showBtnb").show();
					thisTr1.find('td').eq(itemValuesIndex).html(warmValueAll);
					thisTr1.find('td').eq(isStartindex).html(isStartAll);
					isEditAll = false;
				}
			};
			function saveTab(obj){
				$obj = $(obj);
				var updateFlag = true;
				var datas = [];
				var entity = {};
				var thisTr = $(obj).parents("tr");
				var thisTd = $(obj).parents("td");
				var isStartindex = $("#warnParmSet th[name='isStart']").index();
				var itemValuesIndex = $("#warnParmSet th[name='warmValue']").index();
				var itemNoindex = $("#warnParmSet th[name='parmId']").index();
				var itemNo = thisTr.find("td").eq(itemNoindex).text().trim();
				entity.parmId = itemNo;
				var value = thisTr.find('td').eq(itemValuesIndex).find("input").val();
				entity.warmValue = value;
				var isStart = thisTr.find('td').eq(isStartindex).find("input").is(':checked')?'1':'0';
				entity.isStart = isStart;
				datas.push(entity);
				
				if(updateFlag){
					$.ajax({
						type:"post",
						url:webPath+"/mfWarningParm/updateListAjax",
						data:{ajaxData:JSON.stringify(datas)},
						async:true,
						success:function(data){
							if(data.flag=="success"){
								$.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
								thisTr.find('td').eq(itemValuesIndex).html(value);
								if(isStart == '1'){
									thisTr.find('td').eq(isStartindex).html('启用');
								}else{
									thisTr.find('td').eq(isStartindex).html('禁用');
								}
								thisTd.find(".showBtna").hide();
								thisTd.find(".showBtnb").show();
								isEditAll = false;
							}else if(data.flag=="error"){
								$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
							}
						},error:function(){
							$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
						}
					});
				}else{
					$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
				}
			}
		</script>
	</head>
	<body>
		<div class="content">
			<div class="row">
				<div class="col-lg-12" style="height: 93.8%;">
					<div id="warnParmSet" class="table_content col_border">
						<div class="row title_btn">
							<div class="col-lg-3 tab_title">
								<h5>押品预警信息设置</h5>
							</div>
							<div class="col-lg-9 tab_btn">
								<!-- <input type="button" value="保存" onclick="saveTab(this)"/> -->
							</div>
						</div>
						<div class="row">
							<div class="col-lg-12 blue">
								<dhcc:tableTag paginate="collateralWarningDetailList" property="tabledlcollateralwarningdetail0002" head="true"></dhcc:tableTag>
							</div>
							
							
						</div>
					</div>
				</div>
			
			</div>
		</div>
	</body>
</html>