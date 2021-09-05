import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * 
 * @author fanta
 *
 */
class Node implements Comparable<Node>{

	String[][] game;
	String path ="";
	String name = "";
	Node parent = null;
	int level ;
	int index = 0;
	int cost = 0;
	int fn = 0;
	boolean isVisited = false;
	/**
	 * 
	 * @param game : current game board
	 */
	public Node(String[][] game) {
		int row = game.length;
		int col = game[0].length;
		this.game = new String[row][col];
		for (int i = 0; i < game.length; i++) {
			for (int j = 0; j < game[0].length; j++) {
				this.game[i][j] = game[i][j];
			}
		}
		this.level = index++;
	}

	/**
	 * 
	 * @param game : game board
	 * @param pat : game path
	 * @param l : level
	 * @param cost : cost
	 * @param p : parent
	 */
	public Node(String[][] game,String pat,int l, int cost, Node p) {

		int row = game.length;
		int col = game[0].length;
		this.game = new String[row][col];
		for (int i = 0; i < game.length; i++) {
			for (int j = 0; j < game[0].length; j++) {
				this.game[i][j] = game[i][j];
			}
		}
		this.path = pat;
		this.level = index++;
		this.cost = cost;
		this.parent = p;
	}

	/**
	 * deep constructor
	 * @param n : node to be copy
	 */
	public Node(Node n) {
		Node temp = new Node(n.game,n.path, n.level, n.cost, n.parent);
		this.game = temp.game;
		this.path = temp.path;
		this.level = index++;
		this.cost = n.cost;
		this.fn = n.fn;
	}

	/**
	 * f(n) = estimated total cost of path through n to goal
	 * @param goal : goal state
	 * @param n : current node
	 * @return : Evaluation f(n) where n the node
	 * 			f(n) = g + h
	 * 			g : cost so far to reach n
				h : estimated cost from n to goal	
	 */
	public int estimated_total_cost(Node n,String[][] goal)
	{
		this.fn = this.level + calculateManhattanDistance(this, goal);
		return this.fn;
	}

	/**
	 * compareTo : node comparator by fn
	 * if fn(a) = fn(b)
	 * The priority in this case Algorithm will select by there level
	 * and if level(a) = level(b)
	 * The Algorithm will select by name(left, up, right, down)
	 */
	@Override
	public int compareTo(Node o) {
		if (this.fn < o.fn) {
			return -1;
		}
		if (this.fn > o.fn) {
			return 1;
		}
		else if(this.fn==o.fn) {

			if(this.level<o.level) 
			{
				return -1;
			}
			if(this.level>o.level) 
			{
				return 1;
			}
		}
		return 0;
	}

