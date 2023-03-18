package gestione.biblioteca;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Classe contenente le specifiche del Libro
 * @author Torrisi Gaetano
 * @version 2.0
 */

public class Libro implements Serializable, Comparable<Libro>
{
	private static final long serialVersionUID = 1L;
	
	private String titolo;
	private String autore;
	private String editore;
	private int numeroPagine;
	private String codiceIsbn;
	private float prezzo;
	private LocalDate dataRilascio;
	
	private String collana;
	private boolean ebook;
	private String rettifica;

	/**
	 * Costruttore vuoto
	 */
	public Libro() 
	{
		this.titolo = null;
		this.autore = null;
		this.editore = null;
		this.numeroPagine = -1;
		this.codiceIsbn = null;
		this.prezzo = -1;
		this.dataRilascio = null;
		this.collana = null;
		this.ebook = false;
		this.rettifica = null;
	}

	/**
	 * Costruttore senza collana, rettifica ed eBook passati come parametri
	 * 
	 * @param titolo
	 * @param autore
	 * @param editore
	 * @param numeroPagine
	 * @param codiceIsbn
	 * @param prezzo
	 * @param dataRilascio
	 */
	public Libro(String titolo, String autore, String editore, 
			int numeroPagine, String codiceIsbn, float prezzo,
			LocalDate dataRilascio) 
	{
		this.titolo = titolo;
		this.autore = autore;
		this.editore = editore;
		this.numeroPagine = numeroPagine;
		this.codiceIsbn = codiceIsbn;
		this.prezzo = prezzo;
		this.dataRilascio = dataRilascio;
		this.collana = null;
		this.ebook = false;
		this.rettifica = null;
	}
	
	/**
	 * Costruttore contenente come parametri tutti gli attributi della classe
	 * 
	 * @param titolo
	 * @param autore
	 * @param editore
	 * @param numeroPagine
	 * @param codiceIsbn
	 * @param prezzo
	 * @param dataRilascio
	 * @param collana
	 * @param ebook
	 * @param rettifica
	 */
	public Libro(String titolo, String autore, String editore, 
			int numeroPagine, String codiceIsbn, float prezzo,
			LocalDate dataRilascio, String collana, boolean ebook, 
			String rettifica) 
	{
		this.titolo = titolo;
		this.autore = autore;
		this.editore = editore;
		this.numeroPagine = numeroPagine;
		this.codiceIsbn = codiceIsbn;
		this.prezzo = prezzo;
		this.dataRilascio = dataRilascio;
		this.collana = collana;
		this.ebook = ebook;
		this.rettifica = rettifica;
	}

	/**
	 * Costruttore di copia
	 * 
	 * @param libro
	 */
	public Libro(Libro libro)
	{
		this.titolo = libro.getTitolo();
		this.autore = libro.getAutore();
		this.editore = libro.getEditore();
		this.numeroPagine = libro.getNumeroPagine();
		this.codiceIsbn = libro.getCodiceIsbn();
		this.prezzo = libro.getPrezzo();
		this.dataRilascio = libro.getDataRilascio();
		this.collana = libro.getCollana();
		this.ebook = libro.isEbook();
		this.rettifica = libro.getRettifica();
	}
	
	/**
	 * Metodo che stampa le caratteristiche del libro
	 */
	public void stampaLibro() 
	{
		System.out.println(
				"Titolo: " + titolo + "\n" +
				"Autore: " + autore + "\n" +
				"Editore: " + editore + "\n" + 
				"Numero Pagine: " + numeroPagine + "\n" + 
				"Codice ISBN: " + codiceIsbn + "\n" +
				"Prezzo: " + prezzo + "\n" + 
				"Data Rilascio: " + dataRilascio + "\n" +
				"Collana: " + getStringaCollana() + "\n" +
				"Ebook: " + getStringaEbook() + "\n" +
				"Rettifica: " + getStringaRettifica() + "\n\n"
				);
	}
	
	/**
	 * restituisce la collana del libro
	 * 
	 * @return collana
	 */
	public String getCollana() 
	{
		return collana;
	}
	
	/**
	 * imposta la collana del libro in base al parametro passato
	 * 
	 * @param collana
	 */
	public void setCollana(String collana) 
	{
		this.collana = collana;
	}

	/**
	 * restituisce se è presente un ebook
	 * 
	 * @return ebook
	 */
	public boolean isEbook() 
	{
		return ebook;
	}

	/**
	 * imposta l'esistenza di un ebook in base al parametro passato
	 * 
	 * @param ebook
	 */
	public void setEbook(boolean ebook) 
	{
		this.ebook = ebook;
	}

