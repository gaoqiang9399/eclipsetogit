<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ page import="app.component.common.DateUtil"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>参数化设置</title>
<%-- <script type="text/javascript" src="${webPath}/component/sys/js/B1.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script> --%>
<link rel="stylesheet"
	href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="${webPath}/component/sys/css/B1.css" />
<link id="B1" rel="stylesheet" type="text/css"
	href="${webPath}/themes/factor/css/B1${skinSuffix}.css" />
<style>
.work-zone-timeLine{
    margin-left: -40px;
	top: 0px;
	left: 0px;
	right: auto;
}
.month_step {
	overflow-y: auto;
	width: 1000px;
	/* hight: 900px; */
	margin-bottom: 20px;
	margin-left: 200px;
	/* padding: 20px 20px 0; */
	background-color: #ffffff;
	font-size: 12px;
	/*  margin: 15px;  */
	/* box-shadow: 0 1px 2px rgba(0, 0, 0, 0.11); */
	/* zoom: 1; */
	padding-left: 100px;

	/* border: 1px solid #ffffff; */
}

.div0 {
	width: 260px;
	padding-left: 30px;
}

.div1 {
	width: 180px;
	/* padding-left: 50px;
	/* padding-bottom:20px; */
	margin-bottom: 30px;
	/* 	border: 1px solid #ccc; */
	float: left;
}

.div2 {
	width: 260px;
	margin-left: 90px;
	/* 	margin: 10px; */
	/* position: relative;  */
	/* position:absolute; */
	left: 250px;
	/* border: 1px solid #ccc; */
	top: -49px;
	float: left;
	margin-bottom: 20px;
}

.span1 {
	font-size: 16px;
	font-weight: lighter;
	font-color: #65AFE5;
}

.span2 {
	font-size: 14px;
	font-weight: lighter;
	font-color: #65AFE5;
}

.span3 {
	font-size: 18px;
}

.div3 {
	margin-top: 6px;
	margin-bottom: 6px;
}

.time-line-line1 {
	border-left: none;
	border-color: #c0c9d8;
	top: 40px;
	width: 0px;
	background-color: #606A7B;
	height: 588px;
	/* border-left: 1px solid #606A7B; */
	border-right: 1px solid #606A7B;
	margin: 20px auto;
	position: absolute;
	margin-left: 80px;
}

