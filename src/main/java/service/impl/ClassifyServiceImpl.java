package service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import Mapper.ClassifyMapper;
import Mapper.ProductMapper;
import po.Classify;
import po.Product;
import service.ClassifyService;

@Transactional
@Service
public class ClassifyServiceImpl implements ClassifyService {

	private static final Logger logger = LoggerFactory
			.getLogger(ClassifyServiceImpl.class);

	@Autowired
	private ClassifyMapper classifyMapper;
	@Autowired
	private ProductMapper productMapper;

	public List<Classify> selectAllClassify() {
		List<Classify> list = new ArrayList<Classify>();
		try {
			Classify classify = new Classify();
			classify.setIsdelete(false);
			list = classifyMapper.select(classify);
		} catch (Exception e) {
			logger.error("selectAllClassify error:" + e);
		}
		return list;
	}

	public PageInfo<Classify> selectClassifyParams(Map<String, Object> map) {
		PageHelper.startPage((Integer) map.get("pageNo"),
				(Integer) map.get("rowCount"));
		List<Classify> list = new ArrayList<Classify>();
		try {
			list = classifyMapper.selectClassifyParams(map);
		} catch (Exception e) {
			logger.error("selectClassifyParams error:" + e);
		}
		PageInfo<Classify> pageInfo = new PageInfo<Classify>(list);
		return pageInfo;
	}

	public Integer insertOneClassifyReturnId(Classify cls) {
		classifyMapper.insertUseGeneratedKeys(cls);
		return cls.getClassifyId();
	}

	public Classify selectOneClassify(Classify cls) {
		cls.setIsdelete(false);
		return classifyMapper.selectOne(cls);
	}

	public boolean delOneClassifyById(Classify cls) {
		try {
			classifyMapper.delete(cls);
		} catch (Exception e) {
			logger.error("delOneClassifyById error:" + e);
			return false;
		}
		return true;
	}

	public int delClassify(Classify cls) {
		cls.setIsdelete(true);
		int statcode = 0;
		try {
			/*
			 * Product product = new Product();
			 * product.setClassifyId(cls.getClassifyId());
			 * product.setProOnline(true); product.setIsdelete(true);
			 */
			// 删除分类会删除分类下的商品
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("classifyId", cls.getClassifyId());
			productMapper.deleteByClassifyKey1(map);
			classifyMapper.updateByPrimaryKeySelective(cls);
		} catch (Exception e) {
			logger.info("classifyMapperdel.error=" + e);
			statcode = 1;
			throw new RuntimeException();
		}
		return statcode;
	}

	public boolean updateClassify(Classify cls) {
		try {
			classifyMapper.updateByPrimaryKeySelective(cls);
		} catch (Exception e) {
			logger.info("classifyMappersave.update=" + e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
