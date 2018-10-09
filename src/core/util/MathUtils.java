package core.util;

import java.util.List;

public class MathUtils {
	 /// <summary>
    /// �Զ�����ʣ�������ش���ֵ
    /// </summary>
    /// <param name="probability">������ʵ����飬����ֵ���Ҫ����100����[60,30,10]</param>
    /// <param name="value">��������������ͬ�����飬����������ض�Ӧ��ֵ</param>
    /// <returns></returns>
    public static Object ProbabilityRandom(int[] probability, Object[] value)
    {
        int r = RandomUtils.Range(0, 101);
        int low = 0;
        for (int i = 0; i < probability.length; i++)
        {

            if (i > 0)
                low += probability[i - 1];
            int top = low + probability[i];

            //Debug.Log("low :" + low + " top :" + top + " r :" + r);
            if (r >= low && r <= top)
                return value[i];
        }

        return null;

    }
    public static <T> T ProbabilityRandom(List<Integer> probability, List<T> value)
    {
        int r = RandomUtils.Range(0, 101);
        int low = 0;
        for (int i = 0; i < probability.size(); i++)
        {

            if (i > 0)
                low += probability.get( i - 1);
            int top = low + probability.get(i);

            //Debug.Log("low :" + low + " top :" + top + " r :" + r);
            if (r >= low && r <= top)
                return  value.get(i);
        }

        return null;

    }
    /// <summary>
    /// �Զ�����ʣ�������ش���ֵ
    /// </summary>
    /// <param name="probability">������ʵ����飬����ֵ���Ҫ����100����[60,30,10]</param>
    /// <param name="value">��������������ͬ�����飬����������ض�Ӧ��ֵ</param>
    /// <returns></returns>
    public static Object ProbabilityRandom(FixRandom random, int[] probability, Object[] value)
    {
        int r = random.Range(0, 101);
        int low = 0;
        for (int i = 0; i < probability.length; i++)
        {

            if (i > 0)
                low += probability[i - 1];
            int top = low + probability[i];

            //Debug.Log("low :" + low + " top :" + top + " r :" + r);
            if (r >= low && r <= top)
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
            if (r >= low && r <= top)
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
    public static <T> T WeightProbabilityRandom(List<Integer> probability, List<T> value)
    {
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
            if (r >= low && r <= top)
                return  value.get(i);
        }

        return null;

    }
    
  
}
