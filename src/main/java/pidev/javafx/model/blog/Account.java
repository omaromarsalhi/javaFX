package pidev.javafx.model.blog;

public class Account {
    private int id;
    private String name;
    private String profileImg;
    private boolean isVerified;

    public Account(int id, String name, String profileImg, boolean isVerified) {
        this.id = id;
        this.name = name;
        this.profileImg = profileImg;
        this.isVerified = isVerified;
    }
    public Account() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
