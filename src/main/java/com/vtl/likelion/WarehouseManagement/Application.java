package com.vtl.likelion.WarehouseManagement;

import com.vtl.likelion.WarehouseManagement.Connect.Connect;
import com.vtl.likelion.WarehouseManagement.Model.Role;
import com.vtl.likelion.WarehouseManagement.Model.User;
import com.vtl.likelion.WarehouseManagement.Model.Warehouse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public class Application {
    private static Scanner sc = new Scanner(System.in);
    private static User user = null;

    public static void main(String[] args) {
        Connect conn = new Connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

//        while (true) {
//            //MENU
//            showMenu();
//            int choice = 0;
//            try {
//                choice = Integer.parseInt(sc.nextLine());
//            } catch (NumberFormatException e) {
//                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập số.");
//                continue;
//            }
//            switch (choice){
//                case 1: // login admin
//                    break;
//
//                case 2: //login user
//                    break;
//
//                case 3:
//
//            }
//        }

        //login
//        loginCheckUserIfExist(conn, preparedStatement, resultSet);

        //insertUserByAdmin
//        insertUserByAdmin(conn, preparedStatement);

        //updateUserByAdmin
//        updateUserByAdmin(conn, preparedStatement);

        //deleteUserByAdmin
//        deleteUserByAdmin(conn, preparedStatement);

        //insertWarehouse
//        insertWarehouseByAdmin(conn,preparedStatement);

        //setUserIdToWareHouse
//        setUserIdInTBLWarehouse(conn,preparedStatement);

        //updateWarehouse
        updateWarehouseByAdmin(conn,preparedStatement);
    }

    //MENU
    private static void showMenu() {
        System.out.println("******************* MENU *******************");
        if (user == null) {
            System.out.println("1.Login.");
//            System.out.println("2.Login user.");
        }
        if (user != null && user.getRole_id() == 1) {
            System.out.println("2. User management.");
            System.out.println("3. Warehouse management.");
            System.out.println("4. Product management");
            System.out.println("5. Log out.");
        } else if (user != null && user.getRole_id() == 2) {
            System.out.println("6. View warehouse");
            System.out.println("7. Import products from excel");
        } else {
            System.out.println("0. Exit program.");
            System.out.println("========================");
            System.out.print("Choose : ");
        }
    }

    //login&checkUser
    private static void loginCheckUserIfExist(Connect conn, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            System.out.print("Enter username: ");
            String username = sc.next().trim();
            if (username.isEmpty()) {
                System.out.println("You must enter username");
                return;
            }
            System.out.print("Enter password: ");
            String password = sc.next().trim();
            if (password.isEmpty()) {
                System.out.println("You must enter password");
                return;
            }
            //encode password from input password
            String encodePassword = Base64.getEncoder().encodeToString(password.getBytes());

            StringBuilder query = new StringBuilder();
            query.append("select * ");
            query.append("from tbl_user u ");
            query.append("where u.username = ? and u.password = ?");
            conn.openConnect();
            preparedStatement = conn.getConnect().prepareStatement(query.toString());

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, encodePassword);
            resultSet = preparedStatement.executeQuery();
//            if (resultSet.isBeforeFirst()) {
////                while (resultSet.next()) {
//                    if(user.getRole_id() == 1) {
//
//                    }
//
////                }
//            }
            if (resultSet.next()) {
                //get password in database
                String passInDb = resultSet.getString(3);
                //check isEqual with input password
                if (passInDb.equals(encodePassword)) {
                    System.out.println("Hello " + resultSet.getString(2));
                }
            } else {
                System.out.println("Wrong username/password");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.closeConnect();
                if (preparedStatement != null) preparedStatement.close();
                if (resultSet != null) resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //insertUserByAdmin
    private static void insertUserByAdmin(Connect conn, PreparedStatement preparedStatement) {
        try {
            System.out.print("Enter user's username: ");
            String uName = sc.next().trim();
            System.out.print("Enter user's password: ");
            String pwd = sc.next().trim();
            String encodePwd = Base64.getEncoder().encodeToString(pwd.getBytes());
//            ArrayList<User> listUser = new ArrayList<>();
            String query = "insert into tbl_user(USERNAME,PASSWORD,ROLE_ID) values(?,?,?)";
            int result = 0;
            conn.openConnect();
            preparedStatement = conn.getConnect().prepareStatement(query);
            preparedStatement.setString(1, uName);
            preparedStatement.setString(2, encodePwd);
            preparedStatement.setInt(3, 2);
            result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Insert succeeded");
                System.out.println("-------------------------");
            } else {
                System.out.println("Insert failed");
            }
//            listUser.add(new User(uName,encodePwd,3))
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.closeConnect();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //updateUserByAdmin
    private static void updateUserByAdmin(Connect conn, PreparedStatement preparedStatement) {
        try {
            System.out.print("Enter username needs edited: ");
            String uName = sc.next().trim();
            System.out.print("Enter new username: ");
            String newName = sc.next().trim();
            System.out.print("Enter new password: ");
            String pwd = sc.next().trim();
            String encodePwd = Base64.getEncoder().encodeToString(pwd.getBytes());
//            ArrayList<User> listUser = new ArrayList<>();
            StringBuilder updateQuery = new StringBuilder();
            updateQuery.append("update tbl_user ");
            updateQuery.append("set username = ?, password = ? ");
            updateQuery.append("where username = ? ");
            int result = 0;
            conn.openConnect();
            preparedStatement = conn.getConnect().prepareStatement(updateQuery.toString());
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, encodePwd);
            preparedStatement.setString(3, uName);
            result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Update succeeded");
                System.out.println("-------------------------");
            } else {
                System.out.println("User not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.closeConnect();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //DeleteUserByAdmin -
    private static void deleteUserByAdmin(Connect conn, PreparedStatement preparedStatement) {
        try {
            System.out.print("Enter username you want to delete: ");
            String delName = sc.next().trim();
            String delQuery = "delete from tbl_user u where u.username = ? ";
            int result = 0;
            conn.openConnect();
            preparedStatement = conn.getConnect().prepareStatement(delQuery);
            preparedStatement.setString(1, delName);
            result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Delete succeeded");
            } else {
                System.out.println("Delete failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.closeConnect();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //insertWarehouse - gán User
    private static void insertWarehouseByAdmin(Connect conn, PreparedStatement preparedStatement) {
        try {
            System.out.print("Enter warehouse: ");
            String wName = sc.next().trim();
//            ArrayList<User> listUser = new ArrayList<>();
            String query = "insert into tbl_warehouse(WAREHOUSE_NAME) values(?)";
            int result = 0;
            conn.openConnect();
            preparedStatement = conn.getConnect().prepareStatement(query);
            preparedStatement.setString(1, wName);
            result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Insert succeeded");
                System.out.println("-------------------------");
            } else {
                System.out.println("Insert failed");
            }
//            listUser.add(new User(uName,encodePwd,3))
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.closeConnect();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //setUserIdInTBLWarehouse
    private static void setUserIdInTBLWarehouse(Connect conn, PreparedStatement preparedStatement) {
        try {
            System.out.print("Enter warehouse: ");
            String whName = sc.next();
            System.out.print("Enter userId you want to set to warehouse: ");
            int userId = sc.nextInt();
            StringBuilder setQuery = new StringBuilder();
            setQuery.append("update tbl_warehouse ");
            setQuery.append("set user_id = ? ");
            setQuery.append("where warehouse_name = ?");
            int result = 0;

            conn.openConnect();
            preparedStatement = conn.getConnect().prepareStatement(setQuery.toString());
            preparedStatement.setInt(1,userId);
            preparedStatement.setString(2,whName);
            result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Assign succeeded");
                System.out.println("-------------------------");
            } else {
                System.out.println("User not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.closeConnect();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //updateWarehouseByAdmin
    private static void updateWarehouseByAdmin(Connect conn, PreparedStatement preparedStatement){
        try{
            System.out.print("Enter warehouse's name needs edited: ");
            String whName = sc.next().trim();
            System.out.print("Enter new warehouse's name: ");
            String newWhName = sc.next().trim();
            StringBuilder updateQuery = new StringBuilder();
            updateQuery.append("update tbl_warehouse ");
            updateQuery.append("set warehouse_name = ? ");
            updateQuery.append(" where warehouse_name = ? ");
            int result = 0;

            conn.openConnect();
            preparedStatement = conn.getConnect().prepareStatement(updateQuery.toString());
            preparedStatement.setString(1,newWhName);
            preparedStatement.setString(2,whName);
            result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Update succeeded");
                System.out.println("-------------------------");
            } else {
                System.out.println("Update failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.closeConnect();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //DeleteWarehouseByAdmin - chuyển product sang warehouse khác
    private static void deleteWarehouseById(Connect conn, PreparedStatement preparedStatement) {
        try {
            conn.openConnect();
            System.out.print("Enter warehouse id: ");
            int whId = sc.nextInt();
            String deleteQuery = "delete rom warehouse where id = ? ";
            preparedStatement = conn.getConnect().prepareStatement(deleteQuery);
            preparedStatement.setInt(1, whId);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Delete succeeded");
            } else {
                System.out.println("Delete failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.closeConnect();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //getAllWarehouseWithoutUser
    private static void getAllWarehouseWithoutUser(Connect conn, PreparedStatement preparedStatement,ResultSet resultSet){
        try {
            String selectQuery = "select * from tbl_warehouse where user_id is null ";
            List<Warehouse> warehouseList = new ArrayList<>();
            Warehouse warehouse = null;
            conn.openConnect();
            preparedStatement = conn.getConnect().prepareStatement(selectQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                warehouse = new Warehouse();
                warehouse.setId(resultSet.getInt(1));
                warehouse.setWarehouseName(resultSet.getString(2));
                warehouseList.add(warehouse);
            }
        }catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            conn.closeConnect();
            if (preparedStatement != null) preparedStatement.close();
            if (resultSet != null) resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }
    //getAllWarehouse
    //importProductsFromExcel

}
