<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
<style type="text/css">
.cw-chapter-font{position: absolute;  right: 200px;  top: -28px; font-size: 100px;  color: red; z-index: 3}
.chapter-name-div{    
	font-size: 19px;
    color: red;
    text-align: center;
    position: absolute;
    left: 11px;
    top: 39px;
    width: 78px;
    transform: rotate(-15deg);
    -ms-transform: rotate(-15deg);
    -moz-transform: rotate(-15deg);
    -webkit-transform: rotate(-15deg);
}
</style>
<script type="text/javascript">
var ajaxData = '${ajaxData}';
ajaxData = eval("("+ajaxData+")");
var zcflag = '${dataMap.zcflag}';
</script>
<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/cw_assist_util.css" />
<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
<script type="text/javascript" src="${webPath}/component/finance/js/cw_assist_util.js"></script>
</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag fourColumn">
					<div class="form-title">新增资产</div>
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="account_form" theme="simple" name="operform" action="${webPath}/cwAssets/insertAjax">
						<dhcc:bootstarpTag property="formassets0002" mode="query" />
					</form>
					<c:if test='${dataMap.uuid!="" && dataMap.uuid!=null && dataMap.assetsSts=="0"}'>
						<div class="i i-warehouse cw-chapter-font"><div class="chapter-name-div">正常使用</div></div>
					</c:if>
					<c:if test='${dataMap.uuid!="" && dataMap.uuid!=null && dataMap.assetsSts=="1"}'>
						<div class="i i-warehouse cw-chapter-font"><div class="chapter-name-div">已清理</div></div>
					</c:if>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<c:if test='${query==""}'>
				<dhcc:thirdButton value="保存" action="保存" onclick="ajaxAddAcount('#account_form');"></dhcc:thirdButton>
			</c:if>
			<c:if test='${dataMap.uuid!="" && dataMap.uuid!=null && dataMap.assetsSts=="0"}'>
				<dhcc:thirdButton value="清理" action="清理" typeclass="cancel" onclick="cleanAssets();"></dhcc:thirdButton>
			</c:if>
			<c:if test='${dataMap.uuid!="" && dataMap.uuid!=null && dataMap.assetsSts=="1"}'>
				<dhcc:thirdButton value="取消清理" action="取消清理" typeclass="cancel" onclick="reCleanAssets();"></dhcc:thirdButton>
			</c:if>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click2();"></dhcc:thirdButton>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	var rate = "";//残值率
	var term = "";//预计使用期限
	var slist;
	//select触发事件
	$(function() {
		//滚动条
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			},
			callbacks:{
// 				onScrollStart:function(){},
// 				onScroll:function(){},
// 				onTotalScroll:function(){},
// 				onTotalScrollBack:function(){},
// 				onTotalScrollOffset:0,
				whileScrolling:function(){changePosition()},
				whileScrollingInterval:30
			}
		});
		
		
		//获取下拉框的值
		var list = '${dataMap.list}';
		 if (list) {
			var classno = $("input[name='classNo']").val();
			slist = eval(list);
			var opt = $("input[name='className']")[0];
			//opt.options.length = 0;
			for ( var i = 0; i < slist.length; i++) {
				/* if(classno != '' && classno==slist[i].classNo) opt.add(new Option(slist[i].className, slist[i].classNo, true, true));
				else opt.add(new Option(slist[i].className, slist[i].classNo)); */
			}
		}  
		//绑定下拉框组件
		addAssetsClassEvent();
		//科目自动带出事件绑定
		$('input[name^=com]').click(function(){
			var input = $(this);
			autoComPleter(this, '2', function(selected){
				input.val(selected.accNo + "/" + selected.accName);
			});
		});
		//科目弹窗
		$('.cw_com_dialog').on('click', function(){
			var input = $(this).parents('td').find('input:not(.edit_items)');
			openComItemDialog('2', function(data){
				if(data){
					input.val(data.showName);
					input.trigger("change");//当赋值后触发change事件。
// 					input.blur();
				}
			});
		});
		
		//页面验证类型，后台string接收
		$('input[name=number]').attr('datatype', '1');
		$('input[name=assetsTerm]').attr('datatype', '1');
		$('input[name=deprePeriod]').attr('datatype', '1');
		$('input[name=residualRate]').attr('datatype', '9');
		//指定值联动计算
		$('#account_form').on('blur','input[name$=Amt],input[name=assetsTerm],input[name=deprePeriod],input[name=residualRate]' , function(){
			cypherAll(this);
		});
		//修复bug3021：当折旧方式选择不提折旧时，去掉字
		changeForm();
		$("[name=assetsMethod]").change(function(){
			changeForm();
		})
		
		//辅助核算项展示
		$("[name^=com]").change(function(){
			var comLebel=this.value;//选择的科目
			var com=comLebel.split("/");
			var accNo=com[0];//选择之后的科目号accNo
			checkSubInfo($(this),accNo);
		})
		//一开始就在科目输入框后加上一个输入框，存辅助核算项，与插件对应。
		$("[name^=com]").each(function(){
			var thisName=this.name;
			var itemName=thisName.replace("com","item");
			var itemVal=$("[name="+itemName+"]").val();//数据库存的辅助核算项字符串
			var editsCount = $(this).siblings(".edit_items").length;
			if(editsCount == 0){
				$(this).after("<input type='hidden' class='edit_items' autocomplete='off' value=\""+itemVal+"\">");
			}
		})
		
		
		
	});
	//期间修改
	function useDateChange(){
		var weeks = $("[name=weeks]").val();
		var useDate = $("[name=useDate]").val();
		var usearr = useDate.split("-");
		// 拆分年月日
		var weekYear = weeks.substring(0,4);
		var weekMonth = weeks.substring(4,6);
		
		// 得到月数
		var m1 = parseInt(weekYear) * 12 + parseInt(weekMonth);
		// 拆分年月日
		// 得到月数
		var m2 = parseInt(usearr[0]) * 12 + parseInt(usearr[1]);
		var m = m1 - m2;
		if(m>0){
			$("[name=deprePeriod]").val(m).blur();
		}else{
			$("[name=deprePeriod]").val(0).blur();
		}
		
	}
	function addAssetsClassEvent(){
		if(zcflag==1){
			$("input[name=className]").val($("input[name='classNo']").val());
			//资产类别新增
			$("input[name=className]").popupSelection({
					searchOn:true,//启用搜索
					inline:true,//下拉模式
					multiple:false,//dan选选
					items:ajaxData.classType,
					changeCallback : function (obj) {//回调
						$("input[name=className]").val(obj.data("text"));
						var objid= obj.val();
						$("input[name='classNo']").val(objid);
						changezclb(objid);//资产类别切换
					},
					addBtn:{//添加扩展按钮
						"title":"资产类别新增",
						"fun":function(hiddenInput, elem){
							top.cwAssetsClsData;
							top.window.openBigForm('${webPath}/cwAssetsClass/input','资产类别新增',function(data){
								$(elem).popupSelection("addItem", top.cwAssetsClsData);
								refreshClass();
							});
						}
					}
			});
		}
	}
	
	//刷新资产类别数据源
	function refreshClass(){
		jQuery.ajax({
			url:webPath+"/cwAssets/getAssetsClassListAjax",
			data:{ajaxData:''},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					slist = data.list;
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	}
	
	//资产类别切换
	function changezclb(objid){
		//var type = $(this).val();
		var type = objid;
		for ( var i = 0; i < slist.length; i++) {
			var aclass = slist[i];
			if (type == aclass.classNo) {
				$("input[name='classNo']").val(aclass.classNo);
				$("select[name='assetsMethod']").val(aclass.assetsMethod);//折旧方式
				$("select[name='assetsMethod']").trigger("change");
				$('input[name=assetsTerm]').val(aclass.assetsTerm);//期限
				$('input[name=residualRate]').val(aclass.residualRate).blur();//残值率
				$('input[name=comAssets]').val(aclass.comAssets);//固定资产科目
				$("input[name=comAssets]").parent().find(".edit_items").val(aclass.itemAssets);//辅助核算的处理
				$('input[name=comDepre]').val(aclass.comDepre);//累计折旧科目
				$("input[name=comDepre]").parent().find(".edit_items").val(aclass.itemDepre);//辅助核算的处理
				rate = aclass.residualRate;
				term = aclass.assetsTerm;
				
				
			}
		}
	}
	
	
	//bug3021修复
	function changeForm(){
		var dom=$("[name=assetsMethod]")[0];
		
		if(dom.value=="1"){//不提折旧
			$("[name=comDepre]").removeClass("Required");
			$("[name=comDepre]").attr("disabled","disabled");
			$("[name=comCost]").removeClass("Required");
			$("[name=comCost]").attr("disabled","disabled");
			$("[name=comDepre]").parents("tr").hide();
			$("[name=deprePeriod]").removeClass("Required");
			$("[name=deprePeriod]").attr("disabled","disabled");
			$("[name=depreAmt]").removeClass("Required");
			$("[name=depreAmt]").attr("disabled","disabled");
			$("[name=deprePeriod]").parents("tr").hide();
			$("[name=monthAmt]").removeClass("Required");
			$("[name=monthAmt]").attr("disabled","disabled");
			$("[name=monthAmt]").parents("tr").hide();
			//去掉累计折旧为非必填项
			$("[name=comDepre]").removeAttr("mustinput");
			//$("input[name='comDepre']").val("0");
			//去掉折旧费用为非必填项
			$("[name=comCost]").removeAttr("mustinput");
			//$("input[name='comCost']").val("0");
			
		}else{//平准年限法
			$("[name=comDepre]").attr("mustinput","1");//给属性添加必填项
			$("[name=comCost]").attr("mustinput","1");//给属性添加必填项
			
			$("[name=comDepre]").removeAttr("disabled");
			$("[name=comDepre]").addClass("Required");
			$("[name=comCost]").removeAttr("disabled");
			$("[name=comCost]").addClass("Required");
			$("[name=deprePeriod]").removeAttr("disabled");
			$("[name=deprePeriod]").addClass("Required");
			$("[name=depreAmt]").removeAttr("disabled");
			$("[name=depreAmt]").addClass("Required");
			$("[name=monthAmt]").removeAttr("disabled");
			$("[name=monthAmt]").addClass("Required");
			$("[name=comDepre]").parents("tr").show();
			$("[name=deprePeriod]").parents("tr").show();
			$("[name=monthAmt]").parents("tr").show();
		}
	}
	//检查科目数据
	function checkComItem(obj){
		var subVal = $(obj).val().split("/")[0];
		if(autoComData.length == 0){
			autoComPleter($("input[name=brNo]"), '0');
		}
		var ishave = false;
		for(var item in autoComData){
			if(autoComData[item].accNo == subVal){
				ishave = true;	
			}
		}
		if(!ishave){
			$(obj).val('');
		}
	}
	
	//部门回调
	function setSysOrgInfo(sysOrg){
		$("input[name=brName]").val(sysOrg.brName);
		$("input[name=brNo]").val(sysOrg.brNo);
	}
	
	//联动计算
	function cypherAll(obj) {
		var originalAmt = chkNuToZr($('input[name=originalAmt]'), '2'); //原值
		var reservesAmt = chkNuToZr($('input[name=reservesAmt]'), '2'); //减值准备
		var term = $('input[name=assetsTerm]').val(); //预计使用期限
		var period = $('input[name=deprePeriod]').val(); //已折旧期间
		var rate = $('input[name=residualRate]').val(); //残值率
		if(Number(rate) < 0){
			alert(top.getMessage("NOT_SMALL_TIME", {"timeOne":"残值率" , "timeTwo": "0"}), 0)
			$('input[name=residualRate]').val('0').blur();
			return false;
		}
		if(Number(rate) > 100){
			alert(top.getMessage("NOT_FORM_TIME", {"timeOne":"残值率" , "timeTwo": "100"}), 0);
			$('input[name=residualRate]').val('0').blur();
			return false;
		}
		if(Number(period) < 0){
			alert(top.getMessage("NOT_SMALL_TIME", {"timeOne":"残值率" , "timeTwo": "0"}), 0)
			$('input[name=deprePeriod]').val('0').blur();
			return false;
		}
		if(Number(term) < Number(period)){
			alert(top.getMessage("NOT_FORM_TIME", {"timeOne":"已折旧期间" , "timeTwo": "预计使用期限"}), 0);
			$('input[name=deprePeriod]').val('0').blur();
			return false;
		}
		
		//预计残值
		var residualAmt = Number(originalAmt) * Number(rate) / 100;
		$('input[name=residualAmt]').val(parseFormatNum(residualAmt, 2));
		if(Number(originalAmt) < Number(reservesAmt) + Number(residualAmt)){
			alert(top.getMessage("NOT_FORM_TIME", {"timeOne":"减值准备+预计残值" , "timeTwo": "原值"}), 1);
			$('input[name=reservesAmt]').val('0.00').blur();
			return false;
		}

		//计算月折旧
		var monthAmt = (Number(originalAmt) - Number(reservesAmt) - residualAmt) / term;
		$('input[name=monthAmt]').val(parseFormatNum(monthAmt, 2));

		//计算累计折旧
		var depreAmt = monthAmt * period;
		$('input[name=depreAmt]').val(parseFormatNum(depreAmt, 2));

		//计算期初净值
		var networthAmt = Number(originalAmt) - Number(reservesAmt) - depreAmt
		$('input[name=networthAmt]').val(parseFormatNum(networthAmt, 2));
	}

	//去逗号保留两位小数
	function chkNuToZr(obj, flag) {
		var num = obj.val().replace(/,/g, '');
		var reg = /^-?\d+\.?\d{0,2}$/;
		var el = /^\d+$/;
		if (num.indexOf('.') > 0) {
			num = num.substring(0, num.indexOf('.') + 3);
		}
		if (flag == '1') {
			if (num == '') {
				obj.val('0');
				return '0';
			} else if (!(el.test(num)) && num.length > 0) {
				return '0';
			} else {
				return num;
			}
		} else if (flag == '2') {
			if (num == '') {
				obj.val('0.00');
				return '0.00';
			} else if (!(reg.test(num)) && num != '') {
				return "0.00";
			} else {
				return num;
			}
		}
	}
	//给表单设计器的辅助核算输入框赋值
	function changeItemsInput(){
		var itemsDoms=$(".edit_items");
		for(var i=0;i<itemsDoms.length;i++){
			var prevName=$(itemsDoms[i]).prev().attr("name");//生成它的科目框的name
			var thisName=prevName.replace("com","item");//对应的辅助核算输入框的name
			$("[name="+thisName+"]").val(itemsDoms[i].value);
		}
	
	}
	//资产新增
	function ajaxAddAcount(form) {
		changeItemsInput();
		if(Number($('input[name=originalAmt]').val()) == 0){
			alert(top.getMessage("NOT_FORM_EMPTY", "原值"), 1);
			return false;
		}
		var flag = submitJsMethod($(form).get(0), '');
		if(flag){
			var url = $(form).attr("action");
			url=url+'?zcflag='+zcflag;
			var dataParam = JSON.stringify($(form).serializeArray());
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParam
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {
					if (data.flag == "success") {
						myclose_click2();
					} else if (data.flag == "error") {
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
		
	}
	var uuid = '${dataMap.uuid}';
	//清理
	function cleanAssets() {
		jQuery.ajax({
			url : '${webPath}/cwAssets/cleanAssetsAjax',
			data : { uuid : uuid },
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					window.location.reload();
				} else if (data.flag == "error") {
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	}
	//取消清理
	function reCleanAssets() {
// 		var uuid = $('input[name=uuid]').val();
		jQuery.ajax({
			url : '${webPath}/cwAssets/reCleanAssetsAjax',
			data : { uuid : uuid },
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					window.location.reload();
				} else if (data.flag == "error") {
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	}

	//数字加逗号
	function parseFormatNum(number, n) {
		if (n != 0) {
			n = (n > 0 && n <= 20) ? n : 2;
		}
		number = parseFloat((number + "").replace(/[^\d\.-]/g, "")).toFixed(n)
				+ "";
		var sub_val = number.split(".")[0].split("").reverse();
		var sub_xs = number.split(".")[1];

		var show_html = "";
		for (i = 0; i < sub_val.length; i++) {
			show_html += sub_val[i]
					+ ((i + 1) % 3 == 0 && (i + 1) != sub_val.length ? "," : "");
		}

		if (n == 0) {
			return show_html.split("").reverse().join("");
		} else {
			return show_html.split("").reverse().join("") + "." + sub_xs;
		}
	}
	//去掉逗号
	function repamt(obj) {
		obj.replace(/,/g, "");
	}
	//返回
	function myclose_click2(){
		window.location.href="${webPath}/cwAssets/getListPage";
	}
	function callBackFunction(){//空函数才能触发change事件
	}
</script>
