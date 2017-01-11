package com.book.Controller;

import com.book.Storage.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by vaibhav.rana on 1/11/17.
 */
@CrossOrigin(origins = {"*","http://localhost:8080"})
@Controller
public class ServiceController {
    private final StorageService storageService;

    public ServiceController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping(value="/files/{filename:.+}",produces = MediaType.ALL_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @GetMapping(value="/files",produces = APPLICATION_JSON_VALUE)
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService
                .loadAll()
                .map(path ->
                        MvcUriComponentsBuilder
                                .fromMethodName(BookController.class, "showFile", path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList()));

        return "upload";
    }

}
