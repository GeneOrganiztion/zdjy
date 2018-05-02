package Mapper;

import java.util.List;
import java.util.Map;

import po.Orders;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface OrdersMapper extends Mapper<Orders>,MySqlMapper<Orders> {


	public List<Orders> selectOrderAndProByParams(Map<String, Object> map);

	List<Orders> getOrderByUserId(Map map);

	List<Orders> selectOrderByParams(Map map);

	Orders getOrderByOrderId(Integer orderId);
	Long countOrdersByParam(Map<String, Object> map);

	Boolean timeingDelWaitPayOrders(Map map);
}