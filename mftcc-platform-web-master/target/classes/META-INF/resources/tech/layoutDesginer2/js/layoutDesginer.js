var subPluginOption = "subPluginOption";
$(document).ready(function(){
	initConfigCharts();
	/*
     *大类下拉菜单 事件
     */
	$(".fltype-group a").bind("click",function(){
		var $sub = $(this).parent().parent().parent().find("button");
		$sub.find("span").eq(0).html(this.innerHTML);
		$sub.val(this.name);
		selectGluginToShowParameter($(this).parent());
	});
});
/**
 * 
 * 根据控件类型展示控件参数
 * @param {Object} li 
 */
function selectGluginToShowParameter($li){
	var plntype = $li.find("a").eq(0).attr("name");
	var $td = $li.parent().parent().parent();
	$("."+subPluginOption).remove();
	switch (plntype){
		case "charts":
			$td.next().html("图形小类");
			$td.next().next().html(createBtnGroup());
			break;
		case "form":
			$td.next().html("");
			$td.next().next().html("");
			var $tbody = $td.parent().parent();
			createFormGroup($tbody)
			break;
		default:
			break;
	}
}
/**
 * 动态生成表单配置
 */
function createFormGroup($tbody){
	var $tr = $('<tr class='+subPluginOption+'></tr>');
	var $td1 = $('<td>表单编号</td><td class="filter_col_input"><input class="form-control" type="text" name="formId"></td>');
	var $td2 = $('<td>表单类型</td><td class="filter_col_input"></td>');
	var $tr2 = $('<tr class='+subPluginOption+'></tr>');
	var $td21 = $('<td>表单URL</td><td class="filter_col_input"><input class="form-control" type="text" name="formUrl"></td>');
	$tr.append($td1);
	var $div = $('<div class="btn-group charts-group"></div>');
	var $btn = $('<button id="charts-group" name="formType" class="btn btn-default dropdown-toggle" data-toggle="dropdown"><span>选择类型</span><span class="caret"></span></button>')
	var $ul = $('<ul class="dropdown-menu plugin-item"></ul>');
	$ul.append('<li><a name="propertyTag">详情表单</a></li>');
	$ul.append('<li><a name="recordTag">记录表单</a></li>');
	$ul.append('<li><a name="tableTag">列表</a></li>');
	$div.append($btn);
	$div.append($ul);
	$td2.eq(1).append($div);
	$ul.find("a").bind('click', function() {
		var $sub = $(this).parent().parent().parent().find("button");
		$sub.find("span").eq(0).html(this.innerHTML);
		$sub.val(this.name);
	});
	$tr.append($td2);
	$tr2.append($td21);
	$tbody.append($tr);
	$tbody.append($tr2);
	$(".plugin-query").hide();
	return $tr;
}
/**
 * 
 * 根据图形小类展示图形参数
 * @param {Object} li 
 */
function selectChartToShowParameter($a){
	var chartType = $a.attr("name");
	var $plquery = $(".plugin-query");
	var options = {};
	options = configData[chartType].option;
	changeQuery($plquery,chartType,options);
	$plquery.show();
}
/**
 * 动态生成小类下拉菜单
 */
function createBtnGroup(){
	var $div = $('<div class="btn-group charts-group"></div>');
	var $btn = $('<button id="charts-group" name="subplugintype" class="btn btn-default dropdown-toggle" data-toggle="dropdown"><span>选择图形</span><span class="caret"></span></button>')
	var $ul = $('<ul class="dropdown-menu plugin-item"></ul>');
	for(var key in configData){
		var ct = configData[key];
		$ul.append('<li><a name="'+ct.type+'">'+ct.name+'</a></li>');
	}
	$div.append($btn);
	$div.append($ul);
	$ul.find("a").bind('click', function() {
		var $sub = $(this).parent().parent().parent().find("button");
		$sub.find("span").eq(0).html(this.innerHTML);
		$sub.val(this.name);
		selectChartToShowParameter($(this));
	});
	return $div;
}
/**
 * 初始化图形配置数据
 */
function initConfigCharts(){
	$.getJSON(configPath, function(data){
		configData = data;
	}); 
}
/**
 * 动态生成参数菜单
 * @param {Object} $plquery
 * @param {Object} chartType
 */
