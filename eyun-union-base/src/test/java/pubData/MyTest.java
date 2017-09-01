package pubData;

import org.junit.Test;

import com.rainsoft.union.web.account.dao.impl.AccAccountDaoImpl;

public class MyTest 
{	
	@Test
	public void test1()
	{
		AccAccountDaoImpl accAccountDaoImpl = new AccAccountDaoImpl();
		System.out.print(accAccountDaoImpl.getSqlNamespace());
	}
}
