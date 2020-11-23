function addQueryDiv(id){
			var addName =  $("#filterName").val();
			var addHtml = '<div class="rotate-div" id="'+id+'">'
				+'<div class="rotate-obj rotate-borderColor2">'
				+'<span class="rotate-xiexian rotate-borderColor2"></span><span class="rotate-bitian1">已选</span>'
				+'<div class="rotate-des rotate-color2"><div class="rotate-tubiao pull-left rotate-tubiaoBac2"></div><div class="rotate-formName pull-left">'
				+addName+'</div></div><div class="rotate-opre">'
				+'<button class="rotate-add i i-x42" onclick="deleteRotate(this,'+"'2',"+"'"+addName+"'"+');"'
				+'"></button><button class="rotate-del i i-lajitong" title="删除" onclick="deleteQuery('+"'"+id+"'"+')"></div></div></div>';
			$(".addQuery").before(addHtml);
		}
function deleteQuery(id){
	$.ajax({
		type:"post",
		url:webPath+"/mfReportFilter/delete?id="+id,
		success:function(){
			$("#"+id).remove();
		}
	});
}
		//保存筛选开始
		var saveFilterBox = function(){
		var subArr=[];
		var treeObj = $.fn.zTree.getZTreeObj('filter_item_set');
		var nodes = treeObj.getNodes();
		if(typeof($("#filterNo").val())!="undefined"&&$("#filterNo").val()!=""&&$("#filterNo").val()!=null){
			$.each(nodes, function(i,obj) {
			if (obj.filterNo==$("#filterNo").val()) {
			}
			});
		}else{
		var newNode={name:$("#filterName").val()};
		if($("#filterName").val()==""){
			alert("请输入筛选名称",0);
			return false;
		}
		if($("#filterDesc").val()==""){
			alert("请输入筛选描述",0);
		}
	}
	var subTreeObj = $.fn.zTree.getZTreeObj('my_filter_group');
	var subNodes = subTreeObj.getNodes();
	$.each(subNodes, function(i,obj) {
		var subObj = {};
		subObj.treeId="my_filter";
		subObj.checked=true;
		subObj.andOr=obj.andOr;
		subObj.condition=obj.condition;
		subObj.type=obj.type;
//		subObj.type="1";
		subObj.value=obj.value;
		subObj.secondValue=obj.secondValue;
		subObj.noValue=obj.noValue;
		subObj.singleValue=obj.singleValue;
		subObj.betweenValue=obj.betweenValue;
		subObj.listValue=obj.listValue;
		subObj.likeValue=obj.likeValue;
		if(i<subNodes.length-1){
			subArr.push(subObj);
		}
		
	});
	//typeClass 是判断基础表的 
	var typeObj = eval(JSON.stringify(subArr));
	var typeClass = typeObj[0].value;
	var postData = {};
	postData.typeClass = typeClass;
	postData.filterNo = $("#filterNo").val();
	postData.filterName = $("#filterName").val();
	postData.filterContent  = JSON.stringify(subArr);
	postData.useFlag =  "1";
	postData.ajaxData = JSON.stringify(subArr);
	postData.filterDesc = $("#filterDesc").val();
	$.ajax({
		type : 'post',
		url :webPath+"/mfReportFilter/insert" ,
		data:postData,
		error: function(xmlhq,ts,err){console.log(xmlhq+"||"+ts+"||"+err);},
		success : function(data) {
			$(".my-filter-box").modal("hide");
			var thisId = data.thisId;
			addQueryDiv(thisId);
		}
	});
};//保存筛选条件结束 	

		filter_dic = [ {
			"optName":"基础查询表",
			"parm":[{
				"optName":"客户基本信息查询",
				"optCode":"1"
			},{
				"optName":"贷款综合查询",
				"optCode":"2"
			},{
				"optName":"放款信息查询",
				"optCode":"3"
			},{
				"optName":"押品基本信息查询",
				"optCode":"4"			
			}],
			"optCode":"baseQuery",
			"dicType":"y_n"
		},{
		"optName" : "登记人",
		"parm" : [{
			"optName":"管理员",
			"optCode":"0000"
		},{
			"optName":"业务员DL0005",
			"optCode":"DL0005"
		},{
			"optName":"DL0003",
			"optCode":"DL0003"
		}],
		"optCode" : "opNo",
		"dicType" : "y_n"
	}];
		
