package hr.fer.zemris.java.hw16.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * Class used to model blog users.
 * 
 * @author MarkoB
 * @version 1.0
 */
@Entity
@Table(name="blog_users")
public class BlogUser {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String nick;
	private String email;
	private String passwordHash;
	private List<BlogEntry> blogs = new ArrayList<>();
	
	/**
	 * Constructor.
	 */
	public BlogUser() {
	}
	
	/**
	 * Constructor.
	 * 
	 * @param firstName user's first name
	 * @param lastName user's last name
	 * @param email user's e-mail
	 * @param nickName user's nick name
	 * @param passwordHash user's password's hash
	 */
	public BlogUser(String firstName, String lastName, String email, String nickName, String passwordHash) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.nick = nickName;
		this.passwordHash = passwordHash;
	}
	
	/**
	 * Id getter.
	 * 
	 * @return the id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Id setter.
	 * 
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * First name getter.
	 * 
	 * @return the firstName
	 */
	@Column(length=150,nullable=false)
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * First name setter.
	 * 
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Last name getter.
	 * 
	 * @return the lastName
	 */
	@Column(length=200,nullable=false)
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Last name setter.
	 * 
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Nick name getter.
	 * 
	 * @return the nick
	 */
	@Column(length=200,nullable=false,unique=true)
	public String getNick() {
		return nick;
	}
	
	/**
	 * Nick name setter.
	 * 
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * E-mail getter.
	 * 
	 * @return the email
	 */
	@Column(length=200,nullable=false)
	public String getEmail() {
		return email;
	}
	
	/**
	 * E-mail setter.
	 * 
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Password hash getter.
	 * 
	 * @return the passwordHash
	 */
	@Column(length=40,nullable=false)
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * Password hash setter.
	 * 
	 * @param passwordHash the passwordHash to set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	/**
	 * Blogs getter.
	 * 
	 * @return the blogs
	 */
	@OneToMany(mappedBy="creator", fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	@OrderBy("createdAt")
	public List<BlogEntry> getBlogs() {
		return blogs;
	}

	/**
	 * Blogs setter.
	 * 
	 * @param blogs the blogs to set
	 */
	public void setBlogs(List<BlogEntry> blogs) {
		this.blogs = new ArrayList<>(blogs);
	}

	/**
	 * Method used to encode an array of bytes as a hex String.
	 * 
	 * @param bytes array of bytes to be encoded
	 * @return returns the encoded String
	 */
	private static String hexEncode(byte[] bytes) {
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i<bytes.length; i++) {
			if(((int) bytes[i] & 0xff) < 0x10) {
				buffer.append("0");
			}
			buffer.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buffer.toString();
	}
	
	/**
	 * Method used to calculate SHA-1 digest of a String.
	 * 
	 * @param string string whose digest should be calculated
	 * @return returns the String representation of a hex-encoded digest.
	 */
	public static String calcHash(String string) {
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Unsupported digest algorithm!");
		}
		if(sha != null) {
			try {
				return hexEncode(sha.digest(string.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				System.out.println("Unsupported encoding!");
			}
		}
		return null;
	}
}
