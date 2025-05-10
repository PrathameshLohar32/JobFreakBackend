package com.JobAppBackend.JobFreakBackend.contollers;

import com.JobAppBackend.JobFreakBackend.services.ChatBotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/JobFreak/chat")
public class ChatBotController {

    private ChatBotService chatBotService;


    public ChatBotController(ChatBotService chatBotService) {
        this.chatBotService = chatBotService;
    }

    @GetMapping("/askAi")
    public ResponseEntity<Flux<String>> askAi(@RequestParam(name = "query",required = false, defaultValue = "Hi, How can you help me with job search, interview preparation, resume building ?") String query){
        Flux<String> response = chatBotService.getAnswer(query);
        return ResponseEntity.ok(response);
    }
}
