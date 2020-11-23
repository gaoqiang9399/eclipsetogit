<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>参数化设置</title>
<!-- <link rel="stylesheet" type="text/css" -->
<%-- 	href="${webPath}/component/sys/css/B1.css" /> --%>
<link id="B1" rel="stylesheet" type="text/css"
	href="${webPath}/themes/factor/css/B1${skinSuffix}.css" />
<style>


.param_tit{
    margin-left: 200px;
    padding-left: 70px;
    padding-bottom: 40px;
}

.month_step {
	overflow-x: hidden;
	width: calc(100% - 212px);
	margin-bottom: 20px;
	margin-left: 200px;
	background-color: #ffffff;
/* 	font-size: 12px; */
	padding-left: 70px;
 	height: calc(100% - 120px); 
}

#cwSysParmDiv{
    margin-left: 30px;
}

.div0 {
	width: 260px;
	padding-left: 30px;
}

.div1 {
	width: 160px;
	float: left;
}

.div2 {
	width: 260px;
	margin-left: 80px;
	margin-bottom: 20px;
	float: left;
}

.span1 {
/* 	font-size: 16px; */
	font-weight: lighter;
}

.span2 {
/* 	font-size: 14px; */
	font-weight: lighter;
	color: #666;
}

.span3 {
	font-size: 18px;
}

.com_preview{
	font-size: 12px;
}
.div3 {
	margin-top: 6px;
	margin-bottom: 6px;
}

.work-zone-timeLine{
/*     margin-left: -40px; */
	top: 25px; 
	left: 0px;
	right: auto;
}

.time-line-line1 {
	border-left: none;
	border-color: #c0c9d8;
	top: 40px;
	width: 0px;
	background-color: #606A7B;
	height: 388px;
	border-right: 1px solid #606A7B;
	margin: 20px auto;
	position: absolute;
	margin-left: 80px;
}

.time-line-border1{
    left: 72px;
}

.work_timeLine {
	position: fixed;
	background-color: #F0F5FB;
	height: 100%;
	width: 250px;
}
/* 清除浮动 begin*/
.clearfix {
	content: "\200B";
	display: block;
	height: 0;
	clear: both;
}

.clearfix {
	*zoom: 1;
}
/* 清除浮动 end*/
.vch-date {
	display: inline;
	background-color: #FFF;
	cursor: auto;
	background: url(${webPath}/component/finance/voucher/images/datepicker_icon.png) right 0 no-repeat
		#FFF;
}

