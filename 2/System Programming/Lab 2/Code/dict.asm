global find_word
extern string_equals

section .text

; в rdi находится адрес слова
; в rsi адрес последнего слова
; возвращает результат в rax (адрес/0)
find_word: 
    .loop:
    test rsi, rsi 
    jz .end 
    push rsi;позволяет находить слово
    add rsi, 8
    call string_equals
    pop rsi
    test rax, rax
    jnz .found
    mov rsi, [rsi]
    jmp .loop
    .found:
    mov rax, rsi
    .end:
    ret
