//审批角色
var approlesUrl ="../process/getApproles.do";
//检查条件
var checkFunUrl="../process/getCheckFuns.do";
//附加功能
var additionalfunctionUrl = "../process/getFuns.do";

//通用请求
function AjaxUrlForChoice(url,select){
	if(url==""||select==null) return;
	jQuery.ajax( {
		type : "POST",
		cache:false,
		async:false,
		url : url,
		success : function(data) {
			if(data=="") return;
			data = eval("("+data+")");
			for(var i =0;i<data.length;i++){
				var temp = $("<option></option>");
				temp.html(data[i].title);
				temp.attr("value",data[i].id);
				select.append(temp);
//				alert("select.html()::::"+select.html())
			}
		}
	});
}
//正则表达式获取地址栏参数
function GetQueryString(name){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}
