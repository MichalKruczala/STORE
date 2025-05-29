package pl.camp.it.book.store.database.jdbc;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.book.store.database.IOrderDAO;
import pl.camp.it.book.store.database.IOrderPositionDAO;
import pl.camp.it.book.store.model.Order;
import pl.camp.it.book.store.model.OrderPosition;
import pl.camp.it.book.store.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDAOImpl implements IOrderDAO {

    @Autowired
    Connection connection;
    @Autowired
    IOrderPositionDAO orderPositionDAO;
    @Override
    public void persistOrder(final Order order) {
        try {
            String sql = "INSERT INTO torder (user_id, date, state, total) " +
                    "VALUES (?,?,?,?)";
            PreparedStatement ps = this.connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            //ps.setInt(1, order.getUserId());
            ps.setTimestamp(2, Timestamp.valueOf(order.getDate()));
            ps.setString(3, order.getState().toString());
            ps.setDouble(4, order.getTotal());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            order.setId(rs.getInt(1));

            for(OrderPosition orderPosition : order.getPositions()) {
                this.orderPositionDAO.persistOrderPosition(orderPosition, order.getId());
            }

            /*order.getPositions()
                    .forEach(op -> this.orderPositionDAO.persistOrderPosition(op, order.getId()));*/
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateOrder(Order order) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Nie potrzebna metoda !!");
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM torder WHERE user_id = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("id");
                /*result.add(new Order(
                        orderId,
                        rs.getInt("user_id"),
                        this.orderPositionDAO.getOrderPositionByOrderId(orderId),
                        rs.getTimestamp("date").toLocalDateTime(),
                        Order.State.valueOf(rs.getString("state")),
                        rs.getDouble("total")
                ));*/
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        return Optional.empty();
    }
}
