package cz.utb.fai.LibraryApp.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cz.utb.fai.LibraryApp.AppRequestMapping;
import cz.utb.fai.LibraryApp.bussines.services.ImageService;

@Controller
@RequestMapping(value = AppRequestMapping.IMAGE)
public class ImageController {

    @Autowired
    protected ImageService imageService;

    /**
     * Nacte obraze z disku a navrati ho
     * 
     * @param id - ID obrazku (jeho nazev)
     * @return Obrazek
     */
    @GetMapping(value = "", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam String id) {
        try {
            return this.imageService.readImage(id);
        } catch (IOException e) {
            return null;
        }
    }

}
