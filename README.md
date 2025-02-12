# Online Assignment Submission System

## Project Overview
This project is a **Jakarta EE-based web application** that allows students to submit assignments, projects, and practice exercises online, while instructors can create and manage assignments. The system supports secure user authentication and file uploads.

---

## Project Structure
```
AssignmentSubmissionSystem/
├── src/
│   ├── dao/              # Data Access Objects (Hibernate)
│   ├── model/            # Entity Classes (Hibernate)
│   ├── controller/          # Servlets (Controllers)
│   ├── utils/            # Utility classes (e.g., HibernateUtil)
│
├── web/
│   ├── WEB-INF/          # Web configuration files (web.xml)
│   ├── jsp/              # JSP Pages (Frontend UI)
│   ├── css/              # Bootstrap and custom styles
│   ├── js/               # JavaScript scripts
│
├── pom.xml               # Maven dependency management
├── README.md             # Project documentation
└── schema/                  # Database Schemas
```

---

## Technologies Used
- **Backend:** Jakarta EE (Servlets), Hibernate (ORM)
- **Frontend:** JSP, Bootstrap 5
- **Database:** MySQL (Using Hibernate for ORM)
- **Security:** BCrypt (for password hashing)
- **Server:** Tomcat (localhost deployment)

---

## Features
### **Student Functionality**
- Register/Login securely
- View available assignments
- Upload, delete, and re-upload assignment submissions
- View submission deadlines

### **Instructor Functionality**
- Register/Login securely
- Create, edit, and delete assignments
- Set deadlines for submissions
- View submitted assignments
- Identify late submissions

---

## Installation & Setup
### 1️⃣ **Clone the Repository**
```sh
git clone https://github.com/christian783/OnlineSubmissionSystem.git
cd OnlineSubmissionSystem
```

### 2️⃣ **Database Configuration**
- Create a MySQL database: `assignment_db`
- Run the SQL scripts in `schema folder` to set up tables
- Update `hibernate.cfg.xml` with your database credentials:
  ```java
  <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/assignment_db</property>
        <property name="hibernate.connection.username">root</property>
        <property name="hibernate.connection.password">yourPassword</property>
  ```

### 3️⃣ **Run the Application**
- **Using Maven** (if applicable):
  ```sh
  mvn clean install
  mvn tomcat7:run
  ```
- **Manually:**
    - Deploy on Apache Tomcat (`webapps/` folder)
    - Start Tomcat and visit: `http://localhost:8080/AssignmentSubmissionSystem`

---

## API Endpoints (Servlets)
| Endpoint                   | Method | Description |
|----------------------------|--------|-------------|
| `/RegisterServlet`         | POST   | Handles user registration |
| `/login`                   | POST   | Handles user login |
| `/CreateAssignment`        | POST   | Instructor creates an assignment |
| `/UpdateAssignmentServlet` | POST   | Instructor updates an assignment |
| `/DeleteAssignmentServlet` | GET    | Instructor deletes an assignment |
| `/UploadSubmissionServlet` | POST   | Student uploads an assignment |
| `/DeleteSubmissionServlet` | GET    | Student deletes a submission |

---

## Security Features
- **Password hashing with BCrypt** (prevents password leaks)
- **Session-based authentication** (users must log in to access pages)
- **Authorization checks** (students can only delete their submissions, instructors can only delete their own assignments)

---

## License
This project is open-source and licensed under the **MIT License**.

---

## Contributing
Contributions are welcome! To contribute:
1. Fork the repository
2. Create a feature branch: `git checkout -b feature-name`
3. Commit changes: `git commit -m "Added feature XYZ"`
4. Push the branch: `git push origin feature-name`
5. Open a pull request

---

## Contact
For any questions or support, feel free to reach out:
- 📧 Email: christiantc783@gmail.com
- 🔗 GitHub: [Christian783](https://github.com/Christian783)

