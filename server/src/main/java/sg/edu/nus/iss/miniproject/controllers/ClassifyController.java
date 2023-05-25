package sg.edu.nus.iss.miniproject.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.nus.iss.miniproject.models.Classify;
import sg.edu.nus.iss.miniproject.services.FoodService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ClassifyController {

    @Autowired
    private FoodService foodSvc;

    @PostMapping("/upload")
    public ResponseEntity<Classify> uploadFile(@RequestParam("file") MultipartFile image) {
        try {
            String filename = image.getOriginalFilename();
            byte[] bytes = image.getBytes();
            Classify classify = foodSvc.postImageClassify(bytes, filename);
            return new ResponseEntity<>(classify, HttpStatus.OK);
            
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
