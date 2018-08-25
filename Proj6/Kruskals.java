package project6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Kruskals {
	int numOfVertices;
	List<Edge> edges;

	public static class Vertex{
		int num;
		String name;

		public Vertex(String name)					
		{
			this.name = name;
			this.num = 0;
		}		
	}
	
	public static class Edge implements Comparable<Edge>{
		Vertex a,b;
		private int weight;
		
		public Edge(Vertex a, Vertex b)				
		{
			this.a = a;
			this.b = b;
		}
		
		//CompareTo to compare between edges
		@Override
		public int compareTo(Edge e1) {
			if(this.getweight()>e1.getweight())
				return 1;
			else if (this.getweight() < e1.getweight())
				return -1;
			else
				return 0;
		}
		
		public int getweight(){
			return weight;
		}
	}
	

	public ArrayList<Edge> kruskals( List<Edge> edges, int VerticesNumber)
	{
		List<Edge> subtree = new ArrayList<>();
		DisjSets ds = new DisjSets(VerticesNumber);
		PriorityQueue<Edge> pq = new PriorityQueue<Edge>(edges);
		
		while(subtree.size() != VerticesNumber-1)
		{
			Edge edg = pq.poll();					
			int groupa = ds.find(edg.a.num);		
			int groupb = ds.find(edg.b.num);		
			
			if(groupa != groupb)					
			{
				subtree.add(edg);
				ds.union(groupa, groupb);
			}
		}
		return (ArrayList<Edge>) subtree;			//returning list that contains the tree edges of the resulting minimum spanning tree
	}
	
	public static void main(String[] args) throws Exception {
		List<Edge> edges = new ArrayList<>();			        //list of initial edges
		List<Edge> subtree = new ArrayList<>();				//list of resulting tree edges						
		List<Vertex> vertexList = new ArrayList<>();	        //list of vertices
		Integer numOfVertices = 0;
		Map<String, Integer> vertexNum = new ConcurrentHashMap<String, Integer>();
		
		String line = null;
		String fileName = "C:/Users/Sreevatsa H V/Desktop/proj6/assn9_data.csv";
		
		try {
			FileReader file=new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(file);

			while((line = bufferedReader.readLine()) != null) 
			{
				String g[] = line.split(",");
				Vertex a;
				
				if(!(vertexNum.containsKey(g[0])))		              //check if the vertex is read
				{											              //add to the vertex list and assign a number
					vertexNum.put(g[0], numOfVertices++);		
					a = new Vertex(g[0]);
					a.num = numOfVertices-1;
					vertexList.add(a);
				}
				else										
				{
					int verVal = vertexNum.get(g[0]);
					a = vertexList.get(verVal);
				}
				for(int i = 1; i < g.length; i++)		
				{
					Vertex b;	
					if(!(vertexNum.containsKey(g[i])))		
					{											
						vertexNum.put(g[i], numOfVertices++);
						b = new Vertex(g[i]);
						b.num = numOfVertices-1;
						vertexList.add(b);
					}
					else									
					{
						int verVal = vertexNum.get(g[i]);
						b = vertexList.get(verVal);
					}
					
					Edge edg = new Edge(a, b);				
					edg.weight = Integer.parseInt(g[++i]);	
					edges.add(edg);							
				}
			}
			bufferedReader.close(); 
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
				
		Kruskals kru = new Kruskals();
		subtree = kru.kruskals(edges, numOfVertices);
		int total = 0;
		
		for(Edge e: subtree)
		{
			//int total;
			System.out.println("Distance from "+e.a.name + " to " + e.b.name + " is "+e.getweight());
			total =total+ e.getweight();
		}
		System.out.println("Sum of distances in the resulting Minimum Spanning Tree using Kruskal's method = "+total);
	}
}

