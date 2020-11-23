<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/component/include/webPath.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<%String contextPath = request.getContextPath(); 
String parm = request.getParameter("parm");
 String layout = "layout/view";
 %>
 <style type="text/css">
 .reportTu{
 	/* display: none; */
 }
 </style>
 </head>
 <script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
 <script type="text/javascript" src="${webPath}/component/report/js/highcharts.js"></script>
 <script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
 <script type="text/javascript" src="${webPath}/component/report/js/circlePie.js"></script>
  <script type="text/javascript" src="${webPath}/component/report/js/highcharts-more.js"></script>
 <script>
 var parm = '<%=parm%>';
$(function () {
	
    var colors = ["#90ED7D","#9292CC","#7CB5EC","#686BF0","#FF975C","#5FC8DB","#81B960","#DE5311"];
    
    if(parm=="report-five-class-map"){
    $.ajax({
			url : webPath+"/mfVoutypeAnalysis/getPieDataSource",
			data : "",
			type : 'post',
			dataType : 'json',
			async : false,
			success : function(data) {
				if(data!=null){
					if(data.datasourceList == null){
						$("#tu1_none").show();
					}else{
						 circlePie.doubleCirclePie("tu1_amt",colors,data.datasourceList[0],data.datasourceList[1],
						 200,"元","金额","金额",data.datasourceList[2],"按金额占比分布图");
						
						 circlePie.doubleCirclePie("tu1_cnt",colors,data.datasourceList[0],data.datasourceList[3],
						 200,"笔","笔数","笔数",data.datasourceList[4],"按笔数占比分布图");
					}
					 
				}
			},
			error : function() {
			
				
			}
		});
		
    }else if(parm == "term-Class-study"){
    
    	$.ajax({
			url : webPath+"/mfVoutypeAnalysis/getTermPieDataSource",
			data : "",
			type : 'post',
			dataType : 'json',
			async : false,
			success : function(data) {
				if(data!=null){
					if(data.datasourceList == null){
						$("#tu7_none").show();
					}else{
						 circlePie.doubleCirclePie("tu7_amt",colors,data.datasourceList[0],data.datasourceList[1],
						 200,"元","金额","金额",data.datasourceList[2],"按金额占比分布图");
						
						 circlePie.doubleCirclePie("tu7_cnt",colors,data.datasourceList[0],data.datasourceList[3],
						 200,"笔","笔数","笔数",data.datasourceList[4],"按笔数占比分布图");
					 }
				}
			},
			error : function() {
			
				
			}
		});
    }else if(parm == "report-money-account-tab"){
		
		
		 
		
		/**********************贷款金额统计表************************/
		 chart = new Highcharts.Chart({
            chart: {
                renderTo: 'tu3',
				borderWidth:3,
				borderColor:'#E5E5E5',
				borderRadius:0
				
            },
			credits:{
				enabled:false
			},
            title: {
                text: '贷款金额统计表',
                style:{color:'#3d4750',fontSize:'14px',fontWeight:'bold'}
            },
            legend:{
            	itemStyle:{color: '#696969',
							fontWeight: 'bold',
							fontSize: '12px',
							fontFamily: '微软雅黑'
            		}
            },
            xAxis: {
				gridLineWidth: 1,
				lineColor: '#000',
				tickColor: '#000',
                categories: ['一月', '二月', '三月', '四月', '五月', '六月',
                    '七月', '八月', '九月', '十月', '十一月', '十二月'],
				// x轴坐标文本
				labels: {
					style: {
						color: '#3d4750',
						fontWeight: 'bold',
						fontSize: '12px',
						fontFamily: '微软雅黑'
					}
				}
				
			},
			yAxis: {
				minorTickInterval: 'auto',
				lineColor: '#000',
				lineWidth: 0.5,
				gridLineWidth: 1,
				lineWidth: 1,
				title: {
					style: {
						color: '#696969',
						fontWeight: 'bold',
						fontSize: '12px',
						fontFamily: '微软雅黑'
					},
			 		text: '金额(万元)'
				}
       		},
	        tooltip: {
		        formatter: function() {
					var s;
					if (this.point.name) { // the pie chart
						s = ''+this.point.name +': '+ this.y +' fruits';
					} else {
						s = ''+this.x  +': '+ this.y;
					}
						return s;
                }
            },
            series: [{
     	       type: 'column',
               name: '余额',
               data: [3, 2, 1, 3, 4,7,8,9,11,5,3,6]
            	}, 
				{
                type: 'spline',
                name: '新增',
                data: [3, 2.67, 3, 6.33, 3.33,3.01,5,4.2,6,2.2,4.1,3.3],
                marker: {
                	lineWidth: 2,
                	lineColor: Highcharts.getOptions().colors[3],
                	fillColor: 'white'
                }
            }]
        });
		
    }else if(parm == "report-month-back-account-tab"){

		/**********************月放款/回收统计表************************/
		
		 chart = new Highcharts.Chart({
            chart: {
                renderTo: 'tu4',
                type: 'spline',
				borderWidth:3,
				borderColor:'#E5E5E5',
				borderRadius:0
            },
			credits:{
				enabled:false
			},
            title: {
                text: '月放款/回收统计表',
                style:{color:'#3d4750',fontSize:'14px',fontWeight:'bold'}
            },
            legend:{
            	itemStyle:{color: '#696969',
							fontWeight: 'bold',
							fontSize: '12px',
							fontFamily: '微软雅黑'
            		}
            },
            xAxis: {
               	categories: ['一月', '二月', '三月', '四月', '五月', '六月',
                    '七月', '八月', '九月', '十月', '十一月', '十二月'],
				gridLineWidth: 1,
				lineColor: '#000',
				tickColor: '#000',
				labels: {
					style: {
						color: '#3d4750',
						fontWeight: 'bold',
						fontSize: '12px',
						fontFamily: '微软雅黑'
					}
				}
	        },
             yAxis: {
				// 坐标最小值
				min:0,
				// 小网格线
				minorTickInterval: 'auto',
				lineColor: '#000',
				lineWidth: 0.5,
				minorTickInterval: 'auto',
	 			title: {
	 				style: {
	 					color: '#696969',
						fontWeight: 'bold',
						fontSize: '12px',
						fontFamily: '微软雅黑'
					},
			 		text: '金额(万元)'
				}
            },
             tooltip: {
            	crosshairs: true,
            	shared: true
        	},
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: true
                    },
                    enableMouseTracking: false
                }
            },
			
            series: [{
                name: '月放款',
                data: [300.00, 1000.00, 1500.00, 890.00, 2000.00, 1100.00, 1240, 1030.00, 670.00, 2000.00, 1800.00, 1455.00]
            }, {
                name: '月回收',
                data: [200.00, 1500.03, 800.02, 100.00, 1000.00, 600.00, 1000.00, 1300.00, 1000.00, 700.00, 900.00, 1000.00]
            }]
        });

    }else if(parm == "report-cash-back"){
		
		/***************************资金回收预测********************************/
		 chart = new Highcharts.Chart({

        chart: {
            type: 'column',
			renderTo: 'tu2',
            borderWidth:3,
			borderColor:'#E5E5E5',
			borderRadius:0
        },
		credits:{
				enabled:false
				
			},
        title: {
            text: '资金回收预测',
            style:{color:'#3d4750',fontSize:'14px',fontWeight:'bold'}
        },


		
		xAxis: {
			 categories: ['今日', '第五天', '第十天', '第十五天', '第二十天','第二十五天','第三十天'],
		gridLineWidth: 1,
		lineColor: '#000',
		tickColor: '#000',
		labels: {
			style: {
				color: '#3d4750',
				fontWeight: 'bold',
				fontSize: '12px',
				fontFamily: '微软雅黑'
			}
		},
		title: {
			style: {
				color: '#696969',
				fontWeight: 'bold',
				fontSize: '12px',
				fontFamily: '微软雅黑'
			}
		}
	},
	
	legend:{
    	itemStyle:{color: '#696969',
					fontWeight: 'bold',
					fontSize: '12px',
					fontFamily: '微软雅黑'
    		}
    },
		

       yAxis: {
		minorTickInterval: 'auto',
		lineColor: '#000',
		lineWidth: 0.5,
	
		labels: {
			style: {
				color: '#000',
				font: '11px Trebuchet MS, Verdana, sans-serif'
			}
		},
		title: {
			style: {
				color: '#696969',
				fontWeight: 'bold',
				fontSize: '12px',
				fontFamily: '微软雅黑'
			},
			text:'金额(万元)'
		}
	},

        tooltip: {
            formatter: function() {
                return '<b>'+ this.x +'</b><br/>'+
                    this.series.name +': '+ this.y +'<br/>'+
                    'Total: '+ this.point.stackTotal;
            }
        },

        plotOptions: {
            column: {
                stacking: 'normal'
            }
        },

        series: [{
            name: '累计回款',
            data: [5, 3, 4, 7, 2],
            stack: 'male',
			color:'#8BBC22'
        }, {
            name: '新增回款',
            data: [3, 4, 4, 2, 5],
            stack: 'male'
        }]
    });
		/***************************分组柱状图*****************************************/
    
    }else if(parm == "5"){
		
		
		/*************************************客户类型分布图*************************************/


	  chart = new Highcharts.Chart({
        chart: {
           	type: 'bubble',
			plotBorderWidth: 0,
           	renderTo: 'tu5',
            borderWidth:3,
			borderColor:'#E5E5E5',
			borderRadius:0,
			zoomType: 'xy',
			width:400
			
        },
		credits:{
			enabled:false
		},

        title: {
            text: '客户类型分布图',
            	style:{color:'#3d4750',fontSize:'14px',fontWeight:'bold'}
        },

        xAxis: {
            
			min:0,
			max:40,
		
		lineWidth:0,
		labels: {
			enabled:false,
			style: {
				color: '#000',
				font: '11px Trebuchet MS, Verdana, sans-serif'
			}
		}
				
        },

        yAxis: {
            startOnTick: false,
            endOnTick: false,
			 gridLineWidth: 0,
			min:10,
			max:50,
			labels:{
				enabled:false
			},
			title: {
				style: {
					color: '#696969',
					fontWeight: 'bold',
					fontSize: '12px',
					fontFamily: '微软雅黑'
				},
				text:'人(个)'
			}
				
        },
        
        legend:{
        	itemStyle:{color: '#696969',
						fontWeight: 'bold',
						fontSize: '12px',
						fontFamily: '微软雅黑'
        		}
        },
        
		      tooltip: {
           /**
		    formatter: function() {
				
                return '<b>'+ this.x +'</b><br/>'+
                    this.series.name +': '+ this.y +'<br/>'+
                    'Total: '+ this.point.stackTotal;
            }
			**/
			formatter: function() {
				var s = this.series.name+"有"+this.point.z;
				
				return s+"人";
			}
        },
			
  		plotOptions:{
            //气泡大小
			bubble:{
                maxSize:'30%',
                minSize:'20%'
            }
        },
        series: [{
			name:'待办公客户',
            data: [
                [20, 30, 123]
            ],
            marker: {
				
                 fillColor: {
                     radialGradient: { cx: 0.4, cy: 0.3, r: 0.7 },
                     stops: [
                         [0, 'rgba(255,255,255,0.5)'],
                         [1, 'rgba(69,114,167,0.5)']
                     ]
                 }
            }
        },
		
		{
			name:'历史公客户',
            data: [
                [20, 35, 183]
               
            ],
            marker: {
                 fillColor: {
                     radialGradient: { cx: 0.4, cy: 0.3, r: 0.7 },
                     stops: [
                         [0, 'rgba(255,255,255,0.5)'],
                         [1, 'rgba(169,114,167,0.5)']
                     ]
                 }
            }
        },
		 {
			name:'新增客户',
            data: [
                [15, 33, 163]
             
            ],
            marker: {
				
                 fillColor: {
                     radialGradient: { cx: 0.4, cy: 0.3, r: 1.7 },
                     stops: [
                         [0, 'rgba(255,255,255,0.5)'],
                         [1, 'rgba(170,70,67,0.5)']
                     ]
                 }
            }
        }
		,
		 {
			name:'最近发展客户',
            data: [
                [16, 30, 133]
             
            ],
            marker: {
				
                 fillColor: {
                     radialGradient: { cx: 0.4, cy: 0.3, r: 1.7 },
                     stops: [
                         [0, 'rgba(255,255,255,0.5)'],
                         [1, 'rgba(170,70,67,0.5)']
                     ]
                 }
            }
        }
		
		]

    });
/*************************************气泡图************************************/
    }else if(parm == "report-loan-use-account-tab"){
/*************************************贷款用途分布图************************************/
 chart = new Highcharts.Chart({
            chart: {
                renderTo: 'tu6',
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
				borderWidth:3,
				borderColor:'#E5E5E5',
				borderRadius:0
				
            },
			credits:{
				enabled:false
			},
			
            title: {
                text: '贷款用途分布图',
                style:{color:'#3d4750',fontSize:'14px',fontWeight:'bold'}
                
            },
            legend:{
            	itemStyle:{color: '#696969',
							fontWeight: 'bold',
							fontSize: '12px',
							fontFamily: '微软雅黑'
            		}
            },
            tooltip: {
        	    pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>',
            
				valueDecimals:2
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                       enabled: true,
                       style:{
          					color:'#696969',
          					fontWeight: 'bold',
          					fontSize: '12px',
          					fontFamily: '微软雅黑'
          				}
                    },
					
                    showInLegend: true
                }
            },
            series: [{
                type: 'pie',
                name: '占贷款用途的比例',
                data: [
                    
					{'name':'农、林、牧、渔业', y:30.0,'color':'#6766FF'},
                    {'name':'采矿业', y:20.,'color':'#FF9999'},
                    {
                        name: '制造业',
                        y: 15,
                        sliced: true,
                        selected: true,
						'color':'#00CCCB'
                    },
					{'name':'电力、暖气、燃气及水的生产和供应业', y:10,'color':'#CD6667'},
					{'name':'建筑业', y:10},
					{'name':'交通运输、仓储和邮政业', y:5},
					{'name':'交通运输、仓储和邮政业', y:10},
					{'name':'批发和零售业', y:10},
					{'name':'住宿和餐饮业', y:5},
					{'name':'房地产业', y:5}
					
                ]
            }]
        });
    }
    $("#tu"+parm).show();
   });
</script>
<body>
<div class="container" style="margin-top: 50px;">
	<div class="reportTu" id="tu1"><!--五级分类分布图 -->
		<div id="tu1_none" align="center" style="display:none;">暂无数据</div>
		<div id="tu1_amt" style="float:left;width:48%;"></div>
		<div id="tu1_cnt" style="float:left;width:48%;margin-left:20px;"></div>
	</div>
	<div class="reportTu" id="tu2"><!--资金回收预测 -->
	</div>
	<div class="reportTu" id="tu3"><!--贷款金额统计表-->
	</div>
	<div class="reportTu" id="tu4"><!--月放款/回收统计表 -->
	</div>
	<div class="reportTu" id="tu5"><!--客户类型分布图 -->
	</div>
	<div class="reportTu" id="tu6"><!--贷款用途分布图 -->
	</div>
	<div class="reportTu" id="tu7"><!--期限分类布图 -->
		<div id="tu7_none" align="center" style="display:none;">暂无数据</div>
		<div id="tu7_amt" style="float:left;width:48%;"></div>
		<div id="tu7_cnt" style="float:left;width:48%;margin-left:20px;"></div>
	
	</div>
</div>
<script type="text/javascript">

						
  
</script>
</body>
</html>