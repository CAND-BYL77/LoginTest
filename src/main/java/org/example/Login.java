package org.example;

public class Login {
    // The User details.
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String cellphone;

    // This is the Constructor.
    public Login(String username, String password, String firstName,
                 String lastName, String cellphone) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cellphone = cellphone;
    }

    // This is the Validation methods.Username must contain exactly 1 underscore and max 5 characters
    public boolean checkUserName() {
        return username != null && username.matches("^[^_]*_[^_]*$") && username.length() <= 5;
    }

    // Password must have at least 8 characters, 1 uppercase, 1 digit, 1 special character
    public boolean checkPasswordComplexity() {
        return password != null && password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$");
    }

    // Cellphone must start with +27 followed by 9 digits
    public boolean checkCellPhoneNumber() {
        return cellphone != null && cellphone.matches("^\\+27[0-9]{9}$");
    }

    //This is the registration right here.
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        return "User registered successfully.";
    }

    //  Login
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return this.username.equals(enteredUsername) &&
                this.password.equals(enteredPassword);
    }

    public String returnLoginStatus(boolean success) {
        if (success) {
            return "Welcome " + firstName + " " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // Getters and Setters (at the bottom)
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getCellphone() { return cellphone; }
    public void setCellphone(String cellphone) { this.cellphone = cellphone; }
}
