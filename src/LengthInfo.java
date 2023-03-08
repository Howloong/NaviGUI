import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class LengthInfo implements Serializable
{
    private double[][] length;
    private StringBuilder stringBuilder;
    private final ArrayList arrayList;

    public LengthInfo() {
        arrayList = new ArrayList();
    }


    public void init()//初始化函数，当调用方判断文件为空时就需要新建邻接矩阵并初始化
    {
        length = new double[100][100];
        for (int i = 0; i < 100; i++)
        {
            for (int j = 0; j < 100; j++)
            {
                length[i][j] = Integer.MAX_VALUE;
                if (i == j)
                {
                    length[i][j] = 0;
                }
            }
        }
    }

    public void editLength(int from, int to, double weight)
    {
        length[from][to] = weight;
        length[to][from] = weight;
    }

    public void deleteLength(int from, int to)
    {
        length[from][to] = -1;
    }

    public double getMin(int from, int to, TreeMap treeMap)
    {
        int i, j, k, m = 0;
        double D[] = new double[100];
        int P[] = new int[100];
        int fin[] = new int[100];
        i = from;
        double min = Double.MAX_VALUE;
        for (j = 0; j < 100; j++)
        {
            D[j] = length[i][j];
            P[j] = i;
        }
        D[i] = 0;
        fin[i] = 1;
        P[i] = -1;
        for (j = 0; j < 100; j++)
        {
            if (i == j)
            {
                continue;
            }
            else
            {
                for (k = 0; k < 100; k++)
                {
                    if (fin[k] == 0 && D[k] < min)
                    {
                        m = k;
                        min = D[k];
                    }
                }
            }
        }
        fin[m] = 1;
        for (k = 0; k < 100; k++)
        {
            if (fin[k] == 0 && D[m] + length[m][k] < D[k])
            {
                D[k] = D[m] + length[m][k];
                P[k] = m;
            }
        }

        getInfo(treeMap);
        stringBuilder = new StringBuilder();
        stringBuilder.append(arrayList.get(to));
        int pre=P[to];
        while (pre != -1)
        {
            stringBuilder.append(" --- ").append(arrayList.get(pre));
            pre = P[pre];
        }
        return D[to];


    }

    public void getInfo(TreeMap treeMap)
    {
        Set set = treeMap.keySet();
        arrayList.addAll(set);
    }

    public double getLength(int from, int to)
    {
        return length[from][to];
    }

    public String getStringBuilder()
    {
        return stringBuilder.toString();
    }

    public double[][] getLength()
    {
        return length;
    }
}
