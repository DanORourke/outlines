package com.example.outlines.dto;

import com.example.outlines.model.Outline;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutlineDto {
    private UUID id;
    private String title;
    private String compressedTitle;
    private String summary;
    private String content;
    private String contentHTML;
    //need to hold Outlines, not dto's to trade back and forth, something is wrong here
    private Outline parent;
    private UUID parentId;
    private Integer birthOrder;
    private UUID topParentId;
    private Boolean isTopParent;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    public Boolean isTopParent(){
        return isTopParent;
    }

    public String toString(){
        return title;
    }
}
