package controler;

import controler.base.BaseController;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.liufeng.course.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import po.*;
import service.MapOrderProductService;
import service.OrderService;
import service.ProductService;
import service.UserService;
import utils.Constant;
import utils.ST;
import utils.SmsDemo;
import wepay.utils.HttpConnect;
import wepay.utils.HttpResponse;
import wepay.utils.WePay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/weixin")
public class WeChatController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(WeChatController.class);

	 @Autowired
	 private UserService userService;
	// @Autowired
	// private CartService cartService;

	 @Autowired
	 private MapOrderProductService mapOrderProductService;
	 @Autowired
	 private ProductService productService;
	 @Autowired
	 private OrderService orderService;

	@RequestMapping("/oauth")
	public void oauthuser(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
		// 共账号及商户相关参数
		String appid = Constant.APPID;
		String appsecret = Constant.APPSECRET;
		String partner = Constant.PARTNER;
		String partnerkey = Constant.PARTNERKEY;

		String backUri = Constant.WEBACKURI;
		System.out.println(backUri);
		// 授权后要跳转的链接所需的参数一般有会员号，金额，订单号之类，
		// 最好自己带上一个加密字符串将金额加上一个自定义的key用MD5签名或者自己写的签名,
		// 比如 Sign = %3D%2F%CS%
		backUri = backUri;
		// URLEncoder.encode 后可以在backUri 的url里面获取传递的所有参数
		backUri = URLEncoder.encode(backUri);
		// scope 参数视各自需求而定，这里用scope=snsapi_base 不弹出授权页面直接授权目的只获取统一支付接口的openid
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" + "appid=" + appid + "&redirect_uri="
				+ backUri + "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
		response.sendRedirect(url);

	}

	@RequestMapping("/weixinUnion")
	@ResponseBody
	public Map weixinUnion(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {

		//String code1 = request.getParameter("code");
		String code = getParam("code");
        String city = getParam("city");
        String province = getParam("province");
        String nickname = getParam("nickName");
        String avatarUrl = getParam("avatarUrl");
        Integer isman = Integer.valueOf(getParam("gender"));
        boolean sex = true;
        System.out.println(isman == 1);
        if(isman != 1){
            sex=false;
        }
		// 商户相关资料
		String appid = Constant.APPID;
		String appsecret = Constant.APPSECRET;
		String OPENID = "";
		String ACCESS_TOKE = "";
		String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + appsecret
				+ "&code=" + code + "&grant_type=authorization_code";
		Map<String, Object> dataMap = new HashMap<String, Object>();
		HttpResponse temp = HttpConnect.getInstance().doGetStr(URL);
		String tempValue = "";
		if (temp == null) {
		//	response.sendRedirect("/zdjy/error.jsp");
			logger.info("temp==null");
		} else {
			try {
				tempValue = temp.getStringResult();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONObject jsonObj = JSONObject.fromObject(tempValue);
			if (jsonObj.containsKey("errcode")) {
				logger.info("code huoqu errcode=");
				System.out.println(tempValue);
				//response.sendRedirect("/zdjy/error.jsp");
			}
			OPENID = jsonObj.getString("openid");
			ACCESS_TOKE = jsonObj.getString("access_token");
			logger.info("openId=" + OPENID);
			logger.info("ACCESS_TOKE=" + ACCESS_TOKE);
		}

                User user = new User();
				user.setOpenid(OPENID);
				User own = (User) userService.select(user);
				if (null == own) {
					user.setOpenid(OPENID);
					user.setCity(city);
					user.setProvince(province);
					user.setHeadImgurl(avatarUrl);
					user.setNickname(nickname);
					user.setSex(sex);
                    int userid = userService.insertUser(user);
                    dataMap.put("userId", String.valueOf(userid));
				}else{
                    dataMap.put("userId", String.valueOf(own.getId()));
                }
		dataMap.put("openId", OPENID);
		dataMap.put("ACCESS_TOKE", ACCESS_TOKE);

		return dataMap;

//		String UserinfoURL = "https://api.weixin.qq.com/sns/userinfo?access_token=" + ACCESS_TOKE + "&openid=" + OPENID
//				+ "&lang=zh_CN";
//		HttpResponse userinfo = HttpConnect.getInstance().doGetStr(UserinfoURL);
//		String userValue = null;
//		String openid = null;
//		String userid = null;
//		try {
//			if (userinfo == null) {
//				response.sendRedirect("/SpringGene1/error.jsp");
//				logger.info("userinfo==null");
//			} else {
//				try {
//					userValue = userinfo.getStringResult();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				JSONObject jsonObj = JSONObject.fromObject(userValue);
//				if (jsonObj.containsKey("errcode")) {
//					logger.info("errcode=");
//					System.out.println(userValue);
//					response.sendRedirect("/zdjy/error.jsp");
//				}
//				openid = jsonObj.getString("openid");
//				String nickname = jsonObj.getString("nickname");
//				String sex = jsonObj.getString("sex");
//				boolean isman = true;
//				if ("1".equals(sex)) {
//
//				} else {
//					isman = false;
//				}
//				String province = jsonObj.getString("province");
//				String city = jsonObj.getString("city");
//				String headimgurl = jsonObj.getString("headimgurl");
//				User user = new User();
//				user.setOpenid(openid);
//				User own = (User) userService.select(user);
//				if (null == own) {
//					user.setOpenid(openid);
//					user.setCity(city);
//					user.setProvince(province);
//					user.setHeadImgurl(headimgurl);
//					user.setNickname(nickname);
//					user.setSex(isman);
//					int uid = userService.insertUser(user);
//					userid = String.valueOf(uid);
//					Cart cart = new Cart();
//					cart.setUserId(uid);
//					cartService.insertCart(cart);
//				} else {
//					userid = String.valueOf(own.getId());
//					user.setId(own.getId());
//					user.setCity(city);
//					user.setHeadImgurl(headimgurl);
//					user.setNickname(nickname);
//					user.setSex(isman);
//					userService.updateUser(user);
//				}
//			}
//
//		} catch (Exception e) {
//
//			logger.info("weixinUnion errcode=" + e);
//		}
//
//		logger.info("userid=" + userid);
//		logger.info("openid=" + openid);
//
//		response.sendRedirect("/DNAjiankang/index.html#/home/" + userid + "/" + openid);
//
//		// response.sendRedirect("/SpringGene1/test.jsp?dataMap="+openid);
	}
	@RequestMapping("/topay")
	@ResponseBody
	public Map<String, String> weixinpay(HttpServletRequest request, HttpServletResponse response)
			throws Exception, IOException {
		//String product_uid = request.getParameter("parameter");// 商品
		String requestString=getPostJSON(request);
		JSONObject json = JSONObject.fromObject(requestString);
		String openId  = (String)json.get("openId");
		JSONObject parJson = JSONObject.fromObject(json.get("parameter"));
		Integer finalmoney =(Integer)parJson.get("finalmoney");
		Integer orderId =(Integer)parJson.get("orderId");
		String address	=(String)parJson.get("detail_address");
		String user_name =(String)parJson.get("customer_name");
		String user_phone = (String)parJson.get("customer_mobile");
		List<MapOrderProduct> listMapOrder = new ArrayList<MapOrderProduct>();
		JSONArray jsonArray = JSONArray.fromObject(parJson.get("order_items"));
		listMapOrder = JSONArray.toList(jsonArray, MapOrderProduct.class);
		logger.info("openId=" + openId);
		logger.info("orderProducts=" + jsonArray.toString());
		logger.info("orderId=" + orderId);
		logger.info("listMapOrder=" + listMapOrder);
		Map<String, String> map = new HashMap<String, String>();

		if (ST.isNull(orderId)) {
			if (ST.isNull(finalmoney)) {
				map.put("code","10001");
			}
			//int money = Integer.valueOf(finalmoney);
			map = WePay.toPay(openId, finalmoney, request, response);
			map.put("detail_address", address);
			map.put("customer_name",user_name);
			map.put("customer_mobile", user_phone);
			logger.info("first order--------------------------------------------------------------------------");
			logger.info("map packages=" + map.get("package"));
			logger.info("map timeStamp=" + map.get("timeStamp"));
			logger.info("map paySign=" + map.get("paySign"));
			User user = new User();
			user.setOpenid(openId);
			user = (User) userService.select(user);
			map.put("userId", String.valueOf(user.getId()));
			map.put("finalmoney", Integer.toString(finalmoney));
			int order_id = orderService.insertOrder(map);
			map.put("orderId", String.valueOf(order_id));
			if (order_id > 0) {
				for (int i = 0; i < listMapOrder.size(); i++) {
					MapOrderProduct maporder = listMapOrder.get(i);
					logger.info(
							"jin ru listMapOrder order--------------------------------------------------------------------------");
					Product product = new Product();
					product.setId(maporder.getProId());
					Product pro = (Product) productService.selectOne(product);
					maporder.setProName(pro.getProName());
					maporder.setOrdId(order_id);
					maporder.setProPrice(pro.getProRateprice());
					maporder.setProClassifyId(pro.getClassifyId());
					mapOrderProductService.saveMapOderPro(maporder);

				}
				map.put("code","200");
				//WePay.send_template_message(Constant.APPID, Constant.APPSECRET, openId, String.valueOf(user.getId())); // 发送模板消息
			} else {
				map.put("code","10002");
			}
		} else {
			Orders order = orderService.selectOrdersByOrderId(Integer.valueOf(orderId));
			String time = order.getTimestamp();
			int nowtime = (int) (System.currentTimeMillis() / 1000);
			if ((nowtime - Integer.valueOf(time)) > 3600) {
				map.put("finalmoney", Integer.toString(finalmoney));
				logger.info(
						"order out_time order--------------------------------------------------------------------------");
				map.put("orderId", String.valueOf(orderId));
				map = WePay.toPay(openId, Integer.valueOf(finalmoney), request, response);
				order.setTimestamp(map.get("timeStamp"));
				order.setPrepayId(map.get("package"));
				order.setFinalsign(map.get("paySign"));
				order.setNonceStr(map.get("nonceStr"));
				orderService.updateOrder(order);
				map.put("code","200");
			} else {
				logger.info(
						"order repeat order--------------------------------------------------------------------------");
				map.put("appId", Constant.APPID);
				map.put("signType", "MD5");
				map.put("timeStamp", order.getTimestamp());
				map.put("nonceStr", order.getNonceStr());
				map.put("package", order.getPrepayId());
				map.put("paySign", order.getFinalsign());
				map.put("finalmoney", String.valueOf(order.getOrdPrice()));
				map.put("orderId", String.valueOf(order.getId()));
				map.put("code","200");
			}

		}

		return map;
	}
/*
	@RequestMapping("/address")
	@ResponseBody
	public HashMap<String, String> addresstest(HttpServletRequest request, HttpServletResponse response)
			throws Exception, IOException {
		String JSURL = getParam("url");
		String ACCESS_TOKE = "";
		String URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + Constant.APPID
				+ "&secret=" + Constant.APPSECRET;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		HttpResponse temp = HttpConnect.getInstance().doGetStr(URL);
		String tempValue = "";
		if (temp == null) {
			// response.sendRedirect("/SpringGene1/error.jsp");
			logger.info("temp==null");
		} else {
			try {
				tempValue = temp.getStringResult();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONObject jsonObj = JSONObject.fromObject(tempValue);
			if (jsonObj.containsKey("errcode")) {
				logger.info("accesstoken huoqu errcode=" + jsonObj.getString("errcode"));
				System.out.println(tempValue);
				// response.sendRedirect("/SpringGene1/error.jsp");
			}

			ACCESS_TOKE = jsonObj.getString("access_token");
			logger.info("ACCESS_TOKE=" + ACCESS_TOKE);
		}
		// 获取ticket
		String JsApiTicket = "";
		String URLTICK = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=" + ACCESS_TOKE
				+ "&type=jsapi";
		HttpResponse temptick = HttpConnect.getInstance().doGetStr(URLTICK);
		String tempValue1 = "";
		if (temptick == null) {
			response.sendRedirect("/zdjy/error.jsp");
			logger.info("temp==null");
		} else {
			try {
				tempValue1 = temptick.getStringResult();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONObject jsonObj1 = JSONObject.fromObject(tempValue1);
			if (jsonObj1.containsKey("errcode")) {
				logger.info("tick huoqu errmsg=" + jsonObj1.getString("errmsg"));
				// response.sendRedirect("/SpringGene1/error.jsp");
			}
			JsApiTicket = jsonObj1.getString("ticket");
			logger.info("JsApiTicket=" + JsApiTicket);
		}
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
		String noncestr = strReq;
		String timestamp = Sha1Util.getTimeStamp();
		// 获取请求url
		String url = JSURL;
		String str = "jsapi_ticket=" + JsApiTicket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url="
				+ url;
		// sha1加密
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String signature = HttpXmlClient.SHA1(str);
		hashMap.put("signature", signature);
		hashMap.put("appid", Constant.APPID);
		hashMap.put("timestamp", timestamp);
		hashMap.put("noncestr", noncestr);
		logger.info("signature=" + signature);
		logger.info("appid=" + Constant.APPID);
		logger.info("timestamp=" + timestamp);
		logger.info("noncestr=" + noncestr);
		return hashMap;

	}

	@RequestMapping(value = { "/wexin" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public String xxtInterface(WeChat wc) throws Exception {
		String signature = wc.getSignature();
		String timestamp = wc.getTimestamp();
		String nonce = wc.getNonce();
		String echostr = wc.getEchostr();

		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			return echostr;
		}
		return null;
	}

	@RequestMapping(value = { "/wexin" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public String getWeiXinMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String respXml = CoreService.processRequest(request);
		return respXml;
	}

	@RequestMapping(value = { "/totest" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public Map<String, String> totest(HttpServletRequest request, HttpServletResponse response)
			throws Exception, IOException {

		// 网页授权后获取传递的参数
		String userId = request.getParameter("openId");
		String finalmoney = request.getParameter("finalmoney");
		String orderId = request.getParameter("orderId");
		System.out.println("userId=" + userId);
		System.out.println("finalmoney=" + finalmoney);

		Map<String, String> map = new HashMap<String, String>();

		map.put("userId", userId);
		map.put("finalmoney", finalmoney);
		return map;
		
		 * response.sendRedirect("/SpringGene1/pay.jsp?appid="+appid2+
		 * "&timeStamp="+timestamp+"&nonceStr="+nonceStr2+"&package="+packages+
		 * "&sign="+finalsign);
		 

	}
*/
	@RequestMapping("/cancelpay")
	@ResponseBody
	public boolean cancelpay(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
		boolean flag = false;
		String orederId = request.getParameter("orderId");
		Orders order = new Orders();
		order.setId(Integer.valueOf(orederId));
		if (orderService.cancelOrder(order)) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}
	@RequestMapping("/confirmpay")
	@ResponseBody
	public boolean confirmpay(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
		boolean isSuccess=false;
		String orederId = request.getParameter("orderId");
		Orders order = new Orders();
		order.setOrdState(4);
		order.setId(Integer.valueOf(orederId));
		if (orderService.updateOrder(order)) {
			isSuccess=true;
		}
		return isSuccess;
	}
	@RequestMapping(value = "/wechat_notify")
	@ResponseBody
	public String successtapy(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
		PayCallback callback = null;
		try {
			Map<String, String> resultPay = WePay.getCallbackParams(request);
			if (resultPay.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
				// String orderId = resultPay.get("out_trade_no");
				String nonce_str = resultPay.get("nonce_str");
				logger.info("nonce_str=" + nonce_str);
				Orders order = new Orders();
				order.setNonceStr(nonce_str);
				order = orderService.selectOneOrder(order);
				if(order.getOrdState() != 2){
					SmsDemo.sendSms(order.getOrdNum());
				}
				logger.info("wechat_notify orderid=" + order.getId());
				order.setOrdState(2);
				orderService.updateOrder(order);
				if (orderService.updateOrder(order)) {
					callback = new PayCallback();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageUtil.PayCallbackToXml(callback);
	}

	@RequestMapping("/finishpay")
	@ResponseBody
	public boolean finishpay(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
		boolean flag = false;
		String orederId = request.getParameter("orderId");
		String userName = request.getParameter("userName");
		String userPhone = request.getParameter("userPhone");
		String userAddress = request.getParameter("userAddress");
		String area = request.getParameter("userArea");
		String userPostal = request.getParameter("userPostal");
		Orders order = new Orders();
		order.setUserPhone(userPhone);
		order.setUserAddress(userAddress);
		order.setUserPostal(userPostal);
		order.setUserName(userName);
		order.setUserPostal(area);
		order.setId(Integer.valueOf(orederId));
		logger.info("finishpay orderid=" + orederId);
		if (orderService.updateOrder(order)) {
			logger.info("finishpay order success");
			List<MapOrderProduct> listOrderPro = mapOrderProductService
					.selectMapOrderProductByOrdId(Integer.valueOf(orederId));
			for (int i = 0; i < listOrderPro.size(); i++) {
				MapOrderProduct maporder = listOrderPro.get(i);
				Product product = new Product();
				product.setId(maporder.getProId());
				Product pro = (Product) productService.selectOne(product);
				pro.setProSum(pro.getProSum() - maporder.getProCount());
				product.setSelNumber(pro.getSelNumber() + maporder.getProCount());
				productService.updateProduct(pro);
				flag = true;
			}

		} else {
			flag = false;
			logger.info("finishpay order fail");
		}
		return flag;
	}


}