.divbutton1 {
/* 	margin: 6px; */
	padding-left: 20px;
	float: left;
}
.divbutton2{
	float: left;
}
.attsty:hover {
	color: #ffffff !important;
}
.linesty002{
	 line-height: 34px;
}
</style>
</head>
<body class="overflowHidden">
<div class="row">
		<div class="work_timeLine">
			<div class="work-zone-timeLine col-sm-2">
				<div class="time_contents">
					<div class="time-line-bg">
						<div class="time-line-border time-line-border1 time-line-point time-line-point-select" data-dit="all" id="time-line-all">
							<span class="time-line-dot1 line-dot-on i i-duihao1"></span> <a class="time-line-title line-a-on">系统参数设置</a>
						</div>
						<div class="time-line-line1"></div>
						<div class="time-line-body">
							<dl class="time-line-dl1">
								<dd class="time-line-dd time-line-point" data-dit="80011">
									<a class="time-line-dot attsty" href=""><em></em></a> <a data-dit="d3" href="" class="time-line-a">启用会计期间</a>
								</dd>
								<dd class="time-line-dd time-line-point" data-dit="10001">
									<a class="time-line-dot attsty" href=""><em></em></a> <a data-dit="d7" class="time-line-a" href="">出纳启用期间</a>
								</dd>
								<dd class="time-line-dd time-line-point" data-dit="80027">
									<a class="time-line-dot attsty" href=""><em></em></a> <a data-dit="d14" class="time-line-a" href="">科目长度设置</a>
								</dd>
								<dd class="time-line-dd time-line-point" data-dit="10000">
									<a class="time-line-dot" href=""><em></em></a> <a data-dit="m1" class="time-line-a" href="">现金流量分析</a>
								</dd>
								<dd class="time-line-dd time-line-point" data-dit="10002">
									<a class="time-line-dot" href=""><em></em></a> <a data-dit="m3" class="time-line-a" href="">凭证金额负数</a>
								</dd>
	<!-- 							<dd class="time-line-dd time-line-point" data-dit="10003"> -->
	<!-- 								<a class="time-line-dot" href=""><em></em></a> <a data-dit="m6" class="time-line-a" href="">结账时检查</a> -->
	<!-- 							</dd> -->
	<!-- 							<dd class="time-line-dd time-line-point" data-dit="10004"> -->
	<!-- 								<a class="time-line-dot" href=""><em></em></a> <a data-dit="m3" class="time-line-a" href="">设置计提方式</a> -->
	<!-- 							</dd> -->
	<!-- 							<dd class="time-line-dd time-line-point" data-dit="10011"> -->
	<!-- 								<a class="time-line-dot" href=""><em></em></a> <a data-dit="m3" class="time-line-a" href="">月结时检查计提</a> -->
	<!-- 							</dd> -->
								<dd class="time-line-dd time-line-point" data-dit="10012">
									<a class="time-line-dot" href=""><em></em></a> <a data-dit="m3" class="time-line-a" href="">报表科目方向调整</a>
								</dd>
								<dd class="time-line-dd time-line-point" data-dit="10013">
									<a class="time-line-dot" href=""><em></em></a> <a data-dit="m3" class="time-line-a" href="">按余额反方向结转</a>
								</dd>
								<dd class="time-line-dd time-line-point" data-dit="10027">
									<a class="time-line-dot" href=""><em></em></a> <a data-dit="m4" class="time-line-a" href="">计提设置</a>
								</dd>
	<!-- 							<dd class="time-line-dd time-line-point" data-dit="10014"> -->
	<!-- 								<a class="time-line-dot" href=""><em></em></a> <a data-dit="m3" class="time-line-a" href="">计提应收利息</a> -->
	<!-- 							</dd> -->
	<!-- 							<dd class="time-line-dd time-line-point" data-dit="10015"> -->
	<!-- 								<a class="time-line-dot" href=""><em></em></a> <a data-dit="m3" class="time-line-a" href="">凭证快捷键</a> -->
	<!-- 							</dd> -->
							</dl>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row param_tit">
			<h1>系统参数设置</h1>
		</div>
		<div id='month_step_body' class='month_step'>
			<div id="cwSysParmDiv">
				<div class="row set_item" id="code80011" data-val="80011">
					<div class="div1">
						<label class='span1'>启用会计期间:</label>
					</div>
					<div class="div2">
						<input class="form-control vch-date" type="text" id="weeks" name="weeks" value="${dataMap.weeks }" disabled>
					</div>
				</div>
				<div class="row set_item" id="code10001" data-val="10001">
					<div class="div1">
						<label class='span1'>出纳启用期间:</label>
					</div>
					<div class="div2">
						<input class="form-control vch-date" type="text" id="cashierWeek" name="cashierWeek" autocomplete="off" onclick="laydatemonth(this);" onkeydown="enterKey();" value="${dataMap.cashierWeek }">
					</div>
					<div class="divbutton1">
						<button class="btn btn-primary" onclick="setInitWeek();">保存</button>
					</div>
				</div>
				<div class="row set_item" id="code80027" data-val="80027">
					<div class="div1">
						<label class='span1'>科目长度设置:</label>
					</div>
					<div class="div2">
						<select class="form-control" id="comType" name="comType">
							<!-- <option value="">请选择</option> -->
							<option value="0">4-2-2（默认）</option>
							<option value="1">4-3-3</option>
							<option value="2">4-4-4</option>
							<option value="3">4-5-5</option>
						</select> <a href="javascript:void(0);" class="com_preview" onclick="showCodeLenth();">科目规则预览</a>

					</div>
					<div class="divbutton1">
						<button class="btn btn-primary" onclick="setComType()">保存</button>
					</div>
				</div>
				<c:forEach items="${dataMap.list}" var="items">
					<c:if test='${items.pcode!="10001"}'>
						<div class="row set_item" id="code${items.pcode}"  data-val="${items.pcode}">
							<div class="div1">
								<label class="span1">${items.pname}:</label>
							</div>
							<div class="div2">
								<c:forEach items="${items.parmList }" var="param" varStatus="st">
									<input type="radio" name="pcode${items.pcode}" pcode="${items.pcode}" value="${param[0]}" <c:if test='${param[0]==items.pvalue}'>
								</c:if> onclick="updateParamValue(this)">
									<label class="span2" for="pcode${items.pcode}">${param[1]}</label>
									<br>
								</c:forEach>
							</div>
						</div>
					</c:if>
				</c:forEach>
				<!--计提参数设置  -->
				<div class="row set_item" id="code10027" data-val="10027">
					<div class="div1">
						<label class='span1'>计提方式:</label>
					</div>
					<div class="div2">
						<%-- <select class="form-control" id="jitiType" name="jitiType">
							<!-- <option value="">请选择</option> -->
							<option value="0">全部</option>
							<option value="1">五级分类</option>
						</select> --%>
						<input class="form-control" type="text" id="jitiType" name="jitiType" value="${dataMap.jitiType}" disabled>
						 <a href="javascript:void(0);" class="com_preview" onclick="jiTiType();">计提设置</a>
					</div>
					
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
</script>
<script type="text/javascript">
	var comType ='${dataMap.comType}';
	var xjllfx ='${dataMap.xjllfx}';
	var pzfskg ='${dataMap.pzfskg}';
	var sfkmtz ='${dataMap.sfkmtz}';
	var yefxjz ='${dataMap.yefxjz}';
	var parmOffSet = {};
	$(function() {
		$('#comType').val(comType);
		//存贮相对位置
		$('.set_item').each(function(){
			var code=$(this).attr('data-val');
			parmOffSet[code] = $(this).offset().top;
		});
		//科目下拉框单击事件
		$(".work-zone-timeLine").on("click", ".time-line-dot, .time-line-a", function() {
			var that = $(this).parent();
			$(".time-line-dot").removeClass('line-dot-on i i-duihao1');
			$(".time-line-dot1").removeClass('line-dot-on i i-duihao1');
			that.children(".time-line-dot").addClass('line-dot-on i i-duihao1');
			var code=that.attr('data-dit');
			var mtop = parmOffSet[code] - 109;
			$("#cwSysParmDiv").animate({marginTop: "-" +mtop + "px" }, {duration: 500,easing: "swing"});
	        return false;
		});
		
		//自定义滚动条month_step
		$(".month_step").mCustomScrollbar({
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		
		if(xjllfx==0){
			$(":radio[name='pcode10000'][value='0']").attr("checked","checked");
		}else{
			$(":radio[name='pcode10000'][value='1']").attr("checked","checked");
		}
		if(pzfskg==0){
			$(":radio[name='pcode10002'][value='0']").attr("checked","checked");
		}else{
			$(":radio[name='pcode10002'][value='1']").attr("checked","checked");
		}
		if(sfkmtz==0){
			$(":radio[name='pcode10012'][value='0']").attr("checked","checked");
		}else{
			$(":radio[name='pcode10012'][value='1']").attr("checked","checked");
		}
		if(yefxjz==0){
			$(":radio[name='pcode10013'][value='0']").attr("checked","checked");
		}else{
			$(":radio[name='pcode10013'][value='1']").attr("checked","checked");
		}

	});
	function updateParamValue(obj){
		var pcode = $(obj).attr('pcode');
		var pvalue = $(obj).val();
		var resultJson = {
			"pvalue" : pvalue,
			"pcode" : pcode
		};
		goAjax(resultJson);
	}
	
	function goAjax(resultJson) {
		$.ajax({
			url : webPath+'/cwSysParam/changePvalueByCodeAjax',
			type : 'post',
			data : resultJson,
			async : false,
			dataType : 'json',
			error : function(data) {
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			},
			success : function(data) {
				if (data.flag == 'success') {
					//alert("修改成功",1);
				} else {
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}

			}
		});
	}
	
	
	//设置科目编码规则
	function setComType() {
		var comType = $('#comType').val();
		if (comType != '') {
			alert(top.getMessage("WAIT_OPERATION", "设置该规则"), 2, function() {
				var ajaxD = "{'initType':'comtype', 'comType':'" + comType
						+ "'}";
				setInitPram(ajaxD);
			});

		}
	}
	//初始化财务数据
	var repeat = 0;
	function setInitPram(ajaxData) {
		if (repeat > 0) {
			//alert("正在执行，请勿重复操作!", 1);
			alert(top.getMessage("WAIT_OPERATION", "执行"));
			return false;
		} else {
			window.top.LoadingAnimate.start();
			repeat++;
			jQuery.ajax({
				url : webPath+'/cwInitSystem/cwSetInitPramAjax',
				data : {
					ajaxData : ajaxData
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {
					if (data.flag == "success") {
						alert(top.getMessage("SUCCEED_OPERATION"), 1);
						location.reload();
					} else if (data.flag == "error") {
						alert(data.msg, 0);
						repeat--;
					}
					window.top.LoadingAnimate.stop();
				},
				error : function(data) {
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
					window.top.LoadingAnimate.stop();
					repeat--;
				}
			});
		}
	}

	//设置启用期间
	function setInitWeek() {
		var cashierWeek = $('#cashierWeek').val();
		if (cashierWeek != '') {
			var ajaxD = "{'initType':'cashier', 'cashierWeek':'" + cashierWeek + "'}";
			setInitPram(ajaxD);
		}
	}
	//科目编码预览
	function showCodeLenth() {
		var length = $('#comType').val();
		if (length != '') {
			window.parent.openBigForm(
					webPath+"/cwComItem/getAccnoListPage?ajaxData="
							+ length, '科目编码规则预览');
		}
	}
	//计提设置
	function jiTiType(){
		top.addFlag = false;
		top.htmlStrFlag = false;
		//window.parent.openBigForm("${webPath}/cwSysParam/getJiTiType","计提方式设置",'90','90');
		top.createShowDialog(webPath+"/cwSysParam/getJiTiType","计提方式设置",'90','90',function(){
			if(top.addFlag){
				//updateTableData();//重新加载列表数据
				window.location.href=webPath+"/cwSysParam/toSysParamSetPage";
			}
		}); 
	}
</script>
</html>