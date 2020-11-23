<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head >
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<style type="text/css">
		.cwinit-con{
			width: 500px;
			margin-left: 60px;
			margin-top: 60px;
		}
		.cwinit-con .toolbar-bottom{
			margin-top: 20px;
			text-align: center;
		}
		.cwinit-con .tubiao{
			font-size: 15px;
			color: green;
			display: inline-block;
			float: right;
			padding-top: 8px;
		}
		
		.cwinit-con .dn{
			display: none; 
		}
		.cwinit-con .vch-date{
			background-color: #FFF;
			cursor:auto;
			background: url(${webPath}/component/finance/voucher/images/datepicker_icon.png) right 0 no-repeat #FFF;
		}
	</style>
</head>
<body >
	<div class="container">
		<ol class="cwinit-con">
			<li>
				<strong>设置启用期间</strong><span>（设置会计启用期间）</span>
				<div>
					<span>启用期间：</span>
					<input type="text" class="form-control vch-date" id="weeks" name="weeks" autocomplete="off" onclick="laydatemonth(this);" onkeydown="enterKey();" style="width: 140px; display: inline-block;" readonly>
					<button class="btn btn-info btn-xs" onclick="setInitWeek();">确定</button>
					<i class="i i-duihao2 tubiao dn">完成</i>
				</div>
				<div></div>
			</li>
			<li>
				<strong>科目编号规则</strong><span>（选择科目编号所采用的格式）</span>
				<div>
					<span>编码长度：</span>
					<select class="form-control" id="comType" name="comType" style="width: 140px; display: inline-block;">
						<option value="">请选择</option>
						<option value="0">4-2-2（默认）</option>
						<option value="1">4-3-3</option>
						<option value="2">4-4-4</option>
						<option value="3">4-5-5</option>
					</select>
					<button class="btn btn-info btn-xs" onclick="setComType();">确定</button>
					<i class="i i-duihao2 tubiao dn">完成</i>
				</div>
				<a href="javascript:void(0);" onclick="showCodeLenth();">预览科目规则</a>
			</li>
					<li>
				<strong>价税分离</strong>
				<div>
					
					<span>税率：</span>
					<input type="text" class="form-control " id="priceTaxRate" name="priceTaxRate" autocomplete="off" onkeydown="enterKey();" style="width: 65px; display: inline-block;" >
					%
					<span style="margin-left: 8px;">方式：</span>
					<select class="form-control" id="priceTaxType" name="priceTaxType" style="width: 110px; display: inline-block;">
						<!-- <option value="">请选择</option> -->
						<option value="1">实时计税</option>
						<option value="2">按月计税</option>
					</select>
					<button class="btn btn-info btn-xs" onclick="setPriceTaxType();">确定</button>
					<i class="i i-duihao2 tubiao dn">完成</i>
					<!-- <button class="btn btn-info btn-xs" onclick="setPriceTaxType();">确定</button> -->
				<!-- 	<i class="i i-duihao2 tubiao dn">完成</i> -->
				</div>
			<li>
				<strong>科目管理</strong>
				<div>
					<a href="javascript:void(0);" onclick="setComItem();">维护科目数据</a>
					<i class="i i-duihao2 tubiao dn">完成</i>
				</div>
			</li>
			<li>
				<strong>账户余额初始化</strong>
				<div>
					<a href="javascript:void(0);" onclick="setInitBal();">导入初始余额</a>
					<i class="i i-duihao2 tubiao dn">完成</i>
				</div>
			</li>
	
			<div class="toolbar-bottom">
				<button class="btn btn-info" onclick="setInitFlagOk();" >完成初始化</button>
			</div>
		</ol>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		getInitFlag();
	})
	
	var flagData;
	function getInitFlag(){
		$.get("${webPath}/cwInitSystem/getSysInitFlagAjax", function(data){
			if(data.flag == "success"){
				flagData = data.data;
				if(flagData.initWeek!=''){
					$('#weeks').val(flagData.initWeek);
					$('.tubiao:eq(0)').removeClass('dn');
				}
				if(flagData.comDits !=''){
                    var opt = $('#comType')[0];
                    opt.options.length=0;
                    opt.add(new Option('请选择', ''));
                    for(var i=0; i<4; i++){
                        if (i==0){
                            opt.add(new Option(flagData.comDits + '-2-2（默认）' , i));
						} else {
                            opt.add(new Option(flagData.comDits + '-' + (i + 2) + '-' + (i + 2), i));
                        }
                    }
				}
				if(flagData.comType!=''){
					var comType = flagData.comType;
					$('#comType option').each(function(){
						var val = $(this).val();
						if(val == '' || parseInt(val) < parseInt(comType) ){
							$(this).remove();
						}
					})
					$('#comType').val(flagData.comType);
					$('.tubiao:eq(1)').removeClass('dn');
				}
				
				 if(flagData.priceTaxType!=''){
					$('#priceTaxType').val(flagData.priceTaxType);
					$('#priceTaxRate').val(flagData.priceTaxRate);
					$('.tubiao:eq(2)').removeClass('dn');
				} 
				if(flagData.comInit=='1'){
					$('.tubiao:eq(3)').removeClass('dn');
				}
				if(flagData.balInit=='1'){
					$('.tubiao:eq(4)').removeClass('dn');
				}
			}else if(data.flag == "error"){
				 alert(data.msg,0);
			}
		});
	}
	
	//设置启用期间
	function setInitWeek(){
		var weeks= $('#weeks').val();
		if(weeks!=''){
			var ajaxD = "{'initType':'week', 'weeks':'"+weeks+"'}";
			setInitPram(ajaxD, '');
		}
	}
	//设置科目编码规则
	function setComType(){
		var comType= $('#comType').val();
		if(comType!=''){
			//此操作不可逆，确定设置该规则吗？
			alert(top.getMessage("WAIT_OPERATION", "设置该规则"),2,function(){
				var ajaxD = "{'initType':'comtype', 'comType':'"+comType+"'}";
				setInitPram(ajaxD, '');
			});
			
		}
	}
	function setPriceTaxType(){
		var priceTaxType= $('#priceTaxType').val();
		var priceTaxRate= $('#priceTaxRate').val();
		if(!priceTaxRate){
			alert(top.getMessage("NOT_FORM_EMPTY", "税率"),0);
			return;
		}else{
			if(priceTaxRate<0||priceTaxRate>100){
				alert(top.getMessage("ONLY_NUMBER", "0~100之间的数字"),0);
				return;
			}
		}
		
		if(priceTaxType!=''){
			var ajaxD = "{'initType':'priceTaxType', 'priceTaxType':'"+priceTaxType+"','priceTaxRate':'"+priceTaxRate+"'}";
				setInitPram(ajaxD, '');
			/* alert(top.getMessage("WAIT_OPERATION", "设置该规则"),2,function(){
				
			}); */
			
		}
	}

	//科目编码预览
	function showCodeLenth(){
		var length = $('#comType').val();
		if(length!=''){
			window.parent.openBigForm("${webPath}/cwComItem/getAccnoListPage?ajaxData="+ length, '科目编码规则预览');
		}
	}
	
	function setComItem(){
		window.parent.openBigForm("${webPath}/cwComItem/getListPage?accType=1", '维护科目管理',comCallBack);
	}
	//维护科目后回调
	function comCallBack() {
		closeCallBack();
		var ajaxD = "{'initType':'comitem'}";
		setInitPram(ajaxD, '');
		
	};
	//余额初始
	function setInitBal(){
		if(flagData){
			if(flagData.initWeek==''){
				//alert("请先完成会计启用期间设置!",1);
				alert(top.getMessage("FIRST_OPERATION", "会计启用期间设置!"), 1);
			}else{
				window.parent.openBigForm("${webPath}/cwInitBal/getInitPage", '录入财务初始余额',closeCallBack);
			}
		}
	}
	
	//完成初始化操作
	function setInitFlagOk(){
		if(flagData){
			
			if(flagData.initWeek==''){
				//alert("请先完成会计启用期间设置!",1);
				alert(top.getMessage("FIRST_OPERATION", "会计启用期间设置!"), 1);
			}else if(flagData.comType==''){
				//alert("请先完成科目编码规则设置!",1);
				alert(top.getMessage("FIRST_OPERATION", "科目编码规则设置!"), 1);
			}else if(flagData.priceTaxRate==''){
				alert(top.getMessage("FIRST_OPERATION", "填写税率!"), 1);
			}else if(flagData.comInit=='0'){
				//alert("请先维护科目设置!",1);
				alert(top.getMessage("FIRST_OPERATION", "维护科目设置!"), 1);
			}else if(flagData.balInit=='0'){
				//alert("请先完成科目初始余额!",1);
				alert(top.getMessage("FIRST_OPERATION", "请先完成科目初始余额!"), 1);
			}else{
				var ajaxD = "{'initType':'initFlag'}";
				setInitPram(ajaxD, 'ok');
				
			}
		}
	}
	
	function closeCallBack() {
		myclose();
		getInitFlag();
	};
	
	//初始化财务数据
	var repeat = 0;
	function setInitPram(ajaxData, type){
		if(repeat > 0){
			//alert("正在执行，请勿重复操作!",1);
			alert(top.getMessage("WAIT_OPERATION", "执行"), 1);
			return false;
		}else{
			window.top.LoadingAnimate.start();
			repeat++;
			jQuery.ajax({
				url:webPath+'/cwInitSystem/cwSetInitPramAjax',
				data:{ajaxData:ajaxData},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						if(type=='ok'){
							window.parent.location.reload();
						}else{
							alert(top.getMessage("SUCCEED_OPERATION"),1);
							location.reload();
						}
					}else if(data.flag == "error"){
						alert(data.msg,0);
						repeat--;
					}
					window.top.LoadingAnimate.stop();
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "),0);
					window.top.LoadingAnimate.stop();
					repeat--;
				}
			});
		}
	}

</script>
</html>