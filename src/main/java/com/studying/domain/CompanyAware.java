package com.studying.domain;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * Desc
 * 
 * @Date: 2015年3月21日
 */
public class CompanyAware implements BeanFactoryAware{

	private Person person;
	private BeanFactory beanFactory;

	public Person getPerson() {
		// return person;
		return this.beanFactory.getBean("person", Person.class);
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
