import java.util.*;
import java.lang.reflect.*;
class MyAction
{
	private String name;
	private String pass;
//name��setter��getter����
	public String getName()
	{
		return this.name;
	}
	public void setName(String name)
	{
		this.name=name;
	}
//pass��setter��getter����
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
	/*�����û�����
	struts.xml��
	<action name="abc" class="MyAction" method="regist"/>
		<result name="success">welcome.jsp</result>
		<result name="�����">������ͼ</result>
	</action>
	*/



	public static void main(String[] args) //throws Exception
	{
		//�����filter�յ�abc.action������ͨ������struts.xml
		String clazzProp="MyAction";

		//���䣺��ȡAction������Ӧ��Class����
		Class actionClazz = null;
		try {
			actionClazz = Class.forName(clazzProp);
		} catch (ClassNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		//�÷��䴴��Action��ʵ��
		Object actionInst = null;
		try {
			actionInst = actionClazz.newInstance();
		} catch (InstantiationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IllegalAccessException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}//Ҫ��Action�����޲����Ĺ��������еĻ��ͻᱨ��

		//��һ��Map��ģ���������
		//Struts 2����Filter���������
		//Map<String ,String > paraMap = request.getParameterMap();//Ӧ�õõ��������������-����ֵ ��map 

		//Ϊ��Ч��д��
		Map<String ,String >paramMap = new HashMap<>();
		paramMap.put("name","lala");
		paramMap.put("pass","lala");

		for (String paramName:paramMap.keySet())
		{
			//Name �õ������������Ӧ��setter���� 
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
			//�õ����������Ӧ��ֵ
			String paramValue = paramMap.get(paramName);

			//��actionʵ��Ϊ�����ߣ�����setter���������������ֵ��Ϊ����ֵ����
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
	//	action���õĺ����������β�
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

		if (result.equals("ĳ��result��name����ֵ"))
		{
			System.out.println("������Ӧ��������ͼ");
		}
	}
}
