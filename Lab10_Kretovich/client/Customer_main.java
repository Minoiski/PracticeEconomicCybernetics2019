import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.net.*;


public class Customer_main {

	public static void printSchedule(boolean[][] schedule)
	{
		String date;
		Calendar dateC = Calendar.getInstance();
		SimpleDateFormat sFormat = new SimpleDateFormat(" yyyy.MM.dd");
		
		System.out.print("            ");
		for(int a=10;a<15;a++)
			System.out.print("| "+a+".00-"+(a+1)+".00 ");
		System.out.print("|");
		
		for(int i=0;i<7;i++)
		{
			System.out.println();
			date = sFormat.format(dateC.getTime());
			System.out.print(date+" ");
			dateC.add(Calendar.DAY_OF_MONTH , 1);
			for(int j=0;j<5;j++)
			{
				if(!schedule[i][j])
				{
					System.out.print("|    free     ");
				}
				else 
				{
					System.out.print("|   engaged   ");
				}
			}
			System.out.print("|");
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter your full name.");
		String fname;
		fname = in.nextLine();
		System.out.println("Please enter your valid telephone number.");
		String tn;
		tn = in.nextLine();
		System.out.println("Please enter your complaints if you see fit, if not enter -.");
		String co;
		co = in.nextLine();
		
		Socket sock = new Socket(InetAddress.getLocalHost(), 8071);
		OutputStream os = sock.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		InputStream is = sock.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is);
		
		oos.writeObject(new MessageConnect(fname , tn , co));
		
		MessageInfo msg = ( MessageInfo ) ois.readObject();
		boolean[][] s = msg.freeTime;
		if(msg.apologies!=null)
			System.out.println(msg.apologies);
		
		System.out.println("Information about making an appointment for the week ahead(time in columns , days in lines):");
		printSchedule(s);
		
		String date;
		int time;
		int a=-1 , b=-1;
		boolean fl = true;
		do
		{
			System.out.println("If you want to exit, enter $.To continue enter anything else.");
			if(in.nextLine().equals("$"))
				fl=false;
			if(fl)
			{
			System.out.println("Enter the date on which you want to make an appointment.(in format yyyy.mm.dd)");
			StringTokenizer token = new StringTokenizer(in.nextLine(), ". ");
			GregorianCalendar dateGC=new GregorianCalendar(Integer.parseInt(token.nextToken()),Integer.parseInt(token.nextToken())-1,Integer.parseInt(token.nextToken()));
		    SimpleDateFormat sFormat = new SimpleDateFormat("yyyy.MM.dd");
		    date = sFormat.format(dateGC.getTime());
			
			boolean dfl=false;
			GregorianCalendar dateC = new GregorianCalendar();
			for(int i=0;i<7;i++)
			{
				String cDate;
				cDate = sFormat.format(dateC.getTime());
				if(date.equals(cDate))
				{
					dfl=true;
					a=i;
				}
				dateC.add(GregorianCalendar.DAY_OF_MONTH , 1);
			}
			
			if(!dfl)
				System.out.println("Day entered incorrectly!Try again.");
			else
			{
				System.out.println("Enter the time on which you want to make an appointment.(in format hh)");
				time = Integer.parseInt(in.nextLine());
			
				boolean tfl=false;
				for(int j=10;j<15;j++)
				{
					if(dfl&&(time==j))
					{
						tfl=true;
						b=j-10;
					}
				}
			
				if(dfl&&!tfl)
					System.out.println("Time entered incorrectly!Try again.");
			
				if((a!=-1)&&(b!=-1)&&(dfl)&&(tfl))
					if(!s[a][b])
						fl=false;
					else
						System.out.println("this time is already taken!Try again.");
			}
			}
			
		}
		while(fl);
		
		oos.writeObject(new MessageLetter(fname , a , b));
		MessageLetterResult msgr=(MessageLetterResult) ois.readObject();
		System.out.println("Your letter was accepted");
		in.nextLine();
		oos.writeObject(new MessageDisconnect());
		in.close();
		sock.close();
	}
}
