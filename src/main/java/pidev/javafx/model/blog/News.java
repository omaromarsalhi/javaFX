package pidev.javafx.model.blog;

public class News {
    private String date;
    private String title;
    private String description;
    private String imageUrl;
    private String linkUrl;
    private String sourceId;
    private String sourceIcon;

    public News(String sourceId, String date, String title, String description, String imageUrl, String linkUrl, String sourceIcon) {
        this.date = date;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.linkUrl = linkUrl;
        this.sourceId = sourceId;
        this.sourceIcon = sourceIcon;
    }

    @Override
    public String toString() {
        return "News{" +
                "source = " + sourceId + " " + sourceIcon + " " +
                "date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                '}';
    }

    public String getSourceIcon() {
        return sourceIcon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
