package com.example.outlines.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Outline")
public class Outline {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String compressedTitle;
    @Column(columnDefinition = "TEXT")
    private String summary;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column(columnDefinition = "TEXT")
    private String contentHTML;
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Outline parent;
    @Column(name = "parent_id", columnDefinition = "UUID")
    private UUID parentId;
    private Integer birthOrder;
    @Column(columnDefinition = "UUID")
    private UUID topParentId;
    private Boolean isTopParent;
    @OneToMany(mappedBy = "outline")
    private Set<Citation> citationSet;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;


    public String toString(){
        return title;
    }

    //TODO why is lombok @Data not working for these?
    public Boolean isTopParent(){
        return isTopParent;
    }

    public void setIsTopParent(boolean isTopParent){
        this.isTopParent = isTopParent;
    }
}
