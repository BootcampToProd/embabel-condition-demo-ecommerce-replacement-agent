package com.bootcamptoprod.controller;

import com.bootcamptoprod.dto.FinalDecision;
import com.bootcamptoprod.dto.ReplacementRequest;
import com.embabel.agent.api.common.autonomy.AgentInvocation;
import com.embabel.agent.core.AgentPlatform;
import com.embabel.agent.core.ProcessOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ecommerce/product")
public class EcommerceController {

    private static final Logger log = LoggerFactory.getLogger(EcommerceController.class);

    private final AgentPlatform agentPlatform;

    @Autowired
    public EcommerceController(AgentPlatform agentPlatform) {
        this.agentPlatform = agentPlatform;
    }

    @PostMapping("/replacement")
    public ResponseEntity<FinalDecision> handleReplacement(@RequestBody ReplacementRequest request) {

        var productReplacementAgentInvocation = AgentInvocation
                .builder(agentPlatform)
                .options(ProcessOptions.builder().verbosity(v -> {
                    v.showPrompts(true);
                    v.showPlanning(true);
                    v.showLlmResponses(true);
                }).build())
                .build(FinalDecision.class);

        FinalDecision finalDecision = productReplacementAgentInvocation.invoke(request);
        log.info("Result: {}", finalDecision);
        return ResponseEntity.ok(finalDecision);
    }
}