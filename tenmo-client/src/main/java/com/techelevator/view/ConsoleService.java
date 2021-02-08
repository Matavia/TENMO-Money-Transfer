package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Scanner;

import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;

public class ConsoleService {

	private PrintWriter out;
	private Scanner in;

	public ConsoleService(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output, true);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		out.println();
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will
			// be null
		}
		if (choice == null) {
			out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***"
					+ System.lineSeparator());
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print(System.lineSeparator() + "Please choose an option >>> ");
		out.flush();
	}

	//Gets user input as a string
	public String getUserInput(String prompt) {
		out.print(prompt + ": ");
		out.flush();
		return in.nextLine();
	}
	
	//Gets user input as an integer
	public Integer getUserInputInteger(String prompt) {
		Integer result = null;
		do {
			out.print(prompt + ": ");
			out.flush();
			String userInput = in.nextLine();
			try {
				result = Integer.parseInt(userInput);
			} catch (NumberFormatException e) {
				out.println(System.lineSeparator() + "*** " + userInput + " is not valid ***" + System.lineSeparator());
			}
		} while (result == null);
		return result;
	}

	public void printUserList(Object[] users) {
		for (Object user : users) {
			out.print(user);
		}
	}

	// Prints the transfer history
	public void printTransferHistory(Transfer[] transfers, User user) {
	
		for (Transfer transfer : transfers)

			if (user.getUsername().contentEquals(transfer.getAccountToUsername())) {
				out.print("Transfer ID: " + transfer.getTransferId() + "\nFrom: " + transfer.getAccountFromUsername()
						+ "\nAmount: $" + transfer.getAmount() + "\n\n\n");
			} else {
				out.print("Transfer ID: " + transfer.getTransferId() + "\nTo: " + transfer.getAccountToUsername()
						+ "\nAmount: $" + transfer.getAmount() + "\n\n\n");
			}
		}

	

	// Prints Balance
	public void printBalance(BigDecimal balance) {
		out.println("------------------------------ \n");
		out.println("Your current balance is: $" + balance + "\n");
		out.println("------------------------------ \n");
	}
}
