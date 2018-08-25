package project4;

import java.util.ArrayList;

public class MyHashTable {
	public static void main(String[] args) {
				
}
	ArrayList<String> mytable[]=new ArrayList[100];
	static int spaceTaken=0;
	public void insert(String s)
	{
		if(spaceTaken>mytable.length/2)
			mytable=rehashMethod(mytable);
		
		if(mytable[myHashFunc(s,mytable.length)]==null)
		{
			mytable[myHashFunc(s,mytable.length)]=new ArrayList();
			mytable[myHashFunc(s,mytable.length)].add(s);	
			spaceTaken++;
		}
		else
		{	
			if(mytable[myHashFunc(s,mytable.length)].indexOf(s)==-1)
			{
				mytable[myHashFunc(s,mytable.length)].add(s);
			}
		}
	}

	public static int myHashFunc(String key, int tableSize){
		int value = 0;

		for(int i=0;i<key.length();i++)	
			value=value+(key.charAt(i)*((i^3)+(i*11)*(i*4)));
		
		value = value%tableSize;
		return value;
	}
		
	public static ArrayList<String>[] rehashMethod(ArrayList table[])
	{
		int k=table.length;
		ArrayList<String> newtable[]=new ArrayList[2*k];
		
		for(int m=0;m<table.length;m++)
		{
			if(table[m]!=null)
			{
				for(int n=0;n<table[m].size();n++)
				{
					ArrayList<String> x = new ArrayList<String>();
					x=table[m];
					if(newtable[myHashFunc(x.get(n),newtable.length)]==null)
					{
						newtable[myHashFunc(x.get(n),newtable.length)]=new ArrayList();
						newtable[myHashFunc(x.get(n),newtable.length)].add(x.get(n));
					}
					else
					{
						if(newtable[myHashFunc(x.get(n),newtable.length)].indexOf(x.get(n))==-1)
							newtable[myHashFunc(x.get(n),newtable.length)].add(x.get(n));
					}
				}
			}
		}
		return newtable;
	}

	public ArrayList<String> get(String str) {
		ArrayList<String> list=mytable[myHashFunc(str,mytable.length)];
		return list;
	}
}
