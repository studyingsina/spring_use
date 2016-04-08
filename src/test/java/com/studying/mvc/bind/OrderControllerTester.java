package com.studying.mvc.bind;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.alibaba.fastjson.JSON;
import com.studying.form.domain.OrderForm;

/**
 * Desc
 * 
 * @Date: 2015年5月11日
 */
@RunWith(SpringJUnit4ClassRunner.class)
// @WebAppConfiguration(value = "src/main/webapp")
@ContextConfiguration(locations = { "classpath*:HelloWeb-servlet.xml" })
public class OrderControllerTester extends AbstractJUnit4SpringContextTests {

	private static Logger logger = LoggerFactory.getLogger(OrderControllerTester.class);
	@Autowired
	private OrderController orderController;

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private RequestMappingHandlerAdapter handlerAdapter;

	private MockHttpServletRequest request;

	private MockHttpServletResponse response;

	@Test
	public void testCreateOrder() {
		request = new MockHttpServletRequest(HttpMethod.POST.name(), "/v1/yf/createOrder/1024/2024");
		response = new MockHttpServletResponse();
		request.addParameter("poiId", "10241");
		request.addParameter("goodsId", "20241");
		request.addParameter("isNeedRegistered", "false");
		request.addParameter("checkinTime", "1429027200000");
		request.addParameter("checkoutTime", "1429113600000");
		request.addParameter("roomCount", "1");
		request.addParameter("goodsType", "2");
		request.addParameter("platform", "android");
		request.addParameter("partnerId", "392012");
		try {
//			logger.info(appContext.toString());
//			logger.info(handlerAdapter.toString());
			HandlerMethod hm = new HandlerMethod(orderController,
					"createOrderCashier", HttpServletRequest.class, Integer.class, Long.class, OrderForm.class);
			handlerAdapter.handle(request, response, hm);
			Assert.assertEquals(response.getStatus(), 200);
			logger.info(JSON.toJSONString(response.getContentAsString()));
		} catch (Exception e) {
			logger.error("", e);
		}

	}

}
