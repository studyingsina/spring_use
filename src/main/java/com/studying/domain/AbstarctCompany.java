package com.studying.domain;


/**
 * Desc
 * 
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
