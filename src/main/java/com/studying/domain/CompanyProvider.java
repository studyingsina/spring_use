package com.studying.domain;

import javax.inject.Provider;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Desc
 * 
 * @Date: 2015年3月21日
 */
public class CompanyProvider {

	@Autowired
	private Provider<Person> provider;

	public Person getPerson() {
		return provider.get();
	}
	
}
