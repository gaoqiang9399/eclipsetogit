<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/webPath.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String layout = "layout/view";
%>
<%-- <jsp:include page="/creditapp/talkjs.jsp"></jsp:include> --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<title>新增</title>
		<link rel="stylesheet" href="formline.css" />
		<link rel="stylesheet" href="autocompleter.css" />
		<script type="text/javascript" src="../js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="jquery.autocompleter.readOnly.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/uior_val.js"></script>
		<%-- <script type="text/javascript" src="${webPath}/component/include/jquery.autocompleter.js"></script> --%>
		<style type="text/css">
			.content_form,
			.page_w {
				display: none;
			}
			
			.table tr {
				line-height: 25px;
			}
			
			.content table td {
				vertical-align: top;
			}
			
			input[type="text"],
			input[type="password"],
			input[type="email"],
			input[type="number"],
			input[type="tel"],
			input[type="url"],
			select,
			textarea {
				height: 25px;
				padding: 0 0px 0 3px;
				box-shadow: none;
			}
			
			.form_number{
				text-align: right;
				width: 120px;
			}
			
			.form_select {
				background: #fefefe;
			}
			
			input[type="text"].disable {
				background: #eef0f3;
				border-color: #dbdcde;
			}
			
			.UnitFont {
				font-size: 12px;
				color: #707174;
			}
			
			input[type="text"].Required {
				background: #fcf8f8;
				border-color: #f33434;
			}
			
			.Required-font {
				font-size: 12px;
				color: #dd2a18;
				position: absolute;
				padding-left: 26px;
				width: 120px;
			}
			
			.Required-font span {
				width: 16px;
				height: 16px;
				display: block;
			/* 	background: url(Required.png) no-repeat; */
				margin: 0 3px;
				position: absolute;
				left: 0;
				top: 0;
				animation: myfirst 1s linear 0s infinite alternate running;
				-moz-animation: myfirst 1s linear 0s infinite alternate running;	/* Firefox */
				-webkit-animation: myfirst 1s linear 0s infinite alternate running;	/* Safari 和 Chrome */
				-o-animation: myfirst 1s linear 0s infinite alternate running;
			}
			@keyframes myfirst
			{
			0%   {opacity: 1; filter:Alpha(opacity=100);}
			50%  {opacity: 0.2; filter:Alpha(opacity=20);}
			100% {opacity: 1; filter:Alpha(opacity=100);}
			}
			
			@-moz-keyframes myfirst /* Firefox */
			{
			0%   {opacity: 1; filter:Alpha(opacity=100);}
			50%  {opacity: 0.2; filter:Alpha(opacity=20);}
			100% {opacity: 1; filter:Alpha(opacity=100);}
			}
			
			@-webkit-keyframes myfirst /* Safari 和 Chrome */
			{
			0%   {opacity: 1; filter:Alpha(opacity=100);}
			50%  {opacity: 0.2; filter:Alpha(opacity=20);}
			100% {opacity: 1; filter:Alpha(opacity=100);}
			}
			
			@-o-keyframes myfirst /* Opera */
			{
			0%   {opacity: 1; filter:Alpha(opacity=100);}
			50%  {opacity: 0.2; filter:Alpha(opacity=20);}
			100% {opacity: 1; filter:Alpha(opacity=100);}
			}
		</style>
		<script type="text/javascript">
			$(function() {
				$(".ls_list tbody").find("tr").each(function() {
					var obj = $(this).find("td").eq(0);
					$(obj).unbind();
					$(obj).bind("click",
						function() {
							var url = $(obj).find("a").attr("href");
							$(".content_form").slideDown("slow");
							return false;
						});
				});
				$(".form_select").click(function() {
					$(this).css("background", "#fff")
				})
				$(".form_select").blur(function() {
					$(this).css("background", "#fefefe")
				})
				/**
				*自动下拉
				*/
				/* var fields = ["demoName","demoId"];
				for(var i in fields){
					var fieldName = fields[i];
					var $input = $(".content input[name='"+fieldName+"']");
					if($input&&$input.length>0){
						prodAutoMenu($input,fieldName);
					}
				} */
			});
			/**
			*自动下拉
			*/
			function autoTest(data){
				alert("回调函数："+data.demoName)
			}
			
			function submitBtn(obj) {
				$(".content_form").slideUp();
			}

			function colseBtn() {
				$(".content_form").slideUp();
			}
			
			function formatDate(obj){
				var a = /^(\d{4})-(\d{1,2})-(\d{1,2})$/
				var $this =$(obj);
				var val = $this.val();
				if (!a.test(val)) { 
					if ($this.hasClass("Required")) {
						$this.removeClass("Required");
					}
					if($this.parent().find(".Required-font").length>0){
						$this.parent().find(".Required-font").remove();
					}
					var $label = $('<label class="Required-font"><span></span>日期格式不正确！</label>')
					$label.appendTo($this.parent());
					$this.addClass("Required");
					$this.val("");
					return false 
				}else{
					$this.val(val.replace("-/g",""));
				return true 
				} 
			}
		</script>
	</head>

	<BODY>
		<div class="content">
			<div name="formtest-1" class="formDiv">
				<h3> 客户信息 </h3>
				<form id="TestGnAction_update_action" name="operform" action="/RZZL_NEW/TestGnAction_update.action" method="post">
					<table title="formtest1002" height="auto" width="100%">
						<tbody>
							<tr>
								<td colspan="2">
									<h4> <span class="formtith4" >个人客户详细信息 </span> </h4></td>
							</tr>
							<tr>
								<td>
									<div class="formRow">
										<div class="formCol">
											<label class="formLbl"> 客户编号 </label>
											<div>
												<input title="客户编号" name="cifNo" datatype="0" mustinput="0" maxlength="40" onblur="namefirm();func_uior_valTypeImm(this);" onmousedown="enterKey()" readonly onkeydown="enterKey();" class="BOTTOM_LINE disable" value="10000405(disable)" type="text">
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 客户名称 </label>
											<div>
												<input title="客户名称" name="demoName" onclick="prodAutoMenu(this,'${webPath}/prodAutoMenu.action',null)" datatype="0" mustinput="0" maxlength="40" class="BOTTOM_LINE" value="王兆龙" type="text">
												
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 财务客户号 </label>
											<div>
												<input title="财务客户号" name="demoId" datatype="0" mustinput="0" maxlength="40" onblur="namefirm();func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE Required" value="" type="text"><i>*</i>
												<label class="Required-font"><span></span>信息不能为空</label>
											</div>
										</div>
									</div>

									<div class="formRow">
										<div class="formCol">
											<label class="formLbl"> 证件类型 </label>
											<div>
												<select name="idType" title="证件类型" class="form_select" mustinput="0" maxlength="18" onblur="idNoconfirm();func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" style="background: #eef0f3; width:auto;max-width: 400px;">
													<option value=""> </option>
													<option value="0" selected=""> 身份证 </option>
													<option value="1"> 户口本 </option>
													<option value="2"> 护照 </option>
													<option value="3"> 组织机构代码证 </option>
													<option value="4"> 测试自适应下拉，防止内容过长超过最大限制400px,测试自适应下拉，防止内容过长超过最大限制400px</option>
												</select><i>*</i>
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 证件号码 </label>
											<div>
												<input title="证件号码" name="idNo" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="210211******0123" type="text" style=""><i>*</i>
											</div>
										</div>
									</div>
									<div class="formRow"> </div>
									<div class="formRow">
										<div class="formCol">
											<label class="formLbl"> 性别 </label>
											<div>
												<select name="sex" title="性别" class="form_select" mustinput="0" maxlength="18" onblur="idNoconfirm();func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" style="width:50px;">
													<option value=""> </option>
													<option value="0" selected=""> 男 </option>
													<option value="1"> 女 </option>
												</select>
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 出生日期 </label>
											<div>
												<input title="日期" name="txDate" datatype="0" mustinput="1" maxlength="10" onblur="func_uior_valTypeImm(this);formatDate(this);"  onmousedown="enterKey()" onkeydown="enterKey();" onfocus="viewDate(this);" value="19850101" type="text" class="BOTTOM_LINE">
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 民族 </label>
											<div>
												<select name="sex" title="民族" class="form_select" mustinput="0" maxlength="18" onblur="idNoconfirm();func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" style="width:90px;">
													<option value=""> </option>
													<option value="0" selected=""> 汉族 </option>
													<option value="1"> 壮族 </option>
													<option value="2"> 回族 </option>
													<option value="3"> 傣族 </option>
													<option value="4"> 维吾尔族 </option>
												</select>
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 最高学历 </label>
											<div>
												<select name="sex" title="最高学历" class="form_select" mustinput="0" maxlength="18" onblur="idNoconfirm();func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" style="width:90px;">
													<option value=""> </option>
													<option value="0" selected=""> 初中 </option>
													<option value="1"> 大学 </option>
													<option value="2"> 小学 </option>
													<option value="3"> 高中 </option>
												</select>
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 最高学位 </label>
											<div>
												<select name="sex" title="最高学位" class="form_select" mustinput="0" maxlength="18" onblur="idNoconfirm();func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
													<option value=""> </option>
													<option value="0"> 博士 </option>
													<option value="1"> 硕士 </option>
													<option value="2"> 学士 </option>
													<option value="3"> 其他 </option>
												</select>
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 职业 </label>
											<div>
												<select name="sex" title="职业" class="form_select" mustinput="0" maxlength="18" onblur="idNoconfirm();func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
													<option value=""> </option>
													<option value="0" selected=""> 商业、服务业人员 </option>
													<option value="1"> 军人 </option>
													<option value="2"> 办公人员和有关人员 </option>
													<option value="3"> 专业技术人员 </option>
												</select>
											</div>
										</div>
									</div>
									<div class="formRow"> </div>
									<div class="formRow">
										<div class="formCol">
											<label class="formLbl"> 房产类型 </label>
											<div>
												<select name="sex" title="房产类型" class="form_select" mustinput="0" maxlength="18" onblur="idNoconfirm();func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" style="background: #eef0f3;">
													<option value=""> </option>
													<option value="0" selected=""> 完全产权房 </option>
													<option value="1"> 按揭购房 </option>
													<option value="2"> 租房 </option>
												</select><i>*</i>
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 负债月供合计 </label>
											<div>
												<input title="负债月供合计" name="txDate" datatype="0" mustinput="1" maxlength="10" onblur="func_uior_valTypeImm(this);" onclick="selectPop();;" onmousedown="enterKey()" onkeydown="enterKey();" value="0.00" type="text" class="form_number">
												<span class="UnitFont">元</span><i>*</i> </div>
										</div>
									</div>
									<div class="formRow"> </div>
									<div class="formRow">

										<div class="formCol">
											<label class="formLbl"> 是否异地 </label>
											<div>
												<select name="sex" title="是否异地" class="form_select" mustinput="0" maxlength="18" onblur="idNoconfirm();func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
													<option value=""> </option>
													<option value="0"> 是 </option>
													<option value="1"> 否 </option>
												</select>
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 户籍行政区划名称 </label>
											<div>
												<input title="户籍行政区划名称" name="txDate" datatype="0" mustinput="1" maxlength="10" onblur="func_uior_valTypeImm(this);" onclick="selectPop();;" onmousedown="enterKey()" onkeydown="enterKey();" value="广西壮族自治区>>桂林市>>灵川县" type="text" style="width:300px;"><i>*</i>
											</div>
										</div>
									</div>

									<div class="formRow">
										<div class="formCol">
											<label class="formLbl"> 户籍地址 </label>
											<div>
												<input title="户籍地址" name="txDate" datatype="0" mustinput="1" maxlength="10" onblur="func_uior_valTypeImm(this);" onclick="selectPop();;" onmousedown="enterKey()" onkeydown="enterKey();" value="广西灵川县三街镇龙门村委矮树村38号" type="text" style="width:300px;"><i>*</i>
											</div>
										</div>

										<div class="formCol">
											<label class="formLbl"> 现居住地址 </label>
											<div>
												<input title="现居住地址" name="txDate" datatype="0" mustinput="1" maxlength="10" onblur="func_uior_valTypeImm(this);" onclick="selectPop();;" onmousedown="enterKey()" onkeydown="enterKey();" value="广西灵川县三街镇龙门村委矮树村38号" type="text" style="width:300px;"><i>*</i>
											</div>
										</div>
									</div>
									<div class="formRow">
										<div class="formCol">
											<label class="formLbl"> 电子邮件地址 </label>
											<div>
												<input title="电子邮件地址" name="txDate" datatype="0" mustinput="1" maxlength="10" onblur="func_uior_valTypeImm(this);" onclick="selectPop();;" onmousedown="enterKey()" onkeydown="enterKey();" value="" type="text" class="BOTTOM_LINE">
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 传真 </label>
											<div>
												<input title="传真" name="txDate" datatype="0" mustinput="1" maxlength="10" onblur="func_uior_valTypeImm(this);" onclick="selectPop();;" onmousedown="enterKey()" onkeydown="enterKey();" value="" type="text" class="BOTTOM_LINE">
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 手机号码 </label>
											<div>
												<input title="手机号码" name="txDate" datatype="0" mustinput="1" maxlength="10" onblur="func_uior_valTypeImm(this);" onclick="selectPop();;" onmousedown="enterKey()" onkeydown="enterKey();" value="13878315867" type="text" class="BOTTOM_LINE"><i>*</i>
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 手机号码2 </label>
											<div>
												<input title="手机号码2	" name="txDate" datatype="0" mustinput="1" maxlength="10" onblur="func_uior_valTypeImm(this);" onclick="selectPop();;" onmousedown="enterKey()" onkeydown="enterKey();" value="" type="text" class="BOTTOM_LINE">
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 住宅电话 </label>
											<div>
												<input title="住宅电话" name="txDate" datatype="0" mustinput="1" maxlength="10" onblur="func_uior_valTypeImm(this);" onclick="selectPop();;" onmousedown="enterKey()" onkeydown="enterKey();" value="13878315867" type="text" class="BOTTOM_LINE">
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 住宅电话2 </label>
											<div>
												<input title="住宅电话2" name="txDate" datatype="0" mustinput="1" maxlength="10" onblur="func_uior_valTypeImm(this);" onclick="selectPop();;" onmousedown="enterKey()" onkeydown="enterKey();" value="" type="text" class="BOTTOM_LINE">
											</div>
										</div>
									</div>
								</td>
								<td>
									<div class="formRow">
										<div class="formCol">
											<label class="formLbl"> 财务客户简称 </label>
											<div>
												<input title="财务客户简称" name="cifName" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="" type="text">
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 邮寄地址 </label>
											<div>
												<input title="邮寄地址" name="txDate" datatype="0" mustinput="1" maxlength="10" onblur="func_uior_valTypeImm(this);" onclick="selectPop();;" onmousedown="enterKey()" onkeydown="enterKey();" value="广西灵川县三街镇龙门村委矮树村38号" type="text" style="width:300px;" class="BOTTOM_LINE">
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 邮寄地址邮编 </label>
											<div>
												<input title="邮寄地址邮编" name="txDate" datatype="0" mustinput="1" maxlength="10" onblur="func_uior_valTypeImm(this);" onclick="selectPop();;" onmousedown="enterKey()" onkeydown="enterKey();" value="541200" type="text" class="BOTTOM_LINE">
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 供养人口 </label>
											<div>
												<select name="sex" title="供养人口" class="form_select" mustinput="0" maxlength="18" onblur="idNoconfirm();func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
													<option value=""> </option>
													<option value="0"> 0人 </option>
													<option value="1"> 1人 </option>
													<option value="2"> 2人 </option>
													<option value="2"> 其他 </option>
												</select>
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 健康状况 </label>
											<div>
												<select name="sex" title="健康状况" class="form_select" mustinput="0" maxlength="18" onblur="idNoconfirm();func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
													<option value=""> </option>
													<option value="0"> 良好 </option>
													<option value="1"> 一般 </option>
													<option value="2"> 较差 </option>
													<option value="3"> 测试正常，下拉菜单是否，会超长导致样式变形</option>
												</select>
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 户籍行政编号 </label>
											<div>
												<input title="户籍行政编号" name="txDate" datatype="0" mustinput="1" maxlength="10" onblur="func_uior_valTypeImm(this);" onclick="selectPop();;" onmousedown="enterKey()" onkeydown="enterKey();" value="450323" type="text" class="BOTTOM_LINE">
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 居住地邮编 </label>
											<div>
												<input title="居住地邮编" name="txDate" datatype="0" mustinput="1" maxlength="10" onblur="func_uior_valTypeImm(this);" onclick="selectPop();;" onmousedown="enterKey()" onkeydown="enterKey();" value="541200" type="text" class="BOTTOM_LINE">
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 居住状况 </label>
											<div>
												<select name="sex" title="居住状况" class="form_select" mustinput="0" maxlength="18" onblur="idNoconfirm();func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
													<option value=""> </option>
													<option value="0" selected> 自置 </option>
													<option value="1"> 按揭 </option>
													<option value="2"> 其他 </option>
												</select>
											</div>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<h4 class="RowTopline"> <span class="formtith4">工作单位信息 </span> </h4>
								</td>
							</tr>
							<tr>
								<td>

									<div class="formRow">
										<div class="formCol">
											<label class="formLbl"> 单位名称 </label>
											<div>
												<input title="单位名称" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="广西灵川县三街镇龙门村委矮树村38号" type="text" style="width:300px;"><i>*</i>
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 单位所属行业 </label>
											<div>
												<select name="sex" title="单位所属行业" class="form_select" mustinput="0" maxlength="18" onblur="idNoconfirm();func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
													<option value=""> </option>
													<option value="0" selected> 其他 </option>
													<option value="1"> 采矿业 </option>
													<option value="2"> 教育 </option>
												</select><i>*</i>
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 单位性质 </label>
											<div>
												<select name="sex" title="单位性质" class="form_select" mustinput="0" maxlength="18" onblur="idNoconfirm();func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
													<option value=""> </option>
													<option value="0" selected> 其他 </option>
													<option value="1"> 军队 </option>
													<option value="2"> 国有企业 </option>
												</select><i>*</i>
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 单位地址 </label>
											<div>
												<input title="单位地址" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="广西灵川县三街镇龙门村委矮树村38号" type="text" style="width:300px;"><i>*</i>
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 单位邮编 </label>
											<div>
												<input title="单位邮编" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="" type="text">
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 单位电话 </label>
											<div>
												<input title="单位电话" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="13878315867" type="text"><i>*</i>
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 职务 </label>
											<div>
												<select name="sex" title="职务" class="form_select" mustinput="0" maxlength="18" onblur="idNoconfirm();func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
													<option value=""> </option>
													<option value="0"> 高级领导 </option>
													<option value="1"> 中级领导 </option>
													<option value="2"> 一般员工 </option>
												</select>
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 职称 </label>
											<div>
												<select name="sex" title="职称" class="form_select" mustinput="0" maxlength="18" onblur="idNoconfirm();func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
													<option value=""> </option>
													<option value="0"> 高级 </option>
													<option value="1"> 中级 </option>
													<option value="2"> 初级 </option>
												</select>
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 本单位工作期限 </label>
											<div>
												<input title="本单位工作期限" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="48" type="text">
												<span class="UnitFont">月</span> </div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 月收入 </label>
											<div>
												<input title="月收入" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="5000.00" type="text">
												<span class="UnitFont">元</span> </div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 本单位工作起始时间 </label>
											<div>
												<input title="本单位工作起始时间 " name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="" type="text">
											</div>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<h4 class="RowTopline"> <span class="formtith4">配偶信息 </span> </h4></td>
							</tr>
							<tr>
								<td>
									<div class="formRow">
										<div class="formCol">
											<label class="formLbl"> 婚姻状况 </label>
											<div>
												<select name="sex" title="婚姻状况" class="form_select" mustinput="0" maxlength="18" onblur="idNoconfirm();func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
													<option value=""> </option>
													<option value="0" selected> 已婚 </option>
													<option value="1"> 未婚 </option>
													<option value="2"> 丧偶 </option>
												</select>
												<i>*</i>
											</div>
										</div>
									</div>
									<td>
										<div class="formRow">
											<div class="formCol">
												<label class="formLbl"> 配偶姓名 </label>
												<div>
													<input title="配偶姓名" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="" type="text">
												</div>
											</div>
											<div class="formCol">
												<label class="formLbl"> 配偶证件类型 </label>
												<div>
													<select name="sex" title="配偶证件类型" class="form_select" mustinput="0" maxlength="18" onblur="idNoconfirm();func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
														<option value=""> </option>
														<option value="0" selected> 身份证 </option>
														<option value="1"> 护照 </option>
														<option value="2"> 士兵证 </option>
													</select>
												</div>
											</div>
											<div class="formCol">
												<label class="formLbl"> 配偶证件号码 </label>
												<div>
													<input title="配偶证件号码" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="" type="text">
												</div>
											</div>
											<div class="formCol">
												<label class="formLbl"> 配偶工作单位 </label>
												<div>
													<input title="配偶工作单位" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="" type="text">
												</div>
											</div>
											<div class="formCol">
												<label class="formLbl"> 配偶职业 </label>
												<div>
													<select name="sex" title="配偶职业" class="form_select" mustinput="0" maxlength="18" onblur="idNoconfirm();func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
														<option value=""> </option>
														<option value="0"> 专业技术人员 </option>
														<option value="1"> 军人 </option>
														<option value="2"> 商业、服务业人员 </option>
													</select>
												</div>
											</div>
											<div class="formCol">
												<label class="formLbl"> 配偶职务 </label>
												<div>
													<select name="sex" title="配偶职务" class="form_select" mustinput="0" maxlength="18" onblur="idNoconfirm();func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
														<option value=""> </option>
														<option value="0"> 高级领导 </option>
														<option value="1"> 中级领导 </option>
														<option value="2"> 一般员工 </option>
													</select>
												</div>
											</div>
											<div class="formCol">
												<label class="formLbl"> 配偶参加工作时间 </label>
												<div>
													<input title="配偶参加工作时间" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="" type="text">
												</div>
											</div>
											<div class="formCol">
												<label class="formLbl"> 配偶手机号码 </label>
												<div>
													<input title="配偶手机号码" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="" type="text">
												</div>
											</div>
											<div class="formCol">
												<label class="formLbl"> 配偶单位联系电话 </label>
												<div>
													<input title="配偶单位联系电话" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="" type="text">
												</div>
											</div>
										</div>
									</td>
								</td>
							</tr>
							<tr><td colspan="2"><h4 class="RowTopline"> <span class="formtith4">经济来源</span> </h4></td></tr>
							<tr>
								<td>
									<div class="formRow">
										<div class="formCol">
											<label class="formLbl"> 主要经济来源 </label>
											<div>
												<textarea name="remark" title="其他" onfocus="showCharsInfo(this);" onblur="func_uior_valTypeImm(this);hideCharsInfo(this);" onmousedown="enterKey()" onkeydown="enterKey();" onkeyup="textareaInputCount(this)" mustinput="0" datatype="0" style="width:300px; height:50px;"></textarea>
											</div>
										</div>
									</div>
									<div class="formRow">
										<div class="formCol">
											<label class="formLbl"> 其他经济来源 </label>
											<div>
												<textarea name="remark" title="其他" mustinput="0" datatype="0" onblur="func_uior_valTypeImm(this);hideCharsInfo(this);" onfocus="showCharsInfo(this);" onkeyup="textareaInputCount(this)" onmousedown="enterKey()" onkeydown="enterKey();" style="width:300px; height:50px;"></textarea>
											</div>
										</div>
									</div>
										</td>
							</tr>
							<tr><td colspan="2"><h4 class="RowTopline"> <span class="formtith4">登记信息 </span> </h4></td></tr>
							<tr>
								<td>
									
									<div class="formRow">
										<div class="formCol">
											<label class="formLbl"> 登记机构 </label>
											<div>
												<input title="登记机构" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="10000113S" type="text">
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 登记单位名称 </label>
											<div>
												<input title="登记单位名称" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="" type="text" style="width:300px;">
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 登记人名称 </label>
											<div>
												<input title="登记人名称" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="魏丽" type="text">
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 修改人名称 </label>
											<div>
												<input title="修改人名称" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="黄菡" type="text">
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 登记时间 </label>
											<div>
												<input title="登记时间" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="2014.3.15" type="text">
											</div>
										</div>
										<div class="formCol">
											<label class="formLbl"> 更新时间 </label>
											<div>
												<input title="更新时间" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="2014.3.20" type="text">
											</div>
										</div>
										<div class="formRow" style="width:100%; padding:10px 0; margin:0;">
											<input value="保存" type="submit" style="margin:0;">
										</div>
								</td>

							</tr>
						</tbody>
					</table>
				</form>
				</div>
				<div name="formtest-3" class="formDiv">
					<h3> 业务信息 </h3>
					<div class="content_body">
						<div class="content_table  yewu_table">
							<table title="formtest1002" height="auto" width="100%">
								<tbody>
									<tr>
										<td>
											<h4> <span class="formtith4">基本信息</span></h4>
											<div>
												<div class="">
													<div class="formCol">
														<label class="formLbl"> 业务申请号 </label>
														<input title="业务申请号" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="B20150826103027" type="text"><i>*</i>

													</div>
													<div class="formCol">
														<label class="formLbl"> 合同号 </label>

														<input title="合同号" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="B20150826103027-1" type="text"><i>*</i>

													</div>
													<div class="formCol">
														<label class="formLbl"> 客户号 </label>

														<input title="客户号" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="10015458" type="text"><i>*</i>

													</div>
													<div class="formCol">
														<label class="formLbl"> 客户名称 </label>

														<input title="客户名称" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="某某某" type="text"><i>*</i>

													</div>
													<div class="formCol">
														<label class="formLbl"> 产品号 </label>

														<input title="产品号" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="1263" type="text">

													</div>
													<div class="formCol">
														<label class="formLbl"> 产品名称 </label>

														<input title="产品名称" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="商用车等额本息（公路用车）" type="text"><i>*</i>

													</div>
													<div class="formCol">
														<label class="formLbl"> 经销商 </label>

														<input title="经销商 " name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="10000228" type="text"><i>*</i>

													</div>
													<div class="formCol">
														<label class="formLbl"> 经销商名称 </label>

														<input title="经销商名称" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="广州胜龙汽车贸易有限公司" type="text"><i>*</i>

													</div>
													<div class="formCol">
														<label class="formLbl"> 供货商 </label>

														<input title="供货商" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="10000001" type="text">

													</div>
													<div class="formCol">
														<label class="formLbl"> 供货商名称 </label>

														<input title="供货商名称" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="东风柳州汽车有限公司" type="text">

													</div>
													<div class="formCol">
														<label class="formLbl"> 还款方式 </label>

														<input title="还款方式" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="普通贷(等额本息) " type="text"><i>*</i>

													</div>
													<div class="formCol">
														<label class="formLbl"> 产品类型 </label>

														<input title="产品类型" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="商用车 " type="text"><i>*</i>

													</div>
													<div class="formCol">
														<label class="formLbl"> 币种 </label>

														<input title="币种" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="人民币 " type="text">

													</div>
													<div class="formCol">
														<label class="formLbl"> 担保方式 </label>

														<input title="担保方式" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="" type="text"><i>*</i>

													</div>
												</div>
											</div>
											<h4 class="RowTopline"> <span class="formtith4">金融产品信息</span></h4>
											<div class="">
												<div class="formCol">
													<label class="formLbl"> 期限月 </label>

													<input title="期限月" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="24" type="text">

												</div>
												<div class="formCol">
													<label class="formLbl"> 执行利率 </label>

													<input title="执行利率" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="9.18%" type="text">

												</div>
												<div class="formCol">
													<label class="formLbl"> 日罚息利率 </label>

													<input title="日罚息利率" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="日罚息利率" type="text">

												</div>
												<div class="formCol">
													<label class="formLbl"> 预计起租日期 </label>
													<input title="预计起租日期" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="2015-08-26" type="text">
												</div>
												<div class="formCol">
													<label class="formLbl"> 总价款 </label>
													<input title="总价款" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="216,000.00元" type="text">
												</div>
												<div class="formCol">
													<label class="formLbl"> 实际融资额 </label>
													<input title="实际融资额" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="172,800.00元" type="text">
												</div>
												<div class="formCol">
													<label class="formLbl"> 首付比例 </label>
													<input title="首付比例 " name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="20.0%" type="text">
												</div>
												<div class="formCol">
													<label class="formLbl"> 首付金额 </label>
													<input title="首付金额" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="43,200.00元" type="text">
												</div>
												<div class="formCol">
													<label class="formLbl"> 尾款比例 </label>
													<input title="尾款比例" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="0.0%" type="text">
												</div>
												<div class="formCol">
													<label class="formLbl"> 保证金比例 </label>
													<input title="保证金比例" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="10.0%" type="text">
												</div>
												<div class="formCol">
													<label class="formLbl"> 保证金金额 </label>
													<input title="保证金金额" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="17,280.00元 " type="text">
												</div>
												<div class="formCol">
													<label class="formLbl"> 名义价款 </label>
													<input title="名义价款" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="100.00元/台 " type="text">
												</div>
												<div class="formCol">
													<label class="formLbl"> 产品数量 </label>
													<input title="产品数量" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="1 " type="text">
												</div>
												<div class="formCol">
													<label class="formLbl"> 手续费金额 </label>
													<input title="手续费金额" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="2,592.00元" type="text">
												</div>
												<div class="formCol">
													<label class="formLbl"> 杂费 </label>
													<input title="杂费" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="0.00元" type="text">
												</div>
												<div class="formCol">
													<label class="formLbl"> 缴费编号 </label>
													<input title="缴费编号" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="" type="text">
												</div>
												<div class="formCol">
													<label class="formLbl"> 还款账号 </label>
													<input title="还款账号" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="" type="text">
												</div>
												<div class="formCol">
													<label class="formLbl"> 还款账号户名 </label>
													<input title="还款账号户名" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="" type="text">
												</div>
												<div class="formCol">
													<label class="formLbl"> 备注 </label>
													<input title="备注" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="" type="text">
												</div>
												<div class="formCol">
													<label class="formLbl"> 销售员姓名 </label>
													<input title="销售员姓名" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="刘正丰" type="text">
												</div>
												<div class="formCol">
													<label class="formLbl"> 销售员手机 </label>
													<input title="销售员手机" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="13450280390" type="text">
												</div>
											</div>
											<h4 class="RowTopline"> <span class="formtith4">登记信息</span></h4>
											<div class="">
												<div class="formCol">
													<label class="formLbl"> 登记机构名称 </label>
													<input title="登记机构名称" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="广州胜龙汽车贸易有限公司" type="text">
												</div>
												<div class="formCol">
													<label class="formLbl"> 登记人名称 </label>
													<input title="登记人名称" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="广州胜龙汽车贸易有限公司" type="text">
												</div>
												<div class="formCol">
													<label class="formLbl"> 登记日期 </label>
													<input title="登记日期 " name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="2015-08-26" type="text">
												</div>
												<div class="formCol">
													<label class="formLbl"> 更新日期 </label>
													<input title="更新日期" name="id" datatype="0" mustinput="0" maxlength="40" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="2015-08-26" type="text">
												</div>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div name="formtest-4" class="formDiv">
					<h3> 租赁物信息 </h3>
					<div class="content_body">
						<div class="content_table">
							<table id="tablist3" class="ls_list" title="tablecif7011" align="center" border="0" cellspacing="0" width="100%">
								<tbody id="tab">
									<tr class="t2 tit">
										<td align="center"> 租赁物编号 </td>
										<td align="center"> 租赁物类型 </td>
										<td align="center"> 租赁物名称 </td>
										<td align="center"> 单位（元） </td>
									</tr>
									<tr class="t1">
										<td align="center"><a href="CifPersReportAction_getTabViewForRetainBrNo.action?cifSts=&amp;cifType=1&amp;cifNo=10010981&amp;cifName=评级授信经销商1" class="abatch"> 10010981 </a></td>
										<td align="center"> 评级授信经销商1 </td>
										<td align="center"> 牵引车 </td>
										<td align="center"> 216,000.00 </td>
									</tr>
									<tr class="t2">
										<td align="center"><a href="CifPersReportAction_getTabViewForRetainBrNo.action?cifSts=&amp;cifType=2&amp;cifNo=10010979&amp;cifName=短租测试专用121" class="abatch"> 10010979 </a></td>
										<td align="center"> 短租测试专用121 </td>
										<td align="center"> 机动车 </td>
										<td align="center"> 326,000.00 </td>
									</tr>
									<tr class="t1">
										<td align="center"><a href="CifPersReportAction_getTabViewForRetainBrNo.action?cifSts=&amp;cifType=2&amp;cifNo=10010978&amp;cifName=石金峰1" class="abatch"> 10010978 </a></td>
										<td align="center"> 石金峰1 </td>
										<td align="center"> 牵引车 </td>
										<td align="center"> 126,000.00 </td>
									</tr>
								</tbody>
							</table>
							<div class="tool-container gradient tool-top tool-rounded" style="display: block; position: absolute; opacity:0">
								<div class="tool-items"> </div>
							</div>
							<div style="display: none;" class="content_form">
								<form id="cifBaseForms" class="forms" theme="simple" action="" method="post">
									<div class="content_Btn">
										<input value="提交" onclick="submitBtn(this.form)" type="button">
										<input value="取消关闭" onclick="colseBtn()" type="button">
									</div>
									<table id="formcif9004" class="from_w" title="formcif9004" align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
										<tbody>
											<tr>
												<td colspan="4" class="bartitle" align="left"> <span class="bartitlepp">客户基础信息查看<i></i></span> </td>
											</tr>
											<tr>
												<td class="tdlable" align="right">&nbsp;客户编号 </td>
												<td class="tdvalue" align="left">
													<input title="客户编号" name="cifNewNo" datatype="0" mustinput="0" maxlength="30" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE disable" type="text"> [系统自动生成] </td>
												<td class="tdlable" align="right">&nbsp;所属部门 </td>
												<td class="tdvalue" align="left"><span class="select_bg"> <span class="select_hid">
								                <select name="cifDepart" title="所属部门" mustinput="0" maxlength="200" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE">
								                </select>
								                </span> </span>
												</td>
											</tr>
											<tr class="evenRow">
												<td class="tdlable" align="right">&nbsp;客户姓名 </td>
												<td class="tdvalue" align="left">
													<input class="INPUT_TEXT" title="客户姓名" name="cifName" datatype="0" mustinput="1" maxlength="200" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" type="text">
													<span class="necessarynec"> &nbsp;&nbsp;&nbsp; </span></td>
												<td class="tdlable" align="right">&nbsp;客户类型 </td>
												<td class="tdvalue" align="left">
													<select name="cifType" title="客户类型" mustinput="0" maxlength="1" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
														<option value="2"> 个人 </option>
														<option value="1"> 对公 </option>
													</select>
												</td>
											</tr>
											<tr>
												<td class="tdlable" align="right">&nbsp;证件类型 </td>
												<td class="tdvalue" align="left">
													<select name="idType" title="证件类型" mustinput="0" maxlength="2" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
														<option value="" selected=""> </option>
														<option value="A"> 组织机构代码 </option>
														<option value="0"> 身份证 </option>
													</select>
												</td>
												<td class="tdlable" align="right">&nbsp;证件证号 </td>
												<td class="tdvalue" align="left">
													<input class="INPUT_TEXT" title="证件证号" name="idNo" datatype="0" mustinput="0" maxlength="30" onblur="PersIdNoCheck(idType,idNo);func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" type="text">
												</td>
											</tr>
											<tr class="evenRow">
												<td class="tdlable" align="right">&nbsp;联系人 </td>
												<td class="tdvalue" align="left">
													<input class="INPUT_TEXT" title="联系人" name="cifLxr" datatype="0" mustinput="0" maxlength="80" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" type="text">
												</td>
												<td class="tdlable" align="right">&nbsp;联系电话 </td>
												<td class="tdvalue" align="left">
													<input class="INPUT_TEXT" title="联系电话" name="cifMobile" datatype="0" mustinput="1" maxlength="50" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" type="text">
													<span class="necessarynec"> &nbsp;&nbsp;&nbsp; </span></td>
											</tr>
										</tbody>
									</table>
								</form>
							</div>
						</div>
					</div>
				</div>
				<div name="formtest-5" class="formDiv">
					<h3> 担保信息 </h3>
					<div class="content_body">
						<div class="content_table">
							<h4> <span class="formtith4">担保人信息</span></h4>
							<table id="tablist4" class="ls_list" title="tablecif7011" align="center" border="0" cellspacing="0" width="100%">
								<tbody id="tab">
									<tr class="t2 tit">
										<td align="center"> 保证人ID </td>
										<td align="center"> 保证人客户号 </td>
										<td align="center"> 保证人名称 </td>
										<td align="center"> 保证方式 </td>
										<td align="center"> 保证人状态 </td>
									</tr>
									<tr class="t1">
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</tbody>
							</table>
							<h4 class="RowTopline"> <span class="formtith4">抵押物信息</span></h4>
							<table id="tablist4" class="ls_list" title="tablecif7011" align="center" border="0" cellspacing="0" width="100%">
								<tbody id="tab">
									<tr class="t2 tit">
										<td align="center"> 抵押物编号 </td>
										<td align="center"> 抵押物名称 </td>
										<td align="center"> 抵押物类型 </td>
										<td align="center"> 抵押价值 </td>
										<td align="center"> 币种 </td>
										<td align="center"> 登记人 </td>
										<td align="center"> 抵押物状态 </td>
									</tr>
									<tr class="t1">
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</tbody>
							</table>
							<h4 class="RowTopline"> <span class="formtith4">质押物信息</span></h4>
							<table id="tablist4" class="ls_list" title="tablecif7011" align="center" border="0" cellspacing="0" width="100%">
								<tbody id="tab">
									<tr class="t2 tit">
										<td align="center"> 质押物编号 </td>
										<td align="center"> 质押物名称 </td>
										<td align="center"> 质押物类型 </td>
										<td align="center"> 质押价值 </td>
										<td align="center"> 币种 </td>
										<td align="center"> 登记人 </td>
										<td align="center"> 质押物状态 </td>
									</tr>
									<tr class="t1">
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="formRow">
					<input value="提交" type="submit">
				</div>
			</div>
			<div class="timeLine" style="">
				<div class="time_contents">
					<div class="time-line-bg">
						<div style="height: 37px;" class="time-line-line"> </div>
						<div class="time-line-body">
							<dl class="time-line-dl">
							</dl>
						</div>
						<!--<ul class="time-second">
							<li>
								<a>个人客户详细信息</a>
							</li>
							<li class="time-second-on">
								<span class="time-second-line-dot"> </span><a>工作单位信息</a>
							</li>
							<li>
								<a>配偶信息</a>
							</li>
							<li>
								<a>经济来源</a>
							</li>
							<li>
								<a>登记信息</a>
							</li>
						</ul>-->
					</div>
				</div>
			</div>
			<script type="text/javascript" src="formline.js">
			</script>
			<BODY>

</HTML>