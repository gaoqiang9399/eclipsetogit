package app.component.report.entity;
/**
 * 
 * @author rjq
 *
 */
public class PieVouTypeSubBean {
	

	private double  y; //内部分类对应的数值
	private String color;//颜色
	private PieVouTypeSubBeanDrill drilldown;//钻取外部数据
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public PieVouTypeSubBeanDrill getDrilldown() {
		return drilldown;
	}
	public void setDrilldown(PieVouTypeSubBeanDrill drilldown) {
		this.drilldown = drilldown;
	}
	


}
