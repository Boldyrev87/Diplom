package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.DriverManager;


public class DBUtils {
    private static final String URL = System.getProperty("datasource.url");
    private static final String USERNAME = System.getProperty("datasource.username");
    private static final String PASSWORD = System.getProperty("datasource.password");

    @SneakyThrows
    public static PaymentEntity paymentEntity() {
        String sql = "SELECT * FROM payment_entity order by created desc limit 1;";
        QueryRunner runner = new QueryRunner();
        Connection conn = getConnection();
        return runner.query(conn, sql, new BeanHandler<>(PaymentEntity.class));
    }

    @SneakyThrows
    public static CreditRequestEntity creditRequestEntity() {
        String sql = "SELECT * FROM credit_request_entity order by created desc limit 1;";
        QueryRunner runner = new QueryRunner();
        Connection conn = getConnection();
        return runner.query(conn, sql, new BeanHandler<>(CreditRequestEntity.class));
    }

    @SneakyThrows
    private static Connection getConnection() {
        return DriverManager.getConnection(
                URL, USERNAME, PASSWORD
        );
    }

    @SneakyThrows
    public static void prepareDb() {
        String sql = "Delete from credit_request_entity;";
        String sql1 = "Delete from order_entity;";
        String sql2 = "Delete from payment_entity;";
        QueryRunner runner = new QueryRunner();
        Connection conn = getConnection();
        runner.execute(conn, sql);
        runner.execute(conn, sql1);
        runner.execute(conn, sql2);
    }

    @SneakyThrows
    public static void assertDbEmpty() {
        String sql = "Select count(*) from credit_request_entity;";
        String sql1 = "Select count(*) from order_entity;";
        String sql2 = "Select count(*) from payment_entity;";
        QueryRunner runner = new QueryRunner();
        Connection conn = getConnection();
        Long count0 = runner.query(conn, sql, new ScalarHandler<>());
        Assertions.assertEquals(0, count0);
        Long count1 = runner.query(conn, sql1, new ScalarHandler<>());
        Assertions.assertEquals(0, count1);
        Long count2 = runner.query(conn, sql2, new ScalarHandler<>());
        Assertions.assertEquals(0, count2);

    }
}

