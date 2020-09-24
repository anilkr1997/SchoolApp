package com.example.schoolapp.Transport.modelclass;

public class VechiltypeModelclass {
   private String ID;
   private String VehicalTypename;
  private   boolean IsActive;
   private boolean IsDeleted;

    public VechiltypeModelclass(String ID, String vehicalTypename, boolean IsActive, boolean IsDeleted) {
        this.ID = ID;
       this.VehicalTypename = vehicalTypename;
       this. IsActive = IsActive;
       this. IsDeleted = IsDeleted;
    }

    public String getID() {
        return ID;
    }

    public String getVehicalTypename() {
        return VehicalTypename;
    }

    public boolean getIsActive() {
        return IsActive;
    }

    public boolean getIsDeleted() {
        return IsDeleted;
    }
}
