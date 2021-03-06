package psych.server.data.dao.dto;

// Generated Sep 13, 2014 3:10:27 PM by Hibernate Tools 3.4.0.CR1

/**
 * ProfileToSystemRoleDTO generated by hbm2java
 */
public class ProfileToSystemRoleDTO implements java.io.Serializable {

	private String profileToSystemRoleID;
	private SystemProfileDTO systemProfileDTO;
	private SystemRoleDTO systemRoleDTO;

	public ProfileToSystemRoleDTO() {
	}

	public ProfileToSystemRoleDTO(SystemProfileDTO systemProfileDTO,
			SystemRoleDTO systemRoleDTO) {
		this.systemProfileDTO = systemProfileDTO;
		this.systemRoleDTO = systemRoleDTO;
	}

	public String getProfileToSystemRoleID() {
		return this.profileToSystemRoleID;
	}

	public void setProfileToSystemRoleID(String profileToSystemRoleID) {
		this.profileToSystemRoleID = profileToSystemRoleID;
	}

	public SystemProfileDTO getSystemProfileDTO() {
		return this.systemProfileDTO;
	}

	public void setSystemProfileDTO(SystemProfileDTO systemProfileDTO) {
		this.systemProfileDTO = systemProfileDTO;
	}

	public SystemRoleDTO getSystemRoleDTO() {
		return this.systemRoleDTO;
	}

	public void setSystemRoleDTO(SystemRoleDTO systemRoleDTO) {
		this.systemRoleDTO = systemRoleDTO;
	}

}
