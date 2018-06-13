package br.HotelSolution.com.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;
import java.io.IOException;

public class ConnectionFactory 
{
	private String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private String connectionStr = "jdbc:sqlserver://%s;databaseName=%s";
	private String usuario = "";
	private String senha = "";

	private static ConnectionFactory connectionFactory = null;
	
	private String getNomeArquivoConfig()
	{
		String sResult = "";
		try
		{
			sResult = new File(".").getCanonicalPath() + "\\Conexao.INI";
		}
		catch(Exception e)
		{
			System.out.println("Exception - getNomeArquivoConfig");
			e.printStackTrace();
		}
		return sResult;
	}

	private ConnectionFactory()
	{
	   try
	   {
		   Class.forName(driver);
	   }
	   catch(ClassNotFoundException e)
	   {
		   System.out.println("ClassNotFoundException - Constructor - ConnectionFactory");
		   e.printStackTrace();
	   }
	   
	   String sArquivo = getNomeArquivoConfig();
	   File fArqConfig = new File(sArquivo);
	   
	   if (fArqConfig.exists())
	   {
		   try
		   {
			   IniFile iniFile = new IniFile(sArquivo);
			   usuario = iniFile.getString("BD", "USER", "sa");
			   senha = iniFile.getString("BD", "PASSWORD", "masterkey");
			   connectionStr = String.format(connectionStr, iniFile.getString("BD", "SERVER", "PCEDER"), iniFile.getString("BD", "DATABASE", "HOTELBD"));
		   }
		   catch(IOException e) 
		   {
			   System.out.println("IOException - Constructor - ConnectionFactory");
			   e.printStackTrace();
		   }
	   }
	   else
		   System.out.println("Não foi encontrado o arquivo " + sArquivo);
	}

	public Connection getConnection() throws SQLException
	{
	   Connection conn = null;
	   try
	   {
		   conn = DriverManager.getConnection(connectionStr, usuario, senha);
	   }
	   catch(SQLException e)
	   {
		   System.out.println("SQLException - getConnection");
		   e.printStackTrace();
		   throw e;
	   }
	   catch(Exception e)
	   {
		   System.out.println("Exception - getConnection");
		   e.printStackTrace();
		   throw e;
	   }
	   return conn;
	}

	public static ConnectionFactory getInstance()
	{
	   if(connectionFactory == null)
		   connectionFactory = new ConnectionFactory();
	   return connectionFactory;
    }
}