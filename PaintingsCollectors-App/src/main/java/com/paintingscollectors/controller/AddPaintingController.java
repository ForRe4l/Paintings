package com.paintingscollectors.controller;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.PaintingDTO;
import com.paintingscollectors.model.dto.addPaintingDTO;
import com.paintingscollectors.repository.PaintingRepository;
import com.paintingscollectors.repository.UserRepository;
import com.paintingscollectors.service.AddPaintingService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
@Controller
public class AddPaintingController {


    private final AddPaintingService addPaintingService;
    private final UserRepository userRepository;
    private ModelMapper modelMapper ;
    private final PaintingRepository paintingRepository;
    private UserSession userSession;


    public AddPaintingController(PaintingRepository paintingRepository,
                                 ModelMapper modelMapper,
                                 UserSession userSession,
                                 AddPaintingService addPaintingService,
                                 UserRepository userRepository) {

        this.paintingRepository = paintingRepository;
        this.modelMapper = modelMapper;
        this.userSession = userSession;
        this.addPaintingService = addPaintingService;
        this.userRepository = userRepository;
    }

    @ModelAttribute("paintingData")
    public addPaintingDTO createAddPaintingDTO(addPaintingDTO a) {
        return new addPaintingDTO();
    }

    @GetMapping("/add-painting")
    public String addPainting() {
        if(!userSession.userIsLogged()){
            return "redirect:/";
        }

        return "add-painting";
    }

    @PostMapping("/add-painting")
    public String createPainting(@Valid addPaintingDTO addPaintingDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("paintingData", addPaintingDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.paintingData", bindingResult);

            return "redirect:/add-painting";
        }

        boolean success = addPaintingService.addPainting(addPaintingDTO);
        if(!success){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.paintingData", bindingResult);
            return "redirect:/add-painting";
        }


        return "redirect:/home";
    }
}
