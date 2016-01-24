package A00881533_mainServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * The server utilities that are needed
 * so that the main servlet is not clogged
 * 
 * Implements a variety of different functions
 * @author Chusi
 * Date: January 24th, 2016
 */
public class A00881533_ServletUtilities {

	public static final String DOCTYPE =
		    "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
		    "Transitional//EN\">";
	
	//this one is for mail
	public static final String emailValidation = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
	//this one is for phone number
	public static final String phoneValidation = "^\\(?(\\d{3})\\)?[\\.\\-\\/ ]?(\\d{3})[\\.\\-\\/ ]?(\\d{4})$";
  
	/**
	 * Easy way to attach the title
	 * 
	 * Note: This function was taken from the 3613_day2_examples
	 * 
	 * @param title The title that is wanted for it
	 * @return string
	 * Date: January 24th, 2016
	 */
	public static String headWithTitle(String title) {
		return(DOCTYPE + "\n" +
	           "<HTML>\n" +
	           "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n");
	}
  
	/**
	 * A function that is used to validate string input against a regular expression
	 * 
	 * Note: The whole function was given as a requirement for lab 2
	 * 
	 * @param input 	The string that wants to be evaluated against the regular expression
	 * @param pattern	The regular expression in itself
	 * @return boolean	Whether the input passed the test against the regular expression
	 * Date: January 24th, 2016
	 */
	public static boolean isValidInput(String input, String pattern) {
		Pattern patt = Pattern.compile(pattern);
		Matcher match = patt.matcher(input);
		return match.matches();
	}
	
	/**
	 * Takes the response and the request to print all the parameters that were submitted
	 * by the form and printing all of it depending on the validation.
	 * 
	 * Note: Part of the code was coded through 3613_day2_examples and added the functionality
	 * needed that were required by the lab2
	 * 
	 * @param request 				given by the servlet itself
	 * @param response				given by the servlet itself
	 * @throws ServletException		Exceptions for the servlet
	 * @throws IOException			Regular exceptions
	 * Date: January 24th, 2016
	 */
	public static void printResult(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		 response.setContentType("text/html");
		 PrintWriter out = response.getWriter();
		 String title = "Reading All Request Parameters";
		 
		 out.println(headWithTitle(title) +
		                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
		                "<div id=\"header\">" +
						"<H1>COMP 3613 Lab02</H1>" +
						"<H2>Jonathan Chu, A00881533</H2></div>" +
		                "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
		                "<TABLE BORDER=1 ALIGN=CENTER>\n" +
		                "<TR BGCOLOR=\"#FFAD00\">\n" +
		                "<TH>Parameter Name<TH>Parameter Value(s)");
		 
		 Enumeration paramNames = request.getParameterNames();
		 
		 //loop to keep going until all parameters are done with
		 while(paramNames.hasMoreElements()) {
			 //get the parameter name
		      String paramName = (String)paramNames.nextElement();
		      String[] paramValues =
		        request.getParameterValues(paramName);
		 
		      //printing parameter
		      out.print("<TR><TD>" + paramName + "\n<TD>");

		      //save parameter value if its not null or empty
		      if (paramValues.length == 1) {
		    	  String paramValue = paramValues[0];
		    	  
		    	  //if there is nothing on it -> notify user
		    	  if (paramValue.length() == 0)
		    		  out.println("<I> No Value </I>");
		    	  else if (paramName.equals("phone") && !isValidInput(paramValue, phoneValidation))
		    		  out.println("<I>Wrong formatting. Eg: (778)123-7746 </I>");
		          else if (paramName.equals("email") && !isValidInput(paramValue, emailValidation))
		        	  out.println("<I>Wrong Formatting. Eg: username@domain.com </I>");
				  else
		    		  //print if there is anything
		    		  out.println(paramValue);
		      } else {
		    	  out.println("<UL>");
		    	  for(int i=0; i<paramValues.length; i++) {
		    		  out.println("<LI>" + paramValues[i]);
		    	  }
		    	  out.println("</UL>");
		      }
		    }
		    out.println("</TABLE>\n</BODY></HTML>");
	}
}
