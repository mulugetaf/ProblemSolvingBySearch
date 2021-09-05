import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * 
 * @author fanta
 * 
 * A* combines greedy h(n) and uniform cost g(n)
 * 
 */
public class Astar {
	static boolean is_Open_List = false;
	static Boolean is_with_time = false;
	static int count = 1;
	static int iteration;
	static Hashtable<Integer, Node> open = new Hashtable<>();
	static Hashtable<Integer, Node> close = new Hashtable<>();

	/**
	 * 
	 * @param start : start state
	 * @param goal : goal state
	 * @return : string containing :
	 * 			1 shortest path(by cost) from start to goal state
	 * 			2 number of vertices created
	 * 			3 cost it take to reach the goal 
	 * 			4 running time (seconds)
	 * 			5 operators marked by the number of the blocks
	 */
	public static String A(Node start, String[][] goal)
	{
		long start_time = System.currentTimeMillis();
		long end_time = 0;
		String ans = "";
		//use priority queue to order nodes by their f(n) 
		PriorityQueue<Node> A_Queue = new PriorityQueue<Node>();
		start.fn = start.estimated_total_cost(start, goal);
		A_Queue.add(start);
		open.put(start.hashCode(),start);

		while(!A_Queue.isEmpty()) {
			Node n = A_Queue.remove();
			open.remove(n.hashCode(),n);

			//print open list
			if(is_Open_List)
			{
				Iterator<Node> itr = open.values().iterator();
				while(itr.hasNext()){
					Node tmp = itr.next();
					String[] str = Node.convert1D(tmp.game);
					System.out.print(Arrays.toString(str) + " , ") ;
				}
				System.out.println();
			}

			if(Node.equal(n.game, goal)) 
			{
				count++;
				ans+=Node.get_state_path(n, start);
				ans+="\nNum: "+count;
				ans+="\nCost: "+n.cost;
				//print running time
				if(is_with_time) 
				{
					end_time =System.currentTimeMillis();
					float sec = (end_time - start_time) / 1000F;
					ans+="\n" +sec + " seconds";
				}
				return ans;
			}
			close.put(n.hashCode(), n);
			//get all allow operators
			ArrayList<Node> allow_Operators = (ArrayList<Node>) Node.State.get_allow_Operators(n, goal);
			for (Node operator : allow_Operators) {
				count++;
				operator.parent = n;
				operator.isVisited = true;
				operator.fn = operator.estimated_total_cost(operator, goal);

				//check if it is already exposed 
				if(!close.containsKey(operator.hashCode()) 
						&& !open.containsKey(operator.hashCode()))
				{
					// if it is not exposed put to open list and queue 
					open.put(operator.hashCode(), operator);
					A_Queue.add(operator);
				}
				else{
					// if it is already exposed compare new and old by cost
					//and remove the  higher cost
					Node temp = open.get(operator.hashCode());
					if((temp != null) && operator.cost < temp.cost) {
						open.replace(operator.hashCode(),temp, operator);
						//remove the  higher cost
						A_Queue.remove(temp);
						A_Queue.add(operator);
					}
				}
			}

		}
		if(is_with_time) 
		{
			end_time =System.currentTimeMillis();
			float sec = (end_time - start_time) / 1000F;
			ans+="\n" +sec + " seconds";
		}
		return "no path";
	}
	public  void withOpen(boolean open)
	{
		Astar.is_Open_List =  open;
	}
}
