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
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/find/all")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list";  // 사용자 리스트를 보여주는 뷰 (예: user-list.html)
    }

    @GetMapping("/find/{id}")
    public String getUserById(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "user-detail";  // 사용자 세부 정보를 보여주는 뷰 (예: user-detail.html)
    }

    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";  // 회원가입 폼을 보여주는 뷰 (예: signup.html)
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user, Model model) {
        userService.addUser(user);
        return "redirect:/user/find/all";  // 사용자 리스트 페이지로 리다이렉트
    }

    @GetMapping("/update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "user-edit";  // 사용자 정보를 수정하는 폼 뷰 (예: user-edit.html)
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        User updateUser = userService.findUserById(id);
        updateUser.setEmail(user.getEmail());
        updateUser.setNickName(user.getNickName());
        updateUser.setPhoneNumber(user.getPhoneNumber());
        userService.addUser(updateUser);
        return "redirect:/user/find/" + id;  // 수정된 사용자 세부 정보 페이지로 리다이렉트
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        userService.delete(id);
        return "redirect:/user/find/all";  // 삭제 후 사용자 리스트 페이지로 리다이렉트
    }
}
