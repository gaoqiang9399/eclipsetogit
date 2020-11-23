;
var highchartsloudou = function(window, $) {
	/**
	 * 在此处开始定义内部处理函数。
	 */
	var _init = function () {
//		//颜色设置可添加颜色
//		Highcharts.setOptions({
//      	     colors: ['#ED561B']
//   			});
		$('#container').highcharts({
	        chart: {
	            type: 'bar'
	        },
	        title: {
	            text: '新用户转化漏斗分析'
	        },
	        subtitle: {
	            text: '网信-现金贷'
	        },
	        xAxis: {
	        	categories: ['注册用户数', '实名认证数', '准入用户数', '自有反欺诈规则验证', '第三方反欺诈规则验证', '银联智策规则验证', '评分卡用户数', '已定额用户数', '申请贷款', '贷款用户数']
	        },
	        yAxis: {
	            title: {
	                text: ''//控制单位例如12摄氏度
	            },
	            labels: {
	                overflow: 'justify'
	            }
	        },
	        tooltip: {
	            valueSuffix: ''
	        },
	        plotOptions: {
	            bar: {
	                dataLabels: {
	                    enabled: true,
	                    allowOverlap: true
	                }
	            }
	        },
	        legend: {
	        	enabled: true
	        },
	        credits: {
	            enabled: false
	        },
	        series: [{
	            name: '用户数',
	            data: [parseInt(day1), parseInt(day2), parseInt(day3), parseInt(day4), parseInt(day5),parseInt(day6),parseInt(day7),
	                   parseInt(day8),parseInt(day9),parseInt(day10)]
	        }
	        , {
	            name: '每环节转化率为',
	            data: [parseInt(day1mj), parseInt(day2mj), parseInt(day3mj), parseInt(day4mj), parseInt(day5mj), parseInt(day6mj),
	                   parseInt(day7mj), parseInt(day8mj), parseInt(day9mj), parseInt(day10mj)],
	            tooltip: {
	                pointFormat: '{series.name} {point.y:.2f} %'
	            }
	        }
	        , {
	        	name: '总体转化率为',
	        	data: [parseInt(day1zt), parseInt(day2zt), parseInt(day3zt), parseInt(day4zt), parseInt(day5zt),
	        	       parseInt(day6zt),parseInt(day7zt),parseInt(day8zt),parseInt(day9zt),parseInt(day10zt)],
	        	tooltip: {
	   	            pointFormat: '{series.name} {point.y:.2f} %'
	   	        }
	        }
	        ]
	    });
		
		
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init
	};
}(window, jQuery);

