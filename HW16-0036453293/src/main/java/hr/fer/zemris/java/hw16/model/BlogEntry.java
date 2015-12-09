package hr.fer.zemris.java.hw16.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class representing blog entries.
 * 
 * @author MarkoB
 * @version 1.0
 */
@Entity
@Table(name="blog_entries")
@NamedQueries({
	@NamedQuery(name="BlogEntry.upit1",query="select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when")
})
@Cacheable(true)
public class BlogEntry {

	private Long id;
	private List<BlogComment> comments = new ArrayList<>();
	private Date createdAt;
	private Date lastModifiedAt;
	private String title;
	private String text;
	private BlogUser creator;
	
	/**
	 * Constructor.
	 */
	public BlogEntry() {
		
	}
	
	/**
	 * Constructor.
	 * 
	 * @param title entry title
	 * @param text entry text
	 * @param creator entry's creator
	 */
	public BlogEntry(String title, String text, BlogUser creator) {
		this.title = title;
		this.text = text;
		this.creator = creator;
		this.createdAt = new Date();
		this.lastModifiedAt = this.createdAt;
	}
	
	/**
	 * Id getter.
	 * 
	 * @return returns entry id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Id setter.
	 * 
	 * @param id entry id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Comments getter.
	 * 
	 * @return returns the list of comments on the current blog
	 */
	@OneToMany(mappedBy="blogEntry", fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.REMOVE}, 
			orphanRemoval=true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}
	
	/**
	 * Comments setter.
	 * 
	 * @param comments sets the comments on the current blog
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}
	
	/**
	 * Created at getter.
	 * 
	 * @return returns the date the entry was created at
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getCreatedAt() {
		return createdAt;
	}
	
	/**
	 * Created at setter.
	 * 
	 * @param createdAt date the entry was created at
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	/**
	 * Last modified at getter.
	 * 
	 * @return returns the date the entry was last modified at
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}
	
	/**
	 * Last modified at setter.
	 * 
	 * @param lastModifiedAt the date the entry was last modified at
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}
	
	/**
	 * Title getter.
	 * 
	 * @return returns the entry title
	 */
	@Column(length=200,nullable=false)
	public String getTitle() {
		return title;
	}
	
	/**
	 * Title setter.
	 * 
	 * @param title entry title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Text getter.
	 * 
	 * @return returns entry text
	 */
	@Column(length=4096,nullable=false)
	public String getText() {
		return text;
	}
	
	/**
	 * Text setter.
	 * 
	 * @param text entry text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Creator getter.
	 * 
	 * @return the creator
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable=false)
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * Creator setter.
	 * 
	 * @param creator the creator to set
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
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
		BlogEntry other = (BlogEntry) obj;
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