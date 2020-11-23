var Report = function(window,$){
	var _init = function(){
		$(".box-content-bg").each(function(){
			$(this).mCustomScrollbar({
				theme: "minimal-dark",
				advanced:{ 
					updateOnContentResize:true
				}
			});
		});	
		
		$('.box-content-sm').bind("click",function(event){
			switch($(this).attr("id")){
			case "cusFenbu" :
				window.location.href="../report/MfReport_cusDistribute.jsp";
				break;
			case "DaikuanFenbu" :
				window.location.href="../report/MfReport_loanDistribute.jsp";
				break;
			case "Huankuantongji" : 
				window.location.href="../report/MfReport_repayAll.jsp";
				break;
			case "Yuqitongji" :
				window.location.href="../report/MfReport_overdueAll.jsp";
				break;
			case "repayZhuTu" :
				window.location.href="../report/MfReport_repayZhuTu.jsp";
				break;
			case "cusDaoqi" : 
				window.location.href="../report/MfReport_cusDaoqi.jsp";
				break;
			case "Yue" : 
				window.location.href="../report/MfReport_yue.jsp";
				break;
			default :
				break;
			}
		});
	};
	return {
		init:_init
	};
}(window,jQuery);