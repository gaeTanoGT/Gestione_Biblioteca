package gestione.biblioteca;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Classe di interfacciamento grafico
 * @author Torrisi Gaetano
 * @version 2.0
 */
public class Interfaccia 
{
    Libreria libreria;
	
    JFrame frame = new JFrame();
	
    JPanel pan1 = new JPanel();
    JLabel lab1 = new JLabel();
    JPanel pan2 = new JPanel();
    JLabel lab2 = new JLabel();
    JPanel pan3 = new JPanel();

    JButton butt = new JButton();
    JButton butt2 = new JButton();
    JButton back = new JButton();
    
    JTextField titoloField;
    JTextField autoreField;
    JTextField editoreField;
    JTextField pagineField;
    JTextField isbnField;
    JTextField prezzoField;
    JTextField giornoField;
    JTextField meseField;
    JTextField annoField;
    JTextField collanaField;
    JTextField rettificaField;

    JLabel labelPagine;
    JLabel labelPrezzo;
    JLabel labelDateGiorno;
    JLabel labelDateMese;
    JLabel labelDateAnno;
    
    JPanel westJPanel;
    JPanel radJPanel;
    JPanel panelInput;  
    
    JTextArea textArea[];
    
    JTextField textField;
    private String[] optionsToChoose = {"No", "Si"};
    private JComboBox<String> jComboBox = new JComboBox<>(optionsToChoose);
    
    JComboBox<String> jComboBoxScelta;

    JRadioButton[] rad = new JRadioButton[10];
    ButtonGroup gr = new ButtonGroup();		
		//necessario per far selezionare una sola tipologia

    protected boolean mostraErrore = true;
    boolean selezionaLibro = false;
    boolean aggiungiLibro = false;
    boolean rimuoviLibro = false;
    boolean modificaLibro = false;
    
    private int posizione = 0;		
		//0: menu; 1: cercaLibro; 2: stampaLibri di libriRicerca; 
    	//3: aggiungiLibro e conferma aggiunta; 4: rimuoviLibro; 5: modificaLibro
    
    private ArrayList<Libro> libriRicerca;
    
    /**
     * Costruttore di interfaccia in cui vengono settati
     * gli attributi
     */
	public Interfaccia() 
	{
		libreria = new Libreria();
		libreria.deserializza();
		
		frame.setLayout(new BorderLayout(100, 50));
		
		//pannello iniziale
		pan1.setVisible(true);
		pan1.setLayout(new BorderLayout(10, 10));
		
		pan2.setVisible(true);
		pan2.setLayout(new BorderLayout(10, 10));
		
		pan3.setVisible(true);
		
		try {
			panelInput.removeAll();
		} catch (NullPointerException e) {

		}
		
		lab1.setVisible(true);
		lab1.setFont(new Font("Dialog", Font.PLAIN, 30));
		
		lab1.setHorizontalAlignment(JLabel.CENTER);
				
		pan1.add(lab1, BorderLayout.CENTER);
		
		//radioButton
		int widthPanel = 75;
        radJPanel = new JPanel();
        radJPanel.setLayout(new BoxLayout(radJPanel, BoxLayout.Y_AXIS));
        for(int i = 0; i < 6; i++) {
            rad[i] = new JRadioButton();
            rad[i].setFocusPainted(false);
            rad[i].setPreferredSize(new Dimension(100, 30));
            rad[i].setMaximumSize(new Dimension(1000, 300));
            rad[i].setFocusPainted(false);
            rad[i].setFont(new Font("Dialog", Font.PLAIN, 12));
            gr.add(rad[i]);
            radJPanel.add(rad[i]);
        }
        
        westJPanel = new JPanel();
        westJPanel.add(Box.createRigidArea(new Dimension(widthPanel, 0)));
        pan2.add(westJPanel, BorderLayout.WEST);
        pan2.add(radJPanel, BorderLayout.CENTER);
        
        butt.setFocusPainted(false);
        butt.addActionListener(e -> selezione());
        butt.setPreferredSize(new Dimension(100, 50));
        butt.setMaximumSize(new Dimension(100, 50));
        butt.setVisible(true);
        
        back.setFocusPainted(false);
        back.addActionListener(e -> back());
        back.setPreferredSize(new Dimension(100, 50));
        back.setMaximumSize(new Dimension(100, 50));
        back.setText("Indietro");
        
        frame.add(pan1, BorderLayout.NORTH);
        frame.add(pan2, BorderLayout.CENTER);
        frame.add(pan3, BorderLayout.SOUTH);
        
        frame.setTitle("Gestione Biblioteca");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(500, 500);
        frame.setVisible(true);
	}
	
