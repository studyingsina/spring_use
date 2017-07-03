package com.studying.es;

/**
 * 
 *
 * @version ： 1.0
 * 
 * @author ： 苏若年 <a href="mailto:DennisIT@163.com">发送邮件</a>
 * 
 * @since ： 1.0 创建时间: 2013-4-8 下午04:51:03
 * 
 * @function： TODO
 *
 */
public class Medicine {
	private Integer id;
	private String name;
	private String function;

	public Medicine() {
		super();
	}

	public Medicine(Integer id, String name, String function) {
		super();
		this.id = id;
		this.name = name;
		this.function = function;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}
	
}