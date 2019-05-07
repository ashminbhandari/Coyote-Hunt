import java.util.Scanner; //To scan the configuration settings
import java.io.File; //To import the configuration file 
import java.io.IOException; //To report IOException

/**
 * @brief Configuration class.
 * 		
 */
public class config {	
	public static int debug;
	public static int m; //The m dimension for the grid
	public static int n; //The n dimension for the grid 
	public static int ROADRUNNER; //Road-runner definition
	public static int COYOTE; //Coyote definition
	public static int AGENT; //Agent definition
	public static int SPEED; //Speed of the simulation
	public static int breedTimeCoyote; //Breeding time for coyote
	public static int breedTimeRoadrunner; //Breeding time for the road-runner
	public static int hungerToleranceCoyote; //Hunger tolerance for coyote
	
	static {
			//Ask for the name of the configuration file 
			System.out.print("Please enter the name of configuration file (Press ENTER to use defaults.): ");
			Scanner stringScanner = new Scanner(System.in);
			String fileName = stringScanner.nextLine();
			
			//Try and read the file, if an exception is found report it 
			try (Scanner fileScanner = new Scanner(new File(fileName))) {
				//Set values as gotten from the file 
				debug = fileScanner.nextInt();
				m = fileScanner.nextInt();
				n = fileScanner.nextInt();
				ROADRUNNER = fileScanner.nextInt();
				COYOTE = fileScanner.nextInt();
				AGENT = fileScanner.nextInt();
				SPEED = fileScanner.nextInt();
				breedTimeCoyote = fileScanner.nextInt();
				breedTimeRoadrunner = fileScanner.nextInt();
				hungerToleranceCoyote = fileScanner.nextInt();
			} 
			catch (IOException ex) {
				//If file loading failed, proceed with default values
				System.out.println("Couldn't load the configuration file. Default values used.");
				debug = 0;
				m = 3;
				n = 3;
				ROADRUNNER = 1;
				COYOTE = 2;
				AGENT = 0;
				SPEED = 1000;
				breedTimeCoyote = 8;
				breedTimeRoadrunner = 4;
				hungerToleranceCoyote = 4;
			}
			System.out.println("Loading...");
	}
}
