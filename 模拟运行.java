import java.util.*;
import java.lang.reflect.*;
class MyAction
{
	private String name;
	private String pass;
//name的setter和getter方法
	public String getName()
	{
		return this.name;
	}
	public void setName(String name)
	{
		this.name=name;
	}
//pass的setter和getter方法
	public String getPass()
	{
		return this.pass;
	}
	public void setPass(String pass)
	{
		this.pass=pass;
	}

	public String regist()
	{
		System.out.println("name-----:"+name);
		System.out.println("pass-----:"+pass);
		return "success";
	}
}

public class MockFilter
{
	/*假设用户请求
	struts.xml中
	<action name="abc" class="MyAction" method="regist"/>
		<result name="success">welcome.jsp</result>
		<result name="结果名">物理视图</result>
	</action>
	*/



	public static void main(String[] args) //throws Exception
	{
		//假设该filter收到abc.action的请求，通过解析struts.xml
		String clazzProp="MyAction";

		//反射：获取Action类所对应的Class对象
		Class actionClazz = null;
		try {
			actionClazz = Class.forName(clazzProp);
		} catch (ClassNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		//用反射创建Action的实例
		Object actionInst = null;
		try {
			actionInst = actionClazz.newInstance();
		} catch (InstantiationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IllegalAccessException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}//要求Action类有无参数的构造器（有的话就会报错）

		//用一个Map来模拟请求参数
		//Struts 2核心Filter用下面代码
		//Map<String ,String > paraMap = request.getParameterMap();//应该得到所有请求参数名-参数值 的map 

		//为了效果写的
		Map<String ,String >paramMap = new HashMap<>();
		paramMap.put("name","lala");
		paramMap.put("pass","lala");

		for (String paramName:paramMap.keySet())
		{
			//Name 得到请求参数名对应的setter方法 
			Method setter = null;
			try {
				setter = actionClazz.getMethod("set" + paramName.substring(0,1).toUpperCase() 
						+ paramName.substring(1),String.class);
			} catch (NoSuchMethodException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//得到请求参数对应的值
			String paramValue = paramMap.get(paramName);

			//以action实例为调用者，调用setter方法，把请求参数值作为参数值传入
			try {
				setter.invoke(actionInst , paramValue);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	//	action调用的函数不能有形参
		Method targetMethod = null;
		try {
			targetMethod = actionClazz.getMethod("regist");
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = null;
		try {
			result = (String)targetMethod.invoke(actionInst);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (result.equals("某个result的name属性值"))
		{
			System.out.println("跳到对应的物理视图");
		}
	}
}
