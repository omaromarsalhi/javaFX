package pidev.javafx.model.blog;

public enum Reactions {
    NON(0,"Like","#606266", "/icons/blogIcons/like.png"),
    LIKE(1,"Like","#20749C", "/icons/blogIcons/ic_like.png"),
    HAHA(4,"Haha","#f2b548", "/icons/blogIcons/ic_haha.png"),
    SAD(6,"Sad","#f2b548", "/icons/blogIcons/ic_sad.png"),
    ANGRY(7,"Angry","#f2b548", "/icons/blogIcons/ic_angry.png");

    private int id;
    private int idPost;
    private String name;
    private String color;
    private String imgSrc;

    Reactions(int id, int idPost, String name, String color, String imgSrc) {
        this.id = id;
        this.idPost = idPost;
        this.name = name;
        this.color = color;
        this.imgSrc = imgSrc;
    }

    Reactions(int id, String name, String color, String imgSrc) {
        this.id = id;
        this.idPost = idPost;
        this.name = name;
        this.color = color;
        this.imgSrc = imgSrc;
    }

    public int getIdPost() {
        return idPost;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
