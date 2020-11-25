package br.com.frame.controller;

import br.com.frame.dto.FrameDTO;
import br.com.frame.entity.Frame;
import br.com.frame.service.FrameService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agora-system")
public class FrameController {
    @Autowired
    FrameService service;

    @ApiOperation(value = "Responsible to save the frame grabbed")
    @PostMapping("/frame")
    public ResponseEntity<Frame> save(FrameDTO frameDTO) throws Exception {
        Frame frame = service.save(frameDTO);
        if (frame != null) {
            return new ResponseEntity<>(frame, HttpStatus.CREATED);
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @ApiOperation(value = "Retrieve All frame saved into the dataBase")
    @GetMapping("/frame")
    public ResponseEntity<List<Frame>> findAll() throws Exception {
        List<Frame> frameList = service.retrieveAll();
        if (!frameList.isEmpty()) {
            return new ResponseEntity<>(frameList, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Responsible to delete a specific frame into the dataBase")
    @DeleteMapping("/frame")
    public ResponseEntity delete(@PathVariable(value = "id") Long id) throws Exception {
        boolean deleted = service.delete(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Responsible to update a specific frame into the database")
    @PutMapping("/frame")
    public ResponseEntity<Frame> update(@PathVariable(value = "id") Long id, @RequestBody FrameDTO frameDTO) throws Exception {
        boolean upToDate = service.update(id, frameDTO);
        if (upToDate) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}