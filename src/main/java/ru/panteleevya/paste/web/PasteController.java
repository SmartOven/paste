package ru.panteleevya.paste.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.panteleevya.paste.paste.Paste;
import ru.panteleevya.paste.paste.PasteDto;
import ru.panteleevya.paste.paste.PasteSerializable;
import ru.panteleevya.paste.storage.ObjectStorageService;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class PasteController {
    private final ObjectStorageService objectStorageService;

    public PasteController(@Autowired ObjectStorageService objectStorageService) {
        this.objectStorageService = objectStorageService;
    }

    @GetMapping("/paste/get")
    public ResponseEntity<?> getPaste(@RequestParam String pasteId) {
        PasteSerializable pasteSerializable = (PasteSerializable) objectStorageService.fetchObject(pasteId);
        if (pasteSerializable == null) {
            return ResponseEntity.notFound().build();
        }
        Paste paste = new Paste(pasteSerializable.value());
        return ResponseEntity.ok(paste);
    }

    @PostMapping("/paste/create")
    public ResponseEntity<?> createPaste(@RequestBody PasteDto pasteDto) {
        Paste paste = new Paste(pasteDto.value());
        PasteSerializable pasteSerializable = new PasteSerializable(paste.value());
        String objectKey = UUID.randomUUID().toString();
        objectStorageService.compressAndSaveObject(objectKey, pasteSerializable);
        return ResponseEntity.ok(objectKey);
    }

    @DeleteMapping("/paste/delete")
    public ResponseEntity<?> deletePaste(@RequestParam String pasteId) {
        objectStorageService.deleteObject(pasteId);
        return ResponseEntity.noContent().build();
    }
}
