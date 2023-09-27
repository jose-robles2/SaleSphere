package org.example.marketplace.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class State {
    public String stateName;
    private int firearmAge;
    private boolean firearmsAllowed;
    private int tobaccoAge;
    private boolean tobaccoAllowed;
    private int drugAge;
    private boolean drugAllowed;
    private int technologyAge;
    private boolean technologyAllowed;
    private int medicineAge;
    private boolean medicineAllowed;
    private int taxRate;
    @Id
    @GeneratedValue
    private Long id;

    public State(String stateName, int firearmAge, boolean firearmsAllowed,
                 int tobaccoAge, boolean tobaccoAllowed, int drugAge, boolean drugAllowed,
                 int technologyAge, boolean technologyAllowed, int medicineAge, boolean medicineAllowed,
                 int taxRate) {
        this.stateName = stateName;
        this.firearmAge = firearmAge;
        this.firearmsAllowed = firearmsAllowed;
        this.tobaccoAge = tobaccoAge;
        this.tobaccoAllowed = tobaccoAllowed;
        this.drugAge = drugAge;
        this.drugAllowed = drugAllowed;
        this.technologyAge = technologyAge;
        this.technologyAllowed = technologyAllowed;
        this.medicineAge = medicineAge;
        this.medicineAllowed = medicineAllowed;
        this.taxRate = taxRate;
    }

    public int getFirearmAge() {
        return firearmAge;
    }

    public boolean isFirearmsAllowed() {
        return firearmsAllowed;
    }

    public int getTobaccoAge() {
        return tobaccoAge;
    }

    public boolean isTobaccoAllowed() {
        return tobaccoAllowed;
    }

    public int getDrugAge() {
        return drugAge;
    }

    public boolean isDrugAllowed() {
        return drugAllowed;
    }

    public int getTechnologyAge() {
        return technologyAge;
    }

    public boolean isTechnologyAllowed() {
        return technologyAllowed;
    }

    public int getMedicineAge() {
        return medicineAge;
    }

    public boolean isMedicineAllowed() {
        return medicineAllowed;
    }

    public int taxRate() {
        return taxRate;
    }
}