function changeQuery($plquery,chartType,options){
	var $ul = $plquery.find(".dtm-tab0").eq(0);
	$ul.html("");
	for(var key in options){
		var showval = optmenuData[key];
		if(showval!=null&&typeof(showval)!="undefined"){
			$ul.append("<li class='dtm-tab0-hi' name='"+key+"'>"+showval+"</li>");
		}
	}
	switch (chartType){
		case "line":
			setlineValue(options);
			break;
		case "bar":
			setlineValue(options);
			break;
		case "pie":
			setlineValue(options);
			break;
		case "radar":
			setlineValue(options);
			break;
		case "gauge":
//			setlineValue(options);
			break;
		default:
			break;
	}
	$ul.find("li").eq(0).addClass("dtm-tab0-hi-curr");
	$ul.find("li").bind("click",function(){
		$(".dtm-tab0-coni div").hide();
		$(this).parent().find("li").removeClass("dtm-tab0-hi-curr");
		$(this).addClass("dtm-tab0-hi-curr");
		var type = $(this).attr("name");
		$(".dtm-tab0-coni div[name="+type+"]").show();
	});
}
function prodChart($cell,chart){
	var info = $cell.find('.info')[0];
	var myChart;
	if(chart.chartObj==null||typeof(chart.chartObj)=="undefined"){
		myChart = echarts.init(info);
	}else{
		myChart = chart.chartObj;
	}
	myChart.clear();
	myChart.setOption(chart.option);
	return myChart;
}
function prodChartOption($cell){
	var chartType = $("#charts-group").val();
	if(chartType=="") return;
	var chartName = $("#charts-group").find("span").eq(0).html();
	var chart = new Object();
	var options = new Object();
	options =  jQuery.extend(true,{}, configData[chartType].option);
	$(".dtm-tab0").find("li").each(function(i,tem){
		var optType = $(tem).attr("name");
		var item = $(".cpt-tab[name='"+optType+"']")[0];
		switch (optType){
			case "title":
				addOptTitle(item,options,optType);
				break;
			case "tooltip":
				addOptToolTip(item,options,optType);
				break;
			case "legend":
				addOptLegend(item,options,optType);
				break;
			case "xAxis":
				addOptAxis(item,options,optType,"xAxis-type");
				break;
			case "yAxis":
				addOptAxis(item,options,optType,"yAxis-type");
				break;
			case "toolbox":
				addOptToolBox(item,options,optType);
				break;
			case "series":
				if(chartType=="line"||chartType=="bar"){
					addOptLineBarSeries(item,options,optType,chartType);
				}else if(chartType=="pie"){
					addOptPieSeries(item,options,optType,chartType)
				}else if(chartType=="radar"){
					addOptRadarSeries(item,options,optType,chartType)
				}else if(chartType=="gauge"){
					addOptGaugeSeries(item,options,optType,chartType)
				}
				break;
			default:
				break;
		}
	});
	chart["name"] = chartName;
	chart["type"] = chartType;
	chart["option"] = options;
	return chart;
}
function addOptTitle(item,options,optType){
	options[optType]["text"] = $(item).find("input[name='text']").eq(0).val();
	options[optType]["subtext"] = $(item).find("input[name='subtext']").eq(0).val();
}
function addOptToolTip(item,options,optType){
	options[optType]["show"] = toBoolean($(item).find("input[name='tooltip-show']:checked").val());
//	options[optType]["formatter"] = $(item).find("input[name='formatter']").eq(0).val();
}
function addOptLegend(item,options,optType){
	options[optType]["show"] = toBoolean($(item).find("input[name='legend-show']:checked").val());
	options[optType]["selectedMode"] = toBoolean($(item).find("input[name='selectedMode']:checked").val());
}
function addOptToolBox(item,options,optType){
	options[optType]["show"] = toBoolean($(item).find("input[name='toolbox-show']:checked").val());
	for(var key in options[optType]["feature"]){
		options[optType]["feature"][key]["show"] = false;
	}
	$(item).find("input[name='feature']:checked").each(function(){
		var val = $(this).val();
		if(val==""){
			return;
		}
		options[optType]["feature"][val]["show"] = true;
	});
}
function addOptAxis(item,options,optType,name){
	options[optType][0]["type"] = $(item).find("input[name='"+name+"']:checked").val();
	options[optType][0]["name"] = $(item).find("input[name='name']").eq(0).val();
}
function addOptLineBarSeries(item,options,optType,chartType){
	var $input = $(item).find("input[name='markLine']:checked");
	var markLineType = $input.val();
	var markLineName = $input.next().html();
	var markLineData = [];
	if(markLineType!="false"){
		markLineData.push({
			type: markLineType,
			name: markLineName
		});
	}
	var url = $(item).find("input[name='url']").eq(0).val();
	/*$.ajax({
		url: url,
		type: "post",
		async:false,
		dataType: "json",
		success: function(data) {
			alert(data)
		}
	});*/
	var seriesData = [{legname:'最高温度',series:[{name:'周一',value:'11'},{name:'周二',value:'-2'},{name:'周三',value:'4'},{name:'周四',value:'14'},{name:'周五',value:'5'}]},{legname:'最低温度',series:[{name:'周一',value:'7'},{name:'周二',value:'-9'},{name:'周三',value:'0'},{name:'周四',value:'2'},{name:'周五',value:'1'}]}];
	
	for(var i in seriesData){
		var legname = seriesData[i].legname;//系列名称
		var series = seriesData[i].series;
		var data = new Array();
		var axisData = new Array();
		for(var n in series){
			data.push(series[n].value);
			axisData.push(series[n].name);
		}
		if(options.xAxis[0].type=="category"){
			options.xAxis[0].data = axisData;
//			options.xAxis[0].boundaryGap = false;
		}else{
			options.yAxis[0].data = axisData;
//			options.yAxis[0].boundaryGap = false;
		}
		options.legend.data.push(legname);
		options.series.push({
			name: legname, 
			type: chartType, 
			data: data,
			markLine:{
				data: markLineData
			}
		});
	}
}
function addOptPieSeries(item,options,optType,chartType){
	var seriesData = {legname:'访问来源',series:[{name:'直接访问',value:'335'},{name:'邮件营销',value:'310'},{name:'联盟广告',value:'234'},{name:'视频广告',value:'135'},{name:'搜索引擎',value:'1548'}]};
	for(var i in seriesData.series){
		options.legend.data.push(seriesData.series[i].name);
	}
	options.series.push({
		name: seriesData.legname, 
		type: chartType, 
		radius : '55%',
    center: ['50%', '60%'],
		data: seriesData.series
	});
}
function addOptRadarSeries(item,options,optType,chartType){
	var seriesData = [{legname:'预算分配',series:[{name:'销售',value:'4300'},{name:'管理',value:'10000'},{name:'信息技术',value:'28000'},{name:'客服',value:'35000'},{name:'研发',value:'50000'},{name:'市场',value:'19000'}]},{legname:'实际开销',series:[{name:'销售',value:'5000'},{name:'管理',value:'14000'},{name:'信息技术',value:'28000'},{name:'客服',value:'31000'},{name:'研发',value:'42000'},{name:'市场',value:'21000'}]}];
	var obj = new Object();
	var arr = new Array();
	var data = new Array();
	for(var i in seriesData){
		var legname = seriesData[i].legname;
		options.legend.data.push(legname);
		var series = seriesData[i].series;
		var valArr = new Array();
		for(var n in series){
			var name = series[n].name;
			var value = parseInt(series[n].value);
			valArr.push(value);
			if(obj[name]==null||typeof(obj[name])=="undefined"||obj[name]<value){
				obj[name] = value
			}
		}
		data.push({
			value:valArr,
			name:legname
		});
	}
	options.series.push({
		name: "", 
		type: chartType, 
		data: data
	});
	for(var key in obj){
		arr.push({
			text:key,
			max:parseInt(obj[key])
		});
	}
	options.polar.push({
		indicator:arr
	});
}
function addOptGaugeSeries(item,options,optType,chartType){
	var seriesData = {legname:"完成率",tipname:"业务指标",value:50};
	options.series.push({
		name: seriesData.tipname, 
		type: chartType, 
		detail : {formatter:'{value}%'},
		data: [{
			value:seriesData.value,
			name:seriesData.legname
		}]
	});
}
/**
 * 折线初始化默认值
 */
