package com.paintingscollectors.init;

import com.paintingscollectors.model.entity.Style;
import com.paintingscollectors.model.entity.StyleName;
import com.paintingscollectors.repository.StyleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Component
public class InitStyles implements CommandLineRunner {
    private StyleRepository styleRepository;

    private final Map<StyleName,String> styleDescription = Map.of(
            StyleName.IMPRESSIONISM,("Impressionism is a painting style most commonly associated with the 19th century where small brush strokes are used to build up a larger picture."),
            StyleName.ABSTRACT,("Abstract art does not attempt to represent recognizable subjects in a realistic manner."),
            StyleName.EXPRESSIONISM,("Expressionism is a style of art that doesn't concern itself with realism."),
            StyleName.SURREALISM,("Surrealism is characterized by dreamlike, fantastical imagery that often defies logical explanation."),
            StyleName.REALISM,("Also known as naturalism, this style of art is considered as 'real art' and has been the dominant style of painting since the Renaissance.")

    );

    public InitStyles(StyleRepository styleRepository) {
        this.styleRepository = styleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        long count = styleRepository.count();

        if(count == 0){
            for (StyleName languageName : styleDescription.keySet()) {
                Style style = new Style(languageName,styleDescription.get(languageName));

                styleRepository.save(style);
            }
        }
    }
}
