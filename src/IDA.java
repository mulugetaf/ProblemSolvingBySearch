import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Stack;

/**
 * 
 * @author fanta
*The cost threshold increases in each iteration to
*the total cost of the lowest-cost node that was 
*pruned during the previous iteration.
*The algorithm terminated when a goal state is generated 
*whose total cost does not exceed the current threshold
*/
public class IDA {
	static Hashtable<Integer, Node> open = new Hashtable<>();
	static Hashtable<Integer, Node> close = new Hashtable<>();
	static boolean is_Open_List = false;
	static Boolean is_with_time = false;
	static int count = 1;

	public static String IDAstar(Node start, String[][] goal) {
		long start_time = System.currentTimeMillis();
		long end_time = 0;
		String ans="";
		Node root = start;
		Stack<Node> L = new Stack<Node>();

		double threshold = start.estimated_total_cost(root, goal);

		while(threshold != Integer.MAX_VALUE) {
			int minF=Integer.MAX_VALUE;
			start.isVisited=false;
			L.push(root);
			open.put(root.hashCode(),root);


			while(!L.isEmpty()) 
			{
				//remove front
				Node n= L.pop();
				count++;

				//print open_list
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

				//if mark as out
				if(n.isVisited) 
				{
					open.remove(n.hashCode());
				}
				else 
				{
					//mark as out
					n.isVisited = true;
					L.push(n);
					ArrayList<Node> allow_Operators = Node.State.get_allow_Operators(n, goal);
					// For each allowed operator on n
					for (Node operator : allow_Operators) 
					{
						count++;

						//System.out.println(count);
						operator.parent = n;         
						if(operator.fn > threshold) 
						{
							minF= (int) Math.min(minF,operator.fn);
							//continue with the next operator
							continue;
						}

						Node tmp=open.get(operator.hashCode());

						//if h continue tmp = g and tmp mark as out
						if(open.containsKey(operator.hashCode()) && tmp.isVisited)
						{
							//continue with the next operator
							continue;
						}
						//if h continue tmp = g and tmp not mark as out
						if(open.containsKey(operator.hashCode()) && !tmp.isVisited) 
						{
							if(tmp.fn > operator.fn)
							{
								L.remove(tmp);
								open.remove(operator.hashCode());
							}
							else
							{
								//continue with the next operator
								continue;
							}
						}
						//If goal(g) then return path(g) //all the out nodes in L
						if(Node.equal(operator.game, goal)) {
							count++;
							ans+=Node.get_state_path(operator, start);
							ans+="\nNum: "+count;
							ans+="\nCost: "+operator.cost;
							if(is_with_time) 
							{
								end_time =System.currentTimeMillis();
								float sec = (end_time - start_time) / 1000F;
								ans+="\n" +sec + " seconds";
							}
							return ans;
						}

						L.push(operator);
						open.put(start.hashCode(),operator);
					}

				}

			}

			threshold = minF;

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
		IDA.is_Open_List =  open;
	}
}


