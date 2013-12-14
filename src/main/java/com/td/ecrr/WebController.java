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

import com.cloudera.cdk.hbase.data.Party;
import com.cloudera.cdk.hbase.data.service.PartyDatasetService;
import com.cloudera.cdk.hbase.data.service.AddressDatasetService;
import com.cloudera.cdk.hbase.data.service.EventDatasetService;


@Controller
public class WebController {

    //TODO: Change this to proper bean injections via spring annotations..
    PartyDatasetService partyDatasetService = new PartyDatasetService();
    AddressDatasetService addressDatasetService = new AddressDatasetService();

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String showForm(PartyRequest partyRequest) {
        return "form";
    }
    
    @RequestMapping(value="/", method=RequestMethod.POST)
    public String enterId(@Valid PartyRequest partyRequest, BindingResult bindingResult, 
            RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", bindingResult.getFieldError().getDefaultMessage());
            return "redirect:/";
        }
        Party party = null;
        try {
            party = partyDatasetService.get(partyRequest.getId().toString());
            address = addressDatasetService.get(party.getAddressId())  
        }
        catch(Exception e){
            redirectAttributes.addFlashAttribute("error","An error occurred.");
            return "redirect:/";	
        }
        if (party == null){
            redirectAttributes.addFlashAttribute("error","Party id not found.");
            return "redirect:/";   
        }
        model.addAttribute("party", party);    
        return "results";
    }

}
