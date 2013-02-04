package  	com.test.model;

/**
 * The Class User.
 *
 * @author Sudhakar
 */
public class User {

	/** The name. */
	private String name;
	
	/** The age. */
	private int age;
	
	/** The citizenship. */
	private String citizenship;
	
	/** The has_ssid. */
	private boolean has_ssid;
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the age.
	 *
	 * @return the age
	 */
	public int getAge() {
		return age;
	}
	
	/**
	 * Sets the age.
	 *
	 * @param age the new age
	 */
	public void setAge(int age) {
		this.age = age;
	}
	
	/**
	 * Gets the citizenship.
	 *
	 * @return the citizenship
	 */
	public String getCitizenship() {
		return citizenship;
	}
	
	/**
	 * Sets the citizenship.
	 *
	 * @param citizenship the new citizenship
	 */
	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}
	
	/**
	 * Checks if is has_ssid.
	 *
	 * @return true, if is has_ssid
	 * @author  Sudhakar (Infosys)
	 */
	public boolean isHas_ssid() {
		return has_ssid;
	}
	
	/**
	 * Sets the has_ssid.
	 *
	 * @param has_ssid the new has_ssid
	 */
	public void setHas_ssid(boolean has_ssid) {
		this.has_ssid = has_ssid;
	}	
}
