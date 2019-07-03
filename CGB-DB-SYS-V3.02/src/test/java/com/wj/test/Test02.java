package com.wj.test;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test02 {
	protected ClassPathXmlApplicationContext cx;
	@Before
	public void init() {
		cx = new ClassPathXmlApplicationContext("spring-config.xml");
	}
	@Test
	public void test01() {
		SqlSessionFactory bean = cx.getBean("sqlSessionFactory",SqlSessionFactory.class);
		System.out.println(cx);
	}
	
}
