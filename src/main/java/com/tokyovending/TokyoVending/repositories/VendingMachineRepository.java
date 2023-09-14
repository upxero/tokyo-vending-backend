package com.tokyovending.TokyoVending.repositories;

import com.tokyovending.TokyoVending.models.VendingMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendingMachineRepository extends JpaRepository<VendingMachine, Long> {
}