	/**
	 * Metodo che mostra il menu dell'intefaccia
	 */
	public void mostraMenu()
	{
		gr.clearSelection();
		posizione = 0;
		
		frame.repaint();
		
		lab1.setText("Gestione Biblioteca");
		
		pan2.removeAll();
		pan2.add(westJPanel, BorderLayout.WEST);
        	pan2.add(radJPanel, BorderLayout.CENTER);
        
		rad[0].setText("Cerca Libro");
        	rad[1].setText("Aggiungi Libro");
		rad[2].setText("Rimuovi Libro");
		rad[3].setText("Modifica Libro");
		rad[4].setText("Visualizza Libreria");
		rad[5].setText("Salva ed Esci");

		try {
		    rad[6].setVisible(false);
		    rad[7].setVisible(false);
		    rad[8].setVisible(false);
		    rad[9].setVisible(false);
		} catch (NullPointerException e) {

		}

		try {
		    panelInput.setVisible(false);
		} catch (NullPointerException e) {

		}
		//bottone conferma
		butt.setText("Conferma");

		pan3.removeAll();
		pan3.add(butt);
	}
	
	/**
	 * Metodo richiamato alla pressione del pulsante Conferma / Menu
	 * esegue determinati metodi in base alla pagina in cui e' posizionato
	 */
	private void selezione() 
	{
		for(int i = 0; i < 10; i++) {
			try {
				if(rad[i].isSelected()) {
					mostraErrore = false;
					if(posizione == 0) {
						menu(i);		
					} else if(posizione == 1) {
						getInput(i);		
						posizione = 2;
					} else if (posizione == 2) {
						mostraMenu();
					} else if (posizione == 3) {
						verificaAttributi();
					} else if(posizione == 4) {
						if(libreria.rimuoviLibro(libriRicerca.get(
								jComboBoxScelta.getSelectedIndex()))) {
							JOptionPane.showMessageDialog(
								frame, "Rimozione avvenuta con sucesso!");
						} else {
							JOptionPane.showMessageDialog(
								frame, "Errore rimozione libro");
						}
						mostraMenu();
					} else if(posizione == 5) {
						System.out.println("modifica Libro");
						getLibro();
					} else if(posizione == 6) {		//conferma modifica libro
						verificaAttributi();
					}
	            		}
			} catch (NullPointerException e) {

			}
            
	    }
		 
		verificaSelezione();
	}
	
	/**
	 * Metodo richiamato alla pressione del pulsante indietro
	 */
	private void back() 
	{
		if(posizione == 1 || posizione == 3) {
			mostraMenu();
		} else if (posizione == 2) {
			cercaLibri();
		} else if(posizione == 6) {
			cercaLibri();
		}
		
	}

