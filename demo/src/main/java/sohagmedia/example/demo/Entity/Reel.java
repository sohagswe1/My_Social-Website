package sohagmedia.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String videoUrl;

    private String thumbnailUrl;

    @Column(columnDefinition = "TEXT")
    private String caption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long viewCount = 0L;

    private Long likeCount = 0L;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.viewCount == null) {
            this.viewCount = 0L;
        }
        if (this.likeCount == null) {
            this.likeCount = 0L;
        }
    }
}