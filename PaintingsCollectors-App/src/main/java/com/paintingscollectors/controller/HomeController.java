package com.paintingscollectors.controller;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.MostRatedPaintingsDTO;
import com.paintingscollectors.model.dto.PaintingDTO;
import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.StyleName;
import com.paintingscollectors.repository.PaintingRepository;
import com.paintingscollectors.repository.UserRepository;
import com.paintingscollectors.service.AddPaintingService;
import com.paintingscollectors.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final UserSession userSession;
    private final PaintingRepository paintingRepository;
    private final AddPaintingService addPaintingService;
    private final UserService userService;
    private final UserRepository userRepository;

    public HomeController(UserSession userSession, PaintingRepository paintingRepository, AddPaintingService addPaintingService, UserService userService, UserRepository userRepository) {
        this.userSession = userSession;
        this.paintingRepository = paintingRepository;
        this.addPaintingService = addPaintingService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String notLoggedUser() {
        if(userSession.userIsLogged()){
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    @Transactional
    public String LoggedUser(Model model) {
        if(!userSession.userIsLogged()){
            return "redirect:/";
        }

        List<PaintingDTO> allUserPaintings = addPaintingService
                .findAllByOwner()
                .stream()
                .map(a -> new PaintingDTO(a.getId(),a.getOwner(),a.getName(),a.getAuthor(),a.getImageUrl(),a.getStyleName())).toList();

        List<PaintingDTO> favoritePaintings = userService
                .findFavoritePaintings(userSession.getId())
                .stream()
                .map(a -> new PaintingDTO(a.getId(),a.getOwner(),a.getName(),a.getAuthor(),a.getImageUrl(),a.getStyleName())).toList();

        Set<Long> favoritePaintingIds = favoritePaintings.stream()
                .map(PaintingDTO::getId)
                .collect(Collectors.toSet());

        List<PaintingDTO> allPaintingsExceptUsers = paintingRepository.findAll().stream()
                .filter(p -> p.getOwner().getId() != (userSession.getId())) // Exclude user's own paintings
                .filter(p -> !favoritePaintingIds.contains(p.getId())) // Exclude user's favorites
                .map(painting -> new PaintingDTO(
                        painting.getId(),
                        painting.getOwner(),
                        painting.getName(),
                        painting.getAuthor(),
                        painting.getImageUrl(),
                        painting.getStyleName()
                ))
                .toList();

        List<MostRatedPaintingsDTO> mostRatedPaintings = addPaintingService
                .findMostRated()
                .stream()
                .sorted((p1, p2) -> Integer.compare(p2.getVotes(), p1.getVotes())) // Sort by votes descending
                .limit(3)
                .map(a -> new MostRatedPaintingsDTO(a.getId(),a.getName(),a.getAuthor(),a.getVotes())).toList();




        model.addAttribute("YourPaintings", allUserPaintings);
        model.addAttribute("FavPaintings", favoritePaintings);
        model.addAttribute("AllPaintings", allPaintingsExceptUsers);
        model.addAttribute("MostRatedPaintings", mostRatedPaintings);



        return "home";
    }

    @PostMapping("/addFavorite")
    public String favoritePainting(@RequestParam("paintingId") Long paintingId) {
        if(!userSession.userIsLogged()){
            return "redirect:/";
        }

        addPaintingService.addToFavorites(userSession.getId(),paintingId);

        return "redirect:/home";
     }

    @PostMapping("/removeFavorite")
    public String removeFavorite( @RequestParam Long paintingId) {
        if(!userSession.userIsLogged()){
            return "redirect:/";
        }
        userService.removeFromFavorites(userSession.getId(), paintingId);
        return "redirect:/home"; // Redirect to the paintings page or another appropriate page
    }

    @PostMapping("/removeUserPainting")
    public String removeUserPaintings(@RequestParam Long paintingId) {
        if(!userSession.userIsLogged()){
            return "redirect:/";
        }

        userService.deletePainting(paintingId);
        return "redirect:/home"; // Redirect to the paintings page or another appropriate page
    }

    @PostMapping("/vote")
    public String vote(@RequestParam Long paintingId) {
        if(!userSession.userIsLogged()){
            return "redirect:/";
        }
        userSession.hasVoted(paintingId);

        return "redirect:/home";
    }
}
