package pidev.javafx.model.User;

public class User {
      int id;
      int age;
      int num;
      int droit_acces;
      String firstname;
      String lastname;
      String email;
      String cin;
      String adresse;
      Role role;
      String password;
      String date;
      String dob;
      String status;

    public User() {
    }

    public User(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;

    }

    public User(int id, String firstname, String lastname, String email, String cin, int age, int num, String adresse, Role role, int droit_acces, String password, String date) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.cin = cin;
        this.age = age;
        this.num = num;
        this.adresse = adresse;
        this.role = role;
        this.droit_acces = droit_acces;
        this.password = password;
        this.date = date;
    }

    public User(int id, String firstname, String lastname, String email, String cin, int age, int num, String adresse, Role role) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.cin = cin;
        this.age = age;
        this.num = num;
        this.adresse = adresse;
        this.role = role;
        this.droit_acces = droit_acces;
        this.password = password;
        this.date = date;
    }

    public User(String firstname, String email, String cin, int age, int num, String adresse, String dob, String lastname,String status,String date,Role role) {
        this.firstname = firstname;
        this.email = email;
        this.cin = cin;
        this.age = age;
        this.num = num;
        this.adresse = adresse;
        this.dob = dob;
        this.lastname = lastname;
        this.role=role;
        this.status =status;
        this.date=date;
    }







    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Role getRole() {
        return role;
    }

    public void setRole( Role role) {
        this.role=role;
    }

    public int getDroit_acces() {
        return droit_acces;
    }

    public void setDroit_acces(int droit_acces) {
        this.droit_acces = droit_acces;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDob() {
        return dob;
    }









}
