$(function () {
	var ititSysGlobalSearchDataFlag = true;
   $("#nope1").focus(function(){
   	    if(ititSysGlobalSearchDataFlag){
   	    	ititSysGlobalSearchData();
   	    	ititSysGlobalSearchDataFlag = false;
   	    }
   });
	//默认为1 启用
	if(typeof(globalSearchOpenFlag) !="undefined" && globalSearchOpenFlag != '0'){
		//判断加载redis还是h5的
		var cacheOpt = 0;
		$.ajax({
			type:"get",
			url:webPath +"/sysGlobalSearch/checkCache",
			async:false,
			success:function(data){
				if(data.result=="redis"){
					cacheOpt = 1;
					initRedis();
					parmDics = data.parmDics;
					top.tableParmList = data.tableParmList;
					top.sysGlobalType = data.sysGlobalType;
				}else{
					initH5Storage();
					ititSysGlobalSearchData(webPath + "/sysGlobalSearch/globalSearch",{});
				}
			},error:function(){
				console.log("初始化出错");
			}
		});
	}
	
	

   
});
//加载h5
function initH5Storage(){
	   $('#nope1').autocompleter({
	        highlightMatches: false,
	        customLabel:"label",
	        template: '{{ label }}<span> |hex</span>',
	        sreachField: ["label"],
	        hint: false,
	        cache: false,
	        empty: false,
	        limit: 8,
	        callback: function (value, data, selected) {
	        	if(selected){
	        		window.location.href =webPath + selected.url;
	        	}/*else{
	        		sreached(data.searchData);
	        	}*/
	        }
	    });
}

//加载redis
function initRedis(){
	 $('#nope1').autocompleter({
		    source:webPath + '/sysGlobalSearch/globalSearchByRedis',
		    combine: function () {
	            var term = $('nope1').val();
	            return {
	                term: term
	            };
	        },
	        highlightMatches: false,
	        customLabel:"label",
	        template: '{{ label }}<span> |hex</span>',
	        sreachField: ["label"],
	        hint: false,
	        cache: false,
	        empty: false,
	        limit: 8,
	        callback: function (value, data, selected) {
	        	if(selected){
	        		window.location.href = webPath + selected.url;
	        	}/*else{
	        		sreached(data.searchData);
	        	}*/
	        }
	    });
}

//初始化h5数据
function ititSysGlobalSearchData(url,data){
	if(typeof (url) == "undefined" || url==""){
		return;
	}
	$.ajax({
		type:"post",
		url:url,
		data:data,
		async:false,
		success:function(data){
			if(data.flag=="success"){//****************************数据到这里 ******************************
				changeSysGlobalSearch(data.list);
				top.searchList = data.list;
				parmDics = data.parmDics;
				top.tableParmList = data.tableParmList;
				top.sysGlobalType = data.sysGlobalType;
			}else{
				console.log("加载出错");
			}
		},error:function(){
			console.log("初始化出错");
		}
	});
}

//改变h5的数据源
function changeSysGlobalSearch(data){
	searchDatas=data;
	$('#nope1').autocompleter('option',{
	  source:data
  	});
}
//wayclass是筛选项 ；inputSearch是输入的内容,为了在下个页面的输入框中带出来输入内容；firstKindNo是产品号,下个页面业务申请需要
function sreached(data,wayclass,inputSearch,firstKindNo){
	//ititSysGlobalSearchData();
	if(typeof(data)=="undefined"||data==null){
		return;
	}

	top.rzzl.setGlobalSearchData(data);
	window.location.href = webPath+"/layout/view/page/searchList.jsp?wayclass="+wayclass+"&inputSearch="+inputSearch+"&firstKindNo="+firstKindNo;
	top.searchDatas = null;
}