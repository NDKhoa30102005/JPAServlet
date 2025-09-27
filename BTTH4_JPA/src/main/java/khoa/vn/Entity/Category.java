package khoa.vn.Entity;

import java.io.Serializable;



import jakarta.persistence.*;


@Entity
@Table(name = "category")
@NamedQueries({
    @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c"),
    @NamedQuery(name = "Category.findByUser", query = "SELECT c FROM Category c WHERE c.user.id = :userId")
})

public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "categoryId")
	private int categoryId;

	@Column(name = "categoryCode", columnDefinition = "NVARCHAR(255)")
	private String categoryCode;

	@Column(name = "categoryName", columnDefinition = "NVARCHAR(255)")
	private String categoryName;

	@Column(name = "images", columnDefinition = "NVARCHAR(MAX)")
	private String images;


	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public Category() {
		super();
	}

	public Category(int categoryId, String categoryCode, String categoryName, String images, boolean status) {
		super();
		this.categoryId = categoryId;
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
		this.images = images;

	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

}
