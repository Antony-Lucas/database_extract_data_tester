package com.example.demo.controller;

import com.example.demo.dto.ChatGPTRequest;
import com.example.demo.dto.ChatGptResponse;
import com.example.demo.dto.Message;
import com.example.demo.entities.AtendimentoPacienteV;
import com.example.demo.repositories.AtendimentPacienteRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bot")
public class CustomBotController {

    @Value("${openai.model}")
    private String model;
    @Value(("${openai.api.url}"))
    private String apiURL;
    @Autowired
    private RestTemplate template;
    @Autowired
    private AtendimentPacienteRepositories atendimentPacienteRepositories;

    @GetMapping("/getAtendimentos")
    public ResponseEntity<List<AtendimentoPacienteV>> findAll(){
        LocalDate last60Days = LocalDate.now().minusDays(60);
        List<AtendimentoPacienteV> obj = atendimentPacienteRepositories.findAllByDtEntradaAfter(last60Days);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("prompt") String prompt){
        ChatGPTRequest request=new ChatGPTRequest(model, prompt);
        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
        return chatGptResponse.getChoices().get(0).getMessage().getContent();
    }
}
