
package com.example.HttpTest2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;



import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;



/*
 Change on 2014/9/4
 
Previously, we load the page thru visit one single Url, and then interpret
Now, we found Gewara divide the page into several visits, first load the overall movie info("Title"), and foreach movie info, 
 it will load the corresponding page later.("Movie schedule,scoring")
 
 */
class Movie {

    private String FILE_NAME = "";
    private Context context;
    private int myid;
    private int fileExist;
    private ArrayList<String> list = new ArrayList<String>();
    private int date;
   
    // private Button write;

    public Movie(Context con,int id,int base) {
       
    	myid=id;
    	date=base;
    	FILE_NAME="temp"+Integer.toString(myid)+getDate(date)+".txt";
    	context=con;
    	fileExist=1;
    }
    public ArrayList<String> onCreate() {

       //text1 = (EditText) findViewById(R.id.edit1);
        int flag=0;
        
        flag=readFromFile(FILE_NAME);
        
        //list.size为一，表示之前拉的电影信息没有拉到
       if(flag==0||list.size()==0)
       {
           try {
              executeHttpGet(getDate(date));
           } catch (Exception e1) {
              // TODO Auto-generated catch block
           e1.printStackTrace();
           }
       }
       
       
       
       if(flag==0||list.size()!=0)
       {
    	   new Thread(){
    		   @Override
    		   public void run(){
           saveToFile(FILE_NAME);
    		   };
    	   }.start();
       }
       // write=(Button)findViewById(R.id.button1);

       //text1.setText(getDate());

       // Toast.makeText(this, "finish", Toast.LENGTH_SHORT).show();
       
     
       return list;
    }
    

