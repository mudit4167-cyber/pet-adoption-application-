import java.util.ArrayList;
import java.util.Scanner;

// ================= Online Pet Adoption Platform (Simple Version) =================
// A basic console app for a college mini-project.
// Two types of users: admin and normal user.
// Any user can list a pet for adoption, and any user can adopt any available pet.
// ====================================================================================

class User {
    int id;
    String name;
    String email;
    String password;
    String role; // "admin" or "user"

    User(int id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
class Pet {
    int id;
    String name;
    String breed;
    int age;
    String description;
    String status; // "Available" or "Adopted"
    String listedBy;
    Pet(int id, String name, String breed, int age, String description, String listedBy) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.description = description;
        this.status = "Available";
        this.listedBy = listedBy;
    }
    void printShort() {
        System.out.println("ID: " + id + " | " + name + " | Breed: " + breed
                + " | Age: " + age + " | Status: " + status + " | Listed by: " + listedBy);
    }
    void printFull() {
        System.out.println("---------------------------------------------");
        System.out.println("Pet ID      : " + id);
        System.out.println("Name        : " + name);
        System.out.println("Breed       : " + breed);
        System.out.println("Age         : " + age + " years");
        System.out.println("Description : " + description);
        System.out.println("Status      : " + status);
        System.out.println("Listed by   : " + listedBy);
        System.out.println("---------------------------------------------");
    }
}
public class PetAdoptionApp {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<Pet> pets = new ArrayList<>();
    static int userIdCounter = 1;
    static int petIdCounter = 1;
    public static void main(String[] args) {
        addSampleData();
        System.out.println("=====================================");
        System.out.println(" ONLINE PET ADOPTION PLATFORM ");
        System.out.println("=====================================");
        System.out.println("Admin login -> admin@petadopt.com / admin123");
        System.out.println("User login  -> ravi@example.com / user123");
        boolean run = true;
        while (run) {
            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 1) {
                login();
            } else if (choice == 2) {
                register();
            } else if (choice == 3) {
                run = false;
            } else {
                System.out.println("Invalid choice!");
            }
        }
        System.out.println("Thank you for using Pet Adoption Platform!");
    }
    // ---------------- Sample starting data ----------------
    static void addSampleData() {
        users.add(new User(userIdCounter++, "Admin", "admin@petadopt.com", "admin123", "admin"));
        users.add(new User(userIdCounter++, "Ravi Kumar", "ravi@example.com", "user123", "user"));

        pets.add(new Pet(petIdCounter++, "Bruno", "Labrador", 2,
                "Friendly and energetic, loves to play fetch.", "Admin"));
        pets.add(new Pet(petIdCounter++, "Whiskers", "Persian Cat", 1,
                "Calm and affectionate, good with children.", "Ravi Kumar"));
    }
    // ---------------- Login / Register ----------------
    static void login() {
        System.out.print("Enter email: ");
        String email = sc.nextLine().trim();
        System.out.print("Enter password: ");
        String password = sc.nextLine().trim();
        User loggedInUser = null;
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            if (u.email.equals(email) && u.password.equals(password)) {
                loggedInUser = u;
                break;
            }
        }
        if (loggedInUser == null) {
            System.out.println("Invalid email or password.");
            return;
        }
        System.out.println("Login successful! Welcome " + loggedInUser.name);
        if (loggedInUser.role.equals("admin")) {
            adminMenu();
        } else {
            userMenu(loggedInUser);
        }
    }
    static void register() {
        System.out.print("Enter your name: ");
        String name = sc.nextLine().trim();
        System.out.print("Enter email: ");
        String email = sc.nextLine().trim();
        System.out.print("Enter password: ");
        String password = sc.nextLine().trim();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).email.equals(email)) {
                System.out.println("An account with this email already exists.");
                return;
            }
        }
        User newUser = new User(userIdCounter++, name, email, password, "user");
        users.add(newUser);
        System.out.println("Registration successful! You can now login.");
    }

    // ---------------- Admin Menu ----------------
    static void adminMenu() {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n----- ADMIN DASHBOARD -----");
            System.out.println("1. View All Users");
            System.out.println("2. Delete a User");
            System.out.println("3. View All Pets");
            System.out.println("4. Delete a Pet");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    deleteUser();
                    break;
                case 3:
                    viewAllPets();
                    break;
                case 4:
                    deletePet();
                    break;
                case 5:
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    static void viewAllUsers() {
        System.out.println("\n-- All Users --");
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            System.out.println("ID: " + u.id + " | " + u.name + " | " + u.email + " | Role: " + u.role);
        }
    }

    static void deleteUser() {
        System.out.print("Enter user ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        boolean found = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).id == id) {
                users.remove(i);
                found = true;
                break;
            }
        }
        System.out.println(found ? "User deleted." : "User not found.");
    }

    static void viewAllPets() {
        System.out.println("\n-- All Pets --");
        if (pets.size() == 0) {
            System.out.println("No pets listed yet.");
            return;
        }
        for (int i = 0; i < pets.size(); i++) {
            pets.get(i).printShort();
        }
    }

    static void deletePet() {
        System.out.print("Enter pet ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        boolean found = false;
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).id == id) {
                pets.remove(i);
                found = true;
                break;
            }
        }
        System.out.println(found ? "Pet deleted." : "Pet not found.");
    }

    // ---------------- User Menu ----------------
    static void userMenu(User currentUser) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n----- USER DASHBOARD (" + currentUser.name + ") -----");
            System.out.println("1. List a Pet for Adoption");
            System.out.println("2. Browse Available Pets");
            System.out.println("3. Adopt a Pet");
            System.out.println("4. View My Listed Pets");
            System.out.println("5. Update My Profile");
            System.out.println("6. Logout");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    listPet(currentUser);
                    break;
                case 2:
                    browsePets();
                    break;
                case 3:
                    adoptPet(currentUser);
                    break;
                case 4:
                    viewMyPets(currentUser);
                    break;
                case 5:
                    updateProfile(currentUser);
                    break;
                case 6:
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // Anyone can list any pet - it goes live immediately, no approval needed.
    static void listPet(User currentUser) {
        System.out.print("Enter pet name: ");
        String name = sc.nextLine();
        System.out.print("Enter breed: ");
        String breed = sc.nextLine();
        System.out.print("Enter age (years): ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter description: ");
        String description = sc.nextLine();

        Pet newPet = new Pet(petIdCounter++, name, breed, age, description, currentUser.name);
        pets.add(newPet);

        System.out.println("Pet listed successfully! It is now visible to everyone.");
    }

    static void browsePets() {
        System.out.println("\n-- Available Pets --");
        boolean any = false;
        for (int i = 0; i < pets.size(); i++) {
            Pet p = pets.get(i);
            if (p.status.equals("Available")) {
                p.printShort();
                any = true;
            }
        }
        if (!any) {
            System.out.println("No pets available right now.");
        }
    }

    // Anyone can adopt any available pet directly - no application needed.
    static void adoptPet(User currentUser) {
        System.out.print("Enter pet ID you want to adopt: ");
        int id = sc.nextInt();
        sc.nextLine();

        Pet p = findPetById(id);
        if (p == null) {
            System.out.println("Pet not found.");
            return;
        }
        if (!p.status.equals("Available")) {
            System.out.println("Sorry, this pet has already been adopted.");
            return;
        }

        p.status = "Adopted";
        System.out.println("Congratulations " + currentUser.name + "! You have adopted " + p.name + ".");
    }

    static void viewMyPets(User currentUser) {
        System.out.println("\n-- Pets I Listed --");
        boolean any = false;
        for (int i = 0; i < pets.size(); i++) {
            Pet p = pets.get(i);
            if (p.listedBy.equals(currentUser.name)) {
                p.printShort();
                any = true;
            }
        }
        if (!any) {
            System.out.println("You haven't listed any pets yet.");
        }
    }

    static void updateProfile(User currentUser) {
        System.out.println("Leave blank and press Enter to keep the current value.");

        System.out.print("New name (" + currentUser.name + "): ");
        String name = sc.nextLine().trim();
        if (!name.isEmpty()) {
            currentUser.name = name;
        }

        System.out.print("New email (" + currentUser.email + "): ");
        String email = sc.nextLine().trim();
        if (!email.isEmpty()) {
            currentUser.email = email;
        }

        System.out.print("New password: ");
        String password = sc.nextLine().trim();
        if (!password.isEmpty()) {
            currentUser.password = password;
        }

        System.out.println("Profile updated!");
    }

    // ---------------- Helper ----------------
    static Pet findPetById(int id) {
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).id == id) {
                return pets.get(i);
            }
        }
        return null;
    }
}
