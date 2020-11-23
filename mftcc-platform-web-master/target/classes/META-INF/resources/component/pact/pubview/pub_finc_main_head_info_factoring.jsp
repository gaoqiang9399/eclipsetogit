<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
	var appId= '${param.appId}';
	var pactId= '${param.pactId}';
	var fincMainId= '${param.fincMainId}';
	$(function(){
        pubFincMainHeadInfo.init();
	});

</script>
<!--头部主要信息 cus-bo-head-info -->
<div class="row clearfix head-info">
	<!--头像 -->
	<div class="col-xs-3 column text-center head-img ">
		<div>
			<button type="button" class="btn btn-font-pact font-pact-div padding_left_15 ">
				<i class="i i-pact font-icon"></i>
				<div class="font-text-left">融资信息</div>
			</button>
		</div>
		<div class="btn btn-link" onclick="getKindInfo();"></div>
	</div>
	<!--概要信息 -->
	<div class="col-xs-9 column head-content">
		<div class="clearfix">
			<div class="multi-bus pull-right">
				客户共有<span class="moreCnt_apply">申请中业务<a class="moreCnt more-apply-count pointer" onclick="getMultiBusList('apply');"></a> 笔,</span>
				<span class="moreCnt_pact">在履行合同<a class="moreCnt more-pact-count pointer" onclick="getMultiBusList('pact');"></a> 笔</span>
				<span class="moreCnt_finc">, 在履行融资<a class="moreCnt more-finc-count pointer" onclick="getMultiBusList('finc');"></a> 笔 </span>
				<span class="moreCnt_assure">, 为他人担保<a class="moreCnt more-assure-count pointer" onclick="getMultiBusList('assure');"></a> 笔 </span>
			</div>
		</div>
		<div class="margin_bottom_5 clearfix">
			<div >
				<div class="bus-more head-title pull-left"></div>
			</div>
		</div>
		<!--信息查看入口 -->
		<div>

			<table>
				<tr>
					<td>
						<p class="cont-title">融资金额</p>
						<p><span id="fincAmt" class="content-span"></span><span class="unit-span margin_right_25">万</span> </p>
					</td>
					<td>
						<p class="cont-title">融资余额</p>
						<p><span id="fincBal" class="content-span"></span><span class="unit-span margin_right_25">万</span></p>
					</td>
					<td>
						<p class="cont-title">逾期天数</p>
						<p>
							<span id="overDays" class="content-span"></span><span class="unit-span">天</span>
						</p>
					</td>
				</tr>
			</table>
		</div>
		<div class="btn-special">
			
		</div>
	</div>
</div>
	
