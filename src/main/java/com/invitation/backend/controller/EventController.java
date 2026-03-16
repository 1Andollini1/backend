package com.invitation.backend.controller;

import com.invitation.backend.dto.response.EventResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api")
public class EventController {
    @GetMapping("/event")
    public EventResponse event() {
        EventResponse event = new EventResponse();
        event.title = "Свадьба Никиты и Маши";
        event.dateTime = LocalDateTime.of(2026, 1, 13, 10, 45);
        event.address = "Где-то";

        return event;
    }
}
