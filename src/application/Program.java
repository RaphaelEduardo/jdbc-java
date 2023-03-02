package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

// Transações
public class Program {

	/* 
	 * Garantir a consistência do DB com: 
	 * ATOMICIDADE, CONSISTENCIA, ISOLAMENTO E DURABILIDADE	
	 * 
	 * setAutoCommit(false) - cada operação feita isoladamente
	 * será confirmada automaticamente.
	 * 
	 * commit() - confirma a operação.
	 * 
	 * rollback() - desfaz oque já foi feito até o momento
	 * (usado em caso de falha/problemas).
	*/
	public static void main(String[] args) {

		Connection ct = null;
		Statement st = null;
		
		try {
			ct = DB.getConnection();
	
			/*
			 *  Não é para confirmar as atualizações automaticamente.
			 *  Vai ficar pendente a uma confirmação explicita do programador
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
			 *  Confirmação do explicita programador
			 *  todo o bloco acima fica protegido por uma lógica de transação.
			 */
			ct.commit();
			
			System.out.println("rows1 " + rows1);
			
			
			System.out.println("rows2 " + rows2);
			
		}
		// Lógica para voltar a transação caso tenha sido interrompida no meio...
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