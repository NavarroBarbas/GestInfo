import java.io.*;
import java.sql.*;
public class MetodoOrdenador {

	public static String listarOrdenadores(Connection con, String BDNombre)
			throws SQLException {
		Statement stmt = null;
		String lista = "";
		
		String query = "SELECT * FROM " + BDNombre + ".ordenadores";
		
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
						
			while (rs.next()) {
			lista += "\n******************************\n";
				
				int id = rs.getInt("id");
				String marca = rs.getString("marca");
				String modelo = rs.getString("modelo");
				String procesador = rs.getString("procesador");
				String tipoMemoria = rs.getString("tipoMemoria");
				int cantidadMemoria = rs.getInt("cantidadMemoria");
				String ubicacion = rs.getString("ubicacion");
				String numeroSerie = rs.getString("numeroSerie");
							
				
				lista += "ID: " + id + "\n" +
						"Marca: " + marca + "\n" +
						"Modelo: " + modelo + "\n" + 
						"Procesador: " + procesador + "\n" +
						"Tipo de Memoria: " + tipoMemoria + "\n" +
						"Cantidad de Memoria: " + cantidadMemoria + "\n" +
						"Ubicación del ordenador: " + ubicacion + "\n" +
						"Número de serie: " + numeroSerie + "\n";				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
		
		return lista;
	}
	
	public static void guardarListado(String lista) {
		File fs = null;
		File fd = null;
		
		try {
			fd= new File("C:/informes");
			if(!fd.exists()) {
				fd.mkdirs();
			}
			
			fs = new File("C:/informes/ordenadores.txt");
			
			if(!fs.exists()) {
				
				fs.createNewFile();
			}
			
			FileWriter fw = new FileWriter(fs);
			
			fw.write(lista);
			
			if (fw != null) {
				fw.close();
			}
			
			System.out.println("La lista ha sido guardada con éxito!\n");
			
		} catch (Exception e) {
			System.out.println("No se ha podido guardar el listado\n");
		}
	}
	
	public static void insertarOrdenador(Connection con, String BDNombre,
			String marca, String modelo, String procesador, String tipoMemoria,
			int cantidadMemoria, String ubicacion, String numeroSerie) 
			throws SQLException {
		
		Statement stmt = null;
		
		try {
			stmt = con.createStatement();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			ResultSet rs = stmt.executeQuery("SELECT * FROM " + BDNombre + ".ordenadores");
			rs.moveToInsertRow();
			
			rs.updateString("marca", marca);
			rs.updateString("modelo", modelo);
			rs.updateString("procesador", procesador);
			rs.updateString("tipoMemoria", tipoMemoria);
			rs.updateInt("cantidadMemoria", cantidadMemoria);
			rs.updateString("ubicacion", ubicacion);
			rs.updateString("numeroSerie", numeroSerie);
			
			rs.insertRow();
			
			System.out.println("");
			System.out.println("Se ha insertado correctamente el nuevo ordenador\n");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}
	
	public static void listarUbicaciones(Connection con, String BDNombre)
			throws SQLException {
		Statement stmt = null;
		
		String query = "SELECT DISTINCT ubicacion FROM " + BDNombre + ".ordenadores";
		
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			System.out.println("**** Lista De Ubicaciones ****\n");
			
			while (rs.next()) {
				String ubicacion = rs.getString("ubicacion");
				System.out.println(ubicacion);
			}
			
			System.out.println();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}
	
	public static void cambiaUbicacion(Connection con, String BDNombre, 
			int id, String ubicacion) throws SQLException {
		
		Statement stmt = null;
		
		try {
			stmt = con.createStatement();
			
			int total = compruebaID(con, "ordenadores", id);
			
			if(total == 1) {
				stmt.executeUpdate("UPDATE " + BDNombre + ".ordenadores "
					+ "SET ubicacion = '" + ubicacion + "' where id = " + id);
			
				System.out.println("Se ha modificado la ubicación del ordenador " + id
					+ " a '" + ubicacion + "' con exito\n");
			} else {
				System.out.println("\nEl ordenador no existe, pruebe otro ID\n");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}
	
	public static void eliminaOrdenador(Connection con, String BDNombre, int id)
			throws SQLException {
		Statement stmt = null;
		
		
		try {
			stmt = con.createStatement();
			
			
			int total = compruebaID(con, "ordenadores", id);			
			
			
			if(total == 1) {
				stmt.executeUpdate("DELETE FROM ordenadores WHERE id = " + id);
				System.out.println("\nEl ordenador ha sido borrado con éxito!\n");
			} else {
				System.out.println("\nEl ordenador no existe, pruebe otro ID\n");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}
	
	public static int compruebaID(Connection con, String BDNombre, int id)
			throws SQLException {
		String query = "SELECT id FROM ordenadores WHERE id = " + id;
		Statement stmt = null;
		int total = 0;
		
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			total = 0;
			while (rs.next()){
			   total++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
		
		return total;
	}
}
