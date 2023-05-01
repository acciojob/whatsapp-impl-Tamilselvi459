package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private int customGroupCount;
    private  int messageId;
    private HashMap<String ,User> hm;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
        this.hm = new HashMap<>();
    }



    public boolean createUser(String name, String mobile) {
        if(hm.containsKey(mobile)){
            return false;
        }
        else{
            User us = new User(name , mobile);
            hm.put(mobile , us);
            return true;
        }
    }

    public Group createGroup(List<User> users) {
        List<Message> list = new ArrayList<>();
        if(users.size()==2){
            Group group = new Group(users.get(1).getName() , 2);
            groupUserMap.put(group , users);
            adminMap.put(group , users.get(0));
            return group;

        }else{
            customGroupCount++;
            String name = "Group "+customGroupCount;
            Group group = new Group(name,users.size());
            groupUserMap.put(group,users);
            adminMap.put(group,users.get(0));
            return group;
        }
    }

    public int createMessage(String content) {
        messageId = messageId+1;
        Message message = new Message(messageId , content);
        return messageId;
    }

    public int sendMessage(Message message, User sender, Group group) {

//        if(groupUserMap.containsKey(group)){
//            List<User> us =  groupUserMap.get(group);
//            for(User ua: us){
//                if(ua.getName().equals(sender.getName()) ) {
//
//
//                    List<Message> mess = new ArrayList<>();
//                    mess.add(message);
//                    groupMessageMap.put(group, mess);
//                    return mess.size();
//
//                }
//                }
//
//             return -1;
//        }
//        return -2;
        if(adminMap.containsKey(group)){
            List<User> us = groupUserMap.get(group);
            for(User user :us){
                if(user.equals(sender)){
                    List<Message> mess ;
                    if(groupMessageMap.containsKey(group))
                    {
                        mess = groupMessageMap.get(group);
                    }
                    else mess = new ArrayList<>();
                    mess.add(message);
                    groupMessageMap.put(group,mess);
                    return mess.size();
                }
            }
            return -1;
        }
        return -2;
    }

    public int changeAdmin(User approver, User user, Group group) {
        if(groupUserMap.containsKey(group)){
            if(adminMap.containsKey(group)){
                User us = adminMap.get(group);
                if(us.getName().equals(approver.getName()) && us.getMobile().equals(approver.getMobile())){
                   List<User> list = groupUserMap.get(group);
                   for(User as : list){
                       if(as.getName().equals(user.getName()) && as.getMobile().equals(as.getMobile())){
                           adminMap.put(group,user);
                           return 1;
                   }
                   }
                   return -2;
                }
              else   return -1;
            }

        }
        return 0;

    }

//    public int removeUser(User user) {
//        for(Group group : groupUserMap.keySet())
//        {
//            List<User> list = groupUserMap.get(group);
//            for(User us : list){
//                if(us.getName().equals(user.getName()) && us.getMobile().equals(user.getMobile())){
//                   if(adminMap.containsKey(group)){
//                      User as =  adminMap.get(group);
//                      if(as.getName().equals(user.getName())) return -1;
//                      else {
//
//                      }
//                   }
//                }
//            }
//
//        }
//        return 0;
//    }
}
