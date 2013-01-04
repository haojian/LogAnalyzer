package txtOutPut;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.IOOperator;


public class SERPRankIO {
	
	public static SERPRankIO singlton = null;
	public static String SERPDataFileDirectory = "/Users/haojianjin/Desktop/serps";
	public static String IOOutputpath = "rankData.txt";
	private File[] templist;
	private String curID = "";
	private String curTaskID = "";
	private String curSerpIndex = "";
	
	public static SERPRankIO getInstance(){
		if(singlton == null)
			singlton = new SERPRankIO();
		return singlton;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IOOperator.writeToFile(IOOutputpath, "%%%%%%%userID" +"\t" + "taskid" + "\t"+ "serpIndexinTask" + "\t"  + "main_rank" + "\t" +"deep_rank"+ "\t" + "content_rank" + "\t" + "url" + "\n", true);

		SERPRankIO.getInstance().output();
		//test function
		//SERPRankIO.getInstance().outputTest();
	}
	
	public void outputTest(){
		File file = new File(SERPDataFileDirectory + "/SERPHTML_100501_7_2.txt");
		processSERP_v2(file);
	}

	public void output(){
		traverse();
		for(int i=0; i<templist.length; i++){
			extract(templist[i]);
		}
	}
	
	public void processSERP_v2(File input){
		try{
			Document doc = Jsoup.parse(input, "UTF-8", "");
			Element content = doc.getElementById("rso");
			if(content == null){
				return;
			}
			Elements lis = content.getElementsByTag("li");
			int rank = -1;
			for(Element li : lis){
				rank++;
				// to remove the news box, image box, google map results in serp.
				if(li.parent() != content){
					//continue;
				}
				String id = li.attr("id");
				if(id == "newsbox" || id == "imagebox_bigimages"){
					//continue;
				}
				Elements links = li.getElementsByTag("a");
				if(links.size() == 0){
					System.out.print(links.size());
					continue;
				}
				
				String linkHref = links.get(0).attr("href");
				if(linkHref.startsWith("/")){
					//continue;
				}
				IOOperator.writeToFile(IOOutputpath, curID +"\t" + curTaskID + "\t"+ curSerpIndex + "\t"  + rank + "\t" + "-1" +"\t" + "-1" + "\t" + linkHref + "\n", true);
				System.out.println("rank " + rank + "\t" + linkHref);
				
				Elements deep = li.getElementsByClass("fc");
				System.out.print(deep.size() + "\n");
				if(deep.size() == 1)
				{
					int deeprank = 0;
					Elements deeplinks = deep.get(0).getElementsByTag("a");
					for(Element deeplink : deeplinks){
						IOOperator.writeToFile(IOOutputpath, curID +"\t" + curTaskID + "\t"+ curSerpIndex + "\t"  + rank + "\t" + deeprank +"\t" + "-1" + "\t" + deeplink.attr("href") + "\n", true);
						System.out.println("deeprank " + deeprank + "\t" + deeplink.attr("href"));
						deeprank++;
					}
				}
				int contentRank = 0;
				for(Element curElement : links){
					IOOperator.writeToFile(IOOutputpath, curID +"\t" + curTaskID + "\t"+ curSerpIndex + "\t"  + rank + "\t" + "-1" +"\t" + contentRank + "\t" + curElement.attr("href") + "\n", true);
					System.out.println("content_rank " + rank + "\t" + curElement.attr("href"));
					contentRank++;
				}
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void processSERP(File input){
		try{
			Document doc = Jsoup.parse(input, "UTF-8", "");
			Element content = doc.getElementById("rso");
			if(content == null){
				return;
			}
			Elements lis = content.getElementsByTag("li");
			int rank = -1;
			for (Element li : lis) {
				rank++;
				// to remove the news box, image box, google map results in serp.
				if(li.parent() != content){
					//continue;
				}
				String id = li.attr("id");
				if(id == "newsbox" || id == "imagebox_bigimages"){
					//continue;
				}
				Elements links = li.getElementsByTag("a");
				if(links.size() == 0){
					System.out.print(lis.size());
					continue;
				}
				String linkHref = links.get(0).attr("href");
				if(linkHref.startsWith("/")){
					//continue;
				}
				IOOperator.writeToFile(IOOutputpath, curID +"\t" + curTaskID + "\t"+ curSerpIndex + "\t"  + rank + "\t" + linkHref + "\n", true);
				System.out.println("rank " + rank + "\t" + linkHref);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void extract(File input){
		int idstartIndex = input.getName().indexOf("_", 0)+1;
		int ideendIndex = input.getName().indexOf("_", idstartIndex);
		if(idstartIndex<0 || ideendIndex<0)
			return;
		
		int taskidstartIndex = ideendIndex+1;
		int taskidendIndex = input.getName().indexOf("_", taskidstartIndex);
		
		if(taskidstartIndex<0 || taskidendIndex<0)
			return;
		
		int serpstartIndex = taskidendIndex+1;
		int serpendIndex = input.getName().indexOf(".", serpstartIndex);
		
		if(serpstartIndex<0 || serpendIndex<0)
			return;
		
		curID = input.getName().substring(idstartIndex, ideendIndex);
		curTaskID = input.getName().substring(taskidstartIndex, taskidendIndex);
		curSerpIndex = input.getName().substring(serpstartIndex, serpendIndex);

		System.out.println(input.getName() +"\n"+  curID + "\t" + curTaskID + "\t" + curSerpIndex);
		processSERP_v2(input);
	}
	
	public void traverse(){
		File file = new File(SERPDataFileDirectory);
		templist = file.listFiles();
		for(int i=0; i<templist.length; i++){
			if(templist[i].isFile()){
				//System.out.println("" + templist[i].getName());
			}
		}
	}
}
