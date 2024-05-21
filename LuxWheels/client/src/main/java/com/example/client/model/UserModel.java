package com.example.client.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * The UserModel class represents a user in the system.
 * It includes user details such as ID, name, surname, birthdate, license number, email, and password.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

	/** The unique identifier of the user. */
	private Integer id;

	/** The first name of the user. */
	private String name;

	/** The last name of the user. */
	private String surname;

	/** The birthdate of the user. */
	private String birthdate;

	/** The driver's license number of the user. */
	private String licensenumber;

	/** The email address of the user. */
	private String email;

	/** The password of the user. */
	private String password;

	/**
	 * Gets the user's ID.
	 *
	 * @return the ID of the user.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the user's ID.
	 *
	 * @param id the ID to set.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the user's name.
	 *
	 * @return the name of the user.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the user's name.
	 *
	 * @param name the name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the user's surname.
	 *
	 * @return the surname of the user.
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Sets the user's surname.
	 *
	 * @param surname the surname to set.
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Gets the user's birthdate.
	 *
	 * @return the birthdate of the user.
	 */
	public String getBirthdate() {
		return birthdate;
	}

	/**
	 * Sets the user's birthdate.
	 *
	 * @param birthdate the birthdate to set.
	 */
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	/**
	 * Gets the user's license number.
	 *
	 * @return the license number of the user.
	 */
	public String getLicensenumber() {
		return licensenumber;
	}

	/**
	 * Sets the user's license number.
	 *
	 * @param licensenumber the license number to set.
	 */
	public void setLicensenumber(String licensenumber) {
		this.licensenumber = licensenumber;
	}

	/**
	 * Gets the user's email address.
	 *
	 * @return the email address of the user.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the user's email address.
	 *
	 * @param email the email address to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the user's password.
	 *
	 * @return the password of the user.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the user's password.
	 *
	 * @param password the password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}