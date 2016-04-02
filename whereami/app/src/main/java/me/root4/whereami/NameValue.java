package me.root4.whereami;

/**
 * Created by harish on 4/1/16.
 */
public class NameValue {
    private String attrName, attrValue;

    public NameValue() {
    }

    public NameValue(String attrName, String attrValue) {
        this.attrName = attrName;
        this.attrValue = attrValue;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String name) {
        this.attrName = name;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }
}