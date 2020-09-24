package com.example.schoolapp.libarymanagment.modelclass;

public class issueItem {
    private String ID,StudentId,BookId,issueedDate,Issuedforday,SubmissionDate,IssuedBy,Remarks;

    public issueItem(String id, String studentId, String bookId, String issueedDate, String issuedforday, String submissionDate, String issuedBy, String remarks) {
        ID = id;
        StudentId = studentId;
        BookId = bookId;
        this.issueedDate = issueedDate;
        Issuedforday = issuedforday;
        SubmissionDate = submissionDate;
        IssuedBy = issuedBy;
        Remarks = remarks;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    public String getBookId() {
        return BookId;
    }

    public void setBookId(String bookId) {
        BookId = bookId;
    }

    public String getIssueedDate() {
        return issueedDate;
    }

    public void setIssueedDate(String issueedDate) {
        this.issueedDate = issueedDate;
    }

    public String getIssuedforday() {
        return Issuedforday;
    }

    public void setIssuedforday(String issuedforday) {
        Issuedforday = issuedforday;
    }

    public String getSubmissionDate() {
        return SubmissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        SubmissionDate = submissionDate;
    }

    public String getIssuedBy() {
        return IssuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        IssuedBy = issuedBy;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }
}
