package com.driver;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WhatsappService {
    
    WhatsappRepository whatsappRepository = new WhatsappRepository();

    public String createUser(String name, String mobile) throws Exception {
       if(!whatsappRepository.createUser(name,mobile)){
           throw new Exception("User already exists");
       }
       else return "SUCCESS";
    }

    public Group createGroup(List<User> users) {
        return whatsappRepository.createGroup(users);
    }


    public int createMessage(String content) {
        return whatsappRepository.createMessage(content);
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception{

        if(whatsappRepository.sendMessage(message,sender,group) == -2 )
            throw new Exception("Group does not exist");
        else if(whatsappRepository.sendMessage(message,sender,group) == -1)
            throw new Exception("You are not allowed to send message");
        else  return whatsappRepository.sendMessage(message,sender,group);
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception{
        int a = whatsappRepository.changeAdmin(approver,user,group);
        if(a==0) throw new Exception("Group does not exist");
        else if(a==-1) throw new Exception("Approver does not have rights");
        else if(a==-2) throw new Exception("User is not a participant");
        return "SUCCESS";
    }

//    public int removeUser(User user) throws Exception  {
//        int a = whatsappRepository.removeUser(user);
//        if(a==0) throw new Exception("User not found");
//        else if(a==-1) throw new Exception("Cannot remove admin");
//        else
//    }
//
//    public String findMessage(Date start, Date end, int k) {
//
//    }
}
