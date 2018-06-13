package br.HotelSolution.com.java;

import java.sql.*;

public class prjHotelSolution 
{
	private static Connection conn;
	
	private static void gravarLog(String pMsg)
	{
		System.out.println(pMsg);
	}
	
	public static void main(String[] args)
	{
		Timer tempoProc = new Timer();
		gravarLog("Realizando processamento - Início");
		try
		{
			EfetuarConexaoBanco();
			cadastrarConfiguracoesPadrao();
		}
		finally
		{
			gravarLog( String.format("Realizando processamento - Fim - Tempo de Processamento: %s", tempoProc.toString()));
		}
	}
	
	private static void EfetuarConexaoBanco() 
	{
		try 
		{
			gravarLog("Efetuando conexão com o banco de dados - Início");
			conn = ConnectionFactory.getInstance().getConnection();
			gravarLog("Efetuando conexão com o banco de dados - Fim");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	private static void cadastrarConfiguracoesPadrao()
	{
		Timer tempoProc = new Timer();
		gravarLog("Efetuando cadastramento das configurações padrão no banco de dados - Início");
		String query = 
				"Declare @Id TinyInt\r\n" + 
				"Select @Id = ?\r\n" +
				"If Not Exists( Select 1 From TB_CONF Where ID_CONF = @Id)\r\n" + 
				"  Insert Into TB_CONF( ID_CONF, NM_HOTEL, NM_CNPJ, ID_CID) Values ( @Id, ?, ?, ?)";

		PreparedStatement ps;
		try 
		{
			ps = conn.prepareStatement(query);
			ps.setInt(1, 1);
			ps.setString(2, "Pousada Rainha do Mar");
			ps.setString(3, "00.000.000/0000-00");
			ps.setInt(4, 3644);
			ps.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			gravarLog( String.format("Efetuando cadastramento das configurações padrão no banco de dados - Fim - Tempo de Processamento: %s", tempoProc.toString()));
		}
	}
}