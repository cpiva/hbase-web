package com.td.ecrr;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cloudera.cdk.hbase.data.Address;
import com.cloudera.cdk.hbase.data.Agreement;
import com.cloudera.cdk.hbase.data.Party;
import com.cloudera.cdk.hbase.data.PartyAddress;
import com.cloudera.cdk.hbase.data.PartyAgreement;
import com.cloudera.cdk.hbase.data.service.AddressDatasetService;
import com.cloudera.cdk.hbase.data.service.AgreementDatasetService;
import com.cloudera.cdk.hbase.data.service.PartyAddressDatasetService;
import com.cloudera.cdk.hbase.data.service.PartyAgreementDatasetService;
import com.cloudera.cdk.hbase.data.service.PartyDatasetService;

@Controller
public class WebController {

    //TODO: Change this to proper bean injections via spring annotations..
    PartyDatasetService partyDatasetService = new PartyDatasetService();
    AddressDatasetService addressDatasetService = new AddressDatasetService();
    PartyAddressDatasetService partyAddressDatasetService = new PartyAddressDatasetService();
    AgreementDatasetService agreementDatasetService = new AgreementDatasetService();
    PartyAgreementDatasetService partyAgreementDatasetService = new PartyAgreementDatasetService();

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

        Party party;
        List<PartyAddress> partyAddresses;
        List<Address> addresses = new ArrayList<Address>(); 
        List<PartyAgreement> partyAgreements;
        List<Agreement> agreements = new ArrayList<Agreement>(); 

        try {
            party = partyDatasetService.get(partyRequest.getId().toString());
            
            // Address
            partyAddresses = partyAddressDatasetService.scan(partyRequest.getId().toString());
            for(PartyAddress partyAddress : partyAddresses){
                addresses.add(addressDatasetService.get(partyAddress.getValue().toString()));
            }
            
            // Agreement
            partyAgreements = partyAgreementDatasetService.scan(partyRequest.getId().toString());
            for(PartyAgreement partyAgreement : partyAgreements){
                agreements.add(agreementDatasetService.get(partyAgreement.getValue().toString()));
            }
            
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
        model.addAttribute("addresses", addresses);
        model.addAttribute("agreements", agreements);

        return "results";
    }

}
