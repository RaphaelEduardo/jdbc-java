package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

// Transa��es
public class Program {

	/* 
	 * Garantir a consist�ncia do DB com: 
	 * ATOMICIDADE, CONSISTENCIA, ISOLAMENTO E DURABILIDADE	
	 * 
	 * setAutoCommit(false) - cada opera��o feita isoladamente
	 * ser� confirmada automaticamente.
	 * 
	 * commit() - confirma a opera��o.
	 * 
	 * rollback() - desfaz oque j� foi feito at� o momento
	 * (usado em caso de falha/problemas).
	*/
	public static void main(String[] args) {

		Connection ct = null;
		Statement st = null;
		
		try {
			ct = DB.getConnection();
	
			/*
			 *  N�o � para confirmar as atualiza��es automaticamente.
			 *  Vai ficar pendente a uma confirma��o explicita do programador
			 */
	
			ct.setAutoCommit(false);
			
			st = ct.createStatement();
	
			int rows1 = st.executeUpdate(
					"UPDATE seller "
					+ "SET BaseSalary = 2090 "
					+ "WHERE DepartmentId = 1");
			
			/*
			 * int x = 1;
			 * if (x < 2) {
			 *	throw new SQLException("Fake error!");
			 * }
			 */
			
			
			int rows2 = st.executeUpdate(
					"UPDATE seller "
					+ "SET BaseSalary = 3090 "
					+ "WHERE DepartmentId = 2");
			
			
			/*
			 *  Confirma��o do explicita programador
			 *  todo o bloco acima fica protegido por uma l�gica de transa��o.
			 */
			ct.commit();
			
			System.out.println("rows1 " + rows1);
			
			
			System.out.println("rows2 " + rows2);
			
		}
		// L�gica para voltar a transa��o caso tenha sido interrompida no meio...
		catch (SQLException e) {
			try {
				ct.rollback();
				throw new DbException("Transaction rolled back! Caused by " + e.getMessage());
			} catch (SQLException e1) {
				// se der um erro no rollback
				throw new DbException("Error trying to rollback! Caused by: " + e.getMessage());
			}
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
}