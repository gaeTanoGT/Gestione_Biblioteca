package gestione.salvataggio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import gestione.biblioteca.Libro;

/**
 * Classe dedicata alla deserializzazione
 * 
 * @author Torrisi Gaetano
 * @version 2.0
 */

public class Deserializzazione
{
	private static final String FILE_NAME = "libreria.bin";
	private ObjectInputStream inputStream;

	/**
	 * Costruttore vuoto
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Deserializzazione() throws IOException, ClassNotFoundException 
	{

	}

	/**
	 * costruttore con parametro
	 * 
	 * @param inputStream
	 */
	public Deserializzazione(ObjectInputStream inputStream) 
	{
		this.inputStream = inputStream;
	}

	/**
	 * restituisce l'oggetto di tipo ObjectInputStream
	 * 
	 * @return inputStream
	 */
	public ObjectInputStream getInputStream() 
	{
		return inputStream;
	}

	/**
	 * imposta l'ggetto ObjectInputStream in base al parametro passato
	 * 
	 * @param inputStream
	 */
	public void setInputStream(ObjectInputStream inputStream) 
	{
		this.inputStream = inputStream;
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
	 * Metodo dedicato alla deserializzazione degli attributi 
	 * della superclasse Libreria
	 * 
	 * @throws IOException 
	 */
	public ArrayList<Libro> deserializza() throws IOException 
	{
		ArrayList<Libro> libri = new ArrayList<Libro>();
		
		File file = new File(FILE_NAME);
		try {		
			FileInputStream fileInput = new FileInputStream(file);
			try {
				inputStream = new ObjectInputStream(fileInput);
				
				Object object = inputStream.readObject();
				
				if(object instanceof ArrayList<?>) {
					ArrayList<?> arrayList = (ArrayList<?>) object;
					
					if(arrayList.size() >= 1) {
						for(Object aList : arrayList) {
							Object objectOfList = aList;
							
							if(objectOfList instanceof Libro) {
								Libro libro = (Libro) objectOfList;
								libri.add(libro);
							}
						}
					}
				}
				inputStream.close();
				
				System.out.println("Caricamento libri effettuato con successo!\n");
				
				return libri;
			} catch (ClassNotFoundException e) {
				System.err.println("Errore nella ricerca del libro nella classe\n");
			}
		} catch (IOException e) {
			System.err.println("Errore caricamento libri...\nCreazione file ''libreria.bin''\n");
			
			file = new File("libreria.bin");
			
			if(file.createNewFile()) {
				System.out.println("File ''libreria.bin'' creato con successo!\n");
			} else {
				System.err.println("ERRORE creazione file ''libreria.bin''.");
				System.exit(0);
			}
		}
		
		return new ArrayList<Libro>();
	}
}
