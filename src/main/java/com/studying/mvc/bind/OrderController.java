package com.studying.mvc.bind;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.studying.form.domain.OrderForm;

/**
 * 订单接口.
 * 
 * @Date: 2015年5月11日
 */
@Controller
public class OrderController {

	@RequestMapping(value = "/v1/yf/createOrder/{poiId}/{goodsId}", method = RequestMethod.POST)
	@ResponseBody
	public Object createOrderCashier(HttpServletRequest request, @PathVariable("poiId") Integer poiId,
			@PathVariable("goodsId") Long goodsId, OrderForm orderForm) {

		return orderForm;
	}

}
