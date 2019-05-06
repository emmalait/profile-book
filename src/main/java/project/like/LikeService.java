package project.like;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.account.Account;
import project.picture.Picture;
import project.post.Post;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    public void addLikeToPost(Post post, Account liker) {
        List<Like> likes = post.getLikes();
        boolean liked = false;

        for (int i = 0; i < likes.size(); i++) {
            if (likes.get(i).getLiker() == liker) {
                liked = true;
                break;
            }
        }

        if (!liked) {
            Like l = new Like();
            l.setLiker(liker);
            l.setPost(post);
            likeRepository.save(l);
        }
    }
    
    public void addLikeToPicture(Picture picture, Account liker) {
        List<Like> likes = picture.getLikes();
        boolean liked = false;

        for (int i = 0; i < likes.size(); i++) {
            if (likes.get(i).getLiker() == liker) {
                liked = true;
                break;
            }
        }

        if (!liked) {
            Like l = new Like();
            l.setLiker(liker);
            l.setPicture(picture);
            likeRepository.save(l);
        }
    }
    
    public void deleteLikesFromPicture(Picture picture) {
        List<Like> likes = likeRepository.findByPicture(picture);
        
        for (Like like : likes) {
            likeRepository.delete(like);
        }
    }

}
