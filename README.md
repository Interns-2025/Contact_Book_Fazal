# Contact Manager

A simple Java-based Contact Manager application that provides both CLI (Console) and GUI (Swing) interfaces. It follows a clean layered architecture using MVC principles.

## 🛠 Features

- Add, view, delete, and search contacts
- CLI and GUI options to interact with the app
- Formatted table display for CLI using utility class
- Clean separation using Controller, Service, DAO, View, and Model layers

## 📁 Project Structure

```
src/
└── main/
   ├── java/
   │   └── com.khan.fazal.intern/
   │       ├── app/          # Main entry point
   │       ├── controller/   # Controllers for CLI and GUI logic
   │       ├── dao/          # In-memory data access (can be extended to DB)
   │       ├── model/        # Contact data model
   │       ├── service/      # Business logic and validation
   │       ├── utils/        # Utilities (like PrintDash)
   │       └── view/         # CLI and GUI views
   └── resources/
       └── contacts.csv     
```

## 🚀 Getting Started

### Requirements

- Java 17+ (or compatible)
- IntelliJ IDEA (recommended) for development

### How to run

```
git clone https://github.com/Interns-2025/Contact_Book_Fazal.git
cd Contact_Book_Fazal
java -jar ContactManager.jar
```

## 📦 Build Instructions

If you're using Maven:

```bash
mvn clean package
java -cp target/ContactManager-1.0-SNAPSHOT.jar com.khan.fazal.intern.app.Main
```

## 📌 Author

- Fazal Khan

---

© 2025 Contact Manager. All rights reserved.