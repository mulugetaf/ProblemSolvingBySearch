import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 
 * @author fanta
 *
 * Dfbnb works like limit DFS 
 * Meaning, given any partial solution, 
 * the algorithm gives a lower bound on the cost of 
 * each completion of a partial solution.
It
 */
public class DFBnB {
	static int count = 1;
	static boolean is_Open_List = false;
	static Boolean is_with_time = false;

	/**
	 * 
	 * @param start : start state
	 * @param goal : goal sate
	 * @return path
	 */
	public static String dfbnb(Node start, String[][] goal) {
		long start_time = System.currentTimeMillis();
		long end_time = 0;
		Stack<Node> stack = new Stack<Node>();
		HashMap<Integer, Node> h=new HashMap<>();
		String ans = "";
		start.fn = start.estimated_total_cost(start, goal );
		stack.add(start);
		h.put(start.hashCode(), start);
		int t =Integer.MAX_VALUE;

		while(!stack.isEmpty()) {
			Node n =stack.pop(); 

			if (n.isVisited) h.remove(n.hashCode());

			else {
				n.isVisited=true;
				stack.add(n);

				//create all nodes
				List<Node> allowed_operator	 = Node.State.get_allow_Operators(n, goal);
				List<Node> sub_list = new ArrayList<>();
				//sort all nodes
				Collections.sort(allowed_operator);

				for(Node g : allowed_operator) {
					count++;
					g.parent=n;

					Node tmp = h.get(g.hashCode());
					if(g.fn>=t)
					{
						if(allowed_operator.indexOf(g)!=-1) {
							sub_list = allowed_operator.subList(0, allowed_operator.indexOf(g));
							allowed_operator =  sub_list;
						}
						else break;
					}
					else if(h.containsKey(g.hashCode()) && g.isVisited)
					{

						List<Node> temp = new ArrayList<Node>();
						for(Node x : allowed_operator)
						{
							if(!x.equals(g)) temp.add(x);
						}
						allowed_operator = temp;
					}

					else if(h.containsKey(g.hashCode()) && !g.isVisited)
					{
						if(tmp.fn<=g.fn)
						{

							List<Node> temp = new ArrayList<Node>();
							for(Node x : allowed_operator)
							{
								if(!x.equals(g)) temp.add(x);
							}
							allowed_operator = temp;

						}
						else
						{

							stack.remove(tmp);
							h.remove(tmp.hashCode());
						}
					}
					else if(Node.equal(g.game, goal)) 
					{
						t = g.fn;
						ans = Node.get_state_path(g, start);
						ans+="\nNum: "+count;
						ans+="\nCost: "+g.cost;
						if(is_with_time) 
						{
							end_time =System.currentTimeMillis();
							float sec = (end_time - start_time) / 1000F;
							ans+="\n" +sec + " seconds";
						}

						if(allowed_operator.indexOf(g)!=-1) {
							sub_list = allowed_operator.subList(0, allowed_operator.indexOf(g));
							allowed_operator =  sub_list;
						}
						else break;

					}
				}


				if (allowed_operator.size() > 0) {
					Collections.reverse(allowed_operator);
					for (Node node : allowed_operator) {
						stack.push(node);
						h.put(node.hashCode(),node);

					}

				}
			}

		}

		return ans;

	}
	public  void withOpen(boolean open)
	{
		DFBnB.is_Open_List =  open;
	}
}
