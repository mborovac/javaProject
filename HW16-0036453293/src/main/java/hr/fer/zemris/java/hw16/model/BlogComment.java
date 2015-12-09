package hr.fer.zemris.java.hw16.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class used to model blog comments.
 * 
 * @author MarkoB
 * @version 1.0
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {
	
	private Long id;
	private BlogEntry blogEntry;
	private String usersEMail;
	private String message;
	private Date postedOn;
	
	/**
	 * Constructor.
	 */
	public BlogComment() {
		
	}
	
	/**
	 * Constructor.
	 * 
	 * @param email author's e-mail
	 * @param message comment's message
	 * @param blogEntry blog entry to which the comment is attached
	 */
	public BlogComment(String email, String message, BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
		this.usersEMail = email;
		this.message = message;
		this.postedOn = new Date();
	}
	
	/**
	 * Id getter.
	 * 
	 * @return returns comment ID
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Id setter.
	 * 
	 * @param id comment id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Blog entry getter.
	 * 
	 * @return returns the blog entry
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * Blog entry setter.
	 * 
	 * @param blogEntry the blog entry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}
	
	/**
	 * User's e-mail getter.
	 * 
	 * @return returns the user's e-mail
	 */
	@Column(length=100,nullable=false)
	public String getUsersEMail() {
		return usersEMail;
	}
	
	/**
	 * User's e-mail setter.
	 * 
	 * @param usersEMail user's e-mail
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}
	
	/**
	 * Message getter.
	 * 
	 * @return returns the comment message
	 */
	@Column(length=4096,nullable=false)
	public String getMessage() {
		return message;
	}
	
	/**
	 * Message setter.
	 * 
	 * @param message the comment message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Posted on getter.
	 * 
	 * @return returns the date the comment was posted on
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}
	
	/**
	 * Posted on setter.
	 * 
	 * @param postedOn the date the comment was posted on
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
}