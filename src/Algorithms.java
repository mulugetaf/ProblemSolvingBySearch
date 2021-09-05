/**
 * 
 * @author fanta
 *
 */
public class Algorithms {
	public static int NODECOUNT = 1;
	public static int CHECK = 0;
	public static String algo_name;
	public static Boolean is_with_time = false;
	public static Boolean with_open_list = false;

	public static String run(String[][] arr, String[][] goal)
	{
		Node current = new Node(arr);
		
		switch (algo_name) {
		case "BFS":
			if(is_with_time)BFS.is_with_time = true;
			if(with_open_list)BFS.is_Open_List = true;
			return BFS.bfs(current, goal);
		case "Astar":
			if(is_with_time)Astar.is_with_time = true;
			if(with_open_list)Astar.is_Open_List = true;
			return Astar.A(current, goal);
		case "DFBnB":
			if(is_with_time)DFBnB.is_with_time = true;
			if(with_open_list)DFBnB.is_Open_List = true;
			return DFBnB.dfbnb(current, goal);
		case "DFID":
			if(is_with_time)DFID.is_with_time = true;
			if(with_open_list)DFID.is_Open_List = true;
			return DFID.dfid(current, goal);
		case "IDA":
			if(is_with_time)IDA.is_with_time = true;
			if(with_open_list)IDA.is_Open_List = true;
			return IDA.IDAstar(current, goal);

		default:
			return null;
		}
		
		
	}
}
