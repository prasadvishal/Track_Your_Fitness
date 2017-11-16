package com.mindfiresolutions.trackyourfittness.Model;

/**
 * Created by Vishal Prasad on 5/24/2017.
 * * modified on 5/24/2017
 */

public class HealthStatus {
    //private variables
    private Double mWeight;
    private Double mWaterIntake;
    private Double mHighBP;
    private Double mLowBP;
    private Double mDistance;
    private int mDateID;

    public int getmDateID() {
        return mDateID;
    }

    public void setmDateID(int mDateID) {
        this.mDateID = mDateID;
    }

    // constructor
    public HealthStatus(Double weight, Double waterIntake, Double highBp, Double lowBp, Double distance){
        this.mWeight=weight;
        this.mWaterIntake=waterIntake;
        this.mHighBP=highBp;
        this.mLowBP=lowBp;
        this.mDistance=distance;
    }
    // Empty constructor
    public HealthStatus(){

    }

    public Double getmWeight() {
        return mWeight;
    }

    public void setmWeight(Double mWeight) {
        this.mWeight = mWeight;
    }



    public Double getmWaterIntake() {
        return mWaterIntake;
    }

    public void setmWaterIntake(Double mWaterIntake) {
        this.mWaterIntake = mWaterIntake;
    }



    public Double getmHighBP() {
        return mHighBP;
    }

    public void setmHighBP(Double mHighBP) {
        this.mHighBP = mHighBP;
    }

    public Double getmLowBP() {
        return mLowBP;
    }

    public void setmLowBP(Double mLowBP) {
        this.mLowBP = mLowBP;
    }


    public Double getmDistance() {
        return mDistance;
    }

    public void setmDistance(Double mDistance) {
        this.mDistance = mDistance;
    }

}