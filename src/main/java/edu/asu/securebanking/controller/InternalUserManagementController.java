package edu.asu.securebanking.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.securebanking.businessobject.CreateInternalAccountBO;
import edu.asu.securebanking.businessobject.InternalCriticalTransactionBO;
import edu.asu.securebanking.hibernateexception.InternalException;
import edu.asu.securebanking.model.InternalAccount;
import edu.asu.securebanking.model.InternalCriticalTransaction;

@Controller
@RequestMapping("/internalUserManagement")
public class InternalUserManagementController {

	@Autowired
	CreateInternalAccountBO intBo;
	
	@Autowired
	InternalCriticalTransactionBO intCriticalTrans;

	
	@RequestMapping(method = RequestMethod.GET)
	public synchronized ModelAndView initForm(ModelMap model, Principal principal, HttpSession session){
		session.setAttribute("passkeycheck", "true");
		ModelAndView modelAndView = new ModelAndView("internalUserManagement");
		List<InternalCriticalTransaction> criticaltrans = intCriticalTrans.getdetails();
		modelAndView.addObject("criticaltrans", criticaltrans);
		
		InternalCriticalTransaction criticaltran = new InternalCriticalTransaction();
		modelAndView.addObject("criticaltran", criticaltran);
		
		InternalAccount user = new InternalAccount();
		modelAndView.addObject("user", user);
		
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);
		modelAndView.addObject("currentuser", currentuser);
		
