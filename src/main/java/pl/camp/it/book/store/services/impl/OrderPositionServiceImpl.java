package pl.camp.it.book.store.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.book.store.database.IOrderPositionDAO;
import pl.camp.it.book.store.model.OrderPosition;
import pl.camp.it.book.store.services.IOrderPositionService;

import java.util.Optional;

@Service
public class OrderPositionServiceImpl implements IOrderPositionService {

    @Autowired
    IOrderPositionDAO orderPositionDAO;

    @Override
    public Optional<OrderPosition> getOrderPositionById(int id) {
        return this.orderPositionDAO.getOrderPositionById(id);
    }
}
