package Eads.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Controller
public class FileUploadController {

    private final ApplicationEventPublisher eventPublisher;
    private final ResourceLoader resourceLoader;

    @Autowired
    public FileUploadController(ApplicationEventPublisher eventPublisher, ResourceLoader resourceLoader) {
        this.eventPublisher = eventPublisher;
        this.resourceLoader = resourceLoader;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("imageUpload") MultipartFile file, RedirectAttributes redirectAttributes,
                             @RequestParam(required = false) String hiddenName,
                             @RequestParam(required = false) String hiddenDescription,
                             @RequestParam(required = false)String hiddenCity,
                             @RequestParam(required = false) String hiddenPrice,
                             @RequestParam(required = false)List<Long> hiddenCategoryId) {
        try {
            // Define the upload directory path
            Path uploadPath = Paths.get("src/images");
            if (file.isEmpty()) {
                throw new IOException("Failed to upload empty file.");
            }
            // Ensure the directory exists
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Define the file path
            Path filePath = uploadPath.resolve(Objects.requireNonNull(file.getOriginalFilename()));

            // Save the file
            Files.copy(file.getInputStream(), filePath);

            // Prepare the image path with timestamp for the redirect
            String fileName = file.getOriginalFilename();
            long timestamp = System.currentTimeMillis();
            String imagePathWithTimestamp = "/images/" +  fileName;
            System.out.println("Upload path: " + uploadPath.toAbsolutePath());
            System.out.println("File path: " + filePath.toAbsolutePath());
            redirectAttributes.addAttribute("imagePath", imagePathWithTimestamp);
            redirectAttributes.addAttribute("imageSuccess","Image Was Successfully uploaded");
            redirectAttributes.addAttribute("description",hiddenDescription);
            redirectAttributes.addAttribute("city",hiddenCity);
            redirectAttributes.addAttribute("price",hiddenPrice);
            redirectAttributes.addAttribute("categories",hiddenCategoryId);

            redirectAttributes.addAttribute("name",hiddenName);
            return "redirect:/oglas/add";
        } catch (IOException e) {
        e.printStackTrace(); // This will print the exception details to the console
        redirectAttributes.addAttribute("imageSuccess","Image Was not Successfully uploaded");
        return "redirect:/oglas/add";
    }
    }
}
