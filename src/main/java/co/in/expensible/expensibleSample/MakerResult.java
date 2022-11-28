package co.in.expensible.expensibleSample;

import java.util.Date;


class MakerResult {

    public MakerResult(MakerEntity maker) {
        this.id = maker.getId();
        this.itemName = maker.getItemName();
        this.contactEmail = maker.getContactEmail();
        this.contactNumber = maker.getContactNumber();
        this.createdAt = maker.getCreatedAt();
        this.updatedAt = maker.getUpdatedAt();
    }
    
    private String id;
    private String itemName;
    private Date createdAt;
    private Date updatedAt;
    private String contactNumber;
    private String contactEmail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
    
    
}
