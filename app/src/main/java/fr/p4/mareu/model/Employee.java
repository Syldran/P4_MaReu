package fr.p4.mareu.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Employee implements Parcelable {
    private String mMail;

    public Employee(String mail) {
        mMail = mail;
    }

    protected Employee(Parcel in) {
        mMail = in.readString();
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    public String getMail() {
        return mMail;
    }

    public void setMail(String mail) {
        mMail = mail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mMail);
    }


}
