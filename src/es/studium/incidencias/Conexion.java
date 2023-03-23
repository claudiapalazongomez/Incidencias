package es.studium.incidencias;

import java.awt.Choice;
import java.awt.TextArea;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion //aquí metemos todo lo relacionado a la conexión con la bbdd
{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/incidencias";
	String login = "userIncidencias";
	String password = "Studium2023;";
	String sentencia = "";

	Connection connection = null; //para conectarnos a la BD
	Statement statement = null; //para lanzar o ejecutar una sentencia de la BD
	ResultSet rs = null; //para guardar la información que nos devuelve la BD
	
	Conexion(){
		connection = this.conectar(); //para que en el login al crear el objeto, se nos conecte directamente
	}
	
	//Funciones necesarias para nuestro programa
	public Connection conectar() {
		try
		{
			// Cargar los controladores para el acceso a la BD
			Class.forName(driver); 
			// Establecer la conexión con la BD incidencias
			return(DriverManager.getConnection(url, login, password)); //devuelve un objeto de la clase Connection
		}

		catch (ClassNotFoundException cnfe)
		{
			System.out.println("Error 1-"+cnfe.getMessage());
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 2-"+sqle.getMessage());
		}
		return null;
	}
	
	public int comprobarCredenciales(String u, String c) { //usuario y clave del usuario
		String cadena = "SELECT * FROM usuarios WHERE nombreUsuario = '" + u + "' AND claveUsuario = SHA2('" + c + "',256);"; //se indica con SHA2 256 la encriptación de la clave (en este caso)
		try {
			// Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); //permirte moverse hacia delante y hacia atrás con una visión dinámica de los datos. no permite actualización del contenido del ResultSet
			// Crear un objeto ResultSet para guardar lo obtenido
			// y ejecutar la sentencia SQL
			rs = statement.executeQuery(cadena); 
			if(rs.next()) { //para que salte al primer elemento porque el primero es BOR(null)
				return rs.getInt("tipoUsuario"); //si las credenciales son correctas (1)
			}
			else {
				return -1; //si son incorrectas (0)
			}
		}
			catch (SQLException sqle)
			{
				System.out.println("Error 3-"+sqle.getMessage());
			}
			return -1;
				
	}

	public int altaUsuario(String sentencia)
	{
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			statement.executeUpdate(sentencia);
			return 0; //si todo va bien
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 4-"+sqle.getMessage());
			return 1;
		}
	}

	public void rellenarListadoUsuarios(TextArea txaListado)
	{
		String sentencia = "SELECT idUsuario, nombreUsuario, emailUsuario FROM usuarios;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			// En resultado metemos todo lo que queremos almacenar en sentencia
			ResultSet resultado = statement.executeQuery(sentencia);
			while(resultado.next()) {
				txaListado.append(resultado.getString("idUsuario")+" "); 
				txaListado.append(resultado.getString("nombreUsuario")+" ");
				txaListado.append(resultado.getString("emailUsuario")+"\n");
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 5-"+sqle.getMessage());
		}
	}

	public void rellenarChoiceUsuarios(Choice choUsuarios)
	{
		String sentencia = "SELECT idUsuario, nombreUsuario FROM usuarios ORDER BY 1;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			// En resultado metemos todo lo que queremos almacenar en sentencia
			ResultSet resultado = statement.executeQuery(sentencia);
			choUsuarios.add("Elegir usuario...");
			while(resultado.next()) {
				choUsuarios.add(resultado.getString("idUsuario")+"-"+resultado.getString("nombreUsuario")); 
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 6-"+sqle.getMessage());
		}
	}

	public int eliminarUsuario(String idUsuario)
	{
		String sentencia = "DELETE FROM usuarios WHERE idUsuario = " + idUsuario;
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			statement.executeUpdate(sentencia);
			return 0; 
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 7-"+sqle.getMessage());
			return 1;
		}
	}
	
	public String getDatosEdicion(String idUsuario)
	{
		String resultado = "";
		String sentencia = "SELECT * FROM usuarios WHERE idUsuario = " + idUsuario;
		try
		{
			// Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Crear un objeto ResultSet para guardar lo obtenido
			// y ejecutar la sentencia SQL
			ResultSet resultSet = statement.executeQuery(sentencia);
			resultSet.next();
			resultado =(resultSet.getString("idUsuario")+"-"+resultSet.getString("nombreUsuario")+"-"+resultSet.getString("claveUsuario")+"-"+resultSet.getString("emailUsuario"));
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 8-"+sqle.getMessage());
		}
		return resultado;
	}

	public int modificarUsuario(String sentencia)
	{
		try
		{
			// Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Ejecutar la sentencia SQL
			statement.executeUpdate(sentencia);
			return 0;
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 9-"+sqle.getMessage());
			return 1;
		}
	}
}
