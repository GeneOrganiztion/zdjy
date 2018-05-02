package service;

import po.User;

/**
 * Created by i-zhangshengchen on 2016/11/25.
 * @param <T>
 */
public interface UserService<T> {

	public T select(User user) throws Exception;
    
  /*  public List<T> selectall()  throws Exception;*/
    
    public int insertUser(User user)throws Exception;
    
    public boolean updateUser(User user)throws Exception;

}
