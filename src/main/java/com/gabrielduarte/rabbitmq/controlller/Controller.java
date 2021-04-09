package com.gabrielduarte.rabbitmq.controlller;

import com.gabrielduarte.rabbitmq.service.ControllerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
public class Controller {

    private final ControllerService controllerService;

    public Controller(ControllerService controllerService) {
        this.controllerService = controllerService;
    }

    @GetMapping("/produce")
    public void produceMessage() {
        controllerService.produce();
    }

}
