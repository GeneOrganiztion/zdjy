package service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Mapper.HomePageMapper;
import po.HomePage;
import service.HomePageService;

@Transactional
@Service
public class HomePageServiceImpl implements HomePageService {

	private static final Logger logger = LoggerFactory.getLogger(HomePageServiceImpl.class);
	@Autowired
	private HomePageMapper hompageMapper;
	
	public List<HomePage> selectAll(HomePage hop) {
		// TODO Auto-generated method stub
		hop.setIsdelete(false);
		return hompageMapper.select(hop);
	}

}
