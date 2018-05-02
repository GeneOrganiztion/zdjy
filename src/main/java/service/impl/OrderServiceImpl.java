package service.impl;

import Mapper.OrdersMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import po.Orders;
import service.OrderService;
import utils.DateUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Transactional
@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    
    @Autowired
    private OrdersMapper ordesMapper;

    public PageInfo<Orders> selectOrderByParams(Map map) {
        PageHelper.startPage((Integer)map.get("pageNo"),(Integer)map.get("rowCount"));
        List<Orders> list = ordesMapper.selectOrderByParams(map);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public List<Orders> selectOrderAndProByParams(Map map) {
        return ordesMapper.selectOrderAndProByParams(map);
    }

    public Orders selectOrdersByOrderId(Integer ordeId) {
        Orders orders = new Orders();
        orders.setId(ordeId);
        orders.setIsdelete(false);
        return ordesMapper.selectOne(orders);
    }

    public boolean updateOrder(Orders order) {
        try {
            ordesMapper.updateByPrimaryKeySelective(order);
            //ordeMapper.updateByPrimaryKey(order);
        } catch (Exception e) {
            logger.error("updateOrderStatus error:" + e);
            return false;
        }
        return true;
    }

    public  List<Orders>  getOrderByUserId(Map map) {
        return ordesMapper.getOrderByUserId(map);
    }

    public boolean updateOrderStatus(Orders order) {
        try {
            ordesMapper.updateByPrimaryKeySelective(order);
            //ordeMapper.updateByPrimaryKey(order);
        } catch (Exception e) {
            logger.error("updateOrderStatus error:" + e);
            return false;
        }
        return true;
    }

    public Orders getOrderByOrderId(Integer orderId) {
        return ordesMapper.getOrderByOrderId(orderId);
    }

    public int insertOrder(Map map) {
        String prepayid=(String)map.get("package");
        String timestamp=(String)map.get("timeStamp");
        String nonceStr=(String)map.get("nonceStr");
        String finalsign=(String)map.get("paySign");
        String finalmoney=(String)map.get("finalmoney");
        String userId=(String)map.get("userId");
        String detail_address=(String)map.get("detail_address");
        String customer_name=(String)map.get("customer_name");
        String customer_mobile=(String)map.get("customer_mobile");
        int resultid=-1;
        try {
            Orders orders = new Orders();
            orders.setOrdPrice(Integer.valueOf(finalmoney));
            orders.setOrdNum(DateUtil.format(new Date()));
            orders.setOrdState(1);
            orders.setOrdPay("1");
            orders.setOrdUser(Integer.valueOf(userId));
            orders.setPrepayId(prepayid);
            orders.setTimestamp(timestamp);
            orders.setNonceStr(nonceStr);
            orders.setFinalsign(finalsign);
            orders.setIsdelete(false);
            orders.setCreateTime(new Date());
            orders.setLastModifiedTime(new Date());
            orders.setUserAddress(detail_address);
            orders.setUserPhone(customer_mobile);
            orders.setUserName(customer_name);
            ordesMapper.insertUseGeneratedKeys(orders);
            resultid=orders.getId();
        } catch (Exception e) {
            logger.error("insertOrder error:" + e);
            return -1;
        }
        return resultid;

    }

    public boolean cancelOrder(Orders order) {
        order.setIsdelete(true);
        try {
            ordesMapper.updateByPrimaryKeySelective(order);
        } catch (Exception e) {
            logger.error("cancelOrder error:" + e);
            return false;
        }
        return true;
    }

    public Orders selectOneOrder(Orders orders) {
        orders.setIsdelete(false);
        return ordesMapper.selectOne(orders);
    }

    public Long countOrdersByParam(Map map) {
        return ordesMapper.countOrdersByParam(map);
    }

    public Boolean timeingDelWaitPayOrders(Map map) {
        try {
            ordesMapper.timeingDelWaitPayOrders(map);
        } catch (Exception e) {
            logger.error("timeingDelWaitPayOrders error:" + e);
            return false;
        }
        return true;

    }
}
