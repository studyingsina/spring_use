/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 * 酒店后台研发.
 */
package com.studying.domain;


/**
 * Desc
 * 
 * @author: zhangjunwei@meituan.com
 * @Date: 2015年3月21日
 */
public abstract class AbstarctCompany {

	private Person person;

	public Person getPerson() {
		return createPerson();
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	protected abstract Person createPerson();

}
