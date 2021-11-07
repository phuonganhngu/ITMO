extern read_word
extern print_newline
extern print_string
extern string_length
extern find_word

global _start

section .data
not_found_exception: db "Sorry, we can't find your word", 0

section .text
%include "colon.inc"
%include "words.inc"

_start:
    call read_word
    mov rsi, pointer 
    call find_word
    test rax, rax
    jz .not_found

    add rax, 8
    mov rdi, rax
    call string_length
    add rdi, rax
    inc rdi
    call print_string
    jmp .fin

.not_found:
    mov rdi, not_found_exception
    call string_length
    mov rsi, rdi 
    mov rdx, rax 
    mov rax, 1
    mov rdi, 2 
    syscall
    .fin:
    mov rax, 60
    mov rdi, 0    
    syscall
