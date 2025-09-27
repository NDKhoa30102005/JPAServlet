package khoa.vn.Entity;

import jakarta.persistence.*;


@Entity
@Table(name = "users") 
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "roleid")
    private int roleid;  
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    public Profile getProfile() { return profile; }
    public void setProfile(Profile profile) { this.profile = profile; }


    public User() {}

    public User(String username, String password, int roleid) {
        this.username = username;
        this.password = password;
        this.roleid = roleid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", roleid=" + roleid +
                '}';
    }
}
