package service;

import com.github.pagehelper.PageInfo;
import po.Orders;

import java.util.List;
import java.util.Map;

public interface OrderService<T> {


	public PageInfo<Orders> selectOrderByParams(Map<String, Object> map);

	public  List<Orders> selectOrderAndProByParams(Map<String, Object> map);

	public Orders selectOrdersByOrderId(Integer ordeId);

	public boolean updateOrder(Orders orders);
	
	//根据userId或其他属性查看订单的详情，以及订单下的商品

	List<T> getOrderByUserId(Map map);
	
	boolean updateOrderStatus(Orders order);

	public Orders getOrderByOrderId(Integer orderId);
	
	public int insertOrder(Map map);
	
	public boolean cancelOrder(Orders order);
	
	public Orders selectOneOrder(Orders orders);

	public Long countOrdersByParam(Map<String, Object> map);

	//定时删除待付款订单
	public Boolean timeingDelWaitPayOrders(Map<String, Object> map);

}
