/**
 * 科目列表数据
 */
/**
 * 新增一级科目
 */
function addAccountkemu(){
		var st=$('.mySelectedNode');
		var accType ="1";
		if(st.length>0){
			var id=st.attr('id');
			accType = id.substr(id.length-1,1);//截取最后一位字符串
		}
		top.addFlag = false;
		top.htmlStrFlag = false;
		top.createShowDialog(webPath+"/cwComItem/input?accType="+accType,"添加一级科目",'90','90',function(){
			if(top.addFlag){
				updateTableData();//重新加载列表数据
//				if(top.htmlStrFlag){
//					var tableHtml = $(top.htmlString).find("tbody").html();
//					$(".table_content").find("tbody").html(tableHtml);
//				
//				}
				
			}
		});
		
	};
	/*
		详情信息
	*/
	function thisGetById(url){
	//	alert(url);
		top.addFlag = false;
		top.htmlStrFlag = false;
		top.createShowDialog(webPath+"/"+url,"科目详情",'70','70',function(){
			if(top.addFlag){
				updateTableData();//重新加载列表数据
				/*if(top.htmlStrFlag){
					var tableHtml = $(top.htmlString).find("tbody").html();
					$(".table_content").find("tbody").html(tableHtml);
				}*/
			}
		});
	};
	
	/**
	 * 新增二级和二级以上科目
	 */
	function  addAccountById(url){
		//alert(url)
		top.addFlag = false;
		top.htmlStrFlag = false;
		top.createShowDialog(webPath+"/"+url,"新增科目",'70','70',function(){
			if(top.addFlag){
				updateTableData();//重新加载列表数据
			}
		});
	}
	/**
	 * 科目检查
	 * @param url
	 */
	function accountCheck(url){
		top.addFlag = false;
		top.htmlStrFlag = false;
		top.createShowDialog(webPath+"/"+url,"科目检查",'90','90',function(){
			if(top.addFlag){
				if(top.htmlStrFlag){
					var tableHtml = $(top.htmlString).find("tbody").html();
					$(".table_content").find("tbody").html(tableHtml);
				}
			}
		});
	}
	