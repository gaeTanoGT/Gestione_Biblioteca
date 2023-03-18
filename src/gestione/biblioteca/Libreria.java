package gestione.biblioteca;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import gestione.salvataggio.Deserializzazione;
import gestione.salvataggio.Serializzazione;

/**
 * Classe contenente la lista dei libri presenti in libreria ed il numero
 * di libri
 * @author masini libero
 * @version 2.0
 */

public class Libreria
{	
	private ArrayList<Libro> libri = new ArrayList<Libro>();
	
	Serializzazione serializzazione;
	Deserializzazione deserializzazione;

	/**
	 * Costruttore vuoto
	 */
	public Libreria() 
	{
		 
	}
	
	/**
	 * Costruttore con parametri libri
	 * 
	 * @param libri, lista di oggetti Libro presenti in libreria
	 */
	public Libreria(ArrayList<Libro> libri) 
	{
		for(Libro libro : libri) {
			this.libri.add(new Libro(libro));
		}

	}
	
	/**
	 * Costruttore di copia
	 * 
	 * @param l, Libreria da copiare
	 */
	public Libreria(Libreria l) 
	{
		for(Libro libro : l.getLibri()) {
			libri.add(new Libro(libro));
		}
	}
	
	/**
	 * Restituisce il puntatore all'arrayList di oggetti Libro
	 * 
	 * @return libri
	 */
	public ArrayList<Libro> getLibri() 
	{
		return libri;
	}

	/**
	 * imposta l'attributo libri della classe con quello passato come parametro
	 * istanziandolo
	 * 
	 * @param libri, lista di libri
	 */
	public void setLibri(ArrayList<Libro> libri) 
	{
		for(Libro libro : libri) {
			this.libri.add(new Libro(libro));
		}
	}

	/**
	 * restituisce il numero di libri
	 * 
	 * @return libri.size(), numero di libri della lista
	 */
	public int getNumeroLibri() 
	{
		return libri.size();
	}

	/**
	 * Aggiunge un Libro alla lista di libri 
	 * 
	 * @param l, libro da aggiungere
	 * @return stato dell'operazione effettuata
	 */
	public boolean aggiungiLibro(Libro l) 
	{
		return libri.add(new Libro(l));
	}
	
