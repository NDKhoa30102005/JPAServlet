package khoa.vn.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fullname", columnDefinition = "NVARCHAR(255)")
    private String fullname;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "images", columnDefinition = "NVARCHAR(MAX)")
    private String images;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private User user;

    public Profile() {}

    public Profile(String fullname, String phone, String images, User user) {
        this.fullname = fullname;
        this.phone = phone;
        this.images = images;
        this.user = user;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
