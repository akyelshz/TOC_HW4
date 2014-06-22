/*
 * THEORY OF COMPUTATION - HW4
 * 資工系104乙班
 * 沙江益 - Aquile R. Sanchez
 * F74007094
 * File Name: TocHw4.java
 * Run Syntax: java -jar TocHw4.jar URL
 * Output: Given a URL which can load a Json file containing less than 2000 pieces of house trading
 * data in Taiwan, it scans the whole data and find out “which road in a city has house trading
 * records spread in #max_distinct_month.
*/

import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import org.json.*;

class MyClass
{
	String Address;
	int MAXPrice;
	int MINPrice;
	int Counter;
	boolean[] DateMonth = new boolean[13];
	boolean[] DateYear = new boolean[110];
	
	public MyClass(int I, int MyDate, String MyAddress, int Price)
	{
		Counter = 1;
		MAXPrice = Price;
		MINPrice = Price;
		Address = MyAddress;
		for (int i = 0; i < 110; i++)
		{
			for(int j = 1;j<=12; j++)
			{
				DateYear[i] = false;
				DateMonth[j] = false;
			}
		}
		DateYear[MyDate/100] = true;
		DateMonth[MyDate%100] = true;
	}
	
}

public class TocHw4 {
	public static void main(String[] args) {
		String ProvidedURL = args[0];
		//System.out.println("Entering URL...");
		String MyString = GetJsonFile(ProvidedURL);
		//System.out.println("Finished Saving Json File.");
		//System.out.println(MyString);
		int Total = 0;
		try {
			List<MyClass> MyList = new ArrayList<MyClass>();
			HashMap<String, MyClass> MyHashMap = new HashMap<String, MyClass>();
			
			JSONArray MyJsonArray = new JSONArray(MyString);
			JSONObject MyJsonObject;
			
			String Address = null;
			
			for (int i = 0; i < MyJsonArray.length();i++)
			{
				MyJsonObject = MyJsonArray.getJSONObject(i);
				
				//Address = MyJsonObject.getString("土地區段位置或建物區門牌");
				if (MyJsonObject.getString("土地區段位置或建物區門牌").contains("路"))
				{
					String TempStr = MyJsonObject.getString("土地區段位置或建物區門牌").toString();
					Pattern MyPattern = Pattern.compile(".*路");
					Matcher MyMatcher = MyPattern.matcher(TempStr);
					if (MyMatcher.find())
					{
						Address = MyMatcher.group(0);
						
					}
				}
				else if (MyJsonObject.getString("土地區段位置或建物區門牌").contains("街"))
				{
					String TempStr = MyJsonObject.getString("土地區段位置或建物區門牌").toString();
					Pattern MyPattern = Pattern.compile(".*街");
					Matcher MyMatcher = MyPattern.matcher(TempStr);
					if (MyMatcher.find())
					{
						Address = MyMatcher.group(0);
					}
				}
				else if (MyJsonObject.getString("土地區段位置或建物區門牌").contains("大道"))
				{
					String TempStr = MyJsonObject.getString("土地區段位置或建物區門牌").toString();
					Pattern MyPattern = Pattern.compile(".*大道");
					Matcher MyMatcher = MyPattern.matcher(TempStr);
					if (MyMatcher.find())
					{
						Address = MyMatcher.group(0);
					}
				}
				else if (MyJsonObject.getString("土地區段位置或建物區門牌").contains("巷"))
				{
					String TempStr = MyJsonObject.getString("土地區段位置或建物區門牌").toString();
					Pattern MyPattern = Pattern.compile(".*巷");
					Matcher MyMatcher = MyPattern.matcher(TempStr);
					if (MyMatcher.find())
					{
						Address = MyMatcher.group(0);
					}
				}
				else if (MyJsonObject.getString("土地區段位置或建物區門牌").contains("弄"))
				{
					String TempStr = MyJsonObject.getString("土地區段位置或建物區門牌").toString();
					Pattern MyPattern = Pattern.compile(".*弄");
					Matcher MyMatcher = MyPattern.matcher(TempStr);
					if (MyMatcher.find())
					{
						Address = MyMatcher.group(0);
					}
				}
				else
				{
					continue;
				}
				MyClass TempClass = MyHashMap.get(Address);
				if (TempClass == null)
				{
					int Date = MyJsonObject.getInt("交易年月");
					int Price = MyJsonObject.getInt("總價元");
					MyClass AuxClass = new MyClass(i, Date, Address, Price);
					MyHashMap.put(Address, AuxClass);
					//System.out.println(Date+" "+Price);
				}
				else
				{
					int dateyear = MyJsonObject.getInt("交易年月")/100;
					int datemonth = MyJsonObject.getInt("交易年月")%100;
					//System.out.println("Month: "+datemonth+" Year: "+dateyear);
					if (MyJsonObject.getInt("總價元") > TempClass.MAXPrice)
					{
						TempClass.MAXPrice = MyJsonObject.getInt("總價元");
					}
					if (MyJsonObject.getInt("總價元") < TempClass.MINPrice)
					{
						TempClass.MINPrice = MyJsonObject.getInt("總價元");
					}
					if ((TempClass.DateMonth[datemonth] && TempClass.DateYear[dateyear]) == false)
					{
						if (TempClass.Counter>Total)
						{
							Total = TempClass.Counter;
							MyList.clear();
							MyList.add(TempClass);	
						}
						else if (TempClass.Counter == Total)
						{
							MyList.add(TempClass);	
						}
						TempClass.DateMonth[datemonth] = true;
						TempClass.DateYear[dateyear] = true;
						TempClass.Counter++;
					}
				}
				
			}
			for(MyClass TempList:MyList)
			{
				System.out.println(TempList.Address+", 最高成交價: "+TempList.MAXPrice+", 最低成交價: "+TempList.MINPrice);
			}
			//System.out.println(Total);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	static String GetJsonFile(String ProvidedURL)
	{
		String ReadLine;
		StringBuilder MyBuilder = new StringBuilder();
		try {  
			
			URL MyURL = new URL(ProvidedURL);
			URLConnection MyURLConnection = MyURL.openConnection();
			BufferedReader MyBufferedReader = new BufferedReader(new InputStreamReader(MyURLConnection.getInputStream(),"UTF-8"));
			while ((ReadLine = MyBufferedReader.readLine()) != null)
			{
				MyBuilder.append(ReadLine);
			}
			MyBufferedReader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return MyBuilder.toString();
	}

}
