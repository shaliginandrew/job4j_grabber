package ru.job4j.quartz;

import org.apache.log4j.PropertyConfigurator;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    private Properties props;
    private Connection cn;


    public AlertRabbit(Properties props, Connection cn) {
        this.props = props;
        this.cn = cn;

    }

    public Properties initProps() {

        try (InputStream in = AlertRabbit.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            props.load(in);
            PropertyConfigurator.configure(props);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public void timer(Connection cn) {

        try {

            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("connection", cn);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(Integer.parseInt(props.getProperty("rabbit.interval")))
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(6000);
            scheduler.shutdown();

        } catch (Exception se) {
            se.printStackTrace();
        }

    }


    public Connection initConnect(Properties props) throws ClassNotFoundException, SQLException {
        try {
            Class.forName(props.getProperty("driver-class-name"));
            cn = DriverManager.getConnection(
                    props.getProperty("url"),
                    props.getProperty("username"),
                    props.getProperty("password")
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cn;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        AlertRabbit rabbit = new AlertRabbit(new Properties(), null);
        Properties properties = rabbit.initProps();
        try (Connection connection = rabbit.initConnect(properties)) {
            rabbit.timer(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static class Rabbit implements Job {

        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            Connection connection = (Connection) context.getJobDetail().getJobDataMap().get("connection");


            try (PreparedStatement ps = connection.prepareStatement("insert into rabbit (created) values (?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                ps.execute();
                ResultSet keys = ps.getGeneratedKeys();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}