import java.util.Scanner;

public class Hangman {
	public static void main(String[] args) {
		String word = "PROGRAMMERA";	// Man kan byta ut ordet, byt bara tecknena i strängen
		String[] parts = word.trim().split(""); // Delar upp strängen i bokstäver, dock blir det första elementet i listan en "tom" bokstav, som vi bortser ifrån senare
		
		Scanner scanner = new Scanner(System.in); // Scanner som läser in tecken för tecken
		
		String[] answers = new String[word.length() + 6];	// Här sparar vi alla gissningarna. Vi kommer aldrig behöva fler platser eftersom vi max kan ha 6 felgissningar och max hela ordet som rättgissningar
		int wrongAnswers = 0; // Räknar antal fel svar
		String completeWord = ""; // Sparar det användaren listat ut
		
		// Nu loopar så länge vi kan, vi kan ju aldrig lägga in fler än listans längd i listan
		for (int a = 0; a < answers.length; a++) {
			if (wrongAnswers >= 6) { // Om man har missat fler än 5 gånger så är man körd
				System.out.println("Nu har du gissat fel 6 gånger!");
				break; // Avsluta for-loopen
			}
			// Vi börjar bygga upp ordet så långt användaren listat ut
			completeWord = "";
			for (int i = 1; i < parts.length; i++) { // Loopa för varje tecken i strängen och avgör om vi ska skriva ut bokstaven eller ett frågetecken
				boolean printed = false;
				for (int j = 0; j < answers.length; j++) {
					if (parts[i].equals(answers[j])) { // Om användaren har svarat bokstaven ska den visas.
						completeWord += answers[j]; // Därför lägger vi till den i strängen.
						printed = true;
					}
				}
				if (printed == false) {
					completeWord += "?"; // Användaren verkar inte ha gissat denna bokstav så vi skriver ett frågetecken
				}
			}
			System.out.println(); // Tom rad
			System.out.println(completeWord); // Skriv ut hela ordet
			if (completeWord.equals(word)) { // Om användaren gissat hela ordet är vi klara
				break; // Fortsätt till efter loopen
			}
			System.out.println("Du har " + (6-wrongAnswers) + " försök kvar.");
			System.out.print("Gissa på en bokstav: ");
			String letter = scanner.next(); // Läs ett ord från input
			
			// Kolla om användaren redan gissat på bokstaven, då behöver vi ju inte 
			// lägga till den till gissningarna eftersom den redan finns där
			boolean found = false;
			for (String l : answers) { // Betyder att "gör detta för varje element i answers och kalla det elementet för l"
				if (l != null && l.equals(letter)) {
					found = true;
					break;
				}
			}
			if (found == false) { // Om vi inte hittat gissningen sen tidigare, lägg till den i listan
				answers[a] = letter;
			}
			
			// Kolla om det var rätt eller fel
			boolean correct = false;
			for (String l : parts) { // Gå igenom hela parts (dvs hela ordet)
				if (letter != null && letter.equals(l)) { // Kollar om gissningen finns i ordet, isf ska det ju vara korrekt
					correct = true;
					break;
				}
			}
			
			if (correct == false) {
				wrongAnswers++;
				System.out.println("Fel.");
			} else {
				System.out.println("Rätt.");
			}
		}
		
		// Nu har vi avslutat spelet
		if (completeWord.equals(word)) { // Om det är rätt svar
			System.out.println("Du vann!");
		} else {
			System.out.println("Du förlorade!");
		}
	}
}