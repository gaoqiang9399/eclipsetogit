//从科目放大镜赋值给表单属性
function getCowItemInfoArtDialog(cowItemInfo){
	$("input[name=accName]").val(cowItemInfo.accName);
	$("input[name=accNo]").val(cowItemInfo.accNo);
	$("input[name='accName']").attr("readonly",true); 
};

function selectCwComItemDialog(callback){
	dialog({
		id:"cwComItemDialog",
		title:'选择所属科目',
		url: webPath+'/cwComItem/getListAllPage',
		width:550,
		height:400,
		backdropOpacity:0,
		onshow:function(){
			this.returnValue = null;
		},onclose:function(){
			if(this.returnValue){
				//返回对象的属性:groupName,groupNo
				if(typeof(callback)== "function"){
					callback(this.returnValue);
				}else{
					$("input[name=accName]").val(this.returnValue.accName);
				}
			}
		}
	}).showModal();
};