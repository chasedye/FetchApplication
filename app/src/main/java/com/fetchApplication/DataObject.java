package com.fetchApplication;

public class DataObject {

    private String name;
    private int id;
    private int listID;

    public DataObject(String name, int id, int listID)
    {
        this.name = name;
        this.id = id;
        this.listID = listID;
    }

    public boolean isNameNull()
    {
        if(name == null || name.length() < 1) return true;
        else if (name.compareToIgnoreCase("null") == 0) return true;
        else return false;
    }

    public String getName() {return name;}

    public int getNameInt() {
        int num = Integer.parseInt(name.split(" ")[1]);
        return num;
    }

    public int getId() { return id;}

    public int getListID() {return listID;}

    public String toString() { return new String(listID + ": " + name + "\t" + id);}
}
