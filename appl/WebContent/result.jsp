<%@page import="sun.util.calendar.Gregorian"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@ page language=
"java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head> 
<%Calendar c=new GregorianCalendar();%>
<h4 align="right"><%=c.getTime() %></h4>
<body background="G:\photos\Nature\Desert.jpg">
<h1 align=center>RESULT</h1>
<h3 align=center>
<input type="text" name="dis" value='<%=request.getAttribute("answer")%>'>
 
<a href="sample.html"><br><input type="submit" value="ok"></a>
</h3></body>
</html>