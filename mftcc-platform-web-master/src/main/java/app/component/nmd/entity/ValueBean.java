package app.component.nmd.entity;

import java.io.Serializable;
import java.util.List;

public class ValueBean implements Serializable {
	private String name1;
	private String name2;
	private String name3;
	private String name4;
	private String name5;
	private String name6;
	private String name7;
	
	private double value1;
	private double value2;
	private double value3;
	private double value4;
	private double value5;
	private List<TblFormDef> list;
	public List<TblFormDef> getList() {
		return list;
	}
	public void setList(List<TblFormDef> list) {
		this.list = list;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public String getName4() {
		return name4;
	}
	public void setName4(String name4) {
		this.name4 = name4;
	}
	public String getName5() {
		return name5;
	}
	public void setName5(String name5) {
		this.name5 = name5;
	}
	public String getName6() {
		return name6;
	}
	public void setName6(String name6) {
		this.name6 = name6;
	}
	public String getName7() {
		return name7;
	}
	public void setName7(String name7) {
		this.name7 = name7;
	}
	public double getValue1() {
		return value1;
	}
	public void setValue1(double value1) {
		this.value1 = value1;
	}
	public double getValue2() {
		return value2;
	}
	public void setValue2(double value2) {
		this.value2 = value2;
	}
	public double getValue3() {
		return value3;
	}
	public void setValue3(double value3) {
		this.value3 = value3;
	}
	public double getValue4() {
		return value4;
	}
	public void setValue4(double value4) {
		this.value4 = value4;
	}
	public double getValue5() {
		return value5;
	}
	public void setValue5(double value5) {
		this.value5 = value5;
	}
	public String getName3() {
		return name3;
	}
	public void setName3(String name3) {
		this.name3 = name3;
	}

}
