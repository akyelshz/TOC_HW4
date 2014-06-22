import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import org.json.*;

public class TocHw4 {
	static boolean FirstComparison = true;
	public static void main(String[] args) {
		String ProvidedURL = "http://www.datagarage.io/api/538447a07122e8a77dfe2d86";
		System.out.println("Entering URL...");
		String MyString = GetJsonFile(ProvidedURL);
		System.out.println("Finished Saving Json File.");
		System.out.println(MyString);
		
		int Total = 0;
		
		try {
			List<MyClass> MyList = new ArrayList<MyClass>();
			HashMap<String, MyClass> MyHashMap = new HashMap<String, MyClass>();
			
			JSONArray MyJsonArray = new JSONArray(MyString);
			JSONObject MyJsonObject;
			
			String Address = null;
			int Characters = 0;
			
			for (int i = 0; i < MyJsonArray.length();i++)
			{
				MyJsonObject = MyJsonArray.getJSONObject(i);
				//MyClass TempClass
				
				
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
					
				}
				else
				{
					
				}
				
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	class MyClass
	{
		String Address;
		int first;
		int MAXPrice;
		int MINPrice;
		int Counter;
		public MyClass()
		{
			
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