	/**
	 * metodo che cambia schermata in base alla selezione del radiobutton
	 * effettuata
	 * @param ch indice del radiobutton
	 */
	private void menu(int ch) 
	{
		switch (ch) {
		case 0: {
			selezionaLibro = false;
			aggiungiLibro = false;
			rimuoviLibro = false;
			modificaLibro = false;
			libriRicerca = new ArrayList<Libro>();
			System.out.println("Cerca Libro");
			cercaLibri();
			break;
		}
		case 1: {
			posizione = 3;
			selezionaLibro = false;
			aggiungiLibro = true;
			rimuoviLibro = false;
			modificaLibro = false;
			System.out.println("aggiungi libro");
			lab1.setText("Aggiungi Libro");
			getLibro();
			break;
		}
		case 2: {
			selezionaLibro = true;
			aggiungiLibro = false;
			rimuoviLibro = true;
			modificaLibro = false;
			System.out.println("Rimuovi Libro");
			lab1.setText("Rimuovi Libro");
			cercaLibri();
			break;
		}
		case 3: {
			selezionaLibro = true;
			aggiungiLibro = false;
			rimuoviLibro = false;
			modificaLibro = true;
			lab1.setText("Modifica Libro\n Cerca il libro da modificare...");
		System.out.println("Modifica Libro.\n "
				+ "Cerca il libro da modificare...");
			cercaLibri();
			break;
		}
		case 4: {
			posizione = 2;	
			selezionaLibro = false;
			aggiungiLibro = false;
			rimuoviLibro = false;
			modificaLibro = false;
			libriRicerca = libreria.getLibri();
			stampaLibri();
			break;
		}
		case 5: {
			libreria.serializza();
			System.exit(0);
			break;
		}
		default: 
			break;
		}
	}
	
