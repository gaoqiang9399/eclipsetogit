<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/component/include/webPath.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<%String contextPath = request.getContextPath(); 
String parm = request.getParameter("parm");
 %>
 <style type="text/css">
 /* .reportTu{
 	background: url("images/yibiaopan1.png");
	width: 500px;
	height: 317px;
	margin-left: 25%;
 } */
 html,body,.container{
 	height: 100%;
 	overflow: hidden;
 }
 </style>
 </head>
 <script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
  <script type="text/javascript" src="${webPath}/tech/layoutDesginer/js/echarts-all.js"></script>
 <script>
 var parm = '<%=parm%>';
$(function () {
	  var myChart = echarts.init(document.getElementById('container'));
	var name1="笔数";
	var name2="客户总数";
	if(parm == 'report-loan-allamt-tab'){
		name1="余额";
		name2="贷款总额";
	}
	if(parm == "report-cus-count-tab"){
		option = {
			    tooltip : {
			        formatter: "{a} <br/>{c} {b}"
			    },
			    toolbox: {
			        show: true,
			        feature: {
			            restore: {show: true},
			            saveAsImage: {show: true}
			        }
			    },
			    series : [
			        {
			            name: name1,
			            type: 'gauge',
			            z: 3,
			            min: 0,
			            max: 1400,
			            splitNumber: 7,
			            radius: '70%',
			            axisLine: {            // 坐标轴线
			                lineStyle: {       // 属性lineStyle控制线条样式
			                    width: 10
			                }
			            },
			            axisTick: {            // 坐标轴小标记
			                length: 15,        // 属性length控制线长
			                lineStyle: {       // 属性lineStyle控制线条样式
			                    color: 'auto'
			                }
			            },
			            splitLine: {           // 分隔线
			                length: 20,         // 属性length控制线长
			                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
			                    color: 'auto'
			                }
			            },
			            title : {
			                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
			                    fontWeight: 'bolder',
			                    fontSize: 20,
			                    fontStyle: 'italic'
			                }
			            },
			            detail : {
			                show: false
		            	},
			            data:[{value: 1300, name: ''}]
			        },
			        {
			            name: name2,
			            type: 'gauge',
			            //center: ['20%', '55%'],    // 默认全局居中
			            radius: '35%',
			            min:0,
			            max:2200,
			            //endAngle:45,
			            splitNumber:11,
			            axisLine: {            // 坐标轴线
			                lineStyle: {       // 属性lineStyle控制线条样式
			                    width: 8
			                }
			            },
			            axisTick: {            // 坐标轴小标记
			                length:12,        // 属性length控制线长
			                lineStyle: {       // 属性lineStyle控制线条样式
			                    color: 'auto'
			                }
			            },
			            splitLine: {           // 分隔线
			                length:20,         // 属性length控制线长
			                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
			                    color: 'auto'
			                }
			            },
			            pointer: {
			                width:5
			            },
			            title: {
			                offsetCenter: [0, '-30%'],
			                fontSize: 14// x, y，单位px
			            },
			            detail:{
			                show: false
		            	},
			            data:[{value: 1000, name: '目标'}]
			        }
			       
			    ]
			};
	}else{
		option = {
			    tooltip : {
			        formatter: "{a} <br/>{c} {b}"
			    },
			    toolbox: {
			        show: true,
			        feature: {
			            restore: {show: true},
			            saveAsImage: {show: true}
			        }
			    },
			    series : [
			        {
			            name: name1,
			            type: 'gauge',
			            z: 3,
			            min: 0,
			            max: 700,
			            splitNumber: 7,
			            radius: '70%',
			            axisLine: {            // 坐标轴线
			                lineStyle: {       // 属性lineStyle控制线条样式
			                    width: 10
			                }
			            },
			            axisTick: {            // 坐标轴小标记
			                length: 15,        // 属性length控制线长
			                lineStyle: {       // 属性lineStyle控制线条样式
			                    color: 'auto'
			                }
			            },
			            splitLine: {           // 分隔线
			                length: 20,         // 属性length控制线长
			                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
			                    color: 'auto'
			                }
			            },
			            title : {
			                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
			                    fontWeight: 'bolder',
			                    fontSize: 20,
			                    fontStyle: 'italic'
			                }
			            },
			            detail : {
			                show: false
		            	},
			            data:[{value: 130, name: ''}]
			        },
			        {
			            name: name2,
			            type: 'gauge',
			            //center: ['20%', '55%'],    // 默认全局居中
			            radius: '35%',
			            min:0,
			            max:220,
			            //endAngle:45,
			            splitNumber:11,
			            axisLine: {            // 坐标轴线
			                lineStyle: {       // 属性lineStyle控制线条样式
			                    width: 8
			                }
			            },
			            axisTick: {            // 坐标轴小标记
			                length:12,        // 属性length控制线长
			                lineStyle: {       // 属性lineStyle控制线条样式
			                    color: 'auto'
			                }
			            },
			            splitLine: {           // 分隔线
			                length:20,         // 属性length控制线长
			                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
			                    color: 'auto'
			                }
			            },
			            pointer: {
			                width:5
			            },
			            title: {
			                offsetCenter: [0, '-30%'],
			                fontSize: 14// x, y，单位px
			            },
			            detail:{
			                show: false
		            	},
			            data:[{value: 100, name: '目标'}]
			        }
			       
			    ]
			};
	}
	
	  // 使用刚指定的配置项和数据显示图表。
   	 myChart.setOption(option);
		/* setInterval(function (){
		    option.series[0].data[0].value = (Math.random()*100).toFixed(2) - 0;
		    option.series[1].data[0].value = (Math.random()*7).toFixed(2) - 0;
		    option.series[2].data[0].value = (Math.random()*2).toFixed(2) - 0;
		    option.series[3].data[0].value = (Math.random()*2).toFixed(2) - 0;
		    myChart.setOption(option,true);
		},2000); */
});
</script>
<body>
<div class="container" id="container">
	
</div>
</body>
</html>