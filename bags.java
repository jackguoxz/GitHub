package Lint;

import java.util.Arrays;

public class bags {
	
	public static int max(int []w,int []v,int size)
	{
		int [][]dp=new int[size+1][w.length+1];		
		for(int i=1;i<=size;i++)
		{
			for(int j=1;j<w.length;j++)
			{
				if(i>=w[j])
				{
					dp[i][j]=Math.max(dp[i][j-1],dp[i-w[j]][j-1]+v[j]);
					
					//dp[j][i]=Math.max(dp[j-1][i], dp[j-1][i-w[j]]+v[j]);
					//f[i][v]=max{f[i-1][v],f[i-1][v-c[i]]+w[i]}
				}else
				{
					dp[i][j]=dp[i][j-1];
				}
				//System.out.println(dp[i][j]);
			}
		}
		
		int max=Integer.MIN_VALUE;
		for(int i=1;i<=size;i++)
		{
			for(int j=1;j<w.length;j++)
			{
				if(dp[i][j]>max)
					max=dp[i][j];
			}
		}
		
		return max;
		
	}

	public static int getMax(int []w,int []v, int size)
	{
		int [][]dp=new int[w.length+1][size+1];
		//dp[0]=0;
		//int j=0;
		//int []visited=new int[w.length];
		
		
		for (int j = 1; j < w.length; j++) {
			for (int i = 1; i <= size; i++) {
{
					if (i >= w[j]) {					

					dp[j][i] = Math.max(dp[j - 1][i], dp[j - 1][i - w[j]]
							+ v[j]);

				} else {
					dp[j][i] = dp[j - 1][i];
					// System.out.println(dp[j][i]);
				}
			}

			}
		}
		
		int max=Integer.MIN_VALUE;
		
		for(int j=1;j<w.length;j++)
			for (int i = 1; i <= size; i++){
			 if(dp[j][i]>max)
				 max=dp[j][i];
			//System.out.print(dp[j][i]+"-");
			if(i==size)
				System.out.println();
		}
		//System.out.println();	
		//max=dp[i];
		return max;
		
	}
	
	public static void getZeroOnePackHelp(int w,int v, int size,int[]f)
	{

		for(int i=size;i>=w;i--)
		{
			//System.out.print(i-w+":"+f[i-w]+v+"#");
			f[i]=Math.max(f[i], f[i-w]+v);	
			//System.out.print(i+":"+f[i]+"-");
			
		}
		//System.out.println();
		//System.out.println(w+"-"+v);
		
		
	}
	
	public static void completePackHelp(int w,int v,int size, int[]f)
	{
		
		for(int i=w;i<=size;i++)
		{
			f[i]=Math.max(f[i], f[i-w]+v);	
			//System.out.print(i+":"+f[i]+"-");
			
		}
		//System.out.println();

	}
	
	public static int CompletePack(int []w,int []v, int size)
	{
		int max=Integer.MIN_VALUE;
		int []f=new int[size+1];
		for(int i=1;i<w.length;i++)
		{
			completePackHelp(w[i],v[i],size,f);
			
		}
		for(int i=1;i<=size;i++)
		{
			if(f[i]>max)
			{
				max=f[i];
				
			}
			
		}
		return max;
		
	}
	
	public static int ZeroOnePack(int []w,int []v, int size)
	{
		int max=Integer.MIN_VALUE;
		int []f=new int[size+1];
		for(int i=1;i<w.length;i++)
			getZeroOnePackHelp(w[i],v[i],size,f);
		for(int i=1;i<=size;i++)
		{
			if(f[i]>max)
			{
				max=f[i];
				
			}
			
		}
		return max;
		
	}
	
	public static int MultiplePack(int []w,int []v, int []t, int size)
	{
		
		int max=Integer.MIN_VALUE;
		int []f=new int[size+1];
		for(int i=1;i<w.length;i++)
		{
			
			for(int j=1;j<=t[i];j++)
			{
				//System.out.println(t[i]);
				getZeroOnePackHelp(w[i],v[i],size,f);
				//System.out.println(Arrays.toString(f)+"-"+j);
			}
		}
		
		for(int i=1;i<=size;i++)
		{
			if(f[i]>max)
			{
				max=f[i];
				
			}
			
		}
		return max;
	}
	//[0, 0, 6, 6, 12, 12, 18, 18, 24, 24, 30, 30, 36, 36, 42, 42, 48, 48, 54, 54, 54]-9
	//[0, 0, 6, 6, 12, 12, 18, 18, 24, 24, 30, 30, 36, 36, 42, 42, 48, 48, 54, 54, 54]*2
	//
	public static int MultiplePackTwo(int []w,int []v,int []t, int size)
	{
		int max=Integer.MIN_VALUE;
		int []f=new int[size+1];
		int k=1;
		int amount;
		for(int i=1;i<w.length;i++)
		{
			amount=t[i];
			k=1;
			if(t[i]*w[i]>=size)
			{
				//System.out.println(t[i]);
				//completePackHelp(w[i],v[i],size,t[i],f);
				completePackHelp(w[i],v[i],size,f);
			}else
			{
				/*
				while(k<amount)
				{
					getZeroOnePackHelp(k*w[i],k*v[i],size,f);
					System.out.println(Arrays.toString(f)+"-"+k);
					//System.out.println("--------");
					amount=amount-k;
					k=k*12;
				}*/
				getZeroOnePackHelp(amount*w[i],amount*v[i],size,f);
				//System.out.println(Arrays.toString(f)+"*"+amount);
				
			}
		}
		for(int i=1;i<=size;i++)
		{
			if(f[i]>max)
			{
				max=f[i];
				
			}
			
		}
		return max;
	
		
	}
	public static void main(String []args)
	{
		//System.out.print("s");
		int []w={0,2,2,6,5,4};
		int []v={0,6,3,5,4,6};
		int []t={0,9,2,1,1,2};
		
		
		int []w1={0,10, 20, 30};
		int []v1={0,60, 100, 120};
		//int []v2={0,6,5,4};
		
		
		int []w2={0,1,2,3,4,5,6,7,8};
		int []v2={0,3,5,8,9,10,17,17,20};

		int size=50;
		//System.out.println(getMax(w,v,size));
		//System.out.println(max(w,v,size));
		System.out.println(CompletePack(w2,v2,8));
		System.out.println(ZeroOnePack(w1,v1,size));
		System.out.println(CompletePack(w1,v1,size));
		System.out.println(MultiplePack(w,v,t,size));
		System.out.println(MultiplePackTwo(w,v,t,size));
		
		
	}
}
