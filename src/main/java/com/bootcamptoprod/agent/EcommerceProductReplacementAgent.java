package com.bootcamptoprod.agent;

import com.bootcamptoprod.dto.FinalDecision;
import com.bootcamptoprod.dto.InventoryStatus;
import com.bootcamptoprod.dto.ReplacementRequest;
import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.Condition;
import com.embabel.agent.api.common.Ai;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Agent(
        name = "ecommerce-product-replacement-agent",
        description = "Handles product replacements and refunds based solely on inventory status.",
        version = "1.0.0"
)
@Component
public class EcommerceProductReplacementAgent {

    private static final Logger log = LoggerFactory.getLogger(EcommerceProductReplacementAgent.class);

    private static final Map<String, Integer> inventory = Map.of("SKU-HDPHN-01", 10, "SKU-KBD-05", 0);

    @Action(description = "Check product inventory whether mentioned product is in stock", post = {"isInStock", "isOutOfStock"})
    public InventoryStatus checkInventory(ReplacementRequest replacementRequest) {
        log.info("[ACTION] checkInventory START - productSku: {}", replacementRequest.productSku());
        int stock = inventory.getOrDefault(replacementRequest.productSku(), 0);
        boolean inStock = stock > 0;
        InventoryStatus status = new InventoryStatus(replacementRequest.productSku(), inStock);
        log.info("[ACTION] checkInventory END - SKU: {}, units: {}, inStock: {}", replacementRequest.productSku(), stock, inStock);
        return status;
    }

    @Condition(name = "isInStock")
    public boolean isInStock(InventoryStatus status) {
        log.info("[CONDITION] isInStock START - SKU: {}, inStock: {}", status.productSku(), status.inStock());
        boolean result = status.inStock();
        log.info("[CONDITION] isInStock END - result: {}", result);
        return result;
    }

    @Condition(name = "isOutOfStock")
    public boolean isOutOfStock(InventoryStatus status) {
        log.info("[CONDITION] isOutOfStock START - SKU: {}, inStock: {}", status.productSku(), status.inStock());
        boolean result = !status.inStock();
        log.info("[CONDITION] isOutOfStock END - result: {}", result);
        return result;
    }

    @Action(pre = {"isInStock"})
    @AchievesGoal(description = "Replacement issued for in-stock products")
    public FinalDecision issueReplacement(ReplacementRequest replacementRequest, Ai ai) {
        log.info("[GOAL] issueReplacement START - productSku: {}", replacementRequest.productSku());
        FinalDecision decision = ai.withDefaultLlm()
                .createObjectIfPossible(
                        """
                                Inform the customer that their return is approved, replacement unit has been ordered and will be shipped soon. 
                                Respond with decisionCode = "REPLACEMENT_ISSUED" and message.
                                """,
                        FinalDecision.class
                );
        log.info("[GOAL] issueReplacement END - decision: {}", decision);
        return decision;
    }

    @Action(pre = {"isOutOfStock"})
    @AchievesGoal(description = "Refund issued for out-of-stock products")
    public FinalDecision issueRefund(ReplacementRequest replacementRequest, Ai ai) {
        log.info("[GOAL] issueRefund START - productSku: {}", replacementRequest.productSku());


        FinalDecision decision = ai.withDefaultLlm()
                .createObjectIfPossible(
                        """
                                Inform the customer that the product is out of stock and a refund has been issued.
                                Respond decisionCode = "REFUND_ISSUED" and message.
                                """,
                        FinalDecision.class
                );
        log.info("[GOAL] issueRefund END - decision: {}", decision);
        return decision;
    }
}