	/**
	 * 
	 * @param s : current board
	 * @return : current board where "_" replaced to "0"
	 */
	public static String[][] MatConvert(String[][] s)
	{
		String[][] arr = new String[s.length][s[0].length];

		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s[0].length; j++) {
				if(s[i][j].equals("_")) arr[i][j] = "0";
				else arr[i][j] = s[i][j];
			}

		}
		return arr;
	}
	/**
	 * 
	 * @param n : Node n
	 * @param goal : Node goal
	 * @return : true if board o = goal
	 */
	public static boolean equal(String [][] str, String[][] goal)
	{

		for (int i = 0; i < str.length; i++) {
			for (int j = 0; j <str[0].length; j++) {
				if(!str[i][j].equals(goal[i][j])) 
				{
					return false;
				}
			}

		}
		return true;
	}
	/**
	 * 
	 * @param goal
	 * @param start
	 * @return path from star state to goal 
	 */
	public static String get_state_path(Node goal, Node start) {
		Stack<Node> ans = new Stack<Node>();
		//solutionStack.push(currentNode);
		String path="";
		ans.push(goal);
		while (!equal(goal.game,start.game)) {
			ans.push(goal.parent);
			goal=goal.parent;

		}
		while(!ans.isEmpty()) {
			Node tmp=ans.pop();
			path+=tmp.path;
		}
		path=path.substring(0,path.length()-1);
		return path;

	}

	/**
	 * Calculates sum of Manhattan distances
	 * @param n : Node to be calculated
	 * @param goal : goal state
	 * @return : Manhattan distances
	 */

	public int calculateManhattanDistance(Node current , String[][] goal)
	{
		int dist=0;
		String[][] n = MatConvert(current.game);
		goal =  MatConvert(goal);
		for(int i=0;i<goal.length;i++)
		{
			for(int j=0;j<goal[0].length;j++)
			{	
				for(int k=0;k<n.length;k++)
				{
					for(int l=0;l<n[0].length;l++)
					{
						if((n[k][l].equals(goal[i][j])) && (!n[k][l].equals("0")))
						{

							dist+=Math.abs(k-i) + Math.abs(l-j);
						}
					}

				}
			}
		}

		return dist;
	}


	//hashmap
	@Override
	public int hashCode() {
		return Arrays.deepHashCode(convert1D(this.game));
	}
	/**
	 * 
	 * @param board
	 * @return 2D array converted to 1D
	 */
	public static String[] convert1D(String[][] board)
	{
		String ans[] = new String[board.length * board[0].length];
		int k = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) { 
				ans[k]=board[i][j]; 
				k++;
			}
		}
		return ans;
	}
	public void set_all_variables(Node parent , String path, String n, int c)
	{
		this.path = path;
		this.parent = parent;
		this.level = parent.level+1;
		this.cost = parent.cost + c;
		this.name = n;
	}
	/**
	 * 
	 * @author fanta
	 * inner class operator check all allowed operators
	 * return :Node after move if it allowed
	 *  else return null
	 *
	 */
	static class State{

		/**
		 * 
		 * @param g : board state
		 * @param i : index i of blank line
		 * @param j : index j of blank line
		 * @return : current Node after moving two blocks left
		 * @return : null if move not allowed
		 * 
		 * determined that the order of creation of the vertices with a common parent would be according 
		 * to the operator who created them in order
		 * 2 blocks left, 2 blocks up, 2 blocks right, 2 blocks down. If 2 blocks cannot be moved 
		 * the order will be: Left, up , right, down, 
		 * to the empty block that is closer to the first row and then to the second empty block.
		 * if The Two blocks in the same row will have priority for actions to the left of the block.:
		 */
		/**
		 * 
		 * @param n : current node
		 * @param goal : goal state
		 * @return : ArrayList containing all allowed operators
		 */
		public static  ArrayList<Node> get_allow_Operators(Node n, String[][] goal) {
			ArrayList<Node> Allowed_Operators = new ArrayList<Node>();

			for (int i=0;i<n.game.length;i++) {
				for (int j=0;j<n.game[i].length;j++) {
					if(n.game[i][j].equals("_")) {

						//2_blocks_left
						if((i != n.game.length-1) && n.game[i+1][j].equals("_"))
						{
							Node move = two_blocks_left(n,i,j);
							if(move != null)
							{
								move.fn = move.estimated_total_cost(n, goal);
								Allowed_Operators.add(move);
							}
						}

						//2_blocks_up
						if((j != n.game[i].length-1) && n.game[i][j+1].equals("_")) 
						{
							Node move = two_blocks_up(n,i,j);
							if(move != null)
							{

								move.fn = move.estimated_total_cost(n, goal);
								Allowed_Operators.add(move);
							}
						}
						//2_blocks_right
						if((i != n.game.length-1) && n.game[i+1][j].equals("_"))

						{
							Node move = two_blocks_right(n,i,j);
							if(move != null)
							{
								move.fn = move.estimated_total_cost(n, goal);
								Allowed_Operators.add(move);
							}
						}
						////2_blocks_down
						if((j != n.game[0].length-1) && n.game[i][j+1].equals("_")) {
							Node move = two_blocks_down(n,i,j);
							if(move != null)
							{
								move.fn = move.estimated_total_cost(n, goal);
								Allowed_Operators.add(move);
							}
						}

						//1_blocks_left
						if(j!=n.game[0].length) 
						{
							Node move = one_block_left(n,i,j);
							if(move!=null)
							{
								move.fn = move.estimated_total_cost(n, goal);
								Allowed_Operators.add(move);
							}
						}

						//1_blocks_up
						if(i!=n.game.length) {
							Node move = one_block_up(n,i,j);

							if(move != null)
							{
								move.fn = move.estimated_total_cost(n, goal);
								Allowed_Operators.add(move);
							}
						}
						//1_blocks_right

						if(j!=0 && !n.game[i][j-1].equals("_")) {
							Node move = one_block_right(n,i,j);
							if(move != null)
							{
								move.fn = move.estimated_total_cost(n, goal);
								Allowed_Operators.add(move);
							}
						}

						//1_blocks_down
						if(i!=0 && !n.game[i-1][j].equals("_")) {
							Node move = one_block_down(n,i,j);
							if(move!=null)
							{
								move.fn = move.estimated_total_cost(n, goal);
								Allowed_Operators.add(move);
							}

						}
					}
				}
			}

			return Allowed_Operators;
		}

		public static Node two_blocks_left(Node g, int i, int j) {
			Node temp = null;
			if(j==g.game[i].length-1) return null;


			temp = new Node(g.game,g.path,g.level, g.cost , g.parent);
			temp.game[i][j] = temp.game[i][j+1];
			temp.game[i+1][j] = temp.game[i+1][j+1];
			temp.game[i][j+1] = "_";
			temp.game[i+1][j+1] = "_";
			String p = temp.game[i][j] + "&" + temp.game[i+1][j]  + "L-";
			temp.set_all_variables(g, p, "l",  6);
			return temp;
		}

		/**
		 * 
		 * @param g : board state
		 * @param i : index i of blank line
		 * @param j : index j of blank line
		 * @return : current Node after moving two blocks up
		 * @return : null if move not allowed
		 */
		public static Node two_blocks_up(Node g, int i, int j) {
			Node temp = null;
			if(i==0) return temp;
			temp = new Node(g.game,g.path,g.level, g.cost , g.parent);
			temp.game[i][j] = temp.game[i-1][j];
			temp.game[i][j+1] = temp.game[i-1][j+1];
			temp.game[i-1][j] = "_";
			temp.game[i-1][j+1] = "_";
			String p = temp.game[i][j]  + "&" + temp.game[i][j+1] + "D-";
			temp.set_all_variables(g, p, "d",  7);
			return temp;
		}

		/**
		 * 
		 * @param g : board state
		 * @param i : index i of blank line
		 * @param j : index j of blank line
		 * @return : current Node after moving two blocks right
		 * @return : null if move not allowed
		 */
		public static Node two_blocks_right(Node g, int i, int j) {

			Node temp = null;
			if(j==0) return temp;
			temp = new Node(g.game,g.path,g.level, g.cost , g.parent);
			temp.game[i][j] = temp.game[i][j-1];
			temp.game[i+1][j] = temp.game[i+1][j-1];
			temp.game[i][j-1] = "_";
			temp.game[i+1][j-1] = "_";
			String p = temp.game[i][j]  + "&" + temp.game[i+1][j] + "R-";
			temp.set_all_variables(g, p, "r", 6);
			return temp;
		}
		/**
		 * 
		 * @param g : board state
		 * @param i : index i of blank line
		 * @param j : index j of blank line
		 * @return : current Node after moving two blocks down
		 * @return : null if move not allowed
		 */
		public static Node two_blocks_down(Node g, int i, int j) {
			Node temp = null;
			if(i == g.game.length-1) return null;
			temp = new Node(g.game,g.path,g.level, g.cost , g.parent);
			temp.game[i][j] = temp.game[i+1][j];
			temp.game[i][j+1] = temp.game[i+1][j+1];
			temp.game[i+1][j] = "_";
			temp.game[i+1][j+1] = "_";
			String p = temp.game[i][j] + "&" + temp.game[i][j+1] + "U-";
			temp.set_all_variables(g, p, "u", 7);
			return temp;
		}

		/**
		 * 
		 * @param g : board state
		 * @param i : index i of blank line
		 * @param j : index j of blank line
		 * @return : current Node after moving two blocks left
		 * @return : null if move not allowed
		 */
		public static  Node one_block_left (Node g,int i,int j){
			Node temp = null;
			if(j==g.game[i].length-1) return temp;
			temp = new Node(g.game,g.path,g.level, g.cost , g.parent);
			temp.game[i][j] = temp.game[i][j+1];
			String p = temp.game[i][j+1] + "L-";
			temp.set_all_variables(g, p, "l", 5);
			temp.game[i][j+1] = "_";

			return temp;
		}
		/**
		 * 
		 * @param g : board state
		 * @param i : index i of blank line
		 * @param j : index j of blank line
		 * @return : current Node after moving one blocks up
		 * @return : null if move not allowed
		 */
		public static  Node  one_block_up (Node g,int i,int j){
			Node temp = null;
			if(i == g.game.length-1 ) return temp;
			temp = new Node(g.game,g.path,g.level, g.cost , g.parent);
			temp.game[i][j] = temp.game[i+1][j];
			String p = temp.game[i][j]  + "U-";
			temp.game[i+1][j] = "_";
			temp.set_all_variables(g, p, "u", 5);
			return temp;
		}
		/**
		 * 
		 * @param g : board state
		 * @param i : index i of blank line
		 * @param j : index j of blank line
		 * @return : current Node after moving one blocks down
		 * @return : null if move not allowed
		 */
		public static  Node one_block_down (Node g,int i,int j){
			Node temp = null;
			if(i==0) return temp;
			temp = new Node(g.game,g.path,g.level, g.cost , g.parent);
			temp.game[i][j] = temp.game[i-1][j];
			temp.game[i-1][j] = "_";
			String p = temp.game[i][j] + "D-";
			temp.set_all_variables(g, p, "d", 5);
			return temp;
		}
		/**
		 * 
		 * @param g : board state
		 * @param i : index i of blank line
		 * @param j : index j of blank line
		 * @return : current Node after moving one blocks right
		 * @return : null if move not allowed
		 */
		public static  Node one_block_right (Node g,int i,int j){
			Node temp = null;
			if(j==0) return temp;
			temp = new Node(g.game,g.path,g.level, g.cost , g.parent);
			temp.game[i][j] = temp.game[i][j-1];
			temp.game[i][j-1] = "_";
			String p = temp.game[i][j]+ "R-";
			temp.set_all_variables(g, p, "r", 5);
			return temp;
		}
	}
}

