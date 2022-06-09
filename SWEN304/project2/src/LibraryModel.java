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
	private boolean conected = false;

	public LibraryModel(JFrame parent, String userid, String password) {
		dialogParent = parent;

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Can not find" +
					"the driver class: " +
					"\nEither I have not installed it"
					+ "properly or \n postgresql.jar "
					+ " file is not in my CLASSPATH");
		}

		String host = "db.ecs.ac.nz";
		int port = 5432;

		String url = "jdbc:postgresql:" + "//" + host + "/" + userid + "_jdbc";

		try {
			con = DriverManager.getConnection(url, userid, password);
			System.out.println("connected");
			conected = true;
		} catch (SQLException sqlex) {
			System.out.println(sqlex.getMessage());

			/*System.out.println("attempting to establish ssh port forwarding");
			PortForwarding.main(new String[]{});

			try {
				url = "jdbc:postgresql://localhost:5432/" + userid + "_jdbc";
				con = DriverManager.getConnection(url, userid, password);
				con.setAutoCommit(false);
				System.out.println("connected");
			} catch (SQLException e) {
				System.out.println(e.getMessage());*/
				System.out.println("connecting to home pc database");

				try{
					url = "jdbc:postgresql://localhost:54335/300363269_JDBC";
					con = DriverManager.getConnection(url, userid, password);
					System.out.println("connected");
					conected = true;
				}catch (SQLException e2){
					System.out.println(e2.getMessage());
				}
			//}
		}
	}

	public String bookLookup(int isbn) {
		if(!conected){
			return "not connected to database";
		}

		String query =
				"SELECT * " +
				"FROM Book " +
				"WHERE ISBN = " + isbn;
		ResultSet rs = executeQuery(query);

		StringBuilder result = new StringBuilder("Book Lookup: \n");
		try {
			if(rs.next()) {
				String title = rs.getString("title");
				int edition = rs.getInt("Edition_No");
				int numOfCop = rs.getInt("NumOfCop");
				int numLeft = rs.getInt("NumLeft");
				showBook(result, isbn, title, edition, numOfCop, numLeft);
			} else {
				result.append("there is no book with ISBN: ").append(isbn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	private void showBook(StringBuilder s, int isbn, String title, int edition, int numOfCop, int numLeft){
		s.append(isbn).append(": ");
		s.append(title).append("\n");
		s.append("	Edition: ").append(edition);
		s.append(" - Number of copies: ").append(numOfCop);
		s.append(" - Copies left: ").append(numLeft);
		s.append("\n	");
		s.append(bookAuthors(isbn)).append("\n");
	}

	private String bookAuthors(int isbn){
		StringBuilder authors = new StringBuilder();
		String query =
				"SELECT name, surname, authorseqno " +
				"FROM author " +
				"natural join (SELECT authorid, authorseqno FROM book_author WHERE isbn = " + isbn + ") as baa " +
				"ORDER BY authorseqno";
		ResultSet rs = executeQuery(query);

		try {
			while (rs.next()) {
				String name = rs.getString("name").trim();
				String surname = rs.getString("surname").trim();
				authors.append(name).append(" ").append(surname);
				if(!rs.isLast()){
					authors.append(", ");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if(authors.toString().equals("")){
			return "(no authors)";
		}

		return "("+authors+")";
	}

	public String showCatalogue() {
		if(!conected){
			return "not connected to database";
		}

		String query = (
				"SELECT * " +
				"FROM Book");
		ResultSet rs = executeQuery(query);

		StringBuilder result = new StringBuilder("Show Catalogue:\n\n");
		try {
			while (rs.next()) {
				int isbn = rs.getInt("isbn");
				String title = rs.getString("title");
				int edition = rs.getInt("Edition_No");
				int numOfCop = rs.getInt("NumOfCop");
				int numLeft = rs.getInt("NumLeft");

				showBook(result, isbn, title, edition, numOfCop, numLeft);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	public String showLoanedBooks() {
		if(!conected){
			return "not connected to database";
		}

		String query = (
				"SELECT *" +
				"FROM (cust_book natural join book) natural join customer");
		ResultSet rs = executeQuery(query);

		StringBuilder result = new StringBuilder("Show loaned books:\n\n");
		try {
			while (rs.next()) {
				int isbn = rs.getInt("isbn");
				String title = rs.getString("title").trim();
				int edition = rs.getInt("Edition_No");
				int numOfCop = rs.getInt("NumOfCop");
				int numLeft = rs.getInt("NumLeft");

				showBook(result, isbn, title, edition, numOfCop, numLeft);

				int customerid = rs.getInt("customerid");
				String l_name = rs.getString("l_name").trim();
				String f_name = rs.getString("f_name").trim();
				String city = rs.getString("city").trim();
				String duedate = rs.getString("duedate");

				result.append("loaned by: ");
				result.append(f_name).append(" ");
				result.append(l_name).append(" - ");
				result.append(city);
				result.append(" due on: ").append(duedate).append("\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	public String showAuthor(int authorID) {
		if(!conected){
			return "not connected to database";
		}

		String query = (
				"SELECT * " +
				"FROM Author " +
				"WHERE authorID = " + authorID);
		ResultSet rs = executeQuery(query);

		StringBuilder result = new StringBuilder("Show Author:\n\n");
		try {
			if (rs.next()) {
				result.append(rs.getString("AuthorId")).append(": ");
				result.append(rs.getString("Name").trim()).append(", ");
				result.append(rs.getString("Surname")).append("\n");
			} else {
				result.append("there is no author with ID: ").append(authorID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	public String showAllAuthors() {
		if(!conected){
			return "not connected to database";
		}

		String query = (
				"SELECT * " +
				"FROM Author");
		ResultSet rs = executeQuery(query);

		StringBuilder result = new StringBuilder("Show All Authors:\n\n");
		try {
			while (rs.next()) {
				result.append(rs.getString("AuthorId")).append(": ");
				result.append(rs.getString("Name").trim()).append(", ");
				result.append(rs.getString("Surname")).append("\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	public String showCustomer(int customerID) {
		if(!conected){
			return "not connected to database";
		}

		String query = (
				"SELECT * " +
				"FROM Customer " +
				"WHERE customerid = " + customerID);
		ResultSet rs = executeQuery(query);

		StringBuilder result = new StringBuilder("Show Customer:\n\n");
		try {
			if (rs.next()) {
				result.append("");
				result.append(rs.getString("CustomerID")).append(": ");
				result.append(rs.getString("L_Name").trim()).append(", ");
				result.append(rs.getString("F_name").trim()).append(" - ");
				result.append(rs.getString("City")).append("\n");
			} else {
				result.append("there is no customer with ID: ").append(customerID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	public String showAllCustomers() {
		if(!conected){
			return "not connected to database";
		}

		String query = (
				"SELECT * " +
				"FROM Customer");
		ResultSet rs = executeQuery(query);

		StringBuilder result = new StringBuilder("Show All Customers:\n\n");
		try {
			while (rs.next()) {
				result.append("");
				result.append(rs.getString("Customerid")).append(": ");
				result.append(rs.getString("L_Name").trim()).append(", ");
				result.append(rs.getString("F_name").trim()).append(" - ");
				result.append(rs.getString("City")).append("\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	public String borrowBook(int isbn, int customerID,
							 int day, int month, int year) {
		if(!conected){
			return "not connected to database";
		}

		String select_c = (
				"SELECT * " +
				"FROM Customer " +
				"WHERE customerid = " + customerID);

		String select_b = (
				"SELECT * " +
				"FROM Book " +
				"WHERE ISBN = " + isbn);

		String insertLoanQuery = (
				"INSERT INTO cust_book VALUES (" +
				isbn + ", '" + year + "-" + month + "-" + day + "', " + customerID +
				")");

		String updateBookQuery = (
				"UPDATE book " +
				"SET numleft = numleft - 1 " +
				"WHERE isbn = " + isbn);

		try {
			con.setAutoCommit(false);
			String result = "";
			boolean success = true;
			ResultSet c_rs = executeQuery(select_c);
			ResultSet b_rs = executeQuery(select_b);

			if(!c_rs.next()){
				con.rollback();
				result += "Customer not available\n";
				success = false;
			}
			if(!b_rs.next()){
				con.rollback();
				result += "Book not found\n";
				success = false;
			} else if (b_rs.getInt("NumLeft") <= 0){
				con.rollback();
				result += "no copies available\n";
				success = false;
			}

			if(!success){
				con.rollback();
				return result;
			}

			ResultSet i_rs = executeQuery(insertLoanQuery);

			Object[] options={ "yes/ok" };
			JOptionPane.showOptionDialog(null,
					"continue?",
					"",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null, options, options[0]);

			ResultSet u_rs = executeQuery(updateBookQuery);

			con.commit();
			return ("borrowed book:" + b_rs.getString("title") +"\n" +
					"borrowed by: " + c_rs.getString("f_name").trim() + " "
					+ c_rs.getString("l_name").trim() + "\n" +
					"due on: " + year + "-" + month + "-" + day + "\n" +
					"there are " + (b_rs.getInt("numLeft") - 1) + " of " + b_rs.getInt("numofcop") +
					" copies remaining\n");

		} catch (SQLException throwables) {
			throwables.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}

		return "failed to borrow book";
	}

	public String returnBook(int isbn, int customerid) {
		if(!conected){
			return "not connected to database";
		}

		String select_c = (
				"SELECT * " +
				"FROM Customer " +
				"WHERE customerid = " + customerid);

		String select_b = (
				"SELECT * " +
				"FROM Book " +
				"WHERE ISBN = " + isbn);

		String select_cb = (
				"SELECT * " +
				"FROM cust_book " +
				"WHERE ISBN = " + isbn + " AND customerid = " + customerid
				);

		String deleteLoanQuery = (
				"DELETE FROM cust_book "+
				"WHERE isbn = " + isbn + " AND customerid = " + customerid
				);

		String updateBookQuery = (
				"UPDATE book " +
						"SET numleft = numleft + 1 " +
						"WHERE isbn = " + isbn);

		try {
			con.setAutoCommit(false);
			String result = "";
			boolean success = true;
			ResultSet c_rs = executeQuery(select_c);
			ResultSet b_rs = executeQuery(select_b);
			ResultSet cb_rs = executeQuery(select_cb);

			if(!c_rs.next()){
				con.rollback();
				result += "Customer not available\n";
				success = false;
			}
			if(!b_rs.next()){
				result += "Book not found\n";
				success = false;
			}
			if(!cb_rs.next()){
				result += "Loan not found\n";
				success = false;
			}

			if(!success){
				con.rollback();
				return result;
			}

			ResultSet d_rs = executeQuery(deleteLoanQuery);

			Object[] options={ "yes/ok" };
			JOptionPane.showOptionDialog(null,
					"continue?",
					"",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null, options, options[0]);

			ResultSet u_rs = executeQuery(updateBookQuery);

			con.commit();
			return ("returned book " + b_rs.getString("title") + "\n" +
					"borrowed by: " + c_rs.getString("f_name").trim() + " "
					+ c_rs.getString("l_name").trim() + "\n" +
					"there are " + (b_rs.getInt("numLeft") + 1) + " of " + b_rs.getInt("numofcop") +
					" copies remaining\n");

		} catch (SQLException throwables) {
			throwables.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}

		return "Failed to return book";
	}

	public void closeDBConnection() {
		if(conected) {
			try {
				con.close();
				conected = false;
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
	}

	public String deleteCus(int customerID) {
		if(!conected){
			return "not connected to database";
		}

		String select_c = (
				"SELECT * " +
				"FROM Customer " +
				"WHERE customerid = " + customerID);

		String delete_c = (
				"DELETE FROM Customer "+
				"WHERE customerid = " + customerID
		);


		try {
			con.setAutoCommit(false);
			ResultSet s_rs = executeQuery(select_c);
			if(s_rs.next()){
				Statement s = con.createStatement();
				s.executeQuery(delete_c);
				con.commit();
				return "customer: " + customerID + " deleted";
			}else {
				con.rollback();
				return "customer: " + customerID + " not found";
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
		return "Delete Customer Failed";
	}

	public String deleteAuthor(int authorID) {
		String select_a = (
				"SELECT * " +
				"FROM Author " +
				"WHERE authorid = " + authorID);

		String delete_a = (
				"DELETE FROM author "+
				"WHERE authorid = " + authorID
		);


		try {
			con.setAutoCommit(false);
			ResultSet s_rs = executeQuery(select_a);
			if(s_rs.next()){
				Statement s = con.createStatement();
				s.executeQuery(delete_a);
				con.commit();
				return "author: " + authorID + " deleted";
			}else {
				con.rollback();
				return "author: " + authorID + " not found";
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
		return "Delete Author Failed";
	}

	public String deleteBook(int isbn) {
		if(!conected){
			return "not connected to database";
		}

		String select_b = (
				"SELECT * " +
				"FROM Book " +
				"WHERE isbn = " + isbn);

		String delete_b = (
				"DELETE FROM Book "+
				"WHERE isbn = " + isbn
		);


		try {
			con.setAutoCommit(false);
			ResultSet s_rs = executeQuery(select_b);
			if(s_rs.next()){
				Statement s = con.createStatement();
				s.executeQuery(delete_b);
				con.commit();
				return "book: " + isbn + " deleted";
			}else {
				con.rollback();
				return "book: " + isbn + " not found";
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
		return "Delete Book Failed";
	}

	private ResultSet executeQuery(String query) {
		Statement s = null;

		try {
			s = con.createStatement();
		} catch (SQLException sqlex) {
			System.out.println("An exception" +
					"while creating a statement," +
					"probably means I am no longer" +
					"connected");
			sqlex.printStackTrace();
		}

		ResultSet rs = null;

		try {
			rs = s.executeQuery(query);
		} catch (SQLException sqlex) {
			System.out.println("An exception " +
					"while executing a query, probably " +
					"means my SQL is invalid");
			sqlex.printStackTrace();
		}

		return rs;
	}
}

