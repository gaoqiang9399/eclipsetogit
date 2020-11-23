<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<style type="text/css">
    .container h2{
        font-weight:bold;
        text-align: center;
        color:#32b5cb;
    }
	.container h3{
		color:#32b5cb;
		cursor: pointer;
		font-weight:bold;
	}
	.container h4{
	    margin-left:20px;
	    font-weight:bold;
	}
	.container table{
	    width:90%;
	}
	.container table thead tr th{
	    text-align:left;
	}
	.container p {
	    margin-left:40px;
	}
.table-bordered {
    border: 1px solid #333;
}
	
.table-bordered>thead>tr>th, .table-bordered>tbody>tr>th, .table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td, .table-bordered>tbody>tr>td, .table-bordered>tfoot>tr>td {
    border: 1px solid #333;
}
.table>thead>tr>th, .table>tbody>tr>th, .table>tfoot>tr>th, .table>thead>tr>td, .table>tbody>tr>td, .table>tfoot>tr>td{
    padding: 8px;
    vertical-align: top;
    border-top:none;
    font-size:13px;
}

.tdlabel{
	width:13%;
	font-weight:bold;
	text-align:left;
}
.tdvalue{
	width:34%;
}
.button-colse{
    background: #fff;
    color: #000;
    border: 1px solid #d2d3d6;
}
</style>
<script type="text/javascript">
          $(function(){
				  $("#inforcontainer").mCustomScrollbar({
					   advanced:{
						   theme:"minimal-dark",
						   updateOnContentResize:true
					  }
				   });
		    	});
		    
		    function returnList(){
		        //top.window.showDialog("MfToolsRealnameAction_input.action","信息核查","90","95");
		       // $(top.window.document).find("#bigFormShow").find("#bigFormShowiframe").remove();
				//$(top.window.document).find("#bigFormShow").remove();
				myclose();
		    }
		   