	/**
	 * restituisce la rettifica del libro
	 * 
	 * @return rettifica
	 */
	public String getRettifica() 
	{
		return rettifica;
	}

	/**
	 * imposta la rettifica del libro in base al parametro passato
	 * 
	 * @param rettifica
	 */
	public void setRettifica(String rettifica) 
	{
		this.rettifica = rettifica;
	}

	/**
	 * restituisce il titolo del libro
	 * 
	 * @return titolo
	 */
	public String getTitolo() 
	{
		return titolo;
	}

	/**
	 * imposta il titolo del libro in base al parametro passato
	 * 
	 * @param titolo
	 */
	public void setTitolo(String titolo)
	{
		this.titolo = titolo;
	}

	/**
	 * restituisce l'autore del libro
	 * 
	 * @return autore
	 */
	public String getAutore() 
	{
		return autore;
	}

	/**
	 * imposta l'autere del libro in base al parametro passato
	 * 
	 * @param autore
	 */
	public void setAutore(String autore) 
	{
		this.autore = autore;
	}

	/**
	 * restituisce l'editore  del libro
	 * 
	 * @return editore
	 */
	public String getEditore() 
	{
		return editore;
	}

	/**
	 * imposta l'editore del libro in base al parametro passato
	 * 
	 * @param editore
	 */
	public void setEditore(String editore) 
	{
		this.editore = editore;
	}

	/**
	 * restituisce il numero di pagine del libro
	 * 
	 * @return numeroPagine
	 */
	public int getNumeroPagine() 
	{
		return numeroPagine;
	}

	/**
	 * imposta il numero di pagine del libro in base al parametro passato
	 * 
	 * @param numeroPagine
	 */
	public void setNumeroPagine(int numeroPagine) 
	{
		this.numeroPagine = numeroPagine;
	}

	/**
	 * restituisce il codice ISBN del libro
	 * 
	 * @return codiceIsbn
	 */
	public String getCodiceIsbn() 
	{
		return codiceIsbn;
	}

	/**
	 * imposta il codice ISBN del libro in base al parametro passato
	 * 
	 * @param codiceIsbn
	 */
	public void setCodiceIsbn(String codiceIsbn) 
	{
		this.codiceIsbn = codiceIsbn;
	}

	/**
	 * restituisce il prezzo del libro
	 * 
	 * @return prezzo
	 */
	public float getPrezzo() 
	{
		return prezzo;
	}

	/**
	 * imposta il prezzo del libro in base al parametro passato
	 * 
	 * @param prezzo
	 */
	public void setPrezzo(float prezzo) 
	{
		this.prezzo = prezzo;
	}

	/**
	 * restituisce la data di rilascio del libro
	 * 
	 * @return dataRialscio
	 */
	public LocalDate getDataRilascio() 
	{
		return dataRilascio;
	}

	/**
	 * imposta la data di rilascio del libro in base al parametro passato
	 * 
	 * @param dataRilascio
	 */
	public void setDataRilascio(LocalDate dataRilascio) 
	{
		this.dataRilascio = dataRilascio;
	}
	
	/**
	 * Restituisce una stringa che indica la presenza dell'ebook nel libro
	 * 
	 * @return stringa presenza ebook
	 */
	private String getStringaEbook() 
	{
		if(ebook) {
			return "Presente";
		} else {
			return "Assente";
		}
	}
	
	/**
	 * Restituisce una stringa che indica la presenza della collana nel libro
	 * 
	 * @return stringa presenza collana
	 */
	private String getStringaCollana()
	{
		if(collana != null) {
			return collana;
		} else {
			return "Assente";
		}
	}
	
	/**
	 * Restituisce una stringa che indica la presenza della rettifica nel libro
	 * 
	 * @return stringa presenza rettifica
	 */
	private String getStringaRettifica() 
	{
		if(rettifica != null) {
			return rettifica;
		} else {
			return "Assente";
		}
	}
	
	@Override
	public String toString() 
	{
		return  "Titolo: " + titolo + "\n" +
				"Autore: " + autore + "\n" +
				"Editore: " + editore + "\n" + 
				"Numero Pagine: " + numeroPagine + "\n" + 
				"Codice ISBN: " + codiceIsbn + "\n" +
				"Prezzo: " + prezzo + "\n" + 
				"Data Rilascio: " + dataRilascio + "\n" +
				"Collana: " + getStringaCollana() + "\n" +
				"Ebook: " + getStringaEbook() + "\n" +
				"Rettifica: " + getStringaRettifica() + "\n";
	}
	
	@Override
	public int compareTo(Libro comparaLibro) 
	{
        float prezzoLibroComparato = comparaLibro.getPrezzo();
        
        return (int)(this.getPrezzo() - prezzoLibroComparato);
    }
}
