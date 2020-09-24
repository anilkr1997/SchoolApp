package com.example.schoolapp.Model;

public class ClassModel {
    String ID;
    String ClassName;
    boolean IsActive;
    boolean IsDeleted;
  public ClassModel( String ID,
          String ClassName,
                     boolean IsActive,
                     boolean IsDeleted){
    this.ID=ID;
    this.ClassName=ClassName;
    this.IsActive=IsActive;
    this.IsDeleted=IsDeleted;
  }

    public String getID() {
        return ID;
    }

    public String getClassName() {
        return ClassName;
    }

    public boolean getIsActive() {
        return IsActive;
    }

    public boolean getIsDeleted() {
        return IsDeleted;
    }


}
