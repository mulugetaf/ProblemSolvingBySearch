import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Ex1 {

	public static void main(String[] args) throws IOException  {
		String s1 = ("input.txt");
		BufferedReader br = null;
		String line = "";
		String[][] start = null;
		String[][] goal = null ;
		File file = new File(s1);
		try {
			br = new BufferedReader(new FileReader(file));

			Algorithms.algo_name = br.readLine();
			if(Algorithms.algo_name.equals("A*")) Algorithms.algo_name = "Astar";
			if(Algorithms.algo_name.equals("IDA*"))Algorithms.algo_name = "IDA";
			String is_with_time = br.readLine();
			String is_open_List = br.readLine();
			if(is_with_time.equalsIgnoreCase("with time"))
			{
				Algorithms.is_with_time = true;
			}
			else {
				Algorithms.is_with_time  = false;
			}
			if(is_open_List.equalsIgnoreCase("with open"))
			{
				Algorithms.with_open_list = true;
			}
			else {
				Algorithms.with_open_list  = false;
			}
			String[] nm = br.readLine().split("x");   //mat [N][M]
			int N = Integer.parseInt(nm[0]), M = Integer.parseInt(nm[1]);
			start = new String[N][M];
			goal = new String[N][M];


			// board
			for(int i=0; i<start.length;i++){
				String[] lineNumbers = br.readLine().split(",");
				for(int j=0; j<start[0].length;j++){
					start[i][j] = lineNumbers[j];
				}
			}
			line = br.readLine();
			for(int i=0; i<goal.length;i++){
				String[] lineNumbers = br.readLine().split(",");
				for(int j=0; j<goal[0].length;j++){
					goal[i][j] = lineNumbers[j];
				}
			}

		}
		catch (IOException e) {
			System.err.println("No input file");
		} 
		//run Algorithms
		String ans = Algorithms.run( start,  goal);
		if(!Algorithms.with_open_list)
		{
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));;
				writer.write(ans);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println(ans);
		}


	}
}
