<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var appId= '${param.appId}';
	var pactId= '${param.pactId}';
	$(function(){
		pubPactHeadInfo.init();
	});

</script>
<!--头部主要信息 cus-bo-head-info -->
<div class="row clearfix head-info">
	<!--头像 -->
	<div class="col-xs-3 column text-center head-img ">
		<div>
			<button type="button" class="btn btn-font-pact font-pact-div padding_left_15 ">
				<i class="i i-pact font-icon"></i>
				<div class="font-text-left">合同信息</div>
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
				<span class="moreCnt_finc">, 在履行借据<a class="moreCnt more-finc-count pointer" onclick="getMultiBusList('finc');"></a> 笔 </span>
			</div>
		</div>
		<div class="margin_bottom_5 clearfix">
			<div >
				<div class="bus-more head-title pull-left"></div>
			</div>
		</div>
		<!--信息查看入口 -->
		<div>
			<button class="btn btn-lightgray btn-view" id="surveyReport" type="button">
				<i class="i i-eval1"></i>尽调报告
			</button>
			<table>
				<tr>
					<td>
						<p class="cont-title">合同金额</p>
						<p><span id="pactAmt" class="content-span"></span><span class="unit-span margin_right_25">万</span> </p>
					</td>
					<td>
					</td>
					<td>
						<p class="cont-title">合同期限</p>
						<p><span id="term" class="content-span"></span><span class="unit-span margin_right_25"></span></p>
					</td>
				</tr>
			</table>
		</div>
		<div class="btn-special">
			
		</div>
	</div>
</div>
	
