import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * @author fanta
 * BFS Algorithm Treating the graph as a tree and scanning top down.
 * BFS continues until a goal node is generated.
 * If a goal exists in the tree BFS will find a shortest path to a goal.
 * open_list : we add all  vertex's that we need expose. to check if vertex is already exposed
 * 				it take O(1)
 * close_list :  we add all  vertex's that already exposed. to not expose it again
 */
public class BFS {
	static int count = 1;
	static boolean is_Open_List = false;
	static Boolean is_with_time = false;
	static Queue<Node> bfs_queue = new LinkedList<>(); 
	static Hashtable<Integer, Node> Open_List = new Hashtable<>();
	static Hashtable<Integer, Node> Close_List = new Hashtable<>();

	/**
	 * 
	 * @param start : start state
	 * @param goal : goal sate
	 * @return : shortest path to a goal if it exist
	 * 			 "No Path" if path not exist	
	 */
	public static String bfs(Node start, String[][] goal) {

		long start_time = System.currentTimeMillis();
		long end_time = 0;
		String ans = "";
		ArrayList<Node> allow_Operators;
		bfs_queue.add(start);
		Open_List.put(start.hashCode(),start);
		while(!bfs_queue.isEmpty()) {
			Node n = bfs_queue.remove();
			if(is_Open_List)
			{
		
				Iterator<Node> itr = Open_List.values().iterator();

				while(itr.hasNext()){
					Node tmp = itr.next();
					String[] str = Node.convert1D(tmp.game);
					System.out.print(Arrays.toString(str) + " , ") ;
				}
				System.out.println();
			}
			Open_List.remove(n.hashCode(),n);
			Close_List.put(n.hashCode(), n);

			allow_Operators = Node.State.get_allow_Operators(n, goal);
			//For each allowed operator on n if n goal return path
			//else we check if it is already exposed 
			for(Node operator: allow_Operators) {

				operator.parent=n;
				operator.isVisited = true;

				if(Node.equal(operator.game, goal))
				{

					count++;	
					ans+=Node.get_state_path(operator, start);
					ans+="\nNum: "+count;
					ans+="\nCost: "+operator.cost;
					System.err.println(ans);
					if(is_with_time) 
					{
						end_time =System.currentTimeMillis();
						float sec = (end_time - start_time) / 1000F;
						ans+="\n" +sec + " seconds";
					}
					return ans;
				}
				//check if it is already exposed 
				else if(!Open_List.containsKey(operator.hashCode()) &&
						!Close_List.containsKey(operator.hashCode())) 
				{
					count++;
					bfs_queue.add(operator);
					Open_List.put(operator.hashCode(),operator);
				}

			}
		}
		if(is_with_time) 
		{
			end_time =System.currentTimeMillis();
			float sec = (end_time - start_time) / 1000F;
			ans+="\n" +sec + " seconds";
		}

		return "No Path";


	}
	public  void withOpen(boolean open)
	{
		BFS.is_Open_List =  open;
	}
	public static void print_open()
	{

	}

}
