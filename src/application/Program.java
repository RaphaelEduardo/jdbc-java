package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;

//Recuperar Dados
public class Program {
	
	//Statement e ResultSet
	public static void main(String[] args) {

		// Conecta o banco
		Connection connection = null;

		// Prepara um consulta pra buscar todos os departamentos
		Statement statement = null;

		// Guarda o resultado da consulta
		ResultSet resultSet = null;

		try {

			connection = DB.getConnection();
			statement = connection.createStatement();

			resultSet = statement.executeQuery("select * from department");

			while (resultSet.next()) {
				System.out.println(resultSet.getInt("Id") + ", " + resultSet.getString("Name"));
			}

		} catch (SQLException e) {
			e.getStackTrace();
		}
		// é boa prática fechar manualmente, para evitar erros.
		finally {
			DB.closeResultSet(resultSet);
			DB.closeStatement(statement);
			DB.closeConnection();
		}
	}

}
