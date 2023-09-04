package lk.ijse.orm.hibernate_project.dao.custom.impl;

import lk.ijse.orm.hibernate_project.dao.custom.ReservationDAO;
import lk.ijse.orm.hibernate_project.entities.Reservation;
import org.hibernate.Session;

public class ReservationDaoImpl implements ReservationDAO {

    private Session session;

    public ReservationDaoImpl() {
    }

    @Override
    public Integer Save(Reservation reservationEntity) {
        return (int) session.save(reservationEntity);
    }

    @Override
    public Reservation Get(Integer reservation_id) {
        return session.get(Reservation.class, reservation_id);
    }

    @Override
    public void Update(Reservation reservationEntity) {
        session.update(reservationEntity);
    }

    @Override
    public void Delete(Reservation reservationEntity) {
        session.delete(reservationEntity);
    }

    @Override
    public void SetSession(Session session) {
        this.session = session;
    }
}