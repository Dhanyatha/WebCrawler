package Test;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import au.com.bytecode.opencsv.CSVReader;


public class BeforeRerun
{

	public static void main(String[] args) 
	{   
		try
		{
		
		File map=new File("F:\\crawl backup","mapping.csv");
	    File edge_list=new File("F:\\crawl backup","edge_list.csv");
	    CSVReader reader1 = new CSVReader(new FileReader("F:\\mapping.csv"));
		CSVReader reader2 = new CSVReader(new FileReader("F:\\edge_list.csv"));
	    FileWriter writer2=new FileWriter(edge_list,true);
	    FileWriter writer1=new FileWriter(map,true);
	    
		
		updateMapFileForRerun(reader1, writer1);
        System.out.println("Writing map file is complete");
		updateEdgeListForRerun(reader2, writer2);
		reader1.close();
		reader2.close();
		writer1.close();
		writer2.close();
		System.out.print("Writing to Edge list is complete");
		}
		
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

	/**
	 * @param reader2
	 * @param writer2
	 * @throws IOException
	 */
	public static void updateEdgeListForRerun(CSVReader reader2,
			FileWriter writer2) throws IOException {
		String[] row;
		while( ((row = reader2.readNext()) != null)) 
		{
			Long from=Long.parseLong(row[0]);
		    Long to=Long.parseLong(row[1]);

		    from++;
		    to++;

		    
		    writer2.write(from.toString());
		    writer2.write(',');
          	writer2.write(to.toString());
          	writer2.write('\n');

          	writer2.flush();
          	
		}
	}

	/**
	 * @param reader1
	 * @param writer1
	 * @throws IOException
	 */
	public static void updateMapFileForRerun(CSVReader reader1,
			FileWriter writer1) throws IOException {
		String[] row;
		while( ((row = reader1.readNext()) != null)) 
		{
			int first=Integer.parseInt(row[0]);
			String second=row[1];
			first++;
			String first_string= String.valueOf(first);
		    writer1.write(first_string);
		    writer1.write(',');
          	writer1.write(second);
          	writer1.write('\n');

          	writer1.flush();
       
          	
		}
	}
}