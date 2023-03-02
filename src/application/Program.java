package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;
import db.DbIntegrityException;

// Deletar Dados
public class Program {

	// DbIntegrityException (excessão personalizada)
	public static void main(String[] args) {

		Connection ct = null;
		PreparedStatement pst = null;
		
		try {
			ct = DB.getConnection();
			
			pst = ct.prepareStatement(
					"DELETE FROM department " + 
					"WHERE (Id = ?)");
					
			//(pos da ?, e o valor) 
			pst.setInt(1, 2);
			
			int rowsAffected = pst.executeUpdate();
			System.out.println("Done! Rows affected: " + rowsAffected);
			
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			DB.closeStatement(pst);
			DB.closeConnection();
		}
	}
}