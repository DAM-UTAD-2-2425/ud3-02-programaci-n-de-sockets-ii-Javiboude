package cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * TODO: Complementa esta clase para que genere la conexi�n TCP con el servidor
 * para enviar un boleto, recibir la respuesta y finalizar la sesion
 */
public class ClienteTCP {

	private Socket socketCliente = null; // Socket para la conexión del cliente
	private BufferedReader entrada = null; // Flujo de entrada para recibir mensajes
	private PrintWriter salida = null; // Flujo de salida para enviar mensajes

	/**
	 * Constructor
	 */
	public ClienteTCP(String ip, int puerto) {
		try {
			// Se crea el socket con la IP y el puerto proporcionados
			socketCliente = new Socket(ip, puerto);
			System.out.println("Conexión establecida: " + socketCliente);

			// Se inicializan los flujos de entrada y salida
			entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
			salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())), true);
		} catch (IOException e) {
			// Si ocurre un error, se imprime un mensaje y se termina el programa
			System.err.printf("Imposible conectar con ip:%s / puerto:%d", ip, puerto);
			System.exit(-1);
		}
	}
	

		
	/**
	 * @param combinacion que se desea enviar
	 * @return respuesta del servidor con la respuesta del boleto
	 */
		public String comprobarBoleto(int[] combinacion) {
		
			String respuesta = null ;	
		for (int i = 0; i < combinacion.length; i++) {
			respuesta = combinacion[i];
		}
		return respuesta;
	}

	/**
	 * Sirve para finalizar la la conexi�n de Cliente y Servidor
	 */
	public void finSesion() {
		try {
			// Se cierran los flujos y el socket
			salida.close();
			entrada.close();
			socketCliente.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("-> Cliente Terminado");
	}

}
