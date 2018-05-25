package com.tmobile.adstand.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OptionDTO {
    private int id;
    private int payment;
    private int price;
    private String description;
    private String name;
    boolean compatible;

    List<Integer> compatibleOptions = new ArrayList<>();
    List<Integer> requiredOptions = new ArrayList<>();

    public List<Integer> getCompatibleOptions() {
        return compatibleOptions;
    }

    public void setCompatibleOptions(List<Integer> optionsIds) {
        compatibleOptions = optionsIds;
    }

    public void addCompatibleOption(int id) {
        compatibleOptions.add(id);
    }

    public List<Integer> getRequiredOptions() {
        return requiredOptions;
    }

    public void setRequiredOptions(List<Integer> optionsIds) {
        requiredOptions = optionsIds;
    }

    public void addRequiredOptions(int id) {
        requiredOptions.add(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompatible() {
        return compatible;
    }

    public void setCompatible(boolean compatible) {
        this.compatible = compatible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OptionDTO)) return false;
        OptionDTO optionDTO = (OptionDTO) o;
        return id == optionDTO.id &&
                payment == optionDTO.payment &&
                price == optionDTO.price &&
                compatible == optionDTO.compatible &&
                Objects.equals(description, optionDTO.description) &&
                Objects.equals(name, optionDTO.name) &&
                Objects.equals(compatibleOptions, optionDTO.compatibleOptions) &&
                Objects.equals(requiredOptions, optionDTO.requiredOptions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, payment, price, description, name, compatible, compatibleOptions, requiredOptions);
    }
}
