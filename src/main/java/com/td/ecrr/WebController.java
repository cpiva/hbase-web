package com.td.ecrr;

import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cloudera.cdk.data.Key;
import com.cloudera.cdk.data.RandomAccessDataset;
import com.cloudera.cdk.data.RandomAccessDatasetRepository;

import com.cloudera.cdk.hbase.data.service.PartyDatasetService;

@Controller
public class WebController {

    PartyDatasetService partyDatasetService = new PartyDatasetService();

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String showForm(Party party) {
        return "form";
    }
    
    @RequestMapping(value="/", method=RequestMethod.POST)
    public String enterId(@Valid Party party, BindingResult bindingResult, 
            RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", bindingResult.getFieldError().getDefaultMessage());
            return "redirect:/";
        }
        try {
          model.addAttribute("party", partyDatasetService.get(party.getId().toString()));
        }
        catch(Exception e){
            //TODO: redirect to generic error page.		
        }
        return "results";
    }

}
