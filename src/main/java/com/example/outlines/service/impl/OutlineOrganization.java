package com.example.outlines.service.impl;

import com.example.outlines.dto.OutlineDto;
import com.example.outlines.model.Outline;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutlineOrganization {
    OutlineDto parent;
    List<OutlineOrganization> children = new ArrayList<>();

    public OutlineOrganization(List<OutlineDto> outlineDtoList, UUID topParent){
        if(topParent != null) generateFromList(outlineDtoList, topParent);
    }
    public OutlineOrganization(HashMap<UUID, List<OutlineDto>> childrenMap, OutlineDto parent){
        this.parent = parent;
        if(childrenMap.containsKey(parent.getId())){
            for(OutlineDto child : childrenMap.get(parent.getId())){
                children.add(new OutlineOrganization(childrenMap, child));
            }
        }
        children.sort(Comparator.comparingInt(o -> o.parent.getBirthOrder()));
    }

    private void generateFromList(List<OutlineDto> outlineDtoList, UUID topParent){
        //System.out.println(outlineDtoList);
        HashMap<UUID, List<OutlineDto>> childrenMap = new HashMap<>();
        for(OutlineDto outlineDto: outlineDtoList){
            //System.out.println(outlineDto.getId() + " " + outlineDto.getParent());
            if(outlineDto.getId().equals(topParent)){
                parent = outlineDto;
            }
            else{
                List<OutlineDto> list = childrenMap.getOrDefault(outlineDto.getParentId(), new ArrayList<>());
                list.add(outlineDto);
                childrenMap.put(outlineDto.getParentId(), list);
            }
        }
        //System.out.println(childrenMap);
        if(parent == null){
            return;
        }
        //System.out.println(parent.getChildren());
        if(childrenMap.containsKey(topParent)){
            for(OutlineDto child : childrenMap.get(topParent)){
                children.add(new OutlineOrganization(childrenMap, child));
            }
        }
        children.sort(Comparator.comparingInt(o -> o.parent.getBirthOrder()));
        //System.out.println(children);
    }
}
