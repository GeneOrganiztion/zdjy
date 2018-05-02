package service.impl;

import Mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import po.User;
import service.UserService;

import java.util.Date;
@Transactional
@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserMapper UserMapper;


	public User select(User user) throws Exception {
		user.setIsdelete(false);
		return (User)UserMapper.selectOne(user);
	}

	public int insertUser(User user) throws Exception {
		user.setIsdelete(false);
		user.setCreateTime(new Date());
		user.setLastModifiedTime(new Date());
		int newuserid=-1;
		try{
			UserMapper.insertUseGeneratedKeys(user);
			newuserid= user.getId();
		}catch(Exception e){
			logger.info("insertUser"+e);
		}
		return newuserid;
	}

	public boolean updateUser(User user) throws Exception {
		boolean flag=false;
		user.setLastModifiedTime(new Date());
		try{
			UserMapper.updateByPrimaryKeySelective(user);
			flag=true;
		}catch(Exception e){
			logger.info("updateUser "+e);
		}
		return flag;
	}
}
