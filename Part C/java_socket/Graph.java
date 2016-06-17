package java_socket;
// A Java program for Bellman-Ford's single source shortest path
// algorithm.
import java.util.*;
import java.lang.*;
import java.io.*;
 
class row
{
	String start;
	String end;
	int weight;
};
// A class to represent a connected, directed and weighted graph
public class Graph
{
    // A class to represent a weighted edge in graph
    class Edge {
        String src, dest;
        int weight;
        Edge() {
            src = dest = null;
            weight = 0;
        }
    };

    static HashMap<String, Integer> hash;
    static HashMap<String, row> rec;
    int V, E;
    Edge edge[];
 
    // Creates a graph with V vertices and E edges
    Graph(int v, int e)
    {
    		
        V = v;
        E = e;
        edge = new Edge[e];
        for (int i=0; i<e; ++i)
            edge[i] = new Edge();
    }
 
    // The main function that finds shortest distances from src
    // to all other vertices using Bellman-Ford algorithm.  The
    // function also detects negative weight cycle
    void BellmanFord(Graph graph,String src)
    {
        int V = graph.V, E = graph.E;
        int dist[] = new int[V];
 
        // Step 1: Initialize distances from src to all other
        // vertices as INFINITE
        for (int i=0; i<V; ++i)
            dist[i] = Integer.MAX_VALUE;
        dist[hash.get(src)] = 0;
 
        // Step 2: Relax all edges |V| - 1 times. A simple
        // shortest path from src to any other vertex can
        // have at-most |V| - 1 edges
        for (int i=1; i<V; ++i)
        {
            for (int j=0; j<E; ++j)
            {
                int u = hash.get(graph.edge[j].src);
                int v =  hash.get( graph.edge[j].dest);
                int weight =  graph.edge[j].weight;
                if (dist[u]!=Integer.MAX_VALUE &&
                    dist[u]+weight<dist[v])
                    dist[v]=dist[u]+weight;
            }
        }
 
        // Step 3: check for negative-weight cycles.  The above
        // step guarantees shortest distances if graph doesn't
        // contain negative weight cycle. If we get a shorter
        //  path, then there is a cycle.
        for (int j=0; j<E; ++j)
        {
        	 int u = hash.get(graph.edge[j].src);
            int v =  hash.get( graph.edge[j].dest);
            int weight =  graph.edge[j].weight;
            if (dist[u]!=Integer.MAX_VALUE &&
                dist[u]+weight<dist[v])
              System.out.println("Graph contains negative weight cycle");
        }
        printArr(dist, V, src);
    }
 
    // A utility function used to print the solution
    void printArr(int dist[], int V, String src)
    {
    	PrintWriter writer;
		try {
			writer = new PrintWriter(src+".txt", "UTF-8");
	        writer.println("Vertex "+ src +" Distance from Source");
	        Iterator it;
	        it =   hash.entrySet().iterator();
	        for (int i=0; i<V; ++i)
	        {
	        	it =   hash.entrySet().iterator();
	        	while (it.hasNext()) {
		            Map.Entry pair = (Map.Entry)it.next();
		          // System.out.println(pair.getKey() + " = " + pair.getValue());
		            if((int)pair.getValue() == i)
		            	writer.println(pair.getKey()+"\t\t"+dist[i]);
		             // avoids a ConcurrentModificationException
		        }
	        	
	            
	        }
	        //it.remove();
	        writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
 
    // Driver method to test above function
    public static void main1(String change,String args)
    {
        int V = 6;  // Number of vertices in graph
        int E = 6;  // Number of edges in graph
 
        Graph graph = new Graph(V, E);
        StringTokenizer defaultTokenizer = new StringTokenizer(change);
        row temp = new row();
        rec = new HashMap<String,row>();
        int i=0,k=0;
        String a,b;
        int w;
        while (defaultTokenizer.hasMoreTokens())
        {
            a=defaultTokenizer.nextToken();
            temp.start=a;
            b=defaultTokenizer.nextToken();
            temp.end=b;
            w= Integer.parseInt(defaultTokenizer.nextToken());
            temp.weight =w;
            rec.put(a, temp);
		/*		graph.edge[i].src = a;
				graph.edge[i].dest = b;
				graph.edge[i].weight = w;
				i++;*/
        }
        
        //int i=0,k=0;
        File file=null;
        Set<String> nodes;
		try{
			file = new File(args);
			System.out.println(args);
		}catch(Exception e){
			e.printStackTrace();
		}
		Scanner scan;
		try {
			scan = new Scanner(file);
			hash = new HashMap<String,Integer>();
			nodes= new HashSet<String>(); 
			
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				String[] components = line.split("[ ]+");
			
				graph.edge[i].src = components[0];
			   graph.edge[i].dest = components[1];
			   if(rec.get(components[0]).end == components[1])
			   {
				   if(rec.get(components[0]).weight < Integer.parseInt(components[2]))
					   graph.edge[i].weight = rec.get(components[0]).weight;
				   else
					   graph.edge[i].weight = Integer.parseInt(components[2]);
			   }
			   else
				   graph.edge[i].weight = Integer.parseInt(components[2]);
		     
				
				if(!nodes.contains(graph.edge[i].src)){
					hash.put(graph.edge[i].src,k++);
					nodes.add(graph.edge[i].src);
				}
				if(!nodes.contains(graph.edge[i].dest)){
					hash.put(graph.edge[i].dest,k++);
					nodes.add(graph.edge[i].dest);
				}
				i++;
			}
			scan.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		} catch (Exception e){
			e.printStackTrace();
			System.exit(0);
		}
        
     
        graph.BellmanFord(graph, "H1");
        graph.BellmanFord(graph, "H2");
        graph.BellmanFord(graph, "R1");
        graph.BellmanFord(graph, "R2");
        graph.BellmanFord(graph, "R3");
        graph.BellmanFord(graph, "R4");
        
    }
}