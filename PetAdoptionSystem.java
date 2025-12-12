import java.sql.*;
import java.util.*;

public class PetAdoptionSystem {

    // ==========================================
    // DATABASE CONNECTION
    // ==========================================
    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/pet_adoption";
            String user = "root";      // change if needed
            String pass = "";          // change if needed
            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.out.println("Database Connection Failed: " + e);
            return null;
        }
    }

    // ==========================================
    // ADD A PET (CREATE)
    // ==========================================
    public static void addPet() {
        try (Connection conn = getConnection()) {
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter Pet Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Pet Type: ");
            String type = sc.nextLine();

            System.out.print("Enter Pet Age: ");
            int age = sc.nextInt();

            String status = "Available";

            String sql = "INSERT INTO pets(name, type, age, status) VALUES(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, type);
            ps.setInt(3, age);
            ps.setString(4, status);

            ps.executeUpdate();
            System.out.println("\n✔ Pet Added Successfully!\n");

        } catch (Exception e) {
            System.out.println("Error Adding Pet: " + e);
        }
    }

    // ==========================================
    // VIEW PETS (READ)
    // ==========================================
    public static void viewPets() {
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM pets";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n========= PET LIST =========");
            System.out.println("ID | Name | Type | Age | Status");
            System.out.println("==============================");

            while (rs.next()) {
                System.out.println(
                    rs.getInt("id") + " | " +
                    rs.getString("name") + " | " +
                    rs.getString("type") + " | " +
                    rs.getInt("age") + " | " +
                    rs.getString("status")
                );
            }
            System.out.println("==============================\n");

        } catch (Exception e) {
            System.out.println("Error Fetching Pets: " + e);
        }
    }

    // ==========================================
    // UPDATE PET (UPDATE)
    // ==========================================
    public static void updatePet() {
        try (Connection conn = getConnection()) {
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter Pet ID to Update: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter New Pet Name: ");
            String name = sc.nextLine();

            System.out.print("Enter New Pet Type: ");
            String type = sc.nextLine();

            System.out.print("Enter New Age: ");
            int age = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Status (Available/Adopted): ");
            String status = sc.nextLine();

            String sql = "UPDATE pets SET name=?, type=?, age=?, status=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, type);
            ps.setInt(3, age);
            ps.setString(4, status);
            ps.setInt(5, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("\n✔ Pet Updated Successfully!\n");
            else
                System.out.println("\n✖ Pet ID Not Found!\n");

        } catch (Exception e) {
            System.out.println("Error Updating Pet: " + e);
        }
    }

    // ==========================================
    // DELETE PET (DELETE)
    // ==========================================
    public static void deletePet() {
        try (Connection conn = getConnection()) {
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter Pet ID to Delete: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM pets WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("\n✔ Pet Deleted Successfully!\n");
            else
                System.out.println("\n✖ Pet ID Not Found!\n");

        } catch (Exception e) {
            System.out.println("Error Deleting Pet: " + e);
        }
    }

    // ==========================================
    // MAIN MENU
    // ==========================================
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Pet Adoption Center =====");
            System.out.println("1. Add Pet");
            System.out.println("2. View All Pets");
            System.out.println("3. Update Pet Info");
            System.out.println("4. Delete Pet");
            System.out.println("5. Exit");
            System.out.print("Choose Option: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1: addPet(); break;
                case 2: viewPets(); break;
                case 3: updatePet(); break;
                case 4: deletePet(); break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}
