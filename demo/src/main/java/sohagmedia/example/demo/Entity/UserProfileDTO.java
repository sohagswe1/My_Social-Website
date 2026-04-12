package sohagmedia.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Frontend এ User profile দেখানোর জন্য DTO
 * (Sensitive information যেমন password লুকানো থাকে)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
    private Long id;
    private String name;
    private String email;
    private String profilePicture;
    private String bio;
    

    private Long followersCount;
    private Long followingCount;
    private Long postsCount;
    
    /**
     * User entity থেকে DTO তে convert করুন
     */
    public static UserProfileDTO fromUser(User user) {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setProfilePicture(user.getProfilePicture());
        dto.setBio(user.getBio());
        dto.setFollowersCount(user.getFollowersCount());
        dto.setFollowingCount(user.getFollowingCount());
        dto.setPostsCount(user.getPostsCount());
        return dto;
    }
}

