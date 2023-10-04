package com.tokyovending.TokyoVending.controllers;

import com.tokyovending.TokyoVending.dtos.VendingMachineDto;
import com.tokyovending.TokyoVending.exceptions.RecordNotFoundException;
import com.tokyovending.TokyoVending.services.VendingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/vending-machines")
public class VendingMachineController {

    private final VendingMachineService vendingMachineService;

    @Autowired
    public VendingMachineController(VendingMachineService vendingMachineService) {
        this.vendingMachineService = vendingMachineService;
    }

    @GetMapping
    public ResponseEntity<List<VendingMachineDto>> getAllVendingMachines() {
        return ResponseEntity.ok(vendingMachineService.getAllVendingMachines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendingMachineDto> getVendingMachineById(@PathVariable Long id) {
        VendingMachineDto vendingMachineDto = vendingMachineService.getVendingMachineById(id);
        return ResponseEntity.ok(vendingMachineDto);
    }

    @PostMapping
    public ResponseEntity<VendingMachineDto> createVendingMachine(@Valid @RequestBody VendingMachineDto vendingMachineDto) {
        VendingMachineDto createdVendingMachineDto = vendingMachineService.createVendingMachine(vendingMachineDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVendingMachineDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendingMachineDto> updateVendingMachine(@PathVariable Long id, @Valid @RequestBody VendingMachineDto vendingMachineDto) {
        VendingMachineDto updatedVendingMachineDto = vendingMachineService.updateVendingMachine(id, vendingMachineDto);
        if (updatedVendingMachineDto == null) {
            throw new RecordNotFoundException("VendingMachine with ID " + id + " not found.");
        }
        return ResponseEntity.ok(updatedVendingMachineDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendingMachine(@PathVariable Long id) {
        vendingMachineService.deleteVendingMachine(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{vmId}/add-product/{productId}")
    public ResponseEntity<VendingMachineDto> addProductToVendingMachine(@PathVariable Long vmId, @PathVariable Long productId) {
        VendingMachineDto updatedVendingMachineDto = vendingMachineService.addProductToVendingMachine(vmId, productId);
        return ResponseEntity.ok(updatedVendingMachineDto);
    }
}

