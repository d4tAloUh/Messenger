package messenger.backend.seeds;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import messenger.backend.auth.access_levels.Role;
import messenger.backend.chat.PrivateChatEntity;
import messenger.backend.chat.personal.PersonalChatRepository;
import messenger.backend.message.MessageEntity;
import messenger.backend.message.MessageRepository;
import messenger.backend.user.UserEntity;
import messenger.backend.user.UserRepository;
import messenger.backend.userChat.UserChat;
import messenger.backend.userChat.UserChatRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FakerService {

    private final UserRepository userRepository;
    private final PersonalChatRepository privateChatRepo;
    private final MessageRepository messageRepository;
    private final UserChatRepository userChatRepository;

    public static final Faker faker = new Faker();

    public final int userCount = 20;
    public final int privateChatCount = 100;
    public final int msgsPerChat = 64;

    public void generateRandomData() {
        //creating users
        List<UserEntity> users =
                Stream
                        .generate(() -> UserEntity.generateUser())
                        .limit(userCount - 1)
                        .collect(Collectors.toList());
        users.add(UserEntity.builder()
                .username("user")
                .fullName("userFullName")
                .password("$2y$12$ixe4Lh4uQVncJDzPJWckfeyTXPMkuVZm55miqLdnn/TjH0FoF8HOq") //user (BCryptPasswordEncoder(12))
                .role(Role.USER)
                .build()
        );
        userRepository.saveAll(users);

        users.stream().map(UserEntity::getUsername).forEach(System.out::println);

        //creating private chats
        List<PrivateChatEntity> privateChats =
                Stream
                        .generate(() -> PrivateChatEntity.generatePrivateChat())
                        .limit(privateChatCount)
                        .collect(Collectors.toList());

        privateChatRepo.saveAll(privateChats);

        //generating UserChats for private chats
        int privateChatIndex = 0;
        for(Tuple pair : getUserPairs(privateChatCount,userCount)) {
            //creating one UserChat for each user
            UserChat firstLink = UserChat.generateUserChat(UserChat.PermissionLevel.OWNER, privateChats.get(privateChatIndex), users.get(pair.x));
            UserChat secondLink = UserChat.generateUserChat(UserChat.PermissionLevel.OWNER, privateChats.get(privateChatIndex), users.get(pair.y));

            userChatRepository.save(firstLink);
            userChatRepository.save(secondLink);

            List<MessageEntity> firstUserMessages = Stream
                    .generate(() -> MessageEntity.generateMessage(firstLink))
                    .limit(msgsPerChat/2)
                    .collect(Collectors.toList());

            List<MessageEntity> secondUserMessages = Stream
                    .generate(() -> MessageEntity.generateMessage(secondLink))
                    .limit(msgsPerChat/2)
                    .collect(Collectors.toList());

            messageRepository.saveAll(firstUserMessages);
            messageRepository.saveAll(secondUserMessages);


            ++privateChatIndex;
        }


    }


    private HashSet<Tuple> getUserPairs(final int pairs,final int usersLength) {
        HashSet<Tuple> result = new HashSet<>();
        Random random = new Random();
        int first, second;

        while(result.size() < pairs){
            first = random.nextInt(usersLength);
            do
                second = random.nextInt(usersLength);
            while (first == second);

            result.add(new Tuple(Integer.valueOf(first), Integer.valueOf(second)));
        }

//        for (Tuple pair : result){
//            System.out.println(pair);
//        }

        return result;
    }

}
