package Test;
//Packages for setting up HTTP Connection
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//Packages for File operations
import au.com.bytecode.opencsv.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

//Packages to implement BFS 
import java.util.ArrayList;

//Packages to support Open Authorization for Twitter
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

public class WebCrawler
{
	  String CONSUMER_SECRET = "Enter your consumer secret here";
	  String CONSUMER_KEY = "Enter your consumer key here";
	  String ACCESS_TOKEN ="Enter your access token here";
	  String ACCESS_TOKEN_SECRET ="Enter your access secret here";
	  String REQUEST_TOKEN_URL = "https://api.twitter.com/oauth/request_token";
	  String AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize";
	  String ACCESS_TOKEN_URL = "https://api.twitter.com/oauth/access_token";
	  OAuthConsumer Consumer;
	  static ArrayList<String> Queue = new ArrayList<String>();
      static int dead_end_pointer=-1;
      private static int visited_node_count=-1; 
      static int next_seed_node_index=0;
      File map_file=new File("F:\\","edge_list.csv"); //update the file path and edge_list file name
      FileWriter writer,identity_tracker;
      File id_track=new File("F:\\","mapping.csv"); //update the file path and file name for the file that maps node_id in the graph to actual twitter ID
      public boolean restart=true;
      
	  
	public OAuthConsumer GetConsumer()
  {
      OAuthConsumer consu = new DefaultOAuthConsumer(CONSUMER_KEY,CONSUMER_SECRET);
      consu.setTokenWithSecret(ACCESS_TOKEN,ACCESS_TOKEN_SECRET);
      return consu;
  }
	
public String[] GetFriends(String id_str)
    {
	
	String S="";
	String[] friends=null;
	try 
    {
	   System.out.println("Processing friends of user = "+ id_str);
        HttpURLConnection connectionObj = setupConnection(id_str);
                if(connectionObj.getResponseCode()==429)
                {
                	    try
                	    {
                    	System.out.println("Too many requests! Switching to sleep mode");
                        Thread.sleep(900000);
                	    }
                	    catch(Exception e)
                	    {
                	    	e.printStackTrace();
                	    }
                          
                }
                if(connectionObj.getResponseCode()==401 || connectionObj.getResponseCode()== 403)
                {
                	System.out.println("Bypassing a forbidden node");
                	return null;
                }
                if(connectionObj.getResponseCode()==404)
                {
                	System.out.println("Resource Not found! Passing on to the next one");
                	return null;
                }
                if(connectionObj.getResponseCode()==500 || connectionObj.getResponseCode()==502 || connectionObj.getResponseCode()==503)
                {
                	System.out.println("Response code is "+ connectionObj.getResponseCode());
                	Thread.sleep(900000);
                	return null;
                }
                if(connectionObj.getResponseCode()==200)
                {
                	S = fetchTwitterData(connectionObj);
                if(S.isEmpty())
                { 
                	System.out.println("No friends");
                	return null;
                }
                else
                {
                friends=S.split(",");
                }
                
                }   
                connectionObj.disconnect(); 
                
        } //end of try
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }
	return friends;
    }

/**
 * @param connectionObj
 * @return
 * @throws IOException
 */
public String fetchTwitterData(HttpURLConnection connectionObj)
		throws IOException {
	String S;
	InputStreamReader in = new InputStreamReader((InputStream) connectionObj.getContent());
	BufferedReader buffer_reader = new BufferedReader(in);
	StringBuffer buff = new StringBuffer();
	String  tempo= "";
            while((tempo=buffer_reader.readLine())!=null)
            {
            
               buff.append(tempo);
            } 
            String O= buff.toString();
            S=O.substring(8, O.indexOf("],"));
            buffer_reader.close();
	return S;
}

/**
 * @param id_str
 * @return
 * @throws MalformedURLException
 * @throws IOException
 * @throws OAuthMessageSignerException
 * @throws OAuthExpectationFailedException
 * @throws OAuthCommunicationException
 */
