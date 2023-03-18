package gestione.salvataggio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import gestione.biblioteca.Libro;

/**
 * Classe dedicata alla serializzazione
 * 
 * @author Torrisi Gaetano
 * @version 2.0
 */

public class Serializzazione 
{
	private static final String FILE_NAME = "libreria.bin";
	private ObjectOutputStream outputStream;
	
	/**
	 * costruttore vuoto
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Serializzazione() throws IOException, ClassNotFoundException 
	{
		
	}

	/**
	 * costruttore con parametri
	 * 
	 * @param outputStream
	 */
	public Serializzazione(ObjectOutputStream outputStream) 
	{
		this.outputStream = outputStream;
	}
	
	/**
	 *  restituisce l'oggetto di tipo ObjectOutputStream
	 * 
	 * @return ObjectOutputStream
	 */
	public ObjectOutputStream getOutputStream() 
	{
		return outputStream;
	}

	/**
	 * imposta l'ggetto ObjectOutputStream in base al parametro passato
	 * 
	 * @param outputStream
	 */
	public void setOutputStream(ObjectOutputStream outputStream) 
	{
		this.outputStream = outputStream;
	}

	/**
	 * restituisce il nome del file
	 * 
	 * @return FILE_NAME
	 */
	public static String getFileName() 
	{
		return FILE_NAME;
	}
	
	/**
	 * Metodo dedicato alla serializzazione degli attributi 
	 * della superclasse Libreria
	 */
	public void serializza(ArrayList<Libro> libri)
	{
		try {			
			outputStream = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
			
			outputStream.writeObject(libri);
			
			outputStream.close();
			
			System.err.print("Salvataggio libri avvenuto con successo!\n\n");
		} catch (IOException e) {
			System.err.println("ERRORE salvataggio libi.");
		}	
	}
}
