package controler.quartz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import service.OrderService;

/**
 * @author yakungao
 * @date 2017/12/13
 **/
public class DelOrdesQuartz {

    @Autowired
    private OrderService orderService;
    public void execute(){
        HashMap<String, Object> map = new HashMap<String, Object>();
        //删除两小时之前未付款的订单
        int hour = 2;
        String twoOurTime = "";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hour);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        twoOurTime = df.format(calendar.getTime());
        map.put("time", twoOurTime);
        orderService.timeingDelWaitPayOrders(map);
    }

}
