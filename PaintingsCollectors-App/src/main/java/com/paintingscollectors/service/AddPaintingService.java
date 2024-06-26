package com.paintingscollectors.service;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.PaintingDTO;
import com.paintingscollectors.model.dto.addPaintingDTO;
import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.Style;
import com.paintingscollectors.model.entity.StyleName;
import com.paintingscollectors.model.entity.User;
import com.paintingscollectors.repository.PaintingRepository;
import com.paintingscollectors.repository.StyleRepository;
import com.paintingscollectors.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class AddPaintingService {
    private final PaintingRepository paintingRepository;

    private final UserSession userSession;
    private final UserRepository userRepository;
    private final StyleRepository styleRepository;

    public AddPaintingService(PaintingRepository paintingRepository, UserSession userSession, UserRepository userRepository, StyleRepository styleRepository) {
        this.paintingRepository = paintingRepository;
        this.userSession = userSession;
        this.userRepository = userRepository;
        this.styleRepository = styleRepository;
    }

    public boolean addPainting(addPaintingDTO data) {

        Optional<User> byId = userRepository.findById(userSession.getId());

        if (byId.isEmpty()) {
            return false;
        }

        Optional<Style> byStyle = styleRepository.findByStyleName(data.getStyleName());

        if (byStyle.isEmpty()) {
            return false;
        }

        Painting painting = new Painting();
        painting.setName(data.getName());
        painting.setAuthor(data.getAuthor());
        painting.setImageUrl(data.getImageUrl());
        painting.setStyle(byStyle.get());
        painting.setOwner(byId.get());

        paintingRepository.save(painting);
        return true;
    }

    public List<Painting> findAllByOwner() {

        List<Painting> allUserPaintings = paintingRepository.findByOwner(userRepository.findById(userSession.getId()));

        return allUserPaintings;

    }
    @Transactional
    public void addToFavorites(long userId, long paintingId) {

      Optional<User> userOpt =  userRepository.findById(userId);

      if(userOpt.isEmpty()){
          return;
      }

      Optional<Painting> paintingOpt =  paintingRepository.getById(paintingId);

      if(paintingOpt.isEmpty()){
          return;
      }

      userOpt.get().addFavourite(paintingOpt.get());

      userRepository.save(userOpt.get());

    }

    @Transactional
    public List<Painting> findMostRated() {
            return paintingRepository.findAllOrderByVoteDesc();
    }
}