	/**
	 * Metodo che dispone la pagina per la compilazione del form dell'oggetto
	 * libro
	 */
	private void getLibro() 
	{
		JPanel panelInput = new JPanel();
		panelInput.setLayout(new GridLayout(11, 1));

		JLabel labelTitolo = new JLabel("  Inserire il Titolo: ");
		titoloField = new JTextField();
		JPanel panelTitolo = new JPanel();
		panelTitolo.setLayout(new BorderLayout(10, 50));
		panelTitolo.add(labelTitolo, BorderLayout.WEST);
		panelTitolo.add(titoloField, BorderLayout.CENTER);
		panelInput.add(panelTitolo);

		JLabel labelAutore= new JLabel("  Inserire l'autore: ");
		autoreField = new JTextField();
		JPanel panelAutore = new JPanel();
		panelAutore.setLayout(new BorderLayout(10, 50));
		panelAutore.add(labelAutore, BorderLayout.WEST);
		panelAutore.add(autoreField, BorderLayout.CENTER);
		panelInput.add(panelAutore);

		JLabel labelEditore= new JLabel("  Inserire l'editore: ");
		editoreField = new JTextField();
		JPanel panelEditore = new JPanel();
		panelEditore.setLayout(new BorderLayout(10, 50));
		panelEditore.add(labelEditore, BorderLayout.WEST);
		panelEditore.add(editoreField, BorderLayout.CENTER);
		panelInput.add(panelEditore);

		labelPagine = new JLabel("  Inserire il numero di pagine: ");
		pagineField = new JTextField();
		JPanel panelPagine = new JPanel();
		panelPagine.setLayout(new BorderLayout(10, 50));
		panelPagine.add(labelPagine, BorderLayout.WEST);
		panelPagine.add(pagineField, BorderLayout.CENTER);
		panelInput.add(panelPagine);

		JLabel labelIsbn = new JLabel("  Inserire Codice ISBN: ");
		isbnField = new JTextField();
		JPanel panelIsbn = new JPanel();
		panelIsbn.setLayout(new BorderLayout(10, 50));
		panelIsbn.add(labelIsbn, BorderLayout.WEST);
		panelIsbn.add(isbnField, BorderLayout.CENTER);
		panelInput.add(panelIsbn);

		labelPrezzo = new JLabel("  Inserire il prezzo: ");
		prezzoField = new JTextField();
		JPanel panelPrezzo = new JPanel();
		panelPrezzo.setLayout(new BorderLayout(11, 50));
		panelPrezzo.add(labelPrezzo, BorderLayout.WEST);
		panelPrezzo.add(prezzoField, BorderLayout.CENTER);
		panelInput.add(panelPrezzo);
		
		JLabel labelDate = new JLabel("  Inserire la data di rilascio...");
		labelDateGiorno = new JLabel("  Giorno: ");
		labelDateMese = new JLabel("  Mese: ");
		labelDateAnno = new JLabel("  Anno: ");
		giornoField = new JTextField();
		giornoField.setPreferredSize(new Dimension(70, 25));
		meseField = new JTextField();
		meseField.setPreferredSize(new Dimension(70, 25));
		annoField = new JTextField();
		annoField.setPreferredSize(new Dimension(70, 25));
		JPanel panelDate = new JPanel();
		panelInput.add(labelDate, BorderLayout.WEST);
		panelDate.add(labelDateGiorno);
		panelDate.add(giornoField);
		panelDate.add(labelDateMese);
		panelDate.add(meseField);
		panelDate.add(labelDateAnno);
		panelDate.add(annoField);
		panelInput.add(panelDate, BorderLayout.CENTER);		
		
		JLabel labelCollana = new JLabel("  Inserire collana: ");
		collanaField = new JTextField();
		JPanel panelCollana = new JPanel();
		panelCollana.setLayout(new BorderLayout(10, 50));
		panelCollana.add(labelCollana, BorderLayout.WEST);
		panelCollana.add(collanaField, BorderLayout.CENTER);
		panelInput.add(panelCollana);
		
		JLabel labelEbook = new JLabel("  E' incluso l'ebook? : ");
		JPanel panelEbook = new JPanel();
		panelEbook.setLayout(new BorderLayout(10, 50));
		panelEbook.add(labelEbook, BorderLayout.WEST);
		panelEbook.add(jComboBox, BorderLayout.CENTER);
		panelInput.add(panelEbook);
		
		JLabel labelRettifica = new JLabel("  Inserire rettifica: ");
		rettificaField = new JTextField();
		JPanel panelRettifica = new JPanel();
		panelRettifica.setLayout(new BorderLayout(10, 50));
		panelRettifica.add(labelRettifica, BorderLayout.WEST);
		panelRettifica.add(rettificaField, BorderLayout.CENTER);
		panelInput.add(panelRettifica);
		
		if(modificaLibro) {
			pan1.removeAll();
			lab1.setText("Modifica Libro");
			pan1.add(lab1, BorderLayout.CENTER);
			Libro libro = libriRicerca.get(jComboBoxScelta.getSelectedIndex());
			titoloField.setText(libro.getTitolo());
			autoreField.setText(libro.getAutore());
			editoreField.setText(libro.getEditore());
			pagineField.setText(Integer.toString(libro.getNumeroPagine()));
			isbnField.setText(libro.getCodiceIsbn());
			prezzoField.setText(Float.toString(libro.getPrezzo()));
			giornoField.setText(Integer.toString(
					libro.getDataRilascio().getDayOfMonth()));
			meseField.setText(Integer.toString(
					libro.getDataRilascio().getMonthValue()));
			annoField.setText(Integer.toString(
					libro.getDataRilascio().getYear()));
			try {
				collanaField.setText(libro.getCollana());
				jComboBox.setSelectedIndex(libro.isEbook() ? 1 : 0);
				rettificaField.setText(libro.getRettifica());
			} catch (NullPointerException e) {

			}
			
			posizione = 6;
		}
		
		pan2.removeAll();
		pan2.revalidate();
		pan2.add(panelInput);
		
		pan3.removeAll();
		pan3.revalidate();
		pan3.repaint();
		pan3.add(back);
		pan3.add(butt);		
	}
	
