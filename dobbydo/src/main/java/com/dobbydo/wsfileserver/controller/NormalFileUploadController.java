package com.dobbydo.wsfileserver.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("cubemap")
public class NormalFileUploadController {
    
    @PostMapping("normalfileupload")
    public ResponseEntity<Void> InsertNormalFileUpload(@RequestParam("file[]") MultipartFile []file,
                                   RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
    	System.out.println("Normal File Upload");
    	for(int i = 0; i< file.length; i++) {
    		String sourceFileName = file[i].getOriginalFilename(); 
        	System.out.println(sourceFileName);
        	
        	File destinationFile; 
        	String destinationFileName; 
        	do { 
        		destinationFileName = sourceFileName; 
        		destinationFile = new File("D:/upload/" + destinationFileName); 
        		} 
        	while (destinationFile.exists()); 
        	destinationFile.getParentFile().mkdirs(); 
        	file[i].transferTo(destinationFile);
    	}
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    /*
    private final StorageService storageService;
    
    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService
                .loadAll()
                .map(path ->
                        MvcUriComponentsBuilder
                                .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
    */
}
