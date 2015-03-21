/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 * 酒店后台研发.
 */
package com.studying.domain;

import javax.inject.Provider;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Desc
 * 
 * @author: zhangjunwei@meituan.com
 * @Date: 2015年3月21日
 */
public class CompanyProvider {

	@Autowired
	private Provider<Person> provider;

	public Person getPerson() {
		return provider.get();
	}
	
}
