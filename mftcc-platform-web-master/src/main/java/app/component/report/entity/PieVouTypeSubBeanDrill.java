package app.component.report.entity;
/**
 * 
 * @author rjq
 *
 */
public class PieVouTypeSubBeanDrill {

	private String name  ;//内容描述
	private String[]categories;//类别数组
	private double[]data; //对应类别的数组
	private String colors; //颜色
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getCategories() {
		return categories;
	}
	public void setCategories(String[] categories) {
		this.categories = categories;
	}
	public double[] getData() {
		return data;
	}
	public void setData(double[] data) {
		this.data = data;
	}
	public String getColors() {
		return colors;
	}
	public void setColors(String colors) {
		this.colors = colors;
	}

}
