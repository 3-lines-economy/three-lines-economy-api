package com.example.tleapplication.support.exception

class ClaimNotFoundException : RuntimeException("인증 실패: 토큰에 claim이 없습니다.")