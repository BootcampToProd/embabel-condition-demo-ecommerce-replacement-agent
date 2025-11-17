# 🤖 Embabel AI Agent with Conditional Logic: E-Commerce Replacement Agent

This repository demonstrates how to build a decision-making AI agent using the Embabel Framework and Spring Boot. The application showcases an agent that uses conditional logic to handle an e-commerce product replacement workflow. Based on product inventory, the agent intelligently decides whether to issue a replacement or process a refund, all orchestrated by Embabel's planner.

📖 **Complete Guide**: For detailed explanations and a full code walkthrough, read our comprehensive tutorial.<br>
👉 [**Embabel Framework Condition Annotation: Building Decision Making AI Agents**](https://bootcamptoprod.com/embabel-framework-condition-annotation/)

🎥 **Video Tutorial**: Prefer hands-on learning? Watch our step-by-step implementation guide.<br>
👉 YouTube Tutorial - [**Embabel Framework @Condition Annotation - Build AI Agents with Conditional Logic**](https://youtu.be/BRXkK0QP2hc)

<p align="center">
  <a href="https://youtu.be/BRXkK0QP2hc">
    <img src="https://img.youtube.com/vi/BRXkK0QP2hc/0.jpg" alt="Embabel Framework @Condition Annotation - Build AI Agents with Conditional Logic" />
  </a>
</p>

<p align="center">
  ▶️ <a href="https://youtu.be/BRXkK0QP2hc">Watch on YouTube</a>
</p>

---

## ✨ What This Project Demonstrates

This application showcases an **intelligent agent with a rule-based workflow**:

- **Conditional Logic** using Embabel's `@Condition` annotation to control the agent's flow.
- **Decision-Making Workflow** that chooses between issuing a replacement or a refund.
- **Dynamic Planning** where the agent forms a plan based on runtime inventory status.
- **Separation of Concerns** using deterministic Java for business rules (inventory check) and an LLM for creative tasks (generating user messages).
- **A REST API Endpoint** for easy integration and testing of the agent's decision-making capabilities.

---


## 📋 Prerequisites

Before running this application, ensure you have:

- **Java 21** or higher
- **OpenRouter API Key** (free tier available at [OpenRouter.ai](https://openrouter.ai/))

---

## 🚀 Quick Start

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/BootcampToProd/embabel-condition-demo-ecommerce-replacement-agent.git
cd embabel-condition-demo-ecommerce-replacement-agent
```

### 2️⃣ Configure API Key
Provide your OpenRouter API key as an environment variable. You can set this in your IDE's run configuration or directly in your terminal.
```bash
OPENROUTER_API_KEY={YOUR_OPENROUTER_API_KEY}
```

### 3️⃣ Build the Project
```bash
mvn clean install
```

### 4️⃣ Run the Application
```bash
mvn spring-boot:run
```
The application will start on http://localhost:8080.

---

## 💡 Usage Example

Once the application is running, you can test the agent's conditional logic by sending POST requests for both an in-stock and an out-of-stock product.

### ✅ Scenario 1: Product In Stock

#### 📝 cURL Request
```bash
curl --location 'http://localhost:8080/api/v1/ecommerce/product/replacement' \
--header 'Content-Type: application/json' \
--data '{
    "productSku": "SKU-HDPHN-01",
    "problemDescription": "The headphones have a crackling sound in the right ear."
}'
```

#### 📊 Example Output
```json
{
    "decisionCode": "REPLACEMENT_ISSUED",
    "message": "Your return has been approved. A replacement unit has been ordered and will be shipped to you soon."
}
```

### ❌ Scenario 2: Product Out of Stock

#### cURL Request
```bash
curl --location 'http://localhost:8080/api/v1/ecommerce/product/replacement' \
--header 'Content-Type: application/json' \
--data '{
    "productSku": "SKU-KBD-05",
    "problemDescription": "The W key on my keyboard is not working."
}'
```

#### Example Output
```json
{
    "decisionCode": "REFUND_ISSUED",
    "message": "We're sorry, but the product you requested a replacement for is currently out of stock. A refund has been issued to your original payment method."
}
```