function setlineValue(options){
	var chartType = $("#charts-group").val();
	if(chartType=="") return;
	setTitleVal(options.title);
	setToolTipVal(options.tooltip);
	setLegendVal(options.legend);
	setToolboxVal(options.toolbox);
	setxAxisVal(options.xAxis);
	setyAxisVal(options.yAxis);
}

function setTitleVal(title){
	var $item = $(".dtm-tab0-coni").find("[name='title']").eq(0);
	$item.find("input[name='text']").eq(0).val(title.text);
	$item.find("input[name='subtext']").eq(0).val(title.subtext);
}
function setToolTipVal(tooltip){
	var $item = $(".dtm-tab0-coni").find("[name='tooltip']").eq(0);
	$item.find("input[name='tooltip-show'][value='"+tooltip.show+"']").attr("checked",true);
//	$item.find("input[name='formatter']").eq(0).val(tooltip.formatter);
}
function setLegendVal(legend){
	var $item = $(".dtm-tab0-coni").find("[name='legend']").eq(0);
	$item.find("input[name='legend-show'][value='"+legend.show+"']").attr("checked",true);
	$item.find("input[name='selectedMode'][value='"+legend.selectedMode+"']").attr("checked",true);
}
function setToolboxVal(toolbox){
	var $item = $(".dtm-tab0-coni").find("[name='toolbox']").eq(0);
	$item.find("input[name='toolbox-show'][value='"+toolbox.show+"']").attr("checked",true);
	for(var key in toolbox.feature){
		if(toolbox.feature[key].show){
			$item.find("input[name='feature'][value='"+key+"']").attr("checked",true);
		}
	}
}
function setxAxisVal(xAxis){
	if(xAxis==null||typeof(xAxis)=="undefined") return;
	var $item = $(".dtm-tab0-coni").find("[name='xAxis']").eq(0);
	$item.find("input[name='xAxis-type'][value='"+xAxis[0].type+"']").attr("checked",true);
	$item.find("input[name='name']").eq(0).val(xAxis[0].name);
}
function setyAxisVal(yAxis){
	if(yAxis==null||typeof(yAxis)=="undefined") return;
	var $item = $(".dtm-tab0-coni").find("[name='yAxis']").eq(0);
	$item.find("input[name='yAxis-type'][value='"+yAxis[0].type+"']").attr("checked",true);
	$item.find("input[name='name']").eq(0).val(yAxis[0].name);
}
var configData = {
  line: {
    name: '折线图',
    type: 'line',
    option: {
      title: {
        text: '主标题文本',
        subtext: '副标题文本'
      },
      tooltip: {
      	show:true,
      	formatter:"{a} <br>{b} : {c}",
        trigger: 'axis'
      },
      legend: {
      	show:true,
      	selectedMode:false,
      	data:[]
      },
      toolbox: {
        show: true,
        feature: {
          dataView: {show: true,readOnly: true},
          restore: {show: true},
          saveAsImage: {show: true}
        }
      },
      calculable: true,
      xAxis: [
        {
        	type:'category',
        	name:''
        }
      ],
      yAxis: [
        {
        	type:'value',
        	name:''
        }
      ],
      series: [
      ]
    }
  },
  bar: {
    name: '柱状图',
    type: 'bar',
     option: {
      title: {
        text: '主标题文本',
        subtext: '副标题文本'
      },
      tooltip: {
      	show:true,
      	formatter:"{a} <br>{b} : {c}",
        trigger: 'axis'
      },
      legend: {
      	show:true,
      	selectedMode:false,
      	data:[]
      },
      toolbox: {
        show: true,
        feature: {
          dataView: {show: true,readOnly: true},
          restore: {show: true},
          saveAsImage: {show: true}
        }
      },
      calculable: true,
      xAxis: [
        {
        	type:'category',
        	name:''
        }
      ],
      yAxis: [
        {
        	type:'value',
        	name:''
        }
      ],
      series: [
      ]
    }
  },
  pie: {
    name: '饼状图',
    type: 'pie',
    option:{
    	title:{
    		x:'center',
    		text: '某站点用户访问来源',
        subtext: '纯属虚构'
    	},
    	tooltip : {
    		show: true,
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    	},
    	legend: {
    		show: true,
        orient : 'vertical',
        x : 'left',
        data:[]
    	},
    	toolbox: {
        show: true,
        feature: {
          dataView: {show: true,readOnly: true},
          restore: {show: true},
          saveAsImage: {show: true}
        }
      },
      calculable : true,
      series: []
    }
  },
  radar:{
  	name: '雷达图',
    type: 'radar',
    option:{
    	title : {
        text: '预算 vs 开销',
        subtext: '纯属虚构'
    	},
    	tooltip : {
    		show: true,
    		islandFormatter: "{a} <br>{b} : {c}",
        trigger: 'axis'
    	},
    	legend: {
    		show: true,
        orient : 'vertical',
        x : 'right',
        y : 'bottom',
        data:[]
    	},
    	toolbox: {
        show: true,
        feature: {
          dataView: {show: true,readOnly: true},
          restore: {show: true},
          saveAsImage: {show: true}
        }
      },
      calculable : true,
      polar:[],
      series:[]
    }
  },
  gauge:{
  	name:'仪表盘',
  	type:'gauge',
  	option:{
  		tooltip : {
        formatter: "{a} <br/>{b} : {c}%"
    	},
    	toolbox: {
        show: true,
        feature: {
          dataView: {show: true,readOnly: true},
          restore: {show: true},
          saveAsImage: {show: true}
        }
      },
      series:[]
  	}
  }
};
function toBoolean(str){
	if(str=="true"){
		return true;
	}else if(str=="false"){
		return false;
	}else{
		return str;
	}
}
