

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.security.action.GetBooleanAction;

import com.sun.image.codec.jpeg.TruncatedFileException;

/**
 * Servlet implementation class servlet
 */
public class servlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public static Connection getconnection()
	{
		Connection cn=null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			cn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","hr");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cn;
	}
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    response.setContentType("text/html;charset=UTF-8");
    if(request.getParameter("yes").equals("ok"))
	{  
        String a=request.getParameter("one");
		int k=0,cv=0,ans=0;
		int x=0,b;
		double f;
		if(request.getParameter("other").equals("norm"))
		{
		x=insert(a);
		}
		else if(request.getParameter("other").equals("sci"))
		{
		x=insert1(a);
		}
		else if(request.getParameter("other").equals("bin"))
		{
		x=insert2(a);
		}
		if(a.equals("=")&&request.getParameter("other").equals("norm"))
		{  
			ans=calculate();
			x=insert(ans+"");
			if(ans>0)
			{
			request.setAttribute("answer",ans);
			RequestDispatcher rd=request.getRequestDispatcher("result.jsp");
		    rd.forward(request,response);
			}
		}
		    else if(a.equals("=")&&request.getParameter("other").equals("sci"))
			{
		    	f=calculat();
		    	x=insert1(f+"");
				request.setAttribute("answer",f);
				RequestDispatcher rd=request.getRequestDispatcher("result.jsp");
			    rd.forward(request,response);	
			}
		    else if(a.equals("=")&&request.getParameter("other").equals("bin"))
		    {
		       b=cal();
		       x=insert2(b+"");
		       request.setAttribute("answer",Integer.toBinaryString(b));
			   RequestDispatcher rd=request.getRequestDispatcher("result.jsp");
			   rd.forward(request,response);
		    }
		    else
			response.sendRedirect("sample.html");
		}
	if(request.getParameter("yes").equals("reset"))
	{
	truncate();
	response.sendRedirect("sample.html");
	}
 }
