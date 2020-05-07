package com.example.cneditor;

import java.util.Random;

class CreateKey
{
	private String key;
	private String result;
	
	public String GetKey(String key)
	{
		this.key = key;
		return create();
	}		
	
	public String create()
	{
			int x=0,y=0;
			key = key.toUpperCase();
			String r11 = new String();
			String r12 = new String(); 
			String r13 = new String(); 
			while(x<key.length())
			{	
				y =(int) key.charAt(x);
				if(y >= 48 && y <= 58)
				{
					r11 = r11 + (char) y;
				}
				else if(y >= 65 && y <= 90)
				{
					if(y > 77 )
					{
						y-=13;
					}
					else
					{
						y+=13;
					}
					r12 = r12 + (char) y;
				}
				else
				{
					r13 = r13 + (int) y;
				}
				x++;

			}		
			result = reduce(alpha(r12)+nconv(r11)+r13);
			return result;
	}
	
	public String alpha(String r11)
	{
		int i=0;
		String x = new String();
		while(i<r11.length())
		{
			x = x + (64 - (int) r11.charAt(i))*(-1);
			i++;
		}
		return x;
		
	}

	public long nconv(String r12)
    {
        Random rand = new Random();
        long n = Long.parseLong(r12);
        return n + rand.nextInt(1000000000);
    }

	public String reduce(String x)
	{
		System.out.println(x);
		boolean flag=true;
		int i=1,j=0;
		String last = new String();
		last = last + x.charAt(0);
		while(i<x.length())
		{
			j=0;flag = true;
			while(j<last.length())
			{
				if(x.charAt(i) == last.charAt(j))
				{
					flag  = false;
					break;
				}
				j++;
			}
			if(flag)
			{
				last = last + x.charAt(i);
			}
			i++;	
		}	
		return last;
	}
	
}

