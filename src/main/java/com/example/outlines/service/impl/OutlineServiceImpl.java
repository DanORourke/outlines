package com.example.outlines.service.impl;

import com.example.outlines.dto.OutlineDto;
import com.example.outlines.model.Outline;
import com.example.outlines.repository.OutlineRepository;
import com.example.outlines.service.OutlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OutlineServiceImpl implements OutlineService {
    private OutlineRepository outlineRepository;
    @Autowired
    public OutlineServiceImpl(OutlineRepository outlineRepository) {
        this.outlineRepository = outlineRepository;
    }

    @Override
    public OutlineDto findByTitle(String title){
        return mapToOutlineDto(outlineRepository.findByTitle(title));
    }
    @Override
    public OutlineDto findOutlineDtoById(UUID uuid){
        Optional<Outline> outline = outlineRepository.findById(uuid);
        return outline.map(this::mapToOutlineDto).orElse(null);
    }
    @Override
    public Outline findOutlineById(UUID uuid){
        Optional<Outline> outline = outlineRepository.findById(uuid);
        return outline.orElse(null);
    }
    @Override
    public void createOutline(Outline outline){
        outlineRepository.save(outline);
    }
    @Override
    public void createOutline(OutlineDto outlineDto){
        outlineRepository.save(mapToOutline(outlineDto));
    }

    @Override
    public void updateOutlineDto(OutlineDto outlineDto) {
        outlineRepository.save(mapToOutline(outlineDto));
    }
    @Override
    public void updateOutline(Outline outline) {
        outlineRepository.save(outline);
    }

    @Override
    public List<OutlineDto> findAll(){
        List<Outline> outlineDtoList = outlineRepository.findAll();
        return outlineDtoList.stream().map(this::mapToOutlineDto).collect(Collectors.toList());
    }

    @Override
    public OutlineOrganization findByTopParentId(UUID uuid) {
        List<Outline> descendants = outlineRepository.findByTopParentId(uuid);
        List<OutlineDto> descendantsDto = descendants.stream().map(this::mapToOutlineDto).toList();
        return new OutlineOrganization(descendantsDto, uuid);
    }

    @Override
    public List<OutlineDto> findAllTopParents() {
        List<Outline> topParents = outlineRepository.findByIsTopParent(true);
        return topParents.stream().map(this::mapToOutlineDto).toList();
    }

    @Override
    public void delete(Outline outline) {
        outlineRepository.delete(outline);
    }

    @Override
    public UUID createTopOutline(OutlineDto outlineDto) {
        //todo do this better
        Outline outline = mapToOutline(outlineDto);
        createOutline(outline);
        outline.setTopParentId(outline.getId());
        updateOutline(outline);
        return outline.getId();
    }

    public Outline mapToOutline(OutlineDto outlineDto){
        if(outlineDto == null) return null;
        Outline outline = Outline.builder()
                .id(outlineDto.getId())
                .title(outlineDto.getTitle())
                .compressedTitle(outlineDto.getCompressedTitle())
                .summary(outlineDto.getSummary())
                .content(outlineDto.getContent())
                .contentHTML(outlineDto.getContentHTML())
                .birthOrder(outlineDto.getBirthOrder())
                .parent(outlineDto.getParent())
                .parentId(outlineDto.getParentId())
                .topParentId(outlineDto.getTopParentId())
                .isTopParent(outlineDto.isTopParent())
                .build();
        return outline;
    }

    private OutlineDto mapToOutlineDto(Outline outline){
        if(outline == null) return null;
        OutlineDto outlineDto = OutlineDto.builder()
                .id(outline.getId())
                .title(outline.getTitle())
                .compressedTitle(outline.getCompressedTitle())
                .summary(outline.getSummary())
                .content(outline.getContent())
                .contentHTML(outline.getContentHTML())
                .birthOrder(outline.getBirthOrder())
                .parent(outline.getParent())
                .parentId(outline.getParentId())
                .topParentId(outline.getTopParentId())
                .isTopParent(outline.isTopParent())
                .build();
        return outlineDto;
    }
}