.work-zone-timeLine {
	position: fixed;
	background-color: #F0F5FB;
	/* border-left: 1px solid #606A7B;  */
	/* overflow-y: auto; */
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
	margin: 6px;
	padding-left: 20px;
	float: left;
}
/* .time-line-dd{
	margin : 10px 0px;
} */
.attsty:hover {
	color: #ffffff !important;
}
</style>
</head>
<!-- class="overflowHidden" -->
<body style="background-color: #FFFFFF; padding: 0px 0px 0px;"
	class="overflowHidden">

	<div class="work-zone-timeLine col-sm-2">
		<div class="time_contents">
			<div class="time-line-bg">
				<div
					class="time-line-border time-line-border1 time-line-point time-line-point-select"
					data-dit="all" id="time-line-all">
					<span class="time-line-dot1 line-dot-on i i-duihao1"></span> <a
						class="time-line-title line-a-on">系统参数设置</a>
				</div>

				<div class="time-line-line1"></div>
				<div class="time-line-body">
					<dl class="time-line-dl1">
						<%-- <dd class="time-line-dd time-line-point" data-dit="d0">
							<span class="time-line-dot"><em></em></span>
							<a data-dit="d0" class="time-line-a">今天</a>
						</dd> --%>
						<dd class="time-line-dd time-line-point" data-dit="d3">
							<a class="time-line-dot attsty" href="#lzsb"><em></em></a> <a
								data-dit="d3" href="#lzsb" class="time-line-a">启用会计期间</a>
						</dd>
						<dd class="time-line-dd time-line-point" data-dit="d7"
							href="#lzsc">
							<a class="time-line-dot attsty" href="#lzsc"><em></em></a> <a
								data-dit="d7" class="time-line-a" href="#lzsc">出纳启用期间</a>
						</dd>
						<dd class="time-line-dd time-line-point" data-dit="d14">
							<a class="time-line-dot attsty" href="#lzs3"><em></em></a> <a
								data-dit="d14" class="time-line-a" href="#lzs3">科目长度设置</a>
						</dd>
						<dd class="time-line-dd time-line-point" data-dit="m1">
							<a class="time-line-dot" href="#pcode10000"><em></em></a> <a data-dit="m1"
								class="time-line-a" href="#pcode10000">现金流量分析</a>
						</dd>
						<dd class="time-line-dd time-line-point" data-dit="m3">
							<a class="time-line-dot" href="#pcode10002"><em></em></a> <a
								data-dit="m3" class="time-line-a" href="#pcode10002">凭证金额负数</a>
						</dd>
						<dd class="time-line-dd time-line-point" data-dit="m6">
							<a class="time-line-dot" href="#pcode10003"><em></em></a> <a
								data-dit="m6" class="time-line-a" href="#pcode10003">结账时检查</a>
						</dd>
						<dd class="time-line-dd time-line-point" data-dit="m3">
							<a class="time-line-dot" href="#pcode10004"><em></em></a> <a
								data-dit="m3" class="time-line-a" href="#pcode10004">设置计提方式</a>
						</dd>
						<dd class="time-line-dd time-line-point" data-dit="m3">
							<a class="time-line-dot" href="#pcode10011"><em></em></a> <a
								data-dit="m3" class="time-line-a" href="#pcode10011">月结时检查计提</a>
						</dd>
						<dd class="time-line-dd time-line-point" data-dit="m3">
							<a class="time-line-dot" href="#pcode10012"><em></em></a> <a
								data-dit="m3" class="time-line-a" href="#pcode10012">报表科目方向调整</a>
						</dd>
						<dd class="time-line-dd time-line-point" data-dit="m3">
							<a class="time-line-dot" href="#pcode10013"><em></em></a> <a
								data-dit="m3" class="time-line-a" href="#pcode10013">按余额反方向结转</a>
						</dd>
						<dd class="time-line-dd time-line-point" data-dit="m3">
							<a class="time-line-dot" href="#pcode10014"><em></em></a> <a
								data-dit="m3" class="time-line-a" href="#pcode10014">计提应收利息</a>
						</dd>
						<dd class="time-line-dd time-line-point" data-dit="m3">
							<a class="time-line-dot" href="#pcode10015"><em></em></a> <a
								data-dit="m3" class="time-line-a" href="#pcode10015">凭证快捷键</a>
						</dd>


					</dl>

				</div>
				<%-- <div class="time-line-border time-line-border2 time-line-point" data-dit="all">
					<!-- <div class="time-line-head time-line-head2"></div> -->
					<span class="time-line-dot2"></span>
					<a class="time-line-title">更多</a>
				</div> --%>
			</div>
		</div>
	</div>
	<div id='month_step_body' class='month_step'>

		<div id="cwSysParmDiv">

			<div class=" set_item clearfix " id="lzsb">
				<div class="div1">
					<label class='span1'>启用会计期间:</label>
				</div>
				<div class="div2">
					<input class="form-control vch-date" type="text" id="weeks"
						name="weeks" value="2016年02期" disabled>
				</div>
			</div>
			<div class=" set_item clearfix " id="lzsc">
				<div class="div1">
					<label class='span1'>出纳启用期间:</label>
				</div>
				<div class="div2">
					<input class="form-control vch-date" type="text" autocomplete="off"
						onclick="laydatemonth(this);" onkeydown="enterKey();" value="">
				</div>
				<div class="divbutton1">
					<button class="btn btn-info btn-xs" onclick="setInitWeek();">保存</button>
				</div>
			</div>
			<div class=" set_item clearfix " id="lzs3">
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
					</select> <a href="javascript:void(0);" onclick="showCodeLenth();">科目规则预览</a>

				</div>
				<div class="divbutton1">
					<button class="btn btn-info btn-xs" onclick="setComType()">保存</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(".time-line-dot").bind('click', function() {
		var that = $(this);
		$(".time-line-dot").removeClass('line-dot-on i i-duihao1');
		$(".time-line-dot1").removeClass('line-dot-on i i-duihao1');

		that.addClass('line-dot-on i i-duihao1');
	});
	$(".time-line-a").bind('click', function() {
		var that = $(this).prev();
		$(".time-line-dot").removeClass('line-dot-on i i-duihao1');
		$(".time-line-dot1").removeClass('line-dot-on i i-duihao1');
		that.addClass('line-dot-on i i-duihao1');
	});
