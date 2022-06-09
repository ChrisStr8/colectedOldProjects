/*
 * LibraryModel.java
 * Author:
 * Created on:
 */



import javax.swing.*;
import java.sql.*;

public class LibraryModel {

    // For use in creating dialogs and making them modal
    private JFrame dialogParent;

    private Connection con = null;


    public LibraryModel(JFrame parent, String userid, String password) {
    	dialogParent = parent;

    	try{
    		Class.forName("org.postgresql.Driver");
    	}
    	catch (ClassNotFoundException cnfe){
    		System.out.println("Can not find"+
    		"the driver class: "+
    		"\nEither I have not installed it"+
    		"properly or \n postgresql.jar "+
    		" file is not in my CLASSPATH");
    	}

    	String url = "jdbc:postgresql:"+
    			"//db.ecs.vuw.ac.nz/" + userid + "_jdbc";

    	try{
    		con = DriverManager.getConnection(url, userid, password);
    	}
    	catch (SQLException sqlex){
    		System.out.println("Can not connect");
    		System.out.println(sqlex.getMessage());
    	}
    }

    public String bookLookup(int isbn) {
    	String query =
    			"SELECT * " +
				"FROM Book" +
				"WHERE ISBN = " + isbn;
    	ResultSet rs = executeQuery(query);

    	return "Lookup Book Stub";
    }

    public String showCatalogue() {
    	// TODO find authors
    	String query = (
    			"SELECT * " +
				"FROM Book");
    	ResultSet rs = executeQuery(query);

    	String result = "Show Catalogue:\n\n";
    	try {
			while(rs.next()) {
				result += rs.getString("isbn") + ": ";
				result += rs.getString("title") + "\n";
				result += "	Edition: " + rs.getString("Edition_No");
				result += " - Number of copies: " + rs.getString("NumOfCop");
				result += " - Copies left: " + rs.getString("NumLeft");
				result += "\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

    	return result;
    }

    public String showLoanedBooks() {
	return "Show Loaned Books Stub";
    }

    public String showAuthor(int authorID) {
	return "Show Author Stub";
    }

    public String showAllAuthors() {
    	String query = (
    			"SELECT * " +
				"FROM Author");
    	ResultSet rs = executeQuery(query);

    	String result = "Show All Authors:\n\n";
    	try {
			while(rs.next()) {
				result += rs.getString("	AuthorId") + ": ";
				result += rs.getString("Name") + ", ";
				result += rs.getString("Surname") + "\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

    	return result;
    }

    public String showCustomer(int customerID) {
    	String query = (
    			"SELECT * " +
				"FROM Customer" +
    			"WHERE customerid = 16");
    	ResultSet rs = executeQuery(query);

    	String result = "Show All Customers:\n\n";
    	try {
			while(rs.next()) {
				result += "";
				result += rs.getString("CustomerID") + ": ";
				result += rs.getString("L_Name") + ",";
				result += rs.getString("F_name") + " - ";
				result += rs.getString("City") + "\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

    	return result;
    }

    public String showAllCustomers() {
    	String query = (
    			"SELECT * " +
				"FROM Customer");
    	ResultSet rs = executeQuery(query);

    	String result = "Show All Customers:\n\n";
    	try {
			while(rs.next()) {
				result += "";
				result += rs.getString("Customerid") + ": ";
				result += rs.getString("L_Name") + ",";
				result += rs.getString("F_name") + " - ";
				result += rs.getString("City") + "\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

    	return result;
    }

    public String borrowBook(int isbn, int customerID,
			     int day, int month, int year) {
	return "Borrow Book Stub";
    }

    public String returnBook(int isbn, int customerid) {
	return "Return Book Stub";
    }

    public void closeDBConnection() {
    }

    public String deleteCus(int customerID) {
    	return "Delete Customer";
    }

    public String deleteAuthor(int authorID) {
    	return "Delete Author";
    }

    public String deleteBook(int isbn) {
    	return "Delete Book";
    }

    private ResultSet executeQuery(String query) {
    	Statement s = null;

    	try{
    		s = con.createStatement();
		}
		catch (SQLException sqlex){
			System.out.println("An exception"+
			"while creating a statement,"+
			"probably means I am no longer"+
			"connected");
		}

    	ResultSet rs = null;

    	try{
    		rs = s.executeQuery(query);
		}
		catch (SQLException sqlex){
			System.out.println("An exception "+
			"while executing a query, probably "+
			"means my SQL is invalid");
		}

    	return rs;
    }
}