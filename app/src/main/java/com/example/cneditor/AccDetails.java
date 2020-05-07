package com.example.cneditor;

public class AccDetails
{
    public AccDetails(String email, String password, String department, String college , long key) {
        this.email = email;
        this.password = password;
        this.department = department;
        this.college = college;
        this.key = key;
    }

    public AccDetails() {
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDepartment() {
        return department;
    }

    public String getCollege() {
        return college;
    }

    String email;
    String password;
    String department;
    String college;
    long key;

    public long getKey() {
        return key;
    }
    public void setKey(long key){this.key = key;}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
