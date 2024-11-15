package com.sparta.aibusinessproject.domain.member.controller;

import com.sparta.aibusinessproject.domain.member.dto.request.ModifyMemberInfoRequest;
import com.sparta.aibusinessproject.domain.member.dto.response.MemberInfoResponse;
import com.sparta.aibusinessproject.domain.member.service.MemberService;
import com.sparta.aibusinessproject.global.exception.Response;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public Response<MemberInfoResponse> getMemberInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return Response.success(memberService.getMemberInfo(userDetails.getUsername()));
    }

    @PutMapping
    public Response<MemberInfoResponse> modifyMemberInfo(@AuthenticationPrincipal UserDetails userDetails,
                                                         @RequestBody ModifyMemberInfoRequest request) {
        return Response.success(memberService.modifyMemberInfo(request));
    }

    @DeleteMapping
    public Response<UUID> deleteMember(@AuthenticationPrincipal UserDetails userDetails) {
        return Response.success(memberService.deleteMember(userDetails.getUsername()));
    }
}
