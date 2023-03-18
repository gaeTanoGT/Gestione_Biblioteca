package gestione.biblioteca;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe dedicata alla comunicazione via terminale con l'utente
 * 
 * @author Torrisi Gaetano
 * @version 2.0
 */
public class Menu 
{
	Libreria libreria;
	Scanner scanner;

	/**
	 * Costruttore di menu' in cui viene pulito il terminale, 
	 * istanziato un oggetto di tipo Libreria e Scanner, 
	 * e deserializzati gli oggetti di libreria
	 */
	public Menu()
	{
		pulisciTerminal();
		
		libreria = new Libreria();
		scanner = new Scanner(System.in);
		
		libreria.deserializza();
	}
	
	/**
	 * Stampa sul terminale le opzioni del menu ed in base alla scelta fatta
	 * esegue gli opportuni metodi
	 * 
	 * @throws IOException
	 */
	public void mostraMenu() throws IOException 
	{
		System.out.print("Gestione Biblioteca\n"
				+ "\t1: Cerca Libro\n"
				+ "\t2: Aggiungi Libro\n"
				+ "\t3: Rimuovi Libro\n"
				+ "\t4: Modifica Libro\n"
				+ "\t5: Visualizza Libreria\n"
				+ "\t6: Esci\n\n"
				+ "Selezionare un'opzione: ");
		
		boolean b = true;
		
		int ch = getInteroInput();
		while(b) {
			b = false;
			switch (ch) {
			case 1: {
				pulisciTerminal();
				ArrayList<Libro> libriRicerca = new ArrayList<Libro>();
				System.out.println("Cerca Libro");
				libriRicerca = cercaLibri();
				libreria.stampaLibri(libriRicerca);
				systemPause();
				pulisciTerminal();
				mostraMenu();
				break;
			}
			case 2: {
				Libro l = getLibro();
				statoOperazione(libreria.aggiungiLibro(l), 0);
				systemPause();
				pulisciTerminal();
				mostraMenu();
				break;
			}
			case 3: {
				pulisciTerminal();
				ArrayList<Libro> libriRicerca = new ArrayList<Libro>();
				System.out.println("Rimuovi Libro");
				libriRicerca = cercaLibri();
				Libro libroDaEliminare = scegliLibro(libriRicerca, 0);
				statoOperazione(libreria.rimuoviLibro(libroDaEliminare), 1);
				systemPause();
				pulisciTerminal();
				mostraMenu();
				break;
			}
			case 4: {
				pulisciTerminal();
				ArrayList<Libro> libriRicerca = new ArrayList<Libro>();
				System.out.println("Modifica Libro.\n Cerca il libro da modificare...");
				libriRicerca = cercaLibri();
				Libro libroDaModificare = scegliLibro(libriRicerca, 1);
				Libro libroModificato = null;
				if(libroDaModificare != null) {
					System.out.print("Desidera modificare tutto il libro?: ");
					boolean risposta = getYNInput();
					if(risposta) {		//modifica tutti gli attributi del libro
						libroModificato = getLibro();
						statoOperazione(libreria.modificaLibro(libroDaModificare, libroModificato), 2);
					} else {
						int opzione = sceltaAttributo(1);
						statoOperazione(modificaAttributo(libroDaModificare, opzione), 2);
					}
				} else {
					statoOperazione(false, 2);
				}
				systemPause();
				pulisciTerminal();
				mostraMenu();
				break;
			}
			case 5: {
				pulisciTerminal();
				libreria.stampaLibri();
				systemPause();
				pulisciTerminal();
				mostraMenu();
				break;
			}
			case 6: {
				libreria.serializza();
				break;
			}
			default: {
				System.err.println("Input errato... "
						+ "Riprovare scegliendo un'opzione del menu");
				ch = getInteroInput();
				b = true;
				break;
			}
			}
		}
	}
	
	/**
	 * Simulazione di un system("pause") con lettura attraverso Scanner
	 */
	private void systemPause() 
	{
		System.out.println("Digitare un carattere qualsiasi per tornare al menu...");
		scanner.next();
	}
	
	/**
	 * Stampa di 50 righe vuote
	 */
	private void pulisciTerminal() 
	{
		for(int i = 0; i < 50; i++)
		    System.out.print("\n");
	}
	