</script>
<script type="text/javascript">
	var beanlistjson = '${beanlistjson}';
	//var beanlist = '${beanlist}';
	var tembean = $.parseJSON(beanlistjson);
	for ( var i = 0; i < tembean.length; i++) {

		if (tembean[i].params) {
			var st1 = tembean[i].params.split(",");
			var st10 = st1[0].split(":");//0
			var st11 = st1[1].split(":");//1
			$("#cwSysParmDiv").append("<div  class=\"set_item clearfix\">");
			$("#cwSysParmDiv").append(
					"<div class=\"div1\" id=\"pcode"+tembean[i].pcode+"\" ><label class='span1'>"
							+ tembean[i].pname + ":</label></div>");
			$("#cwSysParmDiv")
					.append(
							"<div class=\"div2\"><input type=\"hidden\" id=\"pcode"
									+tembean[i].pcode+"text\" name=\"pcode"
									+tembean[i].pcode+"\"  value=\""
									+tembean[i].pcode+"\" /><input type=\"radio\" id=\"pcode1"
									+tembean[i].pcode+"\" name=\"pcode"+
									tembean[i].pcode+"\" pcode=\""+tembean[i].pcode+"\" value=\""
									+st10[0]+"\" onclick=\"updateParamValue(this)\" /><label class=\"span2\" for=\"pcode1"+tembean[i].pcode+"\">"
									+ st10[1]+ " </label><br><input type=\"radio\" id=\"pcode2"
									+tembean[i].pcode+"\" name=\"pcode"
									+tembean[i].pcode+"\" pcode=\""+tembean[i].pcode+"\" value=\""+st11[0]+"\" onclick=\"updateParamValue(this)\" /> <label class=\"span2\" for=\"pcode2"
									+tembean[i].pcode+"\">"
									+ st11[1] + "</label>");
			$("#cwSysParmDiv").append("</div>");
			$("#cwSysParmDiv").append("</div>");
			 if(tembean[i].pvalue==st10[0]){
				//$("input[name='checkboxall']").prop("checked", true);
				$("#pcode1"+tembean[i].pcode).prop("checked", true);
			}else{
				$("#pcode2"+tembean[i].pcode).prop("checked", true);
			}
			
		}
	}
	$(function() {
		//自定义滚动条month_step
		$("body").mCustomScrollbar({
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});

	});
	function updateParamValue(obj){
		
		//var pcode = $(obj).attr('id');
		var pcode = $(obj).attr('pcode');
		var pvalue = $(obj).val();
		//alert(pcode+"====="+pvalue);
		var resultJson = {
			"pvalue" : pvalue,
			"pcode" : pcode
		};
		goAjax(resultJson);
	}
	function goAjax(resultJson) {
		$.ajax({
			url : '${webPath}/cwSysParam/changePvalueByCodeAjax',
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
			layer.alert(top.getMessage("WAIT_OPERATION", "执行"))
			return false;
		} else {
			window.top.LoadingAnimate.start();
			repeat++;
			jQuery.ajax({
				url : '${webPath}/cwInitSystem/cwSetInitPramAjax',
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
		var weeks = $('#weeks').val();
		if (weeks != '') {
			var ajaxD = "{'initType':'week', 'weeks':'" + weeks + "'}";
			setInitPram(ajaxD);
		}
	}
	//科目编码预览
	function showCodeLenth() {
		var length = $('#comType').val();
		if (length != '') {
			window.parent.openBigForm(
					"${webPath}/cwComItem/getAccnoListPage?ajaxData="
							+ length, '科目编码规则预览');
		}
	}
</script>
</html>