    @SuppressWarnings("finally")
    private int readFromFile(String filename)
    {
        String[]ss=new String[100];
        String NL = System.getProperty("line.separator");
        int flag=0;
        
        Calendar c = Calendar.getInstance();
        c.setTime(new   Date()); 
        c.add(Calendar.DATE,date);
        int hour=c.get(Calendar.HOUR);
        int minute=c.get(Calendar.MINUTE);
        int AM_orPM = c.get(Calendar.AM_PM);

        if(AM_orPM>0)
        {
        hour+=12;
        }
        
       try {
           
    	   File file = context.getFileStreamPath(filename);    	   
    	   if(!file.exists())
    	   {
    		   FileOutputStream fos = context.openFileOutput(filename, context.MODE_APPEND);
			   fos.close();
    	   }
    	   
    	   final String fileName="temp"+Integer.toString(myid)+getDate(-1)+".txt";
    	   final File fileDeleted=context.getFileStreamPath(fileName);
    	   
    	   if(fileDeleted.exists())
    	   {    			 
    		   fileDeleted.delete();
    		   //context.deleteFile(fileName);
    	   }
    	
           FileInputStream fis=context.openFileInput(filename);
           byte[] buffer=new byte[fis.available()];  
            //读取到缓冲区  
            fis.read(buffer);             
            String temp=new String(buffer);
            ss=temp.split(NL);
            
            //s = executeHttpGet(getDate());
            
            if(ss!=null&&getDate(date).compareToIgnoreCase(ss[ss.length-1])==0)
            {
              flag=1;
              for(String t:ss)
              {               
                 //list.add(t);
                 if(date==0&&t.contains(":"))
                 {
                     if(Integer.parseInt(t.substring(0, 2))>hour)
                     {
                        list.add(t);
                     }
                     if(Integer.parseInt(t.substring(0, 2))==hour&&Integer.parseInt(t.substring(3, 5))>=minute)
                     {   
                         list.add(t);
                     }
                 }
                 else if(!t.contains("-"))
                    list.add(t);
              }
            }
           
           
       } catch (Exception e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       
       finally{
           	  
           return flag;
       }
    }
    private void saveToFile(String filename)
    {
        String NL = System.getProperty("line.separator");
        
       try {
           // 实例化文件输入流
           
           
           FileOutputStream fos = context.openFileOutput(filename, 0);
           //fos.
           
           for(String temp:list)
           {
              fos.write(temp.getBytes()); 
              fos.write(NL.getBytes());
              
           }
           //String s=getDate();
           fos.write(((String)getDate(date)).getBytes());
           fos.write(NL.getBytes());
           //fos.write(s.getBytes());
           
           fos.close();
       } catch (Exception e) {
           e.printStackTrace();
       }
    
    }
//  @Override
//    public void onDestroy() {
//        // Cancel the notification -- we use the same ID that we had used to start it
//        //mNM.cancel(R.string.alarm_service_started);
//
//        // Tell the user we stopped.
//       
//    }
    private String getDate(int base)
    {
       String date="";
       String month="",day="";
       Calendar c = Calendar.getInstance();
       c.setTime(new   Date()); 
       c.add(Calendar.DATE,base); 
       int mYear = c.get(Calendar.YEAR); // 获取当前年份
       int mMonth = c.get(Calendar.MONTH);// 获取当前月份
       int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码
       mMonth++;
       
       if(mMonth<10)
       {
           month="0"+mMonth;
       }
       else          
       {
           month=""+mMonth;
       }
       
       if(mDay<10)
       {
           day="0"+mDay;
       }
       else          
       {
           day=""+mDay;
       }
       
       return ""+mYear+"-"+month+"-"+day;
    }
 

    public void executeHttpGet(String date) throws Exception {
       BufferedReader in = null;
      // String Url="http://www.gewara.com/cinema/ajax/getCinemaPlayItem.xhtml?cid=1277473&mid=&fyrq=";
       //String Url="http://www.gewara.com/cinema/ajax/getCinemaPlayItem.xhtml?cid="+Integer.toString(myid)+"&mid=&fyrq=";
       
       //Change Url on 2014/9/4    
       String Url="http://www.gewara.com/cinema/ajax/getOpiItemPage.xhtml?cid="+Integer.toString(myid)+"&mid=&fyrq=";
       ArrayList<String> namelist = new ArrayList<String>();
       ArrayList<String> idlist = new ArrayList<String>();
       //ArrayList<String> scorelist = new ArrayList<String>();
       int flag=0,pos=0;
       int flag2=0;
       int flag3=1;
       int flag4=1;
       Object ia[]=null;
       Object ia2[]=null;
       String s = "";
       String temp="";
       
	   Locale.setDefault(Locale.CHINA);//中文语言环境下
	   Currency currency1 = Currency.getInstance("CNY");
	   String rmb=currency1.getSymbol();
	   
       try {
                     
    
           HttpClient client = new DefaultHttpClient();
           HttpGet request = new HttpGet();
           request.setURI(new URI(Url+date));
//         request.setURI(new URI(
//                "http://www.gewara.com/cinema/ajax/getCinemaPlayItem.xhtml?cid=1277473&mid=&fyrq=2012-04-01"));
           HttpResponse response = client.execute(request);
           in = new BufferedReader(new InputStreamReader(response.getEntity()
                  .getContent(), "UTF-8"));
                     
           
           StringBuffer sb = new StringBuffer("");
           String line = "";
           String NL = System.getProperty("line.separator");
           String tempPrice="";
           
           //System.out.println("jacky");
    
           //System.out.println("jacky");
           
           
       
           while ((line = in.readLine()) != null) {
              //sb.append(line + NL);
              int i, j,len = 0;
              //get movie name
              
              //"void" doesn't work any more, need to use "left", but since 中文，英文也含有 left,所以把他们去除
              //if (flag3==1&line.contains("void")) {
              
            //get movie name
            	   if (line.contains("left"))
            	   {	  
	                  i = line.indexOf(">");
	                  j = line.indexOf("<", i);
                  //temp=line.substring(i + 1, j);
                  
                 // if(!line.substring(i + 1, j).contains("中文")&&!line.substring(i + 1, j).contains("英文"))
	                  namelist.add(line.substring(i + 1, j));
                  //flag2=1;
                  
                 // continue;
                  //System.out.println(line.substring(i, j));
            	   }
              
            	   i=0;
            	   j=0;
            	   if(line.contains("java")&&line.contains("id"))
            	   {
            		   i=line.indexOf("id=");
            		   j = line.indexOf(">", i);
            		   idlist.add(line.substring(i+4, j-1));
            		   
            	   }
              //calculate score,integral part
//              if (line.contains("margin:0")) {
//                  int scorelen = line.length();
//                  //j = line.indexOf("<", i);
//                  //scorelist.add(line.substring(i + 29, i+30)+line.substring(i + 59, i+61));
//                  
//                  //temp=line.substring(i + 29, i+30)+line.substring(i + 59, i+61);
//                  temp=line.substring(scorelen-7,scorelen-6);
//                  flag3=0;
//                  continue;
//                  //namelist.add(line.substring(i + 1, j));
//                  //System.out.println(line.substring(i, j));
//
//                  
//              }
//              
//            //calculate score,decimal part
              
//              if (line.contains("sup")) {
//                  int scorelen = line.length();
//                  //j = line.indexOf("<", i);
//                  //scorelist.add(line.substring(i + 29, i+30)+line.substring(i + 59, i+61));
//                  
//                  //temp=line.substring(i + 29, i+30)+line.substring(i + 59, i+61);
//                  temp+=line.substring(scorelen-8,scorelen-6);
//                  flag3=0;
//                  continue;
//                  //namelist.add(line.substring(i + 1, j));
//                  //System.out.println(line.substring(i, j));
//
//                  
//              }
          
              //calculate score
//              if (line.contains("integer")) {
//                  i = line.indexOf("<");
//                  //j = line.indexOf("<", i);
//                  //scorelist.add(line.substring(i + 29, i+30)+line.substring(i + 59, i+61));
//                  
//                  temp=line.substring(i + 29, i+30)+line.substring(i + 59, i+61);
//                  flag3=0;
//                  continue;
//                  //namelist.add(line.substring(i + 1, j));
//                  //System.out.println(line.substring(i, j));
//
//                  
//              }
//              
//              if(flag2==1)
//              {
//              ia= namelist.toArray();
//              flag2=0;
//              continue;
//              }
//              //ia2 = scorelist.toArray();
//              
//              //namelist.add(temp);
//           
//              
//              //namelist.clear();
//              
//              if(line.contains("chooseOpi_head"))
//              {
//                  flag=1;
//                  pos++;
//                  continue;
//                                
//              }
//             // if(line.contains(":"))
//              if(line.contains(":")&&line.contains("b")&&line.contains("/"))
//              {
//                  if(flag==1)
//                  {
//                     //list.add((String)ia[pos-1]+"   "+(String)ia2[pos-1]);
//                     list.add((String)ia[pos-1]+"   "+temp);
//                     flag=0;
//                  }
//           
//                  len=line.length();
//                  //before version changed on 2013/5/28, the time is changed to <
//                 // list.add(line.substring(len-16,len-11));
////                  String c=""+line.substring(len-9,len-4).charAt(0);
////                  if(Integer.parseInt(c)>=0&&Integer.parseInt(c)<=9){
//                  if(len<25)
//                  {
//                  list.add(line.substring(len-9,len-4));
//                  }
//         //         }
//              
//              }
              
              
//              if(line.contains("b")&&line.contains("/"))
//                {
//              	  int len2=line.length();
//              	  tempPrice=list.get(list.size()-1);
//              	 // tempPrice=list.get(list.size()-1);
//              	  if(len2==30)
//              	  {
//              	   list.set(list.size()-1, tempPrice+"      "+rmb+line.substring(len2-9,len2-4));
//              	  }
//              	  
//                }
            	  
//              if(line.contains("opiPrice"))
//              {
//            	  len=line.length();
//            	  tempPrice=list.get(list.size()-1);
//            	  if(tempPrice.charAt(2)==':')
//            	  {
//            		  list.set(list.size()-1, tempPrice+"      "+rmb+line.substring(len-13,len-11));
//            	  }
//            	  
//              }
           }
           
           in.close();
           String Urlprefix="http://www.gewara.com/movie/ajax/getOpiItem.xhtml?movieid=";
           String Urlsuffix="&fyrq="+date+"&isView=true&cid="+Integer.toString(myid);
           int i=0,j=0,k=0;
           for(String id: idlist)
           {
        	   list.add(namelist.get(k++));
        	   String Url2=Urlprefix+id+Urlsuffix;
        	   request.setURI(new URI(Url2));
//             request.setURI(new URI(
//                    "http://www.gewara.com/cinema/ajax/getCinemaPlayItem.xhtml?cid=1277473&mid=&fyrq=2012-04-01"));
               response = client.execute(request);
               in = new BufferedReader(new InputStreamReader(response.getEntity()
                      .getContent(), "UTF-8"));
               while ((line = in.readLine()) != null) {
            	   
            	   if(line.contains("<b>")&&line.contains(":"))
            	  {
            		   i=line.indexOf("<b>");
            		   j=line.indexOf(":",i);
            		   list.add(line.substring(i+3, j+3));
            		   
            	  }
               
               }
           }
          // list=namelist;
           System.out.println(idlist);
           in.close();
           
           //System.out.println("jacky");
           //String page = sb.toString();
           // Log.v("Log1", page);
           //s = page;
           // text1.setText(page);
           //System.out.println(page);
       } finally {
           if (in != null) {
              try {
                  in.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
              // return page;
           }

       }
       //return s;
    }
}
public class HttpTest2 extends ListActivity {

    public static final int INSERT_ID = Menu.FIRST;
    public static final int Show1_ID = Menu.FIRST+1;
    public static final int Show2_ID = Menu.FIRST+2;
    public static final int Show3_ID = Menu.FIRST+3;
    public static final int Show4_ID = Menu.FIRST+4;
    
    private ListView mylist;
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<String> listClone = new ArrayList<String>();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.main);
       //text1 = (EditText) findViewById(R.id.edit1);
       
       //mylist = (ListView) findViewById(R.id.listview2);

       int i=1277473;
       Movie moive=new Movie(this,i,0);
       listClone=(ArrayList<String>)moive.onCreate().clone();   
       
       if(listClone.size()==0)
       {
       	listClone.add("影院当前还没有更新排片");
       }
/*       ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
               android.R.layout.simple_expandable_list_item_1, listClone);
       mylist.setAdapter(adapter);*/
       
       String[] array = listClone.toArray(new String[listClone.size()]);
		ItemsAdapter ItemsAdapter = new ItemsAdapter(
				HttpTest2.this, R.layout.list,
				array);
		setListAdapter(ItemsAdapter);

		listClone=null;
       
    }
    
