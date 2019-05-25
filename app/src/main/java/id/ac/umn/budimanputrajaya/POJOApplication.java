package id.ac.umn.budimanputrajaya;

import android.os.Parcel;
import android.os.Parcelable;

public class POJOApplication implements Parcelable {
    private String id;
    private String name;
    private String description;
    private String downloads;
    private String iconUrl;
    private String storeUrl;
    private String version;
    private String price;
    private String publisherName;
    private String publisherUrl;
    private String rating;

    public POJOApplication(){}
    public POJOApplication(String id, String name, String description, String downloads, String iconUrl, String storeUrl, String version, String price, String publisherName, String publisherUrl, String rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.downloads = downloads;
        this.iconUrl = iconUrl;
        this.storeUrl = storeUrl;
        this.version = version;
        this.price = price;
        this.publisherName = publisherName;
        this.publisherUrl = publisherUrl;
        this.rating = rating;
    }

    protected POJOApplication(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        downloads = in.readString();
        iconUrl = in.readString();
        storeUrl = in.readString();
        version = in.readString();
        price = in.readString();
        publisherName = in.readString();
        publisherUrl = in.readString();
        rating = in.readString();
    }

    public static final Creator<POJOApplication> CREATOR = new Creator<POJOApplication>() {
        @Override
        public POJOApplication createFromParcel(Parcel in) {
            return new POJOApplication(in);
        }

        @Override
        public POJOApplication[] newArray(int size) {
            return new POJOApplication[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownloads() {
        return downloads;
    }

    public void setDownloads(String downloads) {
        this.downloads = downloads;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherUrl() {
        return publisherUrl;
    }

    public void setPublisherUrl(String publisherUrl) {
        this.publisherUrl = publisherUrl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(downloads);
        dest.writeString(iconUrl);
        dest.writeString(storeUrl);
        dest.writeString(version);
        dest.writeString(price);
        dest.writeString(publisherName);
        dest.writeString(publisherUrl);
        dest.writeString(rating);
    }
}
