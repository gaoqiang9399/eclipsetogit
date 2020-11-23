/** 环形饼图
*/

var circlePie = function(window,$){

	/**
	 * 双环形图
	 * rendToId：渲染id
	 * colors：颜色列表
	 * categories：内环的种类
	 * data：数据源
	 * pieWidth：饼图的宽度
	 * descSuffix：描述后缀
	 * outterdesc：外环描述
	 * innerDesc：内环描述
	 * totalNum：总计
	
	 */
	 var _doubleCirclePie = function(rendToId,colors,categories,data,
			 pieWidth,descSuffix,outterdesc,innerDesc,totalNum,title){
		 
		 var widthVal=$("#"+rendToId).width();
		 if(colors==null){
			 
			 colors = Highcharts.getOptions().colors;
		 }
		 if(title==null){
			 
			 title = "";
		 }
//		    data =[
//		           {
//		               "y": 202000,
//		               "color": "#90ED7D",
//		               "drilldown": {
//		                   "name": "3个月以内",
//		                   "categories": [
//		                       "风控部",
//		                       "业务部"
//		                   ],
//		                   "data": [
//		                       100000,
//		                       102000
//		                   ],
//		                   "colors": "#90ED7D"
//		               }
//		           },
//		           {
//		               "y": 6550000,
//		               "color": "#9292CC",
//		               "drilldown": {
//		                   "name": "3-6个月",
//		                   "categories": [
//		                       "总经办",
//		                       "风控部"
//		                   ],
//		                   "data": [
//		                       50000,
//		                       6500000
//		                   ],
//		                   "colors": "#9292CC"
//		               }
//		           }
//		       ];
		   var browserData = [];
		   var versionsData = [];
		   var i;
		   var j;
		   var  dataLen = data.length;
		   var  drillDataLen;
		   var  brightness;


		// Build the data arrays
		for (i = 0; i < dataLen; i += 1) {

		    // add browser data
		    browserData.push({
		        name: categories[i],
		        y: data[i].y,
		        color: data[i].color
		    });

		    // add version data
		    drillDataLen = data[i].drilldown.data.length;
		    for (j = 0; j < drillDataLen; j += 1) {
		        brightness = 0.2 - (j / drillDataLen) / 5;
		        versionsData.push({
		            name: data[i].drilldown.categories[j],
		            y: data[i].drilldown.data[j],
		            color: Highcharts.Color(data[i].color).brighten(brightness).get()
		        });
		    }
		}

		// Create the chart
		Highcharts.chart({
		    chart: {
		    	 	renderTo: rendToId,
		        	type: 'pie',
		         	plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false,
					borderWidth:3,
					borderColor:'#E5E5E5',
					borderRadius:0,
					width:widthVal
		    },
		    title: {
		        text: title
		    },
		   // subtitle: {
		     //   text: 'Source: <a href="http://netmarketshare.com/">netmarketshare.com</a>'
		   // },
		    yAxis: {
		        title: {
		            text: 'Total percent market share'
		        }
		    },
		    plotOptions: {
		        pie: {
		            shadow: false,
		            center: ['50%', '50%']
		        }
		    },
		    tooltip: {
		        valueSuffix: descSuffix
		    },
		    series: [{
		    	 type: 'pie',
		        name: innerDesc,
		        data: browserData,
		        size: '30%',
		   
		        dataLabels: {
		            formatter: function () {
		                return this.y > 5 ? this.point.name : null;
		            },
		            color: '#ffffff',
		            distance: -30
		        }
		    }, {
		        name: outterdesc,
		        data: versionsData,
		        size: '40%',
		        innerSize: '60%',
		        dataLabels: {
		            formatter: function () {
		                // display only if larger than 1
		            	
		            	//计算比例
		            	var tmpRate=CalcUtil.divide(this.y,totalNum);
		            	tmpRate=CalcUtil.multiply(tmpRate,100);
		            	tmpRate=CalcUtil.formatMoney(tmpRate,2);
		            	
		                return this.y > 1 ? '<b>' + this.point.name + ':</b> ' +
		                		tmpRate + '%' : null;
		            }
		        },
		        id: 'versions'
		    }],
		    responsive: {
		        rules: [{
		            condition: {
		                maxWidth: pieWidth
		            },
		            chartOptions: {
		                series: [{
		                    id: 'versions',
		                    dataLabels: {
		                        enabled: false
		                    }
		                }]
		            }
		        }]
		    }
		});
		}; 
	
	return{
		doubleCirclePie:_doubleCirclePie
	}
}(window,jQuery);
 
	



