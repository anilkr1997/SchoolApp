package com.example.schoolapp.Model;

public class SubmitFee {
    String ID,PaymentModeId,FeeTypeId,SubmissionDate,MonthId,Amount,AdmissionNo,CreatedOn,CreatedBy,UpdatedOn;

    public SubmitFee(String ID, String PaymentModeId, String FeeTypeId, String SubmissionDate, String MonthId,
                     String Amount, String AdmissionNo, String CreatedOn, String CreatedBy, String UpdatedOn
    ){
     this.ID=ID;
     this.PaymentModeId=PaymentModeId;
     this.FeeTypeId=FeeTypeId;
     this.SubmissionDate=SubmissionDate;
     this.MonthId=MonthId;
     this.Amount=Amount;
     this.AdmissionNo=AdmissionNo;
     this.CreatedOn=CreatedOn;
     this.CreatedBy=CreatedBy;
     this.UpdatedOn=UpdatedOn;
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

    public String getMonthId() {
        return MonthId;
    }

    public String getAmount() {
        return Amount;
    }

    public String getAdmissionNo() {
        return AdmissionNo;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public String getUpdatedOn() {
        return UpdatedOn;
    }
}
