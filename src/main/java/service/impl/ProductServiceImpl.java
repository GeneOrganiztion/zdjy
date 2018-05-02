package service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import po.Product;
import service.ProductService;
import Mapper.ProductMapper;
@Transactional
@Service
public class ProductServiceImpl implements ProductService{

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private ProductMapper productMapper;
	
	public List<Product> selectProductByParms(Product product) {
		product.setIsdelete(false);
		product.setProOnline(true);
		return productMapper.select(product);
	}

	public List<Product> selectbyClassify(Product product) {
		product.setIsdelete(false);
		product.setProOnline(true);
		return productMapper.selectProductAndImage(product);
	}

	public Product phoneSelectProAndOneAssess(Product product) {
		product.setIsdelete(false);
		product.setProOnline(true);
		return productMapper.selectProductAndImage(product).get(0);
	}

	public int saveProduct(Product Product) throws Exception {
		Product.setIsdelete(false);
		Product.setCreateTime(new Date());
		Product.setLastModifiedTime(new Date());
		int newproductid=-1;
		try{
			productMapper.insertUseGeneratedKeys(Product);
			newproductid=Product.getId();
			logger.info("newproductid="+newproductid);
		}catch(Exception e){
			logger.info("productMappersave"+e);
		}
		return newproductid;
	}
	
	public boolean updateProduct(Product Product) throws Exception {
		boolean flag=false;
		try{
			productMapper.updateByPrimaryKeySelective(Product);
			flag=true;
		}catch(Exception e){
			logger.info("productMappersave"+e);
		}
		return flag;
	}
	
	public PageInfo selectProductListByParams(Map map) throws Exception {
		PageHelper.startPage((Integer)map.get("pageNo"),(Integer)map.get("rowCount"));
		List<Product> list = productMapper.selectProductByParams(map);
	    PageInfo page = new PageInfo(list);
		return page;
	}
	
	public boolean deleteProductIds(List ids) throws Exception {
		try {
			productMapper.deleteProductByIds(ids);
		} catch (Exception e) {
			logger.error("AdminServiceImpl deleteAdminByIds error:" + e);
			return false;
		}
		return true;
	}
	
	public Product selectOne(Product Product) throws Exception {
		Product.setIsdelete(false);
		return productMapper.selectOne(Product);
	}
	
	public List<Product> findProductByParms(Map<String, Object> map){
		List<Product> list = productMapper.selectProductByParams(map);
		return list;
	}

}
