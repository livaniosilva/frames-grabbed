package br.com.frame;

import br.com.frame.dto.FrameDTO;
import br.com.frame.entity.Frame;
import br.com.frame.service.FrameService;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.logging.Level;
import java.util.logging.Logger;


@SpringBootTest
@RunWith(SpringRunner.class)
public class FrameTest {
    @Autowired
    FrameService service;
    @Autowired
    FrameDTO frameDTO;


    @Test
    public void testFFmpegFrameGrabber() {

        try {
            Frame result;

            String currentDirectory = System.getProperty("user.dir");
            String videoDirectory = currentDirectory + "/src/test/java/org/bytedeco/javacv/resource/";
            String frameDirectory = currentDirectory + "/src/test/java/org/bytedeco/javacv/frames/";
            frameDTO.setDirectory(videoDirectory);
            frameDTO.setName(frameDirectory);

            result = service.save(frameDTO);
            // result = service.save(frame + "big_buck_bunny_trailer.webm", frameDirectory);

            Assert.assertEquals(frameDTO, result);
        } catch (FFmpegFrameGrabber.Exception ex) {
            Logger.getLogger(FrameTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
