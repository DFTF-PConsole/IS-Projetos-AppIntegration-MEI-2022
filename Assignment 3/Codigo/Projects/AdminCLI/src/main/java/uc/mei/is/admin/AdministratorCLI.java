package uc.mei.is.admin;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import uc.mei.is.admin.command.Command;
import uc.mei.is.admin.command.RetrieveMinMaxTempLocation;
import uc.mei.is.admin.command.RetrieveMinMaxTempStation;
import uc.mei.is.admin.command.RetrieveMinTempWeatherStationsRedAlert;
import uc.mei.is.admin.command.RetrieveNumberAlertsType;
import uc.mei.is.admin.command.RetrieveNumberAlertsWeatherStation;
import uc.mei.is.admin.command.RetrieveTempReadingsStandardWeatherEventsLocation;
import uc.mei.is.admin.command.RetrieveTempReadingsStandardWeatherEventsStation;
import uc.mei.is.admin.command.RetrieveWeatherStations;

public class AdministratorCLI {

	public static boolean debugActive = true;
	private Integer lastInsertedInt = 0;


	private final HashMap<Integer,Command> commandsMap;

	/**
	 * A command line application to let the administrator write
	 * and read data through the REST services.
	 */
	public static void main(String[] args) {
		String host = "localhost:8080";
		String basePath = "/api/rest/";
	

		new AdministratorCLI(host,basePath);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @param host
	 * @param basePath
	 */
	public AdministratorCLI(String host, String basePath){
		System.out.println("Init AdminCLI...\nAvaiable Commands:");
		commandsMap = new HashMap<>();

		boolean running = true;
		Scanner sc = new Scanner(System.in);

		while (running) {
			System.out.println("\nDump Mode enter 0\nExecute Mode enter 1\n");
			Integer cmd = 0;
			try {
				System.out.print("AdminCLI-> ");
				cmd = sc.nextInt();
				switch (cmd) {
					case 0:
						initCommands(host,basePath);
						System.out.print("AdminCLI-> ");
						cmd = sc.nextInt();
						dump(cmd, "");
						break;
					case 1:
						initCommands(host,basePath);
						System.out.print("AdminCLI-> ");

						cmd = sc.nextInt();
				
						execute(cmd, "");
						break;
					case -1:
						running = false;
						break;
					default:
						System.out.println("Please select a valid command");
						break;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				// running=false;
				System.out.println("Please select a valid command");
			} finally {
				lastInsertedInt = 0;
			}
		}
		sc.close();
	}

	private void initCommands(String host, String basePath){
		putInMap(++lastInsertedInt,new RetrieveWeatherStations(host, basePath, "station/getAll/"));
		putInMap(++lastInsertedInt, new RetrieveTempReadingsStandardWeatherEventsStation(host, basePath, "req1/getAll"));
		putInMap(++lastInsertedInt, new RetrieveTempReadingsStandardWeatherEventsLocation(host, basePath, "req2/getAll"));
		putInMap(++lastInsertedInt, new RetrieveMinMaxTempStation(host, basePath, "req3/getAll"));
		putInMap(++lastInsertedInt, new RetrieveMinMaxTempLocation(host, basePath, "req4/getAll"));
		putInMap(++lastInsertedInt, new RetrieveNumberAlertsWeatherStation(host, basePath, "req5/getAll"));
		putInMap(++lastInsertedInt, new RetrieveNumberAlertsType(host, basePath, "req6/getAll"));
		putInMap(++lastInsertedInt, new RetrieveMinTempWeatherStationsRedAlert(host, basePath, "req7/getAll"));

		//Add commands here
	}

	public Map<Integer,Command> getCommandsMap() {
		return this.commandsMap;
	}

	public Command getFromMap(Integer id){
		return commandsMap.get(id);
	}

	public void putInMap(Integer id, Command c){
		System.out.println(id + " - " + c.console() + ";");
		commandsMap.put(id, c);
	}

	public boolean execute(Integer commandID,String... args){
		return commandsMap.get(commandID).execute(args);
	}

	public void dump(Integer commandID, String... args){
		System.out.println(commandsMap.get(commandID).dump(args));
	}


	// TODO: Configurar topics (DBInfoTopics, resultsTopics) do Kafka com subscrição de updates da DB
	// https://eai-course.blogspot.com/2019/11/how-to-configure-kafka-connectors.html


	public static boolean isDebugActive() {
		return debugActive;
	}

	public static void setDebugActive(boolean debugActive) {
		AdministratorCLI.debugActive = debugActive;
	}
}