	/**
	 * Legge una stringa attraverso l'use di Scanner
	 * 
	 * @return stringa letta
	 */
	private String getStringaInput() 
	{
		String str = scanner.nextLine();

		if(str == "") {
			str = getStringaInput();
		}
		
		return str;
	}
	
	/**
	 * Legge un numero intero attraverso l'uso di Scanner
	 * 
	 * @return intero letto
	 */
	private int getInteroInput() 
	{
		int n;
		
		try {
			n = scanner.nextInt();
		} catch (InputMismatchException e) {
			System.err.print("Input errato...  "
					+ "Riprovare inserendo un intero: ");
			scanner.nextLine();
			n = getInteroInput();
		}
		
		return n;
	}
	
	/**
	 * Legge un numero decimale attraverso l'uso di Scanner
	 * 
	 * @return numero decimale letto
	 */
	private float getFloatInput() 
	{
		float n; 
		
		try {
			n = scanner.nextFloat();
		} catch (InputMismatchException e) {
			System.err.print("Input errato...  "
					+ "Riprovare inserendo un numero: ");
			scanner.nextLine();
			n = getFloatInput();
		}
		
		return n;
	}
	
	/**
	 * Legge attraverso l'uso di Scanner il giorno, il mese e l'anno,
	 * verificandone la correttezza dei valori
	 * ed inserendoli in un oggetto di tipo LocalDate
	 * 
	 * @return data letta
	 */
	private LocalDate getLocalDateInput() 
	{
		System.out.print("Inserire il giorno: ");
		int dd = getInteroInput();
		while(dd < 1 || dd > 31) {
			System.err.print("Input errato... "
					+ "Riprovare inserendo un giorno adeguato: ");
			dd = getInteroInput();
		}
		
		System.out.print("Inserire il mese: ");
		int mm = getInteroInput();
		while(mm < 1 || mm > 12) {
			System.err.print("Input errato... "
					+ "Riprovare inserendo un mese adeguato: ");
			mm = getInteroInput();
		}
		
		System.out.print("Inserire l'anno: ");
		int yyyy = getInteroInput();
		while(yyyy < -999999999 || yyyy > 999999999) {
			System.err.print("Input errato... "
					+ "Riprovare inserendo un anno adeguato: ");
			yyyy = getInteroInput();
		}
		
		LocalDate data;
		try {
			data = LocalDate.of(yyyy, mm, dd);
		} catch (DateTimeException e) {
			System.err.println("Errore input data. Riprovare...");
			data = getLocalDateInput();
		}
		
		return data;
	}
	
	/**
	 * Legge attraverso l'uso di scanner una stringa 
	 * restituendone il suo valore booleano
	 * 
	 * @return booleano convertito
	 */
	private boolean getYNInput() 
	{
		String str = getStringaInput();
		
		if(str.equalsIgnoreCase("s") || str.equalsIgnoreCase("si") 
				|| str.equalsIgnoreCase("y") || str.equalsIgnoreCase("yes")
				|| str.equals("1")) {
			return true;
		} else if(str.equalsIgnoreCase("n") || str.equalsIgnoreCase("no")
				|| str.equals("0")) {
			return false;
		} else {
			System.err.print("Mmm non ho capito bene. "
					+ "Rispondere con Si/No: ");
			
			return getYNInput();
		}
	}
	
	/**
	 * Crea e restituisce un oggetto di tipo Libro assegnandone 
	 * i suoi parametri
	 * 
	 * @return oggetto Libro con parametri letti da tastiera
	 */
	private Libro getLibro() 
	{
		pulisciTerminal();
		
		Libro libro = new Libro();
		
		System.out.print("Inserire il titolo: ");
		String titolo = getStringaInput();
		libro.setTitolo(titolo);
		
		System.out.print("Inserire l'autore: ");
		String autore = getStringaInput();
		libro.setAutore(autore);
		
		System.out.print("Inserire l'editore: ");
		String editore = getStringaInput();
		libro.setEditore(editore);
		
		System.out.print("Inserire il numero di pagine: ");
		int n = getInteroInput();
		libro.setNumeroPagine(n);
		
		System.out.print("Inserire Codice ISBN: ");
		String codiceIsbn = getStringaInput();
		libro.setCodiceIsbn(codiceIsbn);
		
		System.out.print("Inserire il prezzo: ");
		float prezzo = getFloatInput();
		libro.setPrezzo(prezzo);
		
		System.out.println("Inserire la data di rilascio...");
		LocalDate data = getLocalDateInput();
		libro.setDataRilascio(data);
		
		System.out.print("Desidera inserire anche l'ebook, "
				+ "collana e rettifica? :");
		boolean risposta = getYNInput();
		
		if(risposta) {
			System.out.print("Inserire collana: ");
			String collana = getStringaInput();
			libro.setCollana(collana);
			
			System.out.print("E' incluso l'ebook? : ");
			boolean ebook = getYNInput();
			libro.setEbook(ebook);
			
			System.out.print("Inserire rettifica: ");
			String rettifica = getStringaInput();
			libro.setRettifica(rettifica);
		}
		
		return libro;
	}
	
