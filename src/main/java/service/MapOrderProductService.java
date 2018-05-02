package service;

import java.util.List;

import dto.OrderAndProductDTO;
import po.MapOrderProduct;

public interface MapOrderProductService {
	
	public List<OrderAndProductDTO> selectOderAndProductByOrderId(Integer id);

	public MapOrderProduct selectMapOrderProductById(Integer mapOrderProductId);

	public boolean updateMapOrderProduct(MapOrderProduct mapOrderProduct);

	public List<MapOrderProduct>  selectMapOrderProductByOrdId(Integer oderId);

	public boolean saveMapOderPro(MapOrderProduct mapOrderProduct);
}
