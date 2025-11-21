package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class Login {
    private String username;
    private String password;
    private String cellphoneNumber;
    private String firstName;
    private String lastName;
    private boolean isRegistered;

    public Login() {
        this.username = "";
        this.password = "";
        this.cellphoneNumber = "";
        this.firstName = "";
        this.lastName = "";
        this.isRegistered = false;
    }

    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) return false;
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasCapital = true;
            else if (Character.isDigit(c)) hasNumber = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        return hasCapital && hasNumber && hasSpecial;
    }

    public boolean checkCellPhoneNumber(String cellphone) {
        return cellphone.matches("^\\+27[0-9]{9}$");
    }

    public String registerUser(String username, String password, String cellphoneNumber, String firstName, String lastName) {
        if (!checkUserName(username)) return "Username must contain '_' and <=5 chars.";
        if (!checkPasswordComplexity(password)) return "Password must contain uppercase, number, special char, >=8 chars.";
        if (!checkCellPhoneNumber(cellphoneNumber)) return "Cellphone must start with +27 and have 12 digits.";

        this.username = username;
        this.password = password;
        this.cellphoneNumber = cellphoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isRegistered = true;

        storeUserJSON(); // save as JSON string
        return "User registered successfully.";
    }

    public boolean loginUser(String inputUsername, String inputPassword) {
        return isRegistered && inputUsername.equals(this.username) && inputPassword.equals(this.password);
    }

    public String returnLoginStatus(boolean loginSuccess) {
        if (loginSuccess) return "Welcome " + this.firstName + " " + this.lastName + " it’s great to see you again!";
        return "Username or password incorrect.";
    }

    // Store user as JSON string in file
    public void storeUserJSON() {
        String json = String.format("{\"username\":\"%s\",\"password\":\"%s\",\"cellphone\":\"%s\",\"firstName\":\"%s\",\"lastName\":\"%s\"}",
                username, password, cellphoneNumber, firstName, lastName);
        try (FileWriter writer = new FileWriter("users.json", true)) {
            writer.write(json + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
}