	/**
	 * metodo che verifica l'idoneità degli attributi inseriti nel form
	 * in caso di correttezza nella compilazione aggiunge/modifica il libro
	 */
	private void verificaAttributi() 
	{
		labelPagine.setForeground(Color.black);
		labelPrezzo.setForeground(Color.black);
		labelDateGiorno.setForeground(Color.black);
		labelDateMese.setForeground(Color.black);
		labelDateAnno.setForeground(Color.black);
		pagineField.setForeground(Color.black);
		prezzoField.setForeground(Color.black);
		giornoField.setForeground(Color.black);
		meseField.setForeground(Color.black);
		annoField.setForeground(Color.black);
		
		Libro libro = new Libro();
		boolean ok = true;
		
		libro.setTitolo(titoloField.getText());
		libro.setAutore(autoreField.getText());
		libro.setEditore(editoreField.getText());
		try {
			libro.setNumeroPagine(Integer.parseInt(pagineField.getText()));
		} catch (NumberFormatException e) {
			labelPagine.setForeground(Color.red);
			pagineField.setForeground(Color.red);
			pan2.revalidate();
			ok = false;
		}
		libro.setCodiceIsbn(isbnField.getText());
		try {
			libro.setPrezzo(Float.parseFloat(prezzoField.getText()));
		} catch (NumberFormatException e) {
			labelPrezzo.setForeground(Color.red);
			prezzoField.setForeground(Color.red);
			pan2.revalidate();
			ok = false;
		}
		int dd = 0, mm = 0, yyyy = 0;
		try {
			dd = Integer.parseInt(giornoField.getText());
		} catch (NumberFormatException e) {
			labelDateGiorno.setForeground(Color.red);
			giornoField.setForeground(Color.red);
			pan2.revalidate();
			ok = false;
		}
		try {
			mm = Integer.parseInt(meseField.getText());
		} catch (NumberFormatException e) {
			labelDateMese.setForeground(Color.red);
			meseField.setForeground(Color.red);
			pan2.revalidate();
			ok = false;
		}
		try {
			yyyy = Integer.parseInt(annoField.getText());
		} catch (NumberFormatException e) {
			labelDateAnno.setForeground(Color.red);
			annoField.setForeground(Color.red);
			pan2.revalidate();
			ok = false;
		}
		try {
			libro.setDataRilascio(LocalDate.of(yyyy, mm, dd));
		} catch (NumberFormatException | DateTimeException e) {
			labelDateGiorno.setForeground(Color.red);
			giornoField.setForeground(Color.red);
			labelDateMese.setForeground(Color.red);
			meseField.setForeground(Color.red);
			labelDateAnno.setForeground(Color.red);
			annoField.setForeground(Color.red);
			pan2.revalidate();
			ok = false;
		}
		libro.setCollana(collanaField.getText());
		if(jComboBox.getSelectedIndex() == 0) {
			libro.setEbook(false);
		} else {
			libro.setEbook(true);
		}
		libro.setRettifica(rettificaField.getText());
		
		if(aggiungiLibro) {
			if(ok) {
				if(libreria.aggiungiLibro(libro)) {
					JOptionPane.showMessageDialog(
						frame, "Libro aggiunto con successo");
		        	try {
		        		wait();
					} catch (Exception e) {
					}
		        	mostraMenu();
				}
			} else {
				JOptionPane.showMessageDialog(
					frame, "Errore aggiunta libro");
	        	try {
	        		wait();
				} catch (Exception e) {
				} 
			}
		}
		
		if(modificaLibro) {
			if(ok) {
				if(libreria.modificaLibro(libriRicerca.get(
						jComboBoxScelta.getSelectedIndex()), libro)) {
					JOptionPane.showMessageDialog(
						frame, "Libro modificato con successo");
		        	try {
		        		wait();
					} catch (Exception e) {
					}
		        	mostraMenu();
				}
			} else {
				JOptionPane.showMessageDialog(
					frame, "Errore modifica libro");
	        	try {
	        		wait();
				} catch (Exception e) {
				} 
			}
		}
	}
	
