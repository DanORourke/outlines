package com.example.outlines.service;

import com.example.outlines.dto.OutlineDto;
import com.example.outlines.model.Outline;
import com.example.outlines.service.impl.OutlineOrganization;

import java.util.List;
import java.util.UUID;

public interface OutlineService {
    OutlineDto findByTitle(String title);
    OutlineDto findOutlineDtoById(UUID uuid);
    Outline findOutlineById(UUID uuid);
    void createOutline(Outline outline);

    void createOutline(OutlineDto outlineDto);

    void updateOutlineDto(OutlineDto outlineDto);
    void updateOutline(Outline outline);
    List<OutlineDto> findAll();
    OutlineOrganization findByTopParentId(UUID uuid);
    List<OutlineDto> findAllTopParents();
    void delete(Outline outline);
    UUID createTopOutline(OutlineDto outline);
}
