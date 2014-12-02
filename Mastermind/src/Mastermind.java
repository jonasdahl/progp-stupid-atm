import java.util.Random;
import java.util.Scanner;

public class Mastermind {
	/* Det vi gör är egentligen att vi kollar inmatningen ord för ord och kollar först om det är
	   rätt siffra på rätt plats. Vi sätter alla de till -1 för att inte kunna jämföra igen.
	   Om alla är rätt på rätt plats så slutar vi loopen.
	   Sen kollar vi rätt siffra.
	   Och sen skriver vi ut "resten".
	   Sen loopar vi.
	   */
	public static void main(String[] args) {
		// Skapar en random lista med fyra intar som är random (rätt svar)
		Random random = new Random();
		int[] correctNums = {random.nextInt(6) + 1, random.nextInt(6) + 1, random.nextInt(6) + 1, random.nextInt(6) + 1};
		// Om man vill veta svaret innan man börjar: (tråkigt men bra för testing) ta bort kommentarerna på nästa rad
		//System.out.println("" + correctNums[0] + correctNums[1] + correctNums[2] + correctNums[3]);
		
		// Skriver ut instruktioner
		System.out.println("Gissa genom att skriva första siffran, följt av mellanslag, följt av andra siffran osv.");
		System.out.println("Tex så här: 8 2 3 2");
		System.out.println("Du får tillbaka 1 om någon siffra var på rätt plats, 0 om någon siffra ");
		System.out.println("fanns med men var på fel plats och _ om en siffra inte ska vara med.");
		
		// Scanner är vår inmatning
		Scanner scanner = new Scanner(System.in);
		
		// Räknar försök så vi kan skriva ut det.
		int tries = 0;
		
		while (true) { // Loopa i all oändlighet.
			// Kopierar correctnums för att kunna sätta alla till -1 utan att förstöra svaret.
			int[] correctNumsCopy = {correctNums[0], correctNums[1], correctNums[2], correctNums[3]};
			
			// Antal rätt svar
			int answers = 0;
			
			tries = tries + 1; // Öka försöken, det här är ju ett försök
			System.out.print(tries + ".  "); // Skriv ut vilket försök vi gör
			int[] guesses = new int[4]; // Skapa en lista som ska hålla gissningarna
			// Samla in alla siffror som användaren skrivit.
			for (int i = 0; i < 4; i++) {
				String number = scanner.next(); // Läs till nästa mellanslag
				guesses[i] = Integer.parseInt(number);
			}
			
			// Nu börjar vi kolla gissningarna
			System.out.print("Svar:       ");
			// Kolla alla som är på rätt plats och rätt siffra
			for (int i = 0; i < 4; i++) {
				if (guesses[i] == correctNums[i]) {  // Rätt siffra på rätt plats!
					System.out.print("1 ");
					guesses[i] = -1; // Sätt till -1 för att vi inte ska jämföra igen
					correctNumsCopy[i] = -1; // Samma här
					answers++; // Öka rätt svar med ett
				}
			}
			
			if (answers == 4) { // Om alla svar är rätt
				System.out.println();
				System.out.println("Grattis, du har vunnit!");
				break; // Hoppa ur den yttre loopen, vi har ju vunnit och det tar slut
			}
			
			// Kolla alla som är rätt siffra
			for (int i = 0; i < 4; i++) {
				if (guesses[i] != -1) {	// Om vi inte redan satt rätt på siffran
					// Kolla igenom alla korrekta svar
					for (int j = 0; j < 4; j++) {
						if (guesses[i] == correctNumsCopy[j]) { // Rätt svar, men fel plats, eftersom vi redan kollat rätt plats tidigare.
							System.out.print("0 ");
							guesses[i] = -1;
							correctNumsCopy[j] = -1;
							break;	// Hoppa ur den inre loopen för att inte fortsätta leta efter något vi redan hittat.
						}
					}
				}
			}
			
			// Skriv ut alla som är helt fel
			for (int i = 0; i < 4; i++) {
				if (guesses[i] != -1) {	// Om vi inte redan satt rätt på siffran så är den fel
					System.out.print("_ ");
				}
			}
			System.out.println();
		}
	}
}