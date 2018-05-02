package service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import Mapper.AssessMapper;
import po.Assess;
import po.Image;
import service.AssessService;
import service.ImageService;
import utils.ST;

@Transactional
@Service
public class AssessServiceImpl implements AssessService {

	@Autowired
	private AssessMapper assessMapper;
	@Autowired
	private ImageService imageService;

	public PageInfo<Assess> selectAssessByParams(Map<String, Object> map) {
		PageHelper.startPage((Integer) map.get("pageNo"), (Integer) map.get("rowCount"));
		List<Assess> list = assessMapper.selectAssessByParams(map);
		PageInfo<Assess> page = new PageInfo<Assess>(list);
		return page;
	}

	public Assess selectOneAssessByProId(Map<String, Object> map) {
		
		return assessMapper.selectAssessByParams(map).get(0);
	}

	public Integer totalAssessByProId(Integer proId) {
		Assess assess = new Assess();
		assess.setProId(proId);
		assess.setIsdelete(false);
		return assessMapper.selectCount(assess);
	}

	public Integer saveAssessReturnId(Assess assess) throws Exception{
		assess.setIsdelete(false);
		assess.setLastModifiedTime(new Date());
		assess.setCreateTime(new Date());
		assessMapper.saveAssessReturnId(assess);
		return assess.getAssessId();
	}

	public boolean saveAssess(Assess assess) throws Exception {
		boolean bl = true;
		try {
			assessMapper.insertSelective(assess);
		} catch (Exception e) {
			bl = false;
			throw e;
		}
		return bl;
	}

	public void deleteAssess(String ids) throws Exception {
		String[] strArr = ids.split(",");
		for(String idStr: strArr){
			Integer id = ST.parseInt(idStr);
			Assess assess = new Assess();
			assess.setAssessId(id);
			//删除评价的图片
			List<Image> list = imageService.findImageByAssessId(id);
			for(Image image: list){
				imageService.delImageById(image.getImageId());
			}
			assessMapper.delete(assess);
		}
	}

	public Assess selectAssessById(Integer id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("assessId", id);
		List<Assess> list = assessMapper.selectAssessByParams(map);
		return list.get(0);
	}

	public void updateAssess(Assess assess) throws Exception {
		assessMapper.updateByPrimaryKeySelective(assess);
	}

}
