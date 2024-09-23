# Employee Monitoring and Task Management System

This project involves creating an application for monitoring employees and managing their tasks, divided into two main roles: **Admin** and **Employee**, each with specific functionalities.

## Admin Features:
- **Login**: Admin logs in with a username and password.
- **User Management**: Admin can view the full list of employees, including login times. They can also add, update, and delete employees.
- **Task Assignment**: Admin can assign tasks to employees, including description, deadline, and task type. Tasks are visible to both Admin and Employee.
- **Task Deletion**: Admin can revoke assigned tasks, removing them from the employee's view.
- **View Active Employees**: Admin can see the list of currently logged-in employees.

## Employee Features:
- **Login**: Employees log in with a unique username and password.
- **Task View**: Employees can see tasks assigned by the Admin and mark them as completed or add notes.
- **Automatic Attendance Logging**: Upon login, employee presence is automatically recorded.
- **Logout**: Upon logout, the Admin is notified.

## Diagrams and Design:
- **Use Case Diagrams**: Shows interactions between Admin, Employee, and the system (login, task assignment, task management).
- **Class Diagrams**: Describes the application structure, including users (Admin, Employee), tasks, and user sessions.
- **Sequence Diagrams**: Illustrates specific user interactions, like login, task assignment, and logout.
- **Activity Diagrams**: Details workflows for Admin and Employee, covering main actions like adding employees or assigning tasks.

## Database:
The application uses **PostgreSQL** for reliable transaction management and data integrity. The database stores employee, task, and session data.

## Development Stages:
1. **Requirements Analysis**: Identify key functionalities and specification limits.
2. **Design**: Create UML diagrams (use cases, sequence, activity, class diagrams) to organize the application architecture.
3. **Implementation**: Develop the database and user interface, followed by system functionality.
4. **Testing**: Conduct unit and functional testing to ensure correct system behavior.

## Conclusion:
The application provides an efficient system for employee monitoring and task management. The Admin can easily manage tasks and employees, while employees can view their tasks and notify the Admin about their progress.
