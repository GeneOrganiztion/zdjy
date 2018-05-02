package Mapper;

import java.util.List;
import java.util.Map;

import po.Classify;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ClassifyMapper extends Mapper<Classify>, MySqlMapper<Classify>{
	
	public List<Classify> selectClassifyParams(Map<String, Object> map);
    
	
}