	/**
	 * Metodo richiamato dopo la selezione dell'attributo da ricercare.
	 * apre un jTextField per la ricerca di un libro in base alla 
	 * stringa/opzione inserita
	 * 
	 * @param i indice corrispondente all'attributo selezionato
	 */
	private void getInput(int i) 
	{
		pan2.removeAll();
		
		JLabel label = new JLabel();
		JButton button = new JButton();
		
		button.setFocusPainted(false);
		button.addActionListener(e -> cerca(i));
		button.setPreferredSize(new Dimension(70, 30));
		button.setMaximumSize(new Dimension(70, 30));
		button.setVisible(true);
		
		panelInput = new JPanel();
		panelInput.setLayout(new BorderLayout(10, 50));
		panelInput.add(label, BorderLayout.WEST);
		panelInput.add(button, BorderLayout.EAST);
		
		if(i != 4) {
			textField = new JTextField();
			
			panelInput.add(textField, BorderLayout.CENTER);
			
			button.setText("Cerca");
		}
		
		pan1.add(panelInput, BorderLayout.SOUTH);
		
		switch (i) {
		case 0: {
			lab1.setText("Cerca per Titolo");
			label.setText("   Inserire il titolo da cercare");
			break;
		}
		case 1: {
			lab1.setText("Cerca per Autore");
			label.setText("   Inserire l'autore da cercare");
			break;
		}
		case 2: {
			lab1.setText("Cerca per Editore");
			label.setText("   Inserire l'editore da cercare");
			break;
		}
		case 3: {
			lab1.setText("Cerca per Codice ISBN");
			label.setText("   Inserire il codice ISBN da cercare");
			break;
		}
		case 4: {
			lab1.setText("Cerca per Prezzo");
			label.setText(
				"   Desidera ricercare i libri per crescenza di prezzo? : ");
			
			panelInput.add(jComboBox, BorderLayout.CENTER);
			
			button.setText("Invio");
			break;
		}
		case 5: {
			lab1.setText("Cerca per Collana");
			label.setText("   Inserire la collana da cercare");
			break;
		}
		default:
			break;
		}
		
		butt.setText("Menu'");
	}
	
	//pressione pulsante cerca	(button)
	/**
	 * metodo richiamato alla pressione del pulsante cerca di
	 * getInput
	 * assegna a libriRicerca l'arrayList di libri inerenti 
	 * alla ricerca effettuata
	 * infine stampa libriRicerca
	 * @param i indice corrispondente all'opzione di ricerca selezionata
	 */
	private void cerca(int i) 
	{		
		String ricercaString = null;
		int opzione = 0;
		if(i != 4) {
			ricercaString = textField.getText();
		} else {
			opzione = jComboBox.getSelectedIndex();
		}
		
		switch (i) {
		case 0: {
			libriRicerca = libreria.cercaByTitolo(ricercaString);
			break;
		}
		case 1: {
			libriRicerca = libreria.cercaByAutore(ricercaString);
			break;
		}
		case 2: {
			libriRicerca = libreria.cercaByEditore(ricercaString);
			break;
		}
		case 3: {
			libriRicerca = libreria.cercaByCodiceIsbn(ricercaString);
			break;
		}
		case 4: {
			if(opzione == 0) {	//no
				libriRicerca = libreria.cercaByPrezzo(false);
			} else {	//si
				libriRicerca = libreria.cercaByPrezzo(true);
			}
			break;
		}
		case 5: {
			libriRicerca = libreria.cercaByCollana(ricercaString);
			break;
		}
		default:
			break;
		}

		pulisciInterfaccia();
		
		stampaLibri();		
	}
	
	/**
	 * richiama il metodo repaint del frame
	 */
	private void pulisciInterfaccia() 
	{
		frame.repaint();
	}
	
	/**
	 * Apre un menu per la ricerca di libri con criterio di ricerca 
	 * a scelta dell'utente tra:
	 * Titolo, Autore, Editore, Codice ISBN, Collana.
	 */
	private void cercaLibri() 
	{
		posizione = 1;
		
		pulisciInterfaccia();
		
		gr.clearSelection();
		lab1.setText("Indicare la preferenza di ricerca: ");
		
		rad[0].setText("Titolo");
		rad[1].setText("Autore");
		rad[2].setText("Editore");
		rad[3].setText("Codice ISBN");
		rad[4].setText("Prezzo");
		rad[5].setText("Collana");	

		butt.setText("Conferma");

		pan1.removeAll();
		pan1.add(lab1);

		pan2.removeAll();
		pan2.add(westJPanel, BorderLayout.WEST);
		pan2.add(radJPanel, BorderLayout.CENTER);

		pan3.removeAll();
		pan3.revalidate();
		pan3.add(back);
		pan3.add(butt);
	}
	
