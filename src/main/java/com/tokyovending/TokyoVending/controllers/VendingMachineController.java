package com.tokyovending.TokyoVending.controllers;

import com.tokyovending.TokyoVending.dtos.VendingMachineDto;
import com.tokyovending.TokyoVending.exceptions.RecordNotFoundException;
import com.tokyovending.TokyoVending.models.VendingMachine;
import com.tokyovending.TokyoVending.services.VendingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
        List<VendingMachine> vendingMachines = vendingMachineService.getAllVendingMachines();
        List<VendingMachineDto> vendingMachineDtos = vendingMachines.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(vendingMachineDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendingMachineDto> getVendingMachineById(@PathVariable Long id) {
        VendingMachine vendingMachine = vendingMachineService.getVendingMachineById(id);
        if (vendingMachine != null) {
            return ResponseEntity.ok(convertToDto(vendingMachine));
        } else {
            throw new RecordNotFoundException("VendingMachine with ID " + id + " not found.");
        }
    }

    @PostMapping
    public ResponseEntity<VendingMachineDto> createVendingMachine(@Valid @RequestBody VendingMachineDto vendingMachineDto) {
        VendingMachine vendingMachine = convertToEntity(vendingMachineDto);
        VendingMachine createdVendingMachine = vendingMachineService.createVendingMachine(vendingMachine);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdVendingMachine));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendingMachineDto> updateVendingMachine(@PathVariable Long id, @Valid @RequestBody VendingMachineDto vendingMachineDto) {
        VendingMachine vendingMachine = convertToEntity(vendingMachineDto);
        VendingMachine updatedVendingMachine = vendingMachineService.updateVendingMachine(id, vendingMachine);
        if (updatedVendingMachine != null) {
            return ResponseEntity.ok(convertToDto(updatedVendingMachine));
        } else {
            throw new RecordNotFoundException("VendingMachine with ID " + id + " not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendingMachine(@PathVariable Long id) {
        vendingMachineService.deleteVendingMachine(id);
        return ResponseEntity.noContent().build();
    }

    private VendingMachineDto convertToDto(VendingMachine vendingMachine) {
        VendingMachineDto vendingMachineDto = new VendingMachineDto();
        vendingMachineDto.setId(vendingMachine.getId());
        vendingMachineDto.setLocation(vendingMachine.getLocation());
        return vendingMachineDto;
    }

    private VendingMachine convertToEntity(VendingMachineDto vendingMachineDto) {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.setId(vendingMachineDto.getId());
        vendingMachine.setLocation(vendingMachineDto.getLocation());
        return vendingMachine;
    }
}

