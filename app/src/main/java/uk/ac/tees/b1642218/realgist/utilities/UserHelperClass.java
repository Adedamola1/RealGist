package uk.ac.tees.b1642218.realgist.utilities;

public class UserHelperClass {
    String txtFirstName, txtLastName, txtUsername, txtEmail, txtphoneNo, txtGender, txtPwd;

    public UserHelperClass() {
    }

    public UserHelperClass(String txtFirstName, String txtLastName, String txtUsername, String txtemail,
                           String txtPhoneNumber, String txtGender, String txtPwd) {
        this.txtFirstName = txtFirstName;
        this.txtLastName = txtLastName;
        this.txtUsername = txtUsername;
        this.txtEmail = txtemail;
        this.txtphoneNo = txtPhoneNumber;
        this.txtGender = txtGender;
        this.txtPwd = txtPwd;
    }

    public String getTxtFirstName() {
        return txtFirstName;
    }

    public String getTxtLastName() {
        return txtLastName;
    }

    public String getTxtUsername() {
        return txtUsername;
    }

    public String getTxtemail() {
        return txtEmail;
    }

    public String getTxtPhoneNumber() {
        return txtphoneNo;
    }


    public String getTxtGender() {
        return txtGender;
    }

    public String getTxtPwd() {
        return txtPwd;
    }
}
