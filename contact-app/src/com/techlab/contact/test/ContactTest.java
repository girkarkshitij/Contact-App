package com.techlab.contact.test;

import java.sql.SQLException;
import java.util.Scanner;

import com.techlab.contact.Contact;

public class ContactTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		final Scanner sc = new Scanner(System.in);
		Contact contact = new Contact();

		boolean exit = false;
		String fname, lname, number, email;
		int choice, choice2;
		String[] input;

		contact.connect();

		do {
			System.out.println();
			System.out.println("Enter your choice:");
			System.out.println("1. Display the contacts");
			System.out.println("2. Add a new contact");
			System.out.println("3. Update a contact");
			System.out.println("4. Delete a contact");
			System.out.println("5. Exit");

			choice = inputChoice(sc);

			switch (choice) {
			case 1:
				System.out.println("All contacts:");
				contact.display();
				break;
			case 2:
				input = inputName(sc, contact);
				fname = input[0];
				lname = input[1];
				number = inputNumber(sc, contact);
				email = inputEmail(sc, contact);
				contact.add(fname, lname, number, email);
				break;
			case 3:
				while (true) {
					System.out.println("Enter the details of contact to be updated");
					System.out.println("Enter first name");
					fname = sc.nextLine();
					System.out.println("Enter last name");
					lname = sc.nextLine();
					if (contact.contactExists(fname, lname)) {
						boolean exitLoop = false;
						do {
							System.out.println("Which field do you want to update?");
							System.out.println("1. First Name");
							System.out.println("2. Last Name");
							System.out.println("3. Phone Number");
							System.out.println("4. Email");
							System.out.println("5. Exit");

							choice2 = inputChoice(sc);

							switch (choice2) {
							case 1:
								String newFname = inputNewFirstName(lname, sc, contact);
								contact.updateFirstName(fname, lname, newFname);
								break;
							case 2:
								String newLname = inputNewLastName(fname, sc, contact);
								contact.updateLastName(fname, lname, newLname);
								break;
							case 3:
								String newNumber = inputNumber(sc, contact);
								contact.updateNumber(fname, lname, newNumber);
								break;
							case 4:
								String newEmail = inputEmail(sc, contact);
								contact.updateEmail(fname, lname, newEmail);
								break;
							case 5:
								exitLoop = true;
								break;
							default:
								System.err.println("Invalid input!");
							}
						} while (!exitLoop);
						break;
					} else {
						System.out.println("Contact does not exist.");
					}
				}
				break;
			case 4:
				input = inputName(sc, contact);
				fname = input[0];
				lname = input[1];
				if (contact.contactExists(fname, lname)) {
					contact.delete(fname, lname);
				} else {
					System.err.println("Contact does not exsit.");
				}
				break;
			case 5:
				exit = true;
				break;
			default:
				System.err.println("Invalid input!");
			}

		} while (!exit);
	}

	public static int inputChoice(Scanner sc) {
		int choice;
		while (true) {
			try {
				choice = Integer.parseInt(sc.nextLine());
				break;
			} catch (NumberFormatException e) {
				System.err.println("Invalid input! Please enter a number.");
			}
		}
		return choice;
	}

	public static String inputNewFirstName(String lname, Scanner sc, Contact contact) throws SQLException {
		String newFname;
		while (true) {
			System.out.println("Enter new first name:");
			newFname = sc.nextLine();
			if (contact.contactExists(newFname, lname)) {
				System.err.println("A contact with this name already exists.");
			} else {
				break;
			}
		}
		return newFname;
	}

	public static String inputNewLastName(String fname, Scanner sc, Contact contact) throws SQLException {
		String newLname;
		while (true) {
			System.out.println("Enter new last name:");
			newLname = sc.nextLine();
			if (contact.contactExists(newLname, fname)) {
				System.err.println("A contact with this name already exists.");
			} else {
				break;
			}
		}
		return newLname;
	}

	public static String[] inputName(Scanner sc, Contact contact) throws SQLException {
		String[] input = new String[2];
		while (true) {
			System.out.println("Enter first name:");
			input[0] = sc.nextLine();
			System.out.println("Enter last name:");
			input[1] = sc.nextLine();
			if (contact.contactExists(input[0], input[1])) {
				System.err.println("A contact with this name already exists.");
			} else {
				break;
			}
		}
		return input;
	}

	public static String inputNumber(Scanner sc, Contact contact) throws SQLException {
		String number;
		while (true) {
			System.out.println("Enter number:");
			number = sc.nextLine();
			if (contact.numberExists(number)) {
				System.err.println("This number already exists.");
			} else {
				break;
			}
		}
		return number;
	}

	public static String inputEmail(Scanner sc, Contact contact) throws SQLException {
		String email;
		while (true) {
			System.out.println("Enter email:");
			email = sc.nextLine();
			if (contact.numberExists(email)) {
				System.err.println("This email id already exists.");
			} else {
				break;
			}
		}
		return email;
	}
}
