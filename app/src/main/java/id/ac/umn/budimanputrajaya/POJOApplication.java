package id.ac.umn.budimanputrajaya;

public class POJOApplication {
    private String id;
    private String name;
    private String description;
    private String downloads;
    private String iconUrl;
    private String storeUrl;
    private String version;
    private String price;
    private String priceValue;
    private String priceCurrency;
    private String publisherName;
    private String publisherUrl;
    private String rating;

    public POJOApplication(){}
    public POJOApplication(String id, String name, String description, String downloads, String iconUrl, String storeUrl, String version, String price, String priceValue, String priceCurrency, String publisherName, String publisherUrl, String rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.downloads = downloads;
        this.iconUrl = iconUrl;
        this.storeUrl = storeUrl;
        this.version = version;
        this.price = price;
        this.priceValue = priceValue;
        this.priceCurrency = priceCurrency;
        this.publisherName = publisherName;
        this.publisherUrl = publisherUrl;
        this.rating = rating;
    }

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

    public String getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(String priceValue) {
        this.priceValue = priceValue;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
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
}
