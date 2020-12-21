package ru.job4j.grabber;


import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection cnn;


    public PsqlStore(Properties cfg) throws IllegalStateException {

        try {
            Class.forName(cfg.getProperty("driver-class-name"));
            this.cnn = DriverManager.getConnection(
                    cfg.getProperty("url"),
                    cfg.getProperty("username"),
                    cfg.getProperty("password"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }


    }

    public PsqlStore() {

    }

    @Override
    public void save(Post post) {
        try (PreparedStatement statement =
                     cnn.prepareStatement("INSERT INTO post (name, text, link, created_date, created_time) VALUES (?, ?, ?, ?, ?)  ON CONFLICT (link) \n" +
                             "DO NOTHING", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getText());
            statement.setString(2, post.getDescription());
            statement.setString(3, post.getLink());
            statement.setDate(4, Date.valueOf(post.getCreatedDate()));
            statement.setTime(5, Time.valueOf(post.getCreatedTime()));
            statement.executeUpdate();
            try (ResultSet rsl = statement.getGeneratedKeys()) {
                if (rsl.next()) {
                    post.setId(rsl.getString(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() throws SQLException {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement ps = cnn.prepareStatement("select *from post;")) {
            try (ResultSet rsl = ps.executeQuery()) {
                while (rsl.next()) {
                    Post post = new Post(rsl.getString("link"),
                            rsl.getString("name"),
                            rsl.getString("text"),
                            rsl.getDate("created_date").toLocalDate(),
                            rsl.getTime("created_time").toLocalTime());
                    post.setId(String.valueOf(rsl.getInt("id")));
                    posts.add(post);
                }
            }


        }


        return posts;
    }

    @Override
    public Post findById(String id) throws SQLException {
        Post p = null;
        try (PreparedStatement ps = cnn.prepareStatement("select * from post where id = ?;")) {
            ps.setInt(1, Integer.parseInt(id));
            try (ResultSet rsl = ps.executeQuery()) {
                if (rsl.next()) {
                    p = new Post(rsl.getString("link"),
                            rsl.getString("name"),
                            rsl.getString("text"),
                            rsl.getDate("created_date").toLocalDate(),
                            rsl.getTime("created_time").toLocalTime());
                    p.setId(rsl.getString("id"));

                }

            }

            return p;
        }
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
        PsqlStore psqlStore = new PsqlStore();

        PsqlStore store = new PsqlStore(psqlStore.initProps());


        Post post = new Post("ссылка1", "название объявления1", "описание объявления1",
                LocalDate.parse("2020-08-19"), LocalTime.parse("18:05"));
        store.save(post);
        for (Post p : store.getAll()) {
            System.out.println(p.getText());
            System.out.println(p.getLink());
            System.out.println(p.getDescription());
            System.out.println(p.getCreatedDate() + " " + p.getCreatedTime());
            System.out.println("--------------------------------------------");
        }
        Post p = store.findById("1");
        System.out.println(p.getText());
        System.out.println(p.getLink());
        System.out.println(p.getDescription());
        System.out.println(p.getCreatedDate() + " " + p.getCreatedTime());
    }

    public Properties initProps() {
        Properties cfg = new Properties();
        try (InputStream in = PsqlStore.class.getClassLoader().getResourceAsStream("grabber.properties")) {
            cfg.load(in);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return cfg;
    }

}