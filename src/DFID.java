import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author fanta
 *
 */
public class DFID {

	static boolean is_Open_List = false;
	static Boolean is_with_time = false;
	static Node root ;
	static int count =1;
	static long start_time = System.currentTimeMillis();
	static long end_time = 0;

	/**
	 * 
	 * @param start : start state
	 * @param goal : goal state
	 * @return shortest path to the goal
	 * 
	 * DFID combine the best features of BFS and DFS
	 * first we performs DFS to depth one then we start over executing DFS to depth two
	 * continue to run DFS until solution found.
	 */
	public static String dfid(Node start, String[][] goal) 
	{
		start_time = System.currentTimeMillis();
		end_time = 0;
		String result="";

		root = start;

		for(int depth=0;depth<Integer.MAX_VALUE;depth++) {

			HashMap<Integer,Node> H = new HashMap<>();
			//call recursive limited_dfs
			result=limited_dfs(start,goal, depth,H);

			//If result not cutoff then return result
			if(!result.equals("cutoff")) return result;
		}

		String res="no path";
		res+="\nNum:"+count;
		// print running time
		if(is_with_time) 
		{
			end_time =System.currentTimeMillis();
			float sec = (end_time - start_time) / 1000F;
			res+="\n" +sec + " seconds";
		}
		return res; 

	}
	public static String limited_dfs(Node n , String [][] goal, int limit, HashMap <Integer,Node> H) 
	{
		String result="";
		boolean isCutoff=true;


		if(Node.equal(n.game, goal))
		{
			result=Node.get_state_path(n, root);
			result+="\nNum:"+count;
			result+="\nCost: "+n.cost;

			// print running time if we found goal
			if(is_with_time) 
			{
				end_time =System.currentTimeMillis();
				float sec = (end_time - start_time) / 1000F;
				result+="\n" +sec + " seconds";
			}
			return result;
		}

		else if(limit == 0) return "cutoff";
		else
		{
			H.put(n.hashCode(),n);
			isCutoff = false;
			List<Node> allowed_operator	 = Node.State.get_allow_Operators(n, goal);
			//For each allowed operator on n
			for (Node operator : allowed_operator) {
				count++;
				operator.parent = n;
				operator.isVisited = true;
				if(H.containsKey(operator.hashCode()))
				{

					continue;
				}
				result = limited_dfs(operator, goal, limit-1, H);
				if(result.equals("cutoff"))
				{
					isCutoff = true;
				}

				else if(!result.equals("fail"))
				{
					return result;
				}
			}//end for

			H.remove(n.hashCode())	;
			if(isCutoff)
			{
				return "cutoff";
			}
			else
			{
				return "fail";
			}
		}
	}
	public  void withOpen(boolean open)
	{
		DFID.is_Open_List =  open;
	}
}
