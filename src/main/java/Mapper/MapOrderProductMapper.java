package Mapper;

import java.util.List;

import dto.OrderAndProductDTO;
import po.MapOrderProduct;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface MapOrderProductMapper extends Mapper<MapOrderProduct>,MySqlMapper<MapOrderProduct> {
    /*int deleteByPrimaryKey(MapOrderProductKey key);

    int insert(MapOrderProduct record);

    int insertSelective(MapOrderProduct record);

    MapOrderProduct selectByPrimaryKey(MapOrderProductKey key);

    int updateByPrimaryKeySelective(MapOrderProduct record);

    int updateByPrimaryKey(MapOrderProduct record);*/
	
	public List<OrderAndProductDTO> selectOderAndProductByOrderId(Integer id);
}