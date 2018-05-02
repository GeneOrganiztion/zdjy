package service;

import java.util.Map;

import com.github.pagehelper.PageInfo;

import po.Assess;

public interface AssessService {
	
	public PageInfo<Assess> selectAssessByParams(Map<String, Object> map);
	
	public Assess selectOneAssessByProId(Map<String, Object> map);
	
	public Integer totalAssessByProId(Integer proId);

	public Integer saveAssessReturnId(Assess assess) throws Exception;

	public boolean saveAssess(Assess assess) throws Exception;

	public void deleteAssess(String ids) throws Exception;

	public Assess selectAssessById(Integer id) throws Exception;

	public void updateAssess(Assess assess) throws Exception;
}
