package service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import po.Classify;

public interface ClassifyService {
	
	public List<Classify> selectAllClassify();
	
	public PageInfo<Classify> selectClassifyParams(Map<String, Object> map);
	
	public Integer insertOneClassifyReturnId(Classify cls);
	
	public Classify selectOneClassify(Classify cls);
	
	public boolean delOneClassifyById(Classify cls);
	
	public int delClassify(Classify cls);
	
	public boolean updateClassify(Classify cls);
}
