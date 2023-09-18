package com.tokyovending.TokyoVending.services;

import com.tokyovending.TokyoVending.exceptions.RecordNotFoundException;
import com.tokyovending.TokyoVending.models.VendingMachine;
import com.tokyovending.TokyoVending.repositories.VendingMachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendingMachineService {

    private final VendingMachineRepository vendingMachineRepository;

    @Autowired
    public VendingMachineService(VendingMachineRepository vendingMachineRepository) {
        this.vendingMachineRepository = vendingMachineRepository;
    }

    public List<VendingMachine> getAllVendingMachines() {
        return vendingMachineRepository.findAll();
    }

    public VendingMachine getVendingMachineById(Long id) {
        return vendingMachineRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("VendingMachine with ID " + id + " not found."));
    }

    public VendingMachine createVendingMachine(VendingMachine vendingMachine) {
        return vendingMachineRepository.save(vendingMachine);
    }

    public VendingMachine updateVendingMachine(Long id, VendingMachine vendingMachine) {
        VendingMachine existingVendingMachine = getVendingMachineById(id);
        existingVendingMachine.setLocation(vendingMachine.getLocation());
        return vendingMachineRepository.save(existingVendingMachine);
    }

    public void deleteVendingMachine(Long id) {
        vendingMachineRepository.deleteById(id);
    }
}

