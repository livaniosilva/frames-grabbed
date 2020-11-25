package br.com.frame.service.impl;

import br.com.frame.dto.FrameDTO;
import br.com.frame.entity.Frame;
import br.com.frame.repository.FrameRepository;
import br.com.frame.service.FrameService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class FrameServiceImpl implements FrameService {
    @Autowired
    FrameRepository repository;

    /**
     * Method responsible to save the frames grabbed and saved in a temporary folder to our database
     * @param frameDTO
     * @return
     */
    @Override
    public Frame save(FrameDTO frameDTO) {
        Frame frame = new Frame();
        try {
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(frameDTO.getName());
            grabber.start();

            for (int i = 0; i < 10; i++) {
                org.bytedeco.javacv.Frame frameImage = grabber.grabImage();

                Java2DFrameConverter converter = new Java2DFrameConverter();
                BufferedImage buffer = converter.getBufferedImage(frameImage);

                try {
                    ImageIO.write(buffer, "jpg", new File(frameDTO.getDirectory() + "frameName-" + i + ".jpg"));
                    File file = new File(frameDTO.getDirectory());
                    frame.setName(frameDTO.getName());
                    frame.setImages(blob(file));

                    repository.save(frame);

                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.getLogger(FrameServiceImpl.class.getName()).log(Level.SEVERE, null, e);
                }
            }

        } catch (Exception e) {
            Logger.getLogger(FrameServiceImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    /**
     * Method responsible to find and retrieve an specific frame
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Frame findById(Long id) throws Exception {
        try {
            Optional<Frame> frameRetrieved = repository.findById(id);
            if (frameRetrieved.isPresent()) {
                Frame frame = frameRetrieved.get();

                return frame;
            }

        } catch (Exception e) {
            Logger.getLogger(FrameServiceImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    /**
     * Method responsible to retrieve all frames saved
     * @return
     */
    @Override
    public List<Frame> retrieveAll() {
        List<Frame> frameList = new ArrayList<>();
        try {
            repository.findAll().forEach(frameList::add);
            return frameList;
        } catch (Exception e) {
            Logger.getLogger(FrameServiceImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    /**
     * Method Responsible to update the information and the blob into the databases
     * @param id
     * @param frameDTO
     * @return
     */
    @Override
    public boolean update(Long id, FrameDTO frameDTO) {
        File file = new File(frameDTO.getDirectory());
        try {
            Frame frame = this.findById(id);
            if (frame != null) {
                frame.setName(frameDTO.getName());
                frame.setName(frameDTO.getName());
                frame.setImages(blob(file));

                repository.save(frame);
            }
        } catch (Exception e) {
            Logger.getLogger(FrameServiceImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return false;
    }

    /**
     * Method responsible to delete some specific frame according to the id informed
     * @param id
     * @return
     */
    @Override
    public boolean delete(Long id) {
        try {
            Frame frame = this.findById(id);
            if (frame != null) {
                repository.delete(frame);
                return true;
            }

        } catch (Exception e) {
            Logger.getLogger(FrameServiceImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    /**
     * Method Responsible  to convert an image to a byte[] arrays to be saved as a blob into th databases;
     * @param image
     * @return
     * @throws IOException
     */
    public byte[] blob(File image) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(image.getPath()));
        return bytes;
    }

}
