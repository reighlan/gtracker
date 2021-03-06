package psych.server.data.dao.dto;

// Generated Sep 13, 2014 3:10:27 PM by Hibernate Tools 3.4.0.CR1

/**
 * PropertyDTO generated by hbm2java
 */
public class PropertyDTO implements java.io.Serializable {

	private PropertyPK propertyPK;
	private String propertyValue;

	public PropertyDTO() {
	}

	public PropertyDTO(PropertyPK propertyPK) {
		this.propertyPK = propertyPK;
	}

	public PropertyDTO(PropertyPK propertyPK, String propertyValue) {
		this.propertyPK = propertyPK;
		this.propertyValue = propertyValue;
	}

	public PropertyPK getPropertyPK() {
		return this.propertyPK;
	}

	public void setPropertyPK(PropertyPK propertyPK) {
		this.propertyPK = propertyPK;
	}

	public String getPropertyValue() {
		return this.propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

}
