package service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Mapper.CartMapper;
import po.Cart;
import service.CartService;

@Transactional
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartMapper cartMapper;
	
	public Cart selectCart(Cart cart) {
		cart.setIsdelete(false);
		return cartMapper.selectByUser(cart);
	}
}