	/**
	 * verifica ed avverte l'utente nel caso in cui non abbia selezionato alcun
	 * radiobutton
	 */
	private void verificaSelezione() 
	{
		if(mostraErrore)
		{
			mostraErrore = false;
			JOptionPane.showMessageDialog(frame, "Selezionare un'opzione...");
			try {
				wait();
				} catch (Exception e) {
				} 
		}
	}
	
	/**
	 * metodo che mostra la lista di libri libriRicerca 
	 * attraverso uno jScrollPanel
	 */
	private void stampaLibri() 
	{
		pan2.removeAll();
		pan2.revalidate();
		
		//panel contenente la lista di libri
		JPanel arrayList = new JPanel();
		arrayList.setLayout(new BoxLayout(arrayList, BoxLayout.Y_AXIS));
		
		
		if(libriRicerca.size() > 0) {
			textArea = new JTextArea[libriRicerca.size()];
		} else {
			textArea = new JTextArea[1];
		}
		
		for(int i = 0; i < libriRicerca.size(); i++) {
			textArea[i] = new JTextArea(10, 20);
			textArea[i].setText(i + 1 + "\n" + libriRicerca.get(i).toString());
			textArea[i].setLineWrap(true);
			textArea[i].setWrapStyleWord(true);
			textArea[i].setEditable(false);
			
			arrayList.add(textArea[i]);
		}
		try {
			textArea[0].setCaretPosition(0);	//caret parte dalla cima
		} catch (NullPointerException e) {
			textArea[0] = new JTextArea(10, 20);
			textArea[0].setText("Nessun risultato trovato!");
			textArea[0].setLineWrap(true);
			textArea[0].setWrapStyleWord(true);
			textArea[0].setEditable(false);
			arrayList.add(textArea[0]);
		}
		textArea[0].setCaretPosition(0);
		
		//margine sinistro
		JPanel westJPanel = new JPanel();
		westJPanel.add(Box.createRigidArea(new Dimension(10, 0)));

		//pannello scroll
		final JScrollPane scrollPane = new JScrollPane(arrayList, 
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		butt.setText("Menu'");

		JPanel panelLibri = new JPanel();
		panelLibri.setLayout(new BorderLayout(10, 10));
		panelLibri.add(westJPanel, BorderLayout.WEST);
		panelLibri.add(scrollPane, BorderLayout.CENTER);
		
		pan2.add(panelLibri);
		
		//opzioni sceltaLibro
		if(selezionaLibro) {
			sceltaLibro();			
		}
	}
	
	/**
	 * metodo che mostra un pannello di opzioni indicizzato per 
	 * la selezione di un libro
	 */
	private void sceltaLibro() 
	{		
	    String[] optionsToChoose = new String[libriRicerca.size()];
	    for(int i = 0; i < libriRicerca.size(); i++) {
	    	optionsToChoose[i] = Integer.toString(i + 1);
	    }
		
	    jComboBoxScelta = new JComboBox<>(optionsToChoose);
	    jComboBoxScelta.setPreferredSize(new Dimension(50, 25));

	    JLabel label = new JLabel();
	    if(rimuoviLibro) {
		label.setText("Indicare l'indice del libro da rimuovere: ");
	    } else if(modificaLibro) {
		label.setText("Indicare l'indice del libro da modificare: ");
	    }

	    butt.setText("Conferma");

	    pan3.removeAll();
	    pan3.revalidate();
	    pan3.add(label);
	    pan3.add(jComboBoxScelta, BorderLayout.CENTER);
	    pan3.add(butt, BorderLayout.EAST);

	    if(rimuoviLibro) {
		posizione = 4;
	    } else if(modificaLibro) {
		posizione = 5;
	    }

	    if(libriRicerca.size() == 0) {
		butt.setText("Menu'");
		posizione = 2;
	    }
	}
}
