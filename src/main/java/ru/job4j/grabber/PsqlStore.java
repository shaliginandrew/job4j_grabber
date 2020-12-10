package ru.job4j.grabber;


import org.apache.log4j.PropertyConfigurator;
import ru.job4j.quartz.AlertRabbit;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection cnn;
    Properties cfg = new Properties();


    public PsqlStore(Properties cfg) throws IllegalStateException {

        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
            this.cnn = DriverManager.getConnection(
                    cfg.getProperty("jdbc.url"),
                    cfg.getProperty("jdbc.username"),
                    cfg.getProperty("jdbc.password"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }


    }

    public PsqlStore() {

    }

    @Override
    public void save(Post post) {
        try (PreparedStatement statement =
                     cnn.prepareStatement("INSERT INTO post (name, text, link, created) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getText());
            statement.setString(2, post.getDescription());
            statement.setString(3, post.getLink());
            statement.setTimestamp(4, Timestamp.valueOf(post.getCreatedDate() + " " + post.getCreatedTime()));
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
    public List<Post> getAll() {
        return null;
    }

    @Override
    public Post findById(String id) {
        return null;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    public static void main(String[] args) {
    PsqlStore psqlStore = new PsqlStore();
    Properties cfg = psqlStore.initProps();
    PsqlStore store = new PsqlStore(cfg);





        Post post = new Post("ссылка", "название объявления", "описание объявления",
                LocalDate.parse("2020-08-19"), LocalTime.parse("18:06"));
        psqlStore.save(post);
    }


    public Properties initProps() {

        try (InputStream in = AlertRabbit.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            cfg.load(in);
            PropertyConfigurator.configure(cfg);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return cfg;
    }

}