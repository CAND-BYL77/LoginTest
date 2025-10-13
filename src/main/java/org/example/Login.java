package org.example;

public class Login {
    // Instance variables to store user data
    private String username;
    private String password;
    private String cellphoneNumber;
    private String firstName;
    private String lastName;
    private boolean isRegistered;

    // Constructor
    public Login() {
        this.username = "";
        this.password = "";
        this.cellphoneNumber = "";
        this.firstName = "";
        this.lastName = "";
        this.isRegistered = false;
    }

    // Method to check username format - PURE VALIDATION
    public boolean checkUserName(String username) {
        // Check if username contains underscore and is no more than 5 characters
        return username.contains("_") && username.length() <= 5;
    }

    // Method to check password complexity - PURE VALIDATION
    public boolean checkPasswordComplexity(String password) {
        // Password must be at least 8 characters long
        if (password.length() < 8) {
            return false;
        }

        // Check for capital letter, number, and special character
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }

        return hasCapital && hasNumber && hasSpecial;
    }

    // Method to check cellphone number format using regex - FIXED VALIDATION
    public boolean checkCellPhoneNumber(String cellphone) {
        String regex ="^\\+27[0-9]{9}$";
        return cellphone.matches(regex);
    }

    // Method to register user - DATA HANDLING ONLY
    public String registerUser(String username, String password, String cellphoneNumber, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.cellphoneNumber = cellphoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isRegistered = true;

        return "User registered successfully!";
    }

    // Method to verify login credentials - PURE VALIDATION
    public boolean loginUser(String inputUsername, String inputPassword) {
        if (!isRegistered) {
            return false;
        }
        return inputUsername.equals(this.username) && inputPassword.equals(this.password);
    }

    // Method to return login status message - DATA HANDLING ONLY
    public String returnLoginStatus(boolean loginSuccess) {
        if (loginSuccess) {
            return "Welcome " + this.firstName + ", " + this.lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // Getters for testing purposes
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCellphoneNumber() {
        return cellphoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isRegistered() {
        return isRegistered;
    }
}