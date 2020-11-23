<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>进销存-图形报表</title>
<%
	String contextPath = request.getContextPath(); 
	String parm = request.getParameter("parm");
 %>
 <style type="text/css">
 html,body,.container{
 	height: 100%;
 	overflow: hidden;
 }
 </style>
 </head>
 <script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
 <script type="text/javascript" src="${webPath}/component/pss/js/echarts-3.7.2.js"></script>
 <script>
 	var parm = '<%=parm%>';
	$(function () {
		// 基于准备好的dom，初始化echarts实例
        //var myChart = echarts.init(document.getElementById('container'));
		
		if(parm == 'Pss-Sale-Total-Amount'){
			var dateData = ["暂无数据"];
	 		var amountData = ["暂无数据"];
	 			
	 			$.ajax({
				 	url : webPath+"/pssReport/reportQueryForGraph",
					data : {reportId:'Pss-Sale-Total-Amount'},
					type : 'post',
					dataType : 'json',
					async : false,
					success : function(data) {
						if(data.flag == 'success'){
							if(data.data2 != ''){
								dateData = data.data2;
								amountData = data.data1;
							}
						}
					},
					error : function() {
						
					}
				 });
	 			
	 			eChartContainer = document.getElementById('container');
	 			myChart = echarts.init(eChartContainer);

	 			option = {
	 			    title: {
	 			        text: '近7日销售对比分析',
	 			        x:'center',
	 			        y:'top',
	 			        textAlign:'left'
	 			    },
	 			    tooltip: {},
	 			    legend: {
	 			        //data:['销量'],
	 			        //height: eChartContainer.style.height,
	 			        //width: eChartContainer.style.width,
	 			        //left: 'center',
	 			        //padding: [10000, 0, 1000,0]
	 			    },
	 			    xAxis: {
	 			        data: dateData
	 			    },
	 			    yAxis: {},
	 			    series: [{
	 			        name: '销量',
	 			        type: 'bar',
	 			        data: amountData
	 			    }]
	 			};
	 			myChart.setOption(option);

		}else if(parm == 'Pss-Stock-Count-Distribute'){
			var data1 = ['暂无数据'];
	 		var data2 = [{value:0,name:'暂无数据'}];
	 			$.ajax({
				 	url : webPath+"/pssReport/reportQueryForGraph",
					data : {reportId:'Pss-Stock-Count-Distribute'},
					type : 'post',
					dataType : 'json',
					async : false,
					success : function(data) {
						if(data.flag == 'success'){
							 if(data.data1 != ''){
								 data1 = data.data1;
								 data2 = data.data2;
							 }
						}
					},
					error : function() {
						
					}
				 });
				 
	 			eChartContainer = document.getElementById('container');
	 			myChart = echarts.init(eChartContainer);
	 			
	 			option = {
 				    title : {
 				    	text: '库存数量分布',
 				        //subtext: '',
 				        x:'center'
 				    },
 				    tooltip : {
 				        trigger: 'item',
 				        formatter: "{a} <br/>{b} : {c} ({d}%)"
 				    },
 				    legend: {
 				        orient: 'vertical',
 				        left: 'left',
 				        data:  data1
 				    },
 				    series : [
 				        {
 				            name: '访问来源',
 				            type: 'pie',
 				            radius : '55%',
 				            center: ['50%', '60%'],
 				            data: data2,
 				            itemStyle: {
 				                emphasis: {
 				                    shadowBlur: 10,
 				                    shadowOffsetX: 0,
 				                    shadowColor: 'rgba(0, 0, 0, 0.5)'
 				                }
 				            }
 				        }
 				    ]
 				};
	 			myChart.setOption(option);

		}
		
        // 使用刚指定的配置项和数据显示图表。
       //myChart.setOption(option);
	});
	    
</script>
<body>
<div class="container" id="container">
	
</div>
<script type="text/javascript">

</script>
</body>
</html>