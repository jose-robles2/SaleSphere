package org.example.marketplace.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.text.DecimalFormat;
import java.util.Objects;

@Entity
public class State {
    private String stateName;
    private int firearmsAge;
    private boolean firearmsAllowed;
    private int tobaccoAge;
    private boolean tobaccoAllowed;
    private int drugAge;
    private boolean drugAllowed;
    private int technologyAge;
    private boolean technologyAllowed;
    private int medicineAge;
    private boolean medicineAllowed;
    private int alcoholAge;
    private boolean alcoholAllowed;
    private double taxRate;
    @Id
    @GeneratedValue
    private Long id;

    public State(){};

    public State(String stateName, int firearmsAge, boolean firearmsAllowed,
                 int tobaccoAge, boolean tobaccoAllowed, int drugAge, boolean drugAllowed,
                 int technologyAge, boolean technologyAllowed, int medicineAge, boolean medicineAllowed,
                 int alcoholAge, boolean alcoholAllowed, double taxRate) {
        this.stateName = stateName;
        this.firearmsAge = firearmsAge;
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
        this.alcoholAge = alcoholAge;
        this.alcoholAllowed = alcoholAllowed;
    }

    public int getFirearmsAge() {
        return firearmsAge;
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

    public int getDrugsAge() {
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

    public String getStateName() { return stateName; }

    public int getAlcoholAge() { return alcoholAge;}

    public boolean isAlcoholAllowed() { return alcoholAllowed; }

    public double getTaxRate() { return taxRate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State state)) return false;
        return getFirearmsAge() == state.getFirearmsAge() && isFirearmsAllowed() == state.isFirearmsAllowed() && getTobaccoAge() == state.getTobaccoAge() && isTobaccoAllowed() == state.isTobaccoAllowed() && drugAge == state.drugAge && isDrugAllowed() == state.isDrugAllowed() && getTechnologyAge() == state.getTechnologyAge() && isTechnologyAllowed() == state.isTechnologyAllowed() && getMedicineAge() == state.getMedicineAge() && isMedicineAllowed() == state.isMedicineAllowed() && getAlcoholAge() == state.getAlcoholAge() && isAlcoholAllowed() == state.isAlcoholAllowed() && Double.compare(getTaxRate(), state.getTaxRate()) == 0 && Objects.equals(getStateName(), state.getStateName()) && Objects.equals(id, state.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStateName(), getFirearmsAge(), isFirearmsAllowed(), getTobaccoAge(), isTobaccoAllowed(), drugAge, isDrugAllowed(), getTechnologyAge(), isTechnologyAllowed(), getMedicineAge(), isMedicineAllowed(), getAlcoholAge(), isAlcoholAllowed(), getTaxRate(), id);
    }

    @Override
    public String toString() {
        return "State{" +
                "stateName='" + stateName + '\'' +
                ", firearmsAge=" + firearmsAge +
                ", firearmsAllowed=" + firearmsAllowed +
                ", tobaccoAge=" + tobaccoAge +
                ", tobaccoAllowed=" + tobaccoAllowed +
                ", drugAge=" + drugAge +
                ", drugAllowed=" + drugAllowed +
                ", technologyAge=" + technologyAge +
                ", technologyAllowed=" + technologyAllowed +
                ", medicineAge=" + medicineAge +
                ", medicineAllowed=" + medicineAllowed +
                ", alcoholAge=" + alcoholAge +
                ", alcoholAllowed=" + alcoholAllowed +
                ", taxRate=" + taxRate +
                ", id=" + id +
                '}';
    }
}

