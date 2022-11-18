package project.bookstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.bookstore.dto.MemberFindDto;
import project.bookstore.dto.MemberFindParamDto;
import project.bookstore.dto.MemberSaveDto;
import project.bookstore.dto.MemberUpdateDto;
import project.bookstore.service.MemberService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    // 회원 저장
    @PostMapping("/add")
    public String save(@Validated @RequestBody MemberSaveDto dto) {
        memberService.save(dto);

        return "회원가입 완료!";
    }

    // 모든 회원 조회
    @GetMapping("{memberId}/all")
    public Page<MemberFindDto> findAll(@PathVariable Long memberId, Pageable pageable, @RequestBody MemberFindParamDto param) {
        log.info("page info = {}", pageable);
        return memberService.findAllByParam(memberId, pageable, param);
    }

    @GetMapping("{memberId}")
    public MemberFindDto findById(@PathVariable Long memberId) {
        return memberService.findById(memberId);
    }

    //회원 정보 수정
    @PostMapping("{memberId}/edit")
    public MemberFindDto update(@PathVariable Long memberId, @RequestBody MemberUpdateDto dto) {
        return memberService.update(memberId, dto);
    }

    //삭제
    @PostMapping("{memberId}/delete")
    public String delete(@PathVariable Long memberId) {
        memberService.delete(memberId);
        return "삭제 되었습니다.";
    }
}
