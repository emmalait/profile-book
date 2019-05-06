package project.picture;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.account.Account;
import project.comment.CommentService;
import project.like.LikeService;

@Service
public class PictureService {

    @Autowired
    private PictureRepository pictureRepository;
    
    @Autowired
    private LikeService likeService;
    
    @Autowired
    private CommentService commentService;
    
    public Picture getOne(Long id) {
        return pictureRepository.getOne(id);
    }
    
    public byte[] get(String accountUrl, Long id) {
        return pictureRepository.getOne(id).getContent();
    }
    
    public ResponseEntity<byte[]> getFile(String accountUrl, Long id) {
        Picture p = pictureRepository.getOne(id);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(p.getMediaType()));
        headers.setContentLength(p.getSize());

        return new ResponseEntity<>(p.getContent(), headers, HttpStatus.CREATED);
    }
    
    public void addPicture(Account account, MultipartFile file, String description) throws IOException {
        if (account.getPictures().size() < 10) {
            Picture picture = new Picture();
            picture.setContent(file.getBytes());
            picture.setAccount(account);
            picture.setProfilePic(false);
            picture.setSize(file.getSize());
            picture.setName(file.getOriginalFilename());
            picture.setMediaType(file.getContentType());
            picture.setDescription(description);
            pictureRepository.save(picture);
        }
    }
    
    public void deletePicture(Picture picture) {
        likeService.deleteLikesFromPicture(picture);
        commentService.deleteCommentsFromPicture(picture);
        pictureRepository.delete(picture);
    }
    
    public Picture getProfilePic(Account account) {
        List<Picture> pictures =  account.getPictures();
        for (Picture picture : pictures) {
            if (picture.isProfilePic()) {
                return picture;
            }
        }
        return null;
    }

    public boolean setAsProfilePic(Picture picture) {
        Picture currentProfilePic = getProfilePic(picture.getAccount());
        
        if (currentProfilePic != null) {
            currentProfilePic.setProfilePic(false);
            pictureRepository.save(currentProfilePic);
        }
        
        picture.setProfilePic(true);
        pictureRepository.save(picture);
        return true;
    }
    
    public boolean hasProfilePic(Account account) {
        List<Picture> pictures =  account.getPictures();
        for (Picture picture : pictures) {
            if (picture.isProfilePic()) {
                return true;
            }
        }
        return false;
    }

}
