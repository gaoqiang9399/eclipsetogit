var SysConstant = window.SysConstant = {
	Required : 0,//0-文字提示，1-气泡提示
	placement:'top',//气泡提示方向,top,left,right,bottom
	Chinese_characters_count : 1,//汉子占多少个字节
	SysGlobalSearchForB : true,//是否允许在B面进行全局搜索
	pageSize_s : 0,//小于768PX,0为系统默认，控制不同分辨率每页列表数据展示多少
	pageSize_m :30,//大于等于768小于1080
	pageSize_l : 40,//大于等于1080
	taskOrder:"PAS_STICK DESC,PAS_AWARE DESC,PAS_STS ASC,str_to_date(CREATE_DATE,'%Y%m%d') DESC,str_to_date(CREATE_TIME,'%H:%i:%s') DESC",
	pmsEntranceMultiple:true//角色入口是否可以多选
};