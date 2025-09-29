package com.example.service.impl;

import com.example.dto.ApiResponse;
import com.example.dto.order.OrderCreateDto;
import com.example.dto.order.OrderDto;
import com.example.enums.OrderStatus;
import com.example.exp.AppBadException;
import com.example.model.Order;
import com.example.model.Vehicle;
import com.example.repository.CompanyRepository;
import com.example.repository.OrderRepository;
import com.example.repository.ProfileRepository;
import com.example.repository.VehicleRepository;
import com.example.service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProfileRepository userRepository;
    private final CompanyRepository companyRepository;
    private final VehicleRepository vehicleRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderCreateDto create(OrderCreateDto dto) {
        Order order = new Order();
        order.setShipper(userRepository.findById(dto.getShipperId())
                .orElseThrow(() -> new AppBadException("Shipper not found")));
        order.setCarrier(companyRepository.findById(dto.getCarrierId())
                .orElseThrow(() -> new AppBadException("Carrier not found")));
        order.setVehicle(vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new AppBadException("Vehicle not found")));
        order.setOrigin(dto.getOrigin());
        order.setDestination(dto.getDestination());
        order.setStatus(OrderStatus.PENDING);
        return modelMapper.map(orderRepository.save(order), OrderCreateDto.class);
    }

    @Override
    public OrderCreateDto getById(Long id) {
        return orderRepository.findById(id)
                .map(order -> modelMapper.map(order, OrderCreateDto.class))
                .orElseThrow(() -> new AppBadException("Order not found"));
    }

    @Override
    public Page<OrderDto> getAll(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(order -> modelMapper.map(order, OrderDto.class));
    }

    @Override
    public OrderCreateDto update(Long id, OrderCreateDto dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppBadException("Order not found"));
        order.setOrigin(dto.getOrigin());
        order.setDestination(dto.getDestination());
        order.setStatus(dto.getStatus());
        return modelMapper.map(orderRepository.save(order), OrderCreateDto.class);
    }

    @Override
    public ApiResponse<Boolean> delete(Long id) {
        orderRepository.deleteById(id);
        return ApiResponse.ok(true);
    }

    @Override
    public OrderDto assignDriver(Long orderId, Long vehicleId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppBadException("Order not found"));
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new AppBadException("Vehicle not found"));
        order.setVehicle(vehicle);
        return modelMapper.map(orderRepository.save(order), OrderDto.class);
    }

    @Override
    public OrderDto updateStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppBadException("Order not found"));
        order.setStatus(OrderStatus.valueOf(status));
        return modelMapper.map(orderRepository.save(order), OrderDto.class);
    }
}
