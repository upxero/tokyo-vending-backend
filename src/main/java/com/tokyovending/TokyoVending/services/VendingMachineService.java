package com.tokyovending.TokyoVending.services;

import com.tokyovending.TokyoVending.dtos.VendingMachineDto;
import com.tokyovending.TokyoVending.models.Product;
import com.tokyovending.TokyoVending.repositories.ProductRepository;
import com.tokyovending.TokyoVending.exceptions.RecordNotFoundException;
import com.tokyovending.TokyoVending.exceptions.EntityNotFoundException;
import com.tokyovending.TokyoVending.models.VendingMachine;
import com.tokyovending.TokyoVending.repositories.VendingMachineRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendingMachineService {

    @Autowired
    private VendingMachineRepository vendingMachineRepository;
    @Autowired
    private ProductRepository productRepository;


    public List<VendingMachineDto> getAllVendingMachines() {
        List<VendingMachine> vendingMachines = vendingMachineRepository.findAll();
        return vendingMachines.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public VendingMachineDto getVendingMachineById(Long id) {
        VendingMachine vendingMachine = vendingMachineRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("VendingMachine with ID " + id + " not found.")
        );
        vendingMachine.getProducts().size();
        return convertToDto(vendingMachine);
    }

    public VendingMachineDto createVendingMachine(VendingMachineDto vendingMachineDto) {
        VendingMachine vendingMachine = convertToEntity(vendingMachineDto);
        VendingMachine savedVendingMachine = vendingMachineRepository.save(vendingMachine);
        return convertToDto(savedVendingMachine);
    }

    public VendingMachineDto updateVendingMachine(Long id, VendingMachineDto vendingMachineDto) {
        VendingMachine existingVendingMachine = vendingMachineRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("VendingMachine with ID " + id + " not found.")
        );
        updateEntityFromDto(existingVendingMachine, vendingMachineDto);
        VendingMachine updatedVendingMachine = vendingMachineRepository.save(existingVendingMachine);
        return convertToDto(updatedVendingMachine);
    }

    public void deleteVendingMachine(Long id) {
        if (!vendingMachineRepository.existsById(id)) {
            throw new RecordNotFoundException("VendingMachine with ID " + id + " not found.");
        }
        vendingMachineRepository.deleteById(id);
    }

    private VendingMachineDto convertToDto(VendingMachine vendingMachine) {
        VendingMachineDto vendingMachineDto = new VendingMachineDto();
        vendingMachineDto.setId(vendingMachine.getId());
        vendingMachineDto.setLocation(vendingMachine.getLocation());
        vendingMachineDto.setOpen(vendingMachine.isOpen());
        vendingMachineDto.setProducts(vendingMachine.getProducts());
        return vendingMachineDto;
    }

    private VendingMachine convertToEntity(VendingMachineDto vendingMachineDto) {
        VendingMachine vendingMachine = new VendingMachine();
        updateEntityFromDto(vendingMachine, vendingMachineDto);
        return vendingMachine;
    }

    private void updateEntityFromDto(VendingMachine vendingMachine, VendingMachineDto vendingMachineDto) {
        vendingMachine.setLocation(vendingMachineDto.getLocation());
        vendingMachine.setOpen(vendingMachineDto.isOpen());
    }

    public VendingMachineDto addProductToVendingMachine(Long vmId, Long productId) {
        VendingMachine vendingMachine = vendingMachineRepository.findById(vmId)
                .orElseThrow(() -> new EntityNotFoundException("VendingMachine not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        product.setVendingMachine(vendingMachine);
        vendingMachine.getProducts().add(product);

        VendingMachine updatedVendingMachine = vendingMachineRepository.save(vendingMachine);
        return convertToDto(updatedVendingMachine);
    }

}


