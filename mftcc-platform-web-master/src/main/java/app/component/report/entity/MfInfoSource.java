package app.component.report.entity;

public class MfInfoSource {
		//信息来源
		private  String infosource;
		//产品种类
		private  String kindname;
		//客户名称
		private  String cusname;
		//合同号
		private  String  packno;
		//借据号
		private  String  dueno;
		//贷款金额
		private  String  putoutamt;
		//贷款余额
		private  String  loanamt;
		//贷款利率类型
		private  String  ratetypename;
		//贷款利率
		private  String  excrate;
		//还款方式
		private  String  repaytype;
		//贷款期限
		private  String  timelimit;
		//借款起息日
		private  String  startime;
		//借款到期日
		private  String  endtime;
		//员工号
		private  String  userno;
		//客户经理
		private  String  username;
		//显示借据号
		private  String fincShowId;
		
		public String getCusname() {
			return cusname;
		}

		public void setCusname(String cusname) {
			this.cusname = cusname;
		}

		public String getLoanamt() {
			return loanamt;
		}

		public void setLoanamt(String loanamt) {
			this.loanamt = loanamt;
		}

		public String getRepaytype() {
			return repaytype;
		}

		public void setRepaytype(String repaytype) {
			this.repaytype = repaytype;
		}

		public String getUserno() {
			return userno;
		}

		public void setUserno(String userno) {
			this.userno = userno;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		//部门名称
		private  String depname;

		public String getDepname() {
			return depname;
		}

		public void setDepname(String depname) {
			this.depname = depname;
		}

		public String getKindname() {
			return kindname;
		}

		public void setKindname(String kindname) {
			this.kindname = kindname;
		}

		public String getPackno() {
			return packno;
		}

		public void setPackno(String packno) {
			this.packno = packno;
		}

		public String getDueno() {
			return dueno;
		}

		public void setDueno(String dueno) {
			this.dueno = dueno;
		}

		public String getRatetypename() {
			return ratetypename;
		}

		public void setRatetypename(String ratetypename) {
			this.ratetypename = ratetypename;
		}

		public String getExcrate() {
			return excrate;
		}

		public void setExcrate(String excrate) {
			this.excrate = excrate;
		}

		public String getPutoutamt() {
			return putoutamt;
		}

		public void setPutoutamt(String putoutamt) {
			this.putoutamt = putoutamt;
		}

		public String getTimelimit() {
			return timelimit;
		}

		public void setTimelimit(String timelimit) {
			this.timelimit = timelimit;
		}

		public String getStartime() {
			return startime;
		}

		public void setStartime(String startime) {
			this.startime = startime;
		}

		public String getEndtime() {
			return endtime;
		}

		public void setEndtime(String endtime) {
			this.endtime = endtime;
		}

		public String getInfosource() {
			return infosource;
		}

		public void setInfosource(String infosource) {
			this.infosource = infosource;
		}

		public String getFincShowId() {
			return fincShowId;
		}

		public void setFincShowId(String fincShowId) {
			this.fincShowId = fincShowId;
		}
}
