package CodeSquad.IssueTracker.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("User가 잘 저장되고 반환결과로 user가 잘 반환된다")
    void save() {
        //given
        User user = new User();
        user.setPassword("<PASSWORD>");
        user.setLoginId("code_squad");
        user.setNickName("Johnnie");

        //when
        User savedUser = userService.save(user);

        //then
        assertNotNull(savedUser);
        assertEquals(user.getLoginId(), savedUser.getLoginId());
        assertEquals(user.getNickName(), savedUser.getNickName());
    }

    @Test
    void findByLoginId() {
        // given
        User user = new User();
        user.setPassword("<PASSWORD>");
        user.setLoginId("code_squad");
        user.setNickName("Johnnie");
        userService.save(user);

        // when
        User findUser = userService.findByLoginId("code_squad")
                .orElseThrow(() -> new AssertionError("User not found with loginId: code_squad"));

        // then
        assertThat(findUser).isEqualTo(user);
    }

    @Test
    void findById() {
        // given
        User user = new User();
        user.setPassword("password123");
        user.setLoginId("tester");
        user.setNickName("테스터");
        User savedUser = userService.save(user);

        // when
        User findUser = userService.findById(savedUser.getId())
                .orElseThrow(() -> new AssertionError("User not found by ID"));

        // then
        assertThat(findUser).isEqualTo(savedUser);
    }

    @Test
    void findAll() {
        // given
        User user1 = new User();
        user1.setPassword("1111");
        user1.setLoginId("user_a");
        user1.setNickName("유저A");
        userService.save(user1);

        User user2 = new User();
        user2.setPassword("2222");
        user2.setLoginId("user_b");
        user2.setNickName("유저B");
        userService.save(user2);

        // when
        Iterable<User> users = userService.findAll();

        // then
        assertThat(users).anyMatch(u -> u.getLoginId().equals("user_a"));
        assertThat(users).anyMatch(u -> u.getLoginId().equals("user_b"));
    }

    @Test
    void deleteById() {
        // given
        User user = new User();
        user.setPassword("deletepass");
        user.setLoginId("todelete");
        user.setNickName("삭제유저");
        User savedUser = userService.save(user);

        // when
        userService.deleteById(savedUser.getId());

        // then
        Optional<User> deleted = userService.findById(savedUser.getId());
        assertThat(deleted).isEmpty();
    }
}