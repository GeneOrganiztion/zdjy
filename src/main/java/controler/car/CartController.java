package controler.car;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import controler.base.BaseController;
import po.Cart;
import service.CartService;

@Controller
@RequestMapping(value = "/cart")
public class CartController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CartController.class);

	@Autowired
	private CartService cartService;

	@RequestMapping(value = "/phoneSelectCart")
	@ResponseBody
	public Cart phoneSelectCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Cart resultCart = new Cart();
		try {
			String userId = getParam("userId");
			Cart cart = new Cart();
			cart.setUserId(Integer.parseInt(userId));
			resultCart = cartService.selectCart(cart);
		} catch (Exception e) {
			logger.error("phoneSelectCart error:" + e);
		}
		return resultCart;
	}
}
