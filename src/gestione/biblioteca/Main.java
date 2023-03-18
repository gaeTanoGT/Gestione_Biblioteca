package gestione.biblioteca;

import java.io.IOException;

/**
 * Classe madre per l'istanza di un oggetto di tipo menu
 * 
 * @author Torrisi Gaetano
 * @version 2.0
 */
public class Main 
{
	/**
	 * Metodo main in cui viene istanziato un oggetto di tipo Menu
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException 
	{
		boolean interfacciaGrafica = false;
		
		if(interfacciaGrafica) {
			Interfaccia interfaccia = new Interfaccia();
			interfaccia.mostraMenu();
		} else {
			Menu menu = new Menu();
		
			menu.mostraMenu();
		}
	}
}
