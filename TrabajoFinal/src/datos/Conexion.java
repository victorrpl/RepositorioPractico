package datos;

import java.sql.*;//se importa el paquete sql

public class Conexion {

	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";//driver de JDBC, para establecer el tipo de base de datos que se usara
	private static String JDBC_URL = "jdbc:mysql://localhost:3306/final18-1018?useSSL=false";//url o datos de la base de datos a conectar
	private static String JDBC_USER = "root";//usuario
	private static String JDBC_PASS = "admin";//contrasena.
	private static Driver driver = null;//se declara una variable de tipo Driver
    
	//Metodo establecido para obtener una conexion a la base de datos de mysql.
	public static synchronized Connection getConnection()
	throws SQLException {//al ser un metodo de respuesta, posiblemente arroje una excepcion y debe ir dentro de un try catch.
	if (driver == null) {//si la variable driver es igual a null, se hace lo siguiente
	try {//abrimos el try catch
	Class jdbcDriverClass = Class.forName(JDBC_DRIVER);//creamos una clase para pasarle el driver de la conexion
	driver = (Driver) jdbcDriverClass.newInstance();//a traves de la variable de tipo Driver, se hace un Casting o conversion de objeto
	//para poder solicitar una nueva instancia de nuestra conexion a la base de datos
	DriverManager.registerDriver(driver);//se registra el Driver obtenido en el paso anterior
	} catch (Exception e) {
	System.out.println("Fallo en cargar el driver JDBC");//si ocurre una excepcion, se mostrara en consola lo siquiente
	e.printStackTrace();//en caso de exception, se implime la pila de errores.
	}
	}
	return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);//se retornan los elementos claves para la conexion a la DB.
	}
	//A continuacion se establece el metodo Close o  cerrar, por si casualidad se necesiten para cerrar alguna variable que corresponda 
	//a ese tipo, como Connection, PreparedStatement o  ResultSet.
	public static void close(ResultSet rs) {//cierra un tipo ResultSet.
		try {
			if(rs != null) {
				rs.close();//si el resultSet es distinto de null, entonces se llama su metodo close para cerrarlo
				}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	public static void close(PreparedStatement stmt) {//cierra un tipo PreparedStatement
		try {
			if(stmt != null) {
				stmt.close();
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
		public static void close(Connection conn) {//Cierra un tipo Connection
			try {
				if(conn != null) {
					conn.close();
				}
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
}
