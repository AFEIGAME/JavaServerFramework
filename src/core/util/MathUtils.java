package core.util;

import java.util.ArrayList;
import java.util.List;

import core.log.LogService;

public class MathUtils {
	 /// <summary>
    /// �Զ�����ʣ�������ش���ֵ
    /// </summary>
    /// <param name="probability">������ʵ����飬����ֵ���Ҫ����100����[60,30,10]</param>
    /// <param name="value">��������������ͬ�����飬����������ض�Ӧ��ֵ</param>
    /// <returns></returns>
    public static Object ProbabilityRandom(int[] probability, Object[] value)
    {
        int r = RandomUtils.Range(0, 100);
        int low = 0;
        for (int i = 0; i < probability.length; i++)
        {

            if (i > 0)
                low += probability[i - 1];
            int top = low + probability[i];

            //Debug.Log("low :" + low + " top :" + top + " r :" + r);
            if (r >= low && r < top)
                return value[i];
        }

        return null;

    }
    public static <T> T ProbabilityRandom(List<Integer> probability, List<T> value)
    {
        int r = RandomUtils.Range(0, 100);
        int low = 0;
        for (int i = 0; i < probability.size(); i++)
        {

            if (i > 0)
                low += probability.get( i - 1);
            int top = low + probability.get(i);

            //Debug.Log("low :" + low + " top :" + top + " r :" + r);
            if (r >= low && r < top)
                return  value.get(i);
        }

        return null;

    }
    //��װȨ��������ݣ�������������Ƿ��List���Ƴ���(returnRandomListIndex: ���ص�һ������ķ��ص�Index)
    public static <T> T ProbabilityRandomListValue(List<Integer> probability, List<ArrayList<T>> value,boolean isReturnRemoveFromList,Integer returnRandomListIndex) throws Exception
    {
    	if (probability.size()!=value.size())
    	{
    		throw new Exception("ProbabilityRandomListValue ���ʺ�ֵ��Ŀ��ͬ");
		}
        for (int i = 0; i < value.size(); i++)
        {
        	List<T> tempList= value.get(i);
			if (tempList.size()<=0)
			{
				value.remove(i);
				probability.remove(i);
				i--;
			}
		}
        
        if (value.size()==0)
        {
			return null;
		}
        
       List<T> list=  WeightProbabilityRandom(probability, value);
       returnRandomListIndex = value.indexOf(list);
       
       int r = RandomUtils.Range(0, list.size());
       T t =list.get(r);
       if (isReturnRemoveFromList)
       {
    	   list.remove(t);
       }
        return t;

    }
    /// <summary>
    /// �Զ�����ʣ�������ش���ֵ
    /// </summary>
    /// <param name="probability">������ʵ����飬����ֵ���Ҫ����100����[60,30,10]</param>
    /// <param name="value">��������������ͬ�����飬����������ض�Ӧ��ֵ</param>
    /// <returns></returns>
    public static Object ProbabilityRandom(FixRandom random, int[] probability, Object[] value)
    {
        int r = random.Range(0, 100);
        int low = 0;
        for (int i = 0; i < probability.length; i++)
        {

            if (i > 0)
                low += probability[i - 1];
            int top = low + probability[i];

            //Debug.Log("low :" + low + " top :" + top + " r :" + r);
            if (r >= low && r < top)
                return value[i];
        }

        return null;

    }
    
	 /// <summary>
    /// �Զ���Ȩ�أ�������ش���ֵ
    /// </summary>
    /// <param name="probability">����Ȩ�ص����飬����ֵ���Ҫ����0����[60,30,10]</param>
    /// <param name="value">��Ȩ�����������ͬ�����飬����������ض�Ӧ��ֵ</param>
    /// <returns></returns>
    public static Object WeightProbabilityRandom(int[] probability, Object[] value)
    {
    	int max=0;
    	 for (int i = 0; i < probability.length; i++)
         {
    		 max+=probability[i];
         }
    	
        int r = RandomUtils.Range(0, max);
        int low = 0;
        for (int i = 0; i < probability.length; i++)
        {

            if (i > 0)
                low += probability[i - 1];
            int top = low + probability[i];

            //Debug.Log("low :" + low + " top :" + top + " r :" + r);
            if (r >= low && r < top)
                return value[i];
        }

        return null;

    }
	 /// <summary>
    /// �Զ���Ȩ�أ�������ش���ֵ
    /// </summary>
    /// <param name="probability">����Ȩ�ص����飬����ֵ���Ҫ����0����[60,30,10]</param>
    /// <param name="value">��Ȩ�����������ͬ�����飬����������ض�Ӧ��ֵ</param>
    /// <returns></returns>
    public static <T> T WeightProbabilityRandom(FixRandom random,List<Integer> probability, List<T> value)
    {
    	int max=0;
    	 for (int i = 0; i < probability.size(); i++)
         {
    		 max+=probability.get(i);
         }
    	
        int r = random.Range(0, max);
        int low = 0;
        for (int i = 0; i < probability.size(); i++)
        {

            if (i > 0)
                low += probability.get(i-1);
            int top = low + probability.get(i);

            //Debug.Log("low :" + low + " top :" + top + " r :" + r);
            if (r >= low && r < top)
                return  value.get(i);
        }

        return null;

    }
    /// <summary>
    /// �Զ���Ȩ�أ�������ش���ֵ
    /// </summary>
    /// <param name="probability">����Ȩ�ص����飬����ֵ���Ҫ����0����[60,30,10]</param>
    /// <param name="value">��Ȩ�����������ͬ�����飬����������ض�Ӧ��ֵ</param>
    /// <returns></returns>
    public static <T> T WeightProbabilityRandom(List<Integer> probability, List<T> value)
    {
    	if (probability.size() != value.size())
    	{
			LogService.Error("", "MathUtils.WeightProbabilityRandom �������󣺲�����������ȣ�"+probability.size()+" : "+value.size());
			return null;
		}
    	int max=0;
    	 for (int i = 0; i < probability.size(); i++)
         {
    		 max+=probability.get(i);
         }
    	
        int r = RandomUtils.Range(0, max);
        int low = 0;
        for (int i = 0; i < probability.size(); i++)
        {

            if (i > 0)
                low += probability.get(i-1);
            int top = low + probability.get(i);

            //Debug.Log("low :" + low + " top :" + top + " r :" + r);
            if (r >= low && r < top)
                return  value.get(i);
        }

        return null;

    }
    
  
}
