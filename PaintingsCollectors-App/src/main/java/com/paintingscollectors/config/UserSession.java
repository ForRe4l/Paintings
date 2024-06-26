package com.paintingscollectors.config;

import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.User;
import com.paintingscollectors.repository.PaintingRepository;
import com.paintingscollectors.repository.UserRepository;
import com.paintingscollectors.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@SessionScope
public class UserSession {

    private final PaintingRepository paintingRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private long id;
    private String username;

    public UserSession(UserRepository userRepository, UserService userService,PaintingRepository paintingRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.paintingRepository = paintingRepository;
    }

    public void LoggedUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }

    public boolean userIsLogged() {return id != 0;}

    public void logout(){
        id = 0;
        username = "";
    }

    public long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }

//    public boolean hasVoted(long paintingId){
//        Optional<User> userOpt = userRepository.findById(getId());
//        if(userOpt.isEmpty()){
//            return false;
//        }
//
//        Optional<Painting> paintingOpt = paintingRepository.findById(paintingId);
//        if(userOpt.get().getRatedPaintings().contains(paintingId)){
//            return true;//TODO:
//        }
//
//        return false;
//    }

    @Transactional
    public boolean hasVoted(Long paintingId) {
        Optional<User> userOpt = userRepository.findById(getId());
        Optional<Painting> paintingOpt = paintingRepository.findById(paintingId);

        if (userOpt.isPresent() && paintingOpt.isPresent()) {
            User user = userOpt.get();
            Painting painting = paintingOpt.get();

            if (user.getRatedPaintings().contains(painting)) {
                return false; // User has already voted for this painting
            }

            user.getRatedPaintings().add(painting);
            painting.setVotes(painting.getVotes() + 1);

            userRepository.save(user);
            paintingRepository.save(painting);

            return true; // Vote successful
        }

        return false; // User or painting not found
    }
}