    private class ItemsAdapter extends BaseAdapter {
		String[] items;

		public ItemsAdapter(Context context, int textViewResourceId,
				String[] items) {
			// super(context, textViewResourceId, items);
			this.items = items;
		}

		// @Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.list, null);
			} 
			TextView post = (TextView) v
					.findViewById(R.id.post);
			post.setText(items[position]);
			
			return v;
		}

		public int getCount() {
			return items.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        //return super.onCreateOptionsMenu(menu);
    	  boolean result = super.onCreateOptionsMenu(menu);
          menu.add(0, INSERT_ID, 0, R.string.menu_insert);
          menu.add(0, Show4_ID, 0, R.string.menu_quyang);
          menu.add(0, Show1_ID, 0, R.string.menu_1stday);
          menu.add(0, Show2_ID, 0, R.string.menu_2ndday);
          menu.add(0, Show3_ID, 0, R.string.menu_3rdday);
        

          //menu.add(0, INSERT_ID2, 0, R.string.);.
         // menu.add(0, INSERT_ID, 0, R.string.);
          return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
    	switch (item.getItemId()) {
        case INSERT_ID:
            ShowMoive2(0,59845909);
            break;
        case Show4_ID:
            ShowMoive2(0,5362);
            break;
        case Show1_ID:
            ShowMoive(0);
            break;
        case Show2_ID:
            //ShowMoive(1);
        	ShowMoive2(1,59845909);
            break;
        case Show3_ID:
            //ShowMoive(2);
        	ShowMoive2(2,59845909);
            break;

           // return true;
        }
       
        //return super.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }
    
    private void ShowMoive2(int base, int id)
    {
    	//int i=59845909;
        Movie moive=new Movie(this,id,base);
        listClone=moive.onCreate();  
       
        if(listClone.size()==0)
        {
        	listClone.add("影院当前还没有更新排片");
        }
        
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_expandable_list_item_1, listClone);
//        mylist.setAdapter(adapter);

        String[] array = listClone.toArray(new String[listClone.size()]);
		ItemsAdapter ItemsAdapter = new ItemsAdapter(
				HttpTest2.this, R.layout.list,
				array);
		setListAdapter(ItemsAdapter);

        listClone=null;
    }
    
    private void ShowMoive(int base)
    {
    	int i=1277473;
        Movie moive=new Movie(this,i,base);
        listClone=(ArrayList<String>)moive.onCreate().clone();  
        if(listClone.size()==0)
        {
        	listClone.add("影院当前还没有更新排片");
        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_expandable_list_item_1, listClone);
//        mylist.setAdapter(adapter);
        
        String[] array = listClone.toArray(new String[listClone.size()]);
		ItemsAdapter ItemsAdapter = new ItemsAdapter(
				HttpTest2.this, R.layout.list,
				array);
		setListAdapter(ItemsAdapter);


        listClone=null;
    }
}
 
 


