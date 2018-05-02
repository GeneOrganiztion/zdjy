package Mapper;

import java.util.List;
import java.util.Map;

import po.Assess;
import po.Orders;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface AssessMapper extends Mapper<Assess> {
	
	public List<Assess> selectAssessByParams(Map<String, Object> map);
	
	int saveAssessReturnId(Assess assess);

}