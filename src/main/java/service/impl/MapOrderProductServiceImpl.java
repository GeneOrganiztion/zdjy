package service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dto.OrderAndProductDTO;
import po.MapOrderProduct;
import service.MapOrderProductService;
import Mapper.MapOrderProductMapper;

@Transactional
@Service
public class MapOrderProductServiceImpl implements MapOrderProductService {
	private static final Logger logger = LoggerFactory.getLogger(MapOrderProductServiceImpl.class);
	@Autowired
	private MapOrderProductMapper mapOrderProductMapper;

	public List<OrderAndProductDTO> selectOderAndProductByOrderId(Integer id) {
		// TODO Auto-generated method stub
		return mapOrderProductMapper.selectOderAndProductByOrderId(id);
	}

	public MapOrderProduct selectMapOrderProductById(Integer mapOrderProductId){
		MapOrderProduct mapOrderProduct = new MapOrderProduct();
		mapOrderProduct.setId(mapOrderProductId);
		mapOrderProduct.setIsdelete(false);
		return mapOrderProductMapper.selectOne(mapOrderProduct);

	}
	public boolean updateMapOrderProduct(MapOrderProduct mapOrderProduct){
		try {
			mapOrderProductMapper.updateByPrimaryKey(mapOrderProduct);
		} catch (Exception e) {
			logger.error("updateMapOrderProduct error:" + e);
			return false;
		}
		return true;

	}



	public List<MapOrderProduct> selectMapOrderProductByOrdId(Integer oderId) {
		MapOrderProduct mapOrderProduct = new MapOrderProduct();
		mapOrderProduct.setOrdId(oderId);
		mapOrderProduct.setIsdelete(false);
		return mapOrderProductMapper.select(mapOrderProduct);
	}

	public boolean saveMapOderPro(MapOrderProduct mapOrderProduct){
		Date data=new Date();
		mapOrderProduct.setIsdelete(false);
		mapOrderProduct.setCreateTime(data);
		mapOrderProduct.setLastModifiedTime(data);
		try {
			mapOrderProductMapper.insertUseGeneratedKeys(mapOrderProduct);
		} catch (Exception e) {
			logger.error("saveMapOderPro error:" + e);
			return false;
		}
		return true;
	}
}
