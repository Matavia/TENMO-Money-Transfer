package com.techelevator.tenmo;

import java.math.BigDecimal;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.UserService;

import com.techelevator.tenmo.services.AuthenticationServiceException;

import com.techelevator.view.ConsoleService;

public class App {

	private static final String API_BASE_URL = "http://localhost:8080/";

	private static final String MENU_OPTION_EXIT = "Exit";
	private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN,
			MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS,
			MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS,
			MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };

	private AuthenticatedUser currentUser;
	private ConsoleService console;
	private AuthenticationService authenticationService;
	private UserService userService;

	private Transfer transfer;

	public static void main(String[] args) {
		App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL),
				new UserService(API_BASE_URL));
		app.run();
	}

	public App(ConsoleService console, AuthenticationService authenticationService, UserService userService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.userService = userService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");

		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while (true) {
			String choice = (String) console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if (MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if (MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
				viewTransferDetails();

			} else if (MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				System.out.println("***Feature Coming Soon***");
			} else if (MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if (MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				System.out.println("***Feature Coming Soon***");
			} else if (MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		console.printBalance(userService.getBalance());
	}

	private void viewTransferHistory() {
		console.printTransferHistory(userService.viewTransferHistory(), currentUser.getUser());

	}

	private void viewTransferDetails() {
		Transfer[] transfers = userService.viewTransferHistory();
		Transfer foundTransfer = null;

		while (foundTransfer == null) {
			int id = console.getUserInputInteger("Enter transfer id to view details (Enter 0 to cancel)");
			if (id == 0) {
				break;
			}

			for (Transfer transfer : transfers) {
				if (id == transfer.getTransferId()) {
					foundTransfer = userService.viewTransferDetails(id);
					console.printHeader("Transfer Details");
					System.out.println(foundTransfer);
				}
			}
			if (foundTransfer == null) {
				System.out.println("**Invalid selection**");
			}

		}
	}

	private void sendBucks() {
		User accountFrom = currentUser.getUser();
		console.printHeader("User ID		Name");
		console.printUserList(userService.listAllUsers());
		int accountTo = -1;

		while (accountTo < 0) {
			accountTo = console
					.getUserInputInteger("Please enter the id of the user to Send Bucks to (Enter 0 to cancel) ");
			if (accountTo == 0) {
				break;
			} else if (accountTo == currentUser.getUser().getId()) {
				accountTo = -1;
				System.out.println("\n **Sorry can't send funds to yourself, try again** \n");
			} else {
				int amount = console
						.getUserInputInteger("Please enter the amount you'd like to send (Enter 0 to cancel)");
				if (amount > userService.getBalance().intValue()) {
					System.out.println("\nNot enough balance in your account!");
				} else if(amount < 0) {
					System.out.println("\nQuit playin' wit me, enter an amount over $0 cheapo");
					
				}else if (amount == 0) {
					break;
				} else {
					Transfer sentTransfer = userService.sendTransfer(accountTo, BigDecimal.valueOf(amount),
							accountFrom.getId());
					console.printTransferSuccessMessage(sentTransfer);
				}
			}
		}

	}

	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while (!isAuthenticated()) {
			String choice = (String) console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
		while (!isRegistered) // will keep looping until user is registered
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				authenticationService.register(credentials);
				isRegistered = true;
				System.out.println("Registration successful. You can now login.");
			} catch (AuthenticationServiceException e) {
				System.out.println("REGISTRATION ERROR: " + e.getMessage());
				System.out.println("Please attempt to register again.");
			}
		}
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) // will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				currentUser = authenticationService.login(credentials);
				this.userService.setAuthToken(currentUser.getToken());
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: " + e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}

	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}

}
