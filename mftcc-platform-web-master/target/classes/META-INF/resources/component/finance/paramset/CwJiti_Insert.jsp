<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<style type="text/css">
			.month_step{
				height:110px;
			}
			.key-bott{
				padding-bottom: 18px;
				padding-left: 15px;
			}
			.sptex-col{
				width:180px !important;
				display: inline-block;
			}
			.input-cont{
				/* display: block;  */
			    width: 30%; 
			    height: 34px; 
			    padding: 6px 12px; 
			    font-size: 14px; */
			    line-height: 1.42857143; 
			   /*  color: #555;  */
			    /* background-color: #fff;  */
			    background-image: none;
			    border: 1px solid #ccc;
			    border-radius: 4px;
			    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
			    box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
			}
			.inp-content{
				width: 100%; 
			    height: 34px; 
			    padding: 6px 12px; 
			    font-size: 14px; 
			    line-height: 1.42857143; 
			    background-image: none;
			    border: 1px solid #ccc;
			    border-radius: 4px;
			    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
			    box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
			}
			.tdfont{
				font-size: 22px !important;
				
			}
			.div-amtcon{
				 /*  padding-left: 690px; */
    			  padding-top: 25px;
			}
			.sp-jitizi{
				padding-left: 470px;
/* 				padding-left: 580px; */
			}
			.sp-jitiamt{
				text-align: right;
				background-color: #FFFFFF;
   			    background-color: transparent;
    			border: 0px;
    			padding-right:10px;
			}
		</style>
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-16 col-md-offset-1 margin_top_20">
					<%-- <div class="bootstarpTag">
						<div class="form-title"></div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="CwJitiForm" theme="simple" name="operform" action="${webPath}/cwJiti/insert">
							<dhcc:bootstarpTag property="formcwjiti0002" mode="query"/>
						</form>
					</div>    display: inline-block;" --%>

				<div id="month_step_2" class="month_step">
					<!-- <div class="month_title">
						<span class="txt">设置计提金额</span>
					</div> -->
					<div class="row vch_item">
						<div  class="col-md-12 key-bott" >
								<span class="txt sptex-col">贷款减值损失准备(损益类)：</span>
								<input type="text" class="input-cont" id="jieAccNo" name="accno1">
								 <a id="acc_no_1_a" class="cw_com_dialog"  href="#">科目</a> 
							 	<span id="acc_no_5_s">余额:
							 		<input class="input-cont" type="text" id="jieItemBal" name="acc_no_5" disabled="disabled"> 元
								</span>
							
						</div>
					</div>
					<div class="row vch_item">
						<div  class="col-md-12 key-bott" >
							<span class="txt sptex-col">贷款损失准备(资产类)：</span>
							<input type="text" class="input-cont"  id="daiAccNo" name="acc_no_2">
							<a id="acc_no_2_a" class="cw_com_dialog" href="#">科目</a> 
							<span id="acc_no_3_s">余额:
								<input class="input-cont" type="text"  id="daiItemBal" name="acc_no_3" disabled="disabled"> 元
							</span>
						</div>
						
					</div>
				</div>
				
				<div id="jiTitableDiv" class="table_content">

					<table id="jitiInfotable" width="100%" border="1" align="center"
						cellspacing="1" class="ls_list" title="jitimanage">
						<thead>
							<tr>
								<th scope="col" width="14%" align="center" name="accNo">名    称</th>
								<th scope="col" width="15%" align="center" name="jine">金额（元）</th>
								<th scope="col" width="8%" align="center" name="yunsuan1">运算符</th>
								<th scope="col" width="10%" align="center" name="bili">提取比例（%）</th>
								<th scope="col" width="8%" align="center" name="yunsuan2">运算符</th>
								<th scope="col" width="15%" align="center" name="jieguo">计算结果（元）</th>
								<th scope="col" width="16%" align="center" name="preamt">上次提取金额（元）</th>
								<th scope="col" width="14%" align="center" name="chaamt">差额（元）</th>
							</tr>
						</thead>
						<!-- 所有  -->
						<tbody id="tab1">
							<tr>
								<td width="14%" align="center">全部贷款余额：</td>
								<td width="15%" align="center"><span id="alljiti"></span></td>
								<td width="8%" align="center" class="tdfont">*</td>
								<td width="10%" align="center"><input type="text" id="alllbili" onchange="changeBal(this)" class="inp-content" /></td>
								<td width="8%" align="center">=</td>
								<td width="15%" align="center"><span id="allres"></span></td>
								<td width="16%" align="center"><span id="allpre"></span></td>
								<td width="14%" align="center"><span id="allcha"></span></td>
							</tr>
							<tr>
								<td width="14%" align="center"></td>
								<td width="15%" align="center"></td>
								<td width="8%" align="center" ></td>
								<td width="10%" align="center">合计：</td>
								<td width="8%" align="center"></td>
								<td width="15%" align="center"><span id="allhejiamt"></span></td>
								<td width="16%" align="center"><span id="allhejipreamt"></span></td>
								<td width="14%" align="center"><span id="allhejichaamt"></span></td>
							</tr>
						</tbody>
						<!-- 五级分类 -->
						<tbody id="tab2">
							<tr>
								<td width="14%" align="center">正常贷款余额：</td>
								<td width="15%" align="center"><span id="zcjiti"></span></td>
								<td width="8%" align="center" class="tdfont">*</td>
								<td width="10%" align="center"><input type="text" id="zcbili" onchange="changeBal(this)" class="inp-content" /></td>
								<td width="8%" align="center" class="tdfont">=</td>
								<td width="15%" align="center"><span id="zcres"></span></td>
								<td width="16%" align="center"><span id="upzcamt"></span></td>
								<td width="14%" align="center"><span id="upzccha"></span></td>
							</tr>
							<tr>
								<td width="18%" align="center">关注贷款余额：</td>
								<td width="15%" align="center"><span id="gzjiti"></span></td>
								<td width="8%" align="center" class="tdfont">*</td>
								<td width="10%" align="center"><input type="text" id="gzbili" onchange="changeBal(this)"  class="inp-content" /></td>
								<td width="8%" align="center" class="tdfont">=</td>
								<td width="15%" align="center"><span id="gzres"></span></td>
								<td width="16%" align="center"><span id="upgzamt"></span></td>
								<td width="14%" align="center"><span id="upgzcha"></span></td>
							</tr>
							<tr>
								<td width="18%" align="center">次级贷款余额：</td>
								<td width="15%" align="center"><span id="cjjiti"></span></td>
								<td width="8%" align="center" class="tdfont">*</td>
								<td width="10%" align="center"><input type="text" id="cjbili" onchange="changeBal(this)"  class="inp-content" /></td>
								<td width="8%" align="center" class="tdfont">=</td>
								<td width="15%" align="center"><span id="cjres"></span></td>
								<td width="16%" align="center"><span id="upcjamt"></span></td>
								<td width="14%" align="center"><span id="upcjcha"></span></td>
							</tr>
							<tr>
								<td width="18%" align="center">可疑贷款余额：</td>
								<td width="15%" align="center"><span id="kyjiti"></span></td>
								<td width="8%" align="center" class="tdfont">*</td>
								<td width="10%" align="center"><input type="text" id="kybili" onchange="changeBal(this)"  class="inp-content" /></td>
								<td width="8%" align="center" class="tdfont">=</td>
								<td width="15%" align="center"><span id="kyres"></span></td>
								<td width="16%" align="center"><span id="upkyamt"></span></td>
								<td width="14%" align="center"><span id="upkycha"></span></td>
							</tr>
							<tr>
								<td width="18%" align="center">损失贷款余额：</td>
								<td width="15%" align="center"><span id="ssjiti"></span></td>
								<td width="8%" align="center" class="tdfont">*</td>
								<td width="10%" align="center"><input type="text" id="ssbili" onchange="changeBal(this)"  class="inp-content" /></td>
								<td width="8%" align="center" class="tdfont">=</td>
								<td width="15%" align="center"><span id="ssres"></span></td>
								<td width="16%" align="center"><span id="upssamt"></span></td>
								<td width="14%" align="center"><span id="upsscha"></span></td>
							</tr>
							<tr>
								<td width="18%" align="center"></td>
								<td width="15%" align="center"></td>
								<td width="8%" align="center" ></td>
								<td width="10%" align="center">合计：</td>
								<td width="8%" align="center"></td>
								<td width="15%" align="center"><span id="hejiamt"></span></td>
								<td width="16%" align="center"><span id="fiveHeji"></span></td>
								<td width="14%" align="center"><span id="chaheji"></span></td>
							</tr>

						</tbody>
					</table>
				</div>
				<div class="div-amtcon"><span class="sp-jitizi">本次提取金额：</span>
				<input type="text" class="sp-jitiamt"  id="jitiamt" value="1000" name="acc_no_4" disabled="disabled">元
				</div>

			</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="计提" action="计提" onclick="jitiSaveData()"></dhcc:thirdButton>
	   			<%-- <dhcc:thirdButton value="取消" action="取消" typeclass="myclose" onclick="myclose();"></dhcc:thirdButton> --%>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
	<script type="text/javascript">
		var dataMap = '${dataMap}';
		var jititype = '${dataMap.jititype}';
		//var zcflag = '${dataMap.flag}';
		//alert('${dataMap}');
		$(function(){
			if(dataMap){
				if(jititype==0){
					$("#alljiti").text('${dataMap.allLoanBal}');
					$("#alllbili").val('${dataMap.alljiti}');
					$("#allres").text('${dataMap.allbal}');
					$("#allhejiamt").text('${dataMap.allbal}');
					$("#jitiamt").val('${dataMap.alljitiamt}');
					$("#allpre").text('${dataMap.upJiTiAmt}');
					$("#allhejichaamt").text('${dataMap.allchaamt}');
					$("#allcha").text('${dataMap.allchaamt}');
					$("#allhejipreamt").text('${dataMap.upJiTiAmt}');
					
					$("#tab2").hide();
					
				}else{
					$("#zcjiti").text('${dataMap.bal_1}');
					$("#gzjiti").text('${dataMap.bal_2}');
					$("#cjjiti").text('${dataMap.bal_3}');
					$("#kyjiti").text('${dataMap.bal_4}');
					$("#ssjiti").text('${dataMap.bal_5}');
					
					$("#allbili").val('${dataMap.alljiti}');
					$("#zcbili").val('${dataMap.zcjiti}');
					$("#gzbili").val('${dataMap.gzjiti}');
					$("#cjbili").val('${dataMap.cjjiti}');
					$("#kybili").val('${dataMap.kyjiti}');
					$("#ssbili").val('${dataMap.ssjiti}');
					//
					$("#zcres").text('${dataMap.restbal1}');
					$("#gzres").text('${dataMap.restbal2}');
					$("#cjres").text('${dataMap.restbal3}');
					$("#kyres").text('${dataMap.restbal4}');
					$("#ssres").text('${dataMap.restbal5}');
					$("#hejiamt").text('${dataMap.hejibal}');
					$("#jitiamt").val('${dataMap.jitiamt}');
					$("#upzcamt").text('${dataMap.fivebal1}');
					$("#upgzamt").text('${dataMap.fivebal2}');
					$("#upcjamt").text('${dataMap.fivebal3}');
					$("#upkyamt").text('${dataMap.fivebal4}');
					$("#upssamt").text('${dataMap.fivebal5}');
					
					$("#upzccha").text('${dataMap.fivecha1}');
					$("#upgzcha").text('${dataMap.fivecha2}');
					$("#upcjcha").text('${dataMap.fivecha3}');
					$("#upkycha").text('${dataMap.fivecha4}');
					$("#upsscha").text('${dataMap.fivecha5}');
					
					$("#fiveHeji").text('${dataMap.fiveHeji}');
					$("#chaheji").text('${dataMap.chaheji}');
					
					
					
					$("#tab1").hide();
				}
				$("#jieItemBal").val('${dataMap.jieItemBal}');
				$("#daiItemBal").val('${dataMap.daiItemBal}');
				$("#jieAccNo").val('${dataMap.jieShowName}');
				$("#daiAccNo").val('${dataMap.daiShowName}');
			}
		
		});  
		
		//科目弹窗
		$('.cw_com_dialog').on('click', function(){
			var that = $(this);
			openComItemDialog('2', function(data){
				if(data){
					that.prev('input').val(data.showName);
					//that.prev('input').val(data.id+"/"+data.name);
					//input.trigger("change");//当赋值后触发change事件。
					getkemuBal(data.id,that);
				}
			});
		});
		//获取科目余额
		function getkemuBal(accNo,that){
			var accNoJson = {"accNo":accNo}; 
			 $.ajax({
				 url:webPath+'/cwJiti/getkemuBalByAccnoAjax',
				data:accNoJson,
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						that.next('span').children("input").val(data.bal);
						var daiItemBal = that.next('span').children("input").attr("id");
						/* if(daiItemBal=='daiItemBal'){
							changeHeJiAmt();
						} */
					}
				},error:function(data){
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
		//修改余额
		function changeBal(obj){
			
			var bal = $(obj).parent().prev('td').prev('td').children('span').text();
			var upJiTiBal = $(obj).parent().next('td').next('td').next('td').children('span').text();//上次提取金额
			var bili=$(obj).val();
			var balJson = {"resBal":bal,"bili":bili,"upJiTiBal":upJiTiBal}; 
			 $.ajax({
				 url:webPath+'/cwJiti/getRusultAmtAjax',
				data:balJson,
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						$(obj).parent().next('td').next('td').children('span').text(data.bal);
						if(jititype==0){
							$("#allhejiamt").text(data.bal);
							$("#allcha").text(data.chaBal);
							$("#jitiamt").val(data.chaBal);
							$("#allhejichaamt").text(data.chaBal);
							
						}else{
							//$("#upzccha").text(data.chaBal);
							$(obj).parent().next('td').next('td').next('td').next('td').children('span').text(data.chaBal);//差额
							changeHeJiAmt();
						}
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(data){
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
		//修改合计amt,同时修改计提金额的值
		function changeHeJiAmt(){
			var daiItemBal = $("#fiveHeji").text();
			//var daiItemBal = "0.00";//内容做修改直接将值赋值为0
			if(!daiItemBal){
				daiItemBal = "0.00";
			}
			var zcResAmt =	"0";
			var gzResAmt =	"0";
			var cjResAmt =	"0";
			var kyResAmt =	"0";
			var ssResAmt =	"0";
			if(jititype==0){
				 zcResAmt = $("#allres").text();
			}else{
				 zcResAmt =	$("#zcres").text();
				 gzResAmt =	$("#gzres").text();
				 cjResAmt =	$("#cjres").text();
				 kyResAmt =	$("#kyres").text();
				 ssResAmt =	$("#ssres").text();
			}
			var fiveamtJson = {"zcResAmt":zcResAmt,"gzResAmt":gzResAmt,"cjResAmt":cjResAmt,"kyResAmt":kyResAmt,"ssResAmt":ssResAmt,"daiBal":daiItemBal}; 
			 $.ajax({
				 url:webPath+'/cwJiti/getHeJiAmtAjax',
				data:fiveamtJson,
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						if(jititype==0){
							$("#allhejiamt").text(data.bal);
						}else{
							$("#hejiamt").text(data.bal);
							$("#chaheji").text(data.jitibal);
						}
						$("#jitiamt").val(data.jitibal);
						
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(data){
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		} 
	//封装数据
	function pkgJitiSaveData(){
	
		//借方科目
		var jieAccNo = $("#jieAccNo").val();
		var daiAccNo = $("#daiAccNo").val();
		if(!jieAccNo){
			alert(top.getMessage("NOT_FORM_EMPTY","贷款减值损失准备(损益类)" ),0);
			return;
		}
		if(!daiAccNo){
			alert(top.getMessage("NOT_FORM_EMPTY","贷款损失准备(资产类)" ),0);
			return;
		}
		var jieItemBal = $("#jieItemBal").val();
		var daiItemBal = $("#daiItemBal").val();
		var jitiamt = $("#jitiamt").val();
		var dataMap = {};
		dataMap.jieAccNo=jieAccNo;
		dataMap.daiAccNo=daiAccNo;
		dataMap.jieItemBal=jieItemBal;
		dataMap.daiItemBal=daiItemBal;
		dataMap.jitiamt=jitiamt;//计提金额
		dataMap.jititype=jititype;//计提类型，0全部，1五级分类
		//dataMap.businessNo=businessNo||'';
	//循环每一条余额的值
		var detils = [];
		if(jititype==0){
				dataMap.jiheji=$("#allhejiamt").text();//合计金额
				var detil = {};
				//明细表
				detil.jitiAmt=$("#alljiti").text();
				detil.jtTypeSort="0";
				detil.jtBili=$("#alllbili").val();
				detil.jtRes=$("#allres").text();
				//detil.jthejiamt=$("#allhejiamt").text();
				detil.upjtAmt=$("#allpre").text();
				detil.upchaAmt=$("#allcha").text();
				detils.push(detil);
				
		}else{
				dataMap.jiheji=$("#hejiamt").text();//合计金额
				$("#tab2 tr").each(function(i) {
					if(i!=5){
						var detil = {};
						var jitiAmt = $(this).children("td").first().next("td").children("span").text();
						var jtBili = $(this).children("td").first().next("td").next("td").next("td").children("input").val();
						var jtRes = $(this).children("td").first().next("td").next("td").next("td").next("td").next("td").children("span").text();
						var upjtAmt = $(this).children("td").first().next("td").next("td").next("td").next("td").next("td").next("td").children("span").text();
						var upchaAmt = $(this).children("td").first().next("td").next("td").next("td").next("td").next("td").next("td").next("td").children("span").text();
						//明细表
						detil.jitiAmt= jitiAmt;
						detil.jtTypeSort=i+1;
						detil.jtBili=jtBili;
						detil.jtRes=jtRes;
						detil.upjtAmt=upjtAmt;
						detil.upchaAmt=upchaAmt;
						detils.push(detil);
					}
			
				i++;
			});
		}
		dataMap.detils=detils;
		return dataMap;
	}
	function jitiSaveData(){
		var dataMap = pkgJitiSaveData();
		if(dataMap){
				$.ajax({
					url:webPath+"/cwJiti/insertJiTiDataAjax",
					data:{ajaxData : JSON.stringify(dataMap)},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						if(data.flag == "success"){
								//updateTableData();//重新加载列表数据
								$(parent.document).find("#showDialog .close").click();
								//myclose();
						}else if(data.flag == "error"){
							alert(data.msg,0);
						}
					},error:function(data){
						alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				});
			}
	}
	function myclose(){
		$(parent.document).find("#showDialog .close").click();
	}
	</script>
</html>
