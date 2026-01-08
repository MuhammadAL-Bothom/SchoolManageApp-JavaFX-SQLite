# SchoolManageApp-JavaFX-SQLite

JavaFX + SQLite (JDBC) multi-screen GUI application with CRUD operations.
Built as a Visual Programming course project.

## ✅ Features
- JavaFX GUI (FXML) + CSS styling
- SQLite database (JDBC)
- Multi-screen navigation (8 screens)
- CRUD operations:
  - Items: Create / Read / Update / Delete
  - Users: View / Delete (Admin protected)
  - Transactions: Create + View + automatic stock update
- Reports screen (Total sales + total transactions)
- Default admin user is created automatically on first run

## 🖥️ Screens (Minimum 8)
1. Login  
2. Register  
3. Dashboard  
4. Users  
5. Items  
6. Transactions  
7. Reports  
8. About  

## 🗃️ Database Tables (Minimum 3)
- `users(user_id, username, password, role)`
- `items(item_id, name, quantity, price)`
- `transactions(transaction_id, user_id, item_id, qty, total_amount, date)`

Database file: `app.db` (created automatically in the project folder).

## 🔐 Default Login
- Username: `admin`
- Password: `admin`

## ⚙️ Requirements
- Java JDK 17 or 21 (recommended: 21 LTS)
- Apache Maven
- (Optional) VS Code + Extension Pack for Java

Check installations:
```bash
java -version
mvn -version

▶️ Run the project (Windows / Mac / Linux)

Method 1: Run inside project folder

Open terminal inside the folder that contains pom.xml:

cd VisualProject
mvn clean javafx:run

Method 2: Run using pom.xml path (from anywhere)

mvn -f VisualProject/pom.xml clean javafx:run

🧪 Testing Steps (Quick Demo)

Login using admin/admin

Go to Items → Add item (e.g., Book qty=10 price=5)

Go to Transactions → Select Book, qty=2 → Save

Verify:

New transaction appears

Item quantity decreases

Go to Reports → Verify totals

Go to Users → See users list / delete a non-admin user

Logout

📁 Project Structure
src/main/java/com/wise/app/
  MainApp.java
  DB.java

src/main/java/com/wise/app/controller/
  LoginController.java
  RegisterController.java
  DashboardController.java
  UsersController.java
  ItemsController.java
  TransactionsController.java
  ReportsController.java
  AboutController.java

src/main/java/com/wise/app/dao/
  UserDAO.java
  ItemDAO.java
  TxDAO.java

src/main/java/com/wise/app/model/
  User.java
  Item.java
  Tx.java

src/main/java/com/wise/app/util/
  AlertUtil.java
  Session.java

src/main/resources/com/wise/app/view/
  login.fxml
  register.fxml
  dashboard.fxml
  users.fxml
  items.fxml
  transactions.fxml
  reports.fxml
  about.fxml

src/main/resources/com/wise/app/style/
  app.css


🧯 Common Issues

1) "There is no POM in this directory"

You are not inside VisualProject where pom.xml exists.
Fix:

cd VisualProject

2) Dependencies not resolved

Force update:

mvn -U clean javafx:run

📌 Notes

app.db is generated automatically on first run.

Admin account is inserted automatically if not exists ((admin , admin)).
