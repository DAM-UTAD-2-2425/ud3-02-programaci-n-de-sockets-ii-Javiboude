package servidor;

public class PrimitivaServidor {

	public static void main(String[] args) {
		ServidorTCP canal = new ServidorTCP(5555);
		String linea;
		String respuesta;
		do {
			linea = canal.leerCombinacion();
			respuesta = canal.comprobarBoleto();
			canal.enviarRespuesta(respuesta);
			System.out.println(canal.comprobarBoleto());
			System.out.print("Si no desea jugar escriba 'FIN'");
		} while (!linea.equals("FIN"));
		canal.finSesion();

	}

}
