package sohagmedia.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String profilePicture;
    
    // Bio - optional
    @Column(columnDefinition = "TEXT")
    private String bio;
    
    // User Statistics
    private Long followersCount = 0L;
    private Long followingCount = 0L;
    private Long postsCount = 0L;

    // Relationships
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Like> likes;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Reel> reels;


    @OneToMany(mappedBy = "following")
    @JsonIgnore
    private List<Follow> followers;


    @OneToMany(mappedBy = "follower")
    @JsonIgnore
    private List<Follow> following;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Notification> notifications;

    @PrePersist
    public void onCreate() {
        if (this.followersCount == null) {
            this.followersCount = 0L;
        }
        if (this.followingCount == null) {
            this.followingCount = 0L;
        }
        if (this.postsCount == null) {
            this.postsCount = 0L;
        }
    }
}
