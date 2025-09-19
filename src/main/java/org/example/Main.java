package org.example;

import java.util.Scanner;

public class Main {
        public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);

                // The user registration.
                System.out.println("User Registration");
                System.out.print("Enter first name: ");
                String firstName = scanner.nextLine();

                System.out.print("Enter last name: ");
                String lastName = scanner.nextLine();

                System.out.print("Enter username: ");
                String username = scanner.nextLine();

                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                System.out.print("Enter cellphone number (+27...): ");
                String cellphone = scanner.nextLine();

                // This is where we create the Login object
                Login login = new Login(username, password, firstName, lastName, cellphone);

                // Registration part of the application.
                System.out.println(login.registerUser());

                // This is the Login part.
                System.out.println("\nLogin");
                System.out.print("Enter username: ");
                String enteredUsername = scanner.nextLine();

                System.out.print("Enter password: ");
                String enteredPassword = scanner.nextLine();

                boolean success = login.loginUser(enteredUsername, enteredPassword);
                System.out.println(login.returnLoginStatus(success));

                scanner.close();
        }
}
