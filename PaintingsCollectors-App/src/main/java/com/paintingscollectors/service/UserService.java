package com.paintingscollectors.service;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.UserLoginDTO;
import com.paintingscollectors.model.dto.UserRegisterDTO;
import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.User;
import com.paintingscollectors.repository.PaintingRepository;
import com.paintingscollectors.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserSession loggedUser;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSession userSession;
    private final PaintingRepository paintingRepository;


    public UserService(ModelMapper modelMapper,
                       UserRepository data,
                       PasswordEncoder passwordEncoder,
                       UserSession  loggedUser, UserSession userSession, PaintingRepository paintingRepository) {

        this.modelMapper = modelMapper;
        this.userRepository = data;
        this.passwordEncoder = passwordEncoder;
        this.loggedUser = loggedUser;
        this.userSession = userSession;
        this.paintingRepository = paintingRepository;
    }

        public boolean registerUser(UserRegisterDTO userData){

            if(!userData.getPassword().equals(userData.getConfirmPassword())) {
                return false;
            }

            boolean isUsername =
                    userRepository.existsByUsername(userData.getUsername());

            boolean isEmail =
                    userRepository.existsByEmail(userData.getEmail());


            if (isUsername || isEmail) {
                return false;
            }

            User mappedUser = modelMapper.map(userData, User.class);
            mappedUser.setPassword(passwordEncoder.encode(userData.getPassword()));

            userRepository.save(mappedUser);
            return true;

        }

    public boolean loginUser(UserLoginDTO userData){
        Optional<User> byUsername = userRepository
                .findByUsername(userData.getUsername());

        if(byUsername.isEmpty()){
            return false;
        }
        User user = byUsername.get();
        if(!passwordEncoder.matches(userData.getPassword(), user.getPassword())){
            return false;
        }

        loggedUser.LoggedUser(user);
        return true;
    }

    public void logout(){
        loggedUser.logout();
    }

    @Transactional
    public List<Painting> findFavoritePaintings(long id) {
        Optional<User> byId = userRepository.findById(id);

        if(byId.isEmpty()){
            return new ArrayList<>();
        }
        return byId.get().getFavoritePaintings();
    }


    @Transactional
    public void removeFromFavorites(long userId, long paintingId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return; // or throw an exception
        }

        Optional<Painting> paintingOpt = paintingRepository.findById(paintingId);
        if (paintingOpt.isEmpty()) {
            return; // or throw an exception
        }

        User user = userOpt.get();
        Painting painting = paintingOpt.get();

        user.removeFavourite(painting);
        userRepository.save(user);
    }

    @Transactional
    public void deletePainting(Long paintingId) {

        Optional<Painting> paintingOpt = paintingRepository.findById(paintingId);
        if (paintingOpt.isEmpty()) {
            return; // or throw an exception
        }

        for (User user: userRepository.findAll()){
            user.removeFavourite(paintingOpt.get());
            user.removeVoted(paintingOpt.get());
        }

        paintingRepository.delete(paintingOpt.get());

    }
}
