package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;

// Atualizar Dados
public class Program {

	// PreparedStatement
	public static void main(String[] args) {

		Connection ct = null;
		PreparedStatement pst = null;
		
		try {
			ct = DB.getConnection();
			pst = ct.prepareStatement(
					"UPDATE seller " + 
					"SET BaseSalary = ? " +
					"WHERE (DepartmentId = ?)");
					
			pst.setDouble(1, 200.00);
			pst.setInt(2, 2);
			
			int rowsAffected = pst.executeUpdate();
			System.out.println("Done! Rows affected: " + rowsAffected);
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeStatement(pst);
			DB.closeConnection();
		}
	}
}