	/**
	 * Rimuove un libro dalla lista di libri
	 * 
	 * @param l, libro da rimuovere
	 * @return stato dell'operazione effettuata
	 */
	public boolean rimuoviLibro(Libro l) 
	{
		if(libri.remove(l)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Modifica gli attributi di un libro 
	 * 
	 * @param l, libro da modificare
	 * @param lNew, libro modificato
	 * @return stato dell'operazione effettuata
	 */
	public boolean modificaLibro(Libro l, Libro lNew) 
	{
		try {
			l.setTitolo(lNew.getTitolo());
			l.setAutore(lNew.getAutore());
			l.setEditore(lNew.getEditore());
			l.setNumeroPagine(lNew.getNumeroPagine());
			l.setCodiceIsbn(lNew.getCodiceIsbn());
			l.setPrezzo(lNew.getPrezzo());
			l.setDataRilascio(lNew.getDataRilascio());
			l.setCollana(lNew.getCollana());
			l.setEbook(lNew.isEbook());
			l.setRettifica(lNew.getRettifica());
		} catch (NullPointerException e) {

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Stampa la lista di oggetti Libro attributo della classe
	 */
	public void stampaLibri() 
	{
		boolean b = false;
		try{
			for(Libro libro : libri) {
				b = true;
				libro.stampaLibro();
			}
			
			System.out.println("In libreria sono presenti " + libri.size() + " libri.\n");
		}catch(NullPointerException e) {
		
		}
		
		if(!b) {
			System.out.print("La libreria e' vuota!\n\n");
		}
	}
	
	/**
	 * Stampa la lista di libri passata come parametro, 
	 * inserendo un indice a ciascun Libro
	 * 
	 * @param libriList, lista di libri da stampare
	 */
	public void stampaLibri(ArrayList<Libro> libriList) 
	{		
		if(libriList.isEmpty()) {
			System.out.println("\nNessun libro trovato.\n");
		} else {
			System.out.println("\nRisultati ricerca:\n");
			int i = 1;
			for(Libro libro : libriList) {
				System.out.println(i);
				System.out.println(libro);
				i++;
			}
		}
	}
	
	/**
	 * Istanzia un oggetto di tipo Serializzazione ed avvia la serializzazione 
	 * degli oggetti
	 */
	public void serializza() 
	{
		try {
			serializzazione = new Serializzazione();
			serializzazione.serializza(libri);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Istanzia un oggetto di tipo Deserializzazione ed avvia la deserializzazione 
	 * degli oggetti
	 */
	public void deserializza() 
	{
		try {
			deserializzazione = new Deserializzazione();
			libri = new ArrayList<Libro>(deserializzazione.deserializza());
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("Errore deserializzazione");
		}	
	}
	
	/**
	 * Restituisce la lista di libri corrispondenti al titolo 
	 * passato come parametro
	 * 
	 * @param titolo da cercare tra i libri
	 * @return lista di libri corrispondenti alla ricerca
	 */
	public ArrayList<Libro> cercaByTitolo(String titolo) 
	{
		ArrayList<Libro> libriList = new ArrayList<Libro>();
		
		for(Libro libro : libri) {
			try {
				String curTitolo = (String)libro.getTitolo().subSequence(0, titolo.length());
				if(curTitolo.equalsIgnoreCase(titolo)) {
					libriList.add(libro);
				}
			} catch (StringIndexOutOfBoundsException e) {

			}
		}
		
		return libriList;
	}
	
	/**
	 * Restituisce la lista di libri corrispondenti all'autore 
	 * passato come parametro
	 * 
	 * @param autore da cercare tra i libri
	 * @return lista di libri corrispondenti alla ricerca
	 */
	public ArrayList<Libro> cercaByAutore(String autore) 
	{
		ArrayList<Libro> libriList = new ArrayList<Libro>();
		
		for(Libro libro : libri) {
			try {
				String curAutore = (String)libro.getAutore().subSequence(0, autore.length());
				if(curAutore.equalsIgnoreCase(autore)) {
					libriList.add(libro);
				}
			} catch (StringIndexOutOfBoundsException e) {

			}
		}
		
		return libriList;
	}
	
	/**
	 * Restituisce la lista di libri corrispondenti all'editore 
	 * passato come parametro
	 * 
	 * @param editore da cercare tra i libri
	 * @return lista di libri corrispondenti alla ricerca
	 */
	public ArrayList<Libro> cercaByEditore(String editore) 
	{
		ArrayList<Libro> libriList = new ArrayList<Libro>();
		
		for(Libro libro : libri) {
			try {
				String curEditore = (String)libro.getEditore().subSequence(0, editore.length());
				if(curEditore.equalsIgnoreCase(editore)) {
					libriList.add(libro);
				}
			} catch (StringIndexOutOfBoundsException e) {

			}
		}
		
		return libriList;
	}
	
	/**
	 * Restituisce la lista di libri corrispondenti al codice ISBN 
	 * passato come parametro
	 * 
	 * @param codiceIsbn da cercare tra i libri
	 * @return lista di libri corrispondenti alla ricerca
	 */
	public ArrayList<Libro> cercaByCodiceIsbn(String codiceIsbn) 
	{
		ArrayList<Libro> libriList = new ArrayList<Libro>();
		
		for(Libro libro : libri) {
			try {
				String curCodiceIsbn = (String)libro.getCodiceIsbn().subSequence(0, codiceIsbn.length());
				if(curCodiceIsbn.equalsIgnoreCase(codiceIsbn)) {
					libriList.add(libro);
				}
			} catch (StringIndexOutOfBoundsException e) {

			}
		}
		
		return libriList;
	}
	
	/**
	 * Restituisce la lista di libri ordinata in ordine crescente o decrescente
	 * rispetto al prezzo
	 * 
	 * @param opzione 0: cordine crescente, 1: ordine decrescente
	 * @return lista di libri ordinati secondo il criterio indicato
	 */
	public ArrayList<Libro> cercaByPrezzo(boolean opzione) 
	{
		ArrayList<Libro> libriList = new ArrayList<Libro>();
		
		for(Libro libro : libri) {
			libriList.add(libro);
		}
		
		Collections.sort(libriList);		//ordina i libri per prezzo in ordine crescente
		
		if(!opzione) {
			Collections.reverse(libriList);
		}
		
		return libriList;
	}
	
	/**
	 * Restituisce la lista di libri corrispondenti alla collana
	 * passata come parametro
	 * 
	 * @param collana da cercare tra i libri
	 * @return lista di libri corrispondenti alla ricerca
	 */
	public ArrayList<Libro> cercaByCollana(String collana) 
	{
		ArrayList<Libro> libriList = new ArrayList<Libro>();
		
		for(Libro libro : libri) {
			try {
				String curCollana = (String)libro.getCollana().subSequence(0, collana.length());
				if(curCollana.equalsIgnoreCase(collana)) {
					libriList.add(libro);
				}
			} catch (NullPointerException | StringIndexOutOfBoundsException e) {

			}			
		}
		
		return libriList;
	}
}
