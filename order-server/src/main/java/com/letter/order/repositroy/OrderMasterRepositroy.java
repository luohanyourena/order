package com.letter.order.repositroy;

import com.letter.order.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepositroy extends JpaRepository<OrderMaster,String> {
}