public static void truncate()
{
	Connection cr=getconnection();
	PreparedStatement py;
	try {
		py = cr.prepareStatement("truncate table calculator");
		py.execute();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try{
		py=cr.prepareStatement("truncate table calc");
		py.execute();
	}
	catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		py = cr.prepareStatement("truncate table binary");
		py.execute();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public int insert(String d)
{
	int x=0;
	Connection cr=getconnection();
	PreparedStatement pr;
	try {
		pr = cr.prepareStatement("insert into calculator values(?)");
		pr.setString(1,d);
		x=pr.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return x;
}
public int insert1(String d)
{
	int x=0;
	Connection cr=getconnection();
	PreparedStatement pr;
	try {
		pr=cr.prepareStatement("insert into calc values(?)");
		pr.setString(1,d);
		x=pr.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return x;
}
public int insert2(String k)
{   int l=0;
	Connection ct=getconnection();
	try {
		PreparedStatement po=ct.prepareStatement("insert into binary values(?)");
		po.setString(1,k);
		l=po.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return l;
	
}
public int calculate()
{int sum=0;
Connection cp=getconnection();
try {
	PreparedStatement pt=cp.prepareStatement("select * from calculator");
	ResultSet rs=pt.executeQuery();
	String f="";
	while(rs.next())
	{
		String h=rs.getString(1);
		if(Character.isDigit(h.charAt(0)))
			f+=h+"";
		else
		f+=" "+h+" ";
	}
	String p="";
	String s[]=new String[f.length()];
	s=f.split(" ");
	for(int i=0;i<s.length;i++)
	{
		if(s[i].equals("+"))
			sum=Integer.parseInt(String.valueOf(s[i-1]))+Integer.parseInt(String.valueOf(s[i+1]));
		else if(s[i].equals("-"))
			sum=Integer.parseInt(String.valueOf(s[i-1]))-Integer.parseInt(String.valueOf(s[i+1]));
		else if(s[i].equals("*"))
			sum=Integer.parseInt(String.valueOf(s[i-1]))*Integer.parseInt(String.valueOf(s[i+1]));
		else if(s[i].equals("/"))
			sum=(Integer.parseInt(String.valueOf(s[i-1])))/(Integer.parseInt(String.valueOf(s[i+1])));
		else if(s[i].equals("pow"))
			sum=(int) Math.pow(Integer.parseInt(String.valueOf(s[i-1])),Integer.parseInt(String.valueOf(s[i+1])));
		else if(s[i].equals("fact"))
		{ int p1=Integer.parseInt(String.valueOf(s[i-1]));
		   int t=1; 
		  for(int k=1;k<=p1;k++)
			{
				t*=k;
			}
			sum=t;
		}
		else if(s[i].equals("sqrt"))
		{
			sum=(int) Math.sqrt(Integer.parseInt(String.valueOf(s[i+1])));
		}
		else if(s[0]!=null&&s[1]==null)
			sum=Integer.parseInt(s[0]);
	}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
return sum;
}
public double calculat()
{  
	double d = 0;
	Connection cp=getconnection();
	try {
		PreparedStatement pt=cp.prepareStatement("select * from calc");
		ResultSet rs=pt.executeQuery();
		String f="";
		while(rs.next())
		{
			String h=rs.getString(1);
			if(Character.isDigit(h.charAt(0)))
			{
				f+=h+"";
			}
			else if(!h.isEmpty())
				f+=" "+h+" ";
		}
		
		String s[]=new String[f.length()];
		s=f.split(" ");
        for(int i=0;i<s.length;i++)
        {
        	if(s[i].equals("+"))
    			d=Float.parseFloat(String.valueOf(s[i-1]))+Float.parseFloat(String.valueOf(s[i+1]));
    		else if(s[i].equals("-"))
    			d=Float.parseFloat(String.valueOf(s[i-1]))-Float.parseFloat(String.valueOf(s[i+1]));
    		else if(s[i].equals("*"))
    			d=Float.parseFloat(String.valueOf(s[i-1]))*Float.parseFloat(String.valueOf(s[i+1]));
    		else if(s[i].equals("/"))
    			d=(Float.parseFloat(String.valueOf(s[i-1])))/(Float.parseFloat(String.valueOf(s[i+1])));
    		else if(s[i].equals("cos"))
        		d=(double) Math.cos(Integer.parseInt(String.valueOf(s[i+1])));
        	else if(s[i].equals("sin"))
        		d=(double) Math.sin(Integer.parseInt(String.valueOf(s[i+1])));
        	else if(s[i].equals("tan"))
        		d=(double) Math.tan(Integer.parseInt(String.valueOf(s[i+1])));
        	else if(s[i].equals("log"))
        		d=(double) Math.log(Integer.parseInt(String.valueOf(s[i+1])));
        	else if(s[i].equals("todeg"))
        		d=(double) Math.toDegrees(Integer.parseInt(String.valueOf(s[i+1])));
        	else if(s[i].equals("torad"))
        		d=(double) Math.toRadians(Integer.parseInt(String.valueOf(s[i+1])));
        	else if(s[0]!=null&&s[1]==null)
        	    d=Integer.parseInt(s[0]);
        }
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	return d;
}
public int cal()
{   int k = 0;
	Connection cv=getconnection();
	try {
		PreparedStatement pt=cv.prepareStatement("select * from binary");
		ResultSet rs=pt.executeQuery();
		String p=new String();
		while(rs.next())
		{
			String h=rs.getString(1);
			if(Character.isDigit(h.charAt(0)))
				p+=h.charAt(0)+"";
			else
				p+=" "+h.charAt(0)+" ";
		}
		String s[]=new String[p.length()];
		s=p.split(" ");
		for(int i=0;i<s.length;i++)
		{
			if(s[i].equals("+"))
			    k=Integer.parseInt(s[i-1],2)+Integer.parseInt(s[i+1],2);
			else if(s[i].equals("-"))
				k=Integer.parseInt(s[i-1],2)-Integer.parseInt(s[i+1],2);
			else if(s[i].equals("*"))
				k=Integer.parseInt(s[i-1],2)*Integer.parseInt(s[i+1],2);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return k;
}
}
