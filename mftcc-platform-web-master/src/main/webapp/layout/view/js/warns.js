//日程提醒

//判断浏览器是否支持会话级别的本地存储
function supports_html5_storage() {
    try {
         return 'openDatabase' in window && window['openDatabase'] !== null;
    } catch (e) {
        return false;
    }
}



/*
//添加key-value 数据到 sessionStorage
sessionStorage.setItem("demokey", "233333");
//通过key来获取value
var dt = sessionStorage.getItem("demokey");
//清空所有的key-value数据。
sessionStorage.clear();
*/
function RecWarnd(array){
	this.init(array);
}

RecWarnd.prototype = {
	init:function(array){
		var dataBase = this.getCurrentDb();
		if (!dataBase) {
			console.log("数据库创建失败！");
		} else {
			if(array.length>0){
				this.DBData = array;
			}
				this.DB = dataBase;
				this.dropTable();
				this.createTable();
			console.log("数据库创建成功！");
		}
	},
	createTable:function(){
		var db = this.DB;
		db.transaction(
            	function (trans) {//启动一个事务，并设置回调函数
                //执行创建表的Sql脚本
                	 trans.executeSql("create table if not exists warns(calendarNo text null,makeMan text null,warnTime text null)", [], 
                	 function (ts, data) {
                    }, function (ts, message) {
                    	console.log(message);
                    });
	            });
		if(this.DBData&&this.DBData.length>0){
		this.initSource();
		}
	},
	getCurrentDb:function () {
        //打开数据库，或者直接连接数据库参数：数据库名称，版本，概述，大小
        //如果数据库不存在那么创建之
        var db = openDatabase("warnDB", "1.0", "个人日程提醒", 1024 * 1024, function () { });
        return db;
    },
    initSource:function(){
    	var $this = this;
    	var DBData = $this.DBData;
    	//$this.clearTable();
    	$.each(DBData,function(i,data){
    		$this.insertSource(data);
    	});
    	this.getFirstData();
    },
    insertSource:function(data){
    	var db = this.DB;
        //执行sql脚本，插入数据
        db.transaction(function (trans) {
            trans.executeSql("insert into warns(calendarNo,makeMan,warnTime) values(?,?,?) ", [data.calendarNo, data.makeMan, data.warnTime], function (ts, result) {
            }, function (ts, message) {
            	console.log(message);
            });
        });
    },
    insertData:function(data){
    	var $this = this;
    	var db = $this.DB;
        //执行sql脚本，插入数据
        db.transaction(function (trans) {
            trans.executeSql("insert into warns(calendarNo,makeMan,warnTime) values(?,?,?) ", [data.calendarNo, data.makeMan, data.warnTime], function (ts, result) {
            	console.log("insert");
            	$this.getFirstData();
            }, function (ts, message) {
            	console.log(message);
            });
        });
    },
    updateData:function(data){
    	console.log("update");
    	var $this = this;
    	var db = $this.DB;
    	//执行sql脚本，插入数据
        db.transaction(function (trans) {
            trans.executeSql("select * FROM  warns where calendarNo = ?", [data.calendarNo], function (ts, result) {
            	if(result.rows.length>0){
            		db.transaction(function (trans) {
                        trans.executeSql("update warns set warnTime = ?", [data.warnTime], function (ts, result) {
                        	$this.getFirstData();
                        	console.log("update-update");
                        }, function (ts, message) {
                        	console.log(message);
                        });
                    });
            	}else{
            		db.transaction(function (trans) {
                        trans.executeSql("insert into warns(calendarNo,makeMan,warnTime) values(?,?,?) ", [data.calendarNo, data.makeMan, data.warnTime], 
                        function (ts, result) {
                        	console.log("update-insert");
                        	$this.getFirstData();
                        }, function (ts, message) {
                        	console.log(message);
                        });
                    });
            	}
            }, function (ts, message) {
            	console.log(message);
            });
        });
    },
    dropTable:function(){
    	var db = this.DB;
		db.transaction(
            	function (trans) {//启动一个事务，并设置回调函数
                //执行创建表的Sql脚本
                	 trans.executeSql("drop table if exists warns", [], 
                	 function (ts, result) {
                    }, function (ts, message) {
                    	console.log(message);
                    });
	            });
    },
    clearTable:function(){
    	var db = this.DB;
        //执行sql脚本，插入数据
        db.transaction(function (trans) {
            trans.executeSql("delete FROM  warns",[], function (ts, result) {
            }, function (ts, message) {
                console.log(message);
            });
        });
    },
    deleteData:function(data){
    	var $this = this;
    	var db = this.DB;
        //执行sql脚本，删除数据
        db.transaction(function (trans) {
            trans.executeSql("delete FROM  warns where calendarNo = ?",[data.calendarNo], function (ts,result) {
            	console.log("delete");
            	$this.getFirstData();
            }, function (ts, message) {
                console.log(message);
            });
        });
    },
    getFirstData:function(){
    	var $this =this;
    	var db = this.DB;
        //执行sql脚本，插入数据
        db.transaction(function (trans) {
            trans.executeSql("select * FROM  warns order by warnTime ASC limit 1",[], function (ts,result) {
            	if(result.rows.length>0){
            		$this.createTime(result.rows[0]);
            	}
            }, function (ts, message) {
                console.log(message);
            });
        });
    },
    createTime:function(data){
    	var $this = this;
    	var arr = data.warnTime.split(":");
    	var hour = arr[0];
    	var mins = arr[1];
    	var currDate = new Date();
    	var myDate=new Date();
    	this.clearTime;
    	myDate.setFullYear(currDate.getFullYear() ,currDate.getMonth(),currDate.getDate());
    	myDate.setHours(hour,mins,0,0);
    	var sysDate = new Date();
    	var millis = myDate.getTime()-sysDate.getTime();
    	clearTimeout(this.clearTime);
    	if(millis>0){
    		this.clearTime = setTimeout(function(){
    			$this.getWarnList(data);
    		},millis)
    	}else{
    		this.deleteData(data);
    	}
    },
    getWarnList:function(data){
    	var $this =this;
    	var db = this.DB;
        //执行sql脚本，插入数据
        db.transaction(function (trans) {
            trans.executeSql("select * FROM  warns where warnTime = ?",[data.warnTime], function (ts,result) {
            	$.each(result.rows,function(i,info){
            		$this.sendMsg(info);
            	});
            }, function (ts, message) {
                console.log(message);
            });
        });
    },
    sendMsg:function(info){
    	var $this = this
    	$.ajax({
			type:"POST",
			async:false,
			cache:false,
			url:webPath+"/WorkCalendar/sendMsgAjax",
			dataType:"json",
			data:info,
			success:function(data){
				if(data.flag=="success"){
					$this.deleteData(info);
				};
			},
			error:function(){
				alert("error");
			}
		});
    }
}