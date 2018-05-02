package controler.order;

import com.github.pagehelper.PageInfo;
import controler.base.BaseController;
import domain.OrdersStatus;
import dto.OrderAndProductDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import po.Orders;
import po.ResModel;
import service.MapOrderProductService;
import service.OrderService;
import utils.Constant;
import utils.ST;

@Controller
@RequestMapping(value="/orders")
public class OrderController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	private String SEND_WATTING_PAGE = "order/sendWattingOrder";
	private String COMPLETE_ORDER_PAGE = "order/completeOrder";
	
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private MapOrderProductService mapOrderProductService;
	
	
	@RequestMapping(value="/sendWattingOrderPage")
	public ModelAndView sendWattingOrderPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName(SEND_WATTING_PAGE);
		return mv;
	}
	
	@RequestMapping(value="/completeOrderPage")
	public ModelAndView completeOrderPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName(COMPLETE_ORDER_PAGE);
		return mv;
	}
	
	@RequestMapping(value = "/seletcOrder")
	@ResponseBody
	public PageInfo<Orders> selectOrder(HttpServletRequest request,HttpServletResponse response){
		String sidx = getParam("sidx");// 排序字段;
        String sord = getParam("sord");// 升序降序;
        PageInfo<Orders> pageInfo = new PageInfo<Orders>();
        try {
        	int oneRecord = Integer.valueOf(getParam("rows"));// 一页几行
            int pageNo = Integer.valueOf(getParam("page"));// 第几页
            String orderId = getParam("orderId");
            String ordNum = getParam("ordNum");
            String ordState = getParam("ordState");
            String userPostal = getParam("userPostal");
            String flag = getParam("flag");
            if(ST.isNull(ordState)){
            	if("sendWattingOrder".equals(flag)){
            		ordState = "2";
            	}else if("receWattingAndCompleteOrder".equals(flag)){
            		ordState = "3,4";
            	}
            }
            String beginTime = getParam("beginTime");
            String endTime = getParam("endTime");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("sidx", sidx);// 排序字段
            map.put("sord", sord);// 升序降序
            map.put("rowCount", oneRecord);//一页几行
            map.put("pageNo", pageNo);
            map.put("userPostal", userPostal);
            map.put("ordNum", ordNum);
            if(!ST.isNull(beginTime)){
            	map.put("beginTime", beginTime + " 00:00:00");
            }
            if(!ST.isNull(endTime)){
            	map.put("endTime", endTime + " 59:59:59");
            }
            map.put("orderId", orderId);
            if(!ST.isNull(ordState)){
				List<Integer> list = ST.StringToList(ordState);
				map.put("stateList", list);
			}
            pageInfo= (PageInfo<Orders>)orderService.selectOrderByParams(map);
		} catch (Exception e) {
			logger.error("seletcOrder error:" + e);
		}
        return pageInfo;
	}
	
	@RequestMapping(value = "/selectOrderAndPrductByOrderId")
	@ResponseBody
	public List<OrderAndProductDTO> selectOrderAndPrductByOrderId(HttpServletRequest request,HttpServletResponse response){
		List<OrderAndProductDTO> list = new ArrayList<OrderAndProductDTO>();
		String orderId = getParam("orderId");
		if(ST.isNull(orderId)){
			return list;
		}
        try {
        	list = mapOrderProductService.selectOderAndProductByOrderId(Integer.valueOf(orderId));
		} catch (Exception e) {
			logger.error("selectOrderAndPrductByOrderId error:" + e);
		}
        return list;
	}
	
	@RequestMapping(value = "/saveDeliverProduct")
	@ResponseBody
	public ResModel saveDeliverProduct(HttpServletRequest request,HttpServletResponse response){
		ResModel resModel = new ResModel();
		String orderId = getParam("orderId");
		String courierNum = getParam("courierNum");
		String courierName = getParam("courierName");
		/*String courierPhone = getParam("courierPhone");*/
		if(ST.isNull(orderId)){
			resModel.setSuccess(false);
			return resModel;
		}
		Orders order = new Orders();
		order.setId(Integer.valueOf(orderId));
		order.setUserCourierNum(courierNum);
		order.setUserCourierName(courierName);
		/*order.setCourierPhone(courierPhone);*/
		order.setOrdState(Constant.ORDER_STATUS3);
		//order.setIsdelete(false);
		order.setLastModifiedTime(new Date());
		try {
			orderService.updateOrder(order);
		} catch (Exception e) {
			logger.error("saveDeliverProduct error:" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(true);
		return resModel;
	}
	
	@RequestMapping(value = "/selectOrderDetail")
	@ResponseBody
	public Orders selectOrderDetail(HttpServletRequest request,HttpServletResponse response){
		Orders order = new Orders();
		String orderId = getParam("orderId");
		if(ST.isNull(orderId)){
			return order;
		}
        try {
        	order = orderService.selectOrdersByOrderId(Integer.valueOf(orderId));
		} catch (Exception e) {
			logger.error("selectOrderDetail error:" + e);
		}
        return order;
	}


	/*******************手机端调用接口******************/
	
	/**
	 * 根据userId或其他属性查看订单的详情，以及订单下的商品
	 * @param userId:用户ID   ordState:订单状态（选填  不是必须的）
	 * @return
	 */
	
	@RequestMapping(value = "/phoneSelectOrders")
    @ResponseBody
	public List<Orders> selectOrdersByParams(){
		List<Orders> listOrd = new ArrayList<Orders>();
		String userId = getParam("userId");
		String ordState = getParam("ordState");
		String orderId = getParam("orderId");

		try {
			Map<String,Object> map = new HashMap<String, Object>();
			if(!ST.isNull(userId)){
				map.put("ordUser", userId);
			}
			if(!ST.isNull(ordState)){
				List<Integer> list = ST.StringToList(ordState);
				map.put("stateList", list);
			}
			if(!ST.isNull(orderId)){
				map.put("orderId", orderId);
			}
			listOrd = orderService.selectOrderAndProByParams(map);
		} catch (Exception e) {
			logger.error("selectOrdersByParams error:" + e);
		}
		return listOrd;
	}

	@RequestMapping(value = "/phoneSelectAllOrdersCount")
	@ResponseBody
	public ResModel countOrdersByParams(){
		ResModel resModel = new ResModel();
		Map<String, Object> map = new HashMap<String, Object>();
		String userId = getParam("userId");
		map.put("ordUser", userId);
		try {
			//待付款
			List<Integer> waitPayList = ST.StringToList(String.valueOf(OrdersStatus.WAITPAY.getValue()));
			map.put("stateList", waitPayList);
			Long waitPayCount = orderService.countOrdersByParam(map);
			//待发货
			List<Integer> waitSendList = ST.StringToList(String.valueOf(OrdersStatus.WAITSEND.getValue()));
			map.put("stateList", waitSendList);
			Long waitSendCount = orderService.countOrdersByParam(map);
			//待收货
			List<Integer> waitReceiveList = ST.StringToList(String.valueOf(OrdersStatus.WAITRECEIVE.getValue()));
			map.put("stateList", waitReceiveList);
			Long waitReceiveCount = orderService.countOrdersByParam(map);
			//已完成
			List<Integer> completeList = ST.StringToList(String.valueOf(OrdersStatus.COMPLETE.getValue()));
			map.put("stateList", completeList);
			Long completeCount = orderService.countOrdersByParam(map);

			JSONArray jsonArr = new JSONArray();
			JSONObject waitPay = new JSONObject();
			waitPay.put("waitPayCount", waitPayCount);

			JSONObject waitSendWatPay = new JSONObject();
			waitSendWatPay.put("waitSendCount", waitSendCount);

			JSONObject waitReceivePay = new JSONObject();
			waitReceivePay.put("waitReceiveCount", waitReceiveCount);

			JSONObject completePay = new JSONObject();
			completePay.put("completeCount", completeCount);

			jsonArr.add(waitPay);
			jsonArr.add(waitSendWatPay);
			jsonArr.add(waitReceivePay);
			jsonArr.add(completePay);

			resModel.setMsg("countOrdersByParams success");
			resModel.setSuccess(true);
			resModel.setData(jsonArr);

		}catch (Exception e){
			logger.error("countOrdersByParams error:" + e);
			resModel.setSuccess(false);
			resModel.setMsg("countOrdersByParams error");
		}
		return resModel;
	}
}
