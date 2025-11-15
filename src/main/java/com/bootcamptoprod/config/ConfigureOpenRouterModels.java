package com.bootcamptoprod.config;

import com.embabel.agent.api.models.OpenAiCompatibleModelFactory;
import com.embabel.common.ai.model.Llm;
import com.embabel.common.ai.model.PerTokenPricingModel;
import io.micrometer.observation.ObservationRegistry;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class ConfigureOpenRouterModels extends OpenAiCompatibleModelFactory {

    public ConfigureOpenRouterModels(@NotNull ObservationRegistry observationRegistry,
                                     @Value("${OPENROUTER_API_KEY}") String apiKey) {
        super(
                "https://openrouter.ai",
                apiKey,
                "/api/v1/chat/completions",
                null,
                observationRegistry
        );
    }

    @Bean
    public Llm llama4_free() {
        return openAiCompatibleLlm(
                "meta-llama/llama-4-scout:free",
                new PerTokenPricingModel(0.0, 0.0),
                "OpenRouter",
                LocalDate.of(2025, 1, 1)
        );
    }

    @Bean
    public Llm mistral7b_free() {
        return openAiCompatibleLlm(
                "mistralai/mistral-7b-instruct:free",
                new PerTokenPricingModel(0.0, 0.0),
                "OpenRouter",
                LocalDate.of(2024, 10, 1)
        );
    }

    @Bean
    public Llm deepseek_r1_t2() {
        return openAiCompatibleLlm(
                "tngtech/deepseek-r1t2-chimera:free",
                new PerTokenPricingModel(0.0, 0.0),
                "OpenRouter",
                LocalDate.of(2025, 5, 28)
        );
    }

    @Bean
    public Llm glm_4_5_air() {
        return openAiCompatibleLlm(
                "z-ai/glm-4.5-air:free",
                new PerTokenPricingModel(0.0, 0.0),
                "OpenRouter",
                LocalDate.of(2025, 5, 28)
        );
    }
}