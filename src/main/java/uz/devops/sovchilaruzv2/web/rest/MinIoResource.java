package uz.devops.sovchilaruzv2.web.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.devops.sovchilaruzv2.service.MinIoService;

@Slf4j
@RestController
@RequestMapping("/api/min-io/")
public class MinIoResource {

    private final MinIoService minIoService;

    public MinIoResource(MinIoService minIoService) {
        this.minIoService = minIoService;
    }

    @PostMapping("/upload/photo/{userId}/{fileType}")
    public ResponseEntity<String> uploadPhoto(@PathVariable String userId, @PathVariable String fileType) {
        String preSignedUrl = minIoService.generatePreSignedUrl(fileType, userId);
        log.info("A pre signed url for the photo has been created");
        return ResponseEntity.ok(preSignedUrl);
    }

    @GetMapping("/download/{userId}/{fileId}")
    public ResponseEntity<String> downloadAttachment(@PathVariable String fileId, @PathVariable String userId) {
        String preSigneUrl = minIoService.downloadAttachment(fileId, userId);
        return ResponseEntity.ok(preSigneUrl);
    }

    @DeleteMapping("/delete/{userId}/{fileId}")
    public ResponseEntity<String> deleteAttachment(@PathVariable String fileId, @PathVariable String userId) {
        String preSigneUrl = minIoService.deleteAttachment(fileId, userId);
        return ResponseEntity.ok(preSigneUrl);
    }
}
