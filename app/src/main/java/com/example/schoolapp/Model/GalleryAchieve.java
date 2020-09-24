package com.example.schoolapp.Model;

public class GalleryAchieve {
    String ID,Tittle,CreatedDate,CreatedBy,UpdatedDate,UpdatedBy,IsActive,IsDeleted,Description,FileName;

    public String getID() {
        return ID;
    }

    public String getTittle() {
        return Tittle;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public String getIsActive() {
        return IsActive;
    }

    public String getIsDeleted() {
        return IsDeleted;
    }

    public String getDescription() {
        return Description;
    }


    public String getFileName() {
        return FileName;
    }

    public GalleryAchieve(String ID, String tittle, String createdDate, String createdBy, String updatedDate,
                          String updatedBy, String isActive, String isDeleted, String description,   String fileName) {
        this.ID = ID;
        Tittle = tittle;
        CreatedDate = createdDate;
        CreatedBy = createdBy;
        UpdatedDate = updatedDate;
        UpdatedBy = updatedBy;
        IsActive = isActive;
        IsDeleted = isDeleted;
        Description = description;

        FileName = fileName;
    }
}
