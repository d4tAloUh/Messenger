package messenger.backend.seeds;

import com.github.javafaker.Faker;
import messenger.backend.chat.PrivateChatEntity;
import messenger.backend.message.MessageEntity;
import messenger.backend.user.UserEntity;
import messenger.backend.userChat.UserChat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FakerService {

    public static Faker faker = new Faker();

    public static final int userCount = 10;
    public static final int privateChatCount = 20;
    public static final int msgsPerChat = 16;

    public static void generateRandomData() {
        //creating users
        List<UserEntity> users =
                Stream
                        .generate(() -> UserEntity.generateUser())
                        .limit(userCount)
                        .collect(Collectors.toList());


        //creating private chats
        List<PrivateChatEntity> privateChats =
                Stream
                        .generate(() -> PrivateChatEntity.generatePrivateChat())
                        .limit(privateChatCount)
                        .collect(Collectors.toList());

        //generating UserChats for private chats
        List<UserChat> privateUserChats = new ArrayList<>();
        int privateChatIndex = 0;
        for(Tuple<Integer,Integer> pair : getUserPairs(privateChatCount,userCount)) {
            //creating one UserChat for each user
            UserChat firstLink = UserChat.generateUserChat(UserChat.PermissionLevel.OWNER, privateChats.get(privateChatIndex), users.get(pair.x));
            UserChat secondLink = UserChat.generateUserChat(UserChat.PermissionLevel.OWNER, privateChats.get(privateChatIndex), users.get(pair.y));
            //adding them to List of userChats
//            privateUserChats.add(firstLink);
//            privateUserChats.add(secondLink);

            //adding UserChats to UserEntity
            users.get(pair.x).appendUserChat(firstLink);
            users.get(pair.y).appendUserChat(secondLink);

            //TODO both links? Not sure..
            //adding UserChats to PrivateChatEntity
            privateChats.get(privateChatIndex).appendUserChat(firstLink);
            privateChats.get(privateChatIndex).appendUserChat(secondLink);

            List<MessageEntity> firstUserMessages = Stream
                    .generate(() -> MessageEntity.generateMessage(firstLink))
                    .limit(msgsPerChat/2)
                    .collect(Collectors.toList());

            List<MessageEntity> secondUserMessages = Stream
                    .generate(() -> MessageEntity.generateMessage(secondLink))
                    .limit(msgsPerChat/2)
                    .collect(Collectors.toList());

            firstLink.appendMessages(firstUserMessages);
            secondLink.appendMessages(secondUserMessages);

            ++privateChatIndex;
        }
//        System.out.println(users.get(0));
//        System.out.println(users.get(0).getUserChats());

//        System.out.println(users.get(1));
//        System.out.println(users.get(2));



    }


    private static List<Tuple<Integer, Integer>> getUserPairs(int pairs, int usersLength) {
        List<Tuple<Integer, Integer>> result = new ArrayList<>();
        Random random = new Random();
        int first, second;

        for (int i = 0; i < pairs; i++) {
            first = random.nextInt(usersLength);
            do
                second = random.nextInt(usersLength);
            while (first == second);

            result.add(new Tuple<Integer, Integer>(Integer.valueOf(first), Integer.valueOf(second)));
        }

        return result;
    }

}
