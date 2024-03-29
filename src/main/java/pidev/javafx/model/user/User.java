package pidev.javafx.model.user;

import java.util.Random;

public class User {
    private int id;
    private int age;
    private int num;
    private int droit_acces;
    private String firstname;
    private String lastname;
    private String email;
    private  String cin;
    private String adresse;
    private Role role;
    private String password;
    private String date;
    private String dob;
    private String status;
    private String verificationCode;
    private String photos;
    private String gender;
    private int state;

    public User(String firstname, String email, String password, String cin, int age, int num, String adresse, String dob, String lastName, String status, String date, Role role, String photos, String gender) {
    }

    public Boolean getPassReseted() {
        return isPassReseted;
    }

    public void setPassReseted(Boolean passReseted) {
        isPassReseted = passReseted;
    }

    private Boolean isPassReseted=false;


    public User(int idUser, String firstname, String email, String password, String cin, int age, int num, String adresse, String dob, String lastName, String status, String date, Role role, String photos, String gender) {
        this.id = idUser;
        this.firstname = firstname;
        this.email = email;
        this.cin = cin;
        this.age = age;
        this.num = num;
        this.adresse = adresse;
        this.dob = dob;
        this.lastname = lastName;
        this.role=role;
        this.status =status;
        this.date=date;
        this.photos=photos;
        this.gender=gender;
        this.password=password;
    }

    public User(int idUser) {
        this.id = idUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", age=" + age +
                ", num=" + num +
                ", droit_acces=" + droit_acces +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", cin='" + cin + '\'' +
                ", adresse='" + adresse + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                ", date='" + date + '\'' +
                ", dob='" + dob + '\'' +
                ", status='" + status + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                ", photos='" + photos + '\'' +
                ", gender='" + gender + '\'' +
                ", verified=" + verified +
                ", IsConnected=" + IsConnected +
                ", idMunicipalite=" + idMunicipalite +
                '}';
    }



    public String getVerificationCode() {
        return verificationCode;
    }

    private boolean verified;


    int IsConnected;
      int idMunicipalite;


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





    public User(String firstname, String email, String cin, int age, int num, String adresse, String dob, String lastname,String status,String date,Role role,int idMunicipalite ) {
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
        this.idMunicipalite=idMunicipalite;
    }


    public User(int id, String firstname, String lastname, String email, String cin, int age, int num, String adresse, Role role,String password,String photos) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.cin = cin;
        this.age = age;
        this.num = num;
        this.adresse = adresse;
        this.role = role;
        this.password = password;
        this.photos=photos;

    }

    public User(String firstname, String email, String cin, int age, int num, String adresse, String dob, String lastName, String status, String date, Role role,String Photos,String gender) {
        this.firstname = firstname;
        this.email = email;
        this.cin = cin;
        this.age = age;
        this.num = num;
        this.adresse = adresse;
        this.dob = dob;
        this.lastname = lastName;
        this.role=role;
        this.status =status;
        this.date=date;
        this.photos=Photos;
        this.gender=gender;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public int getIsConnected() {
        return IsConnected;
    }

    public void setIsConnected(int isConnected) {
        IsConnected = isConnected;
    }


    public int getIdMunicipalite() {
        return idMunicipalite;
    }

    public void setIdMunicipalite(int idMunicipalite) {
        this.idMunicipalite = idMunicipalite;
    }
    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String generateVerificationCode() {
        // Générer un code aléatoire de 6 caractères
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        verificationCode = sb.toString();
        return verificationCode;
    }






}