		return modelAndView;
	}

	@RequestMapping(value="/displayusers")
	public synchronized ModelAndView listOfUsers(@ModelAttribute("currentuser") InternalAccount currentuser,
			BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("listallinternalusers");
		List<InternalAccount> users = intBo.getAllInternalUserAccounts();
		System.out.println(users.listIterator());
		modelAndView.addObject("users", users);
		modelAndView.addObject("currentuser", currentuser);
		return modelAndView;
	}
	
	@RequestMapping(value="/finduser", method = RequestMethod.POST)
	public synchronized ModelAndView finduserbyid(@ModelAttribute("user") InternalAccount user,
			BindingResult result) throws InternalException {
		ModelAndView modelAndView = new ModelAndView("findInternalUser");
		InternalAccount userbyid = intBo.findUserByid(user.getEmployeeId());
		System.out.println(userbyid.getUsername());
		modelAndView.addObject("userbyid", userbyid);
		return modelAndView;
	}
	
	@RequestMapping(value="/deleteinternaluser")
	public synchronized ModelAndView deleteuserbyid(@ModelAttribute("user") InternalAccount user,
			BindingResult result, Principal principal) throws InternalException{
		
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);
		
		if(currentuser.getEmployeeId() == user.getEmployeeId()){
			String message = "A Manager cannot delete himself!";
			ModelAndView modelAndView = new ModelAndView("internalmgmtsuccessmsg");
			modelAndView.addObject("message", message);
			return modelAndView;
		} else{
			
		
		ModelAndView modelAndView = new ModelAndView("deleteInternalUser");
		InternalAccount userbyid = intBo.findUserByidanddeptid(user.getEmployeeId(), user.getDeptid());
		System.out.println(userbyid.getUsername());
		modelAndView.addObject("userbyid", userbyid);
		return modelAndView;
		}
	}
	
	@RequestMapping(value="/internalcriticaltransaction")
	public synchronized ModelAndView internalcriticaltransqueue(@ModelAttribute("userbyid") InternalAccount userbyid,
			BindingResult result, Principal principal) throws InternalException{
		
		String name = principal.getName();
		String ceo = "CEO";
		String pres = "PRESIDENT";
		String vpres = "VICE-PRESIDENT";
		
		InternalAccount user = intBo.findUserByusername(name);
		System.out.println("User name is: "+user.getFirstname());
		System.out.println("User Role is: "+user.getPosition());
		
		ModelAndView modelAndView = new ModelAndView("internalmgmtsuccessmsg");
		InternalCriticalTransaction internalCriticalTransaction = new InternalCriticalTransaction();
		internalCriticalTransaction.setRequest("Delete User");
		internalCriticalTransaction.setEmployeeId(userbyid.getEmployeeId());
		internalCriticalTransaction.setDeptid(userbyid.getDeptid());
		if(user.getPosition().equals(ceo)){
			System.out.println("User Role is matched: "+user.getPosition());
			internalCriticalTransaction.setCeoapp("yes");
			internalCriticalTransaction.setPresapp("no");
			internalCriticalTransaction.setVpresapp("no");
		}
		if(user.getPosition().equals(pres)){
			internalCriticalTransaction.setCeoapp("no");
			internalCriticalTransaction.setPresapp("yes");
			internalCriticalTransaction.setVpresapp("no");
		}
		if(user.getPosition().equals(vpres)){
			internalCriticalTransaction.setCeoapp("no");
			internalCriticalTransaction.setPresapp("no");
			internalCriticalTransaction.setVpresapp("yes");
		}

		intCriticalTrans.save(internalCriticalTransaction);

		System.out.println("Critical Transaction inserted!");
		String msg = "Critical Transaction inserted Successfully!";
		modelAndView.addObject("message", msg);
		return modelAndView;
	}
	
	@RequestMapping(value="/approveinternalcriticaltransaction")
	public synchronized ModelAndView approveinternalcriticaltransaction(@ModelAttribute("criticaltran") InternalCriticalTransaction criticaltran,
			BindingResult result, Principal principal) {
		
		String name = principal.getName();
		String ceo = "CEO";
		String pres = "PRESIDENT";
		String vpres = "VICE-PRESIDENT";
		
		InternalAccount user = intBo.findUserByusername(name);
		System.out.println("User name is: "+user.getFirstname());
		System.out.println("User Role is: "+user.getPosition());
		System.out.println("Emp Id is: "+criticaltran.getEmployeeId());
		
		InternalCriticalTransaction internalCriticalTransaction = new InternalCriticalTransaction();
		
		if(user.getPosition().equals(ceo)){
			System.out.println("User Role is matched: "+user.getPosition());
			internalCriticalTransaction = intCriticalTrans.updatebyempidfromceo(criticaltran.getEmployeeId(), "yes");
		}
		if(user.getPosition().equals(pres)){
			System.out.println("User Role is matched: "+user.getPosition());
			internalCriticalTransaction = intCriticalTrans.updatebyempidfrompresident(criticaltran.getEmployeeId(), "yes");
		}
		if(user.getPosition().equals(vpres)){
			System.out.println("User Role is matched: "+user.getPosition());
			internalCriticalTransaction = intCriticalTrans.updatebyempidfromvpresident(criticaltran.getEmployeeId(), "yes");
		}
		
		String yes = "yes";
		
		if(internalCriticalTransaction.getPresapp().equals(internalCriticalTransaction.getCeoapp()) && internalCriticalTransaction.getCeoapp().equals(internalCriticalTransaction.getVpresapp()) && internalCriticalTransaction.getVpresapp().equals(yes)){
			ModelAndView modelAndView = new ModelAndView("internalmgmtsuccessmsg");
			intBo.delete(criticaltran.getEmployeeId());
			System.out.println(criticaltran.getEmployeeId()+", deleted!");
			String msg = "All approval completed! Internal User with Employee ID " +criticaltran.getEmployeeId()+ "deleted Successfully!";
			modelAndView.addObject("message", msg);
			return modelAndView;
		} else{
			ModelAndView modelAndView = new ModelAndView("internalmgmtsuccessmsg");
			System.out.println(criticaltran.getEmployeeId()+", deleted!");
			String msg = "Approved critical transaction for Employee ID " +criticaltran.getEmployeeId()+ "!";
			modelAndView.addObject("message", msg);
			return modelAndView;
		}
		
	}
	
	@RequestMapping(value="/deleteuser")
	public synchronized ModelAndView confirmdeleteuserbyid(@ModelAttribute("userbyid") InternalAccount userbyid,
			BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("internalmgmtsuccessmsg");
		intBo.delete(userbyid.getEmployeeId());
		System.out.println(userbyid.getEmployeeId()+", deleted!");
		String msg = "Internal User with Employee ID " +userbyid.getEmployeeId()+ "deleted Successfully!";
		modelAndView.addObject("message", msg);
		return modelAndView;
	}
	
	@RequestMapping(value="/transferuser", method = RequestMethod.POST)
	public synchronized ModelAndView transferuser(@ModelAttribute("user") InternalAccount user,
			BindingResult result, Principal principal) throws InternalException {
		
		String name = principal.getName();
		InternalAccount currentuser = intBo.findUserByusername(name);
		
		if(currentuser.getEmployeeId() == user.getEmployeeId()){
			String message = "A Manager cannot transfer himself to another department!";
			ModelAndView modelAndView = new ModelAndView("internalmgmtsuccessmsg");
			modelAndView.addObject("message", message);
			return modelAndView;
		} else{
		
		ModelAndView modelAndView = new ModelAndView("internalmgmtsuccessmsg");
		intBo.transferinternaluser(user.getEmployeeId(), user.getDeptid());
		
		String msg = "Internal User with Employee ID " +user.getEmployeeId()+ " transfered to Department ID " + user.getDeptid() + " Successfully!";
		System.out.println(msg);
		modelAndView.addObject("message", msg);
		return modelAndView;
		}
	}

}
