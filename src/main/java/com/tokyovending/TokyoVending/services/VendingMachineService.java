package com.tokyovending.TokyoVending.services;

import com.tokyovending.TokyoVending.dtos.VendingMachineDto;
import com.tokyovending.TokyoVending.models.VendingMachine;
import com.tokyovending.TokyoVending.repositories.VendingMachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendingMachineService {

    private final VendingMachineRepository vendingMachineRepository;

    @Autowired
    public VendingMachineService(VendingMachineRepository vendingMachineRepository) {
        this.vendingMachineRepository = vendingMachineRepository;
    }

    public List<VendingMachineDto> getAllVendingMachines() {
        List<VendingMachine> vendingMachines = vendingMachineRepository.findAll();
        return vendingMachines.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public VendingMachineDto getVendingMachineById(Long id) {
        VendingMachine vendingMachine = vendingMachineRepository.findById(id).orElse(null);
        if (vendingMachine != null) {
            return convertToDto(vendingMachine);
        }
        return null;
    }

    public VendingMachineDto createVendingMachine(VendingMachineDto vendingMachineDto) {
        VendingMachine vendingMachine = convertToEntity(vendingMachineDto);
        VendingMachine savedVendingMachine = vendingMachineRepository.save(vendingMachine);
        return convertToDto(savedVendingMachine);
    }

    public VendingMachineDto updateVendingMachine(Long id, VendingMachineDto vendingMachineDto) {
        VendingMachine existingVendingMachine = vendingMachineRepository.findById(id).orElse(null);
        if (existingVendingMachine != null) {
            updateEntityFromDto(existingVendingMachine, vendingMachineDto);
            VendingMachine updatedVendingMachine = vendingMachineRepository.save(existingVendingMachine);
            return convertToDto(updatedVendingMachine);
        }
        return null;
    }

    public void deleteVendingMachine(Long id) {
        vendingMachineRepository.deleteById(id);
    }

    private VendingMachineDto convertToDto(VendingMachine vendingMachine) {
        VendingMachineDto vendingMachineDto = new VendingMachineDto();
        vendingMachineDto.setId(vendingMachine.getId());
        vendingMachineDto.setLocation(vendingMachine.getLocation());
        return vendingMachineDto;
    }

    private VendingMachine convertToEntity(VendingMachineDto vendingMachineDto) {
        VendingMachine vendingMachine = new VendingMachine();
        updateEntityFromDto(vendingMachine, vendingMachineDto);
        return vendingMachine;
    }

    private void updateEntityFromDto(VendingMachine vendingMachine, VendingMachineDto vendingMachineDto) {
        vendingMachine.setLocation(vendingMachineDto.getLocation());
    }
}


