package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Solution {

    private int id;
    private String created;
    private String updated;
    private String description;
    private User user;
    private Exercise exercise;

    public Solution() {
    }

    public Solution(String created, String updated, String description, User user, Exercise exercise) {
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.user = user;
        this.exercise = exercise;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public void saveToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String sql = "INSERT INTO solution(created, updated, description, users_id, exercise_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement
                    = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);//z automatu wyciaga pierwsza kolumne z tabeli
            preparedStatement.setString(1, this.created);
            preparedStatement.setString(2, this.updated);
            preparedStatement.setString(3, this.description);
            preparedStatement.setInt(4, this.user.getId());
            preparedStatement.setInt(5, this.exercise.getId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } else {
            String sql = "UPDATE solution SET created=?, updated=?, description=?, users_id=?, exercise_id=? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, this.created);
            preparedStatement.setString(2, this.updated);
            preparedStatement.setString(3, this.description);
            preparedStatement.setInt(4, this.user.getId());
            preparedStatement.setInt(5, this.exercise.getId());
            preparedStatement.setInt(6, this.id);
            preparedStatement.executeUpdate();
        }
    }

    static public Solution loadSolutionById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM solution where id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) { //dajemy tutaj if a nie while tak jak zawsze bo potrzebujemy tylko wczytac 1 usera
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getString("created");
            loadedSolution.updated = resultSet.getString("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.user = User.loadUserById(conn, loadedSolution.getUser().getId());
            loadedSolution.exercise = Exercise.loadExerciseById(conn, loadedSolution.getExercise().getId());

            return loadedSolution;
        }
        return null;
    }

    static public ArrayList<Solution> loadAllSolutions(Connection conn) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getString("created");
            loadedSolution.updated = resultSet.getString("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.user = User.loadUserById(conn, loadedSolution.getUser().getId());
            loadedSolution.exercise = Exercise.loadExerciseById(conn, loadedSolution.getExercise().getId());
            solutions.add(loadedSolution);
        }
        return solutions;
    }

    static public ArrayList<Solution> loadAllByUserId(Connection conn, int id) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution JOIN users ON solution.users_id = users.id WHERE users.id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        Solution solution1 = new Solution();
        preparedStatement.setInt(1, solution1.user.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getString("created");
            loadedSolution.updated = resultSet.getString("updated");
            loadedSolution.description = resultSet.getString("descrption");
            solutions.add(loadedSolution);
        }

        return solutions;
    }

    static public ArrayList<Solution> loadAllByExerciseId(Connection conn) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution JOIN exercise ON solution.exercise_id = exercise.id WHERE exercise.id = ? ORDER BY solution.created ASC";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        Solution solution1 = new Solution();
        preparedStatement.setInt(1, solution1.exercise.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getString("created");
            loadedSolution.updated = resultSet.getString("updated");
            loadedSolution.description = resultSet.getString("description");
            solutions.add(loadedSolution);
        }
        return solutions;
    }

    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM solution WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }

    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user + '\'' +
                ", exercise=" + exercise + '\'' +
                '}';
    }

}
