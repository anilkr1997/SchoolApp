package com.example.schoolapp.Fragments;

public class FeeCollectionModel {
    String ID;

    String name;
    String father;
    String PaymentModeId;
    String FeeTypeId;
    String SubmissionDate;
    String Month;
    String Amount;
    String SessionId;
    String SectionId;
    String ClassId;
    String AdmissionNo;
    String SubmitedBy;
    String UpdatedBy;



    public String getName() {
        return name;
    }

    public String getFather() {
        return father;
    }

    public String getID() {
        return ID;
    }

    public String getPaymentModeId() {
        return PaymentModeId;
    }

    public String getFeeTypeId() {
        return FeeTypeId;
    }

    public String getSubmissionDate() {
        return SubmissionDate;
    }

    public String getMonth() {
        return Month;
    }

    public String getAmount() {
        return Amount;
    }

    public String getSessionId() {
        return SessionId;
    }

    public String getSectionId() {
        return SectionId;
    }

    public String getClassId() {
        return ClassId;
    }

    public String getAdmissionNo() {
        return AdmissionNo;
    }

    public String getSubmitedBy() {
        return SubmitedBy;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public String getUpdatedOn() {
        return UpdatedOn;
    }

    String UpdatedOn;

    public FeeCollectionModel(String ID,
    String name,
            String father, String paymentModeId, String feeTypeId, String submissionDate, String month, String amount, String sessionId, String sectionId, String classId, String admissionNo, String submitedBy, String updatedBy, String updatedOn) {
        this.ID = ID;
        this.name = name;
        this.father = father;
        PaymentModeId = paymentModeId;
        FeeTypeId = feeTypeId;
        SubmissionDate = submissionDate;
        Month = month;
        Amount = amount;
        SessionId = sessionId;
        SectionId = sectionId;
        ClassId = classId;
        AdmissionNo = admissionNo;
        SubmitedBy = submitedBy;
        UpdatedBy = updatedBy;
        UpdatedOn = updatedOn;
    }
}
