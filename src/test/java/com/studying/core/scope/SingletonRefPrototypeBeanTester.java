/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 * 酒店后台研发.
 */
package com.studying.core.scope;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.studying.domain.AbstarctCompany;
import com.studying.domain.Company;
import com.studying.domain.CompanyAware;
import com.studying.domain.CompanyProvider;
import com.studying.domain.Person;

/**
 * 单例bean引用原型bean.如:在迁移groupapi时,将restlet方式改为springmvc,原有的resource都是带状态的,需要为单例.<br />
 * 参考:<br /> <a href="http://stackoverflow.com/questions/3891997/how-to-do-spring-lookup-method-injection-with-annotations">
 * how-to-do-spring-lookup-method-injection-with-annotations</a><br />
 * <a href="https://jira.spring.io/browse/SPR-5192">lookupmethod</a><br />
 * <a href="http://docs.spring.io/spring/docs/2.5.x/reference/beans.html#beans-factory-method-injection">Method Injection</a>
 * @author: zhangjunwei@meituan.com
 * @Date: 2015年3月21日
 */
public class SingletonRefPrototypeBeanTester {

	private static Logger logger = LoggerFactory.getLogger(SingletonRefPrototypeBeanTester.class);

	private ApplicationContext context;

	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext("core/bean-scope.xml");
	}

	@Test
	public void testPrototype() {
		logger.info("testPrototype......");
		for (int i = 0; i < 3; i++) {
			Person person = context.getBean("person", Person.class);
			logger.info("person is {}, instance {}", i, person.toString());
		}
	}

	@Test
	public void testSingleton() {
		logger.info("testSingleton......");
		for (int i = 0; i < 3; i++) {
			Company company = context.getBean("company", Company.class);
			logger.info("Company is {}, instance {}, person {}", i, company.toString(), company.getPerson());
		}
	}

	@Test
	public void testSingletonRefPrototype() {
		logger.info("testSingletonRefPrototype......");
		for (int i = 0; i < 3; i++) {
			Company company = context.getBean("company", Company.class);
			logger.info("Company is {},company instance {}, person instance {}", i, company, company.getPerson());
		}
	}
	
	@Test
	public void testSingletonRefProtype02(){
		logger.info("testSingletonRefProtype02......");
		for (int i = 0; i < 3; i++) {
			Company company = context.getBean("company", Company.class);
			logger.info("Company is {},company instance {}, person instance {}", i, company, company.getPerson());
		}
	}
	
	@Test
	public void testSingletonRefProtype03(){
		logger.info("testSingletonRefProtype03......");
		for(int i = 0; i < 3; i++){
			CompanyAware ca = context.getBean("companyAware", CompanyAware.class);
			logger.info("Company is {},company instance {}, person instance {}", i, ca, ca.getPerson());
		}
	}
	
	@Test
	public void testSingletonRefProtype04(){
		logger.info("testSingletonRefProtype04......");
		for(int i = 0; i < 3; i++){
			AbstarctCompany ca = context.getBean("abstarctCompany", AbstarctCompany.class);
			logger.info("Company is {},company instance {}, person instance {}", i, ca, ca.getPerson());
		}
	}
	
	@Test
	public void testSingletonRefProtype05(){
		logger.info("testSingletonRefProtype05......");
		for(int i = 0; i < 3; i++){
			CompanyProvider ca = context.getBean("companyProvider", CompanyProvider.class);
			logger.info("Company is {},company instance {}, person instance {}", i, ca, ca.getPerson());
		}
	}

}