public HttpURLConnection setupConnection(String id_str)
		throws MalformedURLException, IOException, OAuthMessageSignerException,
		OAuthExpectationFailedException, OAuthCommunicationException {
	URL url = new URL("https://api.twitter.com/1.1/friends/ids.json?user_id="+id_str);
	HttpURLConnection connectionObj = (HttpURLConnection) url.openConnection();
	        connectionObj.setReadTimeout(500000);
	        Consumer.sign(connectionObj);
	        connectionObj.connect();
	return connectionObj;
}

    public void BFS(String[] friends,String seed_user_id)
    /*********************Breadth First Search algorithm*********************************/
    {      
    	ArrayList<String> FriendList= new ArrayList<String>();
    	Boolean is_exists; // to check whether an element exists in the queue already
    	
           //converting long type to string type
          /**friends is a JSONArray containing friends data returned for the temp_source_node**/
          /***Below for loop puts the JSONArray contents to a ArrayList, FriendList***/
          try
          {   
              
        	  for(String a: friends)
              {
        		String friend_node=a;
        		FriendList.add(friend_node);
        		//System.out.println("The value of a is: "+friend_node);
        		if(FriendList.size()>999)
        			break;
              }

        /** Check to see if element from Friend list already exists in the queue **/
          
          
          System.out.println("The FriendList size is" + FriendList.size());
          is_exists=Queue.contains(seed_user_id);
          System.out.println("Does the root node exist in the queue? "+ is_exists);
          
          identity_tracker= new FileWriter(id_track,true);
          
          if(is_exists==false)
          {
          	dead_end_pointer++;
          	System.out.println("The end pointer is now at " + dead_end_pointer);
          	Queue.add(dead_end_pointer, seed_user_id);
        	String ind= String.valueOf(dead_end_pointer);
          	identity_tracker.write(ind);
          	identity_tracker.write(',');
          	identity_tracker.write(seed_user_id);
          	identity_tracker.write('\n');
          }
          
          for (int i = 0; i < FriendList.size(); i++)
          {
          	String node=FriendList.get(i);   
          	if(Queue.contains(node))
          	{
          		//Do nothing
          	}
          	else
          	{
          		dead_end_pointer++;
          		Queue.add(dead_end_pointer,node);
          		String ind= String.valueOf(dead_end_pointer);
              	identity_tracker.write(ind);
              	identity_tracker.write(',');
              	identity_tracker.write(node);
              	identity_tracker.write('\n');
          		
          	}
          }
          identity_tracker.flush();
          identity_tracker.close();
          
          setVisited_node_count(getVisited_node_count() + 1);
          System.out.println("No. of nodes visited is "+ getVisited_node_count());

          /*******Appending the edge list file **********/
          boolean isCreated = false; 
          
          writer= new FileWriter(map_file,true);
          if(map_file.exists()!=true)
          {
          	isCreated=map_file.createNewFile();
              System.out.println("Is file created? " +isCreated);
          }
         
          for (int i=0; i<FriendList.size(); i++)
          {
            try 
            {  
          	String source_node_index= String.valueOf(Queue.indexOf(seed_user_id));
          	String other_node=FriendList.get(i);
          	String other_node_index=String.valueOf(Queue.indexOf(other_node));
          	    writer.write(source_node_index);
            	writer.write(',');
            	writer.write(other_node_index);
            	writer.write('\n');
    		  } 
            catch (IOException e) 
    		  {
    			e.printStackTrace();
    		  }
          }
        	writer.flush();
            writer.close();
         } 
    catch (Exception ex) 
    {              
              ex.printStackTrace();
    }
    
}
        
public static void main(String[] args) 
{	
  System.out.println(" Web Crawling started");
  WebCrawler crawler = new WebCrawler();
  crawler.Consumer=crawler.GetConsumer();
  PrintStream out=null;
  try
  {	 out = new PrintStream(new FileOutputStream("F:\\console.txt", true));
     System.setOut(out);
	  if(crawler.restart==true)
	  {
		  crawler.readqueueback();
	  }
	  
     else
     {
      String[] friends= crawler.GetFriends("1856545568");
      if(friends==null)
      {
	  System.out.println("No friends,Crawl ends");
      return;
      }
      crawler.BFS(friends,"1856545568");
     }
  // rest other nodes in the loop
  while(WebCrawler.getVisited_node_count()!=5000)
  {
     String index_string= String.valueOf(Queue.get(WebCrawler.getVisited_node_count()+1));
	  if(index_string.length()==0)
		  {
			  System.out.println("Queue has ended");
			  return;
		  }
		  System.out.println("Next node id is: " + index_string);
		  String[] friends= crawler.GetFriends(Queue.get(WebCrawler.getVisited_node_count()+1));	  
		  if(friends==null)
			  {
			  WebCrawler.setVisited_node_count(WebCrawler.getVisited_node_count() + 1);
				  System.out.println("Null Friendlist -Bypassing the node:" + index_string);
			  }
			  else
			  {
			  crawler.BFS(friends,index_string); 
			  }
  
         }//end of while
  
  }// end of try
  catch(Exception e)
  {
	  System.out.println("Exception in main");
	  e.printStackTrace();
  }
  finally
  {
	  
	  System.out.println("Game over");
	  out.close();
	  System.out.println("Web crawling ended");
  }
return;
}

private void readqueueback() 
{
	// TODO Auto-generated method stub
	try
	{
	CSVReader reader = new CSVReader(new FileReader("F:\\identity_tracker.csv"));
    String [] nextLine;
    while ((nextLine = reader.readNext()) != null) 
    {
        // nextLine[] is an array of values from the line
    	int index= Integer.parseInt(nextLine[0]);
    	Queue.add(index, nextLine[1]);
    }
    reader.close();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
    	
}

public static int getVisited_node_count() 
{
	return visited_node_count;
}

public static void setVisited_node_count(int visited_node_count) 
{
	WebCrawler.visited_node_count = visited_node_count;
}
}
