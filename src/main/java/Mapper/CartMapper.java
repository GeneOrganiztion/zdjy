package Mapper;

import po.Cart;
import tk.mybatis.mapper.common.Mapper;

public interface CartMapper extends Mapper<Cart>{
	
	public Cart selectByUser(Cart cart);
    /*int deleteByPrimaryKey(CartKey key);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(CartKey key);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);*/
}