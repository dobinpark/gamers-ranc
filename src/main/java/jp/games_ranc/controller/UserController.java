package jp.games_ranc.controller;

import jp.games_ranc.entity.User;
import jp.games_ranc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class UserController {

    private final UserService userService;

    @GetMapping("/user/all")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user";  // 사용자 리스트를 보여주는 뷰 (예: user-list.html)
    }

    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "userDetail";  // 사용자 세부 정보를 보여주는 뷰 (예: user-detail.html)
    }

    // 회원가입할 때 요구사항
    // 1. 비밀번호 2차 인증(첫번 째 비밀번호와 두번 째 비밀번호가 같을 시 true)
    // 2. 각 항목마다 예시를 보여줌.

    @GetMapping("/user/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";  // 회원가입 폼을 보여주는 뷰 (예: signup.html)
    }

    @PostMapping("/user/add")
    public String addUser(@ModelAttribute("user") User user, Model model) {
        userService.addUser(user);
        return "redirect:/user/all";  // 사용자 리스트 페이지로 리다이렉트
    }

    @GetMapping("/user/update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "userEdit";  // 사용자 정보를 수정하는 폼 뷰 (예: user-edit.html)
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        User updateUser = userService.findUserById(id);
        updateUser.setEmail(user.getEmail());
        updateUser.setNickName(user.getNickName());
        updateUser.setPhoneNumber(user.getPhoneNumber());

        // 비밀번호 업데이트 로직 추가 (비밀번호 변경이 가능한 경우)
        if (!user.getPassword().isEmpty()) {
            updateUser.setPassword(user.getPassword());
            // 비밀번호 해싱 로직을 추가할 수 있습니다.
        }

        userService.addUser(updateUser);
        return "redirect:/find/" + id;
    }

    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        userService.delete(id);
        return "redirect:/find/all";  // 삭제 후 사용자 리스트 페이지로 리다이렉트
    }
}
