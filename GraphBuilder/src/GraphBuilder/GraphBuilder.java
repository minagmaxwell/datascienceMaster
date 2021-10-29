package GraphBuilder;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList; 


class Graph {
	public boolean adjMatrix[][];
	String nodeNames[];  
	Node nodes[];
	int numNodes = 10;
	
	public Graph(int numNodes) {
		this.numNodes = numNodes;
		nodes = new Node[numNodes];
	    adjMatrix = new boolean[numNodes][numNodes];
	}
  
	void addNode(String name) {
		Node newNode = new Node(name);
		int k = Integer.parseInt(name);
		nodes[k] = newNode;
	}
	
	void removeNode(String name) {
		for(int i=0;i<nodes.length;i++) {
			if(nodes[i] == null) {continue;}
			if(nodes[i].name==name) {
				nodes[i] = null;
				for(int j=0;j<nodes.length;j++) {
					adjMatrix[i][j]= false;
				}
			}
		}
	}
	
	Node getNode(String name) {
		for(int i=0;i<nodes.length;i++) {
			if(nodes[i] == null) {continue;}
			if(nodes[i].name==name) {
				return nodes[i];
			}
		}
		return null;
	}
	
	void addEdge(String node1, String node2) {
		int node1_idx, node2_idx ;
		node1_idx = node2_idx = -1;
		for(int i=0;i<nodes.length;i++) {
			if(nodes[i] == null) {continue;}
			if(nodes[i].name.equals(node1)) { node1_idx = i;}
			if(nodes[i].name.equals(node2)) { node2_idx = i;}
		}
		
		if (node1_idx != -1 && node2_idx != -1) {
			adjMatrix[node1_idx][node2_idx] = true;
//			System.out.println("Edge added");
		}
	}
	
	boolean hasEdge(String node1, String node2) {
		int node1_idx, node2_idx ;
		node1_idx = node2_idx = -1;
		for(int i=0;i<nodes.length;i++) {
			if(nodes[i] == null) {continue;}
			if(nodes[i].name.equals(node1)) { node1_idx = i;}
			if(nodes[i].name.equals(node2)) { node2_idx = i;}
		}
		if (node1_idx != -1 && node2_idx != -1) {
			return adjMatrix[node1_idx][node2_idx];
		}
		else {
			return false;
		}
	}
	
	void removeEdge(String node1, String node2) {
		int node1_idx, node2_idx ;
		node1_idx = node2_idx = -1;
		for(int i=0;i<nodes.length;i++) {
			if(nodes[i] == null) {continue;}
			if(nodes[i].name.equals(node1)) { node1_idx = i;}
			if(nodes[i].name.equals(node2)) { node2_idx = i;}
		}
		if (node1_idx != -1 && node2_idx != -1) {
			adjMatrix[node1_idx][node2_idx] = false;
			adjMatrix[node2_idx][node1_idx] = false;
		}
	}
	
	void printStructure() {
//		for(int i=1;i<nodes.length;i++) {
//			if(nodes[i] == null) {continue;}
//			System.out.println(nodes[i].name);
//		}
		for(int i=1;i<nodes.length;i++) {
			if(nodes[i] == null) {continue;}
			
			for(int j=1;j<nodes.length;j++) {
//				System.out.println(nodes[i].name);
//				System.out.println(adjMatrix[i][j]);
				if(adjMatrix[i][j]==true) {
					System.out.println(nodes[i].name + "-" + nodes[j].name);
				}
			}
		}
	}
	
	int countShortestPath(String node1, String node2) {
		int node1_idx = Integer.parseInt(node1);
		int node2_idx = Integer.parseInt(node2);
		
		
		boolean[] visited = new boolean[nodes.length];
		for (int i=1;i<nodes.length;i++) { visited[i]=false;}
		
		int[] d = new int[nodes.length];
		for (int i=1;i<nodes.length;i++) { d[i]=0;}
		
		Queue<Integer> visitQ = new LinkedList<>();
		visitQ.add(node1_idx);
		visited[node1_idx]=true;
		while(visitQ.size()>0) {
			int f = visitQ.remove();
			
			for(int i=1;i<nodes.length;i++) { 
		
				if(adjMatrix[f][i]==true || adjMatrix[i][f]==true) {
					if(visited[i] == false) {	
//						System.out.println("visiting new node");
						d[i] = d[f]+1;
						visited[i]=true;
						visitQ.add(i);
					}
				}
			}
		}
		return d[node2_idx];
	}
}

class Node {
	public String name;
	Node(String name){
		this.name = name;
	}
}

// Any additional classes here
public class GraphBuilder {
    public static void main(String[] args){
    	//System.out.println("Working Directory = " + System.getProperty("user.dir"));
    	Graph g = new Graph(1300);
    	//System.out.println(g.numNodes);
		for(int i=1;i<=1299;i++) {
			g.addNode(Integer.toString(i));
		}

    	File f = new File("web-google.mtx");
    	int lineCounter = 0;
    	try {
    	    Scanner sc = new Scanner(f);
    	    while( sc.hasNextLine() ) {
    	    	lineCounter++;
    	        String line = sc.nextLine();
    	    	if(lineCounter<=2) {continue;}
    	        String[] tokens = line.split("\\s");
    	        
    	        g.addEdge(tokens[0], tokens[1]);
    	        
    	    }

    	} catch (FileNotFoundException ex) {
    	    System.out.println("File "+f+" not found.");
    	}
    	
//	    g.printStructure();
	    int s = g.countShortestPath("1", "1299");
	    System.out.println(s);
	    
//	    int inCounter = 0;
//	    int outCounter = 0;
//	    for(int i=1;i<=1299;i++) {
//	    	if(g.adjMatrix[333][i]==true) {outCounter++;}
//	    	if(g.adjMatrix[i][333]==true) {inCounter++;}
//	    	
//		}
//	    System.out.println(inCounter);
//	    System.out.println(outCounter);
		
	}
}