</script>
</head>
<body>
	<div class="container"  style="width:100%;">
		<div class="row clearfix" style="padding-right: 100px; padding-left: 80px">
		    <div class="col-md-10 column">
		    <h2>企业信息报告</h2>
		    </div>
		    <div class="col-md-2 column">
		    <button type="button" class="btn btn-primary">打印</button>
		    <button type="button" class="btn btn-primary button-colse" onclick="returnList()">关闭</button>
		    </div>
			<div class="col-md-12 column">
				<h3>一.基本信息</h3>
				<h4>1.1工商基本信息</h4>
				<table class="table" style="margin-left:40px" >
				  <tbody>
				    <tr>
				        <td class="tdlabel">企业名称：</td>
						<td class="tdvalue">北京微金时代科技有限公司</td>
				    </tr>
				    <tr>
				        <td class="tdlabel">英文名称：</td>
						<td class="tdvalue">-</td>
				    </tr>
				    <tr>
				        <td class="tdlabel">注册：</td>
						<td>110108018386527</td>
						<td class="tdlabel">社会统一信誉代码：</td>
						<td>91110108327185940L</td>
				    </tr>
				    <tr>
				        <td class="tdlabel">法定代表人：</td>
						<td class="tdvalue">庞博</td>
						<td class="tdlabel">组织机构代码：</td>
						<td class="tdvalue">32718594-0</td>
				    </tr>
				    <tr>
				        <td class="tdlabel">注册资本：</td>
						<td class="tdvalue">1000 万元 人民币</td>
						<td class="tdlabel">成立日期：</td>
						<td class="tdvalue">2014年12月26日</td>
				    </tr>
				    <tr>
				        <td class="tdlabel">所属行业：</td>
						<td class="tdvalue">科技推广和应用服务业</td>
						<td class="tdlabel">经营状态：</td>
						<td class="tdvalue">开业</td>
				    </tr>
				    <tr>
				        <td class="tdlabel">企业类型：</td>
						<td class="tdvalue">有限责任公司(自然人投资或控股)</td>
				    </tr>
				     <tr>
				        <td class="tdlabel">注册地址：</td>
						<td class="tdvalue">北京市海淀区中关村南大街1号友谊宾馆64033</td>
				    </tr>
				    <tr>
				        <td class="tdlabel">营业期限：</td>
						<td class="tdvalue">自 2014年12月26日 至 2034年12月25日</td>
				    </tr>
				    <tr>
				        <td class="tdlabel">经营范围：</td>
						<td colspan="3">技术开发、技术推广、技术转让、技术咨询、技术服务；销售自行开发的产品；计算机系统服务；数据处理开发；软件咨询；销售机械设备、汽车配件、五金、交电、计算机、软件及辅助设备、家用电器、日用品、服装、工艺品、文化用品、体育用品；货物进出口、技术进出口。(依法须经批准的项目，经相关部门批准后依批准的内容开展经营活动。)</td>
				    </tr>
				    <tr>
				        <td class="tdlabel">登记机关:</td>
						<td class="tdvalue">海淀分局</td>
						<td class="tdlabel">发照日期:</td>
						<td class="tdvalue">2016年01月12日</td>
				    </tr>
				  </tbody>
				</table>
				<h4>1.2股东信息</h4>
				<table class="table  table-bordered">
					<thead>
						<tr>
							<th>编号</th>
							<th>股东</th>
							<th>股东类型</th>
							<th>出资金额</th>
							<th>出资日期</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>庞博</td>
							<td>自然人股东</td>
							<td>800万元</td>
							<td>-</td>
						</tr>
						<tr>
							<td>2</td>
							<td>董国勇</td>
							<td>自然人股东</td>
							<td>200万元</td>
							<td>-</td>
						</tr>
					</tbody>
				</table>
				<h4>1.3固定资产</h4>
				<table class="table  table-bordered">
					<thead>
						<tr>
							<th>编号</th>
							<th>资产名称</th>
							<th>购置时间</th>
							<th>存储地址</th>
							<th>状态</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>金水区洪都花园楼盘</td>
							<td>01/04/2012</td>
							<td>河南省郑州市金水区洪都花园</td>
							<td>员工宿舍</td>
						</tr>
						<tr>
							<td>2</td>
							<td>奔驰汽车</td>
							<td>01/04/2015</td>
							<td>-</td>
							<td>在用</td>
						</tr>
					</tbody>
				</table>
				<h3>二.法律诉讼信息</h3>
				<h4>2.1被执行人信息</h4>
				<p>
					<strong>暂无信息，不排除存在时间相对滞后，或未公布的情况，仅供参考。</strong>
				</p>
				<h4>2.2失信被执行人信息</h4>
				<p>
					<strong>暂无信息，不排除存在时间相对滞后，或未公布的情况，仅供参考。</strong>
				</p>
				<h4>2.3裁判文书信息</h4>
				<p>
					<strong>暂无信息，不排除存在时间相对滞后，或未公布的情况，仅供参考。</strong>
				</p>
				<h4>2.4法院公告</h4>
				<p>
					<strong>暂无信息，不排除存在时间相对滞后，或未公布的情况，仅供参考。</strong>
				</p>
				<h3>三.企业风险</h3>
				<h4>3.1经营异常</h4>
				<p>
					<strong>暂无信息，不排除存在时间相对滞后，或未公布的情况，仅供参考。</strong>
				</p>
				<h4>3.2动产质押登记信息</h4>
				<p>
					<strong>暂无信息，不排除存在时间相对滞后，或未公布的情况，仅供参考。</strong>
				</p>
				<h4>3.3股权出质信息</h4>
				<p>
					<strong>暂无信息，不排除存在时间相对滞后，或未公布的情况，仅供参考。</strong>
				</p>
				<h4>3.4行政处罚</h4>
				<p>
					<strong>暂无信息，不排除存在时间相对滞后，或未公布的情况，仅供参考。</strong>
				</p>
				<h4>3.5抽查检查</h4>
				<p>
					<strong>暂无信息，不排除存在时间相对滞后，或未公布的情况，仅供参考。</strong>
				</p>
				<h4>3.6欠税信息</h4>
				<p>
					<strong>暂无信息，不排除存在时间相对滞后，或未公布的情况，仅供参考。</strong>
				</p>
				<h4>3.7清算信息</h4>
				<p>
					<strong>暂无信息，不排除存在时间相对滞后，或未公布的情况，仅供参考。</strong>
				</p>
				
				<h3>四.年报信息</h3>
				<table class="table  table-bordered">
					<thead>
						<tr>
							<th colspan="3">企业资产状况信息</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td></td>
							<td>2015年度</td>
							<td>2014年度</td>
						</tr>
						<tr>
							<th>资产总额</th>
							<td>企业选择不公示</td>
							<td>企业选择不公示</td>	
						</tr>
						<tr>
							<th>营业总收入中营业务收入</th>
							<td>企业选择不公示</td>
							<td>企业选择不公示</td>	
						</tr>
						<tr>
							<th>所有者权益合计</th>
							<td>企业选择不公示</td>
							<td>企业选择不公示</td>
						</tr>
						<tr>
							<th>净利润</th>
							<td>企业选择不公示</td>
							<td>企业选择不公示</td>	
						</tr>
						<tr>
							<th>营业总收入</th>
							<td>企业选择不公示</td>
							<td>企业选择不公示</td>	
						</tr>
						<tr>
							<th>纳税总额</th>
							<td>企业选择不公示</td>
							<td>企业选择不公示</td>	
						</tr>
						<tr>
							<th>利润总额</th>
							<td>企业选择不公示</td>
							<td>企业选择不公示</td>
						</tr>
						<tr>
							<th>负债总额</th>
							<td>企业选择不公示</td>
							<td>企业选择不公示</td>
						</tr>
					</tbody>
				</table>
				<h3>五.知识产权</h3>
				<h4>5.1商标信息</h4>
				<table class="table  table-bordered">
					<thead>
						<tr>
							<th>序号</th>
							<th>注册号</th>
							<th>名称</th>
							<th>商标类型</th>
							<th>状态</th>
							<th>使用期限</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>17593425</td>
							<td>微金时代</td>
							<td>35-广告销售</td>
							<td>商标注册申请受理通知书发文</td>
							<td>-</td>
						</tr>
						<tr>
							<td>2</td>
							<td>17593425</td>
							<td>微金时代</td>
							<td>35-设计研究</td>
							<td>商标注册申请受理通知书发文</td>
							<td>-</td>
						</tr>
					</tbody>
				</table>
				<h4>5.2专利信息</h4>
				<p>
					<strong>暂无信息，不排除存在时间相对滞后，或未公布的情况，仅供参考。</strong>
				</p>
				<h4>5.3证书资质</h4>
				<p>
					<strong>暂无信息，不排除存在时间相对滞后，或未公布的情况，仅供参考。</strong>
				</p>
				<h4>5.4软件著作权信息</h4>
				<table class="table  table-bordered">
					<thead>
						<tr>
							<th>序号</th>
							<th>软件全称</th>
							<th>登记号</th>
							<th>登记批准日期</th>
							<th>版本号</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>微金移动应用平台</td>
							<td>2016SR200583</td>
							<td>2016年8月1日</td>
							<td>v2.0</td>
						</tr>
						<tr>
							<td>2</td>
							<td>微金私募投资业务平台</td>
							<td>2016SR136760</td>
							<td>2015年7月17日</td>
							<td>v2.0</td>
						</tr>
						<tr>
							<td>3</td>
							<td>网络众筹综合业务平台</td>
							<td>2016SR136652</td>
							<td>2015年7月17日</td>
							<td>v2.0</td>
						</tr>
						<tr>
							<td>4</td>
							<td>微金典当综合业务平台</td>
							<td>2016SR136840</td>
							<td>2015年7月17日</td>
							<td>v2.0</td>
						</tr>
						<tr>
							<td>5</td>
							<td>微金融资租赁综合业务平台</td>
							<td>2016SR136848</td>
							<td>2015年7月17日</td>
							<td>v2.0</td>
						</tr>
						<tr>
							<td>6</td>
							<td>互联网金融综合业务平台</td>
							<td>2016SR136690</td>
							<td>2015年7月17日</td>
							<td>v2.0</td>
						</tr>
						<tr>
							<td>7</td>
							<td>微金小额贷款综合业务平台</td>
							<td>2016SR136757</td>
							<td>2015年7月17日</td>
							<td>v2.0</td>
						</tr>
						<tr>
							<td>8</td>
							<td>微金商业保理综合业务平台</td>
							<td>2016SR136766</td>
							<td>2015年7月17日</td>
							<td>v2.0</td>
						</tr>
						<tr>
							<td>9</td>
							<td>微金融资担保综合业务系统</td>
							<td>2016SR136652</td>
							<td>2015年7月17日</td>
							<td>v2.0</td>
						</tr>
					</tbody>
				</table>
				<h4>5.5作品著作权信息</h4>
				<p>
					<strong>暂无信息，不排除存在时间相对滞后，或未公布的情况，仅供参考。</strong>
				</p>
				<h4>5.6网站域名</h4>
				<p>
					<strong>暂无信息，不排除存在时间相对滞后，或未公布的情况，仅供参考。</strong>
				</p>
				<h3>六.经营信息</h3>
				<h4>6.1联络信息</h4>
				<table class="table table-bordered">
					<thead>
					</thead>
					<tbody>
						<tr>
							<th>电话</th>
							<td>010-53360310</td>
						</tr>
						<tr>
							<th>邮箱</th>
							<td>1415570487@qq.com</td>
						</tr>
						<tr>
							<th>网址</th>
							<td>http://www.dingxinsoft.com</td>
						</tr>
						<tr>
							<th>地址</th>
							<td>北京市海淀区中关村南大街1号友谊宾馆64033</td>
						</tr>
					</tbody>
				</table>
				<h4>6.2融资信息</h4>
				<p>
					<strong>暂无信息，不排除存在时间相对滞后，或未公布的情况，仅供参考。</strong>
				</p>
				<h4>6.3招聘信息</h4>
				<table class="table  table-bordered">
					<thead>
							<tr>
								<th>序号</th>
								<th>职位名称</th>
								<th>工作地点</th>
								<th>月薪</th>
								<th>发布日期</th>
							</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>金融软件销售经理</td>
							<td>北京</td>
							<td>面议</td>
							<td>2016年11月20日</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-md-12 column">
			<div style="text-align:center">
			<button type="button" class="btn btn-primary">打印</button>
		    <button type="button" class="btn btn-primary button-colse" onclick="returnList()">关闭</button>
		    </div>
			</div>
		</div>
	</div>
</body>
</html>