var SpecialProp = {
	"DIV":{
		"filterKey":"class",
		"filterVal":"weui_panel_hd",
		"filterAttr":["text"]
	},
	"A":{
		"filterKey":"class",
		"filterVal":"weui_btn",
		"filterAttr":["text"]
	},
	"LABEL":{
		"filterKey":"class",
		"filterVal":"weui_label",
		"filterAttr":["text"]
	},
	"P":{
		"filterKey":"class",
		"filterVal":"weui_grid_label",
		"filterAttr":["text"]
	}
}
var filterAttr = {
	"class":["outside_border"]
}
var filterElem = {
		"I":true
}
/*java JSON关键字，设置别名*/
var JsonKeyWord = {
	"class":"elem-class"
}
var JsonKeyWordSwap = (function(json){
	var jsonSwap = {};
	$.each(json, function(k,v) {
		jsonSwap[v]=k;
	});
	return jsonSwap;
})(JsonKeyWord);
/*html转json*/
function html2json(targetElem){
	/*var htmlJson = [];
	var tarJson = {};*/
	var $parent = $(targetElem);
	/*tarJson.tagName = getTagName($parent);
	tarJson.children = getChildren($parent);
	tarJson.attributes = getAttributes($parent);
	tarJson.data = getHtmlData($parent);
	tarJson.specialProp = getSpecialProp($parent);
	htmlJson.push(tarJson);*/
	/*console.log(JSON.stringify(htmlJson));
	console.log(htmlJson);*/
	return getChildren($parent);
}
/*取标签名*/
function getTagName(elem){
	var tagName =elem.get(0).tagName;
	return tagName;
}
/*取子元素*/
function getChildren(elem){
	var hj = [];
	if (elem.children().length>0) {
		$.each(elem.children(), function(i,node) {
			var tj = {};
			tj.tagName = getTagName($(node));
			if(!filterElem[tj.tagName]){
				tj.children = getChildren($(node));
				tj.attributes = getAttributes($(node));
				tj.data = getHtmlData($(node));
				tj.specialProp = getSpecialProp($(node));
				hj.push(tj);
			}
		});
	}
	return hj;
}
/*取元素属性*/
function getAttributes(elem){
	var obj = elem.get(0).attributes;
	var json = {};
	$.each(obj, function(i,node) {
		var val = node.nodeValue
		if(filterAttr[node.nodeName]&&filterAttr[node.nodeName].length>0){
			$.each(filterAttr[node.nodeName],function(i,filter){
				if(val.indexOf(filter)>-1){
					val=(val.split(filter)[0]+val.split(filter)[1]);
				}
			});
		}
		if (JsonKeyWord[node.nodeName]) {
			json[JsonKeyWord[node.nodeName]]=val;
		} else{
			json[node.nodeName]=val;
		}
		
	});
	return json;
}
/*取data值*/
function getHtmlData(elem){
	var temp = elem.clone()
	temp.removeData("uiSortable");
	temp.removeData("sortableItem");
	temp.removeData("uiSortableItem");
	temp.removeData("uidraggable");
	return temp.data();
}
/*特殊标签特殊属性*/
function getSpecialProp(elem){
	var json = {};
	var obj = SpecialProp[getTagName(elem)];
	if (obj&&elem.attr(obj.filterKey).indexOf(obj.filterVal)>-1) {
		$.each(obj.filterAttr, function(i,val) {
			switch (val){
				case "text":
					json["text"] = elem.text();
					break;
				default:
					break;
			}
		});
	}
	return json;
}


/*json转html*/
function json2html(arr,elem){
	if (arr&&arr.length>0) {
		$.each(arr, function(i,node) {
			var temp = document.createElement(node.tagName); 
			$.each(node.attributes,function(k,v){
				if (JsonKeyWordSwap[k]) {
					temp.setAttribute(JsonKeyWordSwap[k], v); 
				} else{
					temp.setAttribute(k, v); 
				}
			});
			$.each(node.data,function(k,v){
				$(temp).data(k, v); 
			});
			if (node.specialProp&&SpecialProp[node.tagName]) {
				var obj = SpecialProp[node.tagName];
				if (JsonKeyWord[obj.filterKey]) {
					if(node.attributes[JsonKeyWord[obj.filterKey]].indexOf(SpecialProp[node.tagName].filterVal)>-1){
						$.each(SpecialProp[node.tagName].filterAttr, function(i,val) {
							switch (val){
								case "text":
									$(temp).text(node.specialProp["text"]);
									break;
								default:
									break;
							}
						});
					}
				} else{
					if(node.attributes[SpecialProp[node.tagName].filterKey].indexOf(SpecialProp[node.tagName].filterVal)>-1){
						$.each(SpecialProp[node.tagName].filterAttr, function(i,val) {
							switch (val){
								case "text":
									$(temp).text(node.specialProp["text"]);
									break;
								default:
									break;
							}
						});
					}
				}
			}
			json2html(node.children,$(temp));
			$(elem).append(temp);
		});
	}
}