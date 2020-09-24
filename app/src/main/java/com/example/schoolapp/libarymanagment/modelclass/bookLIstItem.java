package com.example.schoolapp.libarymanagment.modelclass;

public class bookLIstItem {
    private String ID,BookName,BookCode,AuthorName,price,Quantity,RacNo,SubjectCode,IsActive,IsDeleted;

    public bookLIstItem(String ID, String bookName, String bookCode, String authorName, String price, String quantity, String racNo, String subjectCode, String isActive, String isDeleted) {
        this.ID = ID;
       this. BookName = bookName;
        this. BookCode = bookCode;
        this. AuthorName = authorName;
        this.price = price;
        this. Quantity = quantity;
        this.  RacNo = racNo;
        this. SubjectCode = subjectCode;
        this. IsActive = isActive;
        this. IsDeleted = isDeleted;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getBookCode() {
        return BookCode;
    }

    public void setBookCode(String bookCode) {
        BookCode = bookCode;
    }

    public String getAuthorName() {
        return AuthorName;
    }

    public void setAuthorName(String authorName) {
        AuthorName = authorName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getRacNo() {
        return RacNo;
    }

    public void setRacNo(String racNo) {
        RacNo = racNo;
    }

    public String getSubjectCode() {
        return SubjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        SubjectCode = subjectCode;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }

    public String getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        IsDeleted = isDeleted;
    }
}
