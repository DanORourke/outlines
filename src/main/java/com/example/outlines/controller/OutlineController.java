package com.example.outlines.controller;

import com.example.outlines.dto.OutlineDto;
import com.example.outlines.model.Outline;
import com.example.outlines.service.OutlineService;
import com.example.outlines.service.impl.OutlineOrganization;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
//@RequestMapping("/library")
public class OutlineController {
    private final OutlineService outlineService;

    public OutlineController(OutlineService outlineService) {
        this.outlineService = outlineService;
    }
    @GetMapping
    public String getHome(){
        return "home";
    }
    @GetMapping("/library")
    public String getOutlineList(Model model){
        List<OutlineDto> outlineDtoList = outlineService.findAllTopParents();
        model.addAttribute("outlineDtoList", outlineDtoList);
        return "library";
    }

    @GetMapping("/library/{uuid}")
    public String getOutlineByUUID(Model model,  @PathVariable("uuid") UUID uuid){
        OutlineOrganization outlineOrganization = outlineService.findByTopParentId(uuid);
        if(outlineOrganization.getParent() == null) return "redirect:/library";
        model.addAttribute("outOrg", outlineOrganization);
        return "outline";
    }
    @GetMapping("/library/{uuid}/update")
    public String updateOutlineForm(Model model,  @PathVariable("uuid") UUID uuid){
        OutlineDto outlineDto = outlineService.findOutlineDtoById(uuid);
        model.addAttribute("outlineDto", outlineDto);
        return "outline-update";
    }
    @PostMapping("/library/{uuid}/update")
    public String updateOutline(@ModelAttribute("outlineDto") OutlineDto outlineDto,  @PathVariable("uuid") UUID uuid){
        Outline outline = outlineService.findOutlineById(uuid);
        outline.setTitle(outlineDto.getTitle());
        outline.setSummary(outlineDto.getSummary());
        outline.setBirthOrder(outlineDto.getBirthOrder());
        outline.setContent(outlineDto.getContent());
        outline.setContentHTML(outlineDto.getContent());
        outlineService.updateOutline(outline);
        return "redirect:/library/" + outline.getTopParentId();
    }
    @GetMapping("/library/{uuidParent}/createChild/{uuidTop}")
    public String createChildOutlineForm(Model model,  @PathVariable("uuidParent") UUID uuidParent, @PathVariable("uuidTop") UUID uuidTop){
        OutlineDto outline = new OutlineDto();
        outline.setParentId(uuidParent);
        outline.setTopParentId(uuidTop);
        model.addAttribute("outline", outline);
        return "outline-createChild";
    }
    @PostMapping("/library/{uuidParent}/createChild/{uuidTop}")
    public String createChildOutline(@ModelAttribute("outline") OutlineDto outline,
                                   @PathVariable("uuidParent") UUID uuidParent,
                                   @PathVariable("uuidTop") UUID uuidTop){
        outline.setParentId(uuidParent);
        outline.setTopParentId(uuidTop);
        outline.setIsTopParent(false);
        outline.setContentHTML(outline.getContent());
        outlineService.createOutline(outline);
        return "redirect:/library/" + outline.getTopParentId();
    }
    @GetMapping("/create")
    public String createOutlineForm(Model model){
        OutlineDto outline = new OutlineDto();
        model.addAttribute("outline", outline);
        return "outline-create";
    }

    @PostMapping("/create")
    public String createOutline(OutlineDto outline) {
        outline.setContentHTML(outline.getContent());
        outline.setBirthOrder(0);
        outline.setIsTopParent(true);
        UUID topParentId = outlineService.createTopOutline(outline);
        return "redirect:/library/" + topParentId;
    }

    @GetMapping("/library/{uuid}/delete")
    public String deleteOutline(@PathVariable("uuid") UUID outlineId){
        //todo delete and return bool in one call
        Outline outline = outlineService.findOutlineById(outlineId);
        Boolean isTopParent = outline.isTopParent();
        UUID topParentId = outline.getTopParentId();
        outlineService.delete(outline);
        if(isTopParent){
            return "redirect:/library";
        }else{
            return "redirect:/library/" + topParentId;
        }
    }
}
