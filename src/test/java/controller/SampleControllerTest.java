package controller;

import com.charliekriska.metabolic_health_api.controller.SampleController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleControllerTest {

    @Test
    void sampleMethodTest() {
        assertEquals("Hello!", SampleController.sampleMethod());
    }

}
