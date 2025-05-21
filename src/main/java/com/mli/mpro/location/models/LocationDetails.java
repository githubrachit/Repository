package com.mli.mpro.location.models;

import java.util.List;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "location")
public class LocationDetails {
    @Id
    private String id;
    @Sensitive(MaskType.ADDRESS)
    private String name;
    @Indexed(direction = IndexDirection.ASCENDING)
    private String type;
    @Sensitive(MaskType.NAME)
    private String shortName;
    private String phCode;
    private Location location;
    private String code;
    private String timezone;
    @Indexed(direction = IndexDirection.ASCENDING)
    private List<String> containedIn;
    private List<String> alias;

    public LocationDetails() {

    }

    public LocationDetails(String name, String type, String shortName, String phCode, Location location, String code, String timezone, List<String> containedIn,
	    List<String> alias) {
	super();
	this.name = name;
	this.type = type;
	this.shortName = shortName;
	this.phCode = phCode;
	this.location = location;
	this.code = code;
	this.timezone = timezone;
	this.containedIn = containedIn;
	this.alias = alias;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getShortName() {
	return shortName;
    }

    public void setShortName(String shortName) {
	this.shortName = shortName;
    }

    public String getPhCode() {
	return phCode;
    }

    public void setPhCode(String phCode) {
	this.phCode = phCode;
    }

    public Location getLocation() {
	return location;
    }

    public void setLocation(Location location) {
	this.location = location;
    }

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    public String getTimezone() {
	return timezone;
    }

    public void setTimezone(String timezone) {
	this.timezone = timezone;
    }

    public List<String> getContainedIn() {
	return containedIn;
    }

    public void setContainedIn(List<String> containedIn) {
	this.containedIn = containedIn;
    }

    public List<String> getAlias() {
	return alias;
    }

    public void setAlias(List<String> alias) {
	this.alias = alias;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	LocationDetails other = (LocationDetails) obj;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "LocationDetails [id=" + id + ", name=" + name + ", type=" + type + ", shortName=" + shortName + ", phCode=" + phCode + ", location=" + location
		+ ", code=" + code + ", timezone=" + timezone + ", containedIn=" + containedIn + ", alias=" + alias + "]";
    }
}