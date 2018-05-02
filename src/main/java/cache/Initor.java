package cache;

import java.io.Serializable;

public interface Initor extends Serializable
{
	public void init();
	public void refresh(int type);
}	
