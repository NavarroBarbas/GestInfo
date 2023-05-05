import java.sql.*;
import java.util.Scanner;

public class GestInfo {

	public static void main(String[] args) {
		try {		
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ordenadores", "edib", "edib");
			
			Scanner teclado = new Scanner(System.in);
			boolean estado = true;
			while(estado) {
				System.out.println("******** MENÚ ********\n");
				System.out.println("1. Listar Ordenadores");
				System.out.println("2. Insertar Ordenador Nuevo");
				System.out.println("3. Listar Ubicaciones");
				System.out.println("4. Modificar Ubicación de Ordenador");
				System.out.println("5. Eliminar Ordenador");
				System.out.println("6. Salir\n");
				
				System.out.print("Elige tu opción: ");
				int opcion = teclado.nextInt();
				teclado.nextLine();
				System.out.println();
				
				switch (opcion) {
				case 1:
					if(MetodoOrdenador.listarOrdenadores(con, "ordenadores") == "") {
						System.out.println("No se ha añadido ningún ordenador\n");
					
					} else {
						System.out.println("**** Lista De Ordenadores ****");
						System.out.println(MetodoOrdenador.listarOrdenadores(con, "ordenadores"));
												
						System.out.print("Desea guardar la lista? (s/n): ");
						String letra = teclado.nextLine();
						System.out.println();
						letra.toLowerCase();
						
						if (letra.equals("s")) {
							MetodoOrdenador.guardarListado(MetodoOrdenador.listarOrdenadores(con, "ordenadores"));
						}
					}
					break;
					
				case 2:
					System.out.print("Marca: ");
					String marca = teclado.nextLine();
					if(marca.equals("exit")) break;
					
					System.out.print("Modelo: ");
					String modelo = teclado.nextLine();
					if(modelo.equals("exit")) break;
					
					System.out.print("Procesador: ");
					String procesador = teclado.nextLine();
					if(procesador.equals("exit")) break;
					
					System.out.print("Tipo de Memoria: ");
					String tipoMemoria = teclado.nextLine();
					if(tipoMemoria.equals("exit")) break;
					
					System.out.print("Cantidad de Memoria: ");
					int cantidadMemoria = teclado.nextInt();
					teclado.nextLine();
					
					System.out.print("Ubicación del ordenador: ");
					String ubicacion = teclado.nextLine();
					if(ubicacion.equals("exit")) break;
					
					System.out.print("Número de serie: ");
					String numeroSerie = teclado.nextLine();
					if(numeroSerie.equals("exit")) break;
						
					MetodoOrdenador.insertarOrdenador(con, "ordenadores", marca, modelo,
							procesador, tipoMemoria, cantidadMemoria, 
							ubicacion, numeroSerie);
					break;

				case 3:
					MetodoOrdenador.listarUbicaciones(con, "ordenadores");
					break;
					
				case 4:
					System.out.print("Inserte ID del ordenador: ");
					int id = teclado.nextInt();
					teclado.nextLine();
					
					if(MetodoOrdenador.compruebaID(con, "ordenadores", id) == 1) {
						System.out.print("Inserte la nueva ubicación: ");
						ubicacion = teclado.nextLine();
						
						System.out.println();
						MetodoOrdenador.cambiaUbicacion(con, "ordenadores", id, ubicacion);
					
					} else {
						System.out.println("\nEl ordenador no existe, pruebe otro ID\n");
					};
					
					break;
					
				case 5:
					System.out.print("Inserte ID del ordenador a eliminar: ");
					id = teclado.nextInt();
					teclado.nextLine();
					
					MetodoOrdenador.eliminaOrdenador(con, "ordenadores", id);
					break;
	
				case 6:
					System.out.println("Adiós");
					
					estado = false;
					break;
				}			
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}