package jp.games_ranc.controller;

import jp.games_ranc.DTO.JoinDTO;
import jp.games_ranc.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDTO) {

        joinService.joinProcess(joinDTO);

        System.out.println(joinDTO.getUserName());
        joinService.joinProcess(joinDTO);

        return "ok";
    }
}
