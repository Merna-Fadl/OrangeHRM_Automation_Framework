
# 🚀 OrangeHRM Enterprise Automation Framework

This is a professional, production-ready **End-to-End Automation Framework** built from scratch to automate complex business workflows for an **ERP (Enterprise Resource Planning) system**, specifically the OrangeHRM portal. It is meticulously designed with a focus on high scalability, clean code principles, and stable cross-browser execution.

## 🔄 CI/CD & Live Report
* **Build Status:** ![Java CI with Maven](https://github.com/Merna-Fadl/OrangeHRM_Automation_Framework/actions/workflows/maven.yml/badge.svg)
* **Live Execution Report:** [Click here to view the latest Allure Report](https://merna-fadl.github.io/OrangeHRM_Automation_Framework/)

> **Note:** This project is fully integrated with **GitHub Actions**. Tests run automatically on every push or pull request to ensure continuous quality, stability, and fast feedback.

## 🛠️ Tech Stack
* **Language:** Java (JDK 22)
* **Automation Tool:** Selenium WebDriver
* **Testing Framework:** TestNG
* **Build Tool:** Maven
* **Reporting:** Allure Reports
* **Logging:** Log4j2
* **Design Pattern:** Page Object Model (POM) with Fluent Design

## ✨ Advanced Framework Features
* **ERP Business Logic Automation:** Automates multi-step, complex workflows involving roles, dynamic administration views, and data dependency.
* **Cross-Browser & Headless Stability:** Engineered custom driver configurations inside `BrowserFactory` to ensure stable **Headless execution** across Chrome, Edge, and Firefox by isolating window size constraints (`1920x1080`) and specific browser profiles.
* **Data-Driven Testing via JSON:** Integrated a robust `JsonReader` utility to decouple test scripts from hardcoded test data, allowing dynamic test execution using external JSON files.
* **Dynamic Synchronization:** Custom `Waits` utility utilizing Selenium Explicit and Fluent conditions to cleanly handle asynchronous AJAX-based components and completely eliminate flaky tests.
* **Advanced Validations:** Implemented a customized `CustomSoftAssertion` handler to validate multiple checkpoints across enterprise forms without prematurely halting test script execution.
* **Thread-Safe Architecture:** Utilizes a decoupled `DriverManager` leveraging `ThreadLocal<WebDriver>` to natively support parallel test execution.
* **Automatic Visual Evidence:** Custom TestNG listeners capture screenshots automatically on script failure, capturing crucial runtime context and embedding it directly into **Allure Reports**.

## 📂 Project Structure
```text
src/main/java
 ├── DriverManager  --> ThreadLocal driver management & isolated BrowserFactory configurations
 ├── Pages          --> Page Objects using Fluent Design (Login, Admin, PIM, Recruitment)
 └── Utils          --> Reusable components (ElementActions wrappers, Waits, JsonReader, CustomAsserts)

src/test/java
 ├── tests          --> Functional, Negative Data-Driven, and E2E Regression suites
 └── resources      --> Test Data (JSON inputs), sample attachments, and log4j2 configuration files

```

## 📊 Business Scenarios Covered (ERP Logic)

* [x] **Secure Authentication:** Robust logic verifying valid sign-ins and extensive data-driven negative test coverages (e.g., empty fields, invalid credentials) handling field validation states.
* [x] **PIM Module (Employee Lifecycle):** Full CRUD workflow coverage including creating, locating, filtering, and deleting unique employee records safely.
* [x] **Admin Module (System Access Control):** Automating complex management workflows, role definitions, and business rule safeguards (e.g., duplicate username validation).
* [x] **Recruitment Module (Candidate Pipeline):** Handling candidate onboarding steps, pipeline stages, and file attachment handling (`sample_cv.pdf` upload automation).

## 🚀 How to Run Locally

1. **Clone the repository:**
```bash
git clone [https://github.com/Merna-Fadl/OrangeHRM_Automation_Framework.git](https://github.com/Merna-Fadl/OrangeHRM_Automation_Framework.git)
```
2. **Navigate to the project folder:**
```bash
cd OrangeHRM_Automation_Framework
```
3. **Execute the test suites via Maven:**
```bash
mvn clean test

```
4. **Generate and open the Allure Report:**
```bash
allure serve allure-results
```
---

💡 *Developed with passion by [Merna Fadl](https://www.google.com/search?q=https://linkedin.com/in/merna-fadl-2b9578150) — Software Testing & QA Engineer.*

```
### 🎯 الخطوة التالية المقترحة:
بعد رفع الـ `README` الجديد، تأكدي من عمل **Pin** للـ Repository في حسابكِ الشخصي على GitHub لتكون الواجهة الأساسية التي يراها الـ Recruiters والمديرين التقنيين عند مراجعة أعمالكِ. الـ README كُتب بأسلوب هندسي قوي (Engineering jargon) يوضح مدى فهمكِ للتفاصيل الصغيرة مثل الـ Headless والـ Window resizing والـ decoupled data-driven.

```