	/**
	 * Apre un menu per la ricerca di libri con criterio di ricerca 
	 * a scelta dell'utente tra:
	 * Titolo, Autore, Editore, Codice ISBN, Collana.
	 * Verifica l'esistenza di libri corrispondenti al criterio di ricerca 
	 * selezionato, assegnandoli ad una lista di tipo Libro
	 * 
	 * @return la lista di libri inerenti alla ricerca effettuata
	 */
	private ArrayList<Libro> cercaLibri() 
	{
		ArrayList<Libro> libri = new ArrayList<Libro>();
		
		int opzione = sceltaAttributo(0);
		
		switch (opzione) {
		case 1: {
			System.out.print("Inserire il titolo da cercare: ");
			String titolo = getStringaInput();
			libri = libreria.cercaByTitolo(titolo);
			break;
		}
		case 2: {
			System.out.print("Inserire l'autore da cercare: ");
			String autore = getStringaInput();
			libri = libreria.cercaByAutore(autore);
			break;
		}
		case 3: {
			System.out.print("Inserire l'editore da cercare: ");
			String editore = getStringaInput();
			libri = libreria.cercaByEditore(editore);
			break;
		}
		case 4: {
			System.out.print("Inserire il codice ISNB da cercare: ");
			String codiceIsbn = getStringaInput();
			libri = libreria.cercaByCodiceIsbn(codiceIsbn);
			break;
		}
		case 5: {
			System.out.print("Desidera ricercare i libri per crescenza di prezzo? : ");
			boolean opzioneRicerca = getYNInput();
			libri = libreria.cercaByPrezzo(opzioneRicerca);
			break;
		}
		case 6: {
			System.out.print("Inserire la collana da cercare: ");
			String collana = getStringaInput();
			libri = libreria.cercaByCollana(collana);
			break;
		}
		}
		
		return libri;
	}
	
	/**
	 * Modifica un attributo dell'oggetto libro 
	 * leggendone il nuovo valore dallo Scanner in input
	 * 
	 * @param libro, oggetto Libro da modificare
	 * @param opzione, indice dell'attributo da modificare: 
	 * 		0: Titolo; 1: Autore; 2: Editore; 3: Numero Pagine; 4: codice ISBN;
	 * 		5: prezzo; 6: Data Rilascio; 7: Collana; 8: Ebook; 9: Rettifica;
	 * @return stato dell'operazione effettuata
	 */
	private boolean modificaAttributo(Libro libro, int opzione) 
	{
		switch (opzione) {
		case 0: {
			System.out.print("Inserire il nuovo titolo: ");
			String titolo = getStringaInput();
			libro.setTitolo(titolo);
			break;
		}
		case 1: {
			System.out.print("Inserire il nuovo autore: ");
			String autore = getStringaInput();
			libro.setAutore(autore);
			break;
		}
		case 2: {
			System.out.print("Inserire il nuovo editore: ");
			String editore = getStringaInput();
			libro.setEditore(editore);
			break;
		}
		case 3: {
			System.out.print("Inserire il nuovo numero di pagine: ");
			int numeroPagine = getInteroInput();
			libro.setNumeroPagine(numeroPagine);
			break;
		}
		case 4: {
			System.out.print("Inserire il nuovo codice ISBN: ");
			String codiceIsbn = getStringaInput();
			libro.setCodiceIsbn(codiceIsbn);
			break;
		}
		case 5: {
			System.out.print("Inserire il nuovo prezzo: ");
			float prezzo = getFloatInput();
			libro.setPrezzo(prezzo);
			break;
		}
		case 6: {
			System.out.print("Inserire la nuova data di rilascio: ");
			LocalDate data = getLocalDateInput();
			libro.setDataRilascio(data);
			break;
		}
		case 7: {
			System.out.print("Inserire la nuova collana: ");
			String collana = getStringaInput();
			libro.setCollana(collana);
			break;
		}
		case 8: {
			System.out.print("E' presente l'ebook?: ");
			boolean ebook = getYNInput();
			libro.setEbook(ebook);
			break;
		}
		case 9: {
			System.out.print("Inserire la nuova rettifica: ");
			String rettifica = getStringaInput();
			libro.setRettifica(rettifica);
			break;
		}
		default: {
			return false;
		}
		}
		
		return true;
	}
	
	/**
	 * Stampa a video l'elenco degli attributi opzioni del menu'
	 * e ne legge l'input verificandone la correttezza
	 * 
	 * @param tipo, tipo di operazione da eseguire:
	 * 		0: Crecare un libro
	 * 		1: Modificare un attributo di un libro
	 * @return opzione del menu' letta in input
	 */
	private int sceltaAttributo(int tipo) 
	{
		if (tipo == 0) {
			System.out.print(
					  "\t1: Titolo\n"
					+ "\t2: Autore\n"
					+ "\t3: Editore\n"
					+ "\t4: Codice ISBN\n"
					+ "\t5: Prezzo\n"
					+ "\t6: Collana\n"
					+ "Indicare la preferenza di ricerca: ");
		
		} else if (tipo == 1) {
			System.out.print(
					  "\t0: Titolo\n"
					+ "\t1: Autore\n"
					+ "\t2: Editore\n"
					+ "\t3: Numero Pagine\n"
					+ "\t4: Codice ISBN\n"
					+ "\t5: Prezzo\n"
					+ "\t6: Data Rilascio\n"
					+ "\t7: Collana\n"
					+ "\t8: Ebook\n"
					+ "\t9: Rettifica\n"
					+ "Indicare l'attributo da modificare: ");
		}				
		
		boolean b = true;
		
		int opzione = getInteroInput();
		while(b) {
			b = false;
			
			if((opzione > 0 && opzione < 7 && tipo == 0) || (opzione >= 0 && opzione <= 9 && tipo == 1)) {
				return opzione;
			} else {
				System.err.println("Input errato... "
						+ "Riprovare scegliendo un'opzione del menu");
				opzione = getInteroInput();
				b = true;
			}
		}
		
		return -1;
	}
	
	/**
	 * Stampa la lista di libri passata come parametro 
	 * leggendo l'indice del libro associato
	 * 
	 * @param libri, lista di libri
	 * @param tipo, tipologia di operazione da fare:
	 * 		0: rimozione libro; 1: modifica libro
	 * @return Libro selezionato da input
	 */
	private Libro scegliLibro(ArrayList<Libro> libri, int tipo) 
	{
		libreria.stampaLibri(libri);
		
		if(!libri.isEmpty()) {
			if (tipo == 0) {
				System.out.print("Indicare il libro da rimuovere: ");
			} else {
				System.out.print("Indicare il libro da modificare: ");
			}
			
			int n = getInteroInput();
			
			try {
				return libri.get(n - 1);
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Errore selezione libro. Riprovare...");
			}
		}
		
		return null;
	}
	
	/**
	 * Stampa lo stato dell'operazione effettuata
	 * 
	 * @param b, booleano che indica lo stato dell'operazione
	 * @param n, tipologia di operazione fatta: 
	 * 		0: aggiunta Libro; 1: rimozione Libro; 2: modifica Libro
	 */
	private void statoOperazione(boolean b, int n) 
	{
		switch (n) {
		case 0: {
			if(b) {
				System.out.println("\nIl libro e' stato aggiunto con successo alla libreria!");
			} else {
				System.out.println("Errore. Riprovare...\n");
			}
			break;
		}
		case 1: {
			if(b) {
				System.out.println("\nIl libro e' stato rimosso con successo dalla libreria!");
			} else {
				System.out.println("Errore. Riprovare...\n");
			}
			break;
		}
		case 2: {
			if(b) {
				System.out.println("\nIl libro e' stato modificato con successo!");
			} else {
				System.out.println("Errore. Riprovare...\n");
			}
			break;
		}
		default:
			break;
		}
	}
}