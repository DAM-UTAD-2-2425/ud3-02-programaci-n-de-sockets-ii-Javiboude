package servidor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * TODO: Complementa esta clase para que acepte conexiones TCP con clientes para
 * recibir un boleto, generar la respuesta y finalizar la sesion
 */
public class ServidorTCP {

	private Socket socketCliente; // Socket para la conexión del cliente
	private ServerSocket socketServidor; // Socket del servidor para escuchar conexiones
	private BufferedReader entrada; // Flujo de entrada para recibir mensajes del cliente
	private PrintWriter salida; // Flujo de salida para enviar mensajes al cliente

	private String[] respuesta;
	private int[] combinacion;
	private int reintegro;
	private int complementario;

	/**
	 * Constructor
	 */
	public ServidorTCP(int puerto) {
		this.respuesta = new String[9];
		this.respuesta[0] = "Boleto inv�lido - N�meros repetidos";
		this.respuesta[1] = "Boleto inv�lido - n�meros incorretos (1-49)";
		this.respuesta[2] = "6 aciertos";
		this.respuesta[3] = "5 aciertos + complementario";
		this.respuesta[4] = "5 aciertos";
		this.respuesta[5] = "4 aciertos";
		this.respuesta[6] = "3 aciertos";
		this.respuesta[7] = "Reintegro";
		this.respuesta[8] = "Sin premio";
		generarCombinacion();
		imprimirCombinacion();
		this.socketCliente = null;
		this.socketServidor = null;
		this.entrada = null;
		this.salida = null;
		try {
			// Se crea el servidor que escucha en el puerto indicado
			socketServidor = new ServerSocket(puerto);
			System.out.println("Esperando conexión...");

			// Se espera una conexión del cliente
			socketCliente = socketServidor.accept();
			System.out.println("Conexión aceptada: " + socketCliente);

			// Se inicializan los flujos de entrada y salida
			entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
			salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())), true);
		} catch (IOException e) {
			// Si ocurre un error, se informa y el programa termina
			System.out.println("No puede escuchar en el puerto: " + puerto);
			System.exit(-1);
		}
	}

	/**
	 * @return Debe leer la combinacion de numeros que le envia el cliente
	 */
	public String leerCombinacion() {
		String linea = "";
		try {
			// Se lee una línea del flujo de entrada
			linea = entrada.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return linea; // Devuelve la línea recibida
	}

	/**
	 * @return Debe devolver una de las posibles respuestas configuradas
	 */
	public String comprobarBoleto() {
		String respuesta = " ";
		for (Integer elto : this.combinacion) {
			respuesta = respuesta.substring(elto);
		}
		return respuesta;
	}

	/**
	 * @param respuesta se debe enviar al ciente
	 */
	public void enviarRespuesta(String respuesta) {

		if (leerCombinacion().equals(respuesta)) {
			respuesta = "6 aciertos";
		} else if(!leerCombinacion().equals(respuesta)){
			respuesta = "Sin premio";
		}
		salida.println(respuesta);
	}

	/**
	 * Cierra el servidor
	 */
	public void finSesion() {
		try {
			// Se cierran los flujos, el socket del cliente y el del servidor
			salida.close();
			entrada.close();
			socketCliente.close();
			socketServidor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("-> Servidor Terminado");
	}

	/**
	 * Metodo que genera una combinacion. NO MODIFICAR
	 */
	private void generarCombinacion() {
		Set<Integer> numeros = new TreeSet<Integer>();
		Random aleatorio = new Random();
		while (numeros.size() < 6) {
			numeros.add(aleatorio.nextInt(49) + 1);
		}
		int i = 0;
		this.combinacion = new int[6];
		for (Integer elto : numeros) {
			this.combinacion[i++] = elto;
		}
		this.reintegro = aleatorio.nextInt(49) + 1;
		this.complementario = aleatorio.nextInt(49) + 1;
	}

	/**
	 * Metodo que saca por consola del servidor la combinacion
	 */
	private void imprimirCombinacion() {
		System.out.print("Combinaci�n ganadora: ");
		for (Integer elto : this.combinacion)
			System.out.print(elto + " ");
		System.out.println("");
		System.out.println("Complementario:       " + this.complementario);
		System.out.println("Reintegro:            " + this.reintegro);